package com.gizwits.boot.dto;

import java.io.Serializable;

/**
 * Description:管理端个人中心
 * User: yinhui
 * Date: 2017-11-21
 */
public class ManageUserInfoDto implements Serializable{
    /**
     * 用户名
     */
    private String username;
    /**
     * 头像地址
     */
    private String advater;
    /**
     * 运营商名称
     */
    private String operatorName;
    /**
     * 性别
     */
    private String genter;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 手机号码
     */
    private String mobile;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAdvater() {
        return advater;
    }

    public void setAdvater(String advater) {
        this.advater = advater;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getGenter() {
        return genter;
    }

    public void setGenter(String genter) {
        this.genter = genter;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
