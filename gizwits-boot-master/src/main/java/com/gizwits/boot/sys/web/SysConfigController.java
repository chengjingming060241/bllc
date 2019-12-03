package com.gizwits.boot.sys.web;

import java.util.Date;

import javax.validation.Valid;

import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.dto.PageDto;
import com.gizwits.boot.dto.SysConfigForUpdateDto;
import com.gizwits.boot.sys.entity.SysConfig;
import com.gizwits.boot.sys.service.SysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 * 系统配置表,用来动态添加常量配置 前端控制器
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@RestController
@EnableSwagger2
@Api(description = "系统配置接口")
@RequestMapping("/sys/sysConfig")
public class SysConfigController extends BaseController {

    @Autowired
    private SysConfigService sysConfigService;

    @ApiOperation(value = "添加", notes = "添加", consumes = "application/json")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseObject add(@RequestBody @Valid RequestObject<SysConfig> requestObject) {
        SysConfig sysConfig = requestObject.getData();
        sysConfig.setCtime(new Date());
        sysConfig.setUtime(new Date());
        sysConfigService.insert(sysConfig);
        return success();
    }

    @ApiOperation(value = "分页查询", notes = "分页查询", consumes = "application/json")
    @RequestMapping(value = "/getListByPage", method = RequestMethod.POST)
    public ResponseObject<Page<SysConfig>> getListByPage(@RequestBody @Valid RequestObject<PageDto> requestObject) {
        PageDto pageDto = requestObject.getData();
        Page<SysConfig> selectPage = sysConfigService.getListByPage(pageDto);
        return success(selectPage);
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "更新", notes = "更新", consumes = "application/json")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseObject<Boolean> update(@RequestBody RequestObject<SysConfigForUpdateDto> requestObject) {
        return success(sysConfigService.update(requestObject.getData()));
    }

    @ApiOperation(value = "删除", notes = "删除", consumes = "application/json")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseObject<Boolean> delete(@RequestBody @Valid RequestObject<Integer> requestObject) {
        return success(sysConfigService.deleteById(requestObject.getData()));
    }

}
