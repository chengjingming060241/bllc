package com.gizwits.lease.app.web;

import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.user.dto.UserFamilyUpdateDto;
import com.gizwits.lease.user.dto.UserRoomUpdateDto;
import com.gizwits.lease.user.service.UserRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * Created by Sunny on 2019/12/24 15:28
 */
@Api(value = "用户房间管理")
@RestController
@RequestMapping("/app/userRoom")
public class UserRoomController extends BaseController {

    @Autowired
    private UserRoomService userRoomService;

    @Autowired
    private DeviceService deviceService;

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "获取当前用户的房间列表", notes = "获取当前用户的房间列表", consumes = "application/json")
    @RequestMapping(value = "/getUserRooms", method = RequestMethod.GET)
    public ResponseObject getUserRooms() {
        return success(userRoomService.getUserRooms());
    }
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "修改用户房间信息", notes = "修改用户房间信息", consumes = "application/json")
    @RequestMapping(value = "/updateUserRoom", method = RequestMethod.POST)
    public ResponseObject updateUserRoom(@RequestBody RequestObject<UserRoomUpdateDto> requestObject) {
        return success(userRoomService.updateUserRoom(requestObject.getData()));
    }
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "获取房间下设备", notes = "获取房间下设备", consumes = "application/json")
    @RequestMapping(value = "/getUserRoomDevices", method = RequestMethod.POST)
    public ResponseObject getUserRoomDevices(@RequestBody RequestObject<Integer> requestObject) {
        return success(deviceService.getUserRoomDevices(requestObject.getData()));
    }
}
