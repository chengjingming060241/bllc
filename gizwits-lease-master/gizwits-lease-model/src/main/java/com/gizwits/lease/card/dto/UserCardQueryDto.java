package com.gizwits.lease.card.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class UserCardQueryDto {

    @ApiModelProperty("设备号")
    @NotNull(message = "sno may not be null")
    private String sno;

    @ApiModelProperty("消费金额（单位为元）")
    @NotNull(message = "price may not be null")
    private BigDecimal price;

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
