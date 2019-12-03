package com.gizwits.lease.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gizwits.boot.base.Constants;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Date;


/**微信订单查询dto
 * Created by yinhui on 2017/7/28.
 */
public class WXOrderQueryDto implements Serializable{
    // @NotEmpty
    // private String openId;

    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
    private Date startTime;

    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
    private Date endTime;

    private String cardNum;

    // public String getOpenId() {
    //     return openId;
    // }
    //
    // public void setOpenId(String openId) {
    //     this.openId = openId;
    // }

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

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
}
