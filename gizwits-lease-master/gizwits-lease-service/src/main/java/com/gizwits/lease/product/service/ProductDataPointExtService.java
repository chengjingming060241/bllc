package com.gizwits.lease.product.service;

import com.gizwits.lease.product.dto.ProductDataPointExtForAddDto;
import com.gizwits.lease.product.dto.ProductDataPointExtForDeleteDto;
import com.gizwits.lease.product.dto.ProductDataPointExtForQueryDto;
import com.gizwits.lease.product.dto.ProductDataPointExtForUpdateDto;
import com.gizwits.lease.product.entity.ProductDataPointExt;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 产品指令配置扩展表 服务类
 * </p>
 *
 * @author yuqing
 * @since 2018-02-03
 */
public interface ProductDataPointExtService extends IService<ProductDataPointExt> {

    /**
     * 添加扩展指令，返回添加的结果
     * @param params
     * @return
     */
    ProductDataPointExt add(ProductDataPointExtForAddDto params);

    /**
     * 修改扩展指令，返回修改的结果
     * @param params
     * @return
     */
    ProductDataPointExt update(ProductDataPointExtForUpdateDto params);

    /**
     * 删除扩展指令，返回删除的数量
     * @param params
     * @return
     */
    int delete(ProductDataPointExtForDeleteDto params);

    /**
     * 查找扩展指令
     * @param params
     * @return
     */
    List<ProductDataPointExt> find(ProductDataPointExtForQueryDto params);

}
