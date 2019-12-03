package com.gizwits.lease.card.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 卡类型
 * </p>
 *
 * @author ${author}
 * @since 2018-01-24
 */
@TableName("card_type")
public class UserType extends Model<UserType> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 卡类型名称
     */
	@TableField("type_name")
	private Integer typeName;
    /**
     * 套餐金额
     */
	@TableField("type_money")
	private String typeMoney;
    /**
     * 套餐使用次数
     */
	@TableField("type_count")
	private Integer typeCount;
    /**
     * 默认次数
     */
	@TableField("default_usage")
	private Integer defaultUsage;
    /**
     * 分润次数
     */
	@TableField("share_count")
	private Integer shareCount;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 修改时间
     */
	@TableField("update_time")
	private Date updateTime;
    /**
     * 创建人名称
     */
	@TableField("create_name")
	private String createName;
    /**
     * 创建人ID
     */
	@TableField("create_user_id")
	private String createUserId;
    /**
     * 删除
     */
	@TableField("delete")
	private Integer delete;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTypeName() {
		return typeName;
	}

	public void setTypeName(Integer typeName) {
		this.typeName = typeName;
	}

	public String getTypeMoney() {
		return typeMoney;
	}

	public void setTypeMoney(String typeMoney) {
		this.typeMoney = typeMoney;
	}

	public Integer getTypeCount() {
		return typeCount;
	}

	public void setTypeCount(Integer typeCount) {
		this.typeCount = typeCount;
	}

	public Integer getDefaultUsage() {
		return defaultUsage;
	}

	public void setDefaultUsage(Integer defaultUsage) {
		this.defaultUsage = defaultUsage;
	}

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getDelete() {
		return delete;
	}

	public void setDelete(Integer delete) {
		this.delete = delete;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
