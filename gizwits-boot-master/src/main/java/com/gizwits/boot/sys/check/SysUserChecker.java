package com.gizwits.boot.sys.check;


import com.gizwits.boot.common.Processor;
import com.gizwits.boot.exceptions.SysExceptionEnum;
import com.gizwits.boot.exceptions.SystemException;

/**
 * @author lilh
 * @date 2017/7/27 10:47
 */
public abstract class SysUserChecker implements Processor<SysUserCheckerDto> {

    @Override
    public void process(SysUserCheckerDto t) {
        check(t);
    }

    public void check(SysUserCheckerDto t) {
        if (exist(t)) {
            throw new SystemException(getSysExceptionEnum().getCode(), getSysExceptionEnum().getMessage());
        }
    }

    protected abstract SysExceptionEnum getSysExceptionEnum();

    protected abstract boolean exist(SysUserCheckerDto t);

}
