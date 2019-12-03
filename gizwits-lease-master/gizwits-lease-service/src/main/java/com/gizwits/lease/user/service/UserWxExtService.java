package com.gizwits.lease.user.service;

import com.baomidou.mybatisplus.service.IService;
import com.gizwits.lease.user.entity.UserWxExt;

/**
 * <p>
 * 微信用户信息 服务类
 * </p>
 *
 * @author zhl
 * @since 2017-08-10
 */
public interface UserWxExtService extends IService<UserWxExt> {

    UserWxExt getByOpenid(String openid);
    UserWxExt getByOpenidAndWxId(String openid, String wxId);

}
