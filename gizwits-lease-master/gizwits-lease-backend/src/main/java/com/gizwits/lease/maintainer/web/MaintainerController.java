package com.gizwits.lease.maintainer.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.lease.device.entity.dto.DeviceAlarmQueryDto;
import com.gizwits.lease.maintainer.dto.MaintainerAddDto;
import com.gizwits.lease.maintainer.dto.MaintainerQueryDto;
import com.gizwits.lease.maintainer.service.MaintainerService;
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

@RestController
@EnableSwagger2
@Api(description = "维护人员")
@RequestMapping("/maintainer")
public class MaintainerController extends BaseController {

	@Autowired
	private MaintainerService maintainerService;

	@ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
	@ApiOperation(value = "添加", notes = "添加", consumes = "application/son")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseObject add(@RequestBody @Valid RequestObject<MaintainerAddDto> requestObject) {
		maintainerService.add(requestObject.getData());
		return success();
	}

	@ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
	@ApiOperation(value = "删除", notes = "删除", consumes = "application/son")
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public ResponseObject del(@RequestBody @Valid RequestObject<List<Integer>> requestObject) {
		maintainerService.del(requestObject.getData());
		return success();
	}

	@ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
	@ApiOperation(value = "详情", notes = "详情", consumes = "application/son")
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ResponseObject detail(@RequestBody @Valid RequestObject<Integer> requestObject) {
		return success(maintainerService.detail(requestObject.getData()));
	}

	@ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
	@ApiOperation(value = "分页列表", notes = "分页列表", consumes = "application/son")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseObject<Page> list(@RequestBody @Valid RequestObject<Pageable<MaintainerQueryDto>> requestObject) {
		return success(maintainerService.list(requestObject.getData()));
	}

}
