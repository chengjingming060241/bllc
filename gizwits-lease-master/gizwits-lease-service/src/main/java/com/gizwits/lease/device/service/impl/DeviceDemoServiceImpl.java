package com.gizwits.lease.device.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.lease.constant.DeviceOnlineStatus;
import com.gizwits.lease.constant.DeviceWorkStatus;
import com.gizwits.lease.constant.QrcodeType;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.DeviceLaunchArea;
import com.gizwits.lease.device.entity.UserBindDevice;
import com.gizwits.lease.device.service.DeviceDemoService;
import com.gizwits.lease.device.service.DeviceLaunchAreaService;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.device.service.UserBindDeviceService;
import com.gizwits.lease.enums.DeviceActiveStatusType;
import com.gizwits.lease.enums.DeviceOriginType;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.product.entity.Product;
import com.gizwits.lease.product.entity.ProductServiceMode;
import com.gizwits.lease.product.service.ProductService;
import com.gizwits.lease.product.service.ProductServiceModeService;
import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.user.service.UserService;
import com.gizwits.lease.util.DateUtil;
import com.gizwits.lease.util.RandomUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeviceDemoServiceImpl implements DeviceDemoService {

	private static Logger logger = LoggerFactory.getLogger(DeviceDemoServiceImpl.class);

	@Autowired
	private DeviceService deviceService;
	@Autowired
	private ProductService productService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private DeviceLaunchAreaService deviceLaunchAreaService;
	@Autowired
	private ProductServiceModeService productServiceModeService;
	@Autowired
	private UserBindDeviceService userBindDeviceService;
	@Autowired
	private UserService userService;

	public void makeData(Date date, int randomLowerLimit, int randomUpperLimit){
		logger.info("设备数据开始生成："+ DateUtil.getTimestampString(date));
		// 所有投放点用户
		EntityWrapper<DeviceLaunchArea> wrapperLaunch = new EntityWrapper<>();
		wrapperLaunch.eq("status",1).eq("is_deleted", 0);
		List<Integer> launchSysUserIds = deviceLaunchAreaService.selectList(wrapperLaunch).stream()
				.map(DeviceLaunchArea::getOwnerId).distinct().collect(Collectors.toList());
		// 所有收费模式用户
		EntityWrapper<ProductServiceMode> wrapperMode = new EntityWrapper<>();
		wrapperMode.eq("status",1).eq("is_deleted", 0);
		List<Integer> modeSysUserIds = productServiceModeService.selectList(wrapperMode).stream()
				.map(ProductServiceMode::getSysUserId).distinct().collect(Collectors.toList());

		List<Product> products =productService.selectList(new EntityWrapper<Product>()
				.eq("status", 1).eq("is_deleted", 0));
		Map<Product, List<Integer>> productSysUserIdMap = new HashMap<>();
		for (Product p : products) {
			List<Integer> sysUserIds = new ArrayList<>();
			SysUser manufacturer = sysUserService.selectById(p.getManufacturerId());
			sysUserIds.addAll(sysUserService.resolveSysUserAllSubIds(manufacturer, false, false));
			// 租赁产品只获取有投放点和收费模式的人
			if(p.getGizwitsProductKey().equals("65a3901602824991b395f45e49382e62")
					|| p.getGizwitsProductKey().equals("ed0a8fdcdb7f41558de5a79427864dca")){
				sysUserIds.retainAll(launchSysUserIds);
				sysUserIds.retainAll(modeSysUserIds);
			}
			if(!sysUserIds.isEmpty()) {
				productSysUserIdMap.put(p, sysUserIds);
			}
		}

		int deviceNumber = org.apache.commons.lang.math.RandomUtils.nextInt(randomUpperLimit-randomLowerLimit+1)+randomLowerLimit;
		for (int i = 0; i < deviceNumber; i++) {
			try {
				makeOneData(date, productSysUserIdMap);
			}catch (Exception e){
				logger.error("设备生成失败", e);
			}
		}
	}

	private void makeOneData(Date date, Map<Product, List<Integer>> productSysUserIdMap){
		// 随机产品
		Product product = null;
		int productIndex = org.apache.commons.lang.math.RandomUtils.nextInt(productSysUserIdMap.size());
		int i = 0;
		for (Product p:productSysUserIdMap.keySet()) {
			if(i == productIndex){product = p;break;}
			i++;
		}
		logger.info("当前产品："+product.getId());

		// 随机拥有者
		List<Integer> sysUserIds = productSysUserIdMap.get(product);
		Integer sysUserId = sysUserIds.get(org.apache.commons.lang.math.RandomUtils.nextInt(sysUserIds.size()));
		SysUser sysUser = sysUserService.selectById(sysUserId);
		logger.info("当前用户："+sysUserId);


		// 随机时间
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, -org.apache.commons.lang.math.RandomUtils.nextInt(60));
		c.add(Calendar.SECOND, -org.apache.commons.lang.math.RandomUtils.nextInt(60));
		Date randomDate = c.getTime();

		Device device = new Device();
		String sno = deviceService.getSno();
		device.setSno(sno);
		device.setUtime(randomDate);
		device.setCtime(randomDate);

		// 随机mac
		String mac = null;
		// 随机名称
		String name = null;
		// 随机投放点
		DeviceLaunchArea deviceLaunchArea = null;
		// 随机收费模式
		ProductServiceMode productServiceMode = null;
		switch (product.getGizwitsProductKey()){
			case "65a3901602824991b395f45e49382e62"://airlease租赁版
				mac = randomMacForLease();
				name = "机智云商用空气净化器" + mac.substring(mac.length() - 6);
				deviceLaunchArea = randomLaunch(sysUserId);
				productServiceMode = randomMode(sysUserId);
				//创建二维码
				product.setQrcodeType(QrcodeType.WEB.getCode());
				device = deviceService.createQrcodeAndAuthDevice(device, product, sysUser, null);
				break;
			case "ed0a8fdcdb7f41558de5a79427864dca"://waterlease租赁版
				mac = randomMacForLease();
				name = "机智云商用净水器" + mac.substring(mac.length() - 6);
				deviceLaunchArea = randomLaunch(sysUserId);
				productServiceMode = randomMode(sysUserId);
				//创建二维码
				product.setQrcodeType(QrcodeType.WEB.getCode());
				device = deviceService.createQrcodeAndAuthDevice(device, product, sysUser, null);
				break;
			case "5b75f7a8dddb4598bc994e8bfaff5ccf"://airfresh GDMS版
				mac = randomMacForGDMS();
				name = "机智云家用空气净化器" + mac.substring(mac.length() - 6);
				// 绑定用户
				device.setMac(mac);
				randomBindUser(product, device, date);
				break;
			case "c52e0396b2d548cbab824d6a73cebc14"://water GDMS版
				mac = randomMacForGDMS();
				name = "机智云家用净水机" + mac.substring(mac.length() - 6);
				// 绑定用户
				device.setMac(mac);
				randomBindUser(product, device, date);
				break;
			default:return;
		}
		logger.info("当前mac："+mac);

		device.setMac(mac);
		device.setName(name);
		device.setProductId(product.getId());
		device.setProductName(product.getName());
		if (deviceLaunchArea!=null) {
			device.setLaunchAreaId(deviceLaunchArea.getId());
			device.setLaunchAreaName(deviceLaunchArea.getName());
		}
		if (productServiceMode!=null) {
			device.setServiceId(productServiceMode.getId());
			device.setServiceName(productServiceMode.getName());
		}
		// 随机经纬度（100.220972,23.959276）到（116.557771,41.164964）
		double lg = org.apache.commons.lang.math.RandomUtils.nextDouble() * 16.336799 + 100.220972;
		device.setLongitude(new BigDecimal(lg));
		double lt = org.apache.commons.lang.math.RandomUtils.nextDouble() * 17.205688 + 23.959276;
		device.setLatitude(new BigDecimal(lt));

		//关键
		device.setOwnerId(sysUser.getId());

		// 已入库（这样才能在列表显示）
		device.setStatus(0);
		//随机状态
		randomStatus(device);
		device.setSysUserId(sysUser.getId());
		device.setOrigin(DeviceOriginType.RANDOM.getCode());
		device.setActivateStatus(DeviceActiveStatusType.ACTIVE.getCode());
		device.setActivatedTime(randomDate);
		deviceService.insert(device);

		//随机分配到一个分润规则详情
		// TODO
	}

	private String randomMacForGDMS(){
		String mac;
		for (int i = 0; i < 10; i++) {
			mac = "FF"+ RandomStringUtils.randomAlphanumeric(10).toUpperCase();
			if (!deviceService.judgeMacIsExsit(mac)) {
				return mac;
			}
		}
		logger.error("找不到不重复的mac");
		LeaseException.throwSystemException(LeaseExceEnums.DEVICE_EXISTS);
		return null;
	}

	private String randomMacForLease(){
		String mac;
		for (int i = 0; i < 10; i++) {
			mac = "86"+ RandomStringUtils.randomNumeric(13);
			if (!deviceService.judgeMacIsExsit(mac)) {
				return mac;
			}
		}
		logger.error("找不到不重复的mac");
		LeaseException.throwSystemException(LeaseExceEnums.DEVICE_EXISTS);
		return null;
	}

	private void randomBindUser(Product product, Device device, Date date) {
		// 随机用户
		Wrapper<User> wrapper = new EntityWrapper<User>().eq("sys_user_id", product.getManufacturerId())
				.eq("is_deleted", 0).eq("remark", "aep");
		User user = RandomUtils.randomFromDb(userService, wrapper);

		UserBindDevice userBindDevice = new UserBindDevice();
		userBindDevice.setCtime(date);
		userBindDevice.setUtime(date);

		userBindDevice.setUserId(user.getId());
		userBindDevice.setAccountNum(user.getMobile());
		userBindDevice.setMobile(user.getMobile());
		userBindDevice.setIsManage(1);
		userBindDevice.setSno(device.getSno());
		userBindDevice.setMac(device.getMac());
		userBindDevice.setIsDeleted(0);
		userBindDeviceService.insert(userBindDevice);
	}

	private void randomStatus(Device device) {
		switch (org.apache.commons.lang.math.RandomUtils.nextInt(10)) {
			case 0:
			case 1:
				device.setOnlineStatus(DeviceOnlineStatus.OFFLINE.getCode());
				device.setWorkStatus(DeviceWorkStatus.OFFLINE.getCode());
				break;
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
				device.setOnlineStatus(DeviceOnlineStatus.ONLINE.getCode());
				device.setWorkStatus(DeviceWorkStatus.FREE.getCode());
				break;
		}
		switch (org.apache.commons.lang.math.RandomUtils.nextInt(10)) {
			case 0:
			case 1:
			case 2:
				device.setFaultStatus(DeviceWorkStatus.FAULT.getCode());
				break;
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
				device.setFaultStatus(DeviceWorkStatus.NORMAL.getCode());
				break;
		}
	}

	private DeviceLaunchArea randomLaunch(Integer sysUserId){
		EntityWrapper<DeviceLaunchArea> wrapperLaunch = new EntityWrapper<>();
		wrapperLaunch.eq("status",1).eq("is_deleted", 0).eq("owner_id", sysUserId);
		return RandomUtils.randomFromDb(deviceLaunchAreaService, wrapperLaunch);
	}

	private ProductServiceMode randomMode(Integer sysUserId){
		EntityWrapper<ProductServiceMode> wrapperMode = new EntityWrapper<>();
		wrapperMode.eq("status",1).eq("is_deleted", 0).eq("sys_user_id", sysUserId);
		return RandomUtils.randomFromDb(productServiceModeService, wrapperMode);
	}
}
