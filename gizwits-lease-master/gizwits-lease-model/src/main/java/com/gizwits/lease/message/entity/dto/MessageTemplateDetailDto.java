package com.gizwits.lease.message.entity.dto;

import com.gizwits.lease.message.entity.MessageTemplate;
import org.springframework.beans.BeanUtils;

import java.awt.*;
import java.io.Serializable;

/**
 * Description:消息模版详情Dto
 * User: yinhui
 * Date: 2018-01-12
 */
public class MessageTemplateDetailDto implements Serializable{

    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 消息模版内容
     */
    private String content;
    /**
     * 触发条件
     */
    private String command;
    /**
     * 消息模版类型：2故障消息  4设备消息 5 租赁消息
     */
    private Integer type;
    private String typedesc;
    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 当提醒类型为租赁类型时：填写收费模式id
     */
    private Integer serviceId;
    /**
     * 当提醒类型为租赁类型时：填写收费模式名称
     */
    private String serviceName;
    /**
     * 是否依靠数据点上报：0不依靠，1依靠
     */
    private Integer dependOnDataPoint;
    /**
     * 当不依靠数据点时所设判断条件百分比
     */
    private Double rate;

    private Integer rateType;

    public MessageTemplateDetailDto(MessageTemplate messageTemplate) {
        BeanUtils.copyProperties(messageTemplate,this);
    }

    public MessageTemplateDetailDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommand() { return command; }

    public void setCommand(String command) { this.command = command; }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypedesc() {
        return typedesc;
    }

    public void setTypedesc(String typedesc) {
        this.typedesc = typedesc;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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
