package com.gizwits.lease.device.entity.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 添加设备dto
 * Created by yinhui on 2017/7/19.
 */
public class    DeviceAddDto implements Serializable{


    @ApiModelProperty("deviceAddDetailsDtos 存储mac、sn1、sn2、imei的集合")
    private List<DeviceAddDetailsDto> deviceAddDetailsDtos;

    @ApiModelProperty("备注")
    private String remarks;

    @JsonIgnore
    private Integer uncheckOrigin;

    private Integer productId;

    private String productName;

    @ApiModelProperty("供应商")
    private String supplierName;

    @ApiModelProperty("仓库ID")
    private Integer deviceLaunchAreaId;
    private String deivceLaunchAreaName;


    @ApiModelProperty("控制器类型")
    private Boolean controlType;

    @ApiModelProperty("IMEI模式")
    private Boolean IMEIType;

    @ApiModelProperty("批次")
    private String batch;

    public String getDeivceLaunchAreaName() {
        return deivceLaunchAreaName;
    }

    public void setDeivceLaunchAreaName(String deivceLaunchAreaName) {
        this.deivceLaunchAreaName = deivceLaunchAreaName;
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

    public Boolean getIMEIType() {
        return IMEIType;
    }

    public void setIMEIType(Boolean IMEIType) {
        this.IMEIType = IMEIType;
    }

    public Boolean getControlType() {
        return controlType;
    }

    public void setControlType(Boolean controlType) {
        this.controlType = controlType;
    }

    public List<DeviceAddDetailsDto> getDeviceAddDetailsDtos() {
        return deviceAddDetailsDtos;
    }

    public void setDeviceAddDetailsDtos(List<DeviceAddDetailsDto> deviceAddDetailsDtos) {
        this.deviceAddDetailsDtos = deviceAddDetailsDtos;
    }

    public Integer getUncheckOrigin() {
        return uncheckOrigin;
    }

    public void setUncheckOrigin(Integer uncheckOrigin) {
        this.uncheckOrigin = uncheckOrigin;
    }


    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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


    public Integer getDeviceLaunchAreaId() {
        return deviceLaunchAreaId;
    }

    public void setDeviceLaunchAreaId(Integer deviceLaunchAreaId) {
        this.deviceLaunchAreaId = deviceLaunchAreaId;
    }


    @Override
    public String toString() {
        return "DeviceAddDto{" +
                "deviceAddDetailsDtos=" + deviceAddDetailsDtos +
                ", remarks='" + remarks + '\'' +
                ", uncheckOrigin=" + uncheckOrigin +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", deviceLaunchAreaId=" + deviceLaunchAreaId +
                ", controlType=" + controlType +
                ", IMEIType=" + IMEIType +
                '}';
    }
}
