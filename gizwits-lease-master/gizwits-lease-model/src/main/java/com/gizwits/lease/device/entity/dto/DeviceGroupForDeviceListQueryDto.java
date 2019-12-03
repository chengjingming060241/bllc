package com.gizwits.lease.device.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gizwits.boot.annotation.Query;

/**
 * Dto - 设备分组中的设备列表查询
 *
 * @author lilh
 * @date 2017/8/15 20:11
 */
public class DeviceGroupForDeviceListQueryDto {

    /** 产品 */
    private Integer productId;

    @JsonIgnore
    @Query(field = "origin", operator = Query.Operator.ne)
    private Integer ignoreOrigin;

    public Integer getIgnoreOrigin() {
        return ignoreOrigin;
    }

    public void setIgnoreOrigin(Integer ignoreOrigin) {
        this.ignoreOrigin = ignoreOrigin;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
