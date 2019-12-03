package com.gizwits.lease.message.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.message.entity.MessageTemplate;
import com.gizwits.lease.message.entity.dto.MessageTemplateAddDto;
import com.gizwits.lease.message.entity.dto.MessageTemplateDetailDto;
import com.gizwits.lease.message.service.MessageTemplateService;
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
 * <p>
 * 消息模版表 前端控制器
 * </p>
 *
 * @author yinhui
 * @since 2018-01-03
 */
@RestController
@EnableSwagger2
@RequestMapping("/message/messageTemplate")
@Api(description = "自定义消息模版接口")
public class MessageTemplateController extends BaseController {

    @Autowired
    private MessageTemplateService messageTemplateService;

    @Autowired
    private DeviceService deviceService;

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "添加消息模版", notes = "添加消息模版", consumes = "application/json")
    @PostMapping(value = "/add")
    public ResponseObject add(@RequestBody @Valid RequestObject<MessageTemplateAddDto> requestObject) {

        return success(messageTemplateService.add(requestObject.getData()));
    }


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "消息模版列表", notes = "消息模版列表", consumes = "application/json")
    @PostMapping(value = "/list")
    public ResponseObject<Page<MessageTemplateDetailDto>> list(@RequestBody @Valid RequestObject<Pageable> requestObject) {

        return success(messageTemplateService.list(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "更新消息模版", notes = "更新消息模版", consumes = "application/json")
    @PostMapping(value = "/update")
    public ResponseObject update(@RequestBody @Valid RequestObject<MessageTemplateAddDto> requestObject) {

        return success(messageTemplateService.update(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "消息模版详情", notes = "消息模版详情", consumes = "application/json")
    @PostMapping(value = "/detail")
    public ResponseObject<MessageTemplateDetailDto> detail(@RequestBody @Valid RequestObject<Integer> requestObject) {

        return success(messageTemplateService.detail(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除消息模版", notes = "删除消息模版", consumes = "application/json")
    public ResponseObject delete(@RequestBody @Valid RequestObject<List<Integer>> requestObject) {
        String str = messageTemplateService.delete(requestObject.getData());
        return success(str);
    }

    @PostMapping(value = "/test")
    @ApiOperation(value = "测试", notes = "测试", consumes = "application/json")
    public ResponseObject test() {//插入系统消息
        Device device = deviceService.getDeviceInfoBySno("04234925260057981635");
        MessageTemplate template = messageTemplateService.selectOne(new EntityWrapper<MessageTemplate>().eq("id", 1).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
        messageTemplateService.sendSysMessage(device, template);
        return success();
    }
}
