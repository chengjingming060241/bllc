package com.gizwits.lease.message.entity.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Description:
 * Created by Sunny on 2019/12/6 16:29
 */
@Data
public class FeedBackHandleDto {

    @ApiModelProperty("反馈记录id集合")
    private List<Integer> ids;
    @ApiModelProperty("处理内容")
    private String remark;
}
