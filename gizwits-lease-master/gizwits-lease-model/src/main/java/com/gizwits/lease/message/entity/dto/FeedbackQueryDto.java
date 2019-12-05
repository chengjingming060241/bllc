package com.gizwits.lease.message.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gizwits.boot.annotation.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 问题反馈查询dto
 * Created by yinhui on 2017/7/26.
 */
@Data
public class FeedbackQueryDto implements Serializable{


    @ApiModelProperty("反馈内容")
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date begin;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date end;
    @ApiModelProperty("手机号")
    private String mobile;

    @JsonIgnore
    private Integer current;
    @JsonIgnore
    private Integer size;


}
