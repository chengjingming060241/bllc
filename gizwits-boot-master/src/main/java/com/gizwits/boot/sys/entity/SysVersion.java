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
 * 系统版本
 * </p>
 *
 * @author Joke
 * @since 2018-01-24
 */
@TableName("sys_version")
public class SysVersion extends Model<SysVersion> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 版本名
     */
	@TableField("version_name")
	private String versionName;
    /**
     * 版本标识
     */
	@TableField("version_code")
	private String versionCode;
    /**
     * 对应的权限
     */
	@TableField("p_permission_id")
	private Integer pPermissionId;
    /**
     * 排序
     */
	private Integer sort;
    /**
     * 操作人员ID
     */
	@TableField("sys_user_id")
	private Integer sysUserId;
    /**
     * 操作人员名称
     */
	@TableField("sys_user_name")
	private String sysUserName;
	private Date ctime;
	private Date utime;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public Integer getpPermissionId() {
		return pPermissionId;
	}

	public void setpPermissionId(Integer pPermissionId) {
		this.pPermissionId = pPermissionId;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
