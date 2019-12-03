package com.gizwits.lease.test;

import com.gizwits.lease.demo.service.DeviceAlarmDemoService;
import com.gizwits.lease.demo.service.OrderDemoService;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
//@Transactional
public class DeviceAlarmDemoServiceTests {

    private static final Logger logger = LoggerFactory.getLogger(DeviceAlarmDemoServiceTests.class);

    @Autowired
    private DeviceAlarmDemoService deviceAlarmDemoService;

    @Test
    public void testAvailableDeviceForDemoAlarm() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.MONTH, Calendar.FEBRUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date time = calendar.getTime();
        while (time.before(now)) {
            int deviceCount = deviceAlarmDemoService.countDeviceForDemoAlarm(time);
            logger.info("available for demo alarm device count {}", deviceCount);
            time = DateUtils.addDays(time, 1);
        }
    }

    @Test
    public void testGenerateAlarm() {
        Date time = new Date();
        deviceAlarmDemoService.demoGenerateAlarms(time);
    }

    @Test
    @Commit
    public void testBatchGenerateAlarms() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.MONTH, Calendar.FEBRUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startTime = calendar.getTime();

        Date time = startTime;
        while (time.before(now)) {
            deviceAlarmDemoService.demoGenerateAlarms(time);
            time = DateUtils.addHours(time, 1);
        }
    }
}
