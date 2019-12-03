package com.gizwits.lease.product.dto;

import com.gizwits.lease.product.entity.Product;
import com.gizwits.lease.product.entity.ProductCategory;

/**
 * Dto - 下拉列表
 *
 * @author lilh
 * @date 2017/7/17 20:20
 */
public class ProductGategoryForPullDto {

    private Integer id;

    private String name;

    public ProductGategoryForPullDto(ProductCategory productCategory) {
        this.id = productCategory.getId();
        this.name = productCategory.getName();
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
