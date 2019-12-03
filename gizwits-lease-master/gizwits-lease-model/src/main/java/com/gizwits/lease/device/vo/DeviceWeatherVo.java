package com.gizwits.lease.device.vo;


import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class DeviceWeatherVo {


    @ApiModelProperty("设备序列号")
    private String sno;

    @ApiModelProperty("更新时间")
    private Date utime;

    @ApiModelProperty("温度")
    private String tmp;

    @ApiModelProperty("湿度")
    private String hum;

    @ApiModelProperty("pm2.5")
    private String pm25;

    @ApiModelProperty("空气质量")
    private String qlty;


    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
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
