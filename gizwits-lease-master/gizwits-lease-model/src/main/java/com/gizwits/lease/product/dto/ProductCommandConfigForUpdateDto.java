package com.gizwits.lease.product.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author lilh
 * @date 2017/7/20 14:57
 */
public class ProductCommandConfigForUpdateDto {

    @NotNull
    private Integer id;

    @NotNull
    private Integer productId;

    @NotBlank
    private String name;

    @NotBlank
    private String command;

    private Integer isShow;

    private String workingMode;

    private Integer isFree;

    private Integer isClockCorrect;

    private String clockCorrectDatapoint;

    /**
     * 换算数据点单位
     */
    private Integer calculateValue;

    /**
     * 误差范围
     */
    private Integer errorRange;


    /** 续费时参考的数据点 */
    private String refDataPoint;

    public String getClockCorrectDatapoint() {
        return clockCorrectDatapoint;
    }

    public void setClockCorrectDatapoint(String clockCorrectDatapoint) {
        this.clockCorrectDatapoint = clockCorrectDatapoint;
    }

    public Integer getIsClockCorrect() {
        return isClockCorrect;
    }

    public void setIsClockCorrect(Integer isClockCorrect) {
        this.isClockCorrect = isClockCorrect;
    }

    public Integer getCalculateValue() {
        return calculateValue;
    }

    public void setCalculateValue(Integer calculateValue) {
        this.calculateValue = calculateValue;
    }

    public Integer getErrorRange() {
        return errorRange;
    }

    public void setErrorRange(Integer errorRange) {
        this.errorRange = errorRange;
    }

    public String getWorkingMode() {
        return workingMode;
    }

    public void setWorkingMode(String workingMode) {
        this.workingMode = workingMode;
    }

    public Integer getIsFree() {
        return isFree;
    }

    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public String getRefDataPoint() {
        return refDataPoint;
    }

    public void setRefDataPoint(String refDataPoint) {
        this.refDataPoint = refDataPoint;
    }
}
