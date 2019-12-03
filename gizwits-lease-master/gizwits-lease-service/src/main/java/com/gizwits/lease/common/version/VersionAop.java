package com.gizwits.lease.common.version;

import com.gizwits.boot.base.ResponseObject;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class VersionAop {
	private static final Logger log = LoggerFactory.getLogger(VersionAop.class);

	@Around("@annotation(com.gizwits.lease.common.version.DefaultVersion)")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		String requestVersion = request.getHeader("Version");

		MethodSignature sign = (MethodSignature) joinPoint.getSignature();
		Object target = joinPoint.getTarget();
		Method currentMethod = target.getClass().getMethod(sign.getName(), sign.getParameterTypes());

		// 前端要求版本号，查找版本号相同的方法
		if (!StringUtils.isBlank(requestVersion)) {
			RequestMapping requestMapping = currentMethod.getDeclaredAnnotation(RequestMapping.class);
			PostMapping postMapping = currentMethod.getDeclaredAnnotation(PostMapping.class);
			GetMapping getMapping = currentMethod.getDeclaredAnnotation(GetMapping.class);
			String[] uri = null;
			if(requestMapping != null)uri = requestMapping.value();
			else if(postMapping != null)uri = postMapping.value();
			else if(getMapping != null)uri = getMapping.value();
			Method[] declaredMethods = target.getClass().getDeclaredMethods();
			for (Method method : declaredMethods) {
				Version version = method.getDeclaredAnnotation(Version.class);
				if(version != null && Arrays.equals(version.uri(), uri) && version.version().equals(requestVersion)){
					log.info("请求版本号：{}，执行方法：{}", requestVersion, method.getName());
					return invoke(method, target, joinPoint.getArgs(), version.display());
				}
			}
		}
		log.info("请求版本号：{}，执行默认方法：{}", requestVersion, currentMethod.getName());
		DefaultVersion defaultVersion = currentMethod.getDeclaredAnnotation(DefaultVersion.class);
		return invoke(currentMethod, target, joinPoint.getArgs(), defaultVersion.display());
	}

	private Object invoke(Method method, Object target, Object[] args, String[] display)
			throws InvocationTargetException, IllegalAccessException {
		Object result = null;
		try {
			result = method.invoke(target, args);
		} catch (IllegalAccessException | InvocationTargetException e) {
			if(e.getCause() instanceof RuntimeException){
				throw (RuntimeException)e.getCause();
			}else {
				throw e;
			}
		}
		if (result != null && result instanceof ResponseObject) {
			((ResponseObject) result).setDisplay(display);
		}
		return result;
	}

}
