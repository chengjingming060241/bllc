package com.gizwits.lease.product.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by yinhui on 2017/7/11.
 */
public class PriceAndNumDto implements Serializable{
    private Integer id;
    private Double price;
    private Double num;
    @ApiModelProperty("收费详情名称")
    private String name;

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
