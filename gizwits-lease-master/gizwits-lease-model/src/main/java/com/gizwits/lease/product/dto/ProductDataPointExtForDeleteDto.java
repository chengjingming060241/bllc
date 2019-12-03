package com.gizwits.lease.product.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @author yuqing
 * @date 2018-02-05
 */
public class ProductDataPointExtForDeleteDto {

    @NotEmpty
    @ApiModelProperty("被删除的扩展指令id")
    private List<Integer> ids;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
