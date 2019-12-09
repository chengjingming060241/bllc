package com.gizwits.lease.message.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.lease.message.entity.AdvertisementDisplay;
import com.gizwits.lease.message.entity.dto.AdvertisementQueryDto;
import com.gizwits.lease.message.service.AdvertisementDisplayService;
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
 * 广告展示表 前端控制器
 * </p>
 *
 * @author yinhui
 * @since 2018-01-03
 */
@RestController
@Api(description = "广告展示接口")
@EnableSwagger2
@RequestMapping("/message/advertisementDisplay")
public class AdvertisementDisplayController extends BaseController{

    @Autowired
    private AdvertisementDisplayService advertisementDisplayService;

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "添加", notes = "添加", consumes = "application/json")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseObject add(@RequestParam(value = "file",required = false) MultipartFile file,
                              @RequestParam(value = "showTime",required = false) Integer showTime,
                              @RequestParam(value = "url",required = false)String url,
                              @RequestParam(value = "name",required = false)String name,
                              @RequestParam(value = "sort",required = false)Integer sort,
                              @RequestParam(value = "type",required = false)Integer type,
                              @RequestParam(value = "id",required = false)Integer id){


        return success(advertisementDisplayService.addOrUpdate(file,showTime,url,name,sort,id,type));
    }


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "广告详情", notes = "广告详情", consumes = "application/json")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public ResponseObject<AdvertisementDisplay> detail(@RequestBody @Valid RequestObject<Integer> requestObject) {

        return success(advertisementDisplayService.detail(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "列表", notes = "列表", consumes = "application/json")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseObject<Page<AdvertisementDisplay>> list(@RequestBody @Valid RequestObject<Pageable<AdvertisementQueryDto>> requestObject) {

        return success(advertisementDisplayService.list(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "删除", notes = "删除", consumes = "application/json")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseObject delete(@RequestBody @Valid RequestObject<List<Integer>> requestObject) {

        return success(advertisementDisplayService.delete(requestObject.getData()));
    }
}

