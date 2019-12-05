package com.gizwits.lease.stat.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by GaGi on 2017/7/17.
 */
public class StatLocationVo implements Comparable<StatLocationVo>{
    private String province;
    private Number count=0;
    private Number proportion=0.0;

    @JsonIgnore
    private Integer deviceCount;

    public Integer getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(Integer deviceCount) {
        this.deviceCount = deviceCount;
    }

    public Number getProportion() {
        return proportion;
    }

    public void setProportion(Number proportion) {
        this.proportion = proportion;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Number getCount() {
        return count;
    }

    public void setCount(Number count) {
        this.count = count;
    }

    @Override
    public int compareTo(StatLocationVo o) {
        int i = this.getDeviceCount() - o.getDeviceCount();
        return i;
    }
}
