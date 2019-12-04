package com.gizwits.lease.device.entity;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

import java.util.Date;

/**
 * <p>
 * 设备库存表
 * </p>
 *
 * @author zhl
 * @since 2017-07-11
 */
@TableName("device_stock")
public class DeviceStock extends Model<DeviceStock> {

    private static final long serialVersionUID = 1L;

    /**
     * 设备序列号
     */
    @TableId("sno")
    private String sno;
    /**
     * 添加时间
     */
    private Date ctime;
    /**
     * 更新时间
     */
    private Date utime;

    /**
     * MAC地址,通讯用
     */
    private String mac;

    /**
     * 控制器码
     */
    @TableField("sn1")
    private String sN1;

    /**
     * SN2码
     */
    @TableField("sn2")
    private String sN2;

    /**
     * IMEI码
     */
    @TableField("imei")
    private String iMEI;


    /**
     * 扫码状态
     */
    @TableField("sweep_code_status")
    private Integer sweepCodeStatus;

    /**
     * 经办人的系统账号
     */
    @TableField("operator_id")
    private Integer operatorId;

    /**
     * 经办人
     */
    @TableField("operator_name")
    private String operatorName;

    /**
     * 入库员的系统账号
     */
    @TableField("warehousing_id")
    private Integer WarehousingId;
    /**
     * 入库员
     */
    @TableField("warehousing_name")
    private String WarehousingName;

    /**
     * 出库员的系统账号
     */
    @TableField("out_of_stock_id")
    private Integer outOfStockId;
    /**
     * 出库员
     */
    @TableField("out_of_stock_name")
    private String outOfStockName;

    /**
     * 仓库ID
     */
    @TableField("launch_area_id")
    private Integer launchAreaId;
    /**
     * 仓库名称
     */
    @TableField("launch_area_name")
    private String launchAreaName;
    /**
     * 所属品类id
     */
    @TableField("product_id")
    private Integer productId;
    /**
     * 所属品类名称
     */
    @TableField("product_name")
    private String productName;

    /**
     * 所属产品Id
     */
    @TableField("product_category_id")
    private Integer productCategoryId;

    /**
     * 入库批次
     */
    @TableField("batch")
    private String batch;

    /**
     * 出库批 次
     */
    @TableField("out_batch")
    private String outBatch;

    /**
     * 库存、设备删除标识，0：未删除，1：已删除
     */
    @TableField("is_deleted")
    private Integer isDeleted;

    /**
     * 入库记录删除标识，0：未删除，1：已删除
     */
    @TableField("is_deleted_put")
    private Integer isDeletedPut;

    /**
     * 出库记录删除标识，0：未删除，1：已删除
     */
    @TableField("is_deleted_out")
    private Integer isDeletedOut;

    /**
     * 经销商ID
     */
    @TableField("agent_id")
    private Integer agentId;
    /**
     * 创建人
     */
    @TableField("sys_user_id")
    private Integer sysUserId;

    /*
     * 入库时间
     */
    @TableField("entry_time")
    private Date entryTime;

    /*
     * 扫码时间
     */
    @TableField("sweep_code_time")
    private Date sweepCodeTime;

    /**
     * 出库时间
     */
    @TableField("shift_out_time")
    private Date shiftOutTime;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 设备控制器类型
     */
    @TableField("control_type")
    private Boolean controlType;

    /**
     * 供应商
     */
    @TableField("supplier_name")
    private String supplierName;

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

    public Integer getOutOfStockId() {
        return outOfStockId;
    }

    public void setOutOfStockId(Integer outOfStockId) {
        this.outOfStockId = outOfStockId;
    }

    public String getOutOfStockName() {
        return outOfStockName;
    }

    public void setOutOfStockName(String outOfStockName) {
        this.outOfStockName = outOfStockName;
    }

    public String getOutBatch() {
        return outBatch;
    }

    public void setOutBatch(String outBatch) {
        this.outBatch = outBatch;
    }

    public Integer getSweepCodeStatus() {
        return sweepCodeStatus;
    }

    public void setSweepCodeStatus(Integer sweepCodeStatus) {
        this.sweepCodeStatus = sweepCodeStatus;
    }

    public Date getSweepCodeTime() {
        return sweepCodeTime;
    }

    public void setSweepCodeTime(Date sweepCodeTime) {
        this.sweepCodeTime = sweepCodeTime;
    }

    public Integer getWarehousingId() {
        return WarehousingId;
    }

    public void setWarehousingId(Integer warehousingId) {
        WarehousingId = warehousingId;
    }

    public String getWarehousingName() {
        return WarehousingName;
    }

    public void setWarehousingName(String warehousingName) {
        WarehousingName = warehousingName;
    }

    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Boolean getControlType() {
        return controlType;
    }

    public void setControlType(Boolean controlType) {
        this.controlType = controlType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getsN1() {
        return sN1;
    }

    public void setsN1(String sN1) {
        this.sN1 = sN1;
    }

    public String getsN2() {
        return sN2;
    }

    public void setsN2(String sN2) {
        this.sN2 = sN2;
    }

    public String getiMEI() {
        return iMEI;
    }

    public void setiMEI(String iMEI) {
        this.iMEI = iMEI;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Integer getLaunchAreaId() {
        return launchAreaId;
    }

    public void setLaunchAreaId(Integer launchAreaId) {
        this.launchAreaId = launchAreaId;
    }

    public String getLaunchAreaName() {
        return launchAreaName;
    }

    public void setLaunchAreaName(String launchAreaName) {
        this.launchAreaName = launchAreaName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public Date getShiftOutTime() {
        return shiftOutTime;
    }

    public void setShiftOutTime(Date shiftOutTime) {
        this.shiftOutTime = shiftOutTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.sno;
    }

}
