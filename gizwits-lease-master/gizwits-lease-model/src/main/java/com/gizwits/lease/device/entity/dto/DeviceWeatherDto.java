package com.gizwits.lease.device.entity.dto;


import java.util.Date;

public class DeviceWeatherDto {
    /**
     * 城市ID
     */
    private String cityId;
    /**
     * 温度
     */
    private String tmp;
    /**
     * 湿度
     */
    private String hum;
    /**
     * pm2.5
     */
    private String pm25;
    /**
     * 空气质量
     */
    private String qlty;

    private Date utime = new Date();


    public DeviceWeatherDto() {
    }

    public DeviceWeatherDto(String cityId, String tmp, String hum, String pm25, String qlty) {
        this.cityId = cityId;
        this.tmp = tmp;
        this.hum = hum;
        this.pm25 = pm25;
        this.qlty = qlty;
    }


    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getQlty() {
        return qlty;
    }

    public void setQlty(String qlty) {
        this.qlty = qlty;
    }
}
