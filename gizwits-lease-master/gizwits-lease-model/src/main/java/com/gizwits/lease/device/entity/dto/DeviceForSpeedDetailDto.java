package com.gizwits.lease.device.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gizwits.boot.base.Constants;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.DeviceStock;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;
import java.util.Date;

/**
 * Dto - 设备详情
 *
 * @author lilh
 * @date 2017/7/21 11:36
 */
public class DeviceForSpeedDetailDto {

    private String sno;

    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
    private Date ctime;

    private String mac;

    private String sN1;

    private String sN2;

    private String iMEI;

    @ApiModelProperty("控制器")
    private Boolean controlType;

    @ApiModelProperty("扫码时间")
    private Date sweepCodeTime;

    @ApiModelProperty("入库时间")
    private Date entryTime;

    @ApiModelProperty("出库时间")
    private Date shiftOutTime;

    @ApiModelProperty("扫码员姓名")
    private String operatorName;

    @ApiModelProperty("入库员姓名")
    private String WarehousingName;

    @ApiModelProperty("出库员姓名")
    private String outOfStockName;

    /*备注*/
    private String remarks;

    @ApiModelProperty(" 产品型号")
    private String categoryType;

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public DeviceForSpeedDetailDto(Device device) {
        BeanUtils.copyProperties(device, this);
    }

    public DeviceForSpeedDetailDto(DeviceStock device) {
        BeanUtils.copyProperties(device, this);
    }

    public Date getShiftOutTime() {
        return shiftOutTime;
    }

    public void setShiftOutTime(Date shiftOutTime) {
        this.shiftOutTime = shiftOutTime;
    }

    public String getOutOfStockName() {
        return outOfStockName;
    }

    public void setOutOfStockName(String outOfStockName) {
        this.outOfStockName = outOfStockName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getSweepCodeTime() {
        return sweepCodeTime;
    }

    public void setSweepCodeTime(Date sweepCodeTime) {
        this.sweepCodeTime = sweepCodeTime;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getWarehousingName() {
        return WarehousingName;
    }

    public void setWarehousingName(String warehousingName) {
        WarehousingName = warehousingName;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
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

    public Boolean getControlType() {
        return controlType;
    }

    public void setControlType(Boolean controlType) {
        this.controlType = controlType;
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


    @Override
    public String toString() {
        return "DeviceForSpeedDetailDto{" +
                "sno='" + sno + '\'' +
                ", ctime=" + ctime +
                ", mac='" + mac + '\'' +
                ", sN1='" + sN1 + '\'' +
                ", sN2='" + sN2 + '\'' +
                ", iMEI='" + iMEI + '\'' +
                ", controlType=" + controlType +
                ", sweepCodeTime=" + sweepCodeTime +
                ", entryTime=" + entryTime +
                ", operatorName='" + operatorName + '\'' +
                ", WarehousingName='" + WarehousingName + '\'' +
                ", remarks='" + remarks + '\'' +
                ", categoryType='" + categoryType + '\'' +
                '}';
    }
}
