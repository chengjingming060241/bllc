package com.gizwits.lease.device.web;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysRoleService;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.lease.device.entity.DeviceLaunchArea;
import com.gizwits.lease.device.entity.dto.DeviceLaunchAreaListDto;
import com.gizwits.lease.device.entity.dto.DeviceLaunchAreaQueryDto;
import com.gizwits.lease.device.service.DeviceLaunchAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 设备投放点表 前端控制器
 * </p>
 *
 * @author yinhui
 * @since 2017-07-12
 */
@RestController
@EnableSwagger2
@Api(description = "设备仓库")
@RequestMapping("/device/deviceLaunchArea")
public class DeviceLaunchAreaController extends BaseController{
    protected final static Logger logger = LoggerFactory.getLogger("DEVICE_LOGGER");

    @Autowired
    private DeviceLaunchAreaService deviceLaunchAreaService;

    @Autowired
    private SysUserService sysUserService;


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "分页列表", notes = " 分页列表", consumes = "application/json")
    @PostMapping(value = "/list")
    public ResponseObject<Page<DeviceLaunchAreaListDto>> list(@RequestBody @Valid RequestObject<Pageable<DeviceLaunchAreaQueryDto>> requestObject) {
        Pageable<DeviceLaunchAreaQueryDto> pageable = requestObject.getData();
        SysUser userOwner = sysUserService.getCurrentUserOwner();
        if (Objects.isNull(pageable.getQuery())) {
            pageable.setQuery(new DeviceLaunchAreaQueryDto());
        }

        List<Integer> userGroup = sysUserService.ResolveAccessibleByOwnerAndSubordinateUserIds(userOwner);
        if (pageable.getQuery().getSelfOperating()) {
            pageable.getQuery().setAccessableUserIds(userGroup);
        } else {
            //若没有子级用户,则直接返回
            List<Integer> userIds = sysUserService.resolveSysUserAllSubIds(userOwner);
            userIds.removeAll(userGroup);
            if (CollectionUtils.isEmpty(userIds)) {
                return success(new Page<>());
            }
            pageable.getQuery().setAccessableUserIds(userIds);
        }
        logger.info("sys_user_id=" + pageable.getQuery().getAccessableUserIds() + ",查询条件：投放点名称:" + requestObject.getData());
        Page<DeviceLaunchAreaListDto> result = deviceLaunchAreaService.getDeviceLaunchAreaListPage(pageable);
        return success(result);
    }


//    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
//    @ApiOperation(value = "运营商或代理商直接创建的投放点", consumes = "application/json")
//    @PostMapping({"/children", "/listByOperator"})
//    public ResponseObject<Page<DeviceLaunchAreaListDto>> children(@RequestBody RequestObject<Pageable<QueryForCreatorDto>> requestObject) {
//        Pageable<QueryForCreatorDto> data = requestObject.getData();
//        Pageable<DeviceLaunchAreaQueryDto> pageable = new Pageable<>();
//        BeanUtils.copyProperties(data, pageable, "query");
//        pageable.setQuery(new DeviceLaunchAreaQueryDto());
//        if (Objects.isNull(data.getQuery()) || Objects.isNull(data.getQuery().getCreatorId())) {
//            throw new IllegalArgumentException("参数错误");
//        }
//        pageable.getQuery().setAccessableUserIds(Collections.singletonList(data.getQuery().getCreatorId()));
//        return success(deviceLaunchAreaService.getDeviceLaunchAreaListPage(pageable));
//    }


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = " 添加",notes = " 添加",consumes = "application/json")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public @ResponseBody
    ResponseObject add(@RequestParam(name = "deviceLaunchArea")String json){
        JSONObject jsonObject = JSONObject.parseObject(json);
        DeviceLaunchArea deviceLaunchArea = JSONObject.toJavaObject(jsonObject,DeviceLaunchArea.class);

        return success(deviceLaunchAreaService.addDeviceLaunchArea(deviceLaunchArea));
    }


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = " 维护人员",notes = " 维护人员",consumes = "application/json")
    @RequestMapping(value = "/maintainer",method = RequestMethod.POST)
    public @ResponseBody ResponseObject maintainer(){
        logger.info("维护人员列表");
        return success(deviceLaunchAreaService.getDeviceMaintainerDtos());
    }


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "更新",notes = " 更新",consumes = "application/json")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public @ResponseBody
    ResponseObject update(@RequestParam(name = "deviceLaunchArea")String json){
        JSONObject jsonObject = JSONObject.parseObject(json);
        DeviceLaunchArea deviceLaunchArea = JSONObject.toJavaObject(jsonObject,DeviceLaunchArea.class);
        logger.info("更新deviceLaunchArea,id="+deviceLaunchArea.getId());
        deviceLaunchAreaService.updateDeviceLaunchArea(deviceLaunchArea);
        return success();
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "删除",notes = " 删除",consumes = "application/json")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public @ResponseBody
    ResponseObject<String> delete(@RequestBody @Valid RequestObject<List<Integer>> requestObject){
        logger.info("投放点的id列表，批量删除"+requestObject.getData());
        List<Integer> areaIds = requestObject.getData();
        return success(deviceLaunchAreaService.deleteDeviceLaunchAreas(areaIds));
    }

    @ApiOperation(value = " 投放点名称是否存在",notes = "投放点名称是否存在",consumes = "application/json")
    @RequestMapping(value = "/isExist",method = RequestMethod.POST)
    public ResponseObject isExist(@RequestBody @Valid RequestObject<String> requestObject){
        logger.info(" 投放点名称："+requestObject.getData());
        return success(deviceLaunchAreaService.deviceLaunchAreaIsExist(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "仓库状态切换", consumes = "application/json")
    @PostMapping("/switch")
    public ResponseObject change(@RequestBody RequestObject<DeviceLaunchAreaQueryDto> requestObject) {
        return success(deviceLaunchAreaService.change(requestObject.getData()));
    }






}
