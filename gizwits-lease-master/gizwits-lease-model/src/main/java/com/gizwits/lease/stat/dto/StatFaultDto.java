package com.gizwits.lease.stat.dto;

import java.util.Date;

/**
 * Created by GaGi on 2017/8/15.
 */
public class StatFaultDto {
    private Date fromDate;
    private Date toDate;
    private Integer productId;
    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
