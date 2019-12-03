package com.gizwits.lease.benefit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * User: yinhui
 * Date: 2018-05-05
 */
public class ShareProfitSettingDto {

    @ApiModelProperty("分润规则id，更新时需要传")
    private String id;

    @ApiModelProperty("分润规则名称")
    private String name;

    @ApiModelProperty("账单生成周期 DAY,WEEK,MONTH,YEAR")
    @NotNull(message = "frequency may not be null")
    private String frequency;

    @ApiModelProperty("账单生成周期描述：每天，每周，每月，每年")
    private String frequencyDesc;

    @ApiModelProperty("分润层级比例")
    private List<ShareProfitLevelPercentDto> ratioList;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date ctime;

    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date utime;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getFrequencyDesc() { return frequencyDesc; }

    public void setFrequencyDesc(String frequencyDesc) { this.frequencyDesc = frequencyDesc; }

    public List<ShareProfitLevelPercentDto> getRatioList() {
        return ratioList;
    }

    public void setRatioList(List<ShareProfitLevelPercentDto> ratioList) {
        this.ratioList = ratioList;
    }

    public Date getCtime() { return ctime; }

    public void setCtime(Date ctime) { this.ctime = ctime; }

    public Date getUtime() { return utime; }

    public void setUtime(Date utime) { this.utime = utime; }
}

