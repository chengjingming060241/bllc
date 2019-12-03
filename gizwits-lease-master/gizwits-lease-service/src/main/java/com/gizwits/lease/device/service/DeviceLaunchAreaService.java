package com.gizwits.lease.device.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.lease.device.entity.DeviceLaunchArea;
import com.baomidou.mybatisplus.service.IService;
import com.gizwits.lease.device.entity.dto.DeviceLaunchAreaAssociatedOperatorDto;
import com.gizwits.lease.device.entity.dto.DeviceLaunchAreaListDto;
import com.gizwits.lease.device.entity.dto.DeviceLaunchAreaQueryDto;
import com.gizwits.lease.device.entity.dto.DeviceMaintainerDto;
import com.gizwits.lease.device.entity.dto.MJDeviceLaunchAreaDetail;
import com.gizwits.lease.device.entity.dto.QueryForCreatorDto;
import com.gizwits.lease.device.vo.DeviceLaunchAreaCountVo;
import com.gizwits.lease.model.DeleteDto;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

/**
 * <p>
 * 设备投放点表 服务类
 * </p>
 *
 * @author yinhui
 * @since 2017-07-12
 */
public interface DeviceLaunchAreaService extends IService<DeviceLaunchArea> {

    /**
     * 添加设备投放点
     * @param deviceLaunchArea
     * @return
     */
    boolean addDeviceLaunchArea( DeviceLaunchArea deviceLaunchArea);

    /**
     * 根据id删除一个设备投放点
     * @param areaIds
     * @return
     */
    String deleteDeviceLaunchAreas(List<Integer> areaIds);

    DeviceLaunchArea selectById(Integer id);

    /**
     * 更新投放点信息
     * @param deviceLaunchArea
     * @return
     */
    boolean updateDeviceLaunchArea( DeviceLaunchArea deviceLaunchArea);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
     Page<DeviceLaunchAreaListDto> getDeviceLaunchAreaListPage(Pageable<DeviceLaunchAreaQueryDto> pageable) ;

    /**
     * 未绑定设备列表
     * @param pageable
     * @return
     */
    Page<DeviceLaunchAreaListDto> getUnAllotDeviceLaunchAreaListPage(Pageable<DeviceLaunchAreaQueryDto> pageable);

    /**
     * 判断投放点名称是否存在
     * @param name
     * @return
     */
     boolean deviceLaunchAreaIsExist(String name);

    /**
     * 查询投放点信息
     * @param launchaAreaId
     * @return
     */
     DeviceLaunchArea getLaunchAreaInfoById(Integer launchaAreaId);



    /**
     * 判断投放点是否关联运营商
     * @param id
     */
    boolean checkIfBinded(Integer id);

    Pageable<DeviceLaunchAreaQueryDto> setParam(Pageable<QueryForCreatorDto> data);

    MJDeviceLaunchAreaDetail detail(Integer launchAreaId);

    /**
     * 维护人员
     * @return
     */
    List<DeviceMaintainerDto> getDeviceMaintainerDtos();

    List<DeviceLaunchAreaCountVo> areaListManager();

    /**
     * 通过维护人员id查找设备sno
     * @param maintainerId
     * @return
     */
    List<String> getDeviceSnoByMaintainerId(Integer maintainerId);

    /**
     * 根据维护人员ID查询投放点列表
     */
    List<DeviceLaunchArea> getListByMaintainerId(Integer maintainerId);

    /**
     * 状态切换
     */
    boolean change(DeviceLaunchAreaQueryDto deviceLaunchAreaQueryDto);
}
