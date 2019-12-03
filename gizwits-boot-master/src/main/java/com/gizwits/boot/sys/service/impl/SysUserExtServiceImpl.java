package com.gizwits.boot.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.event.SysUserExtUpdatedEvent;
import com.gizwits.boot.sys.dao.SysUserExtDao;
import com.gizwits.boot.sys.entity.SysUserExt;
import com.gizwits.boot.sys.service.SysUserExtService;
import com.gizwits.boot.utils.CommonEventPublisherUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 系统用户扩展表 服务实现类
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@Service
public class SysUserExtServiceImpl extends ServiceImpl<SysUserExtDao, SysUserExt> implements SysUserExtService {

    @Override
    public boolean update(SysUserExt ext) {
        publishIfNecessary(ext);
        SysUserExt exist = selectById(ext.getSysUserId());
        if (Objects.nonNull(exist)) {
            ext.setUtime(new Date());
            BeanUtils.copyProperties(ext, exist);
            return updateById(exist);
        } else {
            ext.setCtime(new Date());
            ext.setUtime(new Date());
            return insert(ext);
        }
    }

    private void publishIfNecessary(SysUserExt ext) {
        if (Objects.nonNull(ext.getSysUserId())) {
            SysUserExt source = selectById(ext.getSysUserId());
            if (Objects.nonNull(source)) {
                CommonEventPublisherUtils.publishEvent(new SysUserExtUpdatedEvent(source));
            }
        }
    }

    @Override
    public SysUserExt getSysUserExtByAlipayAppid(String alipayAppid){
        if (StringUtils.isEmpty(alipayAppid)){
            return null;
        }
        EntityWrapper<SysUserExt> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("alipay_appid",alipayAppid).isNotNull("alipay_private_key").isNotNull("alipay_public_key").orderBy("utime",false);

        return selectOne(entityWrapper);
    }

    @Override
    public SysUserExt getSysUserExtByWeixinAppid(String wxAppid){
        if (StringUtils.isEmpty(wxAppid)){
            return null;
        }
        EntityWrapper<SysUserExt> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("wx_appid",wxAppid).isNotNull("wx_app_secret").isNotNull("wx_partner_id").orderBy("utime",false);

        return selectOne(entityWrapper);
    }

    @Override
    public SysUserExt getLatestSysUserExt(){
        EntityWrapper<SysUserExt> entityWrapper = new EntityWrapper<>();
        entityWrapper.isNotNull("wx_appid").isNotNull("wx_app_secret").isNotNull("wx_partner_id").or().isNotNull("alipay_appid").isNotNull("alipay_private_key").isNotNull("alipay_public_key").orderBy("utime",false);

        return selectOne(entityWrapper);
    }
}
