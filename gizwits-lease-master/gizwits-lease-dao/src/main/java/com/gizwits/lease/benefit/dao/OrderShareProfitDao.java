package com.gizwits.lease.benefit.dao;

import com.gizwits.lease.benefit.dto.BenefitResultListDto;
import com.gizwits.lease.benefit.dto.BenefitResultQueryDto;
import com.gizwits.lease.benefit.dto.OrderShareProfitSumDto;
import com.gizwits.lease.benefit.entity.OrderShareProfit;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
  * 订单的分润信息 Mapper 接口
 * </p>
 *
 * @author yinhui
 * @since 2018-05-05
 */
public interface OrderShareProfitDao extends BaseMapper<OrderShareProfit> {
    /**
     * orderShareProfit.getShareProfitLevel(),
     *  @Param("level")Integer level,
     *    and share_profit_level = #{level}
     * @param billNo
     * @return
     */

    OrderShareProfitSumDto sumShareProfitByBill(@Param("billNo") String billNo);

    List<OrderShareProfit> selectWaitToShareProfit(@Param("orderNo") String orderNo,

                                                   @Param("user") Integer user,
                                                   @Param("ctime")Date ctime);

    int updateShareSuccess(@Param("orderNos") List<String> orderNos,  @Param("user") Integer user, @Param("tradeNo") String tradeNo);

    int updateOrderShareProfitToShare(@Param("orderNo")String orderNo);

     List<Integer> queryAllUserIdsByLeve(@Param("level") Integer level);

     int updateOrderShareProfitToShareByLevel(@Param("userId") Integer userId);

     int updateOrderShareProfitToGenerate(@Param("orderNos") List<String> orderNos,@Param("user") Integer user);

     List<BenefitResultListDto> selectPage(BenefitResultQueryDto queryDto);

     int selectTotal(BenefitResultQueryDto queryDto);

     List<String> selectOrderNos(@Param("userId")Integer userId,@Param("startTime") Date startTime,@Param("now") Date now);

}