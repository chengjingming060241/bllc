package com.gizwits.lease.device.service;

import com.baomidou.mybatisplus.service.IService;
import com.gizwits.lease.device.entity.DevicePlan;
import com.gizwits.lease.device.entity.dto.PlanAddDto;
import com.gizwits.lease.device.entity.dto.PlanUpdateDto;

import java.util.List;

/**
 * Description:
 * Created by Sunny on 2019/12/24 9:32
 */
public interface DevicePlanService extends IService<DevicePlan> {

    /**
     * @description 设备计划列表
     * @param  * @param mac
     */
    List<DevicePlan> list(String mac);
    /**
     * @description 设备添加定时
     * @param  * @param planAddDto
    */
    Boolean addPlan(PlanAddDto planAddDto);
    /**
     * @description 删除定时
     * @param  * @param id
     */
    Boolean delete(Integer id);
    /**
     * @description 修改计划
     * @param  * @param updateDto
     */
    Boolean update(PlanUpdateDto updateDto);
    /**
     * @description 开启或关闭计划
     * @param  * @param updateDto
     */
    Boolean openOrClosePlan(Integer planId);
}
