package com.gizwits.lease.product.service.impl;

import com.gizwits.lease.product.dao.ProductDataPointDao;
import com.gizwits.lease.product.dto.*;
import com.gizwits.lease.product.entity.ProductDataPoint;
import com.gizwits.lease.product.entity.ProductDataPointExt;
import com.gizwits.lease.product.service.ProductDataPointExtService;
import com.gizwits.lease.service.Application;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author yuqing
 * @date 2018-02-05
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {Application.class})
@Transactional
public class ProductDataPointExtServiceImplTest {

    private static Integer DATA_ID = 0;

    private static final Integer PRODUCT_ID = 1;
    public static final String IDENTITY_NAME = "TEST";
    public static final String SHOW_NAME = "TEST";

    @Autowired
    private ProductDataPointExtService productDataPointExtService;

    @Autowired
    private ProductDataPointDao productDataPointDao;

    @Before
    public void setup() {
        ProductDataPoint dataPoint = new ProductDataPoint();
        dataPoint.setId(DATA_ID);
        dataPoint.setIdentityName(IDENTITY_NAME);
        dataPoint.setCtime(new Date());
        dataPoint.setUtime(new Date());
        dataPoint.setShowName(SHOW_NAME);
        dataPoint.setReadWriteType(IDENTITY_NAME);
        dataPoint.setDataType(IDENTITY_NAME);
        dataPoint.setProductId(PRODUCT_ID);
        productDataPointDao.insert(dataPoint);
        DATA_ID = dataPoint.getId();
    }

    @Test
    public void add() {
        ProductDataPointExtForAddDto addDto =
                new ProductDataPointExtForAddDto();

        addDto.setDataId(DATA_ID);
        addDto.setProductId(PRODUCT_ID);
        addDto.setName("空气质量");
        addDto.setShowEnable(true);
        addDto.setVendor(1);
        addDto.setParam(4);
        ProductDataPointExt ext =
                productDataPointExtService.add(addDto);

        Assert.assertNotNull(ext);
        Assert.assertNotNull(ext.getId());
        Assert.assertEquals(addDto.getDataId(), ext.getDataId());
        Assert.assertEquals(addDto.getProductId(), ext.getProductId());
        Assert.assertEquals(addDto.getName(), ext.getName());
        Assert.assertEquals(addDto.getParam(), ext.getParam());
        Assert.assertEquals(addDto.getVendor(), ext.getVendor());
        Assert.assertNotNull(ext.getCtime());
        Assert.assertNotNull(ext.getUtime());
        Assert.assertEquals(IDENTITY_NAME, ext.getIdentityName());

    }

    @Test
    public void update() {
        ProductDataPointExtForAddDto addDto =
                new ProductDataPointExtForAddDto();

        addDto.setDataId(DATA_ID);
        addDto.setProductId(PRODUCT_ID);
        addDto.setName("空气质量");
        addDto.setShowEnable(true);
        addDto.setVendor(1);
        addDto.setParam(4);
        ProductDataPointExt ext =
                productDataPointExtService.add(addDto);

        Assert.assertNotNull(ext);

        ProductDataPointExtForUpdateDto updateDto =
                new ProductDataPointExtForUpdateDto();
        BeanUtils.copyProperties(addDto, updateDto);
        updateDto.setId(ext.getId());

        ProductDataPointExt ext2 =
                productDataPointExtService.update(updateDto);

        Assert.assertNotNull(ext2);
        Assert.assertNotEquals(ext.getUtime(), ext2.getUtime());
        Assert.assertEquals(IDENTITY_NAME, ext2.getIdentityName());
    }

    @Test
    public void delete() {
        ProductDataPointExtForAddDto addDto =
                new ProductDataPointExtForAddDto();

        addDto.setDataId(DATA_ID);
        addDto.setProductId(PRODUCT_ID);
        addDto.setName("空气质量");
        addDto.setShowEnable(true);
        addDto.setVendor(1);
        addDto.setParam(4);
        ProductDataPointExt ext =
                productDataPointExtService.add(addDto);

        Assert.assertNotNull(ext);

        ProductDataPointExtForDeleteDto deleteDto =
                new ProductDataPointExtForDeleteDto();

        deleteDto.setIds(Arrays.asList(ext.getId()));

        int rows = productDataPointExtService.delete(deleteDto);

        Assert.assertEquals(1, rows);
    }

    @Test
    public void find() {
        ProductDataPointExtForAddDto addDto =
                new ProductDataPointExtForAddDto();

        addDto.setDataId(DATA_ID);
        addDto.setProductId(PRODUCT_ID);
        addDto.setName("空气质量");
        addDto.setShowEnable(true);
        addDto.setVendor(1);
        addDto.setParam(4);
        ProductDataPointExt ext =
                productDataPointExtService.add(addDto);

        Assert.assertNotNull(ext);
        Assert.assertNotNull(ext.getIdentityName());

        ProductDataPointExtForQueryDto queryDto =
                new ProductDataPointExtForQueryDto();

        queryDto.setProductId(PRODUCT_ID);

        List<ProductDataPointExt> results = productDataPointExtService.find(queryDto);

        Assert.assertNotNull(results);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(IDENTITY_NAME, results.get(0).getIdentityName());

        queryDto.setProductId(PRODUCT_ID + 1);

        List<ProductDataPointExt> results2 = productDataPointExtService.find(queryDto);

        Assert.assertNotNull(results2);
        Assert.assertEquals(0, results2.size());

        ProductDataPointExtForQueryResultDto resultDto = new ProductDataPointExtForQueryResultDto(results);

        Assert.assertEquals(1, resultDto.getItems().size());

        Assert.assertEquals(results.get(0).isShowEnable(), resultDto.getItems().get(0).getShowEnable());

        ProductDataPointExtForQueryResultDto resultDto2 = new ProductDataPointExtForQueryResultDto(null);

        Assert.assertNull(resultDto2.getItems());
    }
}
