package com.gizwits.lease.app.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.lease.message.entity.dto.MessageDetailQueryDto;
import com.gizwits.lease.message.entity.dto.SysMessageListDto;
import com.gizwits.lease.message.service.SysMessageService;
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
 * Description:
 * User: yinhui
 * Date: 2017-11-14
 */
@RestController
@EnableSwagger2
@Api(description = "消息接口")
@RequestMapping("/app/message")
public class AppMessageController extends BaseController{

    @Autowired
    private SysMessageService sysMessageService;

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "用户端分页列表", notes = "用户端分页列表", consumes = "application/json")
    @RequestMapping(value = "/user/list", method = RequestMethod.POST)
    public ResponseObject<Page<SysMessageListDto>> list(@RequestBody @Valid RequestObject<Pageable> requestObject) {
        return success(sysMessageService.getMessagePage(requestObject.getData()));
    }


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "详情", notes = "详情", consumes = "application/json")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public ResponseObject detail(@RequestBody @Valid RequestObject<Integer> requestObject) {
        return success(sysMessageService.detail(requestObject.getData()));
    }


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "删除", notes = "删除", consumes = "application/json")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseObject delete(@RequestBody @Valid RequestObject<List<Integer>> requestObject) {
          sysMessageService.delete(requestObject.getData());
        return success();
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "请空", notes = "清空", consumes = "application/json")
    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    public ResponseObject clear(@RequestBody @Valid RequestObject requestObject) {
        sysMessageService.clear();
        return success();
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "未读消息数", notes = "未读消息数", consumes = "application/json")
    @RequestMapping(value = "/count", method = RequestMethod.POST)
    public ResponseObject count(@RequestBody @Valid RequestObject requestObject) {

        return success(sysMessageService.countIsNotRead());
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "管理端消息列表", consumes = "application/json")
    @RequestMapping(value = "/manage/list", method = RequestMethod.POST)
    public ResponseObject<Page<SysMessageListDto>> manageList(@RequestBody @Valid RequestObject<Pageable> requestObject) {
        return success(sysMessageService.getManageMessagePage(requestObject.getData()));
    }

   @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "管理端消息详情", consumes = "application/json")
    @RequestMapping(value = "/manage/detail", method = RequestMethod.POST)
    public ResponseObject<SysMessageListDto> getMessageDetail(@RequestBody @Valid RequestObject<MessageDetailQueryDto> requestObject) {
        return success(sysMessageService.getMessageDetail(requestObject.getData().getId()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "管理端未读消息数", notes = "管理端未读消息数", consumes = "application/json")
    @RequestMapping(value = "/manage/count", method = RequestMethod.POST)
    public ResponseObject countManage(@RequestBody @Valid RequestObject requestObject) {
        return success(sysMessageService.countManageIsNotRead());
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "请空", notes = "清空", consumes = "application/json")
    @RequestMapping(value = "/manage/clear", method = RequestMethod.POST)
    public ResponseObject clearManage(@RequestBody @Valid RequestObject requestObject) {
        sysMessageService.clearManage();
        return success();
    }
}
