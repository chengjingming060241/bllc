package com.gizwits.lease.app.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.lease.device.entity.dto.DeviceForFireDto;
import com.gizwits.lease.product.entity.ProductCategory;
import com.gizwits.lease.product.service.ProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;

/**
 * Description:
 * Created by Sunny on 2019/12/17 16:33
 */
@EnableSwagger2
@Api(description = "产品类型(组)接口")
@RestController
@RequestMapping("/app/productCategory")
public class ProductCategoryController extends BaseController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "发送控制指令", consumes = "application/json")
    @GetMapping("/getProductCategory")
    public ResponseObject getProductCategory() {
       return success(productCategoryService.selectList(new EntityWrapper<ProductCategory>().eq("is_deleted",0)));
    }
}
