package com.gizwits.lease.benefit.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Description:
 * User: yinhui
 * Date: 2018-05-05
 */
public class ShareProfitLevelPercentDto {

    @ApiModelProperty("分润层级 1厂商，2代理商，3运营商，4子运营商")
    @NotNull(message = "level may not be null")
    private Integer level;

    @ApiModelProperty("分润比例 0~100")
    @NotNull(message = "percent may not be null")
    @Range(min = 0, max = 100, message = "percent must be [0, 100]")
    private BigDecimal percent;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public BigDecimal getPercent() { return percent; }

    public void setPercent(BigDecimal percent) { this.percent = percent; }
}
