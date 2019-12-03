package com.gizwits.lease.product.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 产品指令配置扩展表
 * </p>
 *
 * @author yuqing
 * @since 2018-02-03
 */
@TableName("product_data_point_ext")
public class ProductDataPointExt extends Model<ProductDataPointExt> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 产品id
     */
	@TableField("product_id")
	private Integer productId;
    /**
     * 数据点id，product_command_config.id
     */
	@TableField("data_id")
	private Integer dataId;
    /**
     * 指令名称
     */
	private String name;
    /**
     * 是否在后台展示：0,不展示；1,展示
     */
	@TableField("show_enable")
	private Boolean showEnable;

	/**
	 * 标志名称
	 */
	@TableField("identity_name")
	private String identityName;

    /**
     * 创建时间
     */
	private Date ctime;
    /**
     * 更新时间
     */
	private Date utime;
    /**
     * 第三方api提供商，0：无；1：和风；2：阿里
     */
	private Integer vendor;
    /**
     * 1：温度；2：湿度；3：pm2.5；4：空气质量
     */
	private Integer param;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getDataId() {
		return dataId;
	}

	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}

	public String getIdentityName() {
		return identityName;
	}

	public void setIdentityName(String identityName) {
		this.identityName = identityName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean isShowEnable() {
		return showEnable;
	}

	public void setShowEnable(Boolean showEnable) {
		this.showEnable = showEnable;
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

	public Integer getVendor() {
		return vendor;
	}

	public void setVendor(Integer vendor) {
		this.vendor = vendor;
	}

	public Integer getParam() {
		return param;
	}

	public void setParam(Integer param) {
		this.param = param;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
