package com.gizwits.lease.card.dto;

import com.gizwits.lease.product.entity.Product;

public class CardLimitProductDto {

    private Integer id;

    private String name;

    public CardLimitProductDto(Product product) {
        id = product.getId();
        name = product.getName();
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
}
