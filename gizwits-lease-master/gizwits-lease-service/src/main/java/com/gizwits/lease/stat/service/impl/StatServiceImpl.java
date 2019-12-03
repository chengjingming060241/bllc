package com.gizwits.lease.stat.service.impl;

import com.gizwits.boot.utils.DateKit;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.stat.service.StatDeviceLocationService;
import com.gizwits.lease.stat.service.StatDeviceOrderWidgetService;
import com.gizwits.lease.stat.service.StatDeviceTrendService;
import com.gizwits.lease.stat.service.StatOrderService;
import com.gizwits.lease.stat.service.StatService;
import com.gizwits.lease.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by GaGi on 2017/8/26.
 */
@Service
public class StatServiceImpl implements StatService {
    @Autowired
    private StatOrderService statOrderService;
    @Autowired
    private StatDeviceOrderWidgetService statDeviceOrderWidgetService;
    @Autowired
    private StatDeviceTrendService statDeviceTrendService;
    @Autowired
    private StatDeviceLocationService statDeviceLocationService;
    @Override
    public void setDataForDeviceAssign(Device device, Integer ownerId) {
        /**
         * 现在的逻辑，订单记录了历史投放点和设备的历史运营者，
         * 所以这里不需要在重新分配时马上就统计订单，还是凌晨跑job就可以了。
         * 至于其他统计，也是凌晨跑就可以了，没必要在分配时跑。
         */

        // Date now = new Date();
        // Date yesterday = DateKit.addDate(now,-1);
        // Date beforeYesterday = DateKit.addDate(now,-2);
        // /** 需求是统计所有订单
        // List<Integer> status = new ArrayList<>();
        // status.add(2);
        //  */
        // Calendar c = Calendar.getInstance();
        // c.set(Calendar.DATE, 1);
        // Date firstDay = c.getTime();
        // Date today = DateUtil.getDayStartTime(now);
        // Date tomorrow = DateUtil.addDay(today, 1);
        // Map<Integer, Integer> map = statDeviceTrendService.getActiveCountFromGizwits();
        // statOrderService.setDataForStatByDevice(device,yesterday,beforeYesterday, null);
        // statDeviceLocationService.setDataForLocationByOwnerId(yesterday,ownerId);
        // statDeviceOrderWidgetService.setDataForWidgetByOwnerId(firstDay,now,yesterday,beforeYesterday,today,tomorrow,ownerId);
        // statDeviceTrendService.setDataForDeviceTrendForOwnerId(now,yesterday,map,ownerId);
    }
}
