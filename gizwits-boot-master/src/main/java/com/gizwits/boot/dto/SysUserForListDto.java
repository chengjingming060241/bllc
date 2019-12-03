package com.gizwits.boot.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.sys.entity.SysUser;
import org.springframework.beans.BeanUtils;

/**
 * Dto - 系统账号列表
 *
 * @author lilh
 * @date 2017/7/12 11:27
 */
public class SysUserForListDto {

    /** id */
    private Integer id;

    /** 修改时间 */
    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
    private Date utime;

    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
    private Date ctime;

    /** 账号 */
    private String username;

    /** 昵称 */
    private String nickName;

    /** 手机号 */
    private String mobile;

    /** 角色名称 */
    private String roleName;

    private Integer isEnable;

    /** 状态 */
    private String statusDesc;

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public SysUserForListDto(SysUser sysUser) {
        BeanUtils.copyProperties(sysUser, this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
}
