package com.gizwits.lease.stat.web;

import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.lease.stat.dto.StatOrderAnalysisDto;
import com.gizwits.lease.stat.service.StatDeviceOrderWidgetService;
import com.gizwits.lease.stat.service.StatOrderService;
import com.gizwits.lease.stat.service.StatUserTrendService;
import com.gizwits.lease.stat.service.StatUserWidgetService;
import com.gizwits.lease.stat.vo.StatOrderAnalysisVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单分析统计表 前端控制器
 * </p>
 *
 * @author gagi
 * @since 2017-07-11
 */
@EnableSwagger2
@Api(description = "订单分析图")
@RestController
@RequestMapping("/stat/statOrder")
public class StatOrderController extends BaseController {
    @Autowired
    private StatOrderService statOrderService;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private StatUserWidgetService statUserWidgetService;

    @Autowired
    StatUserTrendService statUserTrendService;

    @Autowired
    private StatDeviceOrderWidgetService statDeviceOrderWidgetService;

    @ApiOperation(value = "获取折柱图接口（包含金额和数量和增长率,productId必填）")
    @RequestMapping(value = "/analysis", method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    public ResponseObject<List<StatOrderAnalysisVo>> orderAnalysis(@RequestBody @Valid RequestObject<StatOrderAnalysisDto> requestObject) {
        SysUser currentUser = sysUserService.getCurrentUserOwner();
        List<Integer> ids = sysUserService.resolveSysUserAllSubIds(currentUser);
        List<StatOrderAnalysisVo> statOrderAnalysisDtoList = statOrderService.getOrderAnalysis(requestObject.getData(), currentUser, ids);
        return success(statOrderAnalysisDtoList);
    }


    @ApiOperation(value = "测试订单分析")
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    public ResponseObject test() {
        statOrderService.setDataForStatOrder();
        return success();
    }


    @ApiOperation(value = "测试设备订单看板")
    @RequestMapping(value = "/test2", method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    public ResponseObject  test2() {
    statDeviceOrderWidgetService.setDataForWidget();
        return success();
    }

    @ApiOperation(value = "测试用户看板")
    @RequestMapping(value = "/test3", method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    public ResponseObject  test3() {
       statUserWidgetService.setDataForWidget(new Date());
        return success();
    }

    @ApiOperation(value = "测试用户趋势")
    @RequestMapping(value = "/test4", method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    public ResponseObject  test4() {
        statUserTrendService.setDataForStatUserTrend(new Date());
        return success();
    }
}
