package com.gizwits.lease.app.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.lease.product.entity.ProductCategory;
import com.gizwits.lease.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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
    @ApiOperation(value = "发送控制指令", consumes = "application/json")
    @GetMapping("/getProduct")
    public ResponseObject getProductCategory() {
        return success();
    }
}
