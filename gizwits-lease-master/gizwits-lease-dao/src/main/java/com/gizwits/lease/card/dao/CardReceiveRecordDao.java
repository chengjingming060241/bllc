package com.gizwits.lease.card.dao;

import com.gizwits.lease.card.entity.CardReceiveRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  * 卡券领取次数记录 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-02-06
 */
public interface CardReceiveRecordDao extends BaseMapper<CardReceiveRecord> {

    int saveUserReceiveCardRecord(@Param("userId") Integer userId, @Param("cardId") String cardId, @Param("limit") Integer limit);

}