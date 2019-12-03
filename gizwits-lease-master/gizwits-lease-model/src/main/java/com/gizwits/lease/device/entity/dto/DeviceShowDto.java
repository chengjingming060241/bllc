package com.gizwits.lease.device.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备列表展示dto
 * Created by yinhui on 2017/7/26.
 */
public class DeviceShowDto implements Serializable{
    private Date ctime;
    private String sno;
    private String mac;
    private String sN1;
    private String sN2;
    private String iMEI;
    private String name;
    private String product;

    @ApiModelProperty("仓库")
    private String launchArea;

    @ApiModelProperty("经销商")
    private String agentName;

    private String serviceMode;
    private Date   lastOnLineTime;
    private String status;
    private String onlineStatus;
    private String workStatusDesc;

    @ApiModelProperty("入库批次")
    private String batch;

    @ApiModelProperty("出库批次")
    private String outBatch;

    @ApiModelProperty("供应商")
    private String supplierName;

    @ApiModelProperty("库存状态")
    private String sweepCodeStatus;

    @ApiModelProperty("入库时间")
    private Date entryTime;

    @ApiModelProperty("出库时间")
    private Date shiftOutTime;

    @ApiModelProperty(" 产品型号")
    private String categoryType;

    @ApiModelProperty("经办人")
    private String operatorName;

    @ApiModelProperty("设备数量")
    private Integer deviceCount;
    
    private Integer workStatus;
    private String gizDid;

    private String province;
    private String city;
    private String area;
    private String address;

    @ApiModelProperty("归属人姓名")
    private String ownerName;

    @ApiModelProperty("备注")
    private String remarks;


    /** 所属运营商 */
    private String belongOperatorName;

    /**
     * 激活状态 0未激活 1已激活
     */
    private Integer activateStatus;
    private String activateStatusDesc;

    private Boolean lock;

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getOutBatch() {
        return outBatch;
    }

    public void setOutBatch(String outBatch) {
        this.outBatch = outBatch;
    }

    public Date getShiftOutTime() {
        return shiftOutTime;
    }

    public void setShiftOutTime(Date shiftOutTime) {
        this.shiftOutTime = shiftOutTime;
    }

    public Integer getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(Integer deviceCount) {
        this.deviceCount = deviceCount;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSweepCodeStatus() {
        return sweepCodeStatus;
    }

    public void setSweepCodeStatus(String sweepCodeStatus) {
        this.sweepCodeStatus = sweepCodeStatus;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
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

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    public String getGizDid() {
        return gizDid;
    }

    public void setGizDid(String gizDid) {
        this.gizDid = gizDid;
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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getLaunchArea() {
        return launchArea;
    }

    public void setLaunchArea(String launchArea) {
        this.launchArea = launchArea;
    }

    public String getServiceMode() {
        return serviceMode;
    }

    public void setServiceMode(String serviceMode) {
        this.serviceMode = serviceMode;
    }

    public Date getLastOnLineTime() {
        return lastOnLineTime;
    }

    public void setLastOnLineTime(Date lastOnLineTime) {
        this.lastOnLineTime = lastOnLineTime;
    }

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getBelongOperatorName() {
        return belongOperatorName;
    }

    public void setBelongOperatorName(String belongOperatorName) {
        this.belongOperatorName = belongOperatorName;
    }

    public String getWorkStatusDesc() {return workStatusDesc;}

    public void setWorkStatusDesc(String workStatusDesc) {this.workStatusDesc = workStatusDesc;}

    public Integer getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(Integer workStatus) {
        this.workStatus = workStatus;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getActivateStatus() {
        return activateStatus;
    }

    public void setActivateStatus(Integer activateStatus) {
        this.activateStatus = activateStatus;
    }

    public String getActivateStatusDesc() {
        return activateStatusDesc;
    }

    public void setActivateStatusDesc(String activateStatusDesc) {
        this.activateStatusDesc = activateStatusDesc;
    }
}
