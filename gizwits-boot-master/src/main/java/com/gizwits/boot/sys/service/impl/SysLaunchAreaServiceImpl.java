package com.gizwits.boot.sys.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.sys.dao.SysLaunchAreaDao;
import com.gizwits.boot.sys.entity.SysLaunchArea;
import com.gizwits.boot.sys.entity.SysRole;
import com.gizwits.boot.sys.service.SysLaunchAreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SysLaunchAreaServiceImpl extends ServiceImpl<SysLaunchAreaDao, SysLaunchArea> implements SysLaunchAreaService {

    protected Logger logger = LoggerFactory.getLogger("LEASE_LOGGER");


    @Override
    public List<SysLaunchArea> selectAllSysLaunchAreaById() {
        return selectList(new EntityWrapper<SysLaunchArea>().eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
    }
}
