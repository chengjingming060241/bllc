package com.gizwits.lease.device.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gizwits.boot.annotation.Query;
import com.gizwits.boot.enums.DeleteStatus;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;


/**
 * 设备分页查询dto
 * Created by yinhui on 2017/7/19.
 */
public class DeviceStockQueryDto {


    @Query(field = "product_id")
    @ApiModelProperty("品类")
    private Integer productId;

    @Query(field = "sno",operator = Query.Operator.like)
    private String  sno;

    @Query(field = "mac",operator = Query.Operator.like)
    private String mac;

    @Query(field = "name",operator = Query.Operator.like)
    private String deviceName;

    @Query(field = "launch_area_name",operator = Query.Operator.like)
    private String deviceLaunchArea;

    @Query(field = "launch_area_id")
    private String deviceLaunchAreaId;

    @Query(field = "sweep_code_status")
    @ApiModelProperty("扫码状态 0、待扫码 1、待入库")
    private Integer sweepCodeStatus;

    /**
     * 仓库ID列表
     */
    @JsonIgnore
    @Query(field = "launch_area_id", operator = Query.Operator.in)
    private List<Integer> deviceLaunchAreaIdList;

    @JsonIgnore
    @Query(field = "is_deleted")
    private Integer isDeleted = DeleteStatus.NOT_DELETED.getCode();

    @JsonIgnore
    @Query(field = "is_deleted_put")
    private Integer isDeletedPut = DeleteStatus.NOT_DELETED.getCode();

    @JsonIgnore
    @Query(field = "is_deleted_out")
    private Integer isDeletedOut = DeleteStatus.NOT_DELETED.getCode();


    @JsonIgnore
    @Query(field = "sno", operator = Query.Operator.in)
    private List<String> ids;

    /** 排除 */
    @JsonIgnore
    @Query(field = "sno", operator = Query.Operator.notIn)
    private List<String> excludeIds;

    /** 排除待入库的数据 */
//    @JsonIgnore
//    @Query(field = "status", operator = Query.Operator.ne)
//    private Integer excludeStatus = 6;


    @ApiModelProperty("修改时间开始")
    private Date upTimeStart;

    @ApiModelProperty("修改时间结束")
    private Date upTimeEnd;

    @ApiModelProperty("供应商")
    @Query(field = "supplier_name" ,operator = Query.Operator.like)
    private String supplierName;

    @Query(field = "operator_id")
    private Integer operatorId;

    @ApiModelProperty("入库批次")
    @Query(field = "batch")
    private String batch;

    @ApiModelProperty("入库批次")
    @Query(field = "out_batch")
    private String outBatch;


    /** 直接被分配的运营商绑定的账号 */
    private Integer operatorAccountId;

    /** 创建人 */
    private Integer creatorId;

    /**当前用户设备是否分配投放点 1未分配 2已分配*/
    private Integer type;

    public String getOutBatch() {
        return outBatch;
    }

    public void setOutBatch(String outBatch) {
        this.outBatch = outBatch;
    }

    public Integer getIsDeletedPut() {
        return isDeletedPut;
    }

    public void setIsDeletedPut(Integer isDeletedPut) {
        this.isDeletedPut = isDeletedPut;
    }

    public Integer getIsDeletedOut() {
        return isDeletedOut;
    }

    public void setIsDeletedOut(Integer isDeletedOut) {
        this.isDeletedOut = isDeletedOut;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Integer getSweepCodeStatus() {
        return sweepCodeStatus;
    }

    public void setSweepCodeStatus(Integer sweepCodeStatus) {
        this.sweepCodeStatus = sweepCodeStatus;
    }

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

    public List<Integer> getDeviceLaunchAreaIdList() {
        return deviceLaunchAreaIdList;
    }

    public void setDeviceLaunchAreaIdList(List<Integer> deviceLaunchAreaIdList) {
        this.deviceLaunchAreaIdList = deviceLaunchAreaIdList;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceLaunchArea() {
        return deviceLaunchArea;
    }

    public void setDeviceLaunchArea(String deviceLaunchArea) {
        this.deviceLaunchArea = deviceLaunchArea;
    }

    public String getDeviceLaunchAreaId() {return deviceLaunchAreaId;}

    public void setDeviceLaunchAreaId(String deviceLaunchAreaId) {this.deviceLaunchAreaId = deviceLaunchAreaId;}

    public Integer getOperatorAccountId() {
        return operatorAccountId;
    }

    public void setOperatorAccountId(Integer operatorAccountId) {
        this.operatorAccountId = operatorAccountId;
    }


    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }


    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public List<String> getExcludeIds() {
        return excludeIds;
    }

    public void setExcludeIds(List<String> excludeIds) {
        this.excludeIds = excludeIds;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getType() {return type;}

    public void setType(Integer type) {this.type = type;}



}
