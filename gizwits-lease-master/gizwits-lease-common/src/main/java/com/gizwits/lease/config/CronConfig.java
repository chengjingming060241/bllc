package com.gizwits.lease.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by rongmc on 2017/7/20.
 */
@Configuration
@ConfigurationProperties(prefix = "cron")
public class CronConfig {
    private String orderAnalysis;
    private String deviceTrend;
    private String userTrend;
    private String deviceLocation;
    private String userLocation;
    private String deviceOrderWidget;
    private String userWidget;
    private String faultAlertType;

    private String everyTenMin;
    private String everyFiveMin;
    private String daily;
    private String deviceWeather;
    private String everyHour;

    private String everyMin;

    private String appid;

    private String partner;

    private String partnerkey;

    private String path;

    /**
     * 机智云企业id
     */

    private String gizwitsEnterpriseId;

    /**
     * 机智云企业secret
     */

    private String gizwitsEnterpriseSecret;

    public String getGizwitsEnterpriseId() {
        return gizwitsEnterpriseId;
    }

    public void setGizwitsEnterpriseId(String gizwitsEnterpriseId) {
        this.gizwitsEnterpriseId = gizwitsEnterpriseId;
    }

    public String getGizwitsEnterpriseSecret() {
        return gizwitsEnterpriseSecret;
    }

    public void setGizwitsEnterpriseSecret(String gizwitsEnterpriseSecret) {
        this.gizwitsEnterpriseSecret = gizwitsEnterpriseSecret;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getPartnerkey() {
        return partnerkey;
    }

    public void setPartnerkey(String partnerkey) {
        this.partnerkey = partnerkey;
    }

    public String getDeviceWeather() {
        return deviceWeather;
    }

    public void setDeviceWeather(String deviceWeather) {
        this.deviceWeather = deviceWeather;
    }

    public String getEveryFiveMin() {
        return everyFiveMin;
    }

    public void setEveryFiveMin(String everyFiveMin) {
        this.everyFiveMin = everyFiveMin;
    }

    public String getOrderAnalysis() {
        return orderAnalysis;
    }

    public void setOrderAnalysis(String orderAnalysis) {
        this.orderAnalysis = orderAnalysis;
    }

    public String getFaultAlertType() {
        return faultAlertType;
    }

    public void setFaultAlertType(String faultAlertType) {
        this.faultAlertType = faultAlertType;
    }

    public String getDeviceTrend() {
        return deviceTrend;
    }

    public void setDeviceTrend(String deviceTrend) {
        this.deviceTrend = deviceTrend;
    }

    public String getUserTrend() {
        return userTrend;
    }

    public void setUserTrend(String userTrend) {
        this.userTrend = userTrend;
    }

    public String getDeviceLocation() {
        return deviceLocation;
    }

    public void setDeviceLocation(String deviceLocation) {
        this.deviceLocation = deviceLocation;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getDeviceOrderWidget() {
        return deviceOrderWidget;
    }

    public void setDeviceOrderWidget(String deviceOrderWidget) {
        this.deviceOrderWidget = deviceOrderWidget;
    }

    public String getUserWidget() {
        return userWidget;
    }

    public void setUserWidget(String userWidget) {
        this.userWidget = userWidget;
    }

    public String getEveryTenMin() {
        return everyTenMin;
    }

    public void setEveryTenMin(String everyTenMin) {
        this.everyTenMin = everyTenMin;
    }

    public String getDaily() {
        return daily;
    }

    public void setDaily(String daily) {
        this.daily = daily;
    }

    public String getEveryHour() {
        return everyHour;
    }

    public void setEveryHour(String everyHour) {
        this.everyHour = everyHour;
    }

    public String getEveryMin() { return everyMin; }

    public void setEveryMin(String everyMin) { this.everyMin = everyMin; }
}
