package com.gizwits.boot.dto;

import com.gizwits.boot.sys.entity.SysPermission;
import com.gizwits.boot.sys.entity.SysVersion;
import org.springframework.beans.BeanUtils;

/**
 * Dto - 权限详情
 *
 * @author lilh
 * @date 2017/7/28 19:44
 */
public class SysPermissionForDetailDto {

    private Integer id;

    private String permissionKey;

    private String permissionName;

    private Integer pPermissionId;

    private String icon;

    private String uri;

    private Integer permissionType;

    private Integer sort;

    private String versionName;

    private String versionCode;

    public SysPermissionForDetailDto(SysPermission permission) {
        BeanUtils.copyProperties(permission, this);
    }

    public SysPermissionForDetailDto(SysVersion version) {
        BeanUtils.copyProperties(version, this);
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermissionKey() {
        return permissionKey;
    }

    public void setPermissionKey(String permissionKey) {
        this.permissionKey = permissionKey;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Integer getpPermissionId() {
        return pPermissionId;
    }

    public void setpPermissionId(Integer pPermissionId) {
        this.pPermissionId = pPermissionId;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
