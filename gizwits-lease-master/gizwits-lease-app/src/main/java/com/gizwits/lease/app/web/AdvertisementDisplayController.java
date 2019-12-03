package com.gizwits.lease.app.web;

import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.lease.message.entity.AdvertisementDisplay;
import com.gizwits.lease.message.service.AdvertisementDisplayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;

/**
 * <p>
 * 广告展示表 前端控制器
 * </p>
 *
 * @author yinhui
 * @since 2018-01-03
 */
@RestController
@Api(description = "广告展示接口")
@EnableSwagger2
@RequestMapping("/app/message/advertisementDisplay")
public class AdvertisementDisplayController extends BaseController {

    @Autowired
    private AdvertisementDisplayService advertisementDisplayService;

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "广告详情", notes = "广告详情, appKey传入企业id", consumes = "application/json")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public ResponseObject detail(@RequestBody @Valid RequestObject requestObject) {
        return success(advertisementDisplayService.listForManager(requestObject.getAppKey()));
    }
}
