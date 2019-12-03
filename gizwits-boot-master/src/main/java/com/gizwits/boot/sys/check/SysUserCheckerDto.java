package com.gizwits.boot.sys.check;

import java.util.ArrayList;
import java.util.List;

import com.gizwits.boot.dto.SysUserForAddDto;
import com.gizwits.boot.dto.SysUserForUpdateDto;
import org.springframework.beans.BeanUtils;

/**
 * @author lilh
 * @date 2017/7/27 11:04
 */
public class SysUserCheckerDto {

    private Integer id;

    private String username;

    private String mobile;

    private List<Integer> roleIds = new ArrayList<>();

    public SysUserCheckerDto() {
    }

    public SysUserCheckerDto(SysUserForAddDto dto) {
        BeanUtils.copyProperties(dto, this);
    }

    public SysUserCheckerDto(SysUserForUpdateDto dto) {
        BeanUtils.copyProperties(dto, this);
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }
}
