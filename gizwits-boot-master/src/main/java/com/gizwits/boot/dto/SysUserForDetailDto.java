package com.gizwits.boot.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.sys.entity.SysUser;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

/**
 * Dto - 系统账号详情
 *
 * @author lilh
 * @date 2017/7/12 19:38
 */
public class SysUserForDetailDto {

    private Integer id;

    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
    private Date ctime;

    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
    private Date utime;

    /** 帐号 */
    private String username;

    /** 密码 */
    @JsonIgnore
    private String password;

    /** 用户名 */
    private String nickName;

    /** 手机号 */
    private String mobile;

    /** 启用禁用 */
    private Integer isEnable;

    /** 状态 */
    private String statusDesc;

    private String email;

    private String avatar;

    private SysUserExtForAddDto ext;

    private List<Integer> roleIds = new ArrayList<>();

    private List<SysRoleForDetailDto> roles = new ArrayList<>();

    private List<Integer> shareBenefitType = new ArrayList<>();

    private List<SysPermissionForDetailDto> permissions = new ArrayList<>();

    /**
     * 系统名称
     */
    private String sysName;

    /**
     * 系统图标
     */
    private String sysLogo;
    private Integer isAdmin;
    private boolean belongManufacturer;
    private Integer is_admin;

    @ApiModelProperty("共享数据")
    private SysUserShareDataDto shareData;

    public Integer getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(Integer is_admin) {
        this.is_admin = is_admin;
    }

    public boolean getBelongManufacturer() {
        return belongManufacturer;
    }

    public void setBelongManufacturer(boolean belongManufacturer) {
        this.belongManufacturer = belongManufacturer;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer admin) {
        isAdmin = admin;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public SysUserForDetailDto() {
    }

    public SysUserForDetailDto(SysUser user) {
        BeanUtils.copyProperties(user, this);
    }

    public List<SysRoleForDetailDto> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRoleForDetailDto> roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
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

    public List<Integer> getShareBenefitType() { return shareBenefitType; }

    public void setShareBenefitType(List<Integer> shareBenefitType) { this.shareBenefitType = shareBenefitType; }

    public List<SysPermissionForDetailDto> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<SysPermissionForDetailDto> permissions) {
        this.permissions = permissions;
    }

    public SysUserShareDataDto getShareData() {
        return shareData;
    }

    public void setShareData(SysUserShareDataDto shareData) {
        this.shareData = shareData;
    }
}
