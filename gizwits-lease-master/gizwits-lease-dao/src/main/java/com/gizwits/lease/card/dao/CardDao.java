package com.gizwits.lease.card.dao;

import com.gizwits.lease.card.entity.Card;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 卡券 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-01-04
 */
public interface CardDao extends BaseMapper<Card> {
    int removeProductLimit(@Param("cardId") String cardId);
    int removeOperatorLimit(@Param("cardId") String cardId);

    List<Card> selectDispatchedCardList(@Param("channel") Integer channel, @Param("sysUserId") Integer sysUserId);
}