package com.gizwits.lease.device.dao;

import com.gizwits.lease.device.entity.DeviceExtWeather;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gizwits.lease.device.entity.dto.DeviceWeatherDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 设备-天气拓展表 Mapper 接口
 * </p>
 *
 * @author zxhuang
 * @since 2018-02-02
 */
public interface DeviceExtWeatherDao extends BaseMapper<DeviceExtWeather> {

    List<String> selectAllCityIds(@Param(value="source") Integer source);

    void updateWeatherByCityId(DeviceWeatherDto weatherDto);
}