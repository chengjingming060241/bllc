package com.gizwits.lease.china.service.impl;

import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.lease.china.entity.ChinaArea;
import com.gizwits.lease.china.dao.ChinaAreaDao;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gizwits.lease.china.entity.china.dto.AreaDto;
import com.gizwits.lease.china.entity.china.dto.UnifiedAddressDto;
import com.gizwits.lease.china.service.ChinaAreaService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * <p>
 * 全国省市行政编码表 服务实现类
 * </p>
 *
 * @author yinhui
 * @since 2017-07-15
 */
@Service
public class ChinaAreaServiceImpl extends ServiceImpl<ChinaAreaDao, ChinaArea> implements ChinaAreaService {
	
    @Override
    public List<AreaDto> getAreas(Integer code) {
        EntityWrapper<ChinaArea> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("parent_code",code);
        List<AreaDto> areaDtos = new ArrayList<>();
        List<ChinaArea> chinaAreaList =  selectList(entityWrapper);
        for(ChinaArea area:chinaAreaList){
            AreaDto areaDto = new AreaDto();
            String name = area.getName();
            areaDto.setName(name);
            areaDto.setCode(area.getCode());
            areaDtos.add(areaDto);
        }
        return areaDtos;
    }

    public Integer getCodeByCode(Integer code){
        EntityWrapper<ChinaArea> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("parent_code",code);
        return selectOne(entityWrapper).getCode();
    }

    public ChinaArea getAreaByName(String name, String parentName){
        EntityWrapper<ChinaArea> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("name",name).eq("parent_name",parentName);
        return selectOne(entityWrapper);
    }

    /**
     * code = 0表示获取省份和直辖市
     * 随机生成省份，城市
     *
     * @param code
     * @return
     */
    @Override
    public AreaDto getArea(Integer code) {
        Random random = new Random();
        List<AreaDto> list = getAreas(code);
        AreaDto areaDto = null;
        if (!ParamUtil.isNullOrEmptyOrZero(list)) {
            areaDto = list.get(random.nextInt(list.size()));
        }
        return areaDto;
    }

    @Override
    public UnifiedAddressDto unified(UnifiedAddressDto unifiedAddressDto) {
        String province = unifiedAddressDto.getProvince();
        if(StringUtils.isBlank(province)
                || province.length() < 2){
            return unifiedAddressDto
                    .setCity(null)
                    .setProvince(null);
        }

        ChinaArea provinceArea = selectOne(new EntityWrapper<ChinaArea>()
                .eq("parent_name", "中国")
                .like("name", province.substring(0, 2)));
        if(Objects.isNull(provinceArea)){
            return unifiedAddressDto
                    .setCity(null)
                    .setProvince(null);
        }
        province = provinceArea.getName();
        unifiedAddressDto.setProvince(province);
        if(province.length() == 2){
            //省级市，特殊处理
            unifiedAddressDto.setProvince(province + "市");
            province += "城区";
        }

        String city = unifiedAddressDto.getCity();
        if(StringUtils.isBlank(city)
                || city.length() < 2){
            return unifiedAddressDto
                    .setCity(null);
        }

        ChinaArea cityArea = selectOne(new EntityWrapper<ChinaArea>()
                .eq("parent_name", province)
                .like("name", city.substring(0, 2)));
        if(Objects.isNull(cityArea)){
            return unifiedAddressDto
                    .setCity(null);
        }
        unifiedAddressDto.setCity(cityArea.getName());

        return unifiedAddressDto;
    }


}
