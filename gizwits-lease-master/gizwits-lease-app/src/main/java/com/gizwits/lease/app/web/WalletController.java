package com.gizwits.lease.app.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.lease.constant.WalletEnum;
import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.user.service.UserService;
import com.gizwits.lease.wallet.dto.*;
import com.gizwits.lease.wallet.entity.UserWallet;
import com.gizwits.lease.wallet.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by GaGi on 2017/8/4.
 */
@EnableSwagger2
@Api(value = "提供操作钱包，余额的接口")
@RestController
@RequestMapping(value = "/app/wallet")
public class WalletController extends BaseController {
    @Autowired
    private UserWalletService userWalletService;

    @Autowired
    private RechargeMoneyRuleService rechargeMoneyRuleService;

    @Autowired
    private UserWalletUseRecordService userWalletUseRecordService;

    @Autowired
    private UserWalletChargeOrderService userWalletChargeOrderService;

    @Autowired
    private UserService userService;

   /* @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "当前余额", notes = "当前余额", consumes = "application/json")
    @RequestMapping(value = "/balanceForMobileOrOpenid", method = RequestMethod.POST)
    public ResponseObject<UserWallet> balanceForMobileOrOpenid(@RequestBody @Valid RequestObject<UserWalletDto> requestObject) {
        requestObject.getData().setWalletType(WalletEnum.BALENCE.getCode());
        return success(userWalletService.selectUserWallet(requestObject.getData()));
    }*/

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "当前钱包（押金和余额）", notes = "当前钱包（押金和余额）", consumes = "application/json")
    @RequestMapping(value = "/balance", method = RequestMethod.POST)
    public ResponseObject<List<UserWallet>> balance(@RequestBody @Valid RequestObject requestObject) {
        return success(userWalletService.getAllWallet());
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "余额支付", notes = "余额支付", consumes = "application")
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public ResponseObject pay(@RequestBody @Valid RequestObject<WalletPayDto> requestObject) {
        userWalletService.pay(requestObject.getData());
        return success();
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "充值数据页面", notes = "", consumes = "application/json")
    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    public ResponseObject<RechargeRuleForDetailDto> recharge(@RequestBody @Valid RequestObject requestObject) {
        User user = userService.getCurrentUser();
        RechargeRuleForDetailDto rechargeDto = rechargeMoneyRuleService.getRuleDetail(user.getSysUserId());
        return success(rechargeDto);
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "用户钱包充值记录", notes = "用户钱包充值记录", consumes = "application/json")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseObject<Page<UserWalletUseRecordDto>> list(@RequestBody @Valid RequestObject<Pageable> requestObject) {
        return success(userWalletUseRecordService.listPage(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "押金退款", notes = "押金退款", consumes = "application")
    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public ResponseObject refund(@RequestBody @Valid RequestObject<String> requestObject) {
        userWalletChargeOrderService.refund(requestObject.getData());
        return success();
    }

}
