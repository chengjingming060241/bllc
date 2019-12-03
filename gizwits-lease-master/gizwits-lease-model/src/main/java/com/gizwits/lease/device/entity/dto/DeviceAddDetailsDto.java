package com.gizwits.lease.device.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 添加设备dto
 * Created by yinhui on 2017/7/19.
 */
public class DeviceAddDetailsDto implements Serializable{


    private Integer id;

    private String mac;

    private String sN1;

    private String sN2;

    private String iMEI;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }


    @Override
    public String toString() {
        return "DeviceAddDetailsDto{" +
                "id=" + id +
                ", mac='" + mac + '\'' +
                ", sN1='" + sN1 + '\'' +
                ", sN2='" + sN2 + '\'' +
                ", iMEI='" + iMEI + '\'' +
                '}';
    }
}
