package com.gizwits.boot.dto;

import com.gizwits.boot.validators.Mobile;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

/**
 * Dto - 用于添加帐号
 *
 * @author lilh
 * @date 2017/7/11 11:06
 */
public class  SysUserForAddDto {

    /** 帐号 */
    @NotBlank
    @Pattern(regexp = "^[0-9a-z_A-Z]+$", message = "参数格式错误")
    @Length(min = 5, max = 18)
    private String username;

    /** 密码 */
    @NotBlank
    @Pattern(regexp = "^[0-9a-zA-Z\\-_%`~!@#\\u0024\\u005E&\\u002A\\u0028\\u0029\\u002B=]+$", message = "参数格式错误")
    @Length(min = 6, max = 18)
    private String password;

    /** 用户名 */
    @Length(max = 20, message = "超过最大长度")
    private String nickName;

    /** 手机号 */
    @NotBlank
    @Mobile
    private String mobile;

    /** 启用禁用 */
    @NotNull
    private Integer isEnable;

    private SysUserExtForAddDto ext;

    private Integer isAdmin;

    /**仓库id*/
    private Integer launchAreaId;

    /** 角色 */
    @NotEmpty
    private List<Integer> roleIds = new ArrayList<>();

    /**
     * 系统名称
     */
    private String sysName;

    /**
     * 系统图标
     */
    private String sysLogo;

    public Integer getLaunchAreaId() {
        return launchAreaId;
    }

    public void setLaunchAreaId(Integer launchAreaId) {
        this.launchAreaId = launchAreaId;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getSysLogo() {
        return sysLogo;
    }

    public void setSysLogo(String sysLogo) {
        this.sysLogo = sysLogo;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public SysUserExtForAddDto getExt() {
        return ext;
    }

    public void setExt(SysUserExtForAddDto ext) {
        this.ext = ext;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }
}
