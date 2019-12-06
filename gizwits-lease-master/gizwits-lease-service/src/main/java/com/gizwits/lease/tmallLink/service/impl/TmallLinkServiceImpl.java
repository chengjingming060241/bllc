package com.gizwits.lease.tmallLink.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.boot.utils.QueryResolverUtils;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.tmallLink.dao.TmallLinkDao;
import com.gizwits.lease.tmallLink.dto.*;
import com.gizwits.lease.tmallLink.entity.TmallLink;
import com.gizwits.lease.tmallLink.service.TmallLinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TmallLinkServiceImpl extends ServiceImpl<TmallLinkDao, TmallLink> implements TmallLinkService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TmallLinkServiceImpl.class);

    @Autowired
    private SysUserService sysUserService;


    @Override
    public boolean add(TmallLinkForAddDto dto) {
        SysUser currentUserOwner = sysUserService.getCurrentUser();
        Date date = new Date();
        TmallLink tmallLink = new TmallLink();
        BeanUtils.copyProperties(dto, tmallLink);
        tmallLink.setSysUserId(currentUserOwner.getId());
        tmallLink.setSysUserName(currentUserOwner.getNickName());
        tmallLink.setCtime(date);
        tmallLink.setUtime(date);
        LOGGER.info("添加天猫链接【{}】", dto.getLinkName());
        return insert(tmallLink);
    }


    @Override
    public Page<TmallLinkForListDto> page(Pageable<TmallLinkQueryDto> pageable) {
        Page<TmallLink> page = new Page<>();
        BeanUtils.copyProperties(pageable, page);
        Page<TmallLink> selectPage = selectPage(page, QueryResolverUtils.parse(pageable.getQuery(),new EntityWrapper<TmallLink>()));
        Page<TmallLinkForListDto> result = new Page<>();
        BeanUtils.copyProperties(selectPage, result);
        result.setRecords(new ArrayList<>(selectPage.getRecords().size()));
        selectPage.getRecords().forEach(item -> {
            TmallLinkForListDto tmp = new TmallLinkForListDto(item);
            result.getRecords().add(tmp);
        });
        return result;
    }

    @Override
    public TmallLinkForDetailDto detail(Integer id) {
        TmallLink tmallLink = selectById(id);
        if (Objects.isNull(tmallLink)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        return new TmallLinkForDetailDto(tmallLink);
    }


    @Override
    public TmallLinkForDetailDto update(TmallLinkForUpdateDto dto) {
        SysUser currentUserOwner = sysUserService.getCurrentUser();
        TmallLink tmallLink = selectById(dto.getId());
        if (Objects.isNull(tmallLink)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        BeanUtils.copyProperties(dto, tmallLink);
        tmallLink.setUtime(new Date());
        updateById(tmallLink);
        LOGGER.info("修改天猫链接成功,操作员："+currentUserOwner.getRealName());
        return detail(dto.getId());
    }

    @Override
    public String delete(List<Integer> ids) {
        SysUser currentUserOwner = sysUserService.getCurrentUser();
        boolean fails = false;
        List<TmallLink> tmallLinks = selectList(new EntityWrapper<TmallLink>().in("id", ids).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
        if (!ParamUtil.isNullOrEmptyOrZero(tmallLinks)) {
            for (TmallLink tmallLink : tmallLinks) {
                tmallLink.setIsDeleted(DeleteStatus.DELETED.getCode());
                tmallLink.setCtime(new Date());
                fails = updateById(tmallLink);
            }
        }
        if (fails){
            LOGGER.info("删除天猫链接成功,操作员："+currentUserOwner.getRealName());
            return "删除成功";
        }else {
            return "删除失败";
        }

    }


}
