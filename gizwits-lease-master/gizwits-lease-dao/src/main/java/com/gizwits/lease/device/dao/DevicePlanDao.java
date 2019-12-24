package com.gizwits.lease.device.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gizwits.lease.device.entity.DevicePlan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 * Created by Sunny on 2019/12/24 9:26
 */
@Repository
public interface DevicePlanDao extends BaseMapper<DevicePlan> {

    List<DevicePlan> getAllDevicePlanByUsed();
}
