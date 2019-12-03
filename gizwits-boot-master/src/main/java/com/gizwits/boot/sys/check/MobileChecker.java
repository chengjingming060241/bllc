package com.gizwits.boot.sys.check;

import com.gizwits.boot.exceptions.SysExceptionEnum;
import com.gizwits.boot.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Checker - 校验用户手机号
 *
 * @author lilh
 * @date 2017/7/27 10:57
 */
//@Order(10)
//@Component
public class MobileChecker extends SysUserForAddChecker {

    @Autowired
    private SysUserService sysUserService;

    @Override
    protected boolean exist(SysUserCheckerDto dto) {
        return sysUserService.mobileExist(dto.getMobile());
    }

    @Override
    protected SysExceptionEnum getSysExceptionEnum() {
        return SysExceptionEnum.DUP_PHONE_NUMBER;
    }
}
