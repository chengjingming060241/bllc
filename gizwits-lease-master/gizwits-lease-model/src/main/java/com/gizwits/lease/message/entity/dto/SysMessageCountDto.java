package com.gizwits.lease.message.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gizwits.boot.annotation.Query;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统消息查询dto
 * Created by yinhui on 2017/7/26.
 */
public class SysMessageCountDto implements Serializable{


    @Query(field = "message_type",operator = Query.Operator.eq)
    private Integer messageType;

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }


}
