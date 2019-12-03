package com.gizwits.lease.stat.web;

import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.lease.common.version.DefaultVersion;
import com.gizwits.lease.common.version.Version;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.stat.dto.StatDeviceDto;
import com.gizwits.lease.stat.service.StatDeviceOrderWidgetService;
import com.gizwits.lease.stat.vo.StatAlarmWidgetVo;
import com.gizwits.lease.stat.vo.StatDeviceWidgetVo;
import com.gizwits.lease.stat.vo.StatOrderWidgetVo;
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
import java.util.List;

/**
 * <p>
 * 设备订单看板数据统计表 前端控制器
 * </p>
 *
 * @author gagi
 * @since 2017-07-18
 */
@EnableSwagger2
@Api(description = "设备看板，订单看板，告警设备看板")
@RestController
@RequestMapping("/stat/statDeviceOrderWidget")
public class StatDeviceOrderWidgetController extends BaseController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private StatDeviceOrderWidgetService statDeviceOrderWidgetService;

    @Autowired
    private DeviceService deviceService;

    //订单看板
    @ApiOperation(value = "订单看板（productId不填时为全部）")
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    public ResponseObject<StatOrderWidgetVo> orderWidget(@RequestBody @Valid RequestObject<StatDeviceDto> requestObject) {
        SysUser currentUser = sysUserService.getCurrentUserOwner();
        List<Integer> ids = sysUserService.resolveSysUserAllSubIds(currentUser);
        StatOrderWidgetVo statOrderWidgetVo = statDeviceOrderWidgetService.orderWidget(requestObject.getData().getProductId(), currentUser, ids);
        return success(statOrderWidgetVo);
    }

    //设备看板
    @ApiOperation(value = "设备看板（productId不填时为全部）")
    @RequestMapping(value = "/device", method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @DefaultVersion(display = {"totalCount", "newCount", "orderedCount", "faultDeviceCount"})
    public ResponseObject<StatDeviceWidgetVo> deviceWidget(@RequestBody @Valid RequestObject<StatDeviceDto> requestObject) {
//        return getStatDeviceWidgetVoResponseObject(requestObject);
        return success(deviceService.getDeviceWidget(requestObject.getData()));
    }

    @Version(uri = "/device", version = "1.1", display = {"allCount", "onlineRate", "yesterNewCount", "activatedRate"})
    public ResponseObject<StatDeviceWidgetVo> deviceWidget2(@RequestBody @Valid RequestObject<StatDeviceDto> requestObject) {
        return getStatDeviceWidgetVoResponseObject(requestObject);
    }

    private ResponseObject<StatDeviceWidgetVo> getStatDeviceWidgetVoResponseObject(
            @RequestBody @Valid RequestObject<StatDeviceDto> requestObject) {
        SysUser currentUser = sysUserService.getCurrentUserOwner();
        List<Integer> ids = sysUserService.resolveSysUserAllSubAdminIds(currentUser);
        if(ParamUtil.isNullOrEmptyOrZero(requestObject.getData())){
            StatDeviceDto deviceDto = new StatDeviceDto();
            deviceDto.setProductId(0);
            requestObject.setData(deviceDto);
        }
        StatDeviceWidgetVo statDeviceWidgetVo = statDeviceOrderWidgetService.deviceWidget(requestObject.getData().getProductId(), currentUser, ids);
        return success(statDeviceWidgetVo);
    }

    //故障看板
    @ApiOperation(value = "故障看板（productId不填时为全部）")
    @RequestMapping(value = "/alarm", method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    public ResponseObject<StatAlarmWidgetVo> alarmWidget(@RequestBody @Valid RequestObject<StatDeviceDto> requestObject) {
//        SysUser currentUser = sysUserService.getCurrentUserOwner();
//        List<Integer> ids = sysUserService.resolveSysUserAllSubIds(currentUser);
//        StatAlarmWidgetVo statAlarmWidgetVo = statDeviceOrderWidgetService.alarmWidget(requestObject.getData().getProductId(), currentUser, ids);
//        return success(statAlarmWidgetVo);
        return success(deviceService.getAlarmAndFaultWidget(requestObject.getData()));
    }

    @ApiOperation(value = "故障看板（productId不填时为全部）")
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    public ResponseObject test() {
        statDeviceOrderWidgetService.setDataForWidget();
        return success();
    }
}
