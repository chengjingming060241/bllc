package com.gizwits.lease.device.entity.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gizwits.boot.annotation.Query;

import java.io.Serializable;

/**
 * Description:根据sno查询dto
 * User: yinhui
 * Date: 2018-01-22
 */
public class DeviceSnoDto implements Serializable{

    @Query(field = "sno")
    private String sno;

    @Query(field = "user_id")
    private Integer userId;

    @JsonIgnore
    @Query(field = "is_bind")
    private Integer isBind =1;


    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public Integer getIsBind() { return isBind; }

    public void setIsBind(Integer isBind) { this.isBind = isBind; }

    public Integer getUserId() { return userId; }

    public void setUserId(Integer userId) { this.userId = userId; }
}
