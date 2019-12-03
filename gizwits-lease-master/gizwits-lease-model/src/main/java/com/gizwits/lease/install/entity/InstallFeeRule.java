package com.gizwits.lease.install.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 初装费规则
 * </p>
 *
 * @author Joke
 * @since 2018-04-16
 */
@TableName("install_fee_rule")
public class InstallFeeRule extends Model<InstallFeeRule> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 规则名称
     */
	private String name;
    /**
     * 适应产品id
     */
	@TableField("product_id")
	private Integer productId;
    /**
     * 装机金额
     */
	private BigDecimal fee;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
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
