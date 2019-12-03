package com.gizwits.lease.product.vo;

import com.gizwits.lease.product.entity.ProductServiceDetail;

/**
 * Created by GaGi on 2017/8/4.
 */
public class ProductServiceDetailVo {
    private Integer id;
    private Double num;
    private String unit;
    private Double price;

    /**
     * 收费类型
     */
    private String serviceType;

    public ProductServiceDetailVo() {
    }

    public ProductServiceDetailVo(ProductServiceDetail productServiceDetail) {
        this.id = productServiceDetail.getId();
        this.num = productServiceDetail.getNum();
        this.unit = productServiceDetail.getUnit();
        this.price = productServiceDetail.getPrice();
        this.serviceType = productServiceDetail.getServiceType();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

}
