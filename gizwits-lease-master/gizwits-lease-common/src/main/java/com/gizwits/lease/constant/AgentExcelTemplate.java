package com.gizwits.lease.constant;

/**
 * @author lilh
 * @date 2017/8/31 15:29
 */
public class AgentExcelTemplate {

    /**
     * 仓库名称
     */
    private String name;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区/县
     */
    private String area;
    /**
     * 详细地址
     */
    private String address;

    /**
     * 负责人电话
     */
    private String  mobile;

    public AgentExcelTemplate() {
    }

    public AgentExcelTemplate(String name, String province, String city, String area, String address) {
        this.name = name;
        this.province = (province == null?"":province);
        this.city = city;
        this.area = area;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
