package com.gizwits.lease.product.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.enums.SysUserShareDataEnum;
import com.gizwits.boot.exceptions.SysExceptionEnum;
import com.gizwits.boot.exceptions.SystemException;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.CommonEventPublisherUtils;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.boot.utils.QueryResolverUtils;
import com.gizwits.lease.constant.CommandType;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.DeviceToProductServiceMode;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.device.service.DeviceToProductServiceModeService;
import com.gizwits.lease.enums.ServiceType;
import com.gizwits.lease.event.NameModifyEvent;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.model.DeleteDto;
import com.gizwits.lease.product.dao.ProductServiceModeDao;
import com.gizwits.lease.product.dto.PriceAndNumDto;
import com.gizwits.lease.product.dto.ProductServiceDetailDto;
import com.gizwits.lease.product.dto.ProductServiceListQuerytDto;
import com.gizwits.lease.product.dto.ProductServiceModeForAddPageDto;
import com.gizwits.lease.product.dto.ProductServicecModeListDto;
import com.gizwits.lease.product.dto.ServiceTypeDto;
import com.gizwits.lease.product.entity.Product;
import com.gizwits.lease.product.entity.ProductCommandConfig;
import com.gizwits.lease.product.entity.ProductServiceDetail;
import com.gizwits.lease.product.entity.ProductServiceMode;
import com.gizwits.lease.product.service.ProductCommandConfigService;
import com.gizwits.lease.product.service.ProductService;
import com.gizwits.lease.product.service.ProductServiceDetailService;
import com.gizwits.lease.product.service.ProductServiceModeService;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 产品(或者设备)服务方式 服务实现类
 * </p>
 *
 * @author rongmc
 * @since 2017-06-28
 */
@Service
public class ProductServiceModeServiceImpl extends ServiceImpl<ProductServiceModeDao, ProductServiceMode> implements ProductServiceModeService {

    @Autowired
    private ProductService productService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ProductServiceDetailService productServiceDetailService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ProductCommandConfigService productCommandConfigService;

    @Autowired
    private ProductServiceModeDao productServiceModeDao;

    @Autowired
    private DeviceToProductServiceModeService deviceToProductServiceModeService;

    @Override
    public List<ServiceTypeDto> getServiceTypeByProductId(Integer ProductId) {
        EntityWrapper<ProductServiceMode> entity = new EntityWrapper<>();
        entity.eq("product_id", ProductId);
        List<ServiceTypeDto> serviceTypes = new ArrayList<>();
        List<ProductServiceMode> productServiceModes = selectList(entity);
        for (ProductServiceMode productServiceMode : productServiceModes) {
            ServiceTypeDto serviceTypeDto = new ServiceTypeDto();
            serviceTypeDto.setServiceType(productServiceMode.getServiceType());
            serviceTypeDto.setUnit(productServiceMode.getUnit());
            serviceTypes.add(serviceTypeDto);
        }
        return serviceTypes;
    }

    @Override
    public String deleteProductServiceModeById(List<Integer> productServiceModeIds) {
        SysUser current = sysUserService.getCurrentUserOwner();
        List<Integer> userIds = sysUserService.resolveSysUserAllSubAdminIds(current);
        StringBuilder sb = new StringBuilder("删除收费模式后，若设备不存在其他的收费模式，则设备将无法租赁");
        List<String> fails = new LinkedList<>();
        List<ProductServiceMode> serviceModes = selectList(new EntityWrapper<ProductServiceMode>().eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).in("id", productServiceModeIds));
        for (ProductServiceMode serviceMode : serviceModes) {
            //判断用户是否拥有删除收费模式的权限，能删除在列表看到的所有数据，防止调用接口乱删
            if (userIds.contains(serviceMode.getSysUserId())) {
                //置空设备的收费模式
                EntityWrapper<Device> entity = new EntityWrapper<>();
                entity.eq("service_id", serviceMode.getId()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
                List<Device> devices = deviceService.selectList(entity);
                if (!ParamUtil.isNullOrEmptyOrZero(devices)) {
                    for (Device device : devices) {
                        device.setServiceId(null);
                        device.setServiceName(null);
                        device.setUtime(new Date());
                        deviceService.updateAllColumnById(device);
                    }
                }
                //删除收费模式详情
                productServiceDetailService.deleteBatchByModeId(Collections.singletonList(serviceMode.getId()));
                serviceMode.setUtime(new Date());
                serviceMode.setIsDeleted(DeleteStatus.DELETED.getCode());
                //删除收费模式与设备的对应关系，现在一个设备可以对应多个收费模式
                DeviceToProductServiceMode deviceToProductServiceMode = new DeviceToProductServiceMode();
                deviceToProductServiceMode.setUtime(new Date());
                deviceToProductServiceMode.setIsDeleted(DeleteStatus.DELETED.getCode());
                EntityWrapper<DeviceToProductServiceMode> entityWrapper = new EntityWrapper<>();
                entityWrapper.eq("service_mode_id",serviceMode.getId());
                deviceToProductServiceModeService.update(deviceToProductServiceMode,entityWrapper);
                updateById(serviceMode);
            } else {
                fails.add(serviceMode.getName());
            }
        }

        return sb.toString();
    }

    @Override
    public ProductServiceMode selectById(Integer id) {
        return selectOne(new EntityWrapper<ProductServiceMode>().eq("id", id).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
    }

    @Override
    public List<ProductServiceModeForAddPageDto> getAddServiceModePageData() {
        List<Product> products = productService.getProductsWithPermission();
        List<ProductServiceModeForAddPageDto> result = new LinkedList<>();
        if (!ParamUtil.isNullOrEmptyOrZero(products)) {
            for (Product product : products) {
                EntityWrapper<ProductCommandConfig> entityWrapper = new EntityWrapper<>();
                entityWrapper.eq("is_deleted", DeleteStatus.NOT_DELETED.getCode())
                        .eq("command_type", CommandType.SERVICE.getCode());
                entityWrapper.eq("product_id", product.getId());
                List<ProductCommandConfig> serviceCommandList = productCommandConfigService.selectList(entityWrapper);
//                result.add(new ProductServiceModeForAddPageDto(product, serviceCommandList));
                /**
                 * 此返回结果用于测试
                 */
                result.add(new ProductServiceModeForAddPageDto(product));
            }
        }

        return result;
    }

    @Override
    public boolean judgeProductServiceModeIsExist(String serviceMode) {
        SysUser sysUser = sysUserService.getCurrentUser();
        EntityWrapper<ProductServiceMode> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("name", serviceMode)
                .in("sys_user_id", sysUserService.resolveOwnerAccessableUserIds(sysUser))
                .eq("status", 1)
                .eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        ProductServiceMode productServiceMode = selectOne(entityWrapper);
        return !ParamUtil.isNullOrEmptyOrZero(productServiceMode);

    }

    /**
     * 获取收费模式的单位
     */
    public String getServiceModeUnit(ProductServiceMode serviceMode) {
        if (serviceMode == null || ParamUtil.isNullOrEmptyOrZero(serviceMode.getServiceTypeId()) || ParamUtil.isNullOrEmptyOrZero(serviceMode.getServiceType())) {
            LeaseException.throwSystemException(LeaseExceEnums.SERVICE_MODE_CONFIG_ERROR);
        }
        //免费
        if (serviceMode.getServiceType().indexOf(ServiceType.FREE.getDesc()) >= 0) {
            return "";
        }
        ProductCommandConfig commandConfig = productCommandConfigService.selectById(serviceMode.getServiceTypeId());
        if (commandConfig == null) {
            LeaseException.throwSystemException(LeaseExceEnums.SERVICE_MODE_CONFIG_ERROR);
        }

        return productCommandConfigService.getSpecialDisplayUnit(commandConfig);
    }

    @Override
    public void updateProductServiceMode(ProductServicecModeListDto productServicecModeListDto, SysUser sysUser) {
        //更新收费模式表中数据
        ProductCommandConfig commandConfig = productCommandConfigService.selectById(productServicecModeListDto.getServiceTypeId());

        ProductServiceMode productServiceMode = selectOne(new EntityWrapper<ProductServiceMode>().eq("id", productServicecModeListDto.getId()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
        if (ParamUtil.isNullOrEmptyOrZero(productServiceMode)) {
            LeaseException.throwSystemException(LeaseExceEnums.SERVICE_MODE_NOT_EXIST);
        }
        productServiceMode.setUtime(new Date());
        String mode = productServicecModeListDto.getServiceMode();
        if (ParamUtil.isNullOrEmptyOrZero(mode)) {
            throw new SystemException(SysExceptionEnum.ILLEGAL_PARAM.getCode(), SysExceptionEnum.ILLEGAL_PARAM.getMessage());
        }
        String oldMode = productServiceMode.getName();
        productServiceMode.setName(mode);
        productServiceMode.setServiceType(productServicecModeListDto.getServiceType());
        productServiceMode.setServiceTypeId(productServicecModeListDto.getServiceTypeId());
        productServiceMode.setProductId(productServicecModeListDto.getProductId());
        productServiceMode.setSysUserId(sysUser.getId());
        productServiceMode.setSysUserName(sysUser.getUsername());
        productServiceMode.setStatus(1);
        productServiceMode.setCommand(productCommandConfigService.getDeviceModelCommand(commandConfig));
        productServiceMode.setIsFree(commandConfig.getIsFree());
        productServiceMode.setWorkingMode(commandConfig.getWorkingMode());
        productServiceMode.setUnitPrice(productServicecModeListDto.getUnitPrice());

        if (productServicecModeListDto.getServiceType().indexOf(ServiceType.FREE.getDesc()) >= 0) {
            productServiceMode.setUnit(null);
            productServiceDetailService.deleteByServiceModeId(productServicecModeListDto.getId());
            updateAllColumnById(productServiceMode);
            return;
        } else {
            productServiceMode.setUnit(productServicecModeListDto.getUnit());
        }

        updateAllColumnById(productServiceMode);
        if (!oldMode.equals(mode)) {
            NameModifyEvent<Integer> nameModifyEvent = new NameModifyEvent<Integer>(productServiceMode, productServiceMode.getId(), oldMode, mode);
            publishEvent(nameModifyEvent);
        }
        //更新收费详情表中数据
        List<PriceAndNumDto> priceAndNumDtos = productServicecModeListDto.getPriceAndNumDtoList();
        if (!ParamUtil.isNullOrEmptyOrZero(priceAndNumDtos)) {
            for (PriceAndNumDto p : priceAndNumDtos) {
                Integer id = p.getId();
                Double num = p.getNum();
                Double price = p.getPrice();
                ProductServiceDetailDto productServiceDetailDto = new ProductServiceDetailDto();
                productServiceDetailDto.setServiceModeId(productServicecModeListDto.getId());
                productServiceDetailDto.setProductId(productServicecModeListDto.getProductId());
                //免费模式
                productServiceDetailDto.setServiceType("免费");
                productServiceDetailDto.setSysUserId(sysUser.getId());
                boolean flag = productServiceDetailService.judgeProductServiceDetailIsExist(productServiceDetailDto);

                if (ParamUtil.isNullOrEmptyOrZero(id)) {
                    if (!flag) {
                        ProductServiceDetail productPriceDetail = new ProductServiceDetail();
                        productPriceDetail.setCtime(new Date());
                        productPriceDetail.setUtime(new Date());
                        productPriceDetail.setServiceModeId(productServicecModeListDto.getId());
                        productPriceDetail.setProductId(productServicecModeListDto.getProductId());
                        productPriceDetail.setServiceType(productServicecModeListDto.getServiceType());
                        productPriceDetail.setServiceTypeId(productServicecModeListDto.getServiceTypeId());
                        productPriceDetail.setCommand(productCommandConfigService.getCommandByConfig(commandConfig, p.getNum()));
                        productPriceDetail.setSysUserId(sysUser.getId());
                        productPriceDetail.setPrice(p.getPrice());
                        productPriceDetail.setNum(p.getNum());
                        productPriceDetail.setSysUserName(sysUser.getUsername());
                        productPriceDetail.setUnit(productServicecModeListDto.getUnit());
                        productPriceDetail.setName(p.getName());
                        productServiceDetailService.insert(productPriceDetail);
                    }
                } else {
                    if (!flag) {
                        ProductServiceDetail productPriceDetail = productServiceDetailService.selectOne(new EntityWrapper<ProductServiceDetail>().eq("id", id).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
                        if (ParamUtil.isNullOrEmptyOrZero(productPriceDetail)) {
                            LeaseException.throwSystemException(LeaseExceEnums.SERVICE_MODE_DETAIL_NOT_EXIST);
                        }
                        productPriceDetail.setId(id);
                        productPriceDetail.setUtime(new Date());
                        productPriceDetail.setServiceModeId(productServicecModeListDto.getId());
                        productPriceDetail.setProductId(productServicecModeListDto.getProductId());
                        productPriceDetail.setServiceType(productServicecModeListDto.getServiceType());
                        productPriceDetail.setServiceTypeId(productServicecModeListDto.getServiceTypeId());
                        productPriceDetail.setCommand(productCommandConfigService.getCommandByConfig(commandConfig, p.getNum()));
                        productPriceDetail.setSysUserId(sysUser.getId());
                        productPriceDetail.setPrice(p.getPrice());
                        productPriceDetail.setNum(p.getNum());
                        productPriceDetail.setName(p.getName());
                        if (productServicecModeListDto.getServiceType().indexOf(ServiceType.FREE.getDesc()) >= 0) {
                            productPriceDetail.setUnit(null);
                        } else {
                            productPriceDetail.setUnit(productServicecModeListDto.getUnit());
                        }
                        productServiceDetailService.updateAllColumnById(productPriceDetail);
                    } else {
                        List<Integer> priceIds = new ArrayList<>();
                        priceIds.add(id);
                        productServiceDetailService.deleteByIds(priceIds);
                    }
                }
            }
        }
    }

    private void publishEvent(NameModifyEvent<Integer> nameModifyEvent) {
        CommonEventPublisherUtils.publishEvent(nameModifyEvent);
    }

    public void addProductServiceMode(SysUser sysUser, ProductServicecModeListDto productServiceModeListDto) {
        ProductCommandConfig commandConfig = productCommandConfigService.selectById(productServiceModeListDto.getServiceTypeId());
        Date now = new Date();
        //添加收费模式数据
        ProductServiceMode productServiceMode = new ProductServiceMode();
        productServiceMode.setCtime(now);
        productServiceMode.setUtime(now);
        String mode = productServiceModeListDto.getServiceMode();
        if (ParamUtil.isNullOrEmptyOrZero(mode)) {
            throw new com.gizwits.boot.exceptions.SystemException(SysExceptionEnum.ILLEGAL_PARAM.getCode(), SysExceptionEnum.ILLEGAL_PARAM.getMessage());
        }

        EntityWrapper<ProductServiceMode> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("sys_user_id", sysUser.getId())
                .eq("is_deleted", 0)
                .eq("name", productServiceModeListDto.getServiceMode());
        if (selectCount(entityWrapper) > 0) {
            LeaseException.throwSystemException(LeaseExceEnums.SERVICE_MODE_NAME_EXITS);
        }

        productServiceMode.setName(mode);
        productServiceMode.setServiceType(productServiceModeListDto.getServiceType());
        productServiceMode.setProductId(productServiceModeListDto.getProductId());
        productServiceMode.setUnit(productServiceModeListDto.getUnit());
        productServiceMode.setServiceTypeId(productServiceModeListDto.getServiceTypeId());
        productServiceMode.setSysUserId(sysUser.getId());
        productServiceMode.setSysUserName(sysUser.getUsername());
        productServiceMode.setCommand(productCommandConfigService.getDeviceModelCommand(commandConfig));
        productServiceMode.setIsFree(commandConfig.getIsFree());
        productServiceMode.setWorkingMode(commandConfig.getWorkingMode());
        productServiceMode.setUnitPrice(productServiceModeListDto.getUnitPrice());

        if (productServiceModeListDto.getServiceType().contains(ServiceType.FREE.getDesc())) {
            productServiceMode.setUnit(" ");
        }

        insert(productServiceMode);

        List<PriceAndNumDto> priceAndNumDtos = productServiceModeListDto.getPriceAndNumDtoList();
        if (!ParamUtil.isNullOrEmptyOrZero(priceAndNumDtos)) {
            //添加收费详情数据
            for (PriceAndNumDto p : priceAndNumDtos) {
                ProductServiceDetail productPriceDetail = new ProductServiceDetail();
                productPriceDetail.setCtime(now);
                productPriceDetail.setUtime(now);
                productPriceDetail.setServiceModeId(productServiceMode.getId());
                productPriceDetail.setProductId(productServiceModeListDto.getProductId());
                productPriceDetail.setServiceType(productServiceModeListDto.getServiceType());
                productPriceDetail.setServiceTypeId(productServiceModeListDto.getServiceTypeId());
                productPriceDetail.setPrice(p.getPrice());
                productPriceDetail.setNum(p.getNum());
                productPriceDetail.setUnit(productServiceModeListDto.getUnit());
                productPriceDetail.setSysUserId(sysUser.getId());
                productPriceDetail.setSysUserName(sysUser.getUsername());
                productPriceDetail.setName(p.getName());
                productPriceDetail.setCommand(productCommandConfigService.getCommandByConfig(commandConfig, p.getNum()));//设置指令
                productServiceDetailService.insert(productPriceDetail);
            }
        }
    }

    public Page<ProductServicecModeListDto> getProductServiceModeListPage(Pageable<ProductServiceListQuerytDto> pageable) {
        Page<ProductServiceMode> page = new Page<>();
//        page.setOrderByField("utime");
        EntityWrapper<ProductServiceMode> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("status", 1)
                .eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        entityWrapper.orderBy("utime", false);
        BeanUtils.copyProperties(pageable, page);
        Page<ProductServiceMode> page1 = selectPage(
                page, QueryResolverUtils.parse(pageable.getQuery(), entityWrapper));
        List<ProductServiceMode> productServiceModes = page1.getRecords();
        Page<ProductServicecModeListDto> result = new Page<>();
        BeanUtils.copyProperties(page1, result);
        List<ProductServicecModeListDto> productServicecModeListDtos = new ArrayList<ProductServicecModeListDto>();

        for (ProductServiceMode psm : productServiceModes) {
            ProductServicecModeListDto productServicecModeListDto = new ProductServicecModeListDto();
            productServicecModeListDto.setId(psm.getId());
            productServicecModeListDto.setServiceMode(psm.getName());
            productServicecModeListDto.setServiceType(psm.getServiceType());
            productServicecModeListDto.setServiceTypeId(psm.getServiceTypeId());
            productServicecModeListDto.setProductId(psm.getProductId());
            productServicecModeListDto.setUnit(psm.getUnit());
            productServicecModeListDto.setUnitPrice(psm.getUnitPrice());
            Product product = productService.selectOne(new EntityWrapper<Product>()
                    .eq("id", psm.getProductId()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
            if (!Objects.isNull(product)) {
                productServicecModeListDto.setProduct(product.getName());
            }
            productServicecModeListDto.setuTime(psm.getUtime());
            productServicecModeListDto.setcTime(psm.getCtime());


            //添加价格和数量
            List<PriceAndNumDto> priceAndNumDtos = productServiceDetailService.
                    getProductPriceDetailByServiceModeId(psm.getId());
            productServicecModeListDto.setPriceAndNumDtoList(priceAndNumDtos);

            // 统计使用该收费模式的设备数
            productServicecModeListDto.setDeviceCount(deviceService.countByServiceId(psm.getId()));
            productServicecModeListDtos.add(productServicecModeListDto);

        }

        result.setRecords(productServicecModeListDtos);
        return result;
    }

    @Override
    public Page<ProductServicecModeListDto> getProductServiceModeListDtoPage(Pageable<ProductServiceListQuerytDto> pageable) {
        SysUser sysUser = sysUserService.getCurrentUserOwner();
        if (Objects.isNull(pageable.getQuery())) {
            pageable.setQuery(new ProductServiceListQuerytDto());
        }
        if (Objects.isNull(pageable.getQuery().getSelfOperating())) {
            pageable.getQuery().setSelfOperating(true);//若不设置，则只查询当前用户创建的收费模式
        }
        if (pageable.getQuery().getSelfOperating()) {
            pageable.getQuery().setAccessableUserIds(Collections.singletonList(sysUser.getId()));
        } else {
            //查看所有上级的收费模式
            List<Integer> userIds = sysUserService.resolveShareDataSysUserIds(sysUser, SysUserShareDataEnum.PRODUCT_SERVICE_MODE);
            if (CollectionUtils.isEmpty(userIds)) {
                return new Page<>();
            }
            pageable.getQuery().setAccessableUserIds(userIds);
        }
        return getProductServiceModeListPage(pageable);
    }

    public ProductServicecModeListDto getProductServiceModeDetail(Integer serviceModeId) {
        ProductServiceMode productServiceMode = productServiceModeDao.selectById(serviceModeId);
        if (!ParamUtil.isNullOrEmptyOrZero(productServiceMode)) {
            ProductServicecModeListDto productServicecModeListDto = new ProductServicecModeListDto();
            productServicecModeListDto.setId(productServiceMode.getId());
            productServicecModeListDto.setServiceMode(productServiceMode.getName());
            productServicecModeListDto.setServiceType(productServiceMode.getServiceType());
            productServicecModeListDto.setServiceTypeId(productServiceMode.getServiceTypeId());
            productServicecModeListDto.setProductId(productServiceMode.getProductId());
            productServicecModeListDto.setUnit(productServiceMode.getUnit());
            productServicecModeListDto.setProduct(productService.selectById(productServiceMode.getProductId()).getName());
            productServicecModeListDto.setuTime(productServiceMode.getUtime());
            productServicecModeListDto.setcTime(productServiceMode.getCtime());
            productServicecModeListDto.setUnitPrice(productServiceMode.getUnitPrice());

            //添加价格和数量
            List<PriceAndNumDto> priceAndNumDtos = productServiceDetailService.
                    getProductPriceDetailByServiceModeId(productServiceMode.getId());
            productServicecModeListDto.setPriceAndNumDtoList(priceAndNumDtos);

            // 统计使用该收费模式的设备数
            productServicecModeListDto.setDeviceCount(deviceService.countByServiceId(productServiceMode.getId()));

            return productServicecModeListDto;
        }

        return null;
    }

    @Override
    public ProductServiceMode getProductServiceMode(Integer serviceModeId) {
        return selectOne(new EntityWrapper<ProductServiceMode>().eq("id", serviceModeId).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
    }
}


