package com.gizwits.boot.sys.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.dto.PageDto;
import com.gizwits.boot.sys.entity.SysUserToRole;
import com.gizwits.boot.sys.service.SysUserToRoleService;

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
 * 用户角色关系表(多对多) 前端控制器
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@RestController
@EnableSwagger2
@Api(description = "系统用户对应角色接口")
@RequestMapping("/sys/sysUserToRole")
public class SysUserToRoleController  extends BaseController {

    @Autowired
    private SysUserToRoleService sysUserToRoleService;

    @ApiOperation(value = "添加", notes = "添加",consumes = "application/json")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public @ResponseBody
    ResponseObject add(@RequestBody @Valid RequestObject<SysUserToRole> requestObject){
        sysUserToRoleService.insert(requestObject.getData());
        return success();
    }

    @ApiOperation(value = "分页查询", notes = "分页查询",consumes = "application/json")
    @RequestMapping(value = "/getListByPage",method = RequestMethod.POST)
    public @ResponseBody ResponseObject getListByPage(@RequestBody  @Valid  RequestObject<PageDto> requestObject ){
        EntityWrapper<SysUserToRole> entityWrapper = new EntityWrapper<SysUserToRole>();
        PageDto  pageDto = requestObject.getData();
        Page<SysUserToRole>  page = new Page<SysUserToRole>();
        BeanUtils.copyProperties(pageDto,page);
        entityWrapper.orderBy(page.getOrderByField(),page.isAsc());
        Page<SysUserToRole> selectPage= sysUserToRoleService.selectPage(page,entityWrapper);
        return success(selectPage);
    }

    @ApiOperation(value = "更新", notes = "更新",consumes = "application/json")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public @ResponseBody ResponseObject update(@RequestBody SysUserToRole SysUserToRole){
        SysUserToRole.setUtime(new Date());
        sysUserToRoleService.updateById(SysUserToRole);
        return success();
    }

    @ApiOperation(value = "删除", notes = "删除",consumes = "application/json")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public @ResponseBody ResponseObject delete(@RequestBody List<Integer> ids){
        sysUserToRoleService.deleteBatchIds(ids);
        return success();
    }
}
