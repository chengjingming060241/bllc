package com.gizwits.lease.tmallLink.dto;

import com.gizwits.lease.product.dto.ProductDataPointForUpdateDto;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Dto - 产品更新
 *
 * @author lilh
 * @date 2017/7/20 14:03
 */
public class TmallLinkForUpdateDto {

    @NotNull
    private Integer id;

    @NotBlank
    private String name;

    @NotNull
    private Integer categoryId;

    @NotBlank
    private String categoryName;


    private List<ProductDataPointForUpdateDto> dataPoints = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ProductDataPointForUpdateDto> getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(List<ProductDataPointForUpdateDto> dataPoints) {
        this.dataPoints = dataPoints;
    }
}
