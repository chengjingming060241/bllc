package com.gizwits.lease.app.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.lease.device.entity.dto.DeviceForFireDto;
import com.gizwits.lease.product.dto.AppProductQueryDto;
import com.gizwits.lease.product.entity.Product;
import com.gizwits.lease.product.entity.ProductCategory;
import com.gizwits.lease.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;

/**
 * Description:
 * Created by Sunny on 2019/12/17 16:38
 */
@EnableSwagger2
@Api(description = "产品接口")
@RestController
@RequestMapping("/app/product")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "获取产品", consumes = "application/json")
    @GetMapping("/getProduct")
    public ResponseObject getProduct(@RequestBody @Valid RequestObject<AppProductQueryDto> requestObject) {
        return success(productService.getProduct(requestObject.getData()));
    }
}
