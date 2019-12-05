package com.gizwits.lease.message.dao;

import com.gizwits.lease.message.entity.FeedbackUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gizwits.lease.message.entity.dto.FeedbackQueryDto;
import com.gizwits.lease.message.vo.FeedbackListVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
  * 问题反馈表 Mapper 接口
 * </p>
 *
 * @author yinhui
 * @since 2017-07-26
 */
@Repository
public interface FeedbackUserDao extends BaseMapper<FeedbackUser> {


    List<FeedbackListVo> selectFeedBack(FeedbackQueryDto queryDto);

    Integer selectFeedBackCount(FeedbackQueryDto queryDto);

}