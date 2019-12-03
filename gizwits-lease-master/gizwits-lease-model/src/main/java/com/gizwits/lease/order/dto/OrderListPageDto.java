package com.gizwits.lease.order.dto;

import com.baomidou.mybatisplus.plugins.Page;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Description:
 * User: yinhui
 * Date: 2018-07-03
 */
public class OrderListPageDto implements Serializable{

    private Page<OrderListDto> page;

    private BigDecimal totalMoney;

    public Page<OrderListDto> getPage() {
        return page;
    }

    public void setPage(Page<OrderListDto> page) {
        this.page = page;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }
}
