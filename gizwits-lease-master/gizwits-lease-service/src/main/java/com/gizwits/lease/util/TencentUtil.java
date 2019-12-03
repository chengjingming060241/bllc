package com.gizwits.lease.util;

import com.alibaba.fastjson.JSONObject;
import com.gizwits.boot.sys.entity.SysUserExt;
import com.gizwits.boot.utils.CommonEventPublisherUtils;
import com.gizwits.boot.utils.HttpUtil;
import com.gizwits.boot.utils.SignUtils;
import com.gizwits.boot.utils.SysConfigUtils;
import com.gizwits.lease.app.utils.ContextUtil;
import com.gizwits.lease.benefit.entity.ShareBenefitSheet;
import com.gizwits.lease.config.CommonSystemConfig;
import com.gizwits.lease.enums.ShareBenefitSheetStatusType;
import com.gizwits.lease.event.ShareBenefitPayRecordEvent;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.model.*;
import com.gizwits.lease.redis.RedisService;
import com.gizwits.lease.weixin.util.News;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zhl on 2017/2/8.
 */
@Component
public class TencentUtil {

    private static final String UserInfoUrl = "https://graph.qq.com/user/get_user_info?access_token=YOUR_ACCESS_TOKEN&oauth_consumer_key=YOUR_APP_ID&openid=YOUR_OPENID&format=json";

    public static String getUserInfoByToken(String openid, String accessToken) {
        String tencentAppId = SysConfigUtils.get(CommonSystemConfig.class).getTencentAppId();
        String url = UserInfoUrl.replace("YOUR_APP_ID", tencentAppId).replace("YOUR_OPENID", openid).replace("YOUR_ACCESS_TOKEN", accessToken);
        System.out.println("====getUserInfo URL===:"+url);
        return HttpUtil.executeGet(url);
    }

}