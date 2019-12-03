package com.gizwits.lease.order.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gizwits.lease.order.dto.OrderBaseListDto;
import com.gizwits.lease.order.dto.OrderQueryDto;
import com.gizwits.lease.order.dto.ServiceOrderAmountDto;
import com.gizwits.lease.order.entity.OrderBase;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
  * 订单表 Mapper 接口
 * </p>
 *
 * @author rongmc
 * @since 2017-06-30
 */
public interface OrderBaseDao extends BaseMapper<OrderBase> {
    OrderBase findByTradeNo(String tradeNo);

    Map<String,Number> findForStatOrder(@Param("sno") String sno, @Param("date") Date date, @Param("status")List<Integer> status);

    List<OrderBase> findByUserIdAndStatus(@Param("userId")Integer userId, @Param("orderStatus")Integer orderStatus);

    OrderBase findByDeviceIdAndStatus(@Param("deviceId")String deviceId, @Param("orderStatus")Integer orderStatus);

    Integer getOrderCount(@Param("sysUserId") Integer sysUserId, @Param("productId") Integer productId, @Param("fromDate") Date fromDate,@Param("toDate") Date toDate,@Param("status") List<Integer> orderStatus);

    List<OrderBaseListDto> listPage(OrderQueryDto orderQueryDto);

    List<OrderBaseListDto> appListPage(OrderQueryDto orderQueryDto);

    Integer findTotalSize(OrderQueryDto orderQueryDto);

    BigDecimal countTotalMoney( OrderQueryDto orderQueryDto);

    Integer findAppListTotalSize(OrderQueryDto orderQueryDto);

    List<OrderBase> listReadyForShareBenefitOrder(@Param("lastExecuteTime") Date lastExecuteTime, @Param("sno") String sno);


    Integer countUsingOrderByUserIdAndProductId(@Param("userId")Integer userId, @Param("productId")Integer productId);

    Double appListPaySum(OrderQueryDto orderQueryDto);

    Date earliestOrderTime();

//    OrderBase findUsingOrderByUserIdAndProductId(@Param("userId")Integer userId, @Param("productId")Integer productId);

    /**
     *
     * @param orderNo
     * @param refundMoney 需要减少的金额refund=refund-refundMoney
     * @param refundVersion 当前版本号
     * @return
     */
    int addRefundMoney(@Param("orderNo") String orderNo,
                       @Param("refundMoney") BigDecimal refundMoney,
                       @Param("refundVersion") Integer refundVersion);


    int updateAmountByCardDiscount(@Param("orderNo") String orderNo,
                                   @Param("cardId") String cardId,
                                   @Param("cardCode") String cardCode,
                                   @Param("cardDiscount") BigDecimal cardDiscount);

    int updateByRemovingCardDiscount(@Param("orderNo") String orderNo);

    /**
     * 查询设备的最新订单
     * @param orderStatusList 过滤订单状态
     * @param userId 过滤订单用户
     * @param snoList 过滤设备
     * @return
     */
    List<OrderBase> selectDeviceLastOrder(@Param("orderStatusList") List<Integer> orderStatusList, @Param("userId") Integer userId, @Param("snoList") List<String> snoList);


    /**
     * 更新结算订单的分润批次
     */
    int updateShareProfitBatch(@Param("endTime") Date endTime, @Param("batchId") Integer batchId);

    /**
     * 查询分润批次的订单
     */
    List<ServiceOrderAmountDto> selectByShareProfitBatch(@Param("batchId") Integer batchId);

    /**
     * 获取order_base表中不重复的ownerId
     */
    Set<Integer> getDiffOwnerId();
}