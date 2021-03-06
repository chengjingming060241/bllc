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
 * 问题反馈表
 * </p>
 *
 * @author yinhui
 * @since 2017-07-26
 */
@TableName("feedback_user")
public class FeedbackUser extends Model<FeedbackUser> {

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
	 * 用户Id
	 */
	@TableField("user_id")
	private Integer userId;
    /**
     * 用户昵称
     */
	@TableField("nick_name")
	private String nickName;
    /**
     * 头像地址
     */
	private String avatar;
    /**
     * 手机
     */
	private String mobile;
    /**
     * 内容
     */
	private String content;
    /**
     * 图片地址
     */
	@TableField("picture_url")
	private String pictureUrl;
    /**
     * 图片数
     */
	@TableField("picture_num")
	private Integer pictureNum;
    /**
     * 消息来源：1 移动用户端,2 移动管理端 
     */
	private Integer origin;
    /**
     * 设备序列号
     */
	private String sno;
    /**
     * MAC地址
     */
	private String mac;
    /**
     * 收件人id
     */
	@TableField("recipient_id")
	private Integer recipientId;
    /**
     * 收件人姓名
     */
	@TableField("recipient_name")
	private String recipientName;
    /**
     * 是否已读：0 未读，1已读
     */
	@TableField("is_read")
	private Integer isRead;
    /**
     * 反馈类型，1设备使用，2滤网问题，3app使用
     */
	private Integer type;
    /**
     * 反馈状态，0未处理，1已处理，默认未处理
     */
	private Integer status;
    /**
     * 删除标识位=，0未删除，1已删除
     */
    @TableField("is_deleted")
	private Integer isDeleted;
    /**
     * 处理备注
     */
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public Integer getPictureNum() {
		return pictureNum;
	}

	public void setPictureNum(Integer pictureNum) {
		this.pictureNum = pictureNum;
	}

	public Integer getOrigin() {
		return origin;
	}

	public void setOrigin(Integer origin) {
		this.origin = origin;
	}

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Integer getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(Integer recipientId) {
		this.recipientId = recipientId;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Integer getUserId() { return userId; }

	public void setUserId(Integer userId) { this.userId = userId; }

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
