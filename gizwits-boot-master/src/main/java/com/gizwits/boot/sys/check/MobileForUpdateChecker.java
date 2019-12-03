package com.gizwits.boot.sys.check;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.exceptions.SysExceptionEnum;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Checker - 更新手机号
 *
 * @author lilh
 * @date 2017/7/27 12:30
 */
//@Order(5)
//@Component
public class MobileForUpdateChecker extends SysUserForUpdateChecker {

    @Autowired
    private SysUserService sysUserService;

    @Override
    protected SysExceptionEnum getSysExceptionEnum() {
        return SysExceptionEnum.DUP_PHONE_NUMBER;
    }

    @Override
    protected boolean exist(SysUserCheckerDto dto) {
//        return sysUserService.selectCount(new EntityWrapper<SysUser>().eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("mobile", dto.getMobile()).ne("id", dto.getId())) > 0;
        return false;
    }

}
