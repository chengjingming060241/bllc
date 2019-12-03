package com.gizwits.lease.device.web;

import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.lease.device.entity.dto.DeviceForDetailDto;
import com.gizwits.lease.device.service.DeviceExtWeatherService;
import com.gizwits.lease.device.vo.DeviceWeatherVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 * 设备-天气拓展表 前端控制器
 * </p>
 *
 * @author zxhuang
 * @since 2018-02-02
 */
@RestController
@EnableSwagger2
@Api(description = "设备拓展天气信息")
@RequestMapping("/device/weather")
public class DeviceExtWeatherController extends BaseController {

    @Autowired
    private DeviceExtWeatherService deviceExtWeatherService;


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "详情", consumes = "application/json")
    @PostMapping("/detail")
    public ResponseObject<DeviceWeatherVo> detail(@RequestBody RequestObject<String> requestObject) {
        return success(deviceExtWeatherService.detail(requestObject.getData()));
    }
	
}
