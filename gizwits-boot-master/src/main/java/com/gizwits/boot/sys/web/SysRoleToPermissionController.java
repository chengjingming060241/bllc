package com.gizwits.boot.sys.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.dto.PageDto;
import com.gizwits.boot.sys.entity.SysRoleToPermission;
import com.gizwits.boot.sys.service.SysRoleToPermissionService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 * 角色权限关系表(多对多) 前端控制器
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@RestController
@EnableSwagger2
@Api(description = "系统角色对应权限接口")
@RequestMapping("/sys/sysRoleToPermission")
public class SysRoleToPermissionController  extends BaseController {

    @Autowired
    private SysRoleToPermissionService sysRoleToPermissionService;

    @ApiOperation(value = "添加", notes = "添加",consumes = "application/json")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public @ResponseBody
    ResponseObject add(@RequestBody @Valid RequestObject<SysRoleToPermission> requestObject){
        sysRoleToPermissionService.insert(requestObject.getData());
        return success();
    }

    @ApiOperation(value = "分页查询", notes = "分页查询",consumes = "application/json")
    @RequestMapping(value = "/getListByPage",method = RequestMethod.POST)
    public @ResponseBody ResponseObject getListByPage(@RequestBody  @Valid  RequestObject<PageDto> requestObject ){
        EntityWrapper<SysRoleToPermission> entityWrapper = new EntityWrapper<SysRoleToPermission>();
        PageDto  pageDto = requestObject.getData();
        Page<SysRoleToPermission>  page = new Page<SysRoleToPermission>();
        BeanUtils.copyProperties(pageDto,page);
        entityWrapper.orderBy(page.getOrderByField(),page.isAsc());
        Page<SysRoleToPermission> selectPage= sysRoleToPermissionService.selectPage(page,entityWrapper);
        return success(selectPage);
    }

    @ApiOperation(value = "更新", notes = "更新",consumes = "application/json")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public @ResponseBody ResponseObject update(@RequestBody SysRoleToPermission SysRoleToPermission){
        SysRoleToPermission.setUtime(new Date());
        sysRoleToPermissionService.updateById(SysRoleToPermission);
        return success();
    }

    @ApiOperation(value = "删除", notes = "删除",consumes = "application/json")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public @ResponseBody ResponseObject delete(@RequestBody List<Integer> ids){
        sysRoleToPermissionService.deleteBatchIds(ids);
        return success();
    }
}
