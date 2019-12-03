package com.gizwits.lease.order.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.lease.RequestLock;
import com.gizwits.lease.constant.OrderStatus;
import com.gizwits.lease.constant.RefundStatus;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.order.dto.OrderListPageDto;
import com.gizwits.lease.order.dto.OrderQueryDto;
import com.gizwits.lease.order.entity.OrderBase;
import com.gizwits.lease.order.entity.dto.EarlyEndDto;
import com.gizwits.lease.order.service.OrderBaseService;

import com.gizwits.lease.order.vo.AppOrderVo;
import com.gizwits.lease.refund.entity.RefundApply;
import com.gizwits.lease.refund.service.RefundApplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author rongmc
 * @since 2017-06-30
 */
@RestController
@EnableSwagger2
@Api(description = "订单接口")
@RequestMapping("/order/orderBase")
public class OrderBaseController extends BaseController {
    protected Logger logger = LoggerFactory.getLogger("ORDER_LOGGER");
    @Autowired
    private OrderBaseService orderBaseService;
    @Autowired
    private RefundApplyService refundApplyService;
    @Autowired
    private SysUserService sysUserService;

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "分页列表", notes = "分页列表", consumes = "application/json")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseObject<OrderListPageDto> list(@RequestBody @Valid RequestObject<OrderQueryDto> requestObject) {
        OrderQueryDto queryDto = requestObject.getData();
        if (queryDto == null) {
            queryDto = new OrderQueryDto();
            requestObject.setData(queryDto);
        }
        if (null == queryDto.getCurrentId() && null == queryDto.getOperatorAccountId() ) {
            SysUser sysUser = sysUserService.getCurrentUserOwner();
            queryDto.setCurrentId(sysUser.getId());
        }
        OrderListPageDto page = orderBaseService.getOrderListDtoPage(queryDto);
        return success(page);
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "运营商下的订单列表", consumes = "application/json")
    @PostMapping("/listByOperator")
    public ResponseObject<OrderListPageDto> listByOperator(@RequestBody RequestObject<OrderQueryDto> requestObject) {
        return success(orderBaseService.getOrderListDtoPage(requestObject.getData()));
    }


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "订单详情", notes = "订单详情", consumes = "application/json")
    @RequestMapping(value = "/orderDetail", method = RequestMethod.POST)
    public ResponseObject orderDetail(@RequestBody @Valid ResponseObject<String> responseObject) {
        OrderBase orderBase = orderBaseService.getOrderBaseByOrderNo(responseObject.getData());
        return success(orderBaseService.orderDetail(orderBase));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "App端订单详情", notes = "订单详情", consumes = "application/json")
    @RequestMapping(value = "/orderAppDetail", method = RequestMethod.POST)
    public ResponseObject orderAppDetail(@RequestBody @Valid ResponseObject<String> responseObject) {
        OrderBase orderBase = orderBaseService.getOrderBaseByOrderNo(responseObject.getData());
        return success(orderBaseService.orderAppDetail(orderBase));
    }

    @ApiOperation(value = "提前结束订单", notes = "提前结束订单", consumes = "application/json")
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @RequestMapping(value = "earlyEnd", method = RequestMethod.POST)
    public ResponseObject<AppOrderVo> earlyEnd(@RequestBody @Valid RequestObject<EarlyEndDto> requestObject) {
        EarlyEndDto earlyEndDto = requestObject.getData();
        earlyEndDto.setOpenid(null);
        orderBaseService.earlyEnd(earlyEndDto);
        return success();
    }

    @ApiOperation(value = "将异常订单变为已完成", notes = "将异常订单变为已完成", consumes = "application/json")
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @RequestMapping(value = "finish", method = RequestMethod.POST)
    @RequestLock
    public ResponseObject finish(@RequestBody @Valid RequestObject<String> requestObject) {
        OrderBase orderBase = orderBaseService.selectById(requestObject.getData());
        if (!orderBase.getOrderStatus().equals(OrderStatus.ABNORMAL.getCode())) {
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_STATUS_ERROR);
        }
        // 如果有非审核不通过的退款单，则不能标记为已完成
        int count = refundApplyService
                .selectCount(new EntityWrapper<RefundApply>().eq("order_no", orderBase.getOrderNo()).ne("status", RefundStatus.NO_PASS.getCode()));
        if (count > 0) {
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_STATUS_ERROR);
        }
        orderBase.setServiceEndTime(new Date());
        orderBaseService.updateOrderStatusAndHandle(orderBase, OrderStatus.FINISH.getCode());
        return success();
    }

}
