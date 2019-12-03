package com.gizwits.boot.sys.web;

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.common.SysDeleteDto;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.dto.SysRoleForAddDto;
import com.gizwits.boot.dto.SysRoleForDetailDto;
import com.gizwits.boot.dto.SysRoleForListDto;
import com.gizwits.boot.dto.SysRoleForPullDto;
import com.gizwits.boot.sys.entity.SysPermission;
import com.gizwits.boot.dto.SysRoleForQueryDto;
import com.gizwits.boot.sys.entity.SysRole;
import com.gizwits.boot.sys.service.SysRoleService;
import com.gizwits.boot.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 * 系统角色表 前端控制器
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@RestController
@EnableSwagger2
@Api(description = "系统角色接口")
@RequestMapping("/sys/sysRole")
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserService sysUserService;


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "添加", consumes = "application/json")
    @GetMapping("/add")
    public ResponseObject<List<SysPermission>> add() {
        return success(sysUserService.getPermissionByUserId(sysUserService.getCurrentUser().getId()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "添加", notes = "添加", consumes = "application/json")
    @PostMapping(value = "/add")
    public ResponseObject add(@RequestBody @Valid RequestObject<SysRoleForAddDto> requestObject) {
        sysRoleService.add(requestObject.getData());
        return success();
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "分页查询", notes = "分页查询", consumes = "application/json")
    @PostMapping(value = "/getListByPage")
    public ResponseObject<Page<SysRoleForListDto>> getListByPage(@RequestBody @Valid RequestObject<Pageable<SysRoleForQueryDto>> requestObject) {
        return success(sysRoleService.getListByPage(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "详情", consumes = "application/json")
    @PostMapping("/detail")
    public ResponseObject<SysRoleForDetailDto> detail(@RequestBody RequestObject<Integer> requestObject) {
        return success(sysRoleService.detail(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "更新", notes = "更新", consumes = "application/json")
    @PostMapping(value = "/update")
    public ResponseObject update(@RequestBody @Valid RequestObject<SysRoleForAddDto> requestObject) {
        sysRoleService.update(requestObject.getData());
        return success();
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "删除", notes = "删除", consumes = "application/json")
    @PostMapping(value = "/delete")
    public ResponseObject<String > delete(@RequestBody RequestObject<List<Integer>> requestObject) {
        return success(sysRoleService.delete(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "下拉框中的角色数据", consumes = "application/json")
    @PostMapping("/pull")
    public ResponseObject<List<SysRoleForPullDto>> pull(@RequestBody RequestObject<String> body) {
        List<SysRole> roles = sysRoleService.getCreatedRolesBySysUserId(sysUserService.resolveOwnerAccessableUserIds(sysUserService.getCurrentUserOwner()));
        List<SysRoleForPullDto> sysRoleForPullDtos = new LinkedList<>();
        if (CollectionUtils.isNotEmpty(roles)) {
            roles.forEach(role -> sysRoleForPullDtos.add(new SysRoleForPullDto(role)));
        }
        return success(sysRoleForPullDtos);
    }
}
