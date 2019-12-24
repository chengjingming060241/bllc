package com.gizwits.lease.device.entity.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * Description:
 * Created by Sunny on 2019/12/24 17:34
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
public class ControlDto {
    private String productKey;

    private String mac;

    private String did;

    private Map<String, Object> attrs;
}
