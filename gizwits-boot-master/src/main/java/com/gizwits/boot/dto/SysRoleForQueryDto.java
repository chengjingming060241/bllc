package com.gizwits.boot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gizwits.boot.annotation.Query;

import java.util.Date;

/**
 * Dto - 角色搜索
 *
 * @author lilh
 * @date 2017/7/28 14:12
 */
public class SysRoleForQueryDto {

    @Query(field = "role_name", operator = Query.Operator.like)
    private String roleName;

    @Query(field = "ctime", operator = Query.Operator.ge)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @Query(field = "ctime", operator = Query.Operator.le)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
