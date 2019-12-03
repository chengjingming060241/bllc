package com.gizwits.lease.product.dto;

import com.gizwits.lease.product.entity.ProductDataPointExt;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author yuqing
 * @date 2018-02-05
 */
public class ProductDataPointExtForQueryResultDto {

    /**
     * 扩展指令列表
     */
    @ApiModelProperty("扩展指令列表")
    private List<ProductDataPointExtForDetailDto> items;

    /**
     * 第三方接口提供商
     */
    @ApiModelProperty("第三方接口提供商")
    private List<ProductDataPointExtNameValuePair> vendors;

    /**
     * 参数
     */
    @ApiModelProperty("参数列表")
    private List<ProductDataPointExtNameValuePair> params;

    public ProductDataPointExtForQueryResultDto() {
        vendors = new ArrayList<>(2);

        vendors.add(new ProductDataPointExtNameValuePair("和风", 1));
        vendors.add(new ProductDataPointExtNameValuePair("阿里", 2));

        params = new ArrayList<>(4);

        params.add(new ProductDataPointExtNameValuePair("温度", 1));
        params.add(new ProductDataPointExtNameValuePair("湿度", 2));
        params.add(new ProductDataPointExtNameValuePair("PM2.5", 3));
        params.add(new ProductDataPointExtNameValuePair("空气质量", 4));
    }

    public ProductDataPointExtForQueryResultDto(List<ProductDataPointExt> dataList) {
        this();
        items = Optional.ofNullable(dataList).map((list) -> {
            return list.stream().map(ProductDataPointExtForDetailDto::new).collect(Collectors.toList());
        }).orElse(null);
    }

    public List<ProductDataPointExtForDetailDto> getItems() {
        return items;
    }

    public void setItems(List<ProductDataPointExtForDetailDto> items) {
        this.items = items;
    }

    public List<ProductDataPointExtNameValuePair> getVendors() {
        return vendors;
    }

    public void setVendors(List<ProductDataPointExtNameValuePair> vendors) {
        this.vendors = vendors;
    }

    public List<ProductDataPointExtNameValuePair> getParams() {
        return params;
    }

    public void setParams(List<ProductDataPointExtNameValuePair> params) {
        this.params = params;
    }
}
