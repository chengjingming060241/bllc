package com.gizwits.lease.benefit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Description:个人中心分润信息
 * User: yinhui
 * Date: 2018-06-21
 */
public class UserBenefitInfo {

    @ApiModelProperty("分润规则id，更新时需要传")
    private String id;

    @ApiModelProperty("分润规则名称")
    private String name;

    @ApiModelProperty("账单生成周期 DAY,WEEK,MONTH,YEAR")
    @NotNull(message = "frequency may not be null")
    private String frequency;

    @ApiModelProperty("账单生成周期描述：每天，每周，每月，每年")
    private String frequencyDesc;

    @ApiModelProperty("分润比例 0~100")
    private BigDecimal percent;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date ctime;

    @ApiModelProperty("规则所属")
    private String personal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getFrequencyDesc() {
        return frequencyDesc;
    }

    public void setFrequencyDesc(String frequencyDesc) {
        this.frequencyDesc = frequencyDesc;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getPersonal() { return personal; }

    public void setPersonal(String personal) { this.personal = personal; }
}
