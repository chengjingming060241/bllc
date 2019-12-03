package com.gizwits.lease.device.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.enums.SysUserType;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.entity.SysUserExt;
import com.gizwits.boot.sys.service.SysUserExtService;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.CommonEventPublisherUtils;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.boot.utils.QueryResolverUtils;
import com.gizwits.boot.utils.SysConfigUtils;
import com.gizwits.lease.config.CommonSystemConfig;
import com.gizwits.lease.constant.*;
import com.gizwits.lease.device.dao.DeviceDao;
import com.gizwits.lease.device.dao.DeviceStockDao;
import com.gizwits.lease.device.entity.*;
import com.gizwits.lease.device.entity.dto.*;
import com.gizwits.lease.device.service.*;
import com.gizwits.lease.device.vo.*;
import com.gizwits.lease.enums.DeviceActiveStatusType;
import com.gizwits.lease.enums.DeviceOriginType;
import com.gizwits.lease.enums.ProductOperateType;
import com.gizwits.lease.enums.ShowType;
import com.gizwits.lease.event.BindGizwitsDeviceEvent;
import com.gizwits.lease.event.DeviceLocationEvent;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.manager.dto.OperatorAllotDeviceDto;
import com.gizwits.lease.manager.entity.Agent;
import com.gizwits.lease.manager.entity.Manufacturer;
import com.gizwits.lease.manager.service.AgentService;
import com.gizwits.lease.manager.service.ManufacturerService;
import com.gizwits.lease.model.DeviceAddressModel;
import com.gizwits.lease.model.MapDataEntity;
import com.gizwits.lease.operator.service.OperatorExtService;
import com.gizwits.lease.order.entity.OrderBase;
import com.gizwits.lease.order.service.OrderBaseService;
import com.gizwits.lease.product.dto.*;
import com.gizwits.lease.product.entity.*;
import com.gizwits.lease.product.service.*;
import com.gizwits.lease.redis.RedisService;
import com.gizwits.lease.stat.dto.StatDeviceDto;
import com.gizwits.lease.stat.vo.StatAlarmWidgetVo;
import com.gizwits.lease.stat.vo.StatDeviceWidgetVo;
import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.user.service.UserService;
import com.gizwits.lease.user.service.UserWeixinService;
import com.gizwits.lease.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 设备表 服务实现类
 * </p>
 *
 * @author zhl
 * @since 2017-07-11
 */
@Service
public class DeviceStockServiceImpl extends ServiceImpl<DeviceStockDao, DeviceStock> implements DeviceStockService {
    protected static Logger logger = LoggerFactory.getLogger("DEVICE_LOGGER");

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private DeviceService deviceService;



    /**
     * 2	厂商管理员
     * 3	经销商
     * 4	操作员
     * 5    入库员
     */
    public String addDevice(DeviceAddDto deviceAddDto) {
        if (Objects.isNull(deviceAddDto.getDeviceAddDetailsDtos())){
            LeaseException.throwSystemException(LeaseExceEnums.WORK_ORDER_MAC_BLANK);
        }
        String result = "";
        SysUser sysUser = sysUserService.getCurrentUserOwner();
        DeviceStock device = new DeviceStock();
        Date now = new Date();

        List<String> addResult = new ArrayList();

        //厂商
        /**
         * 点击“有控制器”按钮，则开始扫码，自动录入“设备MAC”和“SN1（控制器码）”到扫码框中（关联设备MAC和SN1）；
         * 点击“无控制器”按钮，开始扫码录入，区别是“无控制器”模式只需录入“设备MAC”。
         */
        if (sysUser.getIsAdmin().equals(SysUserType.MANUFACTURER.getCode())){
            List<DeviceStock> devices = new ArrayList<>();

            for (DeviceAddDetailsDto detailsDto : deviceAddDto.getDeviceAddDetailsDtos()) {
                if (Objects.isNull(detailsDto.getMac())){
                    LeaseException.throwSystemException(LeaseExceEnums.WORK_ORDER_MAC_BLANK);
                }
                DeviceStock oldDevice = getDeviceByMac(detailsDto.getMac());
                if (oldDevice != null) {
                    //批量插入预处理
                    addResult.add(detailsDto.getMac());

                } else {
                    device.setSno(deviceService.getSno());
                    device.setMac(detailsDto.getMac());
                    device.setUtime(now);
                    device.setCtime(now);
                    device.setSweepCodeStatus(DeviceSweepCodeStatus.PENDING_CODE.getCode());
                    device.setSysUserId(sysUser.getId());
                    device.setRemarks(deviceAddDto.getRemarks());
                    if (deviceAddDto.getControlType()){  //有控制器
                        device.setsN1(detailsDto.getsN1());
                    }
                    device.setControlType(deviceAddDto.getControlType());
                    devices.add(device);
                }
            }

            for (DeviceStock dev : devices) {
                insertOrUpdate(dev);
            }

            if (addResult.size()>0){
                 result = "以下设备添加失败:"+addResult.toString()+",原因："+LeaseExceEnums.DEVICE_EXISTS;
            }else {
                 result = "添加成功";

            }
            return result;

        }

        /**
         * 操作员扫码模式有两种：
         * 点击“有IMEI”按钮，则开始扫码，由操作员依次录入SN2码、IMEI码（关联SN2码和IMEI码）、再扫SN1码，同SN2码做关联；
         * 点击“无IMEI”按钮，则只需要录入SN2，同厂商录入的设备MAC码做关联。配对没有SN1的设备
         * 点击“完成”按钮，将录入的信息更新到由供应商创建在设备列表中记录中，此时设备状态由待录入变为可入库。
         */
        if (sysUser.getIsAdmin().equals(SysUserType.OPERATOR.getCode())){
            for (DeviceAddDetailsDto detailsDto : deviceAddDto.getDeviceAddDetailsDtos()) {
              if (deviceAddDto.getIMEIType()){
                  DeviceStock deviceSn1 = selectOne(new EntityWrapper<DeviceStock>().eq("sn1", detailsDto.getsN1()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
                  if (Objects.isNull(deviceSn1)){
                      addResult.add(detailsDto.getsN2());
                  }else {
                      deviceSn1.setiMEI(detailsDto.getiMEI());
                      deviceSn1.setsN2(detailsDto.getsN2());
                      deviceSn1.setUtime(now);
                      deviceSn1.setSweepCodeTime(now);
                      deviceSn1.setOperatorId(sysUser.getJobNumber());
                      deviceSn1.setOperatorName(sysUser.getRealName());
                      deviceSn1.setSweepCodeStatus(DeviceSweepCodeStatus.WAIT_TO_ENTRY.getCode());
                      insertOrUpdate(deviceSn1);
                  }
              }else{
                  DeviceStock devices = selectOne(new EntityWrapper<DeviceStock>().isNull("sn1").eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
                  if (Objects.isNull(devices)){
                      addResult.add(detailsDto.getsN2());
                  }else {
                      devices.setsN2(detailsDto.getsN2());
                      devices.setUtime(now);
                      devices.setSweepCodeTime(now);
                      devices.setOperatorId(sysUser.getJobNumber());
                      devices.setOperatorName(sysUser.getRealName());
                      devices.setSweepCodeStatus(DeviceSweepCodeStatus.WAIT_TO_ENTRY.getCode());
                      insertOrUpdate(devices);
                  }
              }
            }
            if (addResult.size()>0){
                result = "以下设备添加失败:"+addResult.toString()+",原因："+LeaseExceEnums.DEVICE_DONT_EXISTS;
            }else {
                result = "添加成功";

            }
            return result;

        }

        //入库员
        /**
         * 入库员点击“开始扫码”，扫描设备的SN2码，再在扫码框填写“入库批次”；
         * 选择“产品型号”、“供应商”（由客户提供供应商，系统写死）；
         * 选择“入库仓库”、“入库时间”；填写“经办人”，
         * “备注”（例如备注“退货”，表明当前批次的设备是退货机）。
         * ”入库设备“记录在“库存管理-库存列表”中；
         * ”入库记录“在”库存管理-入库记录表“中查看。
         */
        if (sysUser.getIsAdmin().equals(SysUserType.CLERK.getCode())){
            for (DeviceAddDetailsDto detailsDto : deviceAddDto.getDeviceAddDetailsDtos()) {
                DeviceStock devicePut = selectOne(new EntityWrapper<DeviceStock>().eq("sn2", detailsDto.getsN2()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
                //判断产品是否符合规则
                ProductCategory productCategory = productCategoryService.selectOne(new EntityWrapper<ProductCategory>().eq("category_type", deviceAddDto.getControlType()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
                if (productCategory.getCategoryId() == null){
                    addResult.add(detailsDto.getsN2());
                    continue;
                }
                Product product = judgeProductIsExist(productCategory.getCategoryId());
                if (Objects.isNull(product)) {
                    addResult.add(detailsDto.getsN2());
                    continue;
                }
                devicePut.setUtime(now);
                devicePut.setWarehousingName(sysUser.getRealName());//经办人
                devicePut.setWarehousingId(sysUser.getJobNumber());
                devicePut.setProductCategoryId(productCategory.getId()); //产品ID
                devicePut.setSweepCodeStatus(DeviceSweepCodeStatus.To_Be_But_Bf_Stock.getCode());
                devicePut.setLaunchAreaId(deviceAddDto.getDeviceLaunchAreaId());  //仓库id
                devicePut.setLaunchAreaName(deviceAddDto.getDeivceLaunchAreaName()); //仓库名称
                devicePut.setProductId(product.getId());   //品类ID
                devicePut.setProductName(product.getName());  //品类名称
                devicePut.setEntryTime(now);  //入库时间
                devicePut.setRemarks(deviceAddDto.getRemarks());
                devicePut.setSupplierName(deviceAddDto.getSupplierName());  //供应商
                devicePut.setBatch(deviceAddDto.getBatch());  //批次
                insertOrUpdate(devicePut);
            }

            if (addResult.size()>0){
                result = "以下设备添加失败:"+addResult.toString()+",原因："+LeaseExceEnums.PRODUCT_DONT_EXISTS;
            }else {
                result = "添加成功";

            }

            return result;

        }
         return null;


    }


    private Product judgeProductIsExist(Integer productId) {
        Product product = productService.selectById(productId);
        //如果产品被删除或者产品为空
        if (product.getIsDeleted().equals(1) || Objects.isNull(product)) {
            return null;
        }
        return product;
    }


    @Override
    public DeviceForSpeedDetailDto detail2(String id) {
        DeviceStock device = selectById(id);
        if (Objects.isNull(device)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        return new DeviceForSpeedDetailDto(device);
    }


    @Override
    public DeviceForStockDetailDto stockDetails(String id) {
        DeviceStock device = selectById(id);
        if (Objects.isNull(device)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        DeviceForStockDetailDto stockDetail =  new DeviceForStockDetailDto(device);
        stockDetail.setSweepCodeStatusName(DeviceSweepCodeStatus.getName(device.getSweepCodeStatus()));
        stockDetail.setCategoryType(productCategoryService.selectById(device.getProductCategoryId()).getCategoryType());   //型号
        return stockDetail;
    }

    @Override
    public Page<DeviceForSpeedDetailDto> putDeviceDetails(Pageable<DeviceQueryDto> pageable) {
        Page<DeviceStock> page = new Page<>();
        BeanUtils.copyProperties(pageable, page);
        Wrapper<DeviceStock> wrapper = new EntityWrapper<>();

        //修改时间
        if (pageable.getQuery().getUpTimeStart()!=null && pageable.getQuery().getUpTimeEnd()!=null){
            wrapper.between("batch",pageable.getQuery().getUpTimeStart(),pageable.getQuery().getUpTimeEnd());
        }
        wrapper.orderBy("batch", false);  //根据更新时间排序

        Page<DeviceStock> page1 = selectPage(page,
                QueryResolverUtils.parse(pageable.getQuery(), wrapper));
        List<DeviceStock> devices = page1.getRecords();
        Page<DeviceForSpeedDetailDto> result = new Page<>();
        BeanUtils.copyProperties(page1, result);
        List<DeviceForSpeedDetailDto> list = getputDeviceDetails(devices);
        result.setRecords(list);
        return result;
    }


    @Override
    public String stockUpdate(DeviceForUpdateDto dto) {
        DeviceStock exist = selectById(dto.getSno());
        String isExistence = DoesItAlreadyExist(dto.getSno(), dto.getMac(), dto.getsN1(), dto.getsN2(), dto.getiMEI());
        if (!isExistence.equals("")){ return isExistence; }

        if (!dto.getMac().equals(exist.getMac())){
           exist.setMac(dto.getMac());
        }
        if (dto.getsN1()!=null && !dto.getsN1().equals(exist.getsN1())){
           exist.setsN1(dto.getsN1());
        }
        if (dto.getsN2()!=null && !dto.getsN2().equals(exist.getsN2())){
            exist.setsN2(dto.getsN2());
        }
        if (dto.getiMEI()!=null && !dto.getiMEI().equals(exist.getiMEI())){
            exist.setiMEI(dto.getiMEI());
        }
        if (dto.getRemarks()!=null && !dto.getRemarks().equals(exist.getRemarks())){
            exist.setRemarks(dto.getRemarks());
        }

        exist.setUtime(new Date());

        updateAllColumnById(exist);

        return LeaseExceEnums.SUCCESSFUL_OPERATION.getMessage();
    }



    @Override
    public Page<DeviceShowDto> listPage(Pageable<DeviceQueryDto> pageable) {

        Page<DeviceStock> page = new Page<>();
        BeanUtils.copyProperties(pageable, page);
        Wrapper<DeviceStock> wrapper = new EntityWrapper<>();

        //修改时间
        if (pageable.getQuery().getUpTimeStart()!=null && pageable.getQuery().getUpTimeEnd()!=null){
            wrapper.between("ctime",pageable.getQuery().getUpTimeStart(),pageable.getQuery().getUpTimeEnd());
        }
        wrapper.orderBy("ctime", false);  //根据更新时间排序

        Page<DeviceStock> page1 = selectPage(page,
                QueryResolverUtils.parse(pageable.getQuery(), wrapper));
        List<DeviceStock> devices = page1.getRecords();
        Page<DeviceShowDto> result = new Page<>();
        BeanUtils.copyProperties(page1, result);
        List<DeviceShowDto> list = getDeviceShowDtos(devices);
        result.setRecords(list);
        return result;
    }

    @Override
    public Page<DeviceShowDto> StockListPage(Pageable<DeviceQueryDto> pageable) {

        Page<DeviceStock> page = new Page<>();
        BeanUtils.copyProperties(pageable, page);
        Wrapper<DeviceStock> wrapper = new EntityWrapper<>();

        //修改时间
        if (pageable.getQuery().getUpTimeStart()!=null && pageable.getQuery().getUpTimeEnd()!=null){
            wrapper.between("ctime",pageable.getQuery().getUpTimeStart(),pageable.getQuery().getUpTimeEnd());
        }
        wrapper.orderBy("ctime", false);  //根据更新时间排序

        Page<DeviceStock> page1 = selectPage(page,
                QueryResolverUtils.parse(pageable.getQuery(), wrapper));
        List<DeviceStock> devices = page1.getRecords();
        Page<DeviceShowDto> result = new Page<>();
        BeanUtils.copyProperties(page1, result);
        List<DeviceShowDto> list = getStockDeviceShowDtos(devices);
        result.setRecords(list);
        return result;
    }

    @Override
    public Page<DeviceShowDto> putListPage(Pageable<DeviceQueryDto> pageable) {

        Page<DeviceStock> page = new Page<>();
        BeanUtils.copyProperties(pageable, page);
        Wrapper<DeviceStock> wrapper = new EntityWrapper<>();
        //修改时间
        if (pageable.getQuery().getUpTimeStart()!=null && pageable.getQuery().getUpTimeEnd()!=null){
            wrapper.between("entry_time",pageable.getQuery().getUpTimeStart(),pageable.getQuery().getUpTimeEnd());
        }
        wrapper.orderBy("entry_time", false);  //根据更新时间排序
        wrapper.groupBy("batch");

        Page<DeviceStock> page1 = selectPage(page,
                QueryResolverUtils.parse(pageable.getQuery(), wrapper));
        List<DeviceStock> devices = page1.getRecords();
        Page<DeviceShowDto> result = new Page<>();
        BeanUtils.copyProperties(page1, result);
        List<DeviceShowDto> list = getPutDeviceShowDtos(devices);
        result.setRecords(list);
        return result;
    }


    private List<DeviceShowDto> getDeviceShowDtos(List<DeviceStock> devices) {
        List<DeviceShowDto> list = new ArrayList<>(devices.size());
        for (DeviceStock device : devices) {
            DeviceShowDto showDto = getDeviceShowDto(device);
            list.add(showDto);
        }
        return list;
    }

    /**库存列表*/
    private List<DeviceShowDto> getStockDeviceShowDtos(List<DeviceStock> devices) {
        List<DeviceShowDto> list = new ArrayList<>(devices.size());
        for (DeviceStock device : devices) {
            DeviceShowDto showDto = getDeviceShowDto(device);
            showDto.setSweepCodeStatus(DeviceSweepCodeStatus.getName(device.getSweepCodeStatus()));
            showDto.setCategoryType(productCategoryService.selectById(device.getProductCategoryId()).getCategoryType());   //型号
            showDto.setLaunchArea(device.getLaunchAreaName());
            list.add(showDto);
        }
        return list;
    }

    /**入库列表 */
    private List<DeviceShowDto> getPutDeviceShowDtos(List<DeviceStock> devices) {
        List<DeviceShowDto> list = new ArrayList<>(devices.size());
        for (DeviceStock device : devices) {
            DeviceShowDto showDto = new DeviceShowDto();
            showDto.setCategoryType(productCategoryService.selectById(device.getProductCategoryId()).getCategoryType());   //型号
            showDto.setLaunchArea(device.getLaunchAreaName());
            showDto.setDeviceCount(selectCount(new EntityWrapper<DeviceStock>()
                    .eq("batch",device.getBatch()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode())));
            showDto.setSupplierName(device.getSupplierName());
            showDto.setOperatorName(device.getOperatorName());
            showDto.setEntryTime(device.getEntryTime());
            list.add(showDto);
        }
        return list;
    }

    /**库存详情*/
    private List<DeviceForSpeedDetailDto> getputDeviceDetails(List<DeviceStock> devices) {
        List<DeviceForSpeedDetailDto> list = new ArrayList<>(devices.size());
        for (DeviceStock device : devices) {
            DeviceForSpeedDetailDto showDto = new  DeviceForSpeedDetailDto(device);
            showDto.setCategoryType(productCategoryService.selectById(device.getProductCategoryId()).getCategoryType());   //型号

            list.add(showDto);
        }
        return list;
    }

    private List<DeviceShowDto> getDeviceShowDtos2(List<DeviceStock> devices) {
        List<DeviceShowDto> list = new ArrayList<>(devices.size());
        for (DeviceStock device : devices) {
            DeviceShowDto showDto = getDeviceShowDto(device);
            list.add(showDto);
        }
        return list;
    }


    private DeviceShowDto getDeviceShowDto(DeviceStock device) {
        DeviceShowDto deviceShowDto = new DeviceShowDto();
        deviceShowDto.setSno(device.getSno());
        deviceShowDto.setMac(device.getMac());
        deviceShowDto.setsN1(device.getsN1());
        deviceShowDto.setsN2(device.getsN2());
        deviceShowDto.setiMEI(device.getiMEI());
        deviceShowDto.setRemarks(device.getRemarks());
        deviceShowDto.setCtime(device.getCtime());
        deviceShowDto.setProduct(device.getProductName());
        deviceShowDto.setLaunchArea(device.getLaunchAreaName());
        deviceShowDto.setSweepCodeStatus(DeviceSweepCodeStatus.getName(device.getSweepCodeStatus()));
        return deviceShowDto;
    }



    @Override
    public String deleteDevice(List<String> snos) {

        boolean fails = false;
        List<DeviceStock> devices = selectList(new EntityWrapper<DeviceStock>().in("sno", snos).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
        for (DeviceStock device : devices) {

                device.setUtime(new Date());
                device.setIsDeleted(DeleteStatus.DELETED.getCode());
            fails = updateById(device);

        }

        if (fails){
            return "删除成功";
        }else {
            return "删除失败";
        }

    }

    @Override
    public DeviceStock getDeviceByMac(String mac) {
        Wrapper<DeviceStock> wrapper = new EntityWrapper<>();
        wrapper.eq("mac", mac).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        return selectOne(wrapper);
    }


    @Override
    public String DoesItAlreadyExist(String sno , String mac, String sn1 , String sn2, String iMEI){
        if (Objects.isNull(sno)) {
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_DONT_EXISTS);
        }

        if (selectCount(new EntityWrapper<DeviceStock>().eq("mac",mac)
                .eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).ne("sno", sno))>0){
            return LeaseExceEnums.DEVICE_MAC_EXISTS.getMessage();
        }
        if (sn1!=null && !sn1.equals("")){
            if (selectCount(new EntityWrapper<DeviceStock>().eq("sn1",sn1)
                    .eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).ne("sno", sno))>0){
                return LeaseExceEnums.DEVICE_SN1_EXISTS.getMessage();
            }
        }
        if (sn2!=null && !sn2.equals("")){
            if (selectCount(new EntityWrapper<DeviceStock>().eq("sn2",sn2)
                    .eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).ne("sno", sno))>0){
                return LeaseExceEnums.DEVICE_SN2_EXISTS.getMessage();
            }
        }
        if (sn2!=null && !sn2.equals("")){
            if (selectCount(new EntityWrapper<DeviceStock>().eq("imei",iMEI)
                    .eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).ne("sno", sno))>0){
                return LeaseExceEnums.DEVICE_IMEI_EXISTS.getMessage();
            }
        }
        return "";
    }

}
