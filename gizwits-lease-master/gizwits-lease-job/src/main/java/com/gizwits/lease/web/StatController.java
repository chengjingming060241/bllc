package com.gizwits.lease.web;

import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.lease.order.dao.OrderBaseDao;
import com.gizwits.lease.stat.service.StatOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.Calendar;

@EnableSwagger2
@Api("统计")
@RestController
@RequestMapping("/stat")
public class StatController extends BaseController{

	@Autowired
	private StatOrderService statOrderService;
	@Autowired
	private OrderBaseDao orderBaseDao;

	@ApiOperation(value = "重新统计订单", consumes = "application/json")
	@PostMapping("/order/restat")
	public ResponseObject fire(@RequestBody @Valid RequestObject requestObject) {
		statOrderService.delete(null);

		// 获取当前日期
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		// 最早的订单日期
		Calendar date = Calendar.getInstance();
		date.setTime(orderBaseDao.earliestOrderTime());
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);

		// 逐天生成
		for(;date.before(today);date.add(Calendar.DATE, 1)){
			statOrderService.setDataForStatOrder(date.getTime());
		}

		return success();
	}

}
