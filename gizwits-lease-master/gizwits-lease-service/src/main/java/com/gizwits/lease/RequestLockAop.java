package com.gizwits.lease;

import com.gizwits.boot.base.Constants;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.lease.redis.RedisService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class RequestLockAop {
	private final Logger log = LoggerFactory.getLogger(RequestLockAop.class);

	@Autowired
	private RedisService redisService;
	@Autowired
	private SysUserService sysUserService;

	@Around("@annotation(com.gizwits.lease.RequestLock)")
	public Object around(ProceedingJoinPoint call) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String token = request.getHeader(Constants.TOKEN_HEADER_NAME);
		SysUser user = sysUserService.getSysUserByAccessToken(token);
		return redisService.lock(request.getRequestURI(), user.getId().toString(), () -> {
			try {
				return call.proceed();
			} catch (RuntimeException e) {
				throw e;
			} catch (Throwable throwable) {
				throw new RuntimeException(throwable);
			}
		});
	}

}
