package com.gizwits.boot.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Dto - 添加角色
 *
 * @author lilh
 * @date 2017/7/12 14:26
 */
public class SysRoleForAddDto {

    private Integer id;

    /**
     * 数字,字母,中文
     */
    @NotBlank
    @Pattern(regexp = "^[0-9a-zA-Z\\u4e00-\\u9fa5]+$", message = "参数格式错误")
    @Length(max = 20)
    private String roleName;

    @NotBlank
    @Pattern(regexp = "^[0-9a-zA-Z\\u4e00-\\u9fa5]+$", message = "参数格式错误")
    @Length(max = 40)
    private String remark;

    @NotNull
    private Integer isShareData;

    @NotNull
    private Integer shareBenefitType;
    /**
     * 是否可以添加多个收费模式
     */
    private Integer isAddMoreServiceMode;
    /**
     * 共享收费模式数据
     */
    private Integer isShareServiceMode;

    private List<Integer> permissions = new ArrayList<>();

    private List<Integer> versions = new ArrayList<>();

    public List<Integer> getVersions() {
        return versions;
    }

    public void setVersions(List<Integer> versions) {
        this.versions = versions;
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

    public Integer getIsAddMoreServiceMode() { return isAddMoreServiceMode; }

    public void setIsAddMoreServiceMode(Integer isAddMoreServiceMode) { this.isAddMoreServiceMode = isAddMoreServiceMode; }

    public Integer getIsShareServiceMode() { return isShareServiceMode; }

    public void setIsShareServiceMode(Integer isShareServiceMode) { this.isShareServiceMode = isShareServiceMode; }

    public List<Integer> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Integer> permissions) {
        this.permissions = permissions;
    }
}
