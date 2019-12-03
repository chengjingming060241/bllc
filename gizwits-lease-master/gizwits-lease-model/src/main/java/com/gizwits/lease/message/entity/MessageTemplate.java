package com.gizwits.lease.message.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.gizwits.lease.message.entity.dto.MessageTemplateAddDto;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * <p>
 * 消息模版表
 * </p>
 *
 * @author yinhui
 * @since 2018-01-17
 */
@TableName("message_template")
public class MessageTemplate extends Model<MessageTemplate> {

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
     * 标题
     */
	private String title;
    /**
     * 消息模版内容
     */
	private String content;
    /**
     * 触发条件
     */
	private String command;
    /**
     * 消息模版类型：2 设备消息 4 租赁消息
     */
	private Integer type;
    /**
     * 产品id
     */
	@TableField("product_id")
	private Integer productId;
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
     * 当提醒类型为租赁类型时：填写收费模式id
     */
	@TableField("service_id")
	private Integer serviceId;
    /**
     * 当提醒类型为租赁类型时：填写收费模式名称
     */
	@TableField("service_name")
	private String serviceName;
    /**
     * 是否依靠数据点上报：0不依靠，1依靠
     */
	@TableField("depend_on_data_point")
	private Integer dependOnDataPoint;
    /**
     * 当不依靠数据点时所设判断条件:百分比/数值
     */
	private Double rate;
    /**
     * 当不依靠数据点时所设判断条件: 1 百分比 2数值
     */
	@TableField("rate_type")
	private Integer rateType;

	public MessageTemplate() {
	}

	public MessageTemplate(MessageTemplateAddDto dto) {
		BeanUtils.copyProperties(dto,this);
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCommand() { return command; }

	public void setCommand(String command) { this.command = command; }

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
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

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Integer getDependOnDataPoint() {
		return dependOnDataPoint;
	}

	public void setDependOnDataPoint(Integer dependOnDataPoint) {
		this.dependOnDataPoint = dependOnDataPoint;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Integer getRateType() { return rateType; }

	public void setRateType(Integer rateType) { this.rateType = rateType; }

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
