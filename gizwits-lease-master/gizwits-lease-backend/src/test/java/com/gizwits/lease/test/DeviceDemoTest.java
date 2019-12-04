package com.gizwits.lease.test;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.DateKit;
import com.gizwits.lease.constant.DeviceSweepCodeStatus;
import com.gizwits.lease.device.dao.DeviceDao;
import com.gizwits.lease.device.dao.DeviceLaunchAreaDao;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.DeviceLaunchArea;
import com.gizwits.lease.device.entity.dto.*;
import com.gizwits.lease.device.service.DeviceAssignService;
import com.gizwits.lease.device.service.DeviceDemoService;
import com.gizwits.lease.device.service.DeviceStockService;
import com.gizwits.lease.product.service.ProductCategoryService;
import com.gizwits.lease.product.service.ProductService;
import com.gizwits.lease.stat.service.StatDeviceTrendService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
// @Transactional
public class DeviceDemoTest {
	private final Logger log = LoggerFactory.getLogger(DeviceDemoTest.class);

	@Autowired
	private DeviceStockService deviceStockService;

	@Autowired
	private DeviceAssignService deviceAssignService;

	//添加
	@Test
	public void addDevice() {
		DeviceAddDto deviceAddDto = new DeviceAddDto();
		List<DeviceAddDetailsDto> detailsDto = new ArrayList<>();

		//有控制器状态
//		deviceAddDto.setControlType(true);
//		for (int i = 0; i <15 ; i++) {
//			DeviceAddDetailsDto d = new DeviceAddDetailsDto();
//			d.setMac("52284623154"+i);
//			d.setsN1("LJCKSD52262FDDSFS5665"+i);
//			detailsDto.add(d);
//		}

//		//无控制器
//		deviceAddDto.setControlType(false);
//		deviceAddDto.setRemarks("无控制器");
//		for (int i = 15; i <30 ; i++) {
//			DeviceAddDetailsDto d = new DeviceAddDetailsDto();
//			d.setMac("52284623154"+i);
//			detailsDto.add(d);
//		}

		/*****************操作员*********************/

//		//有按钮
//		for (int i = 0; i <15 ; i++) {
//			DeviceAddDetailsDto d = new DeviceAddDetailsDto();
//			d.setsN1("LJCKSD52262FDDSFS5665"+i);
//			d.setsN2("LJCKSD52262FDDSASDJKA"+i);
//			d.setiMEI("JFDJFA526562"+i);
//			detailsDto.add(d);
//		}
//		deviceAddDto.setIMEIType(true);
//		deviceAddDto.setDeviceAddDetailsDtos(detailsDto);

		//有按钮
//		for (int i = 0; i <15 ; i++) {
//			DeviceAddDetailsDto d = new DeviceAddDetailsDto();
//			d.setsN2("LJCKJHSYB2FDDSASDJKA"+i);
//			detailsDto.add(d);
//		}
//		deviceAddDto.setIMEIType(false);
//		deviceAddDto.setDeviceAddDetailsDtos(detailsDto);
		/*****************入库员*********************/
		for (int i = 10; i <15 ; i++) {
			DeviceAddDetailsDto d = new DeviceAddDetailsDto();
//			d.setsN2("LJCKJHSYB2FDDSASDJKA"+i);
			d.setsN2("LJCKSD52262FDDSASDJKA"+i);
			deviceAddDto.setBatch("SC-1262");
			detailsDto.add(d);
		}

		deviceAddDto.setRemarks("入库员入库测试");
		deviceAddDto.setDeviceLaunchAreaId(234);
		deviceAddDto.setDeivceLaunchAreaName("昆山仓库");
		deviceAddDto.setSupplierName("苏州供应商");
		deviceAddDto.setProductId(10);

		deviceAddDto.setDeviceAddDetailsDtos(detailsDto);

		deviceStockService.addDevice(deviceAddDto);

	}

	//查询进度
	@Test
	public void sweepProgress() {
		DeviceForSpeedDetailDto deviceForSpeedDetailDto = deviceStockService.sweepProgress("87605328609472679594");
		System.out.println(deviceForSpeedDetailDto.toString());
	}

	@Test
	public void stockDetails() {
		DeviceForStockDetailDto deviceForStockDetailDto = deviceStockService.stockDetails("24921666372248763734");
		System.out.println("库存详情："+deviceForStockDetailDto);
	}


	//库存更新
	@Test
	public void stockUpdate() {
		DeviceForUpdateDto deviceForUpdateDto = new DeviceForUpdateDto();
		deviceForUpdateDto.setSno("24921666372248763734");
		deviceForUpdateDto.setsN1("LJCKSD52262FDDSFS56656");
		deviceForUpdateDto.setiMEI("JFDJFA52656215");
		deviceForUpdateDto.setsN2("LJCKJHSYB2FDDSASDJKA2");
		deviceForUpdateDto.setMac("5228462315415");
		deviceForUpdateDto.setRemarks("基本信息更新");

		deviceStockService.stockUpdate(deviceForUpdateDto);

	}

	//库存设备列表
	@Test
	public void listPage() {
		Pageable<DeviceStockQueryDto> pageable = new Pageable<>();
		DeviceStockQueryDto deviceStockQueryDto = new DeviceStockQueryDto();
		deviceStockQueryDto.setSweepCodeStatus(DeviceSweepCodeStatus.WAIT_TO_ENTRY.getCode());
		pageable.setQuery(deviceStockQueryDto);

		//状态默认为“待扫码”
		if (Objects.isNull(pageable.getQuery().getSweepCodeStatus())){
			pageable.getQuery().setSweepCodeStatus(DeviceSweepCodeStatus.PENDING_CODE.getCode());
		}
		//防止前台查询已删除的数据
		pageable.getQuery().setIsDeleted(DeleteStatus.NOT_DELETED.getCode());
		Page<DeviceShowDto> deviceShowDtoPage = deviceStockService.listPage(pageable);

		for (DeviceShowDto deviceShowDto : deviceShowDtoPage.getRecords()) {
			System.out.println("库存设备列表"+deviceShowDto.toString());
		}


	}

	//库存列表
	@Test
	public void stockListPage() {
		Pageable<DeviceStockQueryDto> pageable = new Pageable<>();
		DeviceStockQueryDto deviceStockQueryDto = new DeviceStockQueryDto();
		deviceStockQueryDto.setSweepCodeStatus(DeviceSweepCodeStatus.To_Be_But_Bf_Stock.getCode());
		pageable.setQuery(deviceStockQueryDto);

		//防止前台查询已删除的数据
        pageable.getQuery().setIsDeleted(DeleteStatus.NOT_DELETED.getCode());
		Page<DeviceShowDto> deviceShowDtoPage = deviceStockService.StockListPage(pageable);

		for (DeviceShowDto deviceShowDto : deviceShowDtoPage.getRecords()) {
			System.out.println("库存列表"+deviceShowDto.toString());
		}
	}


	@Test
	public void outOfStock() {
		// //测试数据
		SysUser sysUser = new SysUser();

		sysUser.setId(611);
		sysUser.setJobNumber(1026);
		sysUser.setIsAdmin(5);
		sysUser.setRealName("橘子");

		DeviceForAssignDto deviceForAssignDto = new DeviceForAssignDto();
		List<DeviceAddDetailsDto> detailsDto = new ArrayList<>();
		for (int i = 0; i <15 ; i++) {
			DeviceAddDetailsDto detailsDto1 = new DeviceAddDetailsDto();
			detailsDto1.setsN2("LJCKSD52262FDDSASDJKA"+i);
			detailsDto.add(detailsDto1);
		}

		deviceForAssignDto.setDeviceAddDetailsDtos(detailsDto);
		deviceForAssignDto.setCurrentUser(sysUser);
		deviceForAssignDto.setOutBatch("SH-123");
		deviceForAssignDto.setAssignedId(66);


		deviceAssignService.outOfStock(deviceForAssignDto);
	}

	//入库记录
	@Test
	public void putListPage() {

		Pageable<DeviceStockQueryDto> pageable = new Pageable<>();

		DeviceStockQueryDto deviceStockQueryDto = new DeviceStockQueryDto();
		deviceStockQueryDto.setIsDeletedPut(DeleteStatus.NOT_DELETED.getCode());
		pageable.setQuery(deviceStockQueryDto);

		Page<DeviceShowDto> deviceShowDtoPage = deviceStockService.putListPage(pageable);

		for (DeviceShowDto deviceShowDto : deviceShowDtoPage.getRecords()) {
			System.out.println("入库记录"+deviceShowDto.toString());
		}
	}

	//入库详情列表
	@Test
	public void putDeviceDetails() {
		Pageable<DeviceStockQueryDto> pageable = new Pageable<>();
		DeviceStockQueryDto deviceStockQueryDto = new DeviceStockQueryDto();
		deviceStockQueryDto.setIsDeletedPut(DeleteStatus.NOT_DELETED.getCode());
		deviceStockQueryDto.setBatch("SC-1262");
		pageable.setQuery(deviceStockQueryDto);

		Page<DeviceForSpeedDetailDto> deviceForSpeedDetailDtoPage = deviceStockService.putDeviceDetails(pageable);
		for (DeviceForSpeedDetailDto deviceShowDto : deviceForSpeedDetailDtoPage.getRecords()) {
			System.out.println("入库详情列表"+deviceShowDto.toString());
		}
	}

	//出库记录
	@Test
	public void outListPage() {
		Pageable<DeviceStockQueryDto> pageable = new Pageable<>();

		DeviceStockQueryDto deviceStockQueryDto = new DeviceStockQueryDto();
		deviceStockQueryDto.setIsDeletedOut(DeleteStatus.NOT_DELETED.getCode());
		pageable.setQuery(deviceStockQueryDto);

		Page<DeviceShowDto> deviceShowDtoPage = deviceStockService.outListPage(pageable);

		for (DeviceShowDto deviceShowDto : deviceShowDtoPage.getRecords()) {
			System.out.println("出库记录"+deviceShowDto.toString());
		}
	}


	@Test
	public void outDeviceDetails() {
		Pageable<DeviceStockQueryDto> pageable = new Pageable<>();
		DeviceStockQueryDto deviceStockQueryDto = new DeviceStockQueryDto();
		deviceStockQueryDto.setIsDeletedOut(DeleteStatus.NOT_DELETED.getCode());
		deviceStockQueryDto.setOutBatch("SH-123");
		pageable.setQuery(deviceStockQueryDto);

		Page<DeviceForSpeedDetailDto> deviceForSpeedDetailDtoPage = deviceStockService.outDeviceDetails(pageable);
		for (DeviceForSpeedDetailDto deviceShowDto : deviceForSpeedDetailDtoPage.getRecords()) {
			System.out.println("入库详情列表"+deviceShowDto.toString());
		}
	}


	@Test
	public void stockDelete() {
	}

	@Test
	public void putDelete() {
	}

	@Test
	public void outDelete() {
	}


}
