package com.gizwits.lease.device.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.entity.SysUserExt;
import com.gizwits.lease.constant.DeviceStatus;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.dto.*;
import com.gizwits.lease.device.vo.BatchDevicePageDto;
import com.gizwits.lease.device.vo.BatchDeviceWebSocketVo;

import com.gizwits.lease.device.entity.dto.DeviceWithLaunchArea;
import com.gizwits.lease.device.entity.dto.ListDeviceForFireDto;
import com.gizwits.lease.device.entity.dto.MJDeviceDetailDto;

import com.gizwits.lease.device.vo.BatchDevicePageDto;
import com.gizwits.lease.device.vo.BatchDeviceWebSocketVo;

import com.gizwits.lease.device.vo.DevicePageDto;
import com.gizwits.lease.device.vo.DeviceWebSocketVo;
import com.gizwits.lease.manager.dto.OperatorAllotDeviceDto;
import com.gizwits.lease.model.DeleteDto;
import com.gizwits.lease.model.DeviceAddressModel;
import com.gizwits.lease.order.entity.OrderBase;
import com.gizwits.lease.product.entity.Product;
import com.gizwits.lease.stat.dto.StatDeviceDto;
import com.gizwits.lease.stat.dto.StatDeviceTrendDto;
import com.gizwits.lease.stat.vo.StatAlarmWidgetVo;
import com.gizwits.lease.stat.vo.StatDeviceWidgetVo;
import com.gizwits.lease.stat.vo.StatLocationVo;
import com.gizwits.lease.stat.vo.StatTrendVo;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 设备表 服务类
 * </p>
 *
 * @author zhl
 * @since 2017-07-11
 */
public interface DeviceService extends IService<Device> {

    /**
     * 根据设备ID查询设备归属的微信运营信息
     */
    SysUserExt getWxConfigByDeviceId(String deviceId);

    /**
     * 检查设备是否投入运营
     */
    boolean checkDeviceIsInOperator(String deviceId);

    /**
     * 获取设备直接运营商的SysUserid
     */
    Integer getDeviceOperatorSysuserid(String deviceId);

    /**
     * 统计一个产品所拥有的设备数
     */
    Integer countDeviceByProductId(Integer productId);

    Integer countLeaseDeviceByProductId(Integer productId);

    /**
     * 通过设备序列号找到产品id
     */
    List<Integer> getProductIdByDeviceSno(List<String> snos);

    /**
     * 通过设备序列号查询投放点id
     */
    List<Integer> getDeviceLaunchAreaIdIdByDeviceSno(List<String> snos);

    /**
     * 统计一个投放点的设备数
     */
    Integer countDeviceByDeviceLaunchAreaId(Integer deviceLaunchAreaId);

    /**
     * 根据Mac和ProductKey获取设备信息
     */
    Device getDeviceByMacAndPk(String mac, String productKey);

    Device insertFromReport(String mac, String productKey);

    /**
     * 从机智云获取坐标
     */
    DeviceAddressModel getDeviceAddressByGizwitsAPI(String productKey, String did, String mac);


    /**
     * 根据设备上报数据点调用高德接口获取地址及经纬度
     * @param productKey
     * @param mac
     * @param networkType 通信类型
     * @return
     */
    DeviceAddressModel getDeviceAddressByGD(String productKey, String mac, String networkType);

    /**
     * 更新设备的在线状态
     */
    boolean updateDeviceOnOffLineStatus(String mac, String productKey, String did, boolean isOnline);


    /**
     * 设备控制
     */
    boolean remoteDeviceControl(String deviceId, String name, Object value);

    boolean remoteDeviceControl(String deviceId, JSONObject attrs);

    /**
     * 根据订单内容,下发指令
     */
    boolean remoteDeviceControlByOrder(OrderBase orderBase);

    /**
     * 获取设备的实时数据点状态
     */
    String getDeviceNowTimeStatus(String deviceId);

    /**
     * 下发设备状态查询指令
     * @param deviceId
     * @param username
     * @return
     */
    boolean sendDeviceStatusQueryCommand(String deviceId, String username);


    /**
     * 通过设备sno查询设备信息
     */
    Device getDeviceInfoBySno(String deviceSno);

    /**
     * 通过产品id查询设备序列号
     */
    List<String> getSnosByProductId(List<Integer> productId);

    /**
     * 统计一个收费模式的设备数
     */
    Integer countByServiceId(Integer serviceId);

    /**
     * 根据产品id查询投放点id
     */
    List<Integer> getDeviceLaunchAreaIdByProductId(Integer productId);

    /**
     * 添加设备
     */
    String addDevice(DeviceAddDto deviceAddDto);

    Device createQrcodeAndAuthDevice(Device device, Product product, SysUser currentUser, DeviceQrcodeService.QrcodeListener qrcodeListener);

    String getSno();

    /**
     * 判断设备序列号是否存在
     */
    boolean judgeMacIsExsit(String mac);


    DeviceForDetailDto detail1(String id);

    /**
     * 设备详情
     */
    DeviceForDetailDto detail(String id);

    /**
     * 管理端查询设备详情
     */
    ManageDeviceDetailDto getDeviceDetail(String sno);

    /**
     * 更新
     */
    boolean update(DeviceForUpdateDto dto);


    /**检查设备码是否已绑定*/
    String DoesItAlreadyExist(String sno , String mac, String sn1 , String sn2, String iMEI);

    /**
     * 分页查询列表
     */
    Page<DeviceShowDto> listPage(Pageable<DeviceQueryDto> pageable);


    Page<DeviceShowDto> listPage2(Pageable<DeviceQueryDto> pageable);

    /**
     * 管理端查询设备列表
     */
    Page<DeviceShowDto> getListForManage(SysUser sysUser, Pageable<DeviceQueryDto> pageable);

    /**
     * 管理端查询设备总数
     */
    int getDeviceCountForManage(SysUser sysUser);

    Page<DeviceShowDto> currentListPage(Pageable<DeviceQueryDto> pageable, Integer type);

    String resolveOwner(Integer ownerId);


    /**
     * 批量删除设备
     */
    String deleteDevice(List<String> snos);



    List<Device> listDeviceByOperatorId(List<Integer> operatorIds);


    DeviceWebSocketVo getInfoForControlDevice(DevicePageDto data);

    void updateDeviceLocation(AppUpdateDeviceLocationDto deviceLocationDto);

    /**
     * 设备授权结果
     *
     * @return 返回0时成功，其他都是失败
     */
    int deviceAuthResult(Device device, SysUserExt sysUserExt);

    BatchDeviceWebSocketVo getInfoForControlBatchDevice(BatchDevicePageDto data);

    /**
     * 设备组中的设备
     */
    List<DeviceShowDto> getDeviceByGroupId(Integer groupId);

    /*
     * 根据openid获取使用中的订单含有的sno
     * @param openid
     * @return
     */
    String getSnoByOpenid(String openid);

    void checkDeviceIsRenting(String sno,boolean canRent);

    Boolean checkDeviceIfUsedByOpenid(Device device, String openId);

    DeviceWebSocketVo getInfoForControlDevice2(DevicePageDto data);

    /**
     * 设备分配运营商
     */
    List<String> deviceAllotOperator(OperatorAllotDeviceDto operatorAllotDeviceDto);

    /**
     * 判断设备是否分配
     */
    boolean judgeSnoIsAllot(Device device);

    /**
     * 麻将机设备详情
     */
    MJDeviceDetailDto deviceDetailForMahjong(String sno);

    List<String> deviceDeleteLaunchArea(DeviceWithLaunchArea deviceWithLaunchArea);

    /***
     * 投放点解除设备
     * @param deviceWithLaunchArea
     * @return
     */
    List<String> deviceWithLaunchArea(DeviceWithLaunchArea deviceWithLaunchArea);

    List<String> getFireFailSnos(ListDeviceForFireDto dto);

    void sendCallOutToManage(Device device);

    Device locationDeviceAddress(String productKey, String mac);

    Device getDeviceByQrcode(String qrcode);

    DeviceForDetailDto detailForApp(String id);

    Device getDeviceByMac(String mac);

    Device selectById(String sno);

    List<DeviceProductShowDto> show(String sno);

    void updateLockFlag(String sno, Integer abnormalTimes, Boolean lock);

    void assingModeOrArea(DeviceAssignDto deviceAssignDto);


    //Sunny ADD//
    /***
     * 设备看板-----设备总数，昨日新增数，设备在线率，设备活跃率
     * @param statDeviceDto
     * @return
     */
    StatDeviceWidgetVo getDeviceWidget(StatDeviceDto statDeviceDto);
    /***
     * 故障报警看板-----报警设备数，报警率，故障设备数，故障率
     * @param statDeviceDto
     * @return
     */
    StatAlarmWidgetVo getAlarmAndFaultWidget(StatDeviceDto statDeviceDto);
    /***
     *   设备地区分布，排行榜--省份
     * @param statDeviceDto
     * @return
     */
    List<StatLocationVo> ditributionByProvince(StatDeviceDto statDeviceDto);

    /***
     *   设备地区分布,排行榜--城市
     * @param statDeviceDto
     * @return
     */
    List<StatLocationVo> ditributionByCity(StatDeviceDto statDeviceDto);

    //Sunny END//
}
