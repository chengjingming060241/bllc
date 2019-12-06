package com.gizwits.lease.export.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.enums.DeleteStatus;

import com.gizwits.boot.enums.SysUserType;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.SysConfigUtils;

import com.gizwits.lease.RequestLock;
import com.gizwits.lease.common.version.DefaultVersion;
import com.gizwits.lease.common.version.Version;
import com.gizwits.lease.config.CommonSystemConfig;
import com.gizwits.lease.constant.*;
import com.gizwits.lease.device.entity.dto.*;
import com.gizwits.lease.device.service.*;
import com.gizwits.lease.exceptions.ExcelException;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.manager.dto.AgentExportResultDto;
import com.gizwits.lease.manager.dto.AgentForListDto;
import com.gizwits.lease.manager.dto.AgentForQueryDto;

import com.gizwits.lease.manager.service.AgentService;
import com.gizwits.lease.order.dto.OrderListDto;
import com.gizwits.lease.order.dto.OrderQueryDto;
import com.gizwits.lease.order.service.OrderBaseService;
import com.gizwits.lease.product.dto.*;
import com.gizwits.lease.product.service.ProductCategoryService;
import com.gizwits.lease.product.service.ProductQrcodeService;
import com.gizwits.lease.product.service.ProductService;
import com.gizwits.lease.product.service.ProductServiceModeService;
import com.gizwits.lease.refund.dto.RefundInfoDto;
import com.gizwits.lease.refund.dto.RefundListQueryDto;
import com.gizwits.lease.refund.service.RefundApplyService;
import com.gizwits.lease.user.dto.UserForListDto;
import com.gizwits.lease.user.dto.UserForQueryDto;
import com.gizwits.lease.user.service.UserService;
import com.gizwits.lease.utils.ImportExcelUtils;
import com.gizwits.lease.utils.JxlExcelUtils;
import com.gizwits.lease.wallet.dto.UserWalletChargeListDto;
import com.gizwits.lease.wallet.dto.UserWalletChargeOrderQueryDto;
import com.gizwits.lease.wallet.service.UserWalletChargeOrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import static org.apache.commons.beanutils.locale.LocaleConvertUtils.convert;

/**
 * Controller - 导出
 *
 * @author lilh
 * @date 2017/8/10 10:23
 */
@Api(description = "导出")
@Controller
@RequestMapping
public class ExportController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceAlarmService deviceAlarmService;

    @Autowired
    private DeviceLaunchAreaService deviceLaunchAreaService;

    @Autowired
    private ProductServiceModeService productServiceModeService;

    @Autowired
    private OrderBaseService orderBaseService;

    @Autowired
    private DeviceQrcodeService deviceQrcodeService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private UserWalletChargeOrderService userWalletChargeOrderService;

    @Autowired
    private AgentService agentService;

    @Autowired
    private RefundApplyService refundApplyService;

    @Autowired
    private DeviceStockService deviceStockService;

    @Autowired
    private ProductQrcodeService productQrcodeService;

    @Autowired
    private DeviceLaunchAreaAssignService deviceLaunchAreaAssignService;




    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "产品品类列表导出", consumes = "application/json")
    @PostMapping("/product/product/export")
    public void product(@RequestBody @Valid RequestObject<ExportHelper<ProductQueryDto, Integer>> requestObject, HttpServletResponse response) {
        ExportHelper<ProductQueryDto, Integer> helper = requestObject.getData();
        Pageable<ProductQueryDto> pageable = getPageable();
        pageable.setQuery(Objects.isNull(helper.getQuery()) ? new ProductQueryDto() : helper.getQuery());
        //按更新时间降序
        pageable.setOrderByField("utime");
        pageable.setAsc(false);
        pageable.getQuery().setIsDeleted(DeleteStatus.NOT_DELETED.getCode());
        SysUser current = sysUserService.getCurrentUser();
        Integer manufacturerAccountId = productService.resolveManufacturerAccount(current);
        if (Objects.nonNull(manufacturerAccountId)) {
            pageable.getQuery().setManufacturerAccountId(manufacturerAccountId);
        } else {
            pageable.getQuery().setAccessableUserIds(sysUserService.resolveSysUserAllSubIds(sysUserService.getSysUserOwner(current)));
        }
        pageable.getQuery().setIds(helper.getIds());
        Page<ProductForListDto> result = productService.page(pageable);
        export("产品品类列表", helper, result.getRecords(), response);
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "产品列表导出", consumes = "application/json")
    @PostMapping("/product/productCategory/export")
    public void productCategory(@RequestBody @Valid RequestObject<ExportHelper<ProductCategoryForQueryDto, Integer>> requestObject, HttpServletResponse response) {
        ExportHelper<ProductCategoryForQueryDto, Integer> helper = requestObject.getData();
        Pageable<ProductCategoryForQueryDto> pageable = getPageable();
        pageable.setQuery(Objects.isNull(helper.getQuery()) ? new ProductCategoryForQueryDto() : helper.getQuery());
        pageable.getQuery().setAccessableUserIds(sysUserService.resolveSysUserAllSubIds(sysUserService.getCurrentUserOwner()));
        pageable.getQuery().setIds(helper.getIds());
        Page<ProductCategoryForListDto> result = productCategoryService.page(pageable);
        export("产品列表", helper, result.getRecords(), response);
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "产品列表导入")
    @PostMapping("/product/productCategory/upload")
    @DefaultVersion
    @RequestLock
    public ResponseObject< List<ProductExportResultDto>> uploadProductCategory(@RequestParam("file") MultipartFile file,
                                                                               @RequestParam(value = "categoryId",required = false) Integer categoryId) throws Exception {
        List<List<Object>> originData = ImportExcelUtils.parse(file.getInputStream(), file.getOriginalFilename());
        return success(productQrcodeService.importExcel(convertProduct(originData),categoryId));

    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "经销商列表导出", consumes = "application/json")
    @PostMapping("/agent/export")
    public void agent(@RequestBody @Valid RequestObject<ExportHelper<AgentForQueryDto, Integer>> requestObject, HttpServletResponse response) {
        ExportHelper<AgentForQueryDto, Integer> helper = requestObject.getData();
        Pageable<AgentForQueryDto> pageable = getPageable();
        pageable.setQuery(Objects.isNull(helper.getQuery()) ? new AgentForQueryDto() : helper.getQuery());
        pageable.getQuery().setIds(helper.getIds());
        pageable.getQuery().setAccessableUserIds(sysUserService.resolveSysUserAllSubAdminIds(sysUserService.getCurrentUserOwner()));
        Page<AgentForListDto> result = agentService.page(pageable);
        export("经销商列表", helper, result.getRecords(), response);
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "经销商列表导入")
    @PostMapping("/agent/upload")
    @DefaultVersion
    @RequestLock
    public ResponseObject< List<AgentExportResultDto>> upload(@RequestParam("file") MultipartFile file) throws Exception {
        List<List<Object>> originData = ImportExcelUtils.parse(file.getInputStream(), file.getOriginalFilename());
        return success(agentService.importExcel(convertAgent(originData)));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "仓库列表导出", consumes = "application/json")
    @PostMapping("/device/deviceLaunchArea/export")
    public void deviceLaunchArea(@RequestBody @Valid RequestObject<ExportHelper<DeviceLaunchAreaQueryDto, Integer>> requestObject, HttpServletResponse response) {
        ExportHelper<DeviceLaunchAreaQueryDto, Integer> helper = requestObject.getData();
        Pageable<DeviceLaunchAreaQueryDto> pageable = getPageable();
        pageable.setQuery(Objects.isNull(helper.getQuery()) ? new DeviceLaunchAreaQueryDto() : helper.getQuery());
        pageable.getQuery().setIds(helper.getIds());
        if (Objects.isNull(pageable.getQuery().getSelfOperating())) {
            pageable.getQuery().setSelfOperating(true);
        }
        SysUser userOwner = sysUserService.getCurrentUserOwner();
        List<Integer> userGroup = sysUserService.resolveOwnerAccessableUserIds(userOwner);
        if (pageable.getQuery().getSelfOperating()) {
            pageable.getQuery().setAccessableUserIds(userGroup);
        } else {
            //若没有子级用户,则返回空列表
            List<Integer> userIds = sysUserService.resolveSysUserAllSubIds(userOwner);
            if (Objects.isNull(userIds)){
                export("仓库列表", helper, new ArrayList<>(0), response);
            }
            userIds.removeAll(userGroup);
            pageable.getQuery().setAccessableUserIds(userIds);
        }
        Page<DeviceLaunchAreaListDto> result = deviceLaunchAreaService.getDeviceLaunchAreaListPage(pageable);
        export("仓库列表", helper, result.getRecords(), response);
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "仓库列表导入")
    @PostMapping("/device/deviceLaunchArea/upload")
    @DefaultVersion
    @RequestLock
    public ResponseObject< List<DeviceLaunchAreaExportResultDto>> uploadDeviceLaunchArea(@RequestParam("file") MultipartFile file,
                                                                    @RequestParam(value = "productId",required = false) Integer productId) throws Exception {
        List<List<Object>> originData = ImportExcelUtils.parse(file.getInputStream(), file.getOriginalFilename());
        return success(deviceLaunchAreaAssignService.importExcel(convertLaunchArea(originData)));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "仓库扫码设备列表导出", consumes = "application/json")
    @PostMapping("/device/deviceStock/export")
    public void deviceStock(@RequestBody @Valid RequestObject<ExportHelper<DeviceStockQueryDto, String>> requestObject, HttpServletResponse response) {
        ExportHelper<DeviceStockQueryDto, String> helper = requestObject.getData();
        Pageable<DeviceStockQueryDto> pageable = getPageable();
        pageable.setQuery(Objects.isNull(helper.getQuery()) ? new DeviceStockQueryDto() : helper.getQuery());
        pageable.getQuery().setIds(helper.getIds());
        pageable.getQuery().setIsDeleted(DeleteStatus.NOT_DELETED.getCode());
        if (Objects.isNull(pageable.getQuery().getSweepCodeStatus())){
            pageable.getQuery().setSweepCodeStatus(DeviceSweepCodeStatus.PENDING_CODE.getCode());    //状态默认为“待扫码”
        }
        pageable.getQuery().setOperatorAccountId(null);
        Page<DeviceShowDto> result = deviceStockService.listPage(pageable);
        export("仓库扫码设备列表", helper, result.getRecords(), response);
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "库存列表导出", consumes = "application/json")
    @PostMapping("/device/deviceStockList/export")
    public void deviceStockExport(@RequestBody @Valid RequestObject<ExportHelper<DeviceStockQueryDto, String>> requestObject, HttpServletResponse response) {
        ExportHelper<DeviceStockQueryDto, String> helper = requestObject.getData();
        Pageable<DeviceStockQueryDto> pageable = getPageable();
        pageable.setQuery(Objects.isNull(helper.getQuery()) ? new DeviceStockQueryDto() : helper.getQuery());
        pageable.getQuery().setIds(helper.getIds());
        pageable.getQuery().setIsDeleted(DeleteStatus.NOT_DELETED.getCode());
        if (Objects.isNull(pageable.getQuery().getSweepCodeStatus())){
            pageable.getQuery().setSweepCodeStatus(DeviceSweepCodeStatus.To_Be_But_Bf_Stock.getCode());    //状态默认为“已入库”
        }
        pageable.getQuery().setOperatorAccountId(null);
        Page<DeviceShowDto> result = deviceStockService.StockListPage(pageable);
        export("库存列表", helper, result.getRecords(), response);
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "仓库入库记录列表导出", consumes = "application/json")
    @PostMapping("/device/deviceStockPut/export")
    public void deviceStockPut(@RequestBody @Valid RequestObject<ExportHelper<DeviceStockQueryDto, String>> requestObject, HttpServletResponse response) {
        ExportHelper<DeviceStockQueryDto, String> helper = requestObject.getData();
        Pageable<DeviceStockQueryDto> pageable = getPageable();
        pageable.setQuery(Objects.isNull(helper.getQuery()) ? new DeviceStockQueryDto() : helper.getQuery());
        pageable.getQuery().setIds(helper.getIds());
        pageable.getQuery().setIsDeleted(DeleteStatus.NOT_DELETED.getCode());
        pageable.getQuery().setOperatorAccountId(null);
        Page<DeviceShowDto> result = deviceStockService.putListPage(pageable);
        export("仓库入库记录列表", helper, result.getRecords(), response);
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "仓库出库记录列表导出", consumes = "application/json")
    @PostMapping("/device/deviceStockOut/export")
    public void deviceStockOut(@RequestBody @Valid RequestObject<ExportHelper<DeviceStockQueryDto, String>> requestObject, HttpServletResponse response) {
        ExportHelper<DeviceStockQueryDto, String> helper = requestObject.getData();
        Pageable<DeviceStockQueryDto> pageable = getPageable();
        pageable.setQuery(Objects.isNull(helper.getQuery()) ? new DeviceStockQueryDto() : helper.getQuery());
        pageable.getQuery().setIds(helper.getIds());
        pageable.getQuery().setIsDeleted(DeleteStatus.NOT_DELETED.getCode());
        pageable.getQuery().setOperatorAccountId(null);
        Page<DeviceShowDto> result = deviceStockService.outListPage(pageable);
        export("仓库出库记录列表", helper, result.getRecords(), response);
    }



    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "用户列表导出", consumes = "application/json")
    @PostMapping("/user/normal/export")
    public void userNormal(@RequestBody @Valid RequestObject<ExportHelper<UserForQueryDto, Integer>> requestObject, HttpServletResponse response) {
        ExportHelper<UserForQueryDto, Integer> helper = requestObject.getData();
        Pageable<UserForQueryDto> pageable = getPageable();
        pageable.setQuery(Objects.isNull(helper.getQuery()) ? new UserForQueryDto() : helper.getQuery());
        pageable.getQuery().setStatus(UserStatus.NORMAL.getCode());
        pageable.getQuery().setIds(helper.getIds());
        Page<UserForListDto> result = userService.page(pageable);
        export("用户列表", helper, result.getRecords(), response);
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "告警设备列表导出", consumes = "application/json")
    @PostMapping("/device/deviceAlarm/export")
    public void deviceAlarm(@RequestBody @Valid RequestObject<ExportHelper<DeviceAlarmQueryDto, Integer>> requestObject, HttpServletResponse response) {
        ExportHelper<DeviceAlarmQueryDto, Integer> helper = requestObject.getData();
        DeviceAlarmQueryDto query = Objects.isNull(helper.getQuery()) ? new DeviceAlarmQueryDto() : helper.getQuery();
        query.setCurrentPage(1);
        query.setPageSize(getDefaultExportSize());
        query.setIds(helper.getIds());
        List<DeviceAlramInfoDto> result = deviceAlarmService.listPage(query);
        export("告警设备列表", helper, result, response);
    }

//    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
//    @ApiOperation(value = "设备列表导出", consumes = "application/json")
//    @PostMapping("/device/device/export")
//    public void device(@RequestBody @Valid RequestObject<ExportHelper<DeviceQueryDto, String>> requestObject, HttpServletResponse response) {
//        ExportHelper<DeviceQueryDto, String> helper = requestObject.getData();
//        Pageable<DeviceQueryDto> pageable = getPageable();
//        pageable.setQuery(Objects.isNull(helper.getQuery()) ? new DeviceQueryDto() : helper.getQuery());
//        pageable.getQuery().setIds(helper.getIds());
//        pageable.getQuery().setIsDeleted(DeleteStatus.NOT_DELETED.getCode());
//        pageable.getQuery().setAccessableOwnerIds(sysUserService.resolveSysUserAllSubAdminIds(sysUserService.getCurrentUserOwner()));
//        pageable.getQuery().setOperatorAccountId(null);
//        Page<DeviceShowDto> result = deviceService.listPage(pageable);
//        export("设备列表", helper, result.getRecords(), response);
//    }





//    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
//    @ApiOperation(value = "收费模式列表导出", consumes = "application/json")
//    @PostMapping("/product/productServiceMode/export")
//    public void productServiceMode(@RequestBody @Valid RequestObject<ExportHelper<ProductServiceListQuerytDto, Integer>> requestObject, HttpServletResponse response) {
//        ExportHelper<ProductServiceListQuerytDto, Integer> helper = requestObject.getData();
//        Pageable<ProductServiceListQuerytDto> pageable = getPageable();
//        pageable.setQuery(Objects.isNull(helper.getQuery()) ? new ProductServiceListQuerytDto() : helper.getQuery());
//        pageable.getQuery().setIds(helper.getIds());
//        Page<ProductServicecModeListDto> result = productServiceModeService.getProductServiceModeListDtoPage(pageable);
//        export("收费模式列表", helper, result.getRecords(), response);
//    }
//
//    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
//    @ApiOperation(value = "订单列表导出", consumes = "application/json")
//    @PostMapping("/order/orderBase/export")
//    public void orderBase(@RequestBody @Valid RequestObject<ExportHelper<OrderQueryDto, String>> requestObject, HttpServletResponse response) {
//        ExportHelper<OrderQueryDto, String> helper = requestObject.getData();
//        OrderQueryDto query = Objects.isNull(helper.getQuery()) ? new OrderQueryDto() : helper.getQuery();
//        query.setCurrentPage(1);
//        query.setPagesize(getDefaultExportSize());
//        if (query.getCurrentId() == null) {
//            SysUser sysUser = sysUserService.getCurrentUserOwner();
//            query.setCurrentId(sysUser.getId());
//        }
//        query.setIds(helper.getIds());
//        query.setOperatorAccountId(null);
//        Pageable<OrderQueryDto> pageable = new Pageable<>();
//        pageable.setCurrent(query.getCurrentPage());
//        pageable.setSize(query.getPagesize());
//        pageable.setQuery(query);
//        Page<OrderListDto> result = orderBaseService.getOrderListDtoPage(pageable);
//        export("订单列表", helper, result.getRecords(), response);
//    }
//
//
//
//
//
//
//
//    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
//    @ApiOperation(value = "用户黑名单列表导出", consumes = "application/json")
//    @PostMapping("/user/black/export")
//    public void userBlack(@RequestBody @Valid RequestObject<ExportHelper<UserForQueryDto, Integer>> requestObject, HttpServletResponse response) {
//        ExportHelper<UserForQueryDto, Integer> helper = requestObject.getData();
//        Pageable<UserForQueryDto> pageable = getPageable();
//        pageable.setQuery(Objects.isNull(helper.getQuery()) ? new UserForQueryDto() : helper.getQuery());
//        pageable.getQuery().setStatus(UserStatus.BLACK.getCode());
//        pageable.getQuery().setIds(helper.getIds());
//        Page<UserForListDto> result = userService.page(pageable);
//        export("用户黑名单列表", helper, result.getRecords(), response);
//    }
//
//
//    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
//    @ApiOperation(value = "充值记录列表导出", consumes = "application/json")
//    @PostMapping("/wallet/userWalletChargeOrder/export")
//    public void userWalletChargeOrder(@RequestBody @Valid RequestObject<ExportHelper<UserWalletChargeOrderQueryDto, String>> requestObject, HttpServletResponse response) {
//        ExportHelper<UserWalletChargeOrderQueryDto, String> helper = requestObject.getData();
//        Pageable<UserWalletChargeOrderQueryDto> pageable = getPageable();
//        pageable.setQuery(Objects.isNull(helper.getQuery()) ? new UserWalletChargeOrderQueryDto() : helper.getQuery());
//        pageable.getQuery().setIds(helper.getIds());
//        Page<UserWalletChargeListDto> result = userWalletChargeOrderService.list(pageable);
//        export("充值记录列表", helper, result.getRecords(), response);
//    }
//
//    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
//    @ApiOperation(value = "退款列表导出", consumes = "application/json")
//    @PostMapping("/refund/export")
//    public void refundExport(@RequestBody @Valid RequestObject<ExportHelper<RefundListQueryDto, String>> requestObject, HttpServletResponse response) {
//        ExportHelper<RefundListQueryDto, String> helper = requestObject.getData();
//        Pageable<RefundListQueryDto> pageable = getPageable();
//        pageable.setQuery(Objects.isNull(helper.getQuery()) ? new RefundListQueryDto() : helper.getQuery());
//        pageable.getQuery().setSysUserIds(sysUserService.resolveSysUserAllSubIds(sysUserService.getCurrentUser()));
//        pageable.getQuery().setRefundNos(helper.getIds());
//        Page<RefundInfoDto> result = refundApplyService.list(pageable);
//        export("退款列表", helper, result.getRecords(), response);
//    }

    private <T> Pageable<T> getPageable() {
        Pageable<T> pageable = new Pageable<>();
        pageable.setSize(getDefaultExportSize());
        return pageable;
    }

    private void export(String filename, ExportHelper<?, ?> helper, List<?> data, HttpServletResponse response) {
        //PoiExcelUtils.exportExcel(filename, null, helper.getTitles(), helper.getProperties(), data, response);
        try {
            JxlExcelUtils.listToExcel(data, helper.getTitles(), helper.getProperties(), filename, response);
        } catch (ExcelException e) {
            logger.error(e.getMessage());
        }
    }

    private List<AgentExcelTemplate> convertAgent(List<List<Object>> originData) {
        if (CollectionUtils.isEmpty(originData)) {
            LeaseException.throwSystemException(LeaseExceEnums.EXCEL_NO_DATA);
        }
        return originData.stream().filter(this::isValidAgent).map(list ->
                new AgentExcelTemplate(String.valueOf(list.get(0)), String.valueOf(list.get(1)),String.valueOf(list.get(2))
                        ,String.valueOf(list.get(3)),String.valueOf(list.get(4)),String.valueOf(list.get(5)))).collect(Collectors.toList());
    }

    private boolean isValidAgent(List<Object> list) {
        return CollectionUtils.isNotEmpty(list) && list.size() == 6 && Objects.nonNull(list.get(0))
                && Objects.nonNull(list.get(1)) && Objects.nonNull(list.get(3))&& Objects.nonNull(list.get(4))&& Objects.nonNull(list.get(5));
    }

    private List<DeviceLaunchAreaExcelTemplate> convertLaunchArea(List<List<Object>> originData) {
        if (CollectionUtils.isEmpty(originData)) {
            LeaseException.throwSystemException(LeaseExceEnums.EXCEL_NO_DATA);
        }
        return originData.stream().filter(this::isValidLaunchArea).map(list ->
                new DeviceLaunchAreaExcelTemplate(String.valueOf(list.get(0)), String.valueOf(list.get(1)),String.valueOf(list.get(2)),String.valueOf(list.get(3)),String.valueOf(list.get(4)))).collect(Collectors.toList());
    }

    private boolean isValidLaunchArea(List<Object> list) {
        return CollectionUtils.isNotEmpty(list) && list.size() == 5 && Objects.nonNull(list.get(0))
                && Objects.nonNull(list.get(2)) && Objects.nonNull(list.get(3))&& Objects.nonNull(list.get(4));
    }

    private List<ProductCategoryExcelTemplate> convertProduct(List<List<Object>> originData) {
        if (CollectionUtils.isEmpty(originData)) {
            LeaseException.throwSystemException(LeaseExceEnums.EXCEL_NO_DATA);
        }
        return originData.stream().filter(this::isValidProduct).map(list ->
                new ProductCategoryExcelTemplate(String.valueOf(list.get(0)), String.valueOf(list.get(1)), String.valueOf(list.get(2)))).collect(Collectors.toList());
    }

    private boolean isValidProduct(List<Object> list) {
        return CollectionUtils.isNotEmpty(list) && list.size() == 3 && Objects.nonNull(list.get(0))
                && Objects.nonNull(list.get(1))&& Objects.nonNull(list.get(2));
    }


    private List<DeviceStockTemplate> convertStock(List<List<Object>> originData) {
        if (CollectionUtils.isEmpty(originData)) {
            LeaseException.throwSystemException(LeaseExceEnums.EXCEL_NO_DATA);
        }
        return originData.stream().filter(this::isValidStock).map(list -> new DeviceStockTemplate(String.valueOf(list.get(0)), String.valueOf(list.get(1))
                ,String.valueOf(list.get(2)),String.valueOf(list.get(3)))).collect(Collectors.toList());
    }

    private boolean isValidStock(List<Object> list) {
        return CollectionUtils.isNotEmpty(list) && list.size() == 4 && Objects.nonNull(list.get(0))
                && Objects.nonNull(list.get(1))&& Objects.nonNull(list.get(2))&& Objects.nonNull(list.get(3));
    }


    private int getDefaultExportSize() {
        return SysConfigUtils.get(CommonSystemConfig.class).getDefaultExportSize();
    }

    private List<DeviceExport> convert2(List<List<Object>> originData) {
        if (CollectionUtils.isEmpty(originData)) {
            LeaseException.throwSystemException(LeaseExceEnums.EXCEL_NO_DATA);
        }
        return originData.stream().filter(this::isValid2).map(list -> new DeviceExport(String.valueOf(list.get(0)))).collect(Collectors.toList());
    }

    private boolean isValid2(List<Object> list) {
        return CollectionUtils.isNotEmpty(list) && list.size() == 1 && Objects.nonNull(list.get(0));
    }


}
