package com.gizwits.lease.install.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 初装费规则关联代理商
 * </p>
 *
 * @author Joke
 * @since 2018-04-16
 */
@TableName("install_fee_rule_to_agent")
public class InstallFeeRuleToAgent extends Model<InstallFeeRuleToAgent> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 规则id
     */
	@TableField("rule_id")
	private Integer ruleId;
    /**
     * 代理商id
     */
	@TableField("agent_id")
	private Integer agentId;
    /**
     * 代理商管理员id
     */
	@TableField("sys_account_id")
	private Integer sysAccountId;
    /**
     * 创建人id
     */
	@TableField("sys_user_id")
	private Integer sysUserId;
    /**
     * 创建时间
     */
	private Date ctime;
    /**
     * 更新时间
     */
	private Date utime;
    /**
     * 是否删除
     */
	private Boolean del;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public Integer getSysAccountId() {
		return sysAccountId;
	}

	public void setSysAccountId(Integer sysAccountId) {
		this.sysAccountId = sysAccountId;
	}

	public Integer getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Integer sysUserId) {
		this.sysUserId = sysUserId;
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

	public Boolean isDel() {
		return del;
	}

	public void setDel(Boolean del) {
		this.del = del;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
