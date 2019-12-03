package com.gizwits.lease.manager.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.gizwits.boot.dto.SysUserExtForAddDto;
import com.gizwits.boot.validators.Mobile;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * Dto - 更新代理商
 *
 * @author lilh
 * @date 2017/8/1 14:38
 */
public class AgentForUpdateDto {

    @NotNull
    private Integer id;

    @NotBlank
    @Pattern(regexp = "^[0-9a-zA-Z\\u4e00-\\u9fa5]+$", message = "参数格式错误")
    @Length(max = 40)
    private String name;


    private String province;

    @NotBlank
    private String city;

    private String area;

    @NotBlank
    @Pattern(regexp = "^[0-9a-zA-Z\\u4e00-\\u9fa5]+$", message = "参数格式错误")
    @Length(max = 20)
    @ApiModelProperty("联系人")
    private String contact;

    @NotBlank
    @Mobile
    @ApiModelProperty("联系电话")
    private String mobile;

    @Email
    @ApiModelProperty("电子邮件")
    private String email;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("备注")
    private Integer remarks;

    /** 微信相关信息 */
    private Account account;

    private Integer coverLevel;

    private Integer status;

    private List<Integer> agentIds;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRemarks() {
        return remarks;
    }

    public void setRemarks(Integer remarks) {
        this.remarks = remarks;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Integer> getAgentIds() {
        return agentIds;
    }

    public void setAgentIds(List<Integer> agentIds) {
        this.agentIds = agentIds;
    }

    public Integer getCoverLevel() {
        return coverLevel;
    }

    public void setCoverLevel(Integer coverLevel) {
        this.coverLevel = coverLevel;
    }

    /** 微信相关信息 */
    //private SysUserExtForAddDto ext;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public static class Account {
        private Integer id;

        /** 微信相关信息 */
        private SysUserExtForAddDto ext;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public SysUserExtForAddDto getExt() {
            return ext;
        }

        public void setExt(SysUserExtForAddDto ext) {
            this.ext = ext;
        }
    }
}
