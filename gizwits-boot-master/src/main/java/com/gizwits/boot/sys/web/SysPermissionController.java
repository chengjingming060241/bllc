package com.gizwits.boot.sys.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.common.SysDeleteDto;
import com.gizwits.boot.dto.PageDto;
import com.gizwits.boot.dto.SysPermissionForAddDto;
import com.gizwits.boot.dto.SysPermissionForDetailDto;
import com.gizwits.boot.dto.SysPermissionForUpdateDto;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.sys.entity.SysPermission;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysPermissionService;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.sys.service.SysVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 系统权限(菜单)表 前端控制器
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@RestController
@EnableSwagger2
@Api(description = "系统权限接口")
@RequestMapping("/sys/sysPermission")
public class SysPermissionController extends BaseController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysVersionService sysVersionService;

    @Autowired
    private SysUserService sysUserService;

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "添加", notes = "添加", consumes = "application/json")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseObject add(@RequestBody @Valid RequestObject<SysPermissionForAddDto> requestObject) {
        SysPermissionForAddDto dto = requestObject.getData();
        SysPermission permission = new SysPermission();
        BeanUtils.copyProperties(dto, permission);
        SysUser current = sysUserService.getCurrentUserOwner();
        permission.setCtime(new Date());
        permission.setUtime(new Date());
        permission.setSysUserId(current.getId());
        permission.setSysUserName(current.getUsername());
        permission.setIsDeleted(DeleteStatus.NOT_DELETED.getCode());
        if (sysPermissionService.insert(permission)) {
            sysVersionService.add(permission.getId(), dto.getVersions());
        }
        return success();
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "分页查询", notes = "分页查询", consumes = "application/json")
    @RequestMapping(value = "/getListByPage", method = RequestMethod.POST)
    public ResponseObject getListByPage(@RequestBody @Valid RequestObject<PageDto> requestObject) {
        PageDto pageDto = requestObject.getData();
        Page<SysPermissionForDetailDto> selectPage = sysPermissionService.getListByPage(pageDto);
        return success(selectPage);
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "更新", notes = "更新", consumes = "application/json")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseObject update(@RequestBody @Valid RequestObject<SysPermissionForUpdateDto> requestObject) {
        sysPermissionService.update(requestObject.getData());
        return success();
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "删除", notes = "删除", consumes = "application/json")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseObject<String> delete(@RequestBody RequestObject<List<Integer>> requestObject) {

        return success(sysPermissionService.delete(requestObject.getData()));
    }
}
