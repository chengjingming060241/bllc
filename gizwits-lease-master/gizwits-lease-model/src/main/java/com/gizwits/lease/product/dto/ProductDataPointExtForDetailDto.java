package com.gizwits.lease.product.dto;

import com.gizwits.lease.product.entity.ProductDataPointExt;
import org.springframework.beans.BeanUtils;

/**
 * @author yuqing
 * @date 2018-02-05
 */
public class ProductDataPointExtForDetailDto extends ProductDataPointExtForUpdateDto {

    public ProductDataPointExtForDetailDto() {}

    public ProductDataPointExtForDetailDto(ProductDataPointExt item) {
        BeanUtils.copyProperties(item, this);
        this.setShowEnable(item.isShowEnable());
    }

}
