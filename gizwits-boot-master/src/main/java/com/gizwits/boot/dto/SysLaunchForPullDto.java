package com.gizwits.boot.dto;

import com.gizwits.boot.sys.entity.SysLaunchArea;
import com.gizwits.boot.sys.entity.SysRole;

/**
 * Dto - 用于下拉选择
 *
 * @author lilh
 * @date 2017/7/11 11:46
 */
public class SysLaunchForPullDto {

    /** 仓库id */
    private Integer id;

    /** 仓库名称 */
    private String name;


    public SysLaunchForPullDto() {}

    public SysLaunchForPullDto(SysLaunchArea sysLaunchArea) {
        this.id = sysLaunchArea.getId();
        this.name = sysLaunchArea.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return name;
    }

    public void setRoleName(String roleName) {
        this.name = roleName;
    }

}
