package com.gizwits.lease.device.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 设备收费模式表(多对多)
 * </p>
 *
 * @author yinhui
 * @since 2018-03-12
 */
@TableName("device_to_product_service_mode")
public class DeviceToProductServiceMode extends Model<DeviceToProductServiceMode> {

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
     * 设备序列号
     */
	private String sno;
    /**
     *  收费模式ID
     */
	@TableField("service_mode_id")
	private Integer serviceModeId;
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
    /**
     * 是否删除：0未删除 1删除
     */
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

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public Integer getServiceModeId() {
		return serviceModeId;
	}

	public void setServiceModeId(Integer serviceModeId) {
		this.serviceModeId = serviceModeId;
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
