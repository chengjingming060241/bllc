package com.gizwits.lease.app.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.lease.app.utils.BrowserUtil;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.model.DeleteDto;
import com.gizwits.lease.work.dto.*;
import com.gizwits.lease.work.service.WorkOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <p>
 * 工单 前端控制器
 * </p>
 *
 * @author Joke
 * @since 2017-10-19
 */
@RestController
@EnableSwagger2
@Api(description = "工单")
@RequestMapping("/app/workOrder")
public class WorkOrderController extends BaseController {

	@Autowired
	private WorkOrderService workOrderService;

	@ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
	@ApiOperation(value = "用户端上报工单", notes = "用户端上报工单", consumes = "application/json")
	@RequestMapping(value = "/appReport", method = RequestMethod.POST)
	public ResponseObject appReport(@RequestParam(name = "files", required = false) List<MultipartFile> files,
			@RequestParam(name = "sno", required = true) String sno,
			@RequestParam(name = "attr", required = true) String attr,
			@RequestParam(name = "description", required = false) String userDescription,
			@RequestParam(name = "userName", required = false) String userName,
			@RequestParam(name = "mobile", required = false) String mobile,
			@RequestParam(name = "address", required = false) String address,
			HttpServletRequest request) {
		WorkOrderAppReportDto dto = new WorkOrderAppReportDto();
		dto.setSno(sno);
		dto.setAttr(attr);
		dto.setUserDescription(userDescription);
		dto.setUserName(userName);
		dto.setMobile(mobile);
		dto.setAddress(address);
		workOrderService.appReport(dto, files, BrowserUtil.getUserBrowserType(request));
		return success();
	}

	@ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
	@ApiOperation(value = "上门时间", notes = "上门时间", consumes = "application/json")
	@RequestMapping(value = "/workTime", method = RequestMethod.POST)
	public ResponseObject workTime(@RequestBody @Valid RequestObject<String> requestObject) {
		List<WorkDateDto> workDateDtoList = workOrderService.getWorkDate(requestObject.getData());
		return success(workDateDtoList);
	}

	@ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
	@ApiOperation(value = "获取可转移的维护人列表", notes = "获取可转移的维护人列表", consumes = "application/json")
	@RequestMapping(value = "/maintainerForTransfer", method = RequestMethod.POST)
	public ResponseObject maintainerForTransfer(@RequestBody @Valid RequestObject<WorkOrderMaintainerListDto> requestObject) {
		List selectors = workOrderService.maintainerList(requestObject.getData());
		return success(selectors);
	}

	@ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
	@ApiOperation(value = "修改工单详情", notes = "修改工单详情", consumes = "application/json")
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ResponseObject edit(@RequestBody @Valid RequestObject<WorkOrderEditDto> requestObject) {
		workOrderService.edit(requestObject.getData(), null);
		return success();
	}

	@ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
	@ApiOperation(value = "维护人员开始处理工单（待处理 -> 处理中）", notes = "维护人员开始处理工单（待处理 -> 处理中）", consumes = "application/json")
	@RequestMapping(value = "/process", method = RequestMethod.POST)
	public ResponseObject process(@RequestBody @Valid RequestObject<String> requestObject) {
		workOrderService.process(requestObject.getData());
		return success();
	}

	@ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
	@ApiOperation(value = "管理端扫码关联设备（处理中状态）", notes = "管理端扫码关联设备（处理中状态）", consumes = "application/json")
	@RequestMapping(value = "/scan", method = RequestMethod.POST)
	public ResponseObject scan(@RequestBody @Valid RequestObject<WorkOrderScanDto> requestObject) {
		workOrderService.scan(requestObject.getData());
		return success();
	}

	@ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
	@ApiOperation(value = "管理端转移工单（待处理、处理中）", notes = "管理端转移工单（待处理、处理中）", consumes = "application/json")
	@RequestMapping(value = "/transfer", method = RequestMethod.POST)
	public ResponseObject transfer(@RequestBody @Valid RequestObject<WorkOrderTransferDto> requestObject) {
		workOrderService.transferMaintainer(requestObject.getData());
		return success();
	}

	@ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
	@ApiOperation(value = "维护人员完成工单（处理中 -> 已完成）", notes = "维护人员完成工单（处理中 -> 已完成）", consumes = "application/json")
	@RequestMapping(value = "/complete", method = RequestMethod.POST)
	public ResponseObject complete(@RequestBody @Valid RequestObject<String> requestObject) {
		workOrderService.complete(requestObject.getData());
		return success();
	}

	@ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
	@ApiOperation(value = "管理端工单列表", notes = "管理端工单列表", consumes = "application/json")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseObject appList(@RequestBody @Valid RequestObject<Pageable<WorkOrderQueryDto>> requestObject) {
		Page<WorkOrderListDto> page = workOrderService.appList(requestObject.getData());
		return success(page);
	}

	@ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
	@ApiOperation(value = "管理端工单详情", notes = "管理端工单详情", consumes = "application/json")
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ResponseObject appDetail(@RequestBody @Valid RequestObject<String> requestObject) {
		WorkOrderDetailDto detail = workOrderService.appDetail(requestObject.getData());
		return success(detail);
	}

	@ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
	@ApiOperation(value = "管理端工单数量统计", notes = "管理端工单数量统计", consumes = "application/json")
	@RequestMapping(value = "/count", method = RequestMethod.POST)
	public ResponseObject appCount(@RequestBody @Valid RequestObject requestObject) {
		WorkOrderCountDto countDto = workOrderService.appCount();
		return success(countDto);
	}
}
