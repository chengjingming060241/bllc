package com.gizwits.lease.manager.dto;

import com.gizwits.boot.dto.SysUserForAddDto;
import com.gizwits.boot.validators.Mobile;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Dto - 添加代理商
 *
 * @author lilh
 * @date 2017/7/31 15:37
 */
public class AgentForBindDto {

    @NotNull
    private Integer id;

    /** 是否有系统账号,1:有, 0:无 */
    @NotNull
    private Integer bindingExistAccount;

    /** 绑定的系统账号，bindingExistAccount为1时有效 */
    private Integer bindingAccountId;

    @Valid
    private SysUserForAddDto user;

    private Integer parentAgentId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getParentAgentId() { return parentAgentId; }

    public void setParentAgentId(Integer parentAgentId) { this.parentAgentId = parentAgentId; }
}
