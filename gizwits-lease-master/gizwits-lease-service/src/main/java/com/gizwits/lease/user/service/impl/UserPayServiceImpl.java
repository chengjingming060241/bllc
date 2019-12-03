package com.gizwits.lease.user.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gizwits.boot.sys.entity.SysUserExt;
import com.gizwits.boot.sys.service.SysUserExtService;
import com.gizwits.lease.constant.*;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.order.entity.OrderBase;
import com.gizwits.lease.order.service.OrderBaseService;
import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.user.entity.UserChargeCardOrder;
import com.gizwits.lease.user.service.UserChargeCardOrderService;
import com.gizwits.lease.user.service.UserPayService;
import com.gizwits.lease.wallet.entity.UserWalletChargeOrder;
import com.gizwits.lease.wallet.service.UserWalletChargeOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserPayServiceImpl implements UserPayService {
	private static Logger loggerOrder = LoggerFactory.getLogger("ORDER_LOGGER");

	@Autowired
	private SysUserExtService sysUserExtService;
	@Autowired
	private UserChargeCardOrderService userChargeCardOrderService;
	@Autowired
	private OrderBaseService orderBaseService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private UserWalletChargeOrderService userWalletChargeOrderService;

	@Override
	public Map<String, Object> prePayForOrder(String orderNo, Integer orderType, User user, String sno) {
		Map<String, Object> map = null;
		//先判断订单类型
		if (orderType.equals(TradeOrderType.CONSUME.getCode())) {//消费订单
			map = prePayForConsumeOrder(sno, user.getId(), orderType, orderNo);
		} else if (orderType.equals(TradeOrderType.CHARGE.getCode())) {//充值订单
			if (Objects.isNull(sno)) {
				map = prePayForChargeOrder(user, orderNo);
			} else {
				map = prePayForChargeOrder(user, orderNo, sno);
			}
		} else if (orderType.equals(TradeOrderType.CARD.getCode())) { // 充值卡订单
			map = prePayForCardOrder(user, orderNo);
		} else {
			loggerOrder.error("订单号：" + orderNo + "的订单的订单类型不存在");
			LeaseException.throwSystemException(LeaseExceEnums.ORDER_TYPE_NOT_EXIST);
		}
		return map;
	}

	private Map<String, Object> prePayForConsumeOrder(String sno, Integer userId, Integer payType, String orderNo) {
		//支付前再次检查订单情况
		Map<String, Object> result = orderBaseService.checkBeforeOrder(sno, ThirdPartyUserType.WEIXIN.getCode());
		if (result == null) {
			LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
		}
		Boolean canRenew = (Boolean) result.get("canRenew");
		OrderBase orderBase = orderBaseService.getOrderBaseByOrderNo(orderNo);
		if (orderBase == null) {
			LeaseException.throwSystemException(LeaseExceEnums.ORDER_DONT_EXISTS);
		}

		//查看该用户是否有使用中的订单
		//        List<OrderBase> unfinishOrderList = orderBaseService.findByUserIdAndStatus(userId, OrderStatus.USING.getCode());
		//        if (unfinishOrderList != null && unfinishOrderList.size() > 0) {
		//            LeaseException.throwSystemException(LeaseExceEnums.HAS_UNFINISH_ORDER);
		//        }

		orderBase.setPayType(payType);
		orderBase.setUtime(new Date());
		orderBaseService.updateById(orderBase);


		Device device = deviceService.getDeviceInfoBySno(sno);
		if (Objects.isNull(device) || DeviceStatus.USING.getCode().equals(device.getWorkStatus()) ||
				DeviceStatus.OFFLINE.getCode().equals(device.getOnlineStatus())) {
			LeaseException.throwSystemException(LeaseExceEnums.DEVICE_INUSING_FAIL);
		}
		//判断设备是否可用被租赁
		deviceService.checkDeviceIsRenting(sno,canRenew);
		//获取设备对应的sysUserExt
		SysUserExt sysUserExt = deviceService.getWxConfigByDeviceId(sno);
		if (Objects.isNull(sysUserExt)) {
			LeaseException.throwSystemException(LeaseExceEnums.DEVICE_NOT_IN_OPERATOR);
		}

		//兼容支付中也可以支付
		if (orderBase.getOrderStatus().equals(OrderStatus.PAYING.getCode())) {
			orderBase.setOrderStatus(OrderStatus.INIT.getCode());
		}
		//修改订单状态为支付中
		orderBaseService.updateOrderStatusAndHandle(orderBase, OrderStatus.PAYING.getCode());
		//保存到map返回到外面使用
		Map<String, Object> map = new HashMap<>();
		map.put("orderNo", orderBase.getOrderNo());
		map.put("fee", orderBase.getAmount());
		map.put("sysUserExt", sysUserExt);
		return map;
	}


	/**
	 * 不带设备序列的充值下单
	 *
	 * @param user
	 * @param orderNo
	 * @return
	 */
	private Map<String, Object> prePayForChargeOrder(User user, String orderNo) {
		//根据openid找到公众号
		SysUserExt sysUserExt =
				sysUserExtService.selectOne(new EntityWrapper<SysUserExt>().eq("wx_id", user.getWxId()));
		if (Objects.isNull(sysUserExt)) {
			LeaseException.throwSystemException(LeaseExceEnums.WEIXIN_ID_IS_NULL);
		}

		UserWalletChargeOrder userWalletChargeOrder = userWalletChargeOrderService.selectById(orderNo);
		if (Objects.isNull(userWalletChargeOrder)) {
			LeaseException.throwSystemException(LeaseExceEnums.CHARGE_ORDER_NOT_EXIST);
		}
		//将订单状态修改为支付中
		userWalletChargeOrderService.updateChargeOrderStatus(orderNo, UserWalletChargeOrderType.PAYING.getCode());

		//保存到map返回到外面使用
		Map<String, Object> map = new HashMap<>();
		map.put("orderNo", userWalletChargeOrder.getChargeOrderNo());
		map.put("fee", userWalletChargeOrder.getFee());
		map.put("sysUserExt", sysUserExt);
		return map;
	}

	/**
	 * 带设备序列号充值下单
	 *
	 * @param user
	 * @param orderNo
	 * @param sno
	 * @return
	 */
	private Map<String, Object> prePayForChargeOrder(User user, String orderNo, String sno) {
		SysUserExt sysUserExt = deviceService.getWxConfigByDeviceId(sno);
		if (Objects.isNull(sysUserExt)) {
			LeaseException.throwSystemException(LeaseExceEnums.WEIXIN_ID_IS_NULL);
		}
		UserWalletChargeOrder userWalletChargeOrder = userWalletChargeOrderService.selectById(orderNo);
		if (Objects.isNull(userWalletChargeOrder)) {
			LeaseException.throwSystemException(LeaseExceEnums.CHARGE_ORDER_NOT_EXIST);
		}

		//将订单状态修改为支付中
		userWalletChargeOrderService.updateChargeOrderStatus(orderNo, UserWalletChargeOrderType.PAYING.getCode());

		//保存到map返回到外面使用
		Map<String, Object> map = new HashMap<>();
		map.put("orderNo", userWalletChargeOrder.getChargeOrderNo());
		map.put("fee", userWalletChargeOrder.getFee());
		map.put("sysUserExt", sysUserExt);
		return map;
	}

	private Map<String, Object> prePayForCardOrder(User user, String orderNo) {
		SysUserExt
				sysUserExt = sysUserExtService.selectOne(new EntityWrapper<SysUserExt>().eq("wx_id", user.getWxId()));
		if (Objects.isNull(sysUserExt)) {
			LeaseException.throwSystemException(LeaseExceEnums.DEVICE_NOT_IN_OPERATOR);
		}
		UserChargeCardOrder cardOrder = userChargeCardOrderService.selectById(orderNo);
		if (Objects.isNull(cardOrder)) {
			LeaseException.throwSystemException(LeaseExceEnums.CHARGE_ORDER_NOT_EXIST);
		}

		//将充值卡订单状态修改为支付中
		userChargeCardOrderService.updateCardOrderStatus(orderNo, OrderStatus.PAYING.getCode());

		//保存到map返回到外面使用
		Map<String, Object> map = new HashMap<>();
		map.put("orderNo", cardOrder.getOrderNo());
		map.put("fee", cardOrder.getMoney());
		map.put("sysUserExt", sysUserExt);
		return map;
	}
}
