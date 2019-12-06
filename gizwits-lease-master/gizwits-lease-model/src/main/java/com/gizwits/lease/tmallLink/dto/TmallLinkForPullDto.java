package com.gizwits.lease.tmallLink.dto;

import com.gizwits.lease.product.entity.Product;

/**
 * Dto - 下拉列表
 *
 * @author lilh
 * @date 2017/7/17 20:20
 */
public class TmallLinkForPullDto {

    private Integer id;

    private String name;

    public TmallLinkForPullDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
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
