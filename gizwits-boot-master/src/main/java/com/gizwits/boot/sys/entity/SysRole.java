package com.gizwits.boot.sys.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 系统角色表
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@TableName("sys_role")
public class SysRole extends Model<SysRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,自增长
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 添加时间
     */
	private Date ctime;
    /**
     * 更新时间
     */
	private Date utime;
    /**
     * 角色名称
     */
	@TableField("role_name")
	private String roleName;
    /**
     * 备注描述
     */
	private String remark;

	@TableField("sys_user_id")
	private Integer sysUserId;

	@TableField("sys_user_name")
	private String sysUserName;


	/**
	 * 角色类型
	 */
	@TableField("is_share_data")
	private Integer isShareData;

	/**
	 * 分润权限类型
	 */
	@TableField("share_benefit_type")
	private Integer shareBenefitType;
	/**
	 *是否共享收费模块数据
	 */
	@TableField("is_share_service_mode")
	private Integer isShareServiceMode;

	/**
	 *设备是否能添加多个收费模式
	 */
	@TableField("is_add_more_service_mode")
	private Integer isAddMoreServiceMode;

	@TableField("is_deleted")
	private Integer isDeleted;

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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Integer sysUserId) {
		this.sysUserId = sysUserId;
	}

	public String getSysUserName() {
		return sysUserName;
	}

	public void setSysUserName(String sysUserName) {
		this.sysUserName = sysUserName;
	}

	public Integer getIsShareData() {
		return isShareData;
	}

	public void setIsShareData(Integer isShareData) {
		this.isShareData = isShareData;
	}

	public Integer getShareBenefitType() {
		return shareBenefitType;
	}

	public void setShareBenefitType(Integer shareBenefitType) {
		this.shareBenefitType = shareBenefitType;
	}

	public Integer getIsShareServiceMode() { return isShareServiceMode; }

	public void setIsShareServiceMode(Integer isShareServiceMode) { this.isShareServiceMode = isShareServiceMode; }

	public Integer getIsAddMoreServiceMode() { return isAddMoreServiceMode; }

	public void setIsAddMoreServiceMode(Integer isAddMoreServiceMode) { this.isAddMoreServiceMode = isAddMoreServiceMode; }

	public Integer getIsDeleted() { return isDeleted; }

	public void setIsDeleted(Integer isDeleted) { this.isDeleted = isDeleted; }
	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
