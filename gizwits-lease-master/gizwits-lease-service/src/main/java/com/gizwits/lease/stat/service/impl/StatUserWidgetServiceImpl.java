package com.gizwits.lease.stat.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.enums.SysUserType;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.DateKit;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.lease.stat.dao.StatUserTrendDao;
import com.gizwits.lease.stat.dto.StatUserTrendDto;
import com.gizwits.lease.stat.entity.StatUserTrend;
import com.gizwits.lease.user.dao.UserDao;
import com.gizwits.lease.enums.PanelModuleItemType;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.panel.service.PersonalPanelService;
import com.gizwits.lease.stat.dao.StatUserWidgetDao;
import com.gizwits.lease.stat.entity.StatUserWidget;
import com.gizwits.lease.stat.service.StatUserWidgetService;
import com.gizwits.lease.stat.vo.StatUserWidgetVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
public class StatUserWidgetServiceImpl extends ServiceImpl<StatUserWidgetDao, StatUserWidget> implements StatUserWidgetService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private StatUserWidgetDao statUserWidgetDao;
    @Autowired
    private PersonalPanelService personalPanelService;

    protected static Logger logger = LoggerFactory.getLogger("USER_LOGGER");

    @Autowired
    private StatUserTrendDao statUserTrendDao;

    @Autowired
    private SysUserService sysUserService;

    // private static ExecutorService executorService = Executors.newFixedThreadPool(3);

    /**
     * 指标说明：
     * 累计用户总数：统计用户总数；（去重）
     * 新增用户人数：统计新增用户数量；（去重）
     * 新增租赁人数：昨日新增使用过租赁服务的用户数；（去重）
     * 累计租赁人数：统计所有使用过租赁服务的用户总数；（去重）
     */
    @Override
    public void setDataForWidget(Date today) {
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DAY_OF_YEAR, -1);
        Date yesterday = c.getTime();
        Date yesterStart = DateKit.getDayStartTime(yesterday);
        Date yesterEnd = DateKit.getDayEndTime(yesterday);
//        Date now = new Date();
        Date before7Days = DateKit.addDate(today, -7);
        EntityWrapper<SysUser> entityWrapper = new EntityWrapper<>();
        entityWrapper.setSqlSelect("id");
        entityWrapper.ne("is_admin", SysUserType.NORMAL.getCode()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        List<SysUser> sysUsers = sysUserService.selectList(entityWrapper);
        if (null == sysUsers || sysUsers.size() == 0){
            return;
        }
        List<Integer> idList = sysUsers.stream().map(SysUser::getId).collect(Collectors.toList());
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //用户看板整合
        for (int i = 0; i < idList.size(); ++i) {
            final int index = i;
            // executorService.execute(() -> {
                try {
                    StatUserWidget statUserWidget = new StatUserWidget();
                    Integer sysUserId = idList.get(index);

                    // 累计用户总数：统计用户总数；（去重）
                    Integer totalCount = userDao.getTotal(sysUserId);
                    // // 活跃用户：7天内至少授权微信公众号过1次 or 7天内至少下单过1次的用户
                    Integer activeCount = userDao.getActive(sysUserId, before7Days, today);
                    // 新增用户人数：统计新增用户数量；（去重）
                    Integer newCount = userDao.getNewByDate(sysUserId, yesterStart,yesterEnd);
                    // 新增租赁人数：昨日新增使用过租赁服务的用户数；（去重）
                    Integer newOrderedCount = userDao.getNewOrderedCount(sysUserId, yesterStart,yesterEnd);
                    // 累计租赁人数：统计所有使用过租赁服务的用户总数；（去重）
                    Integer orderedCount = userDao.getOrderedCount(sysUserId);
                    statUserWidget.setTotalCount(totalCount == null ? 0 : totalCount);
                    statUserWidget.setActiveCount(activeCount == null ? 0 : activeCount);
                    statUserWidget.setNewCount(newCount == null ? 0 : newCount);
                    statUserWidget.setOrderedCount(orderedCount == null ? 0 : orderedCount);
                    statUserWidget.setNewOrderedCount(newOrderedCount == null ? 0 : newOrderedCount);
                    statUserWidget.setUtime(today);
                    statUserWidget.setSysUserId(sysUserId);
                    updatePersonalPanelRealTime(statUserWidget);
                    //如果修改不成功，则需要添加
                    if (statUserWidgetDao.updateByUtimeAndSysUserId(statUserWidget) == 0) {
                        statUserWidget.setCtime(today);
                        statUserWidgetDao.insert(statUserWidget);
//                        updatePersonalPanelNotRealTime(statUserWidget);
                    }
                } catch (Exception e) {
                    logger.warn("===================用户：" + idList.get(index) + "导入到stat_user_widget表失败,原因如下：" + e);
                }
            // });
        }
    }

    private void updatePersonalPanelNotRealTime(StatUserWidget widget) {
        personalPanelService.updateDataItemValue(widget.getSysUserId(), PanelModuleItemType.YESTERDAY_USER_INCREMENT_RATE.getCode(), String.valueOf(widget.getNewPercent()), null);
        personalPanelService.updateDataItemValue(widget.getSysUserId(), PanelModuleItemType.YESTERDAY_ACTIVE_USER_RATE.getCode(), String.valueOf(widget.getActivePercent()), null);
        personalPanelService.updateDataItemValue(widget.getSysUserId(), PanelModuleItemType.YESTERDAY_USER_INCREMENT_NUMBER.getCode(), String.valueOf(widget.getNewCount()), null);
    }

    private void updatePersonalPanelRealTime(StatUserWidget widget) {
        personalPanelService.updateDataItemValue(widget.getSysUserId(), PanelModuleItemType.CURRENT_USER_TOTAL_NUMBER.getCode(), String.valueOf(widget.getTotalCount()), null);
        personalPanelService.updateDataItemValue(widget.getSysUserId(), PanelModuleItemType.YESTERDAY_ACTIVE_USER_TOTAL_NUMBER
		        .getCode(), String.valueOf(widget.getActiveCount()), null);
    }

    @Override
    public StatUserWidgetVo widget(SysUser currentUser, List<Integer> ids) {
        if (!Objects.nonNull(currentUser)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        Date now = new Date();
        Date yesterday = DateKit.addDate(now, -1);
        StatUserWidget yesterdayW = statUserWidgetDao.widgetByIds(ids, now);
        StatUserWidgetVo statUserWidgetVo;
        if (!Objects.isNull(yesterdayW)) {
            statUserWidgetVo = new StatUserWidgetVo(yesterdayW.getTotalCount(), yesterdayW.getNewCount(), yesterdayW.getOrderedCount(), yesterdayW.getNewOrderedCount());
            double yesterdayActivePercent = 0;
            StatUserTrendDto statUserTrendDto = new StatUserTrendDto();
            statUserTrendDto.setFromDate(yesterday);
            statUserTrendDto.setToDate(yesterday);
            StatUserTrend statUserTrend = statUserTrendDao.getWidgetActiveAndTotal(ids, statUserTrendDto);
            if (!ParamUtil.isNullOrEmptyOrZero(statUserTrend)) {
                statUserWidgetVo.setActiveCount(statUserTrend.getActiveCount());
                statUserWidgetVo.setNewCount(statUserTrend.getNewCount());

                yesterdayActivePercent =
                        ParamUtil.isNullOrZero(statUserTrend.getTotalCount()) ? 0 : (double) statUserTrend.getActiveCount() / statUserTrend.getTotalCount();
                yesterdayActivePercent =
                        BigDecimal.valueOf(yesterdayActivePercent).setScale(4, BigDecimal.ROUND_HALF_UP)
                                .doubleValue();
                statUserWidgetVo.setActivePercent(yesterdayActivePercent);
                statUserWidgetVo.setTotalCount(statUserTrend.getTotalCount());
            } else {
//                int beforeYesterdayTotalCount = yesterdayW.getTotalCount() - yesterdayW.getNewCount();
//                yesterdayNewPercent =
//                        ParamUtil.isNullOrZero(beforeYesterdayTotalCount) ? 0 : (double) yesterdayW.getNewCount() / beforeYesterdayTotalCount;
//                yesterdayNewPercent =
//                        BigDecimal.valueOf(yesterdayNewPercent).setScale(4, BigDecimal.ROUND_HALF_UP)
//                                .doubleValue();
                yesterdayActivePercent =
                        ParamUtil.isNullOrZero(yesterdayW.getTotalCount()) ? 0 : (double) yesterdayW.getActiveCount() / yesterdayW.getTotalCount();
                yesterdayActivePercent =
                        BigDecimal.valueOf(yesterdayActivePercent).setScale(4, BigDecimal.ROUND_HALF_UP)
                                .doubleValue();
                statUserWidgetVo.setActiveCount(yesterdayW.getActiveCount());
                statUserWidgetVo.setActivePercent(yesterdayActivePercent);
                statUserWidgetVo.setNewCount(yesterdayW.getNewCount());
            }
//             double yesterdayNewPercent = 0;


        } else {
            logger.warn("请查看stat_user_widget中，当前用户：" + currentUser.getId() + "日期为：" + DateKit.getTimestampString(now) + "是否含有数据");
            statUserWidgetVo = new StatUserWidgetVo();
        }
        return statUserWidgetVo;
    }
}
