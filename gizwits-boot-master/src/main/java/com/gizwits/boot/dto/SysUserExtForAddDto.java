package com.gizwits.boot.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.gizwits.boot.sys.entity.SysUserExt;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

/**
 * Dto - 系统账号扩展
 *
 * @author lilh
 * @date 2017/7/12 18:55
 */
public class SysUserExtForAddDto {

    private String wxAppid;

    private String wxParenterId;

    private String wxAppSecret;

    private String wxPartnerSecret;

    private String wxId;

    private String wxToken;

    private String wxPayBody;

    private String wxSubscribeMsg;

    private String wxTemplateId;

    private String wxOpenId;

    @ApiModelProperty("微信收款openId")
    private String receiverOpenId;
    @ApiModelProperty("微信收款姓名")
    private String receiverWxName;

    @ApiModelProperty("支付宝收款账号")
    private String alipayAccount;

    @ApiModelProperty("支付宝收款姓名")
    private String alipayAccountName;
    /**
     * 分润支付类型:WEIXIN,ALIPAY
     * 默认为微信
     */
    @ApiModelProperty("分润支付类型：WEIXIN,ALIPAY")
    private String shareBenefitType = "WEIXIN";

    public SysUserExtForAddDto() {
    }

    public SysUserExtForAddDto(SysUserExt ext) {
        BeanUtils.copyProperties(ext, this);
    }

    public String getWxAppid() {
        return wxAppid;
    }

    public void setWxAppid(String wxAppid) {
        this.wxAppid = wxAppid;
    }

    public String getWxParenterId() {
        return wxParenterId;
    }

    public void setWxParenterId(String wxParenterId) {
        this.wxParenterId = wxParenterId;
    }

    public String getWxAppSecret() {
        return wxAppSecret;
    }

    public void setWxAppSecret(String wxAppSecret) {
        this.wxAppSecret = wxAppSecret;
    }

    public String getWxPartnerSecret() {
        return wxPartnerSecret;
    }

    public void setWxPartnerSecret(String wxPartnerSecret) {
        this.wxPartnerSecret = wxPartnerSecret;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getWxToken() {
        return wxToken;
    }

    public void setWxToken(String wxToken) {
        this.wxToken = wxToken;
    }

    public String getWxPayBody() {
        return wxPayBody;
    }

    public void setWxPayBody(String wxPayBody) {
        this.wxPayBody = wxPayBody;
    }

    public String getWxSubscribeMsg() {
        return wxSubscribeMsg;
    }

    public void setWxSubscribeMsg(String wxSubscribeMsg) {
        this.wxSubscribeMsg = wxSubscribeMsg;
    }

    public String getWxTemplateId() {
        return wxTemplateId;
    }

    public void setWxTemplateId(String wxTemplateId) {
        this.wxTemplateId = wxTemplateId;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public String getReceiverOpenId() {
        return receiverOpenId;
    }

    public void setReceiverOpenId(String receiverOpenId) {
        this.receiverOpenId = receiverOpenId;
    }

    public String getReceiverWxName() {
        return receiverWxName;
    }

    public void setReceiverWxName(String receiverWxName) {
        this.receiverWxName = receiverWxName;
    }

    public String getAlipayAccount() { return alipayAccount; }

    public void setAlipayAccount(String alipayAccount) { this.alipayAccount = alipayAccount; }

    public String getAlipayAccountName() { return alipayAccountName; }

    public void setAlipayAccountName(String alipayAccountName) { this.alipayAccountName = alipayAccountName; }

    public String getShareBenefitType() { return shareBenefitType; }

    public void setShareBenefitType(String shareBenefitType) { this.shareBenefitType = shareBenefitType; }
}
