package com.gizwits.lease.device.web;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.lease.common.perm.dto.AssignDestinationDto;
import com.gizwits.lease.common.version.DefaultVersion;
import com.gizwits.lease.common.version.Version;
import com.gizwits.lease.device.entity.dto.DeviceForAssignDto;
import com.gizwits.lease.device.entity.dto.DeviceForUnbindDto;
import com.gizwits.lease.device.entity.dto.DeviceGroupForAddDto;
import com.gizwits.lease.device.entity.dto.DeviceGroupForDetailDto;
import com.gizwits.lease.device.entity.dto.DeviceGroupForDeviceListQueryDto;
import com.gizwits.lease.device.entity.dto.DeviceGroupForListDto;
import com.gizwits.lease.device.entity.dto.DeviceGroupForQueryDto;
import com.gizwits.lease.device.entity.dto.DeviceGroupForUpdateDto;
import com.gizwits.lease.device.entity.dto.DeviceQueryDto;
import com.gizwits.lease.device.entity.dto.DeviceShowDto;
import com.gizwits.lease.device.service.DeviceAssignService;
import com.gizwits.lease.device.service.DeviceGroupService;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.enums.AssignDestinationType;
import com.gizwits.lease.enums.DeviceOriginType;
import com.gizwits.lease.model.DeleteDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 设备组 前端控制器
 * </p>
 *
 * @author lilh
 * @since 2017-08-15
 */
@Api(description = "设备组")
@RestController
@RequestMapping("/device/deviceGroup")
public class DeviceGroupController extends BaseController {

    @Autowired
    private DeviceGroupService deviceGroupService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceAssignService deviceAssignService;


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "添加", consumes = "application/json")
    @PostMapping("/add")
    public ResponseObject<Boolean> add(@RequestBody @Valid RequestObject<DeviceGroupForAddDto> requestObject) {
        return success(deviceGroupService.add(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "列表", consumes = "application/json")
    @PostMapping("/page")
    public ResponseObject<Page<DeviceGroupForListDto>> page(@RequestBody RequestObject<Pageable<DeviceGroupForQueryDto>> requestObject) {
        return success(deviceGroupService.page(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "详情", consumes = "application/json")
    @PostMapping("/detail")
    public ResponseObject<DeviceGroupForDetailDto> detail(@RequestBody RequestObject<Integer> requestObject) {
        return success(deviceGroupService.detail(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "更新", consumes = "application/json")
    @PostMapping("/update")
    public ResponseObject<Boolean> update(@RequestBody @Valid RequestObject<DeviceGroupForUpdateDto> requestObject) {
        return success(deviceGroupService.update(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "全部设备", consumes = "application/json")
    @PostMapping("/deviceList")
    @DefaultVersion
    public ResponseObject<Page<DeviceShowDto>> deviceList(@RequestBody @Valid RequestObject<Pageable<DeviceGroupForDeviceListQueryDto>> requestObject) {
        Pageable<DeviceGroupForDeviceListQueryDto> pageable = requestObject.getData();
        if (ParamUtil.isNullOrEmptyOrZero(pageable.getQuery())){
            pageable.setQuery(new DeviceGroupForDeviceListQueryDto());
        }
        pageable.getQuery().setIgnoreOrigin(DeviceOriginType.REPORT.getCode());
       return deviceList2(requestObject);
    }

    @Version(uri = "/deviceList",version = "1.1")
    public ResponseObject<Page<DeviceShowDto>> deviceList2(@RequestBody @Valid RequestObject<Pageable<DeviceGroupForDeviceListQueryDto>> requestObject) {
        Pageable<DeviceQueryDto> pageable = new Pageable<>();
//        BeanUtils.copyProperties(requestObject.getData(), pageable, "query");
//        pageable.setQuery(new DeviceQueryDto());
//        //group_id为空的数据，即未分组的设备
//        //pageable.getQuery().setDeviceGroupId(-1);
//        if (Objects.nonNull(requestObject.getData().getQuery())) {
//            pageable.getQuery().setProductId(requestObject.getData().getQuery().getProductId());
//            pageable.getQuery().setIgnoreOrigin(requestObject.getData().getQuery().getIgnoreOrigin());
//        }
//        pageable.getQuery().setExcludeIds(deviceGroupService.resolveDeviceAlreadyInGroup());
//        // pageable.getQuery().setAccessableOwnerIds(sysUserService.resolveAccessableUserIds(sysUserService.getCurrentUser(), false, true));
//        // 分组的目的就是分组后统一分配，所以只列出属于自己的设备，只有属于自己的才能被自己分配
//        pageable.getQuery().setAccessableOwnerIds(Collections.singletonList(sysUserService.getCurrentUserOwner().getId()));
        return success(deviceService.listPage(pageable));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "获取分配目标", consumes = "application/json")
    @PostMapping("/preAssign")
    public ResponseObject<List<AssignDestinationDto>> preAssign(@RequestBody RequestObject<Object> requestObject) {
//        return success(deviceAssignService.preAssign());
        SysUser currentUser = sysUserService.getCurrentUser();
        if (currentUser.getIsAdmin() == 3){
            return success(Stream.of(AssignDestinationType.AGENT).map(AssignDestinationDto::new).collect(Collectors.toList()));
        }
        return success(deviceAssignService.preAssign());
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "分配", consumes = "application/json")
    @PostMapping("/assign")
    public ResponseObject<Boolean> assign(@RequestBody @Valid RequestObject<DeviceForAssignDto> requestObject) {
        return success(deviceAssignService.assign(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "解绑", consumes = "application/json")
    @PostMapping("/unbind")
    public ResponseObject<Boolean> unbind(@RequestBody RequestObject<DeviceForUnbindDto> requestObject) {
        return success(deviceAssignService.unbind(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "删除", consumes = "application/json")
    @PostMapping("/delete")
    public ResponseObject<String> delete(@RequestBody RequestObject<List<Integer>> requestObject) {
        return success(deviceGroupService.delete(requestObject.getData()));
    }
}
