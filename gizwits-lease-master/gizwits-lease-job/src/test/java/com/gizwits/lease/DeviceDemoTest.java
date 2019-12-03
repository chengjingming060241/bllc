package com.gizwits.lease;

import com.gizwits.boot.utils.DateKit;
import com.gizwits.lease.device.dao.DeviceDao;
import com.gizwits.lease.device.service.DeviceDemoService;
import com.gizwits.lease.stat.service.StatDeviceTrendService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
// @Transactional
public class DeviceDemoTest {
	private final Logger log = LoggerFactory.getLogger(DeviceDemoTest.class);

	@Autowired
	private StatDeviceTrendService statDeviceTrendService;
	@Autowired
	private DeviceDemoService deviceDemoService;
	@Autowired
	private DeviceDao deviceDao;

	@Test
	public void generateHistoryData(){
		Calendar c = getStartTime();
		Calendar now = Calendar.getInstance();

		for (; c.before(now); c.add(Calendar.HOUR, 1)) {
			log.info("当前时间："+DateKit.getTimestampString(c.getTime()));
			deviceDemoService.makeData(c.getTime(),1,2);
		}
	}

//	@Test
//	public void generateHistoryStat(){
//		Calendar c = getStartTime();
//		Calendar now = Calendar.getInstance();
//
//		for (; c.before(now); c.add(Calendar.DATE, 1)) {
//			log.info("当前时间："+DateKit.getTimestampString(c.getTime()));
//			if(c.get(Calendar.HOUR_OF_DAY) == 0){
//				Date today = c.getTime();
//				Date yesterday = DateKit.addDate(today, -1);
//				List<Integer> idList = deviceDao.getDiffOwnerId();
//				for (int i = 0; i < idList.size(); ++i) {
//					Integer ownerId = idList.get(i);
//					try {
//						log.info(ownerId + "的statDeviceTrend录入开始");
////						statDeviceTrendService.setDataForDeviceTrendForOwnerId(today, yesterday, null, ownerId);
//						log.info(ownerId + "的statDeviceTrend录入结束");
//					} catch (Exception e) {
//						log.error("入库失败的sysUserId:" + ownerId + "=========" + e);
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//	}

	private Calendar getStartTime() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c;
	}


}
