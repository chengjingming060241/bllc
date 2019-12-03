package com.gizwits.lease.device.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;

import java.util.Date;

/**
 * Created by zhl on 2017/9/9.
 */
public class DeviceUsingVo {
    private String sno;
    private String mac;
    private String deviceName;
    private String status;
    private String statusCode;
    private String orderNo;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date payTime;
    private String productKey;
    private Double quantity;
    private String unit;

    @ApiModelProperty("在线状态")
    private Integer onlineStatus;
    @ApiModelProperty("在线状态描述")
    private String onlineStatusDescription;
    /**收费模式id*/
    private Integer serviceId;
    /**收费名称*/
    private String serviceName;

    @ApiModelProperty("当前时间")
    private Date now;

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getOnlineStatusDescription() {
        return onlineStatusDescription;
    }

    public void setOnlineStatusDescription(String onlineStatusDescription) {
        this.onlineStatusDescription = onlineStatusDescription;
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

    public Date getNow() { return now; }

    public void setNow(Date now) { this.now = now; }
}
