package com.gizwits.boot.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.sys.entity.SysRole;
import org.springframework.beans.BeanUtils;

/**
 * Dto - 角色列表
 *
 * @author lilh
 * @date 2017/7/11 14:24
 */
public class SysRoleForListDto {

    /** id */
    private Integer id;

    /** 更新时间 */
    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
    private Date utime;

    /** 更新时间 */
    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
    private Date ctime;

    /** 系统帐号数 */
    private Integer sysUserCount = 0;

    /** 角色名称 */
    private String roleName;

    /** 备注 */
    private String remark;

    private Integer shareBenefitType;

    public SysRoleForListDto() {
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public SysRoleForListDto(SysRole role) {
        BeanUtils.copyProperties(role, this);
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

    public Integer getSysUserCount() {
        return sysUserCount;
    }

    public void setSysUserCount(Integer sysUserCount) {
        this.sysUserCount = sysUserCount;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getShareBenefitType() {
        return shareBenefitType;
    }

    public void setShareBenefitType(Integer shareBenefitType) {
        this.shareBenefitType = shareBenefitType;
    }
}
