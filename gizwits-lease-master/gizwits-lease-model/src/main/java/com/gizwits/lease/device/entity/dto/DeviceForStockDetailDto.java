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
public class DeviceForStockDetailDto {

    private String sno;

    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
    private Date ctime;

    private String mac;

    private String sN1;

    private String sN2;
    private String iMEI;


    @ApiModelProperty("入库时间")
    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
    private Date entryTime;

    @ApiModelProperty("入库员姓名")
    private String WarehousingName;

    @ApiModelProperty("扫码状态 0、待扫码 1、待入库")
    private String sweepCodeStatusName;


    private String batch;

    @ApiModelProperty("仓库")
    private String launchAreaName;

    @ApiModelProperty(" 产品型号")
    private String categoryType;

    public String getiMEI() {
        return iMEI;
    }

    public void setiMEI(String iMEI) {
        this.iMEI = iMEI;
    }

    public DeviceForStockDetailDto(Device device) {
        BeanUtils.copyProperties(device, this);
    }

    public DeviceForStockDetailDto(DeviceStock device) {
        BeanUtils.copyProperties(device, this);
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getSweepCodeStatusName() {
        return sweepCodeStatusName;
    }

    public void setSweepCodeStatusName(String sweepCodeStatusName) {
        this.sweepCodeStatusName = sweepCodeStatusName;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getLaunchAreaName() {
        return launchAreaName;
    }

    public void setLaunchAreaName(String launchAreaName) {
        this.launchAreaName = launchAreaName;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
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




}
