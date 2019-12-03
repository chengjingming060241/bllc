package com.gizwits.lease.device.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.lease.device.entity.UserDevice;
import com.gizwits.lease.device.entity.dto.DeviceSnoDto;
import com.gizwits.lease.device.entity.dto.UserDeviceDto;
import com.gizwits.lease.device.entity.dto.UserDeviceQueryDto;
import com.gizwits.lease.device.service.UserDeviceService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 用户绑定设备表 前端控制器
 * </p>
 *
 * @author yinhui
 * @since 2017-07-12
 */
@RestController
@RequestMapping("/device/userBindDevice")
public class UserDeviceController extends BaseController{

    @Autowired
    private UserDeviceService userDeviceService;

    @ApiImplicitParam(paramType = "header",name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "设备绑定的用户列表")
    @PostMapping(value = "/user/list")
    public ResponseObject<Page<UserDevice>> list(@RequestBody @Valid RequestObject<Pageable<DeviceSnoDto>> requestObject){

        return success(userDeviceService.userList(requestObject.getData()));
    }

    @ApiOperation(value = "查询用户已绑定的设备列表")
    @ApiImplicitParam(paramType = "header",name = Constants.TOKEN_HEADER_NAME)
    @PostMapping(value = "/device/list")
    public ResponseObject<Page<UserDeviceDto>> getUserDeviceList(@RequestBody @Valid RequestObject<Pageable<UserDeviceQueryDto>> requestObject){

        return success(userDeviceService.deviceList(requestObject.getData()));
    }
}
