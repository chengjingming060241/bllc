package com.gizwits.lease.device.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by Sunny on 2019/12/17 15:47
 */
@Data
public class AppDeviceMoveDto {

    private List<String> macs=new ArrayList<>();

    @ApiModelProperty("房间号，传入0则移动到默认房间")
    private Integer roomId;
}
