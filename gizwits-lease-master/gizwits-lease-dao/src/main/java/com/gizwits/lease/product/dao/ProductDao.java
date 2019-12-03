package com.gizwits.lease.product.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gizwits.lease.product.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 产品表 Mapper 接口
 * </p>
 *
 * @author rongmc
 * @since 2017-06-19
 */
public interface ProductDao extends BaseMapper<Product> {

    List<Product> findAllUseableProduct();

    Product getProductBySno(@Param("sno") String sno);
}