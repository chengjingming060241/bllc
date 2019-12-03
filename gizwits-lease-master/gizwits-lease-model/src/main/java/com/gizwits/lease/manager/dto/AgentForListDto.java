package com.gizwits.lease.manager.dto;

import com.gizwits.lease.enums.StatusType;
import com.gizwits.lease.manager.entity.Agent;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * Dto - 代理商列表
 *
 * @author lilh
 * @date 2017/7/31 18:26
 */
public class AgentForListDto {

    private Integer id;

    @ApiModelProperty("代理商名称")
    private String name;

    @ApiModelProperty("出库量")
    private Integer stockNumber;

    private String province;

    private String city;

    private String area;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("备注")
    private Integer remarks;

    @ApiModelProperty("状态")
    private String statusDesc;

    /** 投放点数量 */
    private Integer launchAreaCount = 0;

    /** 设备数量 */
    private Integer deviceCount = 0;

    /** 上级代理商 */
    private String parentAgent;

    /** 代理商等级 */
    private Integer agentLevel;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("系统账号id")
    private Integer sysAccountId;

    @ApiModelProperty("修改时间")
    private Date utime;


    public AgentForListDto(Agent agent) {
        BeanUtils.copyProperties(agent, this);
        this.statusDesc = StatusType.getDesc(this.getStatus());
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }

    public Integer getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(Integer stockNumber) {
        this.stockNumber = stockNumber;
    }

    public Integer getRemarks() {
        return remarks;
    }

    public void setRemarks(Integer remarks) {
        this.remarks = remarks;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAgentLevel() {
        return agentLevel;
    }

    public void setAgentLevel(Integer agentLevel) {
        this.agentLevel = agentLevel;
    }

    public String getParentAgent() {
        return parentAgent;
    }

    public void setParentAgent(String parentAgent) {
        this.parentAgent = parentAgent;
    }

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

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Integer getLaunchAreaCount() {
        return launchAreaCount;
    }

    public void setLaunchAreaCount(Integer launchAreaCount) {
        this.launchAreaCount = launchAreaCount;
    }

    public Integer getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(Integer deviceCount) {
        this.deviceCount = deviceCount;
    }

    public Integer getSysAccountId() { return sysAccountId; }

    public void setSysAccountId(Integer sysAccountId) { this.sysAccountId = sysAccountId; }
}
