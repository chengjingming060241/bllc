package com.gizwits.lease.device.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * <p>
 * 用户绑定设备表
 * </p>
 *
 * @author yinhui
 * @since 2018-01-22
 */
@TableName("user_bind_device")
public class UserBindDevice extends Model<UserBindDevice> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,自增长
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 添加时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date ctime;
    /**
     * 更新时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
     * 用户账号
     */
	@TableField("account_num")
	private String accountNum;
    /**
     * 所属用户id
     */
	@TableField("user_id")
	private Integer userId;
    /**
     * 是否删除：0未删除 1已删除
     */
	@TableField("is_deleted")
	private Integer isDeleted;
    /**
     * 是否是管理员：0不是 1是
     */
	@TableField("is_manage")
	private Integer isManage;
    /**
     * 手机号
     */
	private String mobile;


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

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getIsManage() {
		return isManage;
	}

	public void setIsManage(Integer isManage) {
		this.isManage = isManage;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
