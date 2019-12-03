package com.gizwits.lease.device.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gizwits.boot.base.Constants;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户绑定设备表
 * </p>
 *
 * @author yinhui
 * @since 2017-07-12
 */
@Data
@Accessors(chain = true)
@TableName("user_device")
public class UserDevice extends Model<UserDevice> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,自增长
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 添加时间
     */
    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN,timezone = "GMT+8")
	private Date ctime;
    /**
     * 更新时间
     */
	@JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN,timezone = "GMT+8")
	private Date utime;
    /**
     * 对应设备序列号
     */
	private String sno;
    /**
     * 对应设备MAC
     */
	private String mac;
    /**
     * 微信设备ID
     */
	@TableField("wechat_device_id")
	private String wechatDeviceId;
    /**
     * 所属用户
     */
	@TableField("user_id")
	private Integer userId;
	/**
	 *是否绑定设备,0未绑定，1绑定
	 */
	@TableField("is_bind")
	private Integer isBind;

	private String openid;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 是否是管理员
	 */
	@TableField("is_manager")
	private Integer isManager;

    /**
     * 拥有者，指代理商或者运营商
     */
    @TableField("owner_id")
    private Integer ownerId;

    /**
     * 是否删除：0未删除，1删除
     */
    @TableField("is_deleted")
    @TableLogic
    private Integer isDeleted = 0;

    public UserDevice(Date ctime) {
        this.ctime = ctime;
    }

	public UserDevice() {
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getWechatDeviceId() {
		return wechatDeviceId;
	}

	public void setWechatDeviceId(String wechatDeviceId) {
		this.wechatDeviceId = wechatDeviceId;
	}


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getIsBind() {
		return isBind;
	}

	public void setIsBind(Integer isBind) {
		this.isBind = isBind;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getIsManager() {
		return isManager;
	}

	public void setIsManager(Integer isManager) {
		this.isManager = isManager;
	}

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
