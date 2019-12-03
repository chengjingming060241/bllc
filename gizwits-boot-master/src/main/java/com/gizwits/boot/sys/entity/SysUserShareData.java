package com.gizwits.boot.sys.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统用户共享数据表
 * </p>
 *
 * @author 
 * @since 2018-02-09
 */
@TableName("sys_user_share_data")
public class SysUserShareData extends Model<SysUserShareData> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 系统用户ID
     */
	@TableField("sys_user_id")
	private Integer sysUserId;
    /**
     * 共享给下级的数据
     */
	@TableField("share_data")
	private String shareData;
    /**
     * 添加时间
     */
	private Date ctime;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Integer sysUserId) {
		this.sysUserId = sysUserId;
	}

	public String getShareData() {
		return shareData;
	}

	public void setShareData(String shareData) {
		this.shareData = shareData;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
