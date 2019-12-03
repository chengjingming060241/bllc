package com.gizwits.lease.product.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.product.dao.ProductDataPointDao;
import com.gizwits.lease.product.dto.ProductDataPointExtForAddDto;
import com.gizwits.lease.product.dto.ProductDataPointExtForDeleteDto;
import com.gizwits.lease.product.dto.ProductDataPointExtForQueryDto;
import com.gizwits.lease.product.dto.ProductDataPointExtForUpdateDto;
import com.gizwits.lease.product.entity.ProductCommandConfig;
import com.gizwits.lease.product.entity.ProductDataPoint;
import com.gizwits.lease.product.entity.ProductDataPointExt;
import com.gizwits.lease.product.dao.ProductDataPointExtDao;
import com.gizwits.lease.product.service.ProductDataPointExtService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 产品指令配置扩展表 服务实现类
 * </p>
 *
 * @author yuqing
 * @since 2018-02-03
 */
@Service
public class ProductDataPointExtServiceImpl extends ServiceImpl<ProductDataPointExtDao, ProductDataPointExt> implements ProductDataPointExtService {

    @Autowired
    private ProductDataPointExtDao productCommandConfigExtDao;

    @Autowired
    private ProductDataPointDao productDataPointDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ProductDataPointExt add(ProductDataPointExtForAddDto params) {
        ProductDataPoint dataPoint = productDataPointDao.selectById(params.getDataId());
        if (dataPoint == null) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        ProductDataPointExt ext = new ProductDataPointExt();
        BeanUtils.copyProperties(params, ext);
        ext.setCtime(new Date());
        ext.setUtime(ext.getCtime());
        ext.setIdentityName(dataPoint.getIdentityName());
        insert(ext);
        return ext;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ProductDataPointExt update(ProductDataPointExtForUpdateDto params) {
        ProductDataPoint dataPoint = productDataPointDao.selectById(params.getDataId());
        if (dataPoint == null) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        ProductDataPointExt ext = new ProductDataPointExt();
        ext.setUtime(new Date());
        ext.setIdentityName(dataPoint.getIdentityName());
        BeanUtils.copyProperties(params, ext);
        updateById(ext);
        return ext;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int delete(ProductDataPointExtForDeleteDto params) {
        return productCommandConfigExtDao.deleteByIdList(params.getIds());
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class, readOnly = true)
    public List<ProductDataPointExt> find(ProductDataPointExtForQueryDto params) {
        Wrapper<ProductDataPointExt> wrapper = new EntityWrapper<>();
        wrapper.eq("product_id", params.getProductId());
        List<ProductDataPointExt> list = selectList(wrapper);
        return list;
    }
}
