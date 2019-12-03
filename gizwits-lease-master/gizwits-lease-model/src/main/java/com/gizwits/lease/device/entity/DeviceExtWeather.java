package com.gizwits.lease.device.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 设备-天气拓展表
 * </p>
 *
 * @author zxhuang
 * @since 2018-02-02
 */
@TableName("device_ext_weather")
public class DeviceExtWeather extends Model<DeviceExtWeather> {

    private static final long serialVersionUID = 1L;

    public static final Integer SOURCE_HEFENG = 1; //和风天气
    public static final Integer SOURCE_ALIYUN = 2; //阿里天气

    /**
     * 设备序列号
     */
	@TableId("sno")
	private String sno;
	/**
	 * 所属产品
	 */
	@TableField("product_id")
	private Integer productId;
    /**
     * 添加时间
     */
	private Date ctime;
    /**
     * 更新时间
     */
	private Date utime;
    /**
     * 经度
     */
	private BigDecimal longitude;
    /**
     * 维度
     */
	private BigDecimal latitude;
    /**
     * 来源，1：和风，2：阿里
     */
	private Integer source;
    /**
     * 城市ID
     */
	@TableField("city_id")
	private String cityId;
    /**
     * 温度
     */
	private String tmp;
    /**
     * 湿度
     */
	private String hum;
    /**
     * pm2.5
     */
	private String pm25;
    /**
     * 空气质量
     */
	private String qlty;
    /**
     * 省
     */
	private String province;
    /**
     * 市
     */
	private String city;
    /**
     * 区
     */
	private String area;


	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
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

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getTmp() {
		return tmp;
	}

	public void setTmp(String tmp) {
		this.tmp = tmp;
	}

	public String getHum() {
		return hum;
	}

	public void setHum(String hum) {
		this.hum = hum;
	}

	public String getPm25() {
		return pm25;
	}

	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}

	public String getQlty() {
		return qlty;
	}

	public void setQlty(String qlty) {
		this.qlty = qlty;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Override
	protected Serializable pkVal() {
		return this.sno;
	}

}
