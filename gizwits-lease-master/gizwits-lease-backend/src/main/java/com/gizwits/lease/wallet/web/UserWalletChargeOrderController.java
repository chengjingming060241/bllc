package com.gizwits.lease.wallet.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.lease.order.dto.ChargeOrderDto;
import com.gizwits.lease.wallet.dto.DepositListDto;
import com.gizwits.lease.wallet.dto.DepositQueryDto;
import com.gizwits.lease.wallet.dto.UserWalletChargeListDto;
import com.gizwits.lease.wallet.dto.UserWalletChargeOrderQueryDto;
import com.gizwits.lease.wallet.entity.UserWalletChargeOrder;
import com.gizwits.lease.wallet.service.UserWalletChargeOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 * 用户钱包充值单表 前端控制器
 * </p>
 *
 * @author yinhui
 * @since 2017-07-31
 */
@RestController
@EnableSwagger2
@Api(description = " 钱包充值单接口")
@RequestMapping("/wallet/userWalletChargeOrder")
public class UserWalletChargeOrderController extends BaseController{

    @Autowired
    private UserWalletChargeOrderService userWalletChargeOrderService;

    @ApiImplicitParam(paramType = "header",name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "钱包充值单列表",notes = "钱包充值单列表",consumes = "application/json")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public ResponseObject<Page<UserWalletChargeListDto>> list(@RequestBody @Valid RequestObject<Pageable<UserWalletChargeOrderQueryDto>> requestObject){

        return success(userWalletChargeOrderService.list(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header",name = Constants.TOKEN_HEADER_NAME)

    @ApiOperation(value = "押金列表",notes = "押金列表",consumes = "application/json")
    @RequestMapping(value = "/depositList",method = RequestMethod.POST)
    public ResponseObject<Page<DepositListDto>> depositList(@RequestBody @Valid RequestObject<Pageable<DepositQueryDto>> requestObject){

        return success(userWalletChargeOrderService.listDeposit(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header",name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "押金详情",notes = "押金详情",consumes = "application.json")
    @RequestMapping(value = "depositDetail",method = RequestMethod.POST)
    public ResponseObject<DepositListDto> depositDetail(@RequestBody @Valid RequestObject<String> requestObject){

        return success(userWalletChargeOrderService.depositDetail(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header",name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "退款信息",notes = "退款信息",consumes = "application/json")
    @RequestMapping(value = "/refundInfo",method = RequestMethod.POST)
    public ResponseObject refundInfo(@RequestBody @Valid RequestObject<String> requestObject){

        return success(userWalletChargeOrderService.refundInfo(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header",name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "退款",notes = "退款",consumes = "application/json")
    @RequestMapping(value = "/refund",method = RequestMethod.POST)
    public ResponseObject refund(@RequestBody @Valid RequestObject<String> requestObject) {
        userWalletChargeOrderService.refund(requestObject.getData());
        return success();
    }

    @ApiOperation(value = "创建充值单",notes = "创建充值单",consumes = "application/json")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseObject<UserWalletChargeOrder> create(@RequestBody @Valid RequestObject<ChargeOrderDto> requestObject){

        return success(userWalletChargeOrderService.createRechargeOrder(requestObject.getData()));

    }




}
