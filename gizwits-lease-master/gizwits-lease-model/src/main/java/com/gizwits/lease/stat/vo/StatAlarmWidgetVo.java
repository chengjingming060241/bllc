package com.gizwits.lease.stat.vo;

/**
 * Created by GaGi on 2017/7/19.
 */
public class StatAlarmWidgetVo {
    private Integer warnCount;
    private Integer warnRecord;
    private Double alarmPercent;

    private Integer totalCount;

    private Integer faultCount; //故障

    private Double faultPercent;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Double getFaultPercent() {
        return faultPercent;
    }

    public void setFaultPercent(Double faultPercent) {
        this.faultPercent = faultPercent;
    }

    public Integer getFaultCount() {
        return faultCount;
    }

    public void setFaultCount(Integer faultCount) {
        this.faultCount = faultCount;
    }
    public StatAlarmWidgetVo() {

    }
    public StatAlarmWidgetVo(Integer warnCount, Integer warnRecord, Double alarmPercent) {
        this.warnCount = warnCount;
        this.warnRecord = warnRecord;
        this.alarmPercent = alarmPercent;
    }

    public Integer getWarnCount() {

        return warnCount;
    }

    public void setWarnCount(Integer warnCount) {
        this.warnCount = warnCount;
    }

    public Integer getWarnRecord() {
        return warnRecord;
    }

    public void setWarnRecord(Integer warnRecord) {
        this.warnRecord = warnRecord;
    }

    public Double getAlarmPercent() {
        return alarmPercent;
    }

    public void setAlarmPercent(Double alarmPercent) {
        this.alarmPercent = alarmPercent;
    }
}
