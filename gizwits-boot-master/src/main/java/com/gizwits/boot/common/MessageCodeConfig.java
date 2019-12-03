package com.gizwits.boot.common;

import com.gizwits.boot.annotation.SysConfig;

/**
 * 获取平台短信配置信息
 * Created by yinhui on 2017/8/29.
 */
public interface MessageCodeConfig {

    @SysConfig(value = "f2be677d325f630408a559f5c267ee6c", remark = "平台apikey")
    String getApiKey();

    @SysConfig(value = "1860414", remark = "平台验证码模版id")
    String getTemplateId();

    @SysConfig(value = "【机智云】您的验证码是#code#。如非本人操作，请忽略本短信", remark = "平台验证码模版值")
    String getTemplateValue();

    @SysConfig(value = "1877844", remark = "平台故障推送模版id")
    String getAlarmTemplateId();

    @SysConfig(value = "【机智云】故障通知，#device#，故障现象：#symptom#，故障时间：#time#，请及时处理。", remark = "平台故障推送模版")
    String getAlarmTemplateValue();

    @SysConfig(value = "/data/lease/files/avatars/",remark = "系统用户头像存放地址")
    String getAvatarPath();

    @SysConfig(value = "5242880", remark = "上传图片的最大尺寸")
    long getFileMaxSize();

    @SysConfig(value = "jpg,gif,png,jpeg", remark = "图片类型")
    String getPictureType();


    @SysConfig(value = "a714a43e8a14dfe5252a6b55cd966506", remark = "云片APIKey")
    String getMessageApiKey();

    @SysConfig(value = "3327468", remark = "短信验证码的模板ID")
    String getMessageCodeTemplateId();

    @SysConfig(value = "【布朗净空】您的验证码是#code#。如非本人操作，请忽略本短信", remark = "短信验证码模板")
    String getMessageCodeTemplate();

    @SysConfig(value = "909286181@qq.com", remark = "邮箱验证码发件人")
    String getSender();

    @SysConfig(value = "cjm060241", remark = "邮件发送人授权码")
    String getMAIL_FROM_PASSWORD();
}
