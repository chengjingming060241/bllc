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
import com.gizwits.lease.constant.DeviceSweepCodeStatus;
import com.gizwits.lease.device.entity.dto.*;
import com.gizwits.lease.device.service.DeviceAssignService;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.device.service.DeviceStockService;
import com.gizwits.lease.device.service.DeviceToProductServiceModeService;
import com.gizwits.lease.enums.AssignDestinationType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
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
@Api(description = "库存设备接口")
@RequestMapping("/device/deviceStock")
public class DeviceStockController extends BaseController {
    protected Logger logger = LoggerFactory.getLogger("DEVICE_LOGGER");

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private DeviceAssignService deviceAssignService;

    @Autowired
    private DeviceStockService deviceStockService;

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "添加", notes = "添加", consumes = "application/json")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @DefaultVersion
    public ResponseObject<String> add(@RequestBody @Valid RequestObject<DeviceAddDto> requestObject) {
        DeviceAddDto deviceAddDto = requestObject.getData();
        return success(deviceStockService.addDevice(deviceAddDto));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "库存设备列表", notes = "设备列表", consumes = "application/json")
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
        //状态默认为“待扫码”
        if (Objects.isNull(pageable.getQuery().getSweepCodeStatus())){
            pageable.getQuery().setSweepCodeStatus(DeviceSweepCodeStatus.PENDING_CODE.getCode());
        }
        //防止前台查询已删除的数据
        pageable.getQuery().setIsDeleted(DeleteStatus.NOT_DELETED.getCode());
        return success(deviceStockService.listPage(pageable));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "设备扫码进度详情", consumes = "application/json")
    @PostMapping("/detailSweepProgress")
    @DefaultVersion
    public ResponseObject<DeviceForSpeedDetailDto> sweepProgress(@RequestBody RequestObject<String> requestObject) {
        return success(deviceStockService.sweepProgress(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "库存列表", notes = "设备列表", consumes = "application/json")
    @RequestMapping(value = "/stockList", method = RequestMethod.POST)
    public ResponseObject<Page<DeviceShowDto>> stockList(@RequestBody @Valid RequestObject<Pageable<DeviceQueryDto>> requestObject) {
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
        //状态默认为“待出库”
        if (Objects.isNull(pageable.getQuery().getSweepCodeStatus())){
            pageable.getQuery().setSweepCodeStatus(DeviceSweepCodeStatus.To_Be_But_Bf_Stock.getCode());
        }
        //防止前台查询已删除的数据
        pageable.getQuery().setIsDeleted(DeleteStatus.NOT_DELETED.getCode());
        return success(deviceStockService.StockListPage(pageable));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "库存详情", consumes = "application/json")
    @PostMapping("/stockDetails")
    @DefaultVersion
    public ResponseObject<DeviceForStockDetailDto> stockDetails(@RequestBody RequestObject<String> requestObject) {
        return success(deviceStockService.stockDetails(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "库存详情更新", consumes = "application/json")
    @PostMapping("/stockUpdate")
    public ResponseObject<String> stockUpdate(@RequestBody @Valid RequestObject<DeviceForUpdateDto> requestObject) {
        return success( deviceStockService.stockUpdate(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "设备出库", consumes = "application/json")
    @PostMapping("/outOfStock")
    @RequestLock
    public ResponseObject<List<String>> outOfStock(@RequestBody @Valid RequestObject<DeviceForAssignDto> requestObject) {
        SysUser currentUser = sysUserService.getCurrentUserOwner();
        requestObject.getData().setCurrentUser(currentUser);
        return success(deviceAssignService.outOfStock(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "库存、设备列表删除", notes = "删除", consumes = "application/json")
    @PostMapping("/stockDelete")
    public ResponseObject<String> stockDelete(@RequestBody @Valid RequestObject<List<String>> requestObject) {
        return success(deviceStockService.stockDelete(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "入库记录列表", notes = "设备列表", consumes = "application/json")
    @RequestMapping(value = "/putDeviceList", method = RequestMethod.POST)
    public ResponseObject<Page<DeviceShowDto>> putDeviceList(@RequestBody @Valid RequestObject<Pageable<DeviceQueryDto>> requestObject) {
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
        pageable.getQuery().setIsDeletedPut(DeleteStatus.NOT_DELETED.getCode());
        return success(deviceStockService.putListPage(pageable));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "入库记录详情", consumes = "application/json")
    @PostMapping("/putDeviceDetails")
    public ResponseObject<Page<DeviceForSpeedDetailDto>> putDeviceDetails(@RequestBody @Valid RequestObject<Pageable<DeviceQueryDto>> requestObject) {
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
        pageable.getQuery().setIsDeletedPut(DeleteStatus.NOT_DELETED.getCode());
        return success(deviceStockService.putDeviceDetails(pageable));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "入库记录列表删除", notes = "删除", consumes = "application/json")
    @PostMapping("/putDelete")
    public ResponseObject<String> putDelete(@RequestBody @Valid RequestObject<List<String>> requestObject) {
        return success(deviceStockService.putDelete(requestObject.getData()));
    }


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "出库记录列表", notes = "设备列表", consumes = "application/json")
    @RequestMapping(value = "/outDeviceList", method = RequestMethod.POST)
    public ResponseObject<Page<DeviceShowDto>> outDeviceList(@RequestBody @Valid RequestObject<Pageable<DeviceQueryDto>> requestObject) {
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
        pageable.getQuery().setIsDeletedOut(DeleteStatus.NOT_DELETED.getCode());
        return success(deviceStockService.outListPage(pageable));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "出库记录详情", consumes = "application/json")
    @PostMapping("/outDeviceDetails")
    public ResponseObject<Page<DeviceForSpeedDetailDto>> outDeviceDetails(@RequestBody @Valid RequestObject<Pageable<DeviceQueryDto>> requestObject) {
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
        pageable.getQuery().setIsDeletedOut(DeleteStatus.NOT_DELETED.getCode());
        return success(deviceStockService.outDeviceDetails(pageable));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "出库记录列表删除", notes = "删除", consumes = "application/json")
    @PostMapping("/outDelete")
    public ResponseObject<String> outDelete(@RequestBody @Valid RequestObject<List<String>> requestObject) {
        return success(deviceStockService.outDelete(requestObject.getData()));
    }




}
