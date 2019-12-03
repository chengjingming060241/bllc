package com.gizwits.lease.order.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.gizwits.boot.dto.Pageable;

import com.gizwits.lease.benefit.entity.OrderShareProfit;
import com.gizwits.lease.benefit.entity.ShareProfitBatch;
import com.gizwits.lease.device.vo.DeviceUsingVo;
import com.gizwits.lease.enums.OrderAbnormalReason;
import com.gizwits.lease.order.dto.*;


import com.gizwits.lease.order.dto.OrderAppDetailDto;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.order.dto.OrderDetailDto;
import com.gizwits.lease.order.dto.OrderListDto;
import com.gizwits.lease.order.dto.OrderQueryByMobileDto;
import com.gizwits.lease.order.dto.OrderQueryDto;
import com.gizwits.lease.order.dto.PageOrderAppList;
import com.gizwits.lease.order.dto.PayOrderDto;
import com.gizwits.lease.order.dto.WXOrderListDto;
import com.gizwits.lease.order.dto.WXOrderQueryDto;

import com.gizwits.lease.order.dto.*;


import com.gizwits.lease.order.entity.OrderBase;
import com.gizwits.lease.order.entity.dto.EarlyEndDto;
import com.gizwits.lease.order.vo.AppOrderDetailVo;
import com.gizwits.lease.order.vo.AppOrderVo;
import com.gizwits.lease.user.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author rongmc
 * @since 2017-06-30
 */
public interface OrderBaseService extends IService<OrderBase> {

	OrderBase getOrderBaseByTradeNo(String tradeNo);

	OrderBase getDeviceLastUsingOrder(String deviceId);

	OrderBase getDeviceLastOrderByStatus(String deviceId, Integer status);

    OrderBase getDeviceLastOrderEndByJob(String sno);

    OrderBase getOrderBaseByOrderNo(String orderNo);

	/**
	 * 分页信息
	 * @param orderQueryDto
	 * @return
	 */
	OrderListPageDto getOrderListDtoPage( OrderQueryDto orderQueryDto) ;

	Page<OrderListDto> getOrderListDtoPage(Pageable<OrderQueryDto> pageable);

	/**
	 * 分页信息
	 * @param orderQueryDto
	 * @return
	 */
	PageOrderAppList<OrderListDto> getOrderAppListDtoPage(OrderQueryDto orderQueryDto);

	/**
	 * 获得订单详情信息
	 * @param orderBase
	 * @return
	 */
	OrderDetailDto orderDetail(OrderBase orderBase);

	/**
	 * 获得订单详情信息
	 * @param orderBase
	 * @return
	 */
	OrderAppDetailDto orderAppDetail(OrderBase orderBase);

	/**
	 * 查找待分润的的订单
	 * @param sno
	 * @param lastExecuteTime
	 * @return
	 */
	List<OrderBase> getReadyForShareBenefit(String sno,Date lastExecuteTime);

	/** 改变订单的状态
	 * @param orderBase
	 * @param status
	 * @return
     */
	void updateOrderStatusAndHandle(OrderBase orderBase, Integer status);

	List<OrderShareProfit> updateOrderStatusAndHandleWithTransaction(OrderBase orderBase, Integer status);

	/**
	 * 改变订单的状态
	 * @param orderNo
	 * @param status
	 * @return
	 */
	void updateOrderStatusAndHandle(String orderNo, Integer status);

/*	*
	*微信订单列表
	 * @param pageable
	 * @return
	 */
	Page<WXOrderListDto> getWXOrderListPage(Pageable<WXOrderQueryDto> pageable);

	/**
	 * 支付失败后的显示订单数据
	 * @param deviceSno
	 * @return
	 */
	WXOrderListDto getWxPayingOrder(String deviceSno);

	/**
	 * 删除用户页面展示的订单
	 * @param orderNos
	 */
	void deleteUserShowOrder(List<String> orderNos);

    Map<String, Object> checkBeforeOrder(String sno, Integer userBrowserAgentType);

    /**
	 * 根据用户端串过来的dto创建订单
	 * @param orderDto
	 * @param userBrowserAgentType 用户浏览器类型
	 * @return orderBase实体
     */
	AppOrderVo createOrder(PayOrderDto orderDto, Integer userBrowserAgentType);

    AppOrderVo createOrder(User user, PayOrderDto payOrderDto, Integer userBrowserAgentType);

    OrderBase createOrderForChargeCard(String cardNum, Device device);
	/**
	 * 根据openid查询当前的订单号和设备
	 * @param openid
	 * @return
     */
	AppOrderVo getUsingOrderByUserIdentify(String openid);


    @Transactional
    void earlyEnd(EarlyEndDto earlyEndDto);

    List<OrderBase> findByUserIdAndStatus(Integer userId, Integer orderStatus);

    void resolveLeaseMessage(OrderBase orderBase);

    List<OrderShareProfit> updateOrderShareProfit(String orderNo);

    /**
	 * 用户订单
	 * @param pageable
	 * @return
	 */
	Page<WXOrderListDto> WxOrderListPage(Pageable<OrderQueryByMobileDto> pageable);

	/**
	 * 检查设备和订单,是否满足下单和支付的条件
     * @return
     */
//	Map<String, Object> checkBeforeOrder(String sno,Integer userBrowserAgentType, String userIdentify,String mobile);


	Boolean checkAndUpdateConsumeOrder(String orderNo, Double totalFee);

	/**
	 * 获取使用中的订单，下单成功后获取的订单
	 * @return
     */
	AppOrderVo getForAppOrder(AppUsingOrderDto orderDto);

	/**
	 * 获取使用中订单列表
	 * @param pageable
	 * @return
	 */
	Page<AppOrderVo>  getForAppOrderList(Pageable<AppOrderListDto> pageable);


	OrderBase getUsingOrderByOpenid(String sno, String openid);

	/**
	 * 移动用户端订单详情
	 * @param orderNo
	 * @return
     */
	AppOrderDetailVo getOrderDetailForApp(String orderNo);

	/**
	 * 获取用户使用中的设备
	 * @param openid
	 * @return
     */
	List<DeviceUsingVo> getUsingDeviceList(String openid);

	AppOrderVo getOrderDetail(String orderNo);
	AppOrderVo judgeOrder(String orderNo);

	void checkNeedClockCorrect(Device device, String productKey);
	boolean handleNeedClockCorrect(String productKey, Device device, JSONObject realTimeData);

	/**
	 * 处理异常订单
	 */
	void handleAbnormalOrder(OrderBase orderBase, OrderAbnormalReason reason);

	/**
	 * 更新订单卡券优惠金额
	 */
	void updateAmountByCardDiscount(String orderNo, String cardId, String cardCode, BigDecimal cardDiscount);

	/**
	 * 撤销卡券优惠
	 */
	void removeCardDiscount(OrderBase orderBase);

    int updateByShareProfitBatch(ShareProfitBatch shareProfitBatch);

	List<ServiceOrderAmountDto> getOrderListByShareProfitBatch(Integer batchId);
}
