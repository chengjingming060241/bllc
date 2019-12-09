package com.gizwits.lease.tmallLink.web;


import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.lease.tmallLink.dto.*;
import com.gizwits.lease.tmallLink.service.TmallLinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * 天猫链接，前端控制器
 */


@RestController
@EnableSwagger2
@Api(description = "天猫链接接口")
@RequestMapping("/tmall")
public class TmallLinkController extends BaseController {

    @Autowired
    private TmallLinkService tmallLinkService;

    @ApiImplicitParam(paramType = "header" ,name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "添加" ,notes = "添加" ,consumes = "application/json")
    @PostMapping("/add")
    public ResponseObject<Boolean> add(@RequestBody @Valid RequestObject<TmallLinkForAddDto> requestObject){
        return success(tmallLinkService.add(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header" , name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "天猫链接列表" ,notes = "链接列表" ,consumes = "application/json")
    @PostMapping("/list")
    public ResponseObject<Page<TmallLinkForListDto>> list(@RequestBody RequestObject<Pageable<TmallLinkQueryDto>> queryDtoPageable){
        Pageable<TmallLinkQueryDto> pageable = queryDtoPageable.getData();
        if (Objects.isNull(pageable.getQuery())){
            pageable.setQuery(new TmallLinkQueryDto());
        }
        pageable.getQuery().setIsDeleted(DeleteStatus.NOT_DELETED.getCode());  //未删除
       return success(tmallLinkService.page(queryDtoPageable.getData()));
    }

    @ApiImplicitParam(paramType = "header" , name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "链接详情" , notes = "详情", consumes = "application/json")
    @PostMapping("/detail")
    public ResponseObject<TmallLinkForDetailDto> detail(@RequestBody RequestObject<Integer> requestObject){
        return success(tmallLinkService.detail(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header" , name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "详情更新", notes = "更新" , consumes = "application/json")
    @PostMapping("/update")
    public ResponseObject<TmallLinkForDetailDto> update(@RequestBody @Valid RequestObject<TmallLinkForUpdateDto> requestObject){
        TmallLinkForUpdateDto dto = requestObject.getData();
        return success(tmallLinkService.update(dto));
    }

    @ApiImplicitParam(paramType = "header",name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "批量删除" ,notes = "删除" ,consumes = "application/json")
    @PostMapping("/delete")
    public ResponseObject<String> delete(@RequestBody RequestObject<List<Integer>> requestObject){
        return success(tmallLinkService.delete(requestObject.getData()));
    }


}
