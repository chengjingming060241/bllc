package com.gizwits.lease.device.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 * Created by Sunny on 2019/12/24 11:15
 */
@Data
public class PlanUpdateDto {

    private Integer planId;
    @ApiModelProperty("计划内容，格式和添加相同")
    private String content;
}
