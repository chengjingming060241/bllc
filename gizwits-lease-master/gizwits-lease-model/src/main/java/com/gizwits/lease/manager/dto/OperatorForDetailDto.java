package com.gizwits.lease.manager.dto;

import com.gizwits.boot.dto.SysUserForBasicDto;
import com.gizwits.lease.enums.StatusType;
import com.gizwits.lease.manager.entity.Operator;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

/**
 * Dto - 运营详情
 *
 * @author lilh
 * @date 2017/8/1 12:10
 */
public class OperatorForDetailDto {

    //运营商信息

    /** id */
    private Integer id;

    /** 运营商名称 */
    private String name;

    /** 状态 */
    private Integer status;

    /** 状态描述 */
    private String statusDesc;

    /** 省 */
    private String province;

    /** 市 */
    private String city;

    /** 区 */
    private String area;

    /** 详细地址 */
    private String address;

    /** 联系人 */
    private String contact;

    /** 联系人手机号 */
    private String mobile;

    /** 投放点数量 */
    private Integer launchAreaCount;

    /** 设备数量 */
    private Integer deviceCount;

    /** 绑定的系统账号 */
    private Integer sysAccountId;

    /** 创建人 */
    private String sysUserName;


    private String logoUrl;

    private String phone;

    private String description;

    private String webSite;


    /** 运营商扩展信息 **/
    private OperatorExtDto ext;

    //系统帐号信息

    private SysUserForBasicDto account;

    private Integer coverLevel;

    private Integer isAllot;

    @ApiModelProperty("所属代理商系统账号id")
    private Integer parentAgentId;

    private String parentAgentName;

    @ApiModelProperty("所属运营商系统账号id")
    private Integer parentOperatorId;

    private String parentOperatorName;

    @ApiModelProperty("分润规则id")
    private String shareBenefitRuleId;

    @ApiModelProperty("是否可以修改场景方，true可以修改")
    private boolean canModify = true;

    @ApiModelProperty("运营商为用户代充值次数")
    private Integer rechargeCount;

    @ApiModelProperty("运营商为用户代充值金额下限")
    private Integer rechargeCost;

    @ApiModelProperty("运营商充值人数")
    private Integer rechargeNumber;

    @ApiModelProperty("推荐人")
    private String recommenderOpenid;

    @ApiModelProperty("推荐人分润比例")
    private Integer recommenderRules;

    @ApiModelProperty("推荐人昵称")
    private String recommenderName;

    public String getRecommenderName() {
        return recommenderName;
    }

    public void setRecommenderName(String recommenderName) {
        this.recommenderName = recommenderName;
    }

    public String getRecommenderOpenid() {
        return recommenderOpenid;
    }

    public void setRecommenderOpenid(String recommenderOpenid) {
        this.recommenderOpenid = recommenderOpenid;
    }

    public Integer getRecommenderRules() {
        return recommenderRules;
    }

    public void setRecommenderRules(Integer recommenderRules) {
        this.recommenderRules = recommenderRules;
    }

    public Integer getRechargeCount() {
        return rechargeCount;
    }

    public void setRechargeCount(Integer rechargeCount) {
        this.rechargeCount = rechargeCount;
    }

    public Integer getRechargeCost() {
        return rechargeCost;
    }

    public void setRechargeCost(Integer rechargeCost) {
        this.rechargeCost = rechargeCost;
    }

    public Integer getRechargeNumber() {
        return rechargeNumber;
    }

    public void setRechargeNumber(Integer rechargeNumber) {
        this.rechargeNumber = rechargeNumber;
    }


    public Integer getIsAllot() {
        return isAllot;
    }

    public void setIsAllot(Integer isAllot) {
        this.isAllot = isAllot;
    }

    public Integer getCoverLevel() {
        return coverLevel;
    }

    public void setCoverLevel(Integer coverLevel) {
        this.coverLevel = coverLevel;
    }

    public OperatorForDetailDto() {}

    public OperatorForDetailDto(Operator operator) {
        BeanUtils.copyProperties(operator, this);
        this.statusDesc = StatusType.getDesc(operator.getStatus());
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OperatorExtDto getExt() {
        return ext;
    }

    public void setExt(OperatorExtDto ext) {
        this.ext = ext;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public Integer getSysAccountId() {
        return sysAccountId;
    }

    public void setSysAccountId(Integer sysAccountId) {
        this.sysAccountId = sysAccountId;
    }

    public String getSysUserName() {
        return sysUserName;
    }

    public void setSysUserName(String sysUserName) {
        this.sysUserName = sysUserName;
    }

    public SysUserForBasicDto getAccount() {
        return account;
    }

    public void setAccount(SysUserForBasicDto account) {
        this.account = account;
    }

    public Integer getParentAgentId() {
        return parentAgentId;
    }

    public void setParentAgentId(Integer parentAgentId) {
        this.parentAgentId = parentAgentId;
    }

    public Integer getParentOperatorId() {
        return parentOperatorId;
    }

    public void setParentOperatorId(Integer parentOperatorId) {
        this.parentOperatorId = parentOperatorId;
    }

    public String getParentAgentName() { return parentAgentName; }

    public void setParentAgentName(String parentAgentName) { this.parentAgentName = parentAgentName; }

    public String getParentOperatorName() { return parentOperatorName; }

    public void setParentOperatorName(String parentOperatorName) { this.parentOperatorName = parentOperatorName; }

    public String getShareBenefitRuleId() { return shareBenefitRuleId; }

    public void setShareBenefitRuleId(String shareBenefitRuleId) { this.shareBenefitRuleId = shareBenefitRuleId; }

    public boolean isCanModify() { return canModify; }

    public void setCanModify(boolean canModify) { this.canModify = canModify; }
}
