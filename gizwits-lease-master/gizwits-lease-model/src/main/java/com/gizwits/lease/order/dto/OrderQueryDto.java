package com.gizwits.lease.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 订单查询dto
 * Created by yinhui on 2017/7/20.
 */
public class OrderQueryDto implements Serializable{
    private  Integer deviceLaunchAreaId;
    private  String deviceLaunchArea;
    private  String deviceMac;
    private  String userName;
    private Integer userId;
    private  String orderNo;
    private  String sno ;
//    @ApiModelProperty("归属人姓名")
//    private String personal;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endTime;

    private  Integer status;

//    @JsonIgnore
//    private List<Integer> sysUserIds;

    private  Integer currentPage;
    private Integer pagesize;
    @JsonIgnore
    private  Integer begin;


    private Integer operatorAccountId;

    private String mobile;
    @JsonIgnore
    private List<String> ids;

    /** 卡支付时的卡号 */
    private String payCardNum;

    @JsonIgnore
    private Integer payType;

    /**
     * 当前登录人的层主id
     */
    @ApiModelProperty( "订单所属人id")
    private Integer currentId;

    @JsonIgnore
    private int isMana;

    private Integer abnormalHandleStatus;

	public Integer getAbnormalHandleStatus() {
		return abnormalHandleStatus;
	}

	public void setAbnormalHandleStatus(Integer abnormalHandleStatus) {
		this.abnormalHandleStatus = abnormalHandleStatus;
	}

	public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDeviceLaunchAreaId() {return deviceLaunchAreaId;}

    public void setDeviceLaunchAreaId(Integer deviceLaunchAreaId) {this.deviceLaunchAreaId = deviceLaunchAreaId;}

    public String getDeviceLaunchArea() {return deviceLaunchArea;}

    public void setDeviceLaunchArea(String deviceLaunchArea) {this.deviceLaunchArea = deviceLaunchArea;}

    public String getDeviceMac() {return deviceMac;}

    public void setDeviceMac(String deviceMac) {this.deviceMac = deviceMac;}

    public String getSno() { return sno; }

    public void setSno(String sno) { this.sno = sno; }

    public String getUserName() {return userName;}

    public void setUserName(String userName) {this.userName = userName;}

    public String getOrderNo() {return orderNo;}

    public void setOrderNo(String orderNo) {this.orderNo = orderNo;}

//    public String getPersonal() { return personal; }
//
//    public void setPersonal(String personal) { this.personal = personal; }

    public Date getBeginTime() {return beginTime;}

    public void setBeginTime(Date beginTime) {this.beginTime = beginTime;}

    public Date getEndTime() {return endTime;}

    public void setEndTime(Date endTime) {this.endTime = endTime;}

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

//    public List<Integer> getSysUserIds() {
//        return sysUserIds;
//    }
//
//    public void setSysUserIds(List<Integer> sysUserIds) {
//        this.sysUserIds = sysUserIds;
//    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }


    public Integer getBegin() {
        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }


    public Integer getOperatorAccountId() {
        return operatorAccountId;
    }

    public void setOperatorAccountId(Integer operatorAccountId) {
        this.operatorAccountId = operatorAccountId;
    }


    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getPayCardNum() {
        return payCardNum;
    }

    public void setPayCardNum(String payCardNum) { this.payCardNum = payCardNum; }

    public Integer getCurrentId() { return currentId; }

    public void setCurrentId(Integer currentId) { this.currentId = currentId; }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public int getIsMana() { return isMana; }

    public void setIsMana(int isMana) { this.isMana = isMana; }
}
