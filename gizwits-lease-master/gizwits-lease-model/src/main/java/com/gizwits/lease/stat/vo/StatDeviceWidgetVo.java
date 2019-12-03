package com.gizwits.lease.stat.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by GaGi on 2017/7/19.
 */
public class StatDeviceWidgetVo {
    // 这里有些是意义重复的字段，但是前端要根据版本显示不同文案，所以多加一个字段做区分
    @ApiModelProperty("累计设备总数")
    private Integer totalCount;
    @ApiModelProperty("设备总数")
    private Integer allCount;
    @ApiModelProperty("新增设备数")
    private Integer newCount;
    @ApiModelProperty("昨日新增设备数")
    private Integer yesterNewCount;
    // private Double orderedPercent;
    @ApiModelProperty("租赁设备数")
    private Integer orderedCount;
    @ApiModelProperty("故障设备数")
    private Integer alarmCount;

    @ApiModelProperty("设备在线率")
    private Double onlineRate;
    @ApiModelProperty("设备活跃率")
    private Double activatedRate;
    // @ApiModelProperty("今日新激活设备数")
    // private Integer todayActivatedCount;

    @ApiModelProperty("故障设备数")
    private Integer faultDeviceCount;
    @ApiModelProperty("设备故障率")
    private Double faultDeviceRate;
    @ApiModelProperty("报警设备数")
    private Integer alertDeviceCount;
    @ApiModelProperty("设备报警率")
    private Double alertDeviceRate;

    public StatDeviceWidgetVo(Integer totalCount, Integer newCount, Integer orderedCount, Integer alarmCount,
                              Double onlineRate, Double activatedRate,
                              Integer faultDeviceCount, Double faultDeviceRate, Integer alertDeviceCount, Double alertDeviceRate) {
        this.totalCount = totalCount;
        this.allCount = totalCount;
        this.newCount = newCount;
        this.yesterNewCount = newCount;
        this.orderedCount = orderedCount;
        this.alarmCount = alarmCount;
        this.onlineRate = onlineRate;
        this.activatedRate = activatedRate;
        // this.todayActivatedCount = todayActivatedCount;

        this.faultDeviceCount = faultDeviceCount;
        this.faultDeviceRate = faultDeviceRate;
        this.alertDeviceCount = alertDeviceCount;
        this.alertDeviceRate = alertDeviceRate;
    }

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    public Integer getYesterNewCount() {
        return yesterNewCount;
    }

    public void setYesterNewCount(Integer yesterNewCount) {
        this.yesterNewCount = yesterNewCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getNewCount() {
        return newCount;
    }

    public void setNewCount(Integer newCount) {
        this.newCount = newCount;
    }

    public Integer getOrderedCount() {
        return orderedCount;
    }

    public void setOrderedCount(Integer orderedCount) {
        this.orderedCount = orderedCount;
    }

    // public Double getOrderedPercent() {
    //     return orderedPercent;
    // }
    //
    // public void setOrderedPercent(Double orderedPercent) {
    //     this.orderedPercent = orderedPercent;
    // }

    public Integer getAlarmCount() {
        return alarmCount;
    }

    public void setAlarmCount(Integer alarmCount) {
        this.alarmCount = alarmCount;
    }

    public Double getOnlineRate() {
        return onlineRate;
    }

    public void setOnlineRate(Double onlineRate) {
        this.onlineRate = onlineRate;
    }

    public Double getActivatedRate() {
        return activatedRate;
    }

    public void setActivatedRate(Double activatedRate) {
        this.activatedRate = activatedRate;
    }

    // public Integer getTodayActivatedCount() {
    //     return todayActivatedCount;
    // }
    //
    // public void setTodayActivatedCount(Integer todayActivatedCount) {
    //     this.todayActivatedCount = todayActivatedCount;
    // }

    public Integer getFaultDeviceCount() {
        return faultDeviceCount;
    }

    public void setFaultDeviceCount(Integer faultDeviceCount) {
        this.faultDeviceCount = faultDeviceCount;
    }

    public Double getFaultDeviceRate() {
        return faultDeviceRate;
    }

    public void setFaultDeviceRate(Double faultDeviceRate) {
        this.faultDeviceRate = faultDeviceRate;
    }

    public Integer getAlertDeviceCount() {
        return alertDeviceCount;
    }

    public void setAlertDeviceCount(Integer alertDeviceCount) {
        this.alertDeviceCount = alertDeviceCount;
    }

    public Double getAlertDeviceRate() {
        return alertDeviceRate;
    }

    public void setAlertDeviceRate(Double alertDeviceRate) {
        this.alertDeviceRate = alertDeviceRate;
    }
}
