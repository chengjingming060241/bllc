package com.gizwits.lease.device.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 * Created by Sunny on 2019/12/24 9:58
 */
@Data
public class PlanAddDto {

    @ApiModelProperty("设备mac")
    private String mac;
    @ApiModelProperty("定时内容,json格式，例：设置每周五10点开机，{\n" +
            "\t\"time\": \"10:00\",\n" +
            "\t\"repeat\": \"fri\",\n" +
            "\t\"status\": true\n" +
            "},如果只执行一次，repeat填写once，星期请以英文单词前三位,多个以，号隔开")
    private String content;

}
