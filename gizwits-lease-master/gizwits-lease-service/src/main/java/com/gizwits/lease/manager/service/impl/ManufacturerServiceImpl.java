package com.gizwits.lease.manager.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.manager.entity.Agent;
import com.gizwits.lease.manager.entity.Manufacturer;
import com.gizwits.lease.manager.dao.ManufacturerDao;
import com.gizwits.lease.manager.entity.Operator;
import com.gizwits.lease.manager.service.AgentService;
import com.gizwits.lease.manager.service.ManufacturerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.lease.model.DeleteDto;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 厂商(或企业)表 服务实现类
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@Service
public class ManufacturerServiceImpl extends ServiceImpl<ManufacturerDao, Manufacturer> implements ManufacturerService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private DeviceService deviceService;


    @Autowired
    private AgentService agentService;

    @Override
    public List<Integer> resolveBindAccount() {
        SysUser current = sysUserService.getCurrentUserOwner();
        List<Manufacturer> manufacturers = selectList(new EntityWrapper<Manufacturer>().in("sys_user_id", sysUserService.resolveSysUserAllSubAdminIds(current)));
        if (CollectionUtils.isNotEmpty(manufacturers)) {
            return manufacturers.stream().map(Manufacturer::getSysAccountId).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public Manufacturer selectById(Integer id) {
        return selectOne(new EntityWrapper<Manufacturer>().eq("id", id).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
    }


    @Override
    public Manufacturer getBySysAccountId(Integer sysAccountId) {
        return selectOne(new EntityWrapper<Manufacturer>().eq("sys_account_id", sysAccountId).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
    }

    @Override
    public DeleteDto delete(List<Integer> ids) {
        DeleteDto deleteDto = new DeleteDto();
        List<String> fails = new LinkedList<>();
        int count = 0;
        for (Integer id : ids) {
            Manufacturer manufacturer = selectById(id);
            if (!ParamUtil.isNullOrEmptyOrZero(manufacturer)) {
                int num = deviceService.selectCount(new EntityWrapper<Device>().eq("owner_id", manufacturer.getSysAccountId()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
                if (num <= 0) {
                    manufacturer.setUtime(new Date());
                    manufacturer.setIsDeleted(DeleteStatus.DELETED.getCode());
                    updateById(manufacturer);
                    count++;
                }else {
                    fails.add(manufacturer.getName());
                }
            }
        }
        deleteDto.setSucceed(count);
        deleteDto.setFails(fails);
        return deleteDto;
    }

    @Override
    public String getCompanyName(SysUser sysUser) {
        if (sysUser == null) {
            return null;
        }

        Agent agent = agentService.selectOne(new EntityWrapper<Agent>().eq("sys_account_id", sysUser.getId()));
        if (Objects.nonNull(agent)) {
            return agent.getName();
        }
        Manufacturer manufacturer = selectOne(new EntityWrapper<Manufacturer>().eq("sys_account_id", sysUser.getId()));
        if (Objects.nonNull(manufacturer)) {
            return manufacturer.getName();
        }

        if (Objects.equals(sysUser.getSysUserId(), sysUser.getId())) {
            return null;
        } else {
            SysUser parent = sysUserService.selectById(sysUser.getSysUserId());
            return getCompanyName(parent);
        }

    }

    @Override
    public boolean isManufacturer(Integer sysUserId) {
        Wrapper<Manufacturer> wrapper = new EntityWrapper();
        wrapper.eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        wrapper.eq("sys_account_id", sysUserId);
        return selectCount(wrapper) > 0;
    }

    @Override
    public Manufacturer selectByEnterpriseId(String enterpriseId) {
        List<Manufacturer> manufacturers = selectList(new EntityWrapper<Manufacturer>().eq("enterprise_id", enterpriseId).eq("is_deleted", 0));
        if(manufacturers.size() == 0){
            return null;
        }else if(manufacturers.size() > 1){
            LeaseException.throwSystemException(LeaseExceEnums.MANUFACTURER_TOO_MANY);
        }

        return manufacturers.get(0);
    }
}
