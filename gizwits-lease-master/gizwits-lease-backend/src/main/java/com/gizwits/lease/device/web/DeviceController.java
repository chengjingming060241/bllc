package com.gizwits.lease.device.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.lease.RequestLock;
import com.gizwits.lease.common.perm.dto.AssignDestinationDto;
import com.gizwits.lease.common.version.DefaultVersion;
import com.gizwits.lease.common.version.Version;
import com.gizwits.lease.constant.DeviceStatus;
import com.gizwits.lease.constant.DeviceSweepCodeStatus;
import com.gizwits.lease.device.entity.dto.*;
import com.gizwits.lease.device.service.DeviceAssignService;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.device.service.DeviceToProductServiceModeService;
import com.gizwits.lease.enums.AssignDestinationType;
import com.gizwits.lease.enums.DeviceOriginType;
import com.gizwits.lease.model.DeleteDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * <p>
 * 设备表 前端控制器
 * </p>
 *
 * @author zhl
 * @since 2017-07-11
 */
@RestController
@EnableSwagger2
@Api(description = "设备接口")
@RequestMapping("/device/device")
public class DeviceController extends BaseController {
    protected Logger logger = LoggerFactory.getLogger("DEVICE_LOGGER");
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private DeviceAssignService deviceAssignService;

    @Autowired
    private DeviceToProductServiceModeService deviceToProductServiceModeService;

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "设备列表", notes = "设备列表", consumes = "application/json")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @DefaultVersion(display = {"mac", "sno", "name", "product", "belongOperatorName", "launchArea", "serviceMode", "workStatusDesc", "onlineStatus", "activateStatusDesc"})
    public ResponseObject<Page<DeviceShowDto>> list(@RequestBody @Valid RequestObject<Pageable<DeviceQueryDto>> requestObject) {
        Pageable<DeviceQueryDto> pageable = requestObject.getData();
        if (Objects.isNull(pageable.getQuery())) {
            pageable.setQuery(new DeviceQueryDto());
        }
        Integer creatorId = pageable.getQuery().getOperatorAccountId();
        if (Objects.isNull(creatorId)) {
            creatorId = pageable.getQuery().getCreatorId();
        }
        if (Objects.nonNull(creatorId)) {
            pageable.getQuery().setAccessableOwnerIds(sysUserService.resolveSysUserAllSubAdminIds(sysUserService.selectById(creatorId)));
        } else {
            pageable.getQuery().setAccessableOwnerIds(sysUserService.resolveSysUserAllSubAdminIds(sysUserService.getCurrentUserOwner()));
        }

        //防止前台查询已删除的数据
        pageable.getQuery().setIsDeleted(DeleteStatus.NOT_DELETED.getCode());
        return success(deviceService.listPage(pageable));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "添加", notes = "添加", consumes = "application/json")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @DefaultVersion
    public ResponseObject<String> add(@RequestBody @Valid RequestObject<DeviceAddDto> requestObject) {
        DeviceAddDto deviceAddDto = requestObject.getData();
        return success(deviceService.addDevice(deviceAddDto));
    }

    @Version(uri = "/add", version = "1.1")
    public ResponseObject add2(@RequestBody @Valid RequestObject<DeviceAddDto> requestObject) {
        DeviceAddDto deviceAddDto = requestObject.getData();

        deviceService.addDevice(deviceAddDto);
        return success();
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "详情", consumes = "application/json")
    @PostMapping("/detail")
    @DefaultVersion
    public ResponseObject<DeviceForDetailDto> detail(@RequestBody RequestObject<String> requestObject) {
        return success(deviceService.detail1(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "更新", consumes = "application/json")
    @PostMapping("/update")
    public ResponseObject update(@RequestBody @Valid RequestObject<DeviceForUpdateDto> requestObject) {
        deviceService.update(requestObject.getData());
        return success();
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "发送控制指令", consumes = "application/json")
    @PostMapping("/fire")
    public ResponseObject<Boolean> fire(@RequestBody @Valid RequestObject<DeviceForFireDto> requestObject) {
        DeviceForFireDto dto = requestObject.getData();
        if (ParamUtil.isNullOrEmptyOrZero(requestObject.getData().getAttrs())) {
            return success(deviceService.remoteDeviceControl(dto.getSno(), dto.getName(), dto.getValue()));
        } else {
            return success(deviceService.remoteDeviceControl(dto.getSno(), dto.getAttrs()));
        }
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "批量发送控制指令", consumes = "application/json")
    @PostMapping("/batchFire")
    public ResponseObject<List<String>> batchFire(@RequestBody @Valid RequestObject<DeviceForBatchFireDto> requestObject) {
        DeviceForBatchFireDto dto = requestObject.getData();
        if (!ParamUtil.isNullOrEmptyOrZero(dto.getAttrs())) {
            List<String> failSno = dto.getSnoList().parallelStream()
                    .filter(sno -> !deviceService.remoteDeviceControl(sno, dto.getAttrs()))
                    .collect(Collectors.toList());
            return success(failSno);
        } else {
            return success(new LinkedList<>());
        }
    }


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "获取分配目标", consumes = "application/json")
    @PostMapping("/preAssign")
    @DefaultVersion(display = {"code", "desc"})
    public ResponseObject<List<AssignDestinationDto>> preAssign(@RequestBody RequestObject<Object> requestObject) {
        SysUser currentUser = sysUserService.getCurrentUser();
        if (currentUser.getIsAdmin() == 2){
            return success(Stream.of(AssignDestinationType.AGENT).map(AssignDestinationDto::new).collect(Collectors.toList()));
        }
        return success(deviceAssignService.preAssign());
    }

    @Version(uri = "/preAssign", version = "1.1", display = {"code", "desc"})
    public ResponseObject<List<AssignDestinationDto>> preAssign2(@RequestBody RequestObject<Object> requestObject) {
        return success(Stream.of(AssignDestinationType.AGENT).map(AssignDestinationDto::new).collect(Collectors.toList()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "分配", consumes = "application/json")
    @PostMapping("/assign")
    @RequestLock
    public ResponseObject<Boolean> assign(@RequestBody @Valid RequestObject<DeviceForAssignDto> requestObject) {
        SysUser currentUser = sysUserService.getCurrentUserOwner();
        requestObject.getData().setCurrentUser(currentUser);
        return success(deviceAssignService.assign(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "解绑", consumes = "application/json")
    @PostMapping("/unbind")
    @RequestLock
    public ResponseObject<Boolean> unbind(@RequestBody RequestObject<DeviceForUnbindDto> requestObject) {
        return success(deviceAssignService.unbind(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "删除", notes = "删除", consumes = "application/json")
    @PostMapping("/delete")
    public ResponseObject<String> delete(@RequestBody @Valid RequestObject<List<String>> requestObject) {

        return success(deviceService.deleteDevice(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "mac是否存在", notes = "mac是否存在", consumes = "application/json")
    @PostMapping("/macIsExist")
    public ResponseObject macIsExist(@RequestBody @Valid RequestObject<String> requestObject) {

        return success(deviceService.judgeMacIsExsit(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "展示指令", notes = "展示指令", consumes = "application/json")
    @PostMapping("/show")
    public ResponseObject<List<DeviceProductShowDto>> show(@RequestBody @Valid RequestObject<String> requestObject) {

        return success(deviceService.show(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "解除锁定", notes = "解除锁定", consumes = "application/json")
    @PostMapping("/unlock")
    public ResponseObject unlock(@RequestBody @Valid RequestObject<String> requestObject) {
        deviceService.updateLockFlag(requestObject.getData(), 0, false);
        return success();
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "分配收费模式或投放点", notes = "分配收费模式或投放点", consumes = "application/json")
    @PostMapping("/assignModeOrArea")
    public ResponseObject assignModeOrArea(@RequestBody @Valid RequestObject<DeviceAssignDto> requestObject) {
        deviceService.assingModeOrArea(requestObject.getData());
        return success();
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "为设备添加/修改收费模式", notes = "为设备添加/修改收费模式", consumes = "application/json")
    @PostMapping("/addMoreMode")
    public ResponseObject addMoreMode(@RequestBody @Valid RequestObject<DeviceAssignMoreModeDto> requestObject) {
        deviceToProductServiceModeService.batchInsertOrUpdate(requestObject.getData());
        return success();
    }
}
