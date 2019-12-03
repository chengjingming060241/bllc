package com.gizwits.boot.sys.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.dto.PageDto;
import com.gizwits.boot.sys.entity.SysUserExt;
import com.gizwits.boot.sys.service.SysUserExtService;

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
 * 系统用户扩展表 前端控制器
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@RestController
@EnableSwagger2
@Api(description = "系统用户扩展接口")
@RequestMapping("/sys/sysUserExt")
public class SysUserExtController  extends BaseController {

    @Autowired
    private SysUserExtService sysUserExtService;

    @ApiOperation(value = "添加", notes = "添加",consumes = "application/json")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public @ResponseBody
    ResponseObject add(@RequestBody @Valid RequestObject<SysUserExt> requestObject){
        sysUserExtService.insert(requestObject.getData());
        return success();
    }

    @ApiOperation(value = "分页查询", notes = "分页查询",consumes = "application/json")
    @RequestMapping(value = "/getListByPage",method = RequestMethod.POST)
    public @ResponseBody ResponseObject getListByPage(@RequestBody  @Valid  RequestObject<PageDto> requestObject ){
        EntityWrapper<SysUserExt> entityWrapper = new EntityWrapper<SysUserExt>();
        PageDto  pageDto = requestObject.getData();
        Page<SysUserExt>  page = new Page<SysUserExt>();
        BeanUtils.copyProperties(pageDto,page);
        entityWrapper.orderBy(page.getOrderByField(),page.isAsc());
        Page<SysUserExt> selectPage= sysUserExtService.selectPage(page,entityWrapper);
        return success(selectPage);
    }

    @ApiOperation(value = "更新", notes = "更新",consumes = "application/json")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public @ResponseBody ResponseObject update(@RequestBody SysUserExt SysUserExt){
        SysUserExt.setUtime(new Date());
        sysUserExtService.updateById(SysUserExt);
        return success();
    }

    @ApiOperation(value = "删除", notes = "删除",consumes = "application/json")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public @ResponseBody ResponseObject delete(@RequestBody List<Integer> ids){
        sysUserExtService.deleteBatchIds(ids);
        return success();
    }
}
