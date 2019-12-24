package com.gizwits.lease.app.web;

import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.lease.device.entity.DevicePlan;
import com.gizwits.lease.device.entity.dto.PlanAddDto;
import com.gizwits.lease.device.entity.dto.PlanUpdateDto;
import com.gizwits.lease.device.service.DevicePlanService;
import com.gizwits.lease.product.dto.AppProductDataPointDto;
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
 * Description:
 * Created by Sunny on 2019/12/24 9:56
 */
@EnableSwagger2
@Api(description = "设备定时计划")
@RestController
@RequestMapping("/app/devicePlan")
public class DevicePlanController extends BaseController {

    @Autowired
    private DevicePlanService planService;

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "设备计划列表", consumes = "application/json")
    @PostMapping("/list")
    public ResponseObject<List<DevicePlan>> list(@RequestBody RequestObject<String> requestObject) {
        return success(planService.list(requestObject.getData()));
    }
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "添加定时计划", consumes = "application/json")
    @PostMapping("/addPlan")
    public ResponseObject<Boolean> addPlan(@RequestBody RequestObject<PlanAddDto> requestObject) {
        return success(planService.addPlan(requestObject.getData()));
    }
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "删除定时计划", consumes = "application/json")
    @PostMapping("/delete")
    public ResponseObject<Boolean> delete(@RequestBody RequestObject<Integer> requestObject) {
        return success(planService.delete(requestObject.getData()));
    }
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "修改定时计划", consumes = "application/json")
    @PostMapping("/update")
    public ResponseObject<Boolean> update(@RequestBody  RequestObject<PlanUpdateDto> requestObject) {
        return success(planService.update(requestObject.getData()));
    }
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "开启，关闭计划", consumes = "application/json")
    @PostMapping("/openOrClosePlan")
    public ResponseObject<Boolean> openOrClosePlan(@RequestBody  RequestObject<Integer> requestObject) {
        return success(planService.openOrClosePlan(requestObject.getData()));
    }
}
