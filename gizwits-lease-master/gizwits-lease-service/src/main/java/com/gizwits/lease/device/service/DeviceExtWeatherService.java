package com.gizwits.lease.device.service;

import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.DeviceExtWeather;
import com.baomidou.mybatisplus.service.IService;
import com.gizwits.lease.device.vo.DeviceWeatherVo;

/**
 * <p>
 * 设备-天气拓展表 服务类
 * </p>
 *
 * @author zxhuang
 * @since 2018-02-02
 */
public interface DeviceExtWeatherService extends IService<DeviceExtWeather> {

    boolean save(Device device);

    boolean updateWeather(String sno);

    /**
     * 更新所有设备的天气信息
     * @return
     */
    boolean updateWeather();

    DeviceWeatherVo detail(String sno);

    /**
     * 下发设备天气信息
     * @return 下发成功返回true
     */
    boolean sendDeviceWeather();
}
