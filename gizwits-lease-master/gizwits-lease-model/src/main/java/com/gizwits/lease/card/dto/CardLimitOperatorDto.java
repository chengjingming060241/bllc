package com.gizwits.lease.card.dto;

import com.gizwits.lease.manager.entity.Operator;

public class CardLimitOperatorDto {

    private Integer id;

    private String name;

    private String province;

    private String city;

    private String area;

    public CardLimitOperatorDto(Operator operator) {
        id = operator.getId();
        name = operator.getName();
        province = operator.getProvince();
        city = operator.getCity();
        area = operator.getArea();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
