package com.gizwits.boot.sys.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 系统用户扩展表
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@TableName("sys_user_ext")
public class SysUserExt extends Model<SysUserExt> {

    private static final long serialVersionUID = 1L;

    /**
     * 系统用户ID
     */
    @TableId("sys_user_id")
	private Integer sysUserId;

	@TableField("sys_user_name")
	private String sysUserName;

	/**
     * 添加时间
     */
	private Date ctime;
    /**
     * 更新时间
     */
	private Date utime;
    /**
     * 微信appid,必须加密之后存储,分润时候使用
     */
	@TableField("wx_appid")
	private String wxAppid;
    /**
     * 微信支付商户号, 必须加密之后存储,分润时候使用
     */
	@TableField("wx_partner_id")
	private String wxParenterId;
    /**
     * 微信支付key,必须加密之后存储,分润时候使用
     */
	@TableField("wx_app_secret")
	private String wxAppSecret;
    /**
     * 微信支付时,必须加密之后存储,分润时候使用
     */
	@TableField("wx_partner_secret")
	private String wxPartnerSecret;

	/**
	 * 微信id
	 */
	@TableField("wx_id")
	private String wxId;

	/**
	 * 微信token验证
     */
	@TableField("wx_token")
	private String wxToken;

	/**
	 * 微信支付消息体
     */
	@TableField("wx_pay_body")
	private String wxPayBody;

	/**
	 * 关注微信公众号的消息推送
     */
	@TableField("wx_subscribe_msg")
	private String wxSubscribeMsg;

	/**
	 * 消息模板ID
     */
	@TableField("wx_template_id")
	private String wxTemplateId;

	/**
	 * 用于生成设备二维码，对应二维码内容中的域名
     */
	@TableField("qrcode_host")
	private String qrcodeHost;

	/**
	 * 微信小程序二维码
     */
	@TableField("wx_mini_qrcode")
	private String wxMiniQrcode;

	/**
	 * 微信支付证书路径
     */
	@TableField("wx_cert_path")
	private String wxCertPath;

	/**
	 * 微信公众号前端相对路径
	 */
	@TableField("wx_frontend_path")
	private String wxFrontendPath;

	/**
	 * 微信app支付证书路径
	 */
	@TableField("wx_app_cert_path")
	private String wxAppCertPath;

	/**
	 * 微信app支付应用ID
	 */
	@TableField("wx_pay_app_id")
	private String wxPayAppId;

	/**
	 * 微信app支付秘钥
	 */
	@TableField("wx_pay_app_secret")
	private String wxPayAppSecret;

	/**
	 * 维护人员openId
	 */
	@TableField("wx_open_id")
	private String wxOpenId;

	/**
	 * 收款人openId
	 */
	@TableField("receiver_open_id")
	private String receiverOpenId;

	/**
	 * 收款人微信实名
	 */
	@TableField("receiver_wx_name")
	private String receiverWxName;

	/**********************************************/
	/************** 支付宝相关参数  *****************/
	/**********************************************/
	/**
	 * 支付宝生活号应用ID
     */
	@TableField("alipay_appid")
	private String alipayAppid;

	/**
	 * 生活号授权回调地址
     */
	@TableField("alipay_redirect_url")
	private String alipayRedirectUrl;

	/**
	 * 支付宝公钥
     */
	@TableField("alipay_public_key")
	private String alipayPublicKey;

	/**
	 * 用户RSA2私钥
     */
	@TableField("alipay_private_key")
	private String alipayPrivateKey;

	/**
	 * 支付宝支付异步回调地址
     */
	@TableField("alipay_notify_url")
	private String alipayNotifyUrl;

	/**
	 * 支付宝生成公钥
     */
	@TableField("alipay_self_public_key")
	private String alipaySelfPublicKey;

	/**
	 * 支付宝支付完成跳转页面地址
     */
	@TableField("alipay_return_url")
	private String alipayReturnUrl;

	@TableField("alipay_partner")
	private String alipayPartner;

	@TableField("alipay_account")
	private String alipayAccount;

	@TableField("alipay_account_name")
	private String alipayAccountName;


	@TableField("is_deleted")
	private Integer isDeleted;

	public String getQrcodeHost() {
		return qrcodeHost;
	}

	public void setQrcodeHost(String qrcodeHost) {
		this.qrcodeHost = qrcodeHost;
	}

	public String getWxMiniQrcode() {
		return wxMiniQrcode;
	}

	public void setWxMiniQrcode(String wxMiniQrcode) {
		this.wxMiniQrcode = wxMiniQrcode;
	}

	public String getWxCertPath() {
		return wxCertPath;
	}

	public void setWxCertPath(String wxCertPath) {
		this.wxCertPath = wxCertPath;
	}

	public String getAlipayAccount() {
		return alipayAccount;
	}

	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}

	public String getAlipayAccountName() {
		return alipayAccountName;
	}

	public void setAlipayAccountName(String alipayAccountName) {
		this.alipayAccountName = alipayAccountName;
	}

	public String getAlipayPartner() {
		return alipayPartner;
	}

	public void setAlipayPartner(String alipayPartner) {
		this.alipayPartner = alipayPartner;
	}

	public String getAlipaySelfPublicKey() {
		return alipaySelfPublicKey;
	}

	public void setAlipaySelfPublicKey(String alipaySelfPublicKey) {
		this.alipaySelfPublicKey = alipaySelfPublicKey;
	}

	public String getAlipayAppid() {
		return alipayAppid;
	}

	public void setAlipayAppid(String alipayAppid) {
		this.alipayAppid = alipayAppid;
	}

	public String getAlipayRedirectUrl() {
		return alipayRedirectUrl;
	}

	public void setAlipayRedirectUrl(String alipayRedirectUrl) {
		this.alipayRedirectUrl = alipayRedirectUrl;
	}

	public String getAlipayPublicKey() {
		return alipayPublicKey;
	}

	public void setAlipayPublicKey(String alipayPublicKey) {
		this.alipayPublicKey = alipayPublicKey;
	}

	public String getAlipayPrivateKey() {
		return alipayPrivateKey;
	}

	public void setAlipayPrivateKey(String alipayPrivateKey) {
		this.alipayPrivateKey = alipayPrivateKey;
	}

	public String getAlipayNotifyUrl() {
		return alipayNotifyUrl;
	}

	public void setAlipayNotifyUrl(String alipayNotifyUrl) {
		this.alipayNotifyUrl = alipayNotifyUrl;
	}

	public String getAlipayReturnUrl() {
		return alipayReturnUrl;
	}

	public void setAlipayReturnUrl(String alipayReturnUrl) {
		this.alipayReturnUrl = alipayReturnUrl;
	}

	public String getWxTemplateId() {
		return wxTemplateId;
	}

	public void setWxTemplateId(String wxTemplateId) {
		this.wxTemplateId = wxTemplateId;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
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

	public String getSysUserName() {
		return sysUserName;
	}

	public void setSysUserName(String sysUserName) {
		this.sysUserName = sysUserName;
	}

	public Integer getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Integer sysUserId) {
		this.sysUserId = sysUserId;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getUtime() {
		return utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
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

	public Integer getIsDeleted() { return isDeleted; }

	public void setIsDeleted(Integer isDeleted) { this.isDeleted = isDeleted; }

	public String getWxFrontendPath() {
		return wxFrontendPath;
	}

	public void setWxFrontendPath(String wxFrontendPath) {
		this.wxFrontendPath = wxFrontendPath;
	}

	public String getWxAppCertPath() {
		return wxAppCertPath;
	}

	public void setWxAppCertPath(String wxAppCertPath) {
		this.wxAppCertPath = wxAppCertPath;
	}

	public String getWxPayAppId() {
		return wxPayAppId;
	}

	public void setWxPayAppId(String wxPayAppId) {
		this.wxPayAppId = wxPayAppId;
	}

	public String getWxPayAppSecret() {
		return wxPayAppSecret;
	}

	public void setWxPayAppSecret(String wxPayAppSecret) {
		this.wxPayAppSecret = wxPayAppSecret;
	}

	@Override
	protected Serializable pkVal() {
		return this.sysUserId;
	}

}
