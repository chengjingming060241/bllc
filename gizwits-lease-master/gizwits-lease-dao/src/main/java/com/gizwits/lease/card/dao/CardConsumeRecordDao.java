package com.gizwits.lease.card.dao;

import com.gizwits.lease.card.entity.CardConsumeRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  * 卡券使用次数记录 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-02-08
 */
public interface CardConsumeRecordDao extends BaseMapper<CardConsumeRecord> {

    /**
     * 记录用户使用卡券次数
     */
    int saveUserConsumeCardRecord(@Param("userId") Integer userId, @Param("cardId") String cardId, @Param("limit") Integer limit);

    /**
     * 撤销卡券使用
     */
    void rollbackCardConsumption(@Param("userId") Integer userId, @Param("cardId") String cardId);
}