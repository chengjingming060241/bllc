package com.gizwits.lease.message.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.lease.message.entity.SysMessageToUser;
import com.gizwits.lease.message.dao.SysMessageToUserDao;
import com.gizwits.lease.message.service.SysMessageToUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统消息用户表 服务实现类
 * </p>
 *
 * @author yinhui
 * @since 2017-08-08
 */
@Service
public class SysMessageToUserServiceImpl extends ServiceImpl<SysMessageToUserDao, SysMessageToUser> implements SysMessageToUserService {

    @Autowired
    private SysMessageToUserDao sysMessageToUserDao;

    @Override
    public String concatRoleName(Integer sysMessageId) {
        return sysMessageToUserDao.concatRoleName(sysMessageId);
    }

    @Override
    public List<Integer> listSysMessageId(Integer userId,Integer userType) {
        return sysMessageToUserDao.listSysMessageId(userId,userType);
    }

    @Override
    public List<Integer> listUnReadSysMessageIds(Integer userId,Integer userType){
            return sysMessageToUserDao.listUnReadSysMessageIds(userId,userType);
    }

    @Override
    public SysMessageToUser selectByMessageId(Integer messageId) {
        return selectOne(new EntityWrapper<SysMessageToUser>().eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("sys_message_id",messageId));
    }
}
