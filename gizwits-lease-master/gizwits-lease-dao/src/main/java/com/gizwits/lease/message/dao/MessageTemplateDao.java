package com.gizwits.lease.message.dao;

import com.gizwits.lease.message.entity.MessageTemplate;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <p>
  * 消息模版表 Mapper 接口
 * </p>
 *
 * @author yinhui
 * @since 2018-01-03
 */
public interface MessageTemplateDao extends BaseMapper<MessageTemplate> {

     /**查询依靠数据点上报的消息*/
     List<String> getTriggerByOnlyProductId(@Param("productId") Integer productId);
}