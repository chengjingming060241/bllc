package com.gizwits.lease.constant;

public class DeviceStockTemplate {

    /** 设备mac */
    private String mac;

    /** 控制器sn1 */
    private String sn1;

    /**sn2 */
    private String sn2;

    /**sn2 */
    private String iMEI;

    public DeviceStockTemplate() {
    }

    public DeviceStockTemplate(String mac, String sn1, String sn2, String iMEI) {
        this.mac = mac;
        this.sn1 = sn1;
        this.sn2 = sn2;
        this.iMEI = iMEI;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSn1() {
        return sn1;
    }

    public void setSn1(String sn1) {
        this.sn1 = sn1;
    }

    public String getSn2() {
        return sn2;
    }

    public void setSn2(String sn2) {
        this.sn2 = sn2;
    }

    public String getiMEI() {
        return iMEI;
    }

    public void setiMEI(String iMEI) {
        this.iMEI = iMEI;
    }
}
