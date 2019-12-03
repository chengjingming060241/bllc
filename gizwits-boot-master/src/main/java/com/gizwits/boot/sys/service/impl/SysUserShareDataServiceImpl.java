package com.gizwits.boot.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gizwits.boot.dto.SysUserShareDataDto;
import com.gizwits.boot.enums.SysUserShareDataEnum;
import com.gizwits.boot.sys.dao.SysUserShareDataDao;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.entity.SysUserShareData;
import com.gizwits.boot.sys.service.SysUserShareDataService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户共享数据表 服务实现类
 * </p>
 *
 * @author 
 * @since 2018-02-09
 */
@Service
public class SysUserShareDataServiceImpl extends ServiceImpl<SysUserShareDataDao, SysUserShareData> implements SysUserShareDataService {

    @Override
    public boolean isSysUserShareData(SysUser sysUser, SysUserShareDataEnum shareDataEnum) {
        Wrapper<SysUserShareData> wrapper = new EntityWrapper<>();
        wrapper.eq("sys_user_id", sysUser.getId());
        wrapper.eq("share_data", shareDataEnum.getCode());
        return selectCount(wrapper) > 0;
    }

    @Override
    public SysUserShareDataDto getSysUserShareData(SysUser sysUser) {
        Wrapper<SysUserShareData> wrapper = new EntityWrapper<>();
        wrapper.eq("sys_user_id", sysUser.getId());
        List<SysUserShareData> list = selectList(wrapper);
        List<String> shareDataList = list.stream().map(item -> item.getShareData()).collect(Collectors.toList());
        SysUserShareDataDto dto = new SysUserShareDataDto();
        dto.setProductServiceMode(shareDataList.contains(SysUserShareDataEnum.PRODUCT_SERVICE_MODE.getCode()));
        return dto;
    }

    @Override
    public void updateSysUserShareData(SysUser sysUser, SysUserShareDataDto dto) {
        Map<String, Boolean> shareDataMap = new ObjectMapper().convertValue(dto, Map.class);
        if (shareDataMap != null) {
            Date time = new Date();
            shareDataMap.forEach((key, value) -> {
                if (value != null) {
                    if (value) {
                        baseMapper.shareData(sysUser.getId(), key, time);
                    } else {
                        baseMapper.unshareData(sysUser.getId(), key);
                    }
                }
            });
        }
    }
}
