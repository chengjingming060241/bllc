package com.gizwits.lease.device.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 产品管理端故障列表
 * Created by yinhui on 2017/9/6.
 */
public class DeviceAlarmListVo implements Serializable {
    /**
     * 故障id
     */
    private Integer alarmId;
    /**
     * 故障名称
     */
    private String name;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 故障发生时间
     */
    private Date happenTime;
    /**
     * 是否处理
     */
    private Integer status;

    private String statusDescription;

    public Integer getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(Integer alarmId) {
        this.alarmId = alarmId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Date getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(Date happenTime) {
        this.happenTime = happenTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}
