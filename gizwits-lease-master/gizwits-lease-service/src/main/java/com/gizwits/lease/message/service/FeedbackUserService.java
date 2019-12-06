package com.gizwits.lease.message.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.lease.message.entity.FeedbackUser;
import com.baomidou.mybatisplus.service.IService;
import com.gizwits.lease.message.entity.dto.FeedBackHandleDto;
import com.gizwits.lease.message.entity.dto.FeedbackUserDto;
import com.gizwits.lease.message.entity.dto.FeedbackQueryDto;
import com.gizwits.lease.message.vo.FeedbackListVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 问题反馈表 服务类
 * </p>
 *
 * @author yinhui
 * @since 2017-07-26
 */
public interface FeedbackUserService extends IService<FeedbackUser> {

    /**
     * 分页列表查询
     * @param pageable
     * @return
     */
    Page<FeedbackListVo> page(Pageable<FeedbackQueryDto> pageable);

    Integer countByUserName(Integer userId);

    void insertFeedbackUser(FeedbackUserDto feedbackDto);

    boolean saveUserFeedback(List<MultipartFile> fileList, String sno, String phone, String content, Integer origin);

    Boolean delete(List<Integer> ids);
    /**
     * 处理
     * @param dto
     * @return
     */
    Boolean handle(FeedBackHandleDto dto);
}
