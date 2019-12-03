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
 * 系统权限(菜单)表
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@TableName("sys_permission")
public class SysPermission extends Model<SysPermission> {

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
     * 菜单标识,前端使用
     */
	@TableField("permission_key")
	private String permissionKey;
    /**
     * 菜单名称
     */
	@TableField("permission_name")
	private String permissionName;
    /**
     * 父级别菜单ID
     */
	@TableField("p_permission_id")
	private Integer pPermissionId;
    /**
     * logo字符串
     */
	private String icon;
    /**
     * 菜单uri
     */
	private String uri;
    /**
     * 1:左侧菜单 2:头部菜单 3:表格菜单 4:数据类型权限
     */
	@TableField("permission_type")
	private Integer permissionType;
    /**
     * 菜单排序
     */
	private Integer sort;

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

	public String getPermissionKey() {
		return permissionKey;
	}

	public void setPermissionKey(String permissionKey) {
		this.permissionKey = permissionKey;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public Integer getpPermissionId() {
		return pPermissionId;
	}

	public void setpPermissionId(Integer pPermissionId) {
		this.pPermissionId = pPermissionId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Integer getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(Integer permissionType) {
		this.permissionType = permissionType;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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
