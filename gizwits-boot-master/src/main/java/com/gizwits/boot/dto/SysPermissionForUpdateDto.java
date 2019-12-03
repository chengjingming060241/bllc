package com.gizwits.boot.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * Dto - 更新权限
 *
 * @author lilh
 * @date 2017/7/13 11:58
 */
public class SysPermissionForUpdateDto {

    @NotNull
    private Integer id;

    @NotBlank
    private String permissionName;

    @NotBlank
    private String icon;

    @NotBlank
    private String uri;

    @NotNull
    private Integer permissionType;

    private String permissionKey;

    private Integer sort;

    private List<SysVersionForAddDto> versions;

    public List<SysVersionForAddDto> getVersions() {
        return versions;
    }

    public void setVersions(List<SysVersionForAddDto> versions) {
        this.versions = versions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Integer permissionType) {
        this.permissionType = permissionType;
    }

    public String getPermissionKey() {
        return permissionKey;
    }

    public void setPermissionKey(String permissionKey) {
        this.permissionKey = permissionKey;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
