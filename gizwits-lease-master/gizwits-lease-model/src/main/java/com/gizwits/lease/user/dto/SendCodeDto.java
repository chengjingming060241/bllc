package com.gizwits.lease.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 * Created by Sunny on 2019/12/13 14:33
 */
@Data
public class SendCodeDto {

    @ApiModelProperty("加密后的手机号")
    private String mobile;
    @ApiModelProperty("类型，loginOrRegister，password")
    private String type;
}
