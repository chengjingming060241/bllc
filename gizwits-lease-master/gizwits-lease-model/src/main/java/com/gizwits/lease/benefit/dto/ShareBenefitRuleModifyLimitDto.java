package com.gizwits.lease.benefit.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class ShareBenefitRuleModifyLimitDto {

    @ApiModelProperty("首次账单生成前可修改次数")
    @NotNull(message = "beforeBillLimit may not be null")
    private Integer beforeBillLimit;

    @ApiModelProperty("周期单位 1:每年, 2:每月")
    @NotNull(message = "periodUnit may not be null")
    @Range(min = 1, max = 2)
    private Integer periodUnit;

    @ApiModelProperty("周期内可修改次数")
    @NotNull(message = "periodLimit may not be null")
    private Integer periodLimit;

    @ApiModelProperty("可允许修改时段 开始时间")
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty("可允许修改时段 结束时间")
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty("可允许修改时段 1:(星期一) ... 7:(星期日)")
    @NotEmpty(message = "weekdayList may not be empty")
    private List<Integer> weekdayList;

    public Integer getBeforeBillLimit() {
        return beforeBillLimit;
    }

    public void setBeforeBillLimit(Integer beforeBillLimit) {
        this.beforeBillLimit = beforeBillLimit;
    }

    public Integer getPeriodUnit() {
        return periodUnit;
    }

    public void setPeriodUnit(Integer periodUnit) {
        this.periodUnit = periodUnit;
    }

    public Integer getPeriodLimit() {
        return periodLimit;
    }

    public void setPeriodLimit(Integer periodLimit) {
        this.periodLimit = periodLimit;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<Integer> getWeekdayList() {
        return weekdayList;
    }

    public void setWeekdayList(List<Integer> weekdayList) {
        this.weekdayList = weekdayList;
    }
}
