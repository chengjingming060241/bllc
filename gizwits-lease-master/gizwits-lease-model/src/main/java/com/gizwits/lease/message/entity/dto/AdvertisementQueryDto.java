package com.gizwits.lease.message.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Description: 广告列表查询dto
 * Created by Sunny on 2019/12/9 14:25
 */
@Data
public class AdvertisementQueryDto {

    @ApiModelProperty("广告名称")
    private String name;
    @ApiModelProperty("链接地址")
    private String url;
    @ApiModelProperty("展示类型，1设备列表页，2滤材页")
    private String type;
    @ApiModelProperty("开始时间")
    private Date fromDate;
    @ApiModelProperty("结束时间")
    private Date toDate;

    @JsonIgnore
    private Integer current;
    @JsonIgnore
    private Integer size;

}
