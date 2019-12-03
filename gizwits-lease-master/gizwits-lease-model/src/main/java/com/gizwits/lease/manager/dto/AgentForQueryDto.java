package com.gizwits.lease.manager.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gizwits.boot.annotation.Query;
import io.swagger.annotations.ApiModelProperty;

/**
 * Dto - 代理商查询
 *
 * @author lilh
 * @date 2017/7/31 18:09
 */
public class AgentForQueryDto {

    @Query(field = "name", operator = Query.Operator.like)
    private String name;

    /**
     * 省
     */
    @Query(field = "province" ,operator = Query.Operator.like)
    private String province;
    /**
     * 市
     */
    @Query(field = "city" ,operator = Query.Operator.like)
    private String city;
    /**
     * 区/县
     */
    @Query(field = "area" ,operator = Query.Operator.like)
    private String area;

    @Query(field = "status")
    private Integer status;

    @Query(field = "is_deleted")
    private Integer isDeleted = 0;

    @JsonIgnore
    @Query(field = "sys_user_id", operator = Query.Operator.in)
    private List<Integer> accessableUserIds;

    @JsonIgnore
    @Query(field = "id", operator = Query.Operator.in)
    private List<Integer> ids;

    @ApiModelProperty("库存选择类型：1.出库总量等于、2.出库总量大于、3.出库总量小于")
    private Integer stockType;


    @ApiModelProperty("库存量")
    private Integer stockNumber;

    @ApiModelProperty("备注")
    private Integer remarks;

    @ApiModelProperty("修改时间开始")
    private Date upTimeStart;

    @ApiModelProperty("修改时间结束")
    private Date upTimeEnd;

    public Date getUpTimeStart() {
        return upTimeStart;
    }

    public void setUpTimeStart(Date upTimeStart) {
        this.upTimeStart = upTimeStart;
    }

    public Date getUpTimeEnd() {
        return upTimeEnd;
    }

    public void setUpTimeEnd(Date upTimeEnd) {
        this.upTimeEnd = upTimeEnd;
    }

    public Integer getStockType() {
        return stockType;
    }

    public void setStockType(Integer stockType) {
        this.stockType = stockType;
    }

    public Integer getRemarks() {
        return remarks;
    }

    public void setRemarks(Integer remarks) {
        this.remarks = remarks;
    }

    public Integer getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(Integer stockNumber) {
        this.stockNumber = stockNumber;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Integer> getAccessableUserIds() {
        return accessableUserIds;
    }

    public void setAccessableUserIds(List<Integer> accessableUserIds) {
        this.accessableUserIds = accessableUserIds;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
