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
 * 用户角色关系表(多对多)
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@TableName("sys_user_to_role")
public class SysUserToRole extends Model<SysUserToRole> {

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
     * 帐号ID
     */
	@TableField("user_id")
	private Integer userId;
    /**
     * 权限(菜单)ID
     */
	@TableField("role_id")
	private Integer roleId;

	/**
	 * 创建人
	 */
	@TableField("sys_user_id")
	private Integer sysUserId;

	/**
	 * 创建人名称
	 */
	@TableField("sys_user_name")
	private String sysUserName;

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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
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

	public Integer getIsDeleted() { return isDeleted; }

	public void setIsDeleted(Integer isDeleted) { this.isDeleted = isDeleted; }
}
