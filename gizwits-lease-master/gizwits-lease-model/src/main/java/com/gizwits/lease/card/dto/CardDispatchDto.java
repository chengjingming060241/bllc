package com.gizwits.lease.card.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CardDispatchDto {
    @ApiModelProperty(value = "卡券ID", required = true)
    @NotNull(message = "cardId may not be null")
    private String cardId;

    @ApiModelProperty(value = "微信投放 0:否, 1:是", required = true)
    @NotNull(message = "dispatchWeb may not be null")
    private Integer dispatchWeb;

    @ApiModelProperty(value = "APP投放 0:否, 1:是", required = true)
    @NotNull(message = "dispatchApp may not be null")
    private Integer dispatchApp;

    @ApiModelProperty("卡券封面")
    private String cover;

    @ApiModelProperty("卡券展示顺序")
    @Min(value = 1, message = "只能输入大于等于1的整数值")
    private Integer sequence;

    @ApiModelProperty("卡券适用产品ID")
    private Integer productId;

    @ApiModelProperty("卡券适用运营商ID数组")
    private List<Integer> operatorIdList;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Integer getDispatchWeb() {
        return dispatchWeb;
    }

    public void setDispatchWeb(Integer dispatchWeb) {
        this.dispatchWeb = dispatchWeb;
    }

    public Integer getDispatchApp() {
        return dispatchApp;
    }

    public void setDispatchApp(Integer dispatchApp) {
        this.dispatchApp = dispatchApp;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public List<Integer> getOperatorIdList() {
        return operatorIdList;
    }

    public void setOperatorIdList(List<Integer> operatorIdList) {
        this.operatorIdList = operatorIdList;
    }
}
