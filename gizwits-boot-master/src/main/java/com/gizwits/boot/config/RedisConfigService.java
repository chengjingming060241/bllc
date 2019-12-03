package com.gizwits.boot.config;

import com.gizwits.boot.utils.ParamUtil;
import groovy.util.logging.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unchecked")
@Service
@Slf4j
public class RedisConfigService {

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;


    private final static String SYS_USER_WITH_URL = "SYS_USER_WITH_URL:";// 储存用户与url之间的访问信息

    /*********************************************************************/
    /************************ 系统用户与请求url的缓存    *********************/
    /*********************************************************************/
    public void cacheSysUserWithUrl(Integer userId , String url) {
        if (ParamUtil.isNullOrEmptyOrZero(userId) || ParamUtil.isNullOrEmptyOrZero(url)) {
            return;
        }
        int expire = 1;
        redisTemplate.opsForValue().set(SYS_USER_WITH_URL+userId ,url, expire, TimeUnit.SECONDS);
    }

    public String getUrlBySysUserId(Integer  userId) {
        if (ParamUtil.isNullOrEmptyOrZero(userId)) {
            return null;
        }
        Object objects = redisTemplate.opsForValue().get(SYS_USER_WITH_URL + userId);
        if (Objects.nonNull(objects)) {
            return (String) objects;
        }
        return null;

    }



}
