package com.gizwits.lease.winxin.web;

import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.lease.constant.OrderStatus;
import com.gizwits.lease.constant.UserWalletChargeOrderType;
import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.user.service.UserService;
import com.gizwits.lease.utils.uuidUtil;
import com.gizwits.lease.wallet.entity.UserWalletChargeOrder;
import com.gizwits.lease.wallet.service.UserWalletChargeOrderService;
import com.gizwits.lease.winxin.service.WeixinPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Api(value = "微信扫码下单")
@RestController
@RequestMapping("/winXinPay")
public class PayController {
	
	@Autowired
	private WeixinPayService weixinPayService;

	@Autowired
	private UserWalletChargeOrderService userWalletChargeOrderService;

	@Autowired
	private SysUserService sysUserService;

	@ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
	@ApiOperation(value = "获取微信付款二维码链接", consumes = "application/json")
	@GetMapping("/createNative")
	public Map createNative(){
		//1.获取当前登录用户
		SysUser user = sysUserService.getCurrentUser();
		//2.提取支付日志
		UserWalletChargeOrder userOrder = userWalletChargeOrderService.searchUserWalletChargeOrder(user.getId()+"");
		//3.调用微信支付接口
		if(userOrder!=null){
			return weixinPayService.createNative(userOrder.getChargeOrderNo(), userOrder.getFee().toString());
		}else{
			return new HashMap<>();
		}
	}

	@ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
	@ApiOperation(value = "微信扫码支付回调", consumes = "application/json")
	@GetMapping("/queryPayStatus")
	@ResponseBody
	public ResponseObject queryPayStatus(String out_trade_no){
		ResponseObject result=null;
		int x=0;
        userWalletChargeOrderService.updateChargeOrderStatus(out_trade_no, UserWalletChargeOrderType.PAYING.getCode());
		while(true){
			
			Map<String,String> map = weixinPayService.queryPayStatus(out_trade_no);//调用查询
			if(map==null){
				result=new ResponseObject("false", "支付发生错误");
				break;
			}
			if(map.get("trade_state").equals("SUCCESS")){//支付成功
				result=new ResponseObject("true", "支付成功");
				//修改订单状态
				break;
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			
			x++;
			if(x>=100){				
				result=new ResponseObject("false", "二维码超时");
				break;				
			}
			
		}
		return result;
	}
}
