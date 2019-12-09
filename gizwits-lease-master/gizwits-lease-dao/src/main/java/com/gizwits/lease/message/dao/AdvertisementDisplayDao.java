package com.gizwits.lease.message.dao;

import com.gizwits.lease.message.entity.AdvertisementDisplay;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gizwits.lease.message.entity.dto.AdvertisementQueryDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
  * 广告展示表 Mapper 接口
 * </p>
 *
 * @author yinhui
 * @since 2018-01-19
 */
@Repository
public interface AdvertisementDisplayDao extends BaseMapper<AdvertisementDisplay> {

    List<AdvertisementDisplay> selectAdvertPage(AdvertisementQueryDto queryDto);

    Integer selectAdvertPageCount(AdvertisementQueryDto queryDto);
}