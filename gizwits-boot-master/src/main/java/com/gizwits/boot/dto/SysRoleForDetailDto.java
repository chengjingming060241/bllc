package com.gizwits.boot.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.sys.entity.SysPermission;
import com.gizwits.boot.sys.entity.SysRole;
import org.springframework.beans.BeanUtils;

/**
 * Dto - 角色详情
 *
 * @author lilh
 * @date 2017/7/12 15:07
 */
public class SysRoleForDetailDto {

    private Integer id;

    private String roleName;

    private String remark;

    private Integer sysUserCount;

    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
    private Date ctime;

    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
    private Date utime;

    private Integer isShareData;

    private Integer shareBenefitType;

    /**
     * 是否可以添加多个收费模式
     */
    private Integer isAddMoreServiceMode;
    /**
     * 共享收费模式数据
     */
    private Integer isShareServiceMode;


    private List<SysPermissionForDetailDto> permissions = new ArrayList<>();

    public SysRoleForDetailDto(SysRole role) {
        BeanUtils.copyProperties(role, this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getSysUserCount() {
        return sysUserCount;
    }

    public void setSysUserCount(Integer sysUserCount) {
        this.sysUserCount = sysUserCount;
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

    public Integer getIsShareData() {
        return isShareData;
    }

    public void setIsShareData(Integer isShareData) {
        this.isShareData = isShareData;
    }

    public Integer getShareBenefitType() {
        return shareBenefitType;
    }

    public void setShareBenefitType(Integer shareBenefitType) {
        this.shareBenefitType = shareBenefitType;
    }

    public List<SysPermissionForDetailDto> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<SysPermissionForDetailDto> permissions) {
        this.permissions = permissions;
    }

    public Integer getIsAddMoreServiceMode() {
        return isAddMoreServiceMode;
    }

    public void setIsAddMoreServiceMode(Integer isAddMoreServiceMode) {
        this.isAddMoreServiceMode = isAddMoreServiceMode;
    }

    public Integer getIsShareServiceMode() {
        return isShareServiceMode;
    }

    public void setIsShareServiceMode(Integer isShareServiceMode) {
        this.isShareServiceMode = isShareServiceMode;
    }
}
