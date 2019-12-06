package com.gizwits.lease.device.entity.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gizwits.boot.base.Constants;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.manager.dto.OperatorExtDto;
import com.gizwits.lease.product.dto.ProductCommandForDeviceDetailDto;
import com.gizwits.lease.product.dto.ProductServiceDetailForDeviceDto;
import org.springframework.beans.BeanUtils;

/**
 * Dto - 设备详情
 *
 * @author lilh
 * @date 2017/7/21 11:36
 */
public class DeviceForDetailDto {

    private String sno;

    private String name;

    private String sn1;
    private String imei;

    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
    private Date ctime;

    private String mac;

    private Integer workStatus;

    private String workStatusDesc;

    private Integer onlineStatus;

    private String onlineStatusDesc;

    private Integer productId;

    private String productName;

    private Date lastOnlineTime;

    private BigDecimal longitude;

    private BigDecimal latitude;

    /*属于运营商姓名**/
    private String belongOperatorName;

    private String logoUrl;

    private String description;

    private String webSite;

    private String phone;

    private String productKey;

    private Integer status;//是否串货

    List<ProductServiceDetailForDeviceDto> serviceMode;

    private DeviceLaunchAreaForDeviceDto launchArea;

    private OperatorExtDto operatorExt;

    /**
     * 是否能添加多个收费模式 false不能 true可以
     */
    private Boolean canAddMoreServiceMode;

    /**
     * 安装用户姓名
     */
    private String installUserName;

    /**
     * 安装用户所在省
     */
    private String installUserProvince;

    /**
     * 安装用户所在市
     */
    private String installUserCity;

    /**
     * 安装用户所在区
     */
    private String installUserArea;

    /**
     * 安装用户详细地址
     */
    private String installUserAddress;

    /**
     * 安装用户手机号
     */
    private String installUserMobile;

    /**
     * 是否锁定
     */
    private Boolean lock;

    /**
     * 初装费
     */
    private BigDecimal installFee;

    /** 是否可以更新投放点 */
    private Boolean canModifyLaunchArea = false;
    /**是否可以更新收费模式*/
    private Boolean canModifyServiceMode = false;
    /**是否可以修改设备*/
    private Boolean canModify = false ;

    public String getSn1() {
        return sn1;
    }

    public void setSn1(String sn1) {
        this.sn1 = sn1;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /** 控制指令 */
    private List<ProductCommandForDeviceDetailDto> controlCommands = new ArrayList<>();

    public DeviceForDetailDto(Device device) {
        BeanUtils.copyProperties(device, this);
    }

    public BigDecimal getInstallFee() {
        return installFee;
    }

    public void setInstallFee(BigDecimal installFee) {
        this.installFee = installFee;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    public OperatorExtDto getOperatorExt() {
        return operatorExt;
    }

    public String getInstallUserName() {
        return installUserName;
    }

    public void setInstallUserName(String installUserName) {
        this.installUserName = installUserName;
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

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public void setOperatorExt(OperatorExtDto operatorExt) {
        this.operatorExt = operatorExt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
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

    public String getWorkStatusDesc() {
        return workStatusDesc;
    }

    public void setWorkStatusDesc(String workStatusDesc) {
        this.workStatusDesc = workStatusDesc;
    }

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getOnlineStatusDesc() {
        return onlineStatusDesc;
    }

    public void setOnlineStatusDesc(String onlineStatusDesc) {
        this.onlineStatusDesc = onlineStatusDesc;
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

    public Date getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(Date lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
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

    public String getBelongOperatorName() {
        return belongOperatorName;
    }

    public void setBelongOperatorName(String belongOperatorName) {
        this.belongOperatorName = belongOperatorName;
    }

    public List<ProductServiceDetailForDeviceDto> getServiceMode() {
        return serviceMode;
    }

    public void setServiceMode(List<ProductServiceDetailForDeviceDto> serviceMode) {
        this.serviceMode = serviceMode;
    }

    public DeviceLaunchAreaForDeviceDto getLaunchArea() {
        return launchArea;
    }

    public void setLaunchArea(DeviceLaunchAreaForDeviceDto launchArea) {
        this.launchArea = launchArea;
    }

    public Boolean getCanModifyLaunchArea() {
        return canModifyLaunchArea;
    }

    public void setCanModifyLaunchArea(Boolean canModifyLaunchArea) {
        this.canModifyLaunchArea = canModifyLaunchArea;
    }

    public List<ProductCommandForDeviceDetailDto> getControlCommands() {
        return controlCommands;
    }

    public void setControlCommands(List<ProductCommandForDeviceDetailDto> controlCommands) {
        this.controlCommands = controlCommands;
    }

    public Boolean getCanModifyServiceMode() {
        return canModifyServiceMode;
    }

    public void setCanModifyServiceMode(Boolean canModifyServiceMode) {
        this.canModifyServiceMode = canModifyServiceMode;
    }

    public Boolean getCanModify() {
        return canModify;
    }

    public void setCanModify(Boolean canModify) {
        this.canModify = canModify;
    }

    public Boolean getCanAddMoreServiceMode() {
        return canAddMoreServiceMode;
    }

    public void setCanAddMoreServiceMode(Boolean canAddMoreServiceMode) {
        this.canAddMoreServiceMode = canAddMoreServiceMode;
    }
}
