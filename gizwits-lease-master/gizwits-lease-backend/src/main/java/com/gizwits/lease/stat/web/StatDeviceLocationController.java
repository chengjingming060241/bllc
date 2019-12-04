package com.gizwits.lease.stat.web;

import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.product.entity.Product;
import com.gizwits.lease.product.service.ProductService;
import com.gizwits.lease.stat.dto.StatDeviceDto;
import com.gizwits.lease.stat.service.StatDeviceLocationService;
import com.gizwits.lease.stat.vo.StatLocationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 设备地图分布统计表 前端控制器
 * </p>
 *
 * @author gagi
 * @since 2017-07-14
 */
@EnableSwagger2
@Api(description = "设备分布图与分布排行")
@RestController
@RequestMapping("/stat/statDeviceLocation")
public class StatDeviceLocationController extends BaseController {
    @Autowired
    private StatDeviceLocationService statDeviceLocationService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ProductService productService;

    @Autowired
    private DeviceService deviceService;

//    //设备分布
//    @ApiOperation(value = "获取设备分布图（productId）")
//    @RequestMapping(value = "/deviceDitribution", method = RequestMethod.POST)
//    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
//    public ResponseObject<List<StatLocationVo>> ditribution(@RequestBody @Valid RequestObject<StatDeviceDto> requestObject) {
//        if(ParamUtil.isNullOrEmptyOrZero(requestObject.getData())){
//            StatDeviceDto deviceDto = new StatDeviceDto();
//            deviceDto.setProductId(0);
//            requestObject.setData(deviceDto);
//        }
//
//        SysUser currentUser = sysUserService.getCurrentUserOwner();
//        //根据当前用户查询需要共享的用户
//        List<Integer> sysUserIds = sysUserService.resolveSysUserAllSubIds(currentUser);
//
//        List<Integer> productIds;
//        Integer productId = requestObject.getData().getProductId();
//        if(ParamUtil.isNullOrZero(productId)){
//            productIds = productService.getProductsWithPermission().stream().map(Product::getId).collect(Collectors.toList());
//        }else {
//            productIds = new ArrayList<>();
//            productIds.add(productId);
//        }
//
//        List<StatLocationVo> list = statDeviceLocationService.getDistribution(productIds, currentUser, sysUserIds);
//        return success(list);
//    }
//
//    //设备分布排行
//    @ApiOperation(value = "获取设备分布图排行（productId）")
//    @RequestMapping(value = "/deviceRank", method = RequestMethod.POST)
//    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
//    public ResponseObject<List<StatLocationVo>> rank(@RequestBody @Valid RequestObject<StatDeviceDto> requestObject) {
//        if(ParamUtil.isNullOrEmptyOrZero(requestObject.getData())){
//            StatDeviceDto deviceDto = new StatDeviceDto();
//            deviceDto.setProductId(0);
//            requestObject.setData(deviceDto);
//        }
//        SysUser currentUser = sysUserService.getCurrentUserOwner();
//        List<Integer> ids = sysUserService.resolveSysUserAllSubIds(currentUser);
//
//        List<Integer> productIds;
//        Integer productId = requestObject.getData().getProductId();
//        if(ParamUtil.isNullOrZero(productId)){
//            productIds = productService.getProductsWithPermission().stream().map(Product::getId).collect(Collectors.toList());
//        }else {
//            productIds = new ArrayList<>();
//            productIds.add(productId);
//        }
//
//        List<StatLocationVo> list = statDeviceLocationService.getRank(productIds, currentUser, ids);
//        return success(list);
//    }

    //=========================================================================//
    @ApiOperation(value = "获取设备分布，排行榜--省（productId）")
    @RequestMapping(value = "/deviceDitribution", method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    public ResponseObject<List<StatLocationVo>> ditributionByProvince(@RequestBody @Valid RequestObject<StatDeviceDto> requestObject) {

         return success(deviceService.ditributionByProvince(requestObject.getData()));
    }
    @ApiOperation(value = "获取设备分布图，排行榜--市（productId）")
    @RequestMapping(value = "/ditributionByCity", method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    public ResponseObject<List<StatLocationVo>> ditributionByCity(@RequestBody @Valid RequestObject<StatDeviceDto> requestObject) {

        return success(deviceService.ditributionByCity(requestObject.getData()));
    }
    @ApiOperation(value = "获取设备分布图排行-省（productId）")
    @RequestMapping(value = "/deviceRank", method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    public ResponseObject<List<StatLocationVo>> rankByProvince(@RequestBody @Valid RequestObject<StatDeviceDto> requestObject) {
        return success(deviceService.ditributionByProvince(requestObject.getData()));
    }
    //=========================================================================//
}
