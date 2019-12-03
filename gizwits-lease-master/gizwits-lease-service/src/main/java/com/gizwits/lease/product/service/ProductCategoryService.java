package com.gizwits.lease.product.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.lease.model.DeleteDto;
import com.gizwits.lease.product.dto.*;
import com.gizwits.lease.product.entity.Product;
import com.gizwits.lease.product.entity.ProductCategory;

/**
 * <p>
 * 产品类型 服务类
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
public interface ProductCategoryService extends IService<ProductCategory> {

    /**
     * 添加
     */
    boolean add(ProductCategoryForAddDto dto);

    /**
     * 分布查询
     */
    Page<ProductCategoryForListDto> page(Pageable<ProductCategoryForQueryDto> queryPage);

    /**
     * 详情
     */
    ProductCategoryForDetailDto detail(Integer id);

    /**
     * 更新
     */
    boolean update(ProductCategoryForUpdateDto dto);

    /**
     * 品类列表
     */
    List<ProductCategory> list(SysUser current);

    String deleted(List<Integer> ids);

    ProductCategory selectById(Integer id);

    /**
     * 获取录入产品页面的数据
     */
    PreProductDto getAddProductPageData();

    /**
     * 获取有权限的产品
     */
    List<ProductCategory> getProductsWithPermission();

    Integer resolveManufacturerAccount(SysUser current);
}
