package com.gizwits.boot.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gizwits.boot.enums.SysUserStatus;

/**
 * Dto - 添加系统账号页面
 *
 * @author lilh
 * @date 2017/7/13 9:28
 */
public class SysUserForAddPageDto {

    private List<SysRoleForPullDto> roles = new ArrayList<>();

    private List<SysLaunchForPullDto> launchs = new ArrayList<>();

    private List<UserStatus> statuses = new ArrayList<>();


    public List<SysRoleForPullDto> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRoleForPullDto> roles) {
        this.roles = roles;
    }

    public List<SysLaunchForPullDto> getLaunchs() {
        return launchs;
    }

    public void setLaunchs(List<SysLaunchForPullDto> launchs) {
        this.launchs = launchs;
    }

    public List<UserStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<UserStatus> statuses) {
        this.statuses = statuses;
    }

    public void initStatus(SysUserStatus[] userStatuses) {
        Arrays.stream(userStatuses).forEach(type -> statuses.add(new UserStatus(type)));
    }

    static class UserStatus {
        private Integer code;
        private String desc;

        UserStatus(SysUserStatus status) {
            this.code = status.getCode();
            this.desc = status.getDesc();
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
