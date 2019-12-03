package com.gizwits.boot.sys.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.dto.PageDto;
import com.gizwits.boot.dto.SysConfigForUpdateDto;
import com.gizwits.boot.sys.dao.SysConfigDao;
import com.gizwits.boot.sys.entity.SysConfig;
import com.gizwits.boot.sys.service.SysConfigService;
import com.gizwits.boot.utils.ParamUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统配置表,用来动态添加常量配置 服务实现类
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfig> implements SysConfigService {

    @Override
    public Page<SysConfig> getListByPage(PageDto pageDto) {

        //设置条件
        Page<SysConfig> page = new Page<SysConfig>();
        BeanUtils.copyProperties(pageDto, page);
        EntityWrapper<SysConfig> entityWrapper = new EntityWrapper<SysConfig>();
        page.setCondition(null);
        //设置时间条件
        if (!ParamUtil.isNullOrEmptyOrZero(pageDto.getStartTime())) {
            entityWrapper.ge(Constants.CTIME, pageDto.getStartTime());
        }
        if (!ParamUtil.isNullOrEmptyOrZero(pageDto.getEndTime())) {
            entityWrapper.lt(Constants.CTIME, pageDto.getEndTime());
        }
        if (!ParamUtil.isNullOrEmptyOrZero(pageDto.getCondition())){
            Map<String,Object> condition = pageDto.getCondition();
            for (String key:condition.keySet()){
                entityWrapper.like(key,(String)condition.get(key));
            }

        }
        //排序
        entityWrapper.orderBy(page.getOrderByField(), page.isAsc());
        Page<SysConfig> selectPage = selectPage(page, entityWrapper);


        return selectPage;
    }

    @Override
    public boolean update(SysConfigForUpdateDto dto) {
        SysConfig exist = selectById(dto.getId());
        if (Objects.isNull(exist)) {
            return false;
        }
        BeanUtils.copyProperties(dto, exist);
        exist.setUtime(new Date());
        return updateAllColumnById(exist);
    }
}
