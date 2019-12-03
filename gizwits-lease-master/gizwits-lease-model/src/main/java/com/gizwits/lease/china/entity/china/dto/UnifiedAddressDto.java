package com.gizwits.lease.china.entity.china.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 统一规范不同来源的地址信息
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UnifiedAddressDto {

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;


}

