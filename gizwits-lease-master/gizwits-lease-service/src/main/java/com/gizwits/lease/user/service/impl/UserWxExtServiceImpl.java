package com.gizwits.lease.user.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.lease.user.dao.UserWxExtDao;
import com.gizwits.lease.user.entity.UserWxExt;
import com.gizwits.lease.user.service.UserWxExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 微信用户信息 服务实现类
 * </p>
 *
 * @author zhl
 * @since 2017-08-10
 */
@Service
public class UserWxExtServiceImpl extends ServiceImpl<UserWxExtDao, UserWxExt> implements UserWxExtService {

    @Autowired
    private UserWxExtDao userWxExtDao;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserWxExt getByOpenid(String openid) {
        UserWxExt userWxExt = selectOne(new EntityWrapper<UserWxExt>().eq("user_openid", openid).orderBy("ctime", false));
        if (!Objects.isNull(userWxExt)){
            return userWxExt;
        }
        userWxExt = selectOne(new EntityWrapper<UserWxExt>().eq("openid", openid).orderBy("ctime", false));
        return userWxExt;
    }



    @Override
    public UserWxExt getByOpenidAndWxId(String openid, String wxId) {
        UserWxExt userWxExt = selectOne(new EntityWrapper<UserWxExt>().eq("user_openid", openid).eq("wx_id", wxId));
        if (!Objects.isNull(userWxExt)){
            return userWxExt;
        }
        userWxExt = selectOne(new EntityWrapper<UserWxExt>().eq("openid", openid));
        return userWxExt;
    }
}
