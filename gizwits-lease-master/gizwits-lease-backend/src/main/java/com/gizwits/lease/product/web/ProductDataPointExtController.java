package com.gizwits.lease.product.web;

import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.lease.product.dto.*;
import com.gizwits.lease.product.service.ProductDataPointExtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;

/**
 * <p>
 * 产品指令配置扩展表 前端控制器
 * </p>
 *
 * @author yuqing
 * @since 2018-02-03
 */
@EnableSwagger2
@Api("扩展指令")
@RestController
@RequestMapping("/product/productDataPointExtController")
public class ProductDataPointExtController extends BaseController {

    @Autowired
    private ProductDataPointExtService productDataPointExtService;

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "添加扩展指令", consumes = "application/json")
    @PostMapping("/add")
    public ResponseObject<ProductDataPointExtForDetailDto> add(@RequestBody @Valid RequestObject<ProductDataPointExtForAddDto> params) {
        return success(new ProductDataPointExtForDetailDto(productDataPointExtService.add(params.getData())));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "更新扩展指令", consumes = "application/json")
    @PostMapping("/update")
    public ResponseObject<ProductDataPointExtForDetailDto> update(@RequestBody @Valid RequestObject<ProductDataPointExtForUpdateDto> params) {
        return success(new ProductDataPointExtForDetailDto(productDataPointExtService.update(params.getData())));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "删除扩展指令", consumes = "application/json")
    @PostMapping("/delete")
    public ResponseObject<Integer> delete(@RequestBody @Valid RequestObject<ProductDataPointExtForDeleteDto> params) {
        return success(productDataPointExtService.delete(params.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "查询扩展指令", consumes = "application/json")
    @PostMapping("/query")
    public ResponseObject<ProductDataPointExtForQueryResultDto> query(@RequestBody @Valid RequestObject<ProductDataPointExtForQueryDto> params) {
        return success(new ProductDataPointExtForQueryResultDto(productDataPointExtService.find(params.getData())));
    }
}
