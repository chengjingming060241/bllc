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
 * 系统消息表
 * </p>
 *
 * @author yinhui
 * @since 2018-01-10
 */
@TableName("sys_message")
public class SysMessage extends Model<SysMessage> {

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
     * 发件人ID
     */
	@TableField("addresser_id")
	private Integer addresserId;
    /**
     * 发件人名称
     */
	@TableField("addresser_name")
	private String addresserName;
    /**
     * 标题
     */
	private String title;
    /**
     * 内容
     */
	private String content;
    /**
     * 是否已读：0 未读，1已读
     */
	@TableField("is_read")
	private Integer isRead;
    /**
     * 是否发送：0，为发送，1，已发送
     */
	@TableField("is_send")
	private Integer isSend;
    /**
     * 是否绑定微信
     */
	@TableField("is_bind_wx")
	private Integer isBindWx;
    /**
     * 消息类型：1工单 2故障 3分润账单 4滤芯消息 5 租赁消息
     */
	@TableField("message_type")
	private Integer messageType;
    /**
     * 是否删除：0未删除 1已删除
     */
	@TableField("is_deleted")
	private Integer isDeleted;
    /**
     * 设备Mac
     */
	private String mac;
    /**
     * 是否解决：0未解决 1已解决
     */
	@TableField("is_fixed")
	private Integer isFixed;
    /**
     * 故障，滤芯，租赁消息的触发条件
     */
	private String command;


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

	public Integer getAddresserId() {
		return addresserId;
	}

	public void setAddresserId(Integer addresserId) {
		this.addresserId = addresserId;
	}

	public String getAddresserName() {
		return addresserName;
	}

	public void setAddresserName(String addresserName) {
		this.addresserName = addresserName;
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

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Integer getIsSend() {
		return isSend;
	}

	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
	}

	public Integer getIsBindWx() {
		return isBindWx;
	}

	public void setIsBindWx(Integer isBindWx) {
		this.isBindWx = isBindWx;
	}

	public Integer getMessageType() {
		return messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Integer getIsFixed() {
		return isFixed;
	}

	public void setIsFixed(Integer isFixed) {
		this.isFixed = isFixed;
	}

	public String getCommand() { return command; }

	public void setCommand(String command) { this.command = command; }

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
