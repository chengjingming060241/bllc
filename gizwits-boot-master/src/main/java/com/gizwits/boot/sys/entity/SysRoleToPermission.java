package com.gizwits.boot.sys.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 角色权限关系表(多对多)
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@TableName("sys_role_to_permission")
public class SysRoleToPermission extends Model<SysRoleToPermission> {

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
     * 角色ID
     */
	@TableField("role_id")
	private Integer roleId;
    /**
     * 权限(菜单)ID
     */
	@TableField("permission_id")
	private Integer permissionId;


	@TableField("sys_user_id")
	private Integer sysUserId;

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

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
