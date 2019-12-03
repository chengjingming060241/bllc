package com.gizwits.lease.message.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 广告展示表
 * </p>
 *
 * @author yinhui
 * @since 2018-01-23
 */
@TableName("advertisement_display")
public class AdvertisementDisplay extends Model<AdvertisementDisplay> {

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
     * 广告图片
     */
	private String picture;
    /**
     * 展示时间，单位（秒）
     */
	@TableField("show_time")
	private Integer showTime;
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
    /**
     * 是否删除：0,否；1,是
     */
	@TableField("is_deleted")
	private Integer isDeleted;
    /**
     * 跳转链接
     */
	private String url;
    /**
     * 广告名
     */
	private String name;
    /**
     * 排序字段
     */
	private Integer sort;


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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Integer getShowTime() {
		return showTime;
	}

	public void setShowTime(Integer showTime) {
		this.showTime = showTime;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
