package com.gizwits.lease.stat.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.enums.SysUserType;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.DateKit;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.lease.benefit.dao.ShareBenefitSheetOrderDao;
import com.gizwits.lease.constant.OrderStatus;
import com.gizwits.lease.device.dao.DeviceAlarmDao;
import com.gizwits.lease.device.dao.DeviceDao;
import com.gizwits.lease.enums.PanelModuleItemType;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.order.dao.OrderBaseDao;
import com.gizwits.lease.panel.service.PersonalPanelService;
import com.gizwits.lease.stat.dao.StatDeviceOrderWidgetDao;
import com.gizwits.lease.stat.entity.StatDeviceOrderWidget;
import com.gizwits.lease.stat.service.StatDeviceOrderWidgetService;
import com.gizwits.lease.stat.vo.StatAlarmWidgetVo;
import com.gizwits.lease.stat.vo.StatDeviceWidgetVo;
import com.gizwits.lease.stat.vo.StatOrderWidgetVo;
import com.gizwits.lease.util.DateUtil;
import com.gizwits.lease.utils.CalendarUtils;
import com.gizwits.lease.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 设备订单看板数据统计表 服务实现类
 * </p>
 *
 * @author gagi
 * @since 2017-07-18
 */
@Service
public class StatDeviceOrderWidgetServiceImpl extends ServiceImpl<StatDeviceOrderWidgetDao, StatDeviceOrderWidget> implements StatDeviceOrderWidgetService {
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private OrderBaseDao orderBaseDao;
    @Autowired
    private StatDeviceOrderWidgetDao statDeviceOrderWidgetDao;
    @Autowired
    private DeviceAlarmDao deviceAlarmDao;
    @Autowired
    private PersonalPanelService personalPanelService;
    @Autowired
    private SysUserService sysUserService;

    protected Logger loggerOrder = LoggerFactory.getLogger("ORDER_LOGGER");
    protected Logger loggerDevice = LoggerFactory.getLogger("DEVICE_LOGGER");

    // private static ExecutorService executorService = Executors.newFixedThreadPool(3);
    private static final List<Integer> statOrderStatusList = Arrays.asList(OrderStatus.PAYED.getCode(),
            OrderStatus.USING.getCode(), OrderStatus.FINISH.getCode(), OrderStatus.REFUNDED.getCode(), OrderStatus.REFUND_FAIL.getCode(), OrderStatus.REFUNDING.getCode());

    /**
     * 产品v1.2
     * <p>
     * 设备分析昨日指标说明：
     * 累积设备总数：当前系统的设备总数；
     * 新增设备数量：统计新增加的设备台数；
     * 租赁设备数量：统计处于租赁中的设备台数；
     * 故障设备台数：统计设备故障台数；
     * <p>
     * 订单分析指标说明：
     * 新增订单数量：指的是昨日产生的订单数量；
     * 本月订单数：指的是本月1号到昨天的所有订单数；
     * 订单完成数：指的是所有已完成的订单数量；
     * 累计订单总数：指的所有订单数量
     */
    @Override
    public void setDataForWidget() {
        //设置日期
        Date firstDay = CalendarUtils.getFirstDayOfThisMonth().getTime();
        Date now = new Date();
        Date yesterday = CalendarUtils.getZeroHourOfYesterday().getTime();
        Date beforeYesterday = CalendarUtils.getZeroHourBeforeYesterday().getTime();
        Date today = DateUtil.getDayStartTime(now);
        Date tomorrow = DateUtil.addDay(today, 1);
        EntityWrapper<SysUser> entityWrapper = new EntityWrapper<>();
        entityWrapper.setSqlSelect("id");
        entityWrapper.ne("is_admin", SysUserType.NORMAL.getCode()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        List<SysUser> sysUsers = sysUserService.selectList(entityWrapper);
        if (null == sysUsers || sysUsers.size() == 0) {
            return;
        }
        List<Integer> idList = sysUsers.stream().map(SysUser::getId).collect(Collectors.toList());

        for (Integer ownerId : idList) {
            try {
                setDataForWidgetByOwnerId(firstDay, now, yesterday, beforeYesterday, today, tomorrow, ownerId);
            } catch (Exception e) {
                loggerOrder.error("==============用户" + ownerId + "导入到stat_device_order_widget中不成功，原因：" + e);
                loggerDevice.error("==============用户" + ownerId + "导入到stat_device_order_widget中不成功，原因：" + e);
                e.printStackTrace();
//            }
            }


            // });
        }
    }

    @Override
    public void setDataForWidgetByOwnerId(Date firstDay, Date now, Date yesterday, Date beforeYesterday, Date today, Date tomorrow, Integer ownerId) {
        Date yesterdayStart = DateKit.getDayStartTime(yesterday);
        Date yesterDayEnd = DateKit.getDayEndTime(yesterday);

        List<Integer> productIdList = deviceDao.getProductId(ownerId);

        for (int j = 0; j < productIdList.size(); ++j) {
            StatDeviceOrderWidget statDeviceOrderWidget = new StatDeviceOrderWidget();
            Integer productId = productIdList.get(j);

            // 设备分析昨日指标说明：
            // 累积设备总数：当前系统的设备总数；
            Integer totalCount = deviceDao.getTotalBySysUserIdAndIsNotDeleted(ownerId, productId, today);
            // 新增设备数量：统计新增加的设备台数；
            Integer newCount = deviceDao.getNewCount(ownerId, productId, yesterdayStart, yesterDayEnd);
            // 租赁设备数量：统计处于租赁中的设备台数；
            Integer orderedCount = deviceDao.getOrderedCount(ownerId, productId, yesterdayStart, yesterDayEnd);
            // 故障设备台数：统计设备故障台数；
            Integer alarmCount = deviceDao.selectFaultCount(ownerId, productId, today);//故障设备数
            Integer warnCount = deviceDao.selectAlertCount(ownerId, productId, today);//报警设备数
            Integer warnRecord = deviceAlarmDao.getRecord(ownerId, productId, yesterdayStart, yesterDayEnd);//报警+故障记录数（非设备数）

            // 订单分析指标说明：
            // 新增订单数量：指的是昨日产生的订单数量；
            Integer orderCountYesterday = orderBaseDao.getOrderCount(ownerId, productId, yesterdayStart, yesterDayEnd, null);
            // 本月订单数：指的是本月1号到昨天的所有订单数；
            Integer orderCountMonth = orderBaseDao.getOrderCount(ownerId, productId, firstDay, yesterday, null);
            // 订单完成数：指的是所有已完成的订单数量；
            Integer orderFinishCount = orderBaseDao.getOrderCount(ownerId, productId, null, yesterday, Arrays.asList(OrderStatus.FINISH.getCode()));
            // 累计订单总数：指的所有订单数量
            Integer orderTotalCount = orderBaseDao.getOrderCount(ownerId, productId, null, yesterday, null);

            // 百分比在统计时计算没有用，因为上级查看分析时，需要累加下级的数量再进行百分比计算，这是一个动态的数据。
            // Double orderedPercent = (ParamUtil.isNullOrZero(totalCount) ? 0.0 : (double) orderedCount / totalCount);
            // Double alarmPercent = (ParamUtil.isNullOrZero(totalCount) ? 0.0 : (double) alarmCount / totalCount);
            //统计分润单 今日订单数量
//            Integer shareOrderCount = shareBenefitSheetOrderDao.getOrderCount(ownerId, productId, yesterday);

            Integer onlineDeviceCount = deviceDao.selectOnlineCount(ownerId, productId);
            Integer activatedDeviceCount = deviceDao.selectActivatedCount(ownerId, productId, null, today);
            Integer activatedDeviceCountToday = deviceDao.selectActivatedCount(ownerId, productId, today, tomorrow);

            statDeviceOrderWidget.setUtime(now);
            statDeviceOrderWidget.setTotalCount(totalCount == null ? 0 : totalCount);
            statDeviceOrderWidget.setNewCount(newCount == null ? 0 : newCount);
            statDeviceOrderWidget.setOrderedCount(orderedCount == null ? 0 : orderedCount);
            statDeviceOrderWidget.setAlarmCount(alarmCount == null ? 0 : alarmCount);
            statDeviceOrderWidget.setOrderCountYesterday(orderCountYesterday == null ? 0 : orderCountYesterday);
            statDeviceOrderWidget.setOrderCountMonth(orderCountMonth == null ? 0 : orderCountMonth);
            statDeviceOrderWidget.setOrderTotalCount(orderTotalCount == null ? 0 : orderTotalCount);
            statDeviceOrderWidget.setOrderFinishCount(orderFinishCount == null ? 0 : orderFinishCount);
            statDeviceOrderWidget.setWarnCount(warnCount == null ? 0 : warnCount);
            statDeviceOrderWidget.setWarnRecord(warnRecord == null ? 0 : warnRecord);
            statDeviceOrderWidget.setSysUserId(ownerId);
            statDeviceOrderWidget.setProductId(productId);
            statDeviceOrderWidget.setShareOrderCount(0);

            statDeviceOrderWidget.setOnlineDeviceCount(ObjectUtils.ifNull(onlineDeviceCount, 0));
            statDeviceOrderWidget.setActivatedDeviceCount(ObjectUtils.ifNull(activatedDeviceCount, 0));
            statDeviceOrderWidget.setActivatedDeviceCountToday(ObjectUtils.ifNull(activatedDeviceCountToday, 0));

//            updateForPersonalPanelRealTime(statDeviceOrderWidget);

            //如果update实体statDeviceOrderWidget==0则insert
            // 先根据条件查询是否存在记录，根据id去更新避免死锁
            Integer id = statDeviceOrderWidgetDao.selectByUtimeAndSysUserIdAndProductId(now, ownerId, productId, statDeviceOrderWidget);
            if (!ParamUtil.isNullOrEmptyOrZero(id)) {
                statDeviceOrderWidget.setId(id);
                updateById(statDeviceOrderWidget);
            } else {
                //将非实时的给添加到数据库
//                Integer orderCountYesterday = orderBaseDao.getOrderCount(ownerId, productId, yesterday, yesterday, null);
//                Integer orderCountBeforeYesterday = orderBaseDao.getOrderCount(ownerId, productId, beforeYesterday, beforeYesterday, null);
//                statDeviceOrderWidget.setOrderCountYesterday(orderCountYesterday == null ? 0 : orderCountYesterday);
//                statDeviceOrderWidget.setOrderCountBeforeYesterday(orderCountBeforeYesterday == null ? 0 : orderCountBeforeYesterday);
                // Double orderNewPercent = StatisticsUtil.calcIncreasePercent(orderCountBeforeYesterday, orderCountYesterday);
                // statDeviceOrderWidget.setOrderNewPercentYesterday(orderNewPercent);
                statDeviceOrderWidget.setCtime(now);
                statDeviceOrderWidgetDao.insert(statDeviceOrderWidget);
//                updateForPersonalPanelNotRealTime(statDeviceOrderWidget);
            }

        }
    }

    private void updateForPersonalPanelNotRealTime(StatDeviceOrderWidget widget) {
        personalPanelService.updateDataItemValue(widget.getSysUserId(), PanelModuleItemType.YESTERDAY_ORDER_TOTAL_NUMBER.getCode(), String.valueOf(widget.getOrderCountYesterday()), null);
        personalPanelService.updateDataItemValue(widget.getSysUserId(), PanelModuleItemType.YESTERDAY_ORDER_INCREMENT_RATE.getCode(), String.valueOf(widget.getOrderNewPercentYesterday()), null);
        personalPanelService.updateDataItemValue(widget.getSysUserId(), PanelModuleItemType.CURRENT_MONTH_ORDER_TOTAL_NUMBER.getCode(), String.valueOf(widget.getOrderCountMonth()), null);
    }

    private void updateForPersonalPanelRealTime(StatDeviceOrderWidget widget) {
        personalPanelService.updateDataItemValue(widget.getSysUserId(), PanelModuleItemType.CURRENT_DEVICE_TOTAL_NUMBER.getCode(), String.valueOf(widget.getTotalCount()), null);
        personalPanelService.updateDataItemValue(widget.getSysUserId(), PanelModuleItemType.YESTERDAY_DEVICE_NEW_ADD_NUMBER.getCode(), String.valueOf(widget.getNewCount()), null);
        personalPanelService.updateDataItemValue(widget.getSysUserId(), PanelModuleItemType.YESTERDAY_DEVICE_ORDER_RATE.getCode(), String.valueOf(widget.getOrderedPercent()), null);

        personalPanelService.updateDataItemValue(widget.getSysUserId(), PanelModuleItemType.CURRENT_DEVICE_FAULT_NUMBER.getCode(), String.valueOf(widget.getAlarmCount()), null);
        personalPanelService.updateDataItemValue(widget.getSysUserId(), PanelModuleItemType.CURRENT_FAULT_DEVICE_RATE.getCode(), String.valueOf(getRate(widget.getAlarmCount(), widget.getTotalCount())), null);
        personalPanelService.updateDataItemValue(widget.getSysUserId(), PanelModuleItemType.CURRENT_ALERT_DEVICE_NUMBER.getCode(), String.valueOf(widget.getWarnCount()), null);
        personalPanelService.updateDataItemValue(widget.getSysUserId(), PanelModuleItemType.CURRENT_ALERT_DEVICE_RATE.getCode(), String.valueOf(getRate(widget.getWarnCount(), widget.getTotalCount())), null);

        personalPanelService.updateDataItemValue(widget.getSysUserId(), PanelModuleItemType.TODAY_ORDER_TOTAL_NUMBER.getCode(), String.valueOf(widget.getOrderCountToday()), null);

        personalPanelService.updateDataItemValue(widget.getSysUserId(), PanelModuleItemType.DEVICE_ONLINE_RATE.getCode(), String.valueOf(getRate(widget.getOnlineDeviceCount(), widget.getTotalCount())), null);
        personalPanelService.updateDataItemValue(widget.getSysUserId(), PanelModuleItemType.DEVICE_ACTIVATED_RATE.getCode(), String.valueOf(getRate(widget.getActivatedDeviceCount(), widget.getTotalCount())), null);
        personalPanelService.updateDataItemValue(widget.getSysUserId(), PanelModuleItemType.TODAY_DEVICE_ACTIVATED_NUMBER.getCode(), String.valueOf(widget.getActivatedDeviceCountToday()), null);
    }

    @Override
    public StatOrderWidgetVo orderWidget(Integer productId, SysUser currentUser, List<Integer> ids) {
        if (!Objects.nonNull(currentUser)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        // Date now = new Date();
        // 拿出最近一天统计的数据
        Date recentlyDate = statDeviceOrderWidgetDao.getRecentlyDate();
        StatDeviceOrderWidget widget = null;
        if (recentlyDate != null) {
            widget = statDeviceOrderWidgetDao.orderWidgetByIds(ids, productId, recentlyDate);
        }
        Integer yesterday = 0, month = 0, finish = 0, total = 0;
        // Double percent = 0.0;
        // StatWidgetDataVo yesterday = new StatWidgetDataVo();
        //如果productId为空时则将所有的项统计下来
        if (!Objects.isNull(widget)) {
            yesterday = widget.getOrderCountYesterday();
            month = widget.getOrderCountMonth();
            finish = widget.getOrderFinishCount() == null ? 0 : widget.getOrderFinishCount();
            total = widget.getOrderTotalCount() == null ? 0 : widget.getOrderTotalCount();
            // yesterday.setData(widget.getOrderCountYesterday());
            // yesterday.setOdd(widget.getOrderCountYesterday() - widget.getOrderCountBeforeYesterday());
        } else {
            loggerOrder.warn("请查看statDeviceOrderWidget中是否有最近一天:" + DateKit.getTimestampString(recentlyDate) + "，产品id:" + productId + "和当前用户:" + currentUser.getId() + "的订单数据");
            widget = new StatDeviceOrderWidget();
            widget.setShareOrderCount(0);
            widget.setShareOrderMoney(new BigDecimal(0));
            // yesterday.setData(0);
            // yesterday.setOdd(0);
        }
        // 类似这种百分比的东西，要获取的时候再计算，不能直接用数据库内的总和求平均值
        // percent = ParamUtil.isNullOrZero(widget.getOrderCountBeforeYesterday()) ? 0 :
        //         yesterday.getOdd().doubleValue() / widget.getOrderCountBeforeYesterday();
        // percent = BigDecimal.valueOf(percent).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        StatOrderWidgetVo statOrderWidgetVo = new StatOrderWidgetVo(yesterday, month, finish, total);
        statOrderWidgetVo.setShareOrderCount(widget.getShareOrderCount());
        statOrderWidgetVo.setShareOrderMoney(widget.getShareOrderMoney());
        return statOrderWidgetVo;
    }

    @Override
    public StatDeviceWidgetVo deviceWidget(Integer productId, SysUser currentUser, List<Integer> ids) {
        if (!Objects.nonNull(currentUser)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        // Date now = new Date();
        // 拿出最近一天统计的数据
        Date recentlyDate = statDeviceOrderWidgetDao.getRecentlyDate();
        StatDeviceOrderWidget widget = null;
        if (recentlyDate != null) {
            widget = statDeviceOrderWidgetDao.deviceWidgetByIds(ids, productId, recentlyDate);
        }
        Integer totalCount = 0, newCount = 0, alarmCount = 0, orderedCount = 0, todayActivatedCount = 0;
        Double onlineRate = 0.0, activatedRate = 0.0;
        Integer faultCount = 0, alertCount = 0;
        Double faultRate = 0.0, alertRate = 0.0;
        // Double orderedPercent = 0.0;
        if (!Objects.isNull(widget)) {
            totalCount = widget.getTotalCount();
            newCount = widget.getNewCount();
            alarmCount = ObjectUtils.ifNull(widget.getAlarmCount(), 0) + ObjectUtils.ifNull(widget.getWarnCount(), 0);
            orderedCount = (widget.getOrderedCount() == null ? 0 : widget.getOrderedCount());
            // Double percent = (ParamUtil.isNullOrZero(total) ? 0.0 : (double) orderedCount / total);
            // orderedPercent = new BigDecimal(percent).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();

            onlineRate = getRate(widget.getOnlineDeviceCount(), widget.getTotalCount());
            activatedRate = getRate(widget.getActivatedDeviceCount(), widget.getTotalCount());
            // todayActivatedCount = ObjectUtils.ifNull(widget.getActivatedDeviceCountToday(), 0);

            faultCount = ObjectUtils.ifNull(widget.getAlarmCount(), 0);
            faultRate = getRate(faultCount, totalCount);
            alertCount = ObjectUtils.ifNull(widget.getWarnCount(), 0);
            alertRate = getRate(alertCount, totalCount);
        } else {
            loggerOrder.warn("请查看statDeviceOrderWidget中是否有最近一天:" + DateKit.getTimestampString(recentlyDate) + "，产品id:" + productId + "和当前用户:" + currentUser.getId() + "的设备数据");
        }
        StatDeviceWidgetVo statDeviceWidgetVo = new StatDeviceWidgetVo(totalCount, newCount, orderedCount, alarmCount,
                onlineRate, activatedRate,
                faultCount, faultRate, alertCount, alertRate);
        return statDeviceWidgetVo;
    }

    @Override
    public StatAlarmWidgetVo alarmWidget(Integer productId, SysUser currentUser, List<Integer> ids) {
        if (!Objects.nonNull(currentUser)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        // Date now = new Date();
        // 拿出最近一天统计的数据
        Date recentlyDate = statDeviceOrderWidgetDao.getRecentlyDate();
        StatDeviceOrderWidget widget = null;
        if (recentlyDate != null) {
            widget = statDeviceOrderWidgetDao.alarmWidgetByIds(ids, productId, recentlyDate);
        }
        Integer alarmDevice = 0, yesterdayAlarmRecord = 0;
        Double percent = 0.0;
        if (!Objects.isNull(widget)) {
            Integer total = widget.getTotalCount() == null ? 0 : widget.getTotalCount();
            Integer alarmCount = widget.getAlarmCount() == null ? 0 : widget.getAlarmCount();
            Integer warnCount = widget.getWarnCount() == null ? 0 : widget.getWarnCount();
            alarmDevice = alarmCount + warnCount;
            yesterdayAlarmRecord = widget.getWarnRecord();
            Double alarmPercent = (ParamUtil.isNullOrZero(total) ? 0.0 : (double) alarmDevice / total);
            percent = new BigDecimal(alarmPercent).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        } else {
            loggerOrder.warn("请查看statDeviceOrderWidget中是否有符合当天，产品id和当前用户的告警设备数据");
        }
        StatAlarmWidgetVo statAlarmWidgetVo = new StatAlarmWidgetVo(alarmDevice, yesterdayAlarmRecord, percent);
        return statAlarmWidgetVo;
    }

    private Double getRate(Integer numerator, Integer denominator) {
        if (ParamUtil.isNullOrZero(numerator) || ParamUtil.isNullOrZero(denominator)) {
            return 0.0;
        }
        return new BigDecimal((double) numerator / denominator).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
