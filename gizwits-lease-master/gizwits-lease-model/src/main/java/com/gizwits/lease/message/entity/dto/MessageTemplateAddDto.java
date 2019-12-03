package com.gizwits.lease.message.entity.dto;

import com.alibaba.fastjson.JSONObject;
import com.gizwits.lease.message.entity.MessageTemplate;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Description:
 * User: yinhui
 * Date: 2018-01-03
 */
public class MessageTemplateAddDto implements Serializable{

    private Integer templateId;

    /**消息模版*/
    private String content;

    /**推送消息标题*/
    private String  title;
    /**
     * 消息模版类型: 4设备消息 5 租赁消息
     */
    private Integer type;
    /**
     * 产品id
     */
    private Integer productId;
    /**
     * 触发条件 已json的形式
     */
    private String command;

    private Integer serviceId;

    private String serviceName;
    /**
     * 是否依靠数据点上报：0不依靠，1依靠
     */
    private Integer dependOnDataPoint;
    /**
     * 当不依靠数据点时所设判断条件：百分比/数值
     */
    private Double rate;
    /***
     * 1 百分比 2数值
     */
    private Integer rateType;

    public MessageTemplateAddDto(MessageTemplate messageTemplate) {
        BeanUtils.copyProperties(messageTemplate,this);
    }

    public MessageTemplateAddDto() {
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public Integer getProductId() { return productId; }

    public void setProductId(Integer productId) { this.productId = productId; }

    public String getCommand() { return command; }

    public void setCommand(String command) { this.command = command; }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getDependOnDataPoint() {
        return dependOnDataPoint;
    }

    public void setDependOnDataPoint(Integer dependOnDataPoint) {
        this.dependOnDataPoint = dependOnDataPoint;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getRateType() {
        return rateType;
    }

    public void setRateType(Integer rateType) {
        this.rateType = rateType;
    }
}
