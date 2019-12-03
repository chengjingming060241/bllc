package com.gizwits.lease.device.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.entity.SysUserExt;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.DeviceStock;
import com.gizwits.lease.device.entity.dto.*;
import com.gizwits.lease.device.vo.BatchDevicePageDto;
import com.gizwits.lease.device.vo.BatchDeviceWebSocketVo;
import com.gizwits.lease.device.vo.DevicePageDto;
import com.gizwits.lease.device.vo.DeviceWebSocketVo;
import com.gizwits.lease.manager.dto.OperatorAllotDeviceDto;
import com.gizwits.lease.model.DeviceAddressModel;
import com.gizwits.lease.order.entity.OrderBase;
import com.gizwits.lease.product.entity.Product;
import com.gizwits.lease.stat.dto.StatDeviceDto;
import com.gizwits.lease.stat.vo.StatAlarmWidgetVo;
import com.gizwits.lease.stat.vo.StatDeviceWidgetVo;

import java.util.List;


/**
 * <p>
 * 设备表 服务类
 * </p>
 *
 * @author zhl
 * @since 2017-07-11
 */
public interface DeviceStockService extends IService<DeviceStock> {

    /**
     * 添加设备
     */
    String addDevice(DeviceAddDto deviceAddDto);


    /*库存管理设备扫码进度详情**/
    DeviceForSpeedDetailDto detail2(String id);

    /*库存详情**/
    DeviceForStockDetailDto stockDetails(String id);


    /*库存详情**/
    Page<DeviceForSpeedDetailDto> putDeviceDetails(Pageable<DeviceQueryDto> pageable);

    /**
     * 更新
     */
    String stockUpdate(DeviceForUpdateDto dto);

    /**检查设备码是否已绑定*/
    String DoesItAlreadyExist(String sno, String mac, String sn1, String sn2, String iMEI);

    /**
     * 分页查询列表
     */
    Page<DeviceShowDto> listPage(Pageable<DeviceQueryDto> pageable);

    /**
     * 库存列表
     */
    Page<DeviceShowDto> StockListPage(Pageable<DeviceQueryDto> pageable);

    /**
     * 入库列表
     */
    Page<DeviceShowDto> putListPage(Pageable<DeviceQueryDto> pageable);



    /**
     * 批量删除设备
     */
    String deleteDevice(List<String> snos);

    DeviceStock getDeviceByMac(String mac);

}
