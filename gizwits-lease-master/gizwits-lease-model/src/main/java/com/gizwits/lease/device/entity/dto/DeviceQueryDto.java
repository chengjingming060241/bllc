package com.gizwits.lease.device.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gizwits.boot.annotation.Query;
import com.gizwits.boot.enums.DeleteStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * 设备分页查询dto
 * Created by yinhui on 2017/7/19.
 */
@Data
public class DeviceQueryDto{

    @ApiModelProperty("设备mac")
    private String mac;
    @ApiModelProperty("所属产品")
    private Integer productId;

    @ApiModelProperty("sn1")
    private String sn1;

    @ApiModelProperty("设备名称")
    private String name;

    @ApiModelProperty("设备状态，1正常，2故障，3报警，4串货")
    private Integer workStatus;

    @ApiModelProperty("在线状态，1在线，2离线")
    private Integer onlineStatus;

    @ApiModelProperty("开始时间")
    private Date fromDate;

    @ApiModelProperty("结束时间")
    private Date toDate;

    @JsonIgnore
    private Integer current;
    @JsonIgnore
    private Integer size;

}
