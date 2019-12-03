package com.gizwits.lease.product.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.lease.common.version.DefaultVersion;
import com.gizwits.lease.common.version.Version;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.model.DeleteDto;
import com.gizwits.lease.product.dto.*;
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
 * 产品品类表 前端控制器
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@RestController
@EnableSwagger2
@Api(description = "产品品类接口")
@RequestMapping("/product/product")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private DeviceService deviceService;

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "添加", notes = "添加", consumes = "application/json")
    @PostMapping(value = "/add")
    public ResponseObject add(@RequestBody @Valid RequestObject<ProductForAddDto> requestObject) {
        return success(productService.add(requestObject.getData()));
    }

//    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
//    @ApiOperation(value = "获取添加界面的数据", consumes = "application/json")
//    @GetMapping(value = "/adds")
//    public ResponseObject<PreProductDto> add() {
//        return success(productService.getAddProductPageData());
//    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "查看", consumes = "application/json")
    @PostMapping("/detail")
    @DefaultVersion
    public ResponseObject<ProductForDetailDto> detail(@RequestBody RequestObject<Integer> requestObject) {
        ProductForDetailDto detailDto = productService.detail(requestObject.getData());
        detailDto.setDeviceCount(deviceService.countLeaseDeviceByProductId(requestObject.getData()));
        return success(detailDto);
    }

    @Version(uri = "/detail",version = "1.1")
    public ResponseObject<ProductForDetailDto> detail2(@RequestBody RequestObject<Integer> requestObject) {
        return success(productService.detail(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "分页查询", notes = "分页查询", consumes = "application/json")
    @PostMapping(value = "/page")
    @DefaultVersion
    public ResponseObject<Page<ProductForListDto>> page(@RequestBody RequestObject<Pageable<ProductQueryDto>> requestObject) {
        Pageable<ProductQueryDto> pageable = requestObject.getData();
        if (Objects.isNull(pageable.getQuery())) {
            pageable.setQuery(new ProductQueryDto());
        }
        SysUser current = sysUserService.getCurrentUser();
        pageable.getQuery().setIsDeleted(DeleteStatus.NOT_DELETED.getCode());
        Integer manufacturerAccountId = productService.resolveManufacturerAccount(current);
        if (Objects.nonNull(manufacturerAccountId)) {
            pageable.getQuery().setManufacturerAccountId(manufacturerAccountId);
        } else {
            List<Integer> integers = sysUserService.resolveOwnerAccessableUserIds(sysUserService.getSysUserOwner(current));
            pageable.getQuery().setAccessableUserIds(integers);
        }
        return success(productService.page(pageable));
    }

    @Version(uri = "/page",version = "1.1")
    public ResponseObject<Page<ProductForListDto>> page2(@RequestBody RequestObject<Pageable<ProductQueryDto>> requestObject) {
        Pageable<ProductQueryDto> pageable = requestObject.getData();
        if (Objects.isNull(pageable.getQuery())) {
            pageable.setQuery(new ProductQueryDto());
        }
        SysUser current = sysUserService.getCurrentUser();
        pageable.getQuery().setIsDeleted(DeleteStatus.NOT_DELETED.getCode());
        Integer manufacturerAccountId = productService.resolveManufacturerAccount(current);
        if (Objects.nonNull(manufacturerAccountId)) {
            pageable.getQuery().setManufacturerAccountId(manufacturerAccountId);
        } else {
            pageable.getQuery().setAccessableUserIds(sysUserService.resolveSysUserAllSubIds(sysUserService.getSysUserOwner(current)));
        }

        return success(productService.page2(pageable));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "品类下拉列表", consumes = "application/json")
    @PostMapping("/pull")
    public ResponseObject<List<ProductForPullDto>> pull() {
        return success(productService.getProductsWithPermission().stream().map(ProductForPullDto::new).collect(Collectors.toList()));
    }


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "同步", consumes = "application/json")
    @PostMapping("/sync")
    public ResponseObject<ProductDataPointForListDto> sync(@RequestBody @Valid RequestObject<GizwitsDataPointReqDto> requestObject) {
        return success(productService.sync(requestObject.getData()));
    }


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "删除", notes = "删除", consumes = "application/json")
    @PostMapping(value = "/delete")
    public ResponseObject<String> disable(@RequestBody RequestObject<List<Integer>> requestObject) {
        return success( productService.delete(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "更新", consumes = "application/json")
    @PostMapping("/update")
    public ResponseObject update(@RequestBody @Valid RequestObject<ProductForUpdateDto> requestObject) {
        productService.update(requestObject.getData());
        return success();
    }
}
