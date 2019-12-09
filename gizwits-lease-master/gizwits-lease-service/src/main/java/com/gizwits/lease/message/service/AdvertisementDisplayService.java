package com.gizwits.lease.message.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.lease.message.entity.AdvertisementDisplay;
import com.baomidou.mybatisplus.service.IService;
import com.gizwits.lease.message.entity.dto.AdvertisementQueryDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 广告展示表 服务类
 * </p>
 *
 * @author yinhui
 * @since 2018-01-19
 */
public interface AdvertisementDisplayService extends IService<AdvertisementDisplay> {

    boolean addOrUpdate (MultipartFile file, Integer showTime,String url,String name,Integer sort,Integer id,Integer type);

    AdvertisementDisplay detail(Integer id);

    List<AdvertisementDisplay> listForManager(String enterpriseId);

    boolean delete(List<Integer> id);

    AdvertisementDisplay getById(Integer id);

    Page<AdvertisementDisplay> list(Pageable<AdvertisementQueryDto> pageable);
}
