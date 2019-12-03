package com.gizwits.boot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gizwits.boot.enums.SysUserStatus;
import com.gizwits.boot.sys.entity.SysUser;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author lilh
 * @date 2017/8/1 14:24
 */
public class SysUserForBasicDto {

    /** id */
    private Integer id;

    /** 用户名 */
    private String username;

    /** 昵称 */
    private String nickName;

    /** 手机号 */
    private String mobile;

    /** 状态描述 */
    private String statusDesc;

    /** 角色名称，取一个 */
    private String roleName;

    /** 分润权限 */
    private Integer shareBenefitType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date ctime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date utime;

    /** 扩展信息 */
    private SysUserExtForAddDto ext;

    public SysUserForBasicDto(SysUser sysUser) {
        BeanUtils.copyProperties(sysUser, this);
        this.statusDesc = SysUserStatus.getDesc(sysUser.getIsEnable());
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getShareBenefitType() {
        return shareBenefitType;
    }

    public void setShareBenefitType(Integer shareBenefitType) {
        this.shareBenefitType = shareBenefitType;
    }

    public SysUserExtForAddDto getExt() {
        return ext;
    }

    public void setExt(SysUserExtForAddDto ext) {
        this.ext = ext;
    }
}
