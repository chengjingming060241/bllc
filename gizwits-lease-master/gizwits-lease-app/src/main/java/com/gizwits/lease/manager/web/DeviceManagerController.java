package com.gizwits.lease.manager.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.lease.device.entity.dto.*;
import com.gizwits.lease.device.service.DeviceAlarmService;
import com.gizwits.lease.device.service.DeviceLaunchAreaService;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.device.vo.DeviceAlarmListVo;
import com.gizwits.lease.device.vo.DeviceLaunchAreaCountVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by yinhui on 2017/9/6.
 */
@RestController
@EnableSwagger2
@Api(description = "艾斯沃管理端接口")
@RequestMapping(value = "/app/device/manager")
public class DeviceManagerController extends BaseController {

    @Autowired
    private DeviceLaunchAreaService deviceLaunchAreaService;

    @Autowired
    private DeviceAlarmService deviceAlarmService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SysUserService sysUserService;

    @ApiImplicitParam(paramType = "header",name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "投放点列表",notes = "投放点列表",consumes = "application/json")
    @PostMapping("/launchList")
    public ResponseObject<List<DeviceLaunchAreaCountVo>> launchList(){
        return success(deviceLaunchAreaService.areaListManager());
    }

    @ApiImplicitParam(paramType = "header",name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "故障列表",notes = "故障列表",consumes = "application/json")
    @PostMapping("/alarmList")
    public ResponseObject<Page<DeviceAlarmListVo>> alarmList(@RequestBody @Valid
		    RequestObject<Pageable<String>> requestObject){

        return success(deviceAlarmService.deviceAlarmListManager(requestObject.getData()));
    }

    @ApiOperation(value = "管理端查询设备列表", consumes = "application/json")
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @PostMapping(value = "/device")
    public ResponseObject<Page<DeviceShowDto>> device(@RequestBody @Valid
		    RequestObject<Pageable<DeviceQueryDto>> requestObject) {
        SysUser sysUser = sysUserService.getCurrentUser();
        return success(deviceService.getListForManage(sysUser, requestObject.getData()));
    }

    @ApiOperation(value = "管理端查询设备总数", consumes = "application/json")
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @PostMapping(value = "/device/count")
    public ResponseObject getDeviceCount(@RequestBody @Valid RequestObject requestObject) {
        SysUser sysUser = sysUserService.getCurrentUser();
        return success(deviceService.getDeviceCountForManage(sysUser));
    }

    @ApiOperation(value = "设备详情", consumes = "application/json")
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @PostMapping(value = "/detail")
    public ResponseObject<ManageDeviceDetailDto> getDeviceDetail(@RequestBody @Valid
		    RequestObject<ManageDeviceQueryDto> requestObject) {
        return success(deviceService.getDeviceDetail(requestObject.getData().getSno()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "故障详情", notes = "故障详情", consumes = "application/json")
    @PostMapping(value = "/alarmDetail")
    public ResponseObject<DeviceAlarmDetailDto> alarmDetail(@RequestBody @Valid RequestObject<Integer> requestObject) {

        return success(deviceAlarmService.getDeviceAlramInfoById(requestObject.getData()));
    }


}
