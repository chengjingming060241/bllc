package com.gizwits.lease.product.dao;

import com.gizwits.lease.product.entity.ProductDataPointExt;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 产品指令配置扩展表 Mapper 接口
 * </p>
 *
 * @author yuqing
 * @since 2018-02-03
 */
public interface ProductDataPointExtDao extends BaseMapper<ProductDataPointExt> {

    int deleteByIdList(@Param("ids") List<Integer> ids);

}