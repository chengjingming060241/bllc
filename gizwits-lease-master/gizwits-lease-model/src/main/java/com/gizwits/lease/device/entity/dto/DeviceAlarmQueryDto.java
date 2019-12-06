package com.gizwits.lease.device.entity.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 故障信息查询条件Dto
 * Created by yinhui on 2017/7/15.
 */
public class DeviceAlarmQueryDto implements Serializable{

    private String deviceMac;
    private String deviceName;
    private Integer status;
    private Integer type;
    private Integer productId;

    private String content;//内容

    private Date fromDate;
    private Date toDate;

    private Integer pageSize;
    private Integer currentPage;

    private String sno;

    @JsonIgnore
    private Integer start;
    @JsonIgnore
    private Integer end;

    @JsonIgnore
    private List<String> snos;

    @JsonIgnore
    private List<Integer> ids;

    public String getSno() {
        return sno;
    }

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

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getStart() {

        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public List<String> getSnos() {
        return snos;
    }

    public void setSnos(List<String> snos) {
        this.snos = snos;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
