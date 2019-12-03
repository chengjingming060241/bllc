package com.gizwits.lease.manager.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.lease.manager.entity.Manufacturer;
import com.gizwits.lease.model.DeleteDto;

/**
 * <p>
 * 厂商(或企业)表 服务类
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
public interface ManufacturerService extends IService<Manufacturer> {

    /**
     * 获取绑定的厂商帐号
     */
    List<Integer> resolveBindAccount();

    Manufacturer selectById(Integer id);

    Manufacturer getBySysAccountId(Integer sysAccountId);

    DeleteDto delete(List<Integer> ids);

    /**
     * 查询系统用户所属公司名称
     */
    String getCompanyName(SysUser sysUser);

    Manufacturer selectByEnterpriseId(String enterpriseId);

    boolean isManufacturer(Integer sysUserId);
}
