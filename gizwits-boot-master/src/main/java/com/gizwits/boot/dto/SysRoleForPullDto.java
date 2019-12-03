package com.gizwits.boot.dto;

import com.gizwits.boot.sys.entity.SysRole;

/**
 * Dto - 用于下拉选择
 *
 * @author lilh
 * @date 2017/7/11 11:46
 */
public class SysRoleForPullDto {

    /** 角色id */
    private Integer id;

    /** 角色 */
    private String roleName;

    /** 分润权限类型 */
    private Integer shareBenefitType;

    public SysRoleForPullDto() {}

    public SysRoleForPullDto(SysRole sysRole) {
        this.id = sysRole.getId();
        this.roleName = sysRole.getRoleName();
        this.shareBenefitType = sysRole.getShareBenefitType();
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

    public Integer getShareBenefitType() {
        return shareBenefitType;
    }

    public void setShareBenefitType(Integer shareBenefitType) {
        this.shareBenefitType = shareBenefitType;
    }
}
