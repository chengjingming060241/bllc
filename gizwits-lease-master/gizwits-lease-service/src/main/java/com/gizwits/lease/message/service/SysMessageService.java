package com.gizwits.lease.message.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.lease.message.entity.SysMessage;
import com.baomidou.mybatisplus.service.IService;
import com.gizwits.lease.message.entity.dto.*;
import com.gizwits.lease.model.DeleteDto;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 系统消息表 服务类
 * </p>
 *
 * @author yinhui
 * @since 2017-07-26
 */
public interface SysMessageService extends IService<SysMessage> {

    /**
     * 分页查询
     * @param pageable
     * @return
     */
     Page<SysMessageListDto> getSysMessagePage(Pageable<SysMessageQueryDto> pageable);

    Page<SysMessageListDto> getMessagePage(Pageable pageable);

    Integer countIsNotRead();

    SysMessage selectById(Integer id);

    DeleteDto delete(List<Integer> ids);

    void clear();

    /**
     * 添加页面所需数据
     * @return
     */
    SysMessageForAddpage listRoles();

    /**
     * 添加
     * @param sysMessageAdd
     * @return
     */
    boolean addSysMessage(SysMessageAdd sysMessageAdd);

    /**
     * 定时发送
     */
    void sendByTime(String date);

    /**
     * 详情
     * @param id
     */
    SysMessageListDto detail(Integer id);

    /**
     * 根据openid查询这个用户相关的公众号曾经发送的消息列表
     * @param
     * @return
     */
//    List<SysMessageListDto> getMessagePage(String openid);

	Integer getUnreadCount(SysMessageCountDto dto);

    /**
     * 发送消息给系统用户
     */
    void sendMessage(String title, String content, List<Integer> recipientSysUserIds);
    void sendMessage(Integer senderSysUserId, String title, String content, List<Integer> recipientSysUserIds);

    Page<SysMessageListDto> getManageMessagePage(Pageable pageable);

    SysMessageListDto getMessageDetail(Integer id);

    Integer countManageIsNotRead();

    void clearManage();
}
