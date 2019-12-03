package com.gizwits.lease.device.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.DeviceExtWeather;
import com.gizwits.lease.device.dao.DeviceExtWeatherDao;
import com.gizwits.lease.device.entity.dto.DeviceWeatherDto;
import com.gizwits.lease.device.service.DeviceExtWeatherService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.device.vo.DeviceControlVO;
import com.gizwits.lease.device.vo.DeviceWeatherVo;
import com.gizwits.lease.enums.DeviceWeatherEnums;
import com.gizwits.lease.product.entity.Product;
import com.gizwits.lease.product.entity.ProductDataPointExt;
import com.gizwits.lease.product.service.ProductDataPointExtService;
import com.gizwits.lease.product.service.ProductService;
import com.gizwits.lease.util.DeviceControlAPI;
import com.gizwits.lease.utils.http.HttpClientUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 设备-天气拓展表 服务实现类
 * </p>
 *
 * @author zxhuang
 * @since 2018-02-02
 */
@Service
public class DeviceExtWeatherServiceImpl extends ServiceImpl<DeviceExtWeatherDao, DeviceExtWeather> implements DeviceExtWeatherService {

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceExtWeatherDao deviceExtWeatherDao;

    @Autowired
    private ProductDataPointExtService dataPointExtService;

    @Autowired
    private ProductService productService;

    private final String appid = "a2bf9a7b-ca03-4dd1-9460-fe4d17ebf645";


//    @PostConstruct
    public void initDevice(){
        System.out.println("开始同步设备到拓展表");
        List<Device> devices = deviceService.selectList(null);
        devices.forEach(device -> {
            if(device.getLongitude() != null){
                save(device);
            }
        });
    }


    @Async
    @Override
    public boolean save(Device device) {
        DeviceExtWeather weather = getDeviceExtWeather(device);
        if(weather == null)
            return false;

        insertOrUpdate(weather);
        return true;
    }

    private DeviceExtWeather getDeviceExtWeather(Device device) {
        if(device.getLongitude() != null){
            String cityUrl = "https://air.iotsdk.com/cityId";
            HashMap<String, String> cityBody = new HashMap<>();
            cityBody.put("appid", appid);
            cityBody.put("longitude",String.valueOf(device.getLongitude().doubleValue()));
            cityBody.put("latitude",String.valueOf(device.getLatitude().doubleValue()));
            String cityResult = HttpClientUtil.httpRequest(cityUrl, new HashMap<>(), cityBody, HttpClientUtil.MethodType.METHOD_GET);
            JSONObject response = JSON.parseObject(cityResult);

            if(response.getInteger("code") == 200){
                DeviceExtWeather weather = new DeviceExtWeather();
                weather.setSno(device.getSno());
                weather.setProductId(device.getProductId());
                weather.setLatitude(device.getLatitude());
                weather.setLongitude(device.getLongitude());
                String cityId = response.getString("data");
                weather.setCityId(cityId);
                weather.setSource(DeviceExtWeather.SOURCE_HEFENG);
                weather.setCtime(new Date());

                setNowWeather(weather);

                return weather;
            }
        }

        return null;
    }

    private JSONObject getNowWeather(String cityId){
        String weatherUrl = "https://air.iotsdk.com/now/id";
        HashMap<String, String> weatherBody = new HashMap<>();
        weatherBody.put("appid", appid);
        weatherBody.put("cityIds", cityId);

        String weatherResult = HttpClientUtil.httpRequest(weatherUrl, new HashMap<>(), weatherBody, HttpClientUtil.MethodType.METHOD_GET);
        JSONObject weatherResponse = JSON.parseObject(weatherResult);
        if(weatherResponse.getInteger("code") == 200){
            JSONArray weatherArray = weatherResponse.getJSONArray("data");
            JSONObject weatherJson = weatherArray.getJSONObject(0);
            return weatherJson;
        }

        return null;
    }

    private void setNowWeather(DeviceExtWeather weather){
        //获取天气,默认从和风获取 TODO 其它来源自行拓展
        JSONObject weatherJson = getNowWeather(weather.getCityId());
        if(weatherJson != null){
            DeviceWeatherDto weatherDto = weatherJson2WeatherDto(weather.getCityId(),weatherJson);
            BeanUtils.copyProperties(weatherDto, weather);
        }
    }

    private DeviceWeatherDto weatherJson2WeatherDto(String cityId,JSONObject weatherJson) {
        JSONObject now = weatherJson.getJSONObject("now");
        String hum = now==null? "" : now.getString("hum");
        String tmp = now==null? "" : now.getString("tmp");

        JSONObject aqi = weatherJson.getJSONObject("aqi").getJSONObject("city");
        String pm25 = aqi==null? "" : aqi.getString("pm25");
        String qlty = aqi==null? "" : aqi.getString("qlty");

        return new DeviceWeatherDto(cityId, tmp, hum, pm25, qlty);
    }

    @Override
    public boolean updateWeather(String sno) {
        DeviceExtWeather weather = selectById(sno);
        if(weather == null)
            return false;

        setNowWeather(weather);

        updateById(weather);
        return true;
    }


    @Override
    public boolean updateWeather() {
        List<String> cityIds = deviceExtWeatherDao.selectAllCityIds(DeviceExtWeather.SOURCE_HEFENG);
        cityIds.forEach(cityId -> {
            JSONObject weatherJson = getNowWeather(cityId);
            if(weatherJson != null){
                DeviceWeatherDto weatherDto = weatherJson2WeatherDto(cityId,weatherJson);
                deviceExtWeatherDao.updateWeatherByCityId(weatherDto);
            }
        });
        return true;
    }

    @Override
    public DeviceWeatherVo detail(String sno) {
        DeviceWeatherVo vo = new DeviceWeatherVo();
        DeviceExtWeather weather = selectById(sno);
        if(weather == null)
            return vo;

        BeanUtils.copyProperties(weather, vo);
        return vo;
    }

    /**
     * 下发设备天气
     * @return 下发成功返回true
     */
    @Override
    public boolean sendDeviceWeather() {

        List<ProductDataPointExt> productCommandConfigExtList = dataPointExtService.selectList(new EntityWrapper<ProductDataPointExt>().eq("show_enable", true));
        if (CollectionUtils.isEmpty(productCommandConfigExtList)) {
            return false;
        }
        List<Integer> productIdList = productCommandConfigExtList.stream().map(ProductDataPointExt::getProductId).collect(Collectors.toList());

        // 设备温度映射表
        List<DeviceExtWeather> deviceExtWeatherList = selectList(new EntityWrapper<DeviceExtWeather>().in("product_id", productIdList));
        if (CollectionUtils.isEmpty(deviceExtWeatherList)) {
            return false;
        }
        Map<Integer, List<DeviceExtWeather>> pidToDeviceExts = deviceExtWeatherList.stream().collect(Collectors.groupingBy(DeviceExtWeather::getProductId, Collectors.toList()));

        // 设备映射表
        List<String> deviceIds = deviceExtWeatherList.stream().map(DeviceExtWeather::getSno).collect(Collectors.toList());
        List<Device> deviceList = deviceService.selectBatchIds(deviceIds);
        if (CollectionUtils.isEmpty(deviceList)) {
            return false;
        }
        Map<String, Device> snoToDevice = deviceList.stream().collect(Collectors.toMap(Device::getSno, d -> d));

        // 获取产品映射表
        List<Product> productList = productService.selectBatchIds(productIdList);
        if (CollectionUtils.isEmpty(productList)) {
            return false;
        }
        Map<Integer, Product> pidToProduct = productList.stream().collect(Collectors.toMap(Product::getId, p -> p));

        // 数据点扩展映射表
        Map<Integer, List<ProductDataPointExt>> toMap = productCommandConfigExtList.stream().collect(Collectors.groupingBy(ProductDataPointExt::getProductId, Collectors.toList()));
        // 循环下发设备温度
        productIdList.forEach(pid -> doSend(prepControlInfo(toMap.get(pid), pidToDeviceExts.get(pid), snoToDevice, pidToProduct)));
        return true;
    }

    /* 准备设备下发信息 */
    private List<DeviceControlVO> prepControlInfo(List<ProductDataPointExt> extList, List<DeviceExtWeather> deviceExtWeathers
            , Map<String, Device> snoToDevice, Map<Integer, Product> pidToProduct) {
        if (CollectionUtils.isNotEmpty(deviceExtWeathers)) {
            List<DeviceControlVO> controlList = new ArrayList<>();
            deviceExtWeathers.forEach(item -> {
                JSONObject attrs = new JSONObject();
                prepAttrs(extList, item, attrs);
                controlList.add(new DeviceControlVO(pidToProduct.get(item.getProductId()).getGizwitsProductKey(), snoToDevice.get(item.getSno()).getGizDid(), attrs));
            });
            return controlList;
        }
        return null;
    }

    /* 准备设备数据点信息 */
    private void prepAttrs(List<ProductDataPointExt> extList, DeviceExtWeather item, JSONObject attrs) {
        if (CollectionUtils.isNotEmpty(extList) && Objects.nonNull(item)) {
            extList.forEach(ext -> {
                Integer param = ext.getParam();
                String name = ext.getIdentityName();
                if (DeviceWeatherEnums.TEMPERATURE.getParam() == param) {
                    attrs.put(name, item.getTmp());
                } else if (DeviceWeatherEnums.HUMIDITY.getParam() == param) {
                    attrs.put(name, item.getHum());
                } else if (DeviceWeatherEnums.PM2D5.getParam() == param) {
                    attrs.put(name, item.getPm25());
                } else if (DeviceWeatherEnums.AIR_QUALITY.getParam() == param) {
                    attrs.put(name, item.getQlty());
                }
            });
        }
    }

    @Async
    public void doSend(List<DeviceControlVO> controlList) {
        if (CollectionUtils.isEmpty(controlList)) {
            return;
        }
        controlList.forEach(v -> DeviceControlAPI.remoteControl(v.getProductKey(), v.getDid(), v.getAttrs()));
    }

}
