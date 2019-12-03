package com.gizwits.lease.app.web;

import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.lease.app.utils.BrowserUtil;
import com.gizwits.lease.order.dto.AlipayOrderDto;
import com.gizwits.lease.order.dto.PrePayDto;
import com.gizwits.lease.trade.service.TradeAlipayService;
import com.gizwits.lease.user.service.UserAlipayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by kufufu on 2017/10/25.
 */
@EnableSwagger2
@Api(value = "支付宝支付,支付宝退款")
@RestController
@RequestMapping(value = "/app/aliPay")
public class AliPayController extends BaseController {

    @Autowired
    private UserAlipayService userAlipayService;

    @Autowired
    private TradeAlipayService tradeAlipayService;

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "返回app支付签名", consumes = "application/json")
    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    public ResponseObject getSignByAlipaysOrder(@RequestBody @Valid RequestObject<AlipayOrderDto> requestObject) {
        return success(userAlipayService.signByAlipaysOrder(requestObject.getData()));
    }

    @ApiOperation(value = "支付宝支付",notes = "支付宝支付",consumes = "application/json")
    @RequestMapping(value = "/pay",method = RequestMethod.POST)
    public ResponseObject pay(@RequestBody @Valid RequestObject<PrePayDto> requestObject, HttpServletRequest request){

        return success(tradeAlipayService.prePay(requestObject.getData(), BrowserUtil.getUserBrowserType(request)));
    }

    @ApiOperation(value = "支付宝订单支付状态查询接口", notes = "此接口主要是订单支付状态查询")
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @RequestMapping(value = "/queryStatus", method = RequestMethod.POST)
    public ResponseObject prePayQueryStatus(@RequestBody @Valid RequestObject<String> requestObject) {
        return success(userAlipayService.queryOrderPayStatus(requestObject.getData()));
    }

    /**
     * 支付宝应用网关验签
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/verify", method = RequestMethod.POST, produces = "text/xml;charset=UTF-8")
    public void verify(HttpServletRequest request, HttpServletResponse response) throws Exception{
        userAlipayService.verifyAlipay(response, request);
    }

    /**
     * 支付宝支付回调
     * @param request
     * @param resp
     */
    @RequestMapping(value = "/notify",method = RequestMethod.POST)
    public void alipayNotify(HttpServletRequest request, HttpServletResponse resp) {
        tradeAlipayService.alipayNotify(request,resp);
    }

    /**
     * 获取支付宝用户信息
     * @param appId
     * @param scope
     * @param state
     * @param authCode
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/userinfo", method = RequestMethod.GET, produces = "text/xml;charset=UTF-8")
    public void getAlipayCode(@RequestParam("app_id") String appId,
                              @RequestParam("scope") String scope,
                              @RequestParam("state") String state,
                              @RequestParam("auth_code") String authCode,HttpServletResponse response) throws Exception{
        userAlipayService.getAndSaveUserinfo(state,authCode,response);
    }


}
