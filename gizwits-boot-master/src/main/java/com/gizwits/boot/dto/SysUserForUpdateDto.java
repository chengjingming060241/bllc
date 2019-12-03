package com.gizwits.boot.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.gizwits.boot.validators.Mobile;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Dto - 系统账号更新
 *
 * @author lilh
 * @date 2017/7/12 20:02
 */
public class SysUserForUpdateDto {

    private Integer id;

    /** 密码 */
/*    @NotBlank
    @Pattern(regexp = "^[0-9a-zA-Z\\-_%`~!@#\\u0024\\u005E&\\u002A\\u0028\\u0029\\u002B=]+$", message = "参数格式错误")
    @Length(min = 6, max = 18)
    private String password;*/

    /** 用户名 */
    @Length(max = 20, message = "超过最大长度")
    private String nickName;

    /** 手机号 */
    @Mobile
    private String mobile;

    private String email;

    /** 启用禁用 */
    private Integer isEnable;

    private List<Integer> roleIds = new ArrayList<>();

    private SysUserExtForAddDto ext;
    /**
     * 系统名称
     */
    private String sysName;

    /**
     * 系统图标
     */
    private String sysLogo;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public SysUserExtForAddDto getExt() {
        return ext;
    }

    public void setExt(SysUserExtForAddDto ext) {
        this.ext = ext;
    }
}
