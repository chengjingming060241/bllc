package com.gizwits.boot.sys.check;

import com.gizwits.boot.exceptions.SysExceptionEnum;
import com.gizwits.boot.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Checker - 校验用户名
 *
 * @author lilh
 * @date 2017/7/27 10:52
 */
@Order(5)
@Component
public class UsernameChecker extends SysUserForAddChecker {

    @Autowired
    private SysUserService sysUserService;

    @Override
    protected boolean exist(SysUserCheckerDto dto) {
        return sysUserService.usernameExist(dto.getUsername());
    }

    @Override
    protected SysExceptionEnum getSysExceptionEnum() {
        return SysExceptionEnum.DUP_USER_NAME;
    }


}
