package com.gizwits.lease.device.entity.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Dto - 设备更新
 *
 * @author lilh
 * @date 2017/7/21 16:29
 */
public class DeviceForUpdateDto {

    /** id */
    @NotBlank
    private String sno;

    /** mac */
    private String mac;

    private String sN1;

    private String sN2;

    private String iMEI;

    /** 设备名称 */
    private String name;

    /** 收费模式id */
    private Integer serviceId;

    private String serviceName;

    /** 投放点id */
    private Integer launchAreaId;

    private String launchAreaName;

    private String remarks;

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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getLaunchAreaId() {
        return launchAreaId;
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
}
