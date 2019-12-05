package com.gizwits.lease.stat.web;

import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserExtService;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.stat.dto.StatDeviceTrendDto;
import com.gizwits.lease.stat.service.StatDeviceTrendService;
import com.gizwits.lease.stat.vo.StatTrendVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 设备趋势统计表 前端控制器
 * </p>
 *
 * @author gagi
 * @since 2017-07-11
 */
@EnableSwagger2
@Api(description = "设备趋势图")
@RestController
@RequestMapping("/stat/statDeviceTrend")
public class StatDeviceTrendController extends BaseController {
    @Autowired
    private StatDeviceTrendService statDeviceTrendService;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserExtService sysUserExtService;

    @Autowired
    private DeviceService deviceService;

    @ApiOperation(value = "获取设备新增趋势图数据（productId填）")
    @RequestMapping(value = "/newTrend", method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    public ResponseObject<List<StatTrendVo>> newTrend(@RequestBody @Valid RequestObject<StatDeviceTrendDto> requestObject) {
        List<StatTrendVo> list = statDeviceTrendService.getNewTrend(requestObject.getData());
        return success(list);
    }

    //活跃趋势
    @ApiOperation(value = "获取设备活跃趋势图数据（productId选填）")
    @RequestMapping(value = "/activeTrend", method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    public ResponseObject<List<StatTrendVo>> activeTrend(@RequestBody @Valid RequestObject<StatDeviceTrendDto> requestObject) {
        List<StatTrendVo> list = statDeviceTrendService.getActiveTrend(requestObject.getData());
        return success(list);
    }
    //设备总趋势
    @ApiOperation(value = "获取设备总数趋势图数据（productId选填）")
    @RequestMapping(value = "/allTotalTrend", method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    public ResponseObject<List<StatTrendVo>> allTotalTrend(@RequestBody @Valid RequestObject<StatDeviceTrendDto> requestObject) {

        return success(statDeviceTrendService.allTotalTrend(requestObject.getData()));
    }

    //设备订单率趋势
    @ApiOperation(value = "获取设备使用率趋势图数据（productId选填）")
    @RequestMapping(value = "/usePercentTrend", method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    public ResponseObject<List<StatTrendVo>> usePercentTrend(@RequestBody @Valid RequestObject<StatDeviceTrendDto> requestObject) {
        SysUser currentUser = sysUserService.getCurrentUserOwner();
        List<Integer> ids = sysUserService.resolveSysUserAllSubIds(currentUser);
        List<StatTrendVo> list = statDeviceTrendService.getUserPercentTrend(requestObject.getData(), currentUser, ids);
        return success(list);
    }

    @ApiOperation("新激活设备趋势")
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @PostMapping("/newActivatedTrend")
    public ResponseObject<List<StatTrendVo>> newActivatedTrend(@RequestBody @Valid RequestObject<StatDeviceTrendDto> requestObject) {
        SysUser currentUser = sysUserService.getCurrentUserOwner();
        List<Integer> ids = sysUserService.resolveSysUserAllSubIds(currentUser);
        List<StatTrendVo> list = statDeviceTrendService.getNewActivatedTrend(requestObject.getData(), currentUser, ids);
        return success(list);
    }

    @ApiOperation("报警设备趋势")
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @PostMapping("/alertDeviceTrend")
    public ResponseObject<List<StatTrendVo>> getAlertDeviceTrend(@RequestBody @Valid RequestObject<StatDeviceTrendDto> requestObject) {
        List<StatTrendVo> list = statDeviceTrendService.getAlertDeviceTrend(requestObject.getData());
        return success(list);
    }

    @ApiOperation("故障设备趋势")
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @PostMapping("/faultDeviceTrend")
    public ResponseObject<List<StatTrendVo>> getFaultDeviceTrend(@RequestBody @Valid RequestObject<StatDeviceTrendDto> requestObject) {
        List<StatTrendVo> list = statDeviceTrendService.getFaultDeviceTrend(requestObject.getData());
        return success(list);
    }
}
