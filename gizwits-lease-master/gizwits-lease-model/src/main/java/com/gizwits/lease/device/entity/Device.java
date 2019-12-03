package com.gizwits.lease.device.entity;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 设备表
 * </p>
 *
 * @author zhl
 * @since 2017-07-11
 */
public class Device extends Model<Device> {

    private static final long serialVersionUID = 1L;

    /**
     * 设备序列号
     */
    @TableId("sno")
    private String sno;
    /**
     * 添加时间
     */
    private Date ctime;
    /**
     * 更新时间
     */
    private Date utime;
    /**
     * 设备名称
     */
    private String name;
    /**
     * MAC地址,通讯用
     */
    private String mac;

    /**
     * 控制器码
     */
    @TableField("sn1")
    private String sN1;

    /**
     * SN2码
     */
    @TableField("sn2")
    private String sN2;

    /**
     * IMEI码
     */
    @TableField("imei")
    private String iMEI;


    /**
     * 设备工作状态,0:离线,1:空闲 2:使用中 3:故障中 4:禁用
     * 正常、故障、报警、窜货报警
     */
    @TableField("work_status")
    private Integer workStatus;

    /**
     * 设备在线状态
     */
    @TableField("online_status")
    private Integer onlineStatus;

    /**
     * 设备故障状态
     */
    @TableField("fault_status")
    private Integer faultStatus;

    /**
     * 设备状态,0:入库 1:出库 2:服务中 3:暂停服务 4:已返厂 5:已报废
     * 0.待扫码、1.可入库
     */
    private Integer status;
    /**
     * 经度
     */
    private BigDecimal longitude;
    /**
     * 维度
     */
    private BigDecimal latitude;
    /**
     * 最后上线时间
     */
    @TableField("last_online_time")
    private Date lastOnlineTime;
    /**
     * 经办人的系统账号
     */
    @TableField("operator_id")
    private Integer operatorId;
    /**
     * 经办人
     */
    @TableField("operator_name")
    private String operatorName;

    /**
     * 仓库ID
     */
    @TableField("launch_area_id")
    private Integer launchAreaId;
    /**
     * 仓库名称
     */
    @TableField("launch_area_name")
    private String launchAreaName;
    /**
     * 所属品类id
     */
    @TableField("product_id")
    private Integer productId;
    /**
     * 所属品类名称
     */
    @TableField("product_name")
    private String productName;

    /**
     * 所属产品Id
     */
    @TableField("product_category_id")
    private Integer productCategoryId;

    /**
     * 收费模式ID
     */
    @TableField("service_id")
    private Integer serviceId;

    /**
     * 收费模式名称
     */
    @TableField("service_name")
    private String serviceName;
    /**
     * 删除标识，0：未删除，1：已删除
     */
    @TableField("is_deleted")
    private Integer isDeleted;
    /**
     * 经销商ID
     */
    @TableField("agent_id")
    private Integer agentId;
    /**
     * 创建人
     */
    @TableField("sys_user_id")
    private Integer sysUserId;

    /**
     * 机智云设备did
     */
    @TableField("giz_did")
    private String gizDid;
    /**
     * 机智云设备passcode
     */

	@TableField("giz_pass_code")
	private String gizPassCode;
	/**
	 * 机智云websockt端口
	 */
	@TableField("giz_ws_port")
	private String gizWsPort;

	/**
	 * 拥有者
	 */
	@TableField("owner_id")
	private Integer ownerId;

    @TableField("owner_name")
    private String ownerName;

	@TableField("giz_wss_port")
	private String gizWssPort;

	@TableField("giz_host")
	private String gizHost;

    /**
     * 微信ticket(为生成二维码所用)，当生成二维码方式为微信生成时，该字段不为空
     */
    @TableField("wx_ticket")
    private String wxTicket;
    /**
     * 微信绑定设备的id，用于找到该设备
     */
    @TableField("wx_did")
    private String wxDid;
    /**
     * 二维码路径，当生成二维码方式为WEB生成时，该字段不为空
     */
    @TableField("content_url")
    private String contentUrl;

    /**

     * 设备组id
     */
    @TableField("group_id")
    private Integer groupId;


    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    /**
     * 安装用户姓名
     */
    @TableField("install_user_name")
    private String installUserName;

    /**
     * 安装用户所在省
     */
    @TableField("install_user_province")
    private String installUserProvince;

    /**
     * 安装用户所在市
     */
    @TableField("install_user_city")
    private String installUserCity;

    /**
     * 安装用户所在区
     */
    @TableField("install_user_area")
    private String installUserArea;

    /**
     * 安装用户详细地址
     */
    @TableField("install_user_address")
    private String installUserAddress;

    /**
     * 安装用户手机号
     */
    @TableField("install_user_mobile")
    private String installUserMobile;
    /**
     * 激活状态：1未激活 2激活
     */
    @TableField("activate_status")
    private Integer activateStatus;

    /**
     * 激活时间
     */
    @TableField("activated_time")
    private Date activatedTime;

    /**
     * 数据来源
     */
    private Integer origin;

    /**
     * 连续下单异常的次数
     */
    @TableField("abnormal_times")
    private Integer abnormalTimes;

    /**
     * 多次异常后锁定设备
     */
    @TableField("lock")
    private Boolean lock;

    /**
     * 国家
     */
    private String country;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 设备控制器类型
     */
    @TableField("control_type")
    private Boolean controlType;


    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public Boolean getControlType() {
        return controlType;
    }

    public void setControlType(Boolean controlType) {
        this.controlType = controlType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getsN1() {
        return sN1;
    }

    public void setsN1(String sN1) {
        this.sN1 = sN1;
    }

    public String getsN2() {
        return sN2;
    }

    public void setsN2(String sN2) {
        this.sN2 = sN2;
    }

    public String getiMEI() {
        return iMEI;
    }

    public void setiMEI(String iMEI) {
        this.iMEI = iMEI;
    }

    public Integer getAbnormalTimes() {
        return abnormalTimes;
    }

    public void setAbnormalTimes(Integer abnormalTimes) {
        this.abnormalTimes = abnormalTimes;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    public String getInstallUserProvince() {
        return installUserProvince;
    }

    public void setInstallUserProvince(String installUserProvince) {
        this.installUserProvince = installUserProvince;
    }

    public String getInstallUserCity() {
        return installUserCity;
    }

    public void setInstallUserCity(String installUserCity) {
        this.installUserCity = installUserCity;
    }

    public String getInstallUserArea() {
        return installUserArea;
    }

    public void setInstallUserArea(String installUserArea) {
        this.installUserArea = installUserArea;
    }

    public String getInstallUserName() {
        return installUserName;
    }

    public void setInstallUserName(String installUserName) {
        this.installUserName = installUserName;
    }

    public String getInstallUserAddress() {
        return installUserAddress;
    }

    public void setInstallUserAddress(String installUserAddress) {
        this.installUserAddress = installUserAddress;
    }

    public String getInstallUserMobile() {
        return installUserMobile;
    }

    public void setInstallUserMobile(String installUserMobile) {
        this.installUserMobile = installUserMobile;
    }

	public String getGizWsPort() {
		return gizWsPort;
	}

	public void setGizWsPort(String gizWsPort) {
		this.gizWsPort = gizWsPort;
	}

	public String getGizWssPort() {
		return gizWssPort;
	}

	public void setGizWssPort(String gizWssPort) {
		this.gizWssPort = gizWssPort;
	}

	public String getGizHost() {
		return gizHost;
	}

	public void setGizHost(String gizHost) {
		this.gizHost = gizHost;
	}


    public String getWxDid() {
        return wxDid;
    }

    public void setWxDid(String wxDid) {
        this.wxDid = wxDid;
    }

    public String getWxTicket() {
        return wxTicket;
    }

    public void setWxTicket(String wxTicket) {
        this.wxTicket = wxTicket;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
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

    public Integer getFaultStatus() {
        return faultStatus;
    }

    public void setFaultStatus(Integer faultStatus) {
        this.faultStatus = faultStatus;
    }

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Integer getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(Integer workStatus) {
        this.workStatus = workStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Date getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(Date lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Integer getLaunchAreaId() {
        return launchAreaId;
    }

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

    public void setLaunchAreaId(Integer launchAreaId) {
        this.launchAreaId = launchAreaId;
    }

    public String getLaunchAreaName() {
        return launchAreaName;
    }

    public void setLaunchAreaName(String launchAreaName) {
        this.launchAreaName = launchAreaName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getGizDid() {
        return gizDid;
    }

    public void setGizDid(String gizDid) {
        this.gizDid = gizDid;
    }

    public String getGizPassCode() {
        return gizPassCode;
    }

    public void setGizPassCode(String gizPassCode) {
        this.gizPassCode = gizPassCode;
    }


    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getActivateStatus() { return activateStatus; }

    public void setActivateStatus(Integer activateStatus) { this.activateStatus = activateStatus; }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public Date getActivatedTime() {
        return activatedTime;
    }

    public void setActivatedTime(Date activatedTime) {
        this.activatedTime = activatedTime;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    @Override
    protected Serializable pkVal() {
        return this.sno;
    }

}
