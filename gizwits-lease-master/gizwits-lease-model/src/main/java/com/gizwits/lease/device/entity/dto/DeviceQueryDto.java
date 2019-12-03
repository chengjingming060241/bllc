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
public class DeviceQueryDto{

    @JsonIgnore
    @Query(field = "origin", operator = Query.Operator.ne)
    private Integer ignoreOrigin;

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

    @Query(field = "status")
    private Integer status;

    @Query(field = "sweep_code_status")
    @ApiModelProperty("扫码状态 0、待扫码 1、待入库")
    private Integer sweepCodeStatus;

    @Query(field = "online_status")
    private Integer onlineStatus;

    @Query(field = "work_status")
    private Integer workStatus;

    @Query(field = "owner_name",operator = Query.Operator.like)
    private String ownerName;

    @JsonIgnore
    @Query(field = "fault_status")
    private Integer faultStatus;

    @JsonIgnore
    @Query(field = "`lock` ")
    private Boolean lock;

    @JsonIgnore
    @Query(field = "owner_id", operator = Query.Operator.in)
    private List<Integer> accessableOwnerIds;

    /**
     * 仓库ID列表
     */
    @JsonIgnore
    @Query(field = "launch_area_id", operator = Query.Operator.in)
    private List<Integer> deviceLaunchAreaIdList;

    /** 设备组为空 */
    @JsonIgnore
    @Query(field = "group_id", operator = Query.Operator.isNull, condition = "-1")
    private Integer deviceGroupId;

    @JsonIgnore
    @Query(field = "is_deleted")
    private Integer isDeleted = DeleteStatus.NOT_DELETED.getCode();


    @JsonIgnore
    @Query(field = "sno", operator = Query.Operator.in)
    private List<String> ids;

    /** 排除 */
    @JsonIgnore
    @Query(field = "sno", operator = Query.Operator.notIn)
    private List<String> excludeIds;

    /** 排除待入库的数据 */
    @JsonIgnore
    @Query(field = "status", operator = Query.Operator.ne)
    private Integer excludeStatus = 6;


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


    /** 直接被分配的运营商绑定的账号 */
    private Integer operatorAccountId;

    /** 创建人 */
    private Integer creatorId;

    /**当前用户设备是否分配投放点 1未分配 2已分配*/
    private Integer type;


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

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    public List<Integer> getDeviceLaunchAreaIdList() {
        return deviceLaunchAreaIdList;
    }

    public void setDeviceLaunchAreaIdList(List<Integer> deviceLaunchAreaIdList) {
        this.deviceLaunchAreaIdList = deviceLaunchAreaIdList;
    }

    public Integer getIgnoreOrigin() {
        return ignoreOrigin;
    }

    public void setIgnoreOrigin(Integer ignoreOrigin) {
        this.ignoreOrigin = ignoreOrigin;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getOwnerName() { return ownerName; }

    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public Integer getOperatorAccountId() {
        return operatorAccountId;
    }

    public void setOperatorAccountId(Integer operatorAccountId) {
        this.operatorAccountId = operatorAccountId;
    }

    public List<Integer> getAccessableOwnerIds() {
        return accessableOwnerIds;
    }

    public void setAccessableOwnerIds(List<Integer> accessableOwnerIds) {
        this.accessableOwnerIds = accessableOwnerIds;
    }
    public Integer getWorkStatus() {return workStatus;}

    public void setWorkStatus(Integer workStatus) {this.workStatus = workStatus;}

    public Integer getDeviceGroupId() {
        return deviceGroupId;
    }

    public void setDeviceGroupId(Integer deviceGroupId) {
        this.deviceGroupId = deviceGroupId;
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

    public Integer getFaultStatus() { return faultStatus; }

    public void setFaultStatus(Integer faultStatus) { this.faultStatus = faultStatus; }

    public Integer getType() {return type;}

    public void setType(Integer type) {this.type = type;}

    public Integer getExcludeStatus() {
        return excludeStatus;
    }

    public void setExcludeStatus(Integer excludeStatus) {
        this.excludeStatus = excludeStatus;

    }

}
