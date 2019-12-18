package com.gizwits.lease.product.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 * Created by Sunny on 2019/12/18 10:47
 */
@Data
public class AppProductQueryDto {

    @ApiModelProperty("类别id")
    private Integer categoryId;
    @ApiModelProperty("名称")
    private String productName;
}
