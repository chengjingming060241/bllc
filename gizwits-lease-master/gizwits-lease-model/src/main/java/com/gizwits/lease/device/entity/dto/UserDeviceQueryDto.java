package com.gizwits.lease.device.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDeviceQueryDto {


    private Integer roomId;

    @JsonIgnore
    private Integer current;
    @JsonIgnore
    private Integer size;
    @JsonIgnore
    private Integer userId;
}
