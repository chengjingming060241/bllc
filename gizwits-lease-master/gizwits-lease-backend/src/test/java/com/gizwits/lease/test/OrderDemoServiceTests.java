package com.gizwits.lease.test;

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
import java.util.Random;

@SpringBootTest
@RunWith(SpringRunner.class)
//@Transactional
public class OrderDemoServiceTests {

    private static final Logger logger = LoggerFactory.getLogger(OrderDemoServiceTests.class);

    @Autowired
    private OrderDemoService orderDemoService;

    // 删除订单
    // delete o from order_base o left join device d on o.sno = d.sno where d.origin = 3
    // delete osf from order_status_flow osf left join order_base o on o.order_no = osf.order_no where o.order_no is null and osf.ctime between '2018-01-31 00:00:00' and '2018-02-28 00:00:00';

    @Test
    public void testAvailableDeviceForOrder() {
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
            int deviceCount = orderDemoService.countAvailableDeviceForOrder(time);
            logger.info("available for order device count {} at {}", deviceCount, time);
            time = DateUtils.addDays(time, 1);
        }
    }

    @Test
    public void testGenerateOrders() {
        Date time = new Date();
        orderDemoService.demoGenerateOrders(time);
    }

    @Test
    public void testEndOrders() {
        Date time = new Date();
        orderDemoService.finishOrders(time);
    }

    @Test
//    @Commit
    @Transactional
    public void testBatchGenerateOrders() {
        Date now = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.MONTH, Calendar.FEBRUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startTime = calendar.getTime();

//        calendar.set(Calendar.MONTH, Calendar.MARCH);
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        Date endTime = calendar.getTime();

        int total = 0;
        Date time = startTime;
        while (time.before(now)) {
            int count = orderDemoService.demoGenerateOrders(time);
            total += count;
            time = DateUtils.addHours(time, 1);
        }
        long minutes = (new Date().getTime() - now.getTime()) / 1000 / 60;
        logger.info("generated order total {} use time {} mins", total, minutes);
    }
}
