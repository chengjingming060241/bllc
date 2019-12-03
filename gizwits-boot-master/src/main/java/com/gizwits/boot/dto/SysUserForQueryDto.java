package com.gizwits.boot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gizwits.boot.annotation.Query;

import java.util.Date;

/**
 * Created by zhl on 2018/1/11.
 */
public class SysUserForQueryDto {
    @Query(field = "username", operator = Query.Operator.like)
    private String username ;

    @Query(field = "nick_name", operator = Query.Operator.like)
    private String nickname ;

    @Query(field = "mobile", operator = Query.Operator.like)
    private String mobile;

    @Query(field = "ctime", operator = Query.Operator.ge)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @Query(field = "ctime", operator = Query.Operator.le)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    @JsonIgnore
    @Query(field = "is_deleted")
    private Integer isDeleted =0;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
