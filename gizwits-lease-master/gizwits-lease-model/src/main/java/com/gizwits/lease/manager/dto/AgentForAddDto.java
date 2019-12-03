package com.gizwits.lease.manager.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.baomidou.mybatisplus.annotations.TableField;
import com.gizwits.boot.dto.SysUserForAddDto;
import com.gizwits.boot.validators.Mobile;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Dto - 添加代理商
 *
 * @author lilh
 * @date 2017/7/31 15:37
 */
public class AgentForAddDto {


    @ApiModelProperty("运营商名称")
    @NotBlank
    @Pattern(regexp = "^[0-9a-zA-Z\\u4e00-\\u9fa5]+$", message = "参数格式错误")
    @Length(max = 40)
    private String name;

    /** 联系人 */
    @Pattern(regexp = "^[0-9a-zA-Z\\u4e00-\\u9fa5]+$", message = "参数格式错误")
    @Length(max = 20)
    private String contact;


    @ApiModelProperty("手机号")
    @Mobile
    private String mobile;

    @ApiModelProperty("状态：正常、警告、冻结")
    @NotBlank
    private Integer status;

    /** 省 */
    private String province;

    /** 市 */
    @NotBlank
    private String city;

    /** 区/县 */
    @NotBlank
    private String area;

    /** 详细地址 */
    @NotBlank
    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("备注")
    private String remarks;

    /** 是否有系统账号,1:有, 0:无 */

    private Integer bindingExistAccount;

    /** 绑定的系统账号，bindingExistAccount为1时有效 */
    private Integer bindingAccountId;

    @Valid
    private SysUserForAddDto user;

    private Integer coverLevel;

    private Integer parentAgentId;


    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getParentAgentId() {
        return parentAgentId;
    }

    public void setParentAgentId(Integer parentAgentId) {
        this.parentAgentId = parentAgentId;
    }

    public Integer getCoverLevel() {
        return coverLevel;
    }

    public void setCoverLevel(Integer coverLevel) {
        this.coverLevel = coverLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getBindingExistAccount() {
        return bindingExistAccount;
    }

    public void setBindingExistAccount(Integer bindingExistAccount) {
        this.bindingExistAccount = bindingExistAccount;
    }

    public Integer getBindingAccountId() {
        return bindingAccountId;
    }

    public void setBindingAccountId(Integer bindingAccountId) {
        this.bindingAccountId = bindingAccountId;
    }

    public SysUserForAddDto getUser() {
        return user;
    }

    public void setUser(SysUserForAddDto user) {
        this.user = user;
    }

}
