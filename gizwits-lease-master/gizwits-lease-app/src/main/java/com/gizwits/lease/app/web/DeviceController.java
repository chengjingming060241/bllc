package com.gizwits.lease.app.web;

import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.lease.common.version.DefaultVersion;
import com.gizwits.lease.common.version.Version;
import com.gizwits.lease.device.entity.dto.*;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.device.service.UserDeviceService;
import com.gizwits.lease.device.vo.BatchDevicePageDto;
import com.gizwits.lease.device.vo.BatchDeviceWebSocketVo;
import com.gizwits.lease.device.vo.DevicePageDto;
import com.gizwits.lease.device.vo.DeviceWebSocketVo;
import com.gizwits.lease.product.dto.AppProductDataPointDto;
import com.gizwits.lease.product.entity.ProductDataPoint;
import com.gizwits.lease.product.service.ProductDataPointService;
import com.gizwits.lease.product.service.ProductService;
import com.gizwits.lease.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * Created by GaGi on 2017/8/4.
 */
@EnableSwagger2
@Api(description = "提供设备控制接口")
@RestController
@RequestMapping("/app/device")
public class DeviceController extends BaseController {
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDataPointService productDataPointService;

    @Autowired
    private UserDeviceService userDeviceService;

    @Autowired
    private UserService userService;

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "发送控制指令", consumes = "application/json")
    @PostMapping("/fire")
    public ResponseObject<Boolean> fire(@RequestBody @Valid RequestObject<DeviceForFireDto> requestObject) {
        DeviceForFireDto dto = requestObject.getData();
        if (ParamUtil.isNullOrEmptyOrZero(requestObject.getData().getAttrs())) {
            return success(deviceService.remoteDeviceControl(dto.getSno(), dto.getName(), dto.getValue()));
        } else {
            return success(deviceService.remoteDeviceControl(dto.getSno(), dto.getAttrs()));
        }
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "获取控制指令", consumes = "application/json")
    @PostMapping("/get/dataPoint")
    public ResponseObject<Boolean> getDataPoint(@RequestBody @Valid RequestObject<AppProductDataPointDto> requestObject) {
        return success();
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "刷新页面获取设备详情，userToken,productKey等信息", consumes = "application/json")
    @PostMapping("/intoDevicePage")
    @DefaultVersion()
    public ResponseObject<DeviceWebSocketVo> intoDevicePage(@RequestBody @Valid RequestObject<DevicePageDto> requestObject) {
        DeviceWebSocketVo data = deviceService.getInfoForControlDevice(requestObject.getData());
        return success(data);
    }

    @Version(uri = "/intoDevicePage", version = "1.1")
    public ResponseObject<DeviceWebSocketVo> intoDevicePage2(@RequestBody @Valid RequestObject<DevicePageDto> requestObject) {
        DeviceWebSocketVo data = deviceService.getInfoForControlDevice2(requestObject.getData());
        return success(data);
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "刷新页面获取多个设备详情，userToken,productKey等信息", consumes = "application/json")
    @PostMapping("/intoBatchDevicePage")
    public @ResponseBody
    ResponseObject<BatchDeviceWebSocketVo> intoBatchDevicePage(@RequestBody @Valid RequestObject<BatchDevicePageDto> requestObject) {
        BatchDeviceWebSocketVo data = deviceService.getInfoForControlBatchDevice(requestObject.getData());
        return success(data);
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "详情", consumes = "application/json")
    @PostMapping("/detail")
    public ResponseObject<DeviceForDetailDto> detail(@RequestBody RequestObject<String> requestObject) {
        return success(deviceService.detailForApp(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "获取产品的数据点", consumes = "application/json")
    @PostMapping("/datapoint")
    public ResponseObject<List<ProductDataPoint>> datapoint(@RequestBody RequestObject<String> requestObject) {
        return success(productDataPointService.getProdcutAllDataPoint(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "绑定设备", consumes = "application/json")
    @PostMapping("/bindDevice")
    public ResponseObject bindDevice(@RequestBody RequestObject<UserBindDeviceDto> requestObject) {

        return success(userDeviceService.add(requestObject.getData()));
    }


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "解绑设备", consumes = "application/json")
    @PostMapping("/unBinddevice")
    public ResponseObject unBindDevice(@RequestBody RequestObject<UserBindDeviceDto> requestObject) {

        return success(userDeviceService.deleteBind(requestObject.getData()));
    }


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "更新设备经纬度信息", consumes = "application/json")
    @PostMapping("/update/device")
    public ResponseObject update(@RequestBody RequestObject<AppUpdateDeviceLocationDto> requestObject) {
        deviceService.updateDeviceLocation(requestObject.getData());
        return success();
    }

   /* @ApiOperation(value = "查询用户已绑定的设备列表")
    @ApiImplicitParam(paramType = "header",name = Constants.TOKEN_HEADER_NAME)
    @PostMapping(value = "/device/list")
    public ResponseObject<Page<UserDeviceDto>> getUserDeviceList(@RequestBody @Valid RequestObject<Pageable<UserDeviceQueryDto>> requestObject){
        User user = userService.getCurrentUser();
        Pageable<UserDeviceQueryDto> pageable = requestObject.getData();
        if (ParamUtil.isNullOrEmptyOrZero(pageable.getQuery())){
            UserDeviceQueryDto deviceQueryDto = new UserDeviceQueryDto();
            deviceQueryDto.setUserId(user.getId());
            pageable.setQuery(deviceQueryDto);
        }
        return success(userBindDeviceService.getUserDeviceList(requestObject.getData()));
    }*/
}
