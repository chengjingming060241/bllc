package com.gizwits.boot.common;

import com.gizwits.boot.annotation.SysConfig;

/**
 * Config - web配置
 *
 * @author lilh
 * @date 2017/7/20 14:33
 */
public interface WebCommonConfig {

    @SysConfig(value = "false", remark = "是否使用反向代理")
    String getIsUsedReverseProxy();


    @SysConfig(value = "1", remark = "管理员角色id")
    Integer getManagerRoleId();

    @SysConfig(value = "1",remark = "管理员用户id")
    Integer getManagerUserId();

    @SysConfig(value = "WEIXIN", remark = "分润支付的类型: WEIXIN;ALIPAY")
    String getShareBenefitPayType();
}
