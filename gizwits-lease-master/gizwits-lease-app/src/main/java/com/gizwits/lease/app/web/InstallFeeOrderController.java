package com.gizwits.lease.app.web;

import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.lease.constant.OrderStatus;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.install.dto.InstallFeeOrderAddDto;
import com.gizwits.lease.install.dto.InstallFeeOrderQueryDto;
import com.gizwits.lease.install.dto.InstallFeeRuleQueryDto;
import com.gizwits.lease.install.entity.InstallFeeOrder;
import com.gizwits.lease.install.entity.InstallFeeRule;
import com.gizwits.lease.install.service.InstallFeeOrderService;
import com.gizwits.lease.install.service.InstallFeeRuleService;
import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 初装费订单 前端控制器
 * </p>
 *
 * @author Joke
 * @since 2018-04-16
 */
@RestController
@EnableSwagger2
@Api(description = "初装费订单")
@RequestMapping("app/install/installFeeOrder")
public class InstallFeeOrderController extends BaseController{

	@Autowired
	private InstallFeeOrderService installFeeOrderService;
	@Autowired
	private InstallFeeRuleService installFeeRuleService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private UserService userService;

	@ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
	@ApiOperation(value = "添加", notes = "添加", consumes = "application/son")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseObject add(@RequestBody @Valid RequestObject<InstallFeeOrderAddDto> requestObject) {
		User currentUser = userService.getCurrentUser();
		requestObject.getData().setUser(currentUser);
		return success(installFeeOrderService.add(requestObject.getData()));
	}

	@ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
	@ApiOperation(value = "详情", notes = "列表", consumes = "application/son")
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ResponseObject detail(@RequestBody @Valid RequestObject<String> requestObject) {
		return success(installFeeOrderService.detail(requestObject.getData()));
	}

	@ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
	@ApiOperation(value = "列表", notes = "列表", consumes = "application/son")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseObject list(@RequestBody @Valid RequestObject<Pageable<InstallFeeOrderQueryDto>> requestObject) {
		User currentUser = userService.getCurrentUser();
		if(requestObject.getData().getQuery() == null)requestObject.getData().setQuery(new InstallFeeOrderQueryDto());
		requestObject.getData().getQuery().setUserId(currentUser.getId());
		return success(installFeeOrderService.list(requestObject.getData()));
	}

	@ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
	@ApiOperation(value = "检查是否需要支付初装费", notes = "检查是否需要支付初装费", consumes = "application/son")
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public ResponseObject check(@RequestBody @Valid RequestObject<String> requestObject) {
		InstallFeeOrder order = installFeeOrderService.getOrderBySnoAndStatus(requestObject.getData(),OrderStatus.PAYED);
		if(order != null) return success();
		Device device = deviceService.selectById(requestObject.getData());
		return success(installFeeRuleService.getRuleByDevice(device));
	}
}
