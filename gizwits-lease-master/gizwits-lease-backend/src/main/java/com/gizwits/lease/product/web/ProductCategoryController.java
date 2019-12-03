package com.gizwits.lease.product.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.lease.model.DeleteDto;
import com.gizwits.lease.product.dto.*;
import com.gizwits.lease.product.service.ProductCategoryService;
import com.gizwits.lease.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 产品类型 前端控制器
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@RestController
@RequestMapping("/product/productCategory")
@Api(description = "产品接口")
@EnableSwagger2
public class ProductCategoryController extends BaseController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private SysUserService sysUserService;


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "添加", notes = "添加", consumes = "application/json")
    @PostMapping(value = "/add")
    public ResponseObject add(@RequestBody @Valid RequestObject<ProductCategoryForAddDto> requestObject) {
        productCategoryService.add(requestObject.getData());
        return success();
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "获取添加界面的数据", consumes = "application/json")
    @GetMapping(value = "/add")
    public ResponseObject<PreProductDto> add() {
        return success(productCategoryService.getAddProductPageData());
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "分页查询", notes = "分页查询", consumes = "application/json")
    @PostMapping(value = "/page")
    public ResponseObject<Page<ProductCategoryForListDto>> page(@RequestBody RequestObject<Pageable<ProductCategoryForQueryDto>> requestObject) {
        Pageable<ProductCategoryForQueryDto> pageable = requestObject.getData();
        if (Objects.isNull(pageable.getQuery())) {
            pageable.setQuery(new ProductCategoryForQueryDto());
        }
        pageable.getQuery().setAccessableUserIds(sysUserService.resolveSysUserAllSubIds(sysUserService.getCurrentUserOwner()));
        return success(productCategoryService.page(pageable));
    }


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "详情", consumes = "application/json")
    @PostMapping("/detail")
    public ResponseObject<ProductCategoryForDetailDto> detail(@RequestBody RequestObject<Integer> requestObject) {
        return success(productCategoryService.detail(requestObject.getData()));
    }


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "更新", notes = "更新", consumes = "application/json")
    @PostMapping(value = "/update")
    public ResponseObject update(@RequestBody @Valid RequestObject<ProductCategoryForUpdateDto> requestObject) {
        productCategoryService.update(requestObject.getData());
        return success();
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "删除", notes = "删除", consumes = "application/json")
    @PostMapping(value = "/delete")
    public ResponseObject<String> delete(@RequestBody RequestObject<List<Integer>> requestObject) {

        return success(productCategoryService.deleted(requestObject.getData()));
    }


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "产品下拉列表", consumes = "application/json")
    @PostMapping("/pull")
    public ResponseObject<List<ProductGategoryForPullDto>> pull() {
        return success(productCategoryService.getProductsWithPermission().stream().map(ProductGategoryForPullDto::new).collect(Collectors.toList()));
    }
}
