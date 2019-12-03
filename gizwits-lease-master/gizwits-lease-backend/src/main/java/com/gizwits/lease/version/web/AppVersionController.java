package com.gizwits.lease.version.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.lease.version.entity.AppVersion;
import com.gizwits.lease.version.service.AppVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * app版本记录表 前端控制器
 * </p>
 *
 * @author yinhui
 * @since 2018-01-23
 */
@RestController
@EnableSwagger2
@Api(description = "app版本接口")
@RequestMapping("/version/appVersion")
public class AppVersionController extends BaseController{

    @Autowired
    private AppVersionService appVersionService;

    @ApiImplicitParam(paramType = "header",name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "版本列表 ")
    @PostMapping(value = "/list")
    public ResponseObject<Page<AppVersion>> list(@RequestBody @Valid RequestObject<Pageable> requestObject){
        return success(appVersionService.list(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header",name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "添加或修改 ")
    @PostMapping(value = "/add")
    public ResponseObject add(@RequestParam(value = "file",required = true)MultipartFile file,
                              @RequestParam(value = "id",required = false)Integer id,
                              @RequestParam(value = "description",required = true)String description,
                              @RequestParam(value = "version",required = true)String version){
        appVersionService.add(id,file,version,description);
        return success();
    }

    @ApiImplicitParam(paramType = "header",name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "详情 ")
    @PostMapping(value = "/detail")
    public ResponseObject<AppVersion> detail(@RequestBody @Valid RequestObject<Integer> requestObject){

        return success(appVersionService.selectById(requestObject.getData()));
    }


    @ApiImplicitParam(paramType = "header",name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "删除 ")
    @PostMapping(value = "/delete")
    public ResponseObject delete(@RequestBody @Valid RequestObject<List<Integer>> requestObject){
        appVersionService.delete(requestObject.getData());
        return success();
    }

    @ApiImplicitParam(paramType = "header",name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "最新app版本 ")
    @PostMapping(value = "/newVersion")
    public ResponseObject newVersion(@RequestBody @Valid RequestObject requestObject){

        return success(appVersionService.getNewVersion());
    }
}
