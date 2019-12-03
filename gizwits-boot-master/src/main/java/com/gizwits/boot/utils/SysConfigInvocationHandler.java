package com.gizwits.boot.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Objects;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gizwits.boot.annotation.SysConfig;
import com.gizwits.boot.sys.service.SysConfigService;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

/**
 * InvocationHandler - handler
 *
 * @author lilh
 * @date 2017/7/3 18:52
 */
@Component
public class SysConfigInvocationHandler implements InvocationHandler {

    private static ConvertUtilsBean convertUtilsBean;

    @Autowired
    private SysConfigService sysConfigService;

    static {
        convertUtilsBean = new ConvertUtilsBean();
        DateConverter dateConverter = new DateConverter();
        dateConverter.setUseLocaleFormat(true);
        dateConverter.setPattern(com.gizwits.boot.base.Constants.DEFAULT_DATE_PATTERN);
        convertUtilsBean.register(dateConverter, Date.class);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if (methodName.length() > 3) {
            String configKey = StringUtils.uncapitalize(methodName.substring(3));
            Object value = get(configKey, method);
            if (Objects.isNull(value)) {
                value = getDefaultValue(configKey, method);
            }
            return value;
        }
        return null;
    }

    private Object getDefaultValue(String configKey, Method method) {
        SysConfig annotation = AnnotationUtils.getAnnotation(method, SysConfig.class);
        if (Objects.nonNull(annotation)) {
            save(configKey, annotation);
            return convert(annotation.value(), method.getReturnType());
        }
        return null;
    }

    private void save(String configKey, SysConfig annotation) {
        com.gizwits.boot.sys.entity.SysConfig sysConfig = new com.gizwits.boot.sys.entity.SysConfig();
        sysConfig.setConfigKey(configKey);
        sysConfig.setConfigValue(annotation.value());
        sysConfig.setCtime(new Date());
        sysConfig.setRemark(annotation.remark());
        sysConfig.setStatus(1);
        //sysConfig.setType(method.getReturnType().getName());
        sysConfigService.insert(sysConfig);
    }

    private Object get(String configKey, Method method) {
        com.gizwits.boot.sys.entity.SysConfig sysConfig = sysConfigService.selectOne(new EntityWrapper<com.gizwits.boot.sys.entity.SysConfig>().eq("config_key", configKey).eq("status", 1));
        if (Objects.nonNull(sysConfig)) {
            return convert(sysConfig.getConfigValue(), method.getReturnType());
        }
        return null;
    }

    private Object convert(Object value, Class<?> targetClass) {
        return convertUtilsBean.convert(value, targetClass);
    }
}
