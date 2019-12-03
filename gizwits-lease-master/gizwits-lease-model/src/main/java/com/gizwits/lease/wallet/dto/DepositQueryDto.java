package com.gizwits.lease.wallet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gizwits.boot.annotation.Query;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 押金列表查询dto
 * Created by yinhui on 2017/8/25.
 */
public class DepositQueryDto implements Serializable {
    /**手机号码*/
    private String mobile;

    /**用户昵称*/
    private String nickName;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date begin;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date end;

    @JsonIgnore
    private Integer walletType;

    @JsonIgnore
    /**分页大小*/
    private Integer pagesize;

    @JsonIgnore
    /**分页开始页*/
    private Integer start;

    @JsonIgnore
    /**所拥有的用户id*/
    private List<Integer> userIds;

    @JsonIgnore
    private Integer status;

    public String getMobile() {return mobile;}

    public void setMobile(String mobile) {this.mobile = mobile;}

    public String getNickName() {return nickName;}

    public void setNickName(String nickName) {this.nickName = nickName;}

    public Date getBegin() {return begin;}

    public void setBegin(Date begin) {this.begin = begin;}

    public Date getEnd() {return end;}

    public void setEnd(Date end) {this.end = end;}

    public Integer getWalletType() {return walletType;}

    public void setWalletType(Integer walletType) {this.walletType = walletType;}

    public Integer getPagesize() {return pagesize;}

    public void setPagesize(Integer pagesize) {this.pagesize = pagesize;}

    public Integer getStart() {return start;}

    public void setStart(Integer start) {this.start = start;}

    public List<Integer> getUserIds() {return userIds;}

    public void setUserIds(List<Integer> userIds) {this.userIds = userIds;}

    public Integer getStatus() {return status;}

    public void setStatus(Integer status) {this.status = status;}
}
