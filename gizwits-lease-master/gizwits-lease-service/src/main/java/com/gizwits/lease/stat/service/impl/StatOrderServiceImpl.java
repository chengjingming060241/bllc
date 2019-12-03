package com.gizwits.lease.stat.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.exceptions.SystemException;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.DateKit;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.lease.constant.OrderStatus;
import com.gizwits.lease.device.dao.DeviceDao;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.DeviceLaunchArea;
import com.gizwits.lease.device.service.DeviceLaunchAreaService;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.manager.entity.Agent;
import com.gizwits.lease.manager.entity.Operator;
import com.gizwits.lease.manager.service.AgentService;
import com.gizwits.lease.order.dao.OrderBaseDao;
import com.gizwits.lease.stat.dao.StatOrderDao;
import com.gizwits.lease.stat.dto.StatOrderAnalysisDto;
import com.gizwits.lease.stat.entity.StatOrder;
import com.gizwits.lease.stat.service.StatOrderService;
import com.gizwits.lease.stat.vo.StatOrderAnalysisVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 订单分析统计表 服务实现类
 * </p>
 *
 * @author gagi
 * @since 2017-07-11
 */
@Service
public class StatOrderServiceImpl extends ServiceImpl<StatOrderDao, StatOrder> implements StatOrderService {

    @Autowired
    private OrderBaseDao orderBaseDao;
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private AgentService agentService;

    @Autowired
    private DeviceLaunchAreaService deviceLaunchAreaService;

    protected static final Logger logger = LoggerFactory.getLogger("ORDER_LOGGER");
    private static final List<Integer> statOrderStatusList = Arrays.asList(OrderStatus.USING.getCode(), OrderStatus.FINISH.getCode());

    @Override
    public void setDataForStatOrder() {
        // 获取当前日期
        Calendar today = Calendar.getInstance();
        beZero(today);
        // 一个月前
        Calendar date = Calendar.getInstance();
        date.setTime(today.getTime());
        date.add(Calendar.MONTH, -1);

        // 检查最近一个月的统计是否有遗留
        for (; date.before(today); date.add(Calendar.DATE, 1)) {
            setDataForStatOrder(date.getTime());
        }
    }

    @Override
    public void setDataForStatOrder(Date date) {
        logger.info("===============订单统计开始" + DateKit.getTimestampString(date));
        Date start = DateKit.getDayStartTime(date);
        Date end = DateKit.getDayEndTime(date);
        StatOrderAnalysisDto statOrderAnalysisDto = new StatOrderAnalysisDto();
        statOrderAnalysisDto.setDateFormat("%Y-%m-%d");
        statOrderAnalysisDto.setFromDate(date);
        statOrderAnalysisDto.setToDate(date);
        List<StatOrderAnalysisVo> statOrderList = baseMapper.getOrderAnalysisByIds(statOrderAnalysisDto, null);
        if (!statOrderList.isEmpty()) {
            logger.info("==================={}已统计过，不需要再次统计", DateKit.getTimestampString(date));
            return;
        }

        // 查找当天里，产生过订单的设备，及其当时的归属者。
        // 如果当天执行过重新分配，则可能存在一个设备对应多个归属者，这时候要分开统计。
        List<StatOrder> distinctList = baseMapper.getDistinctFromOrder(start,end, statOrderStatusList);
        for (StatOrder statOrder : distinctList) {
            setDataForStatByDevice(date, statOrder.getSno(), statOrder.getLaunchAreaId(), statOrder.getSysUserId());
        }
    }

    @Override
    public void setDataForStatByDevice(Date date, String sno, Integer launchAreaId, Integer sysUserId) {
        logger.info("===============订单统计，开始统计设备：{}，投放点：{}，归属者：{}", sno, launchAreaId, sysUserId);
        if (StringUtils.isBlank(sno) || launchAreaId == null || sysUserId == null) {
            logger.info("===============订单统计参数有误");
            return;
        }
        Device device = deviceDao.selectById(sno);
        if (device == null) {
            logger.error("设备{}已被物理删除", sno);
            return;
        }
        DeviceLaunchArea launchArea = deviceLaunchAreaService.selectById(launchAreaId);
        if (launchArea == null) {
            logger.error("投放点{}已被物理删除", launchAreaId);
            return;
        }
        StatOrder statOrder = new StatOrder();
        // 创建时间
        statOrder.setCtime(date);
        // 设备序列号
        statOrder.setSno(device.getSno());
        // 产品
        statOrder.setProductId(device.getProductId());
        // 投放点
        statOrder.setLaunchAreaId(launchArea.getId());
        // 归属人
        Map<String, Object> map = resolveOperatorAndAgent(sysUserId);
        if (!map.containsKey("owner")) return;
        SysUser owner = (SysUser) map.get("owner");
        statOrder.setSysUserId(owner.getId());
        if (map.containsKey("operator")) {
            statOrder.setOperatorId(((Operator) map.get("operator")).getId());
        }
        if (map.containsKey("agent")) {
            statOrder.setAgentId(((Agent) map.get("agent")).getId());
        }

        StatOrder orderAnalysis = baseMapper.calculateOrderAnalysis(date, sno, launchAreaId, sysUserId, statOrderStatusList);
        // 当日订单数量
        statOrder.setOrderCount(orderAnalysis.getOrderCount());
        // 当日订单金额
        statOrder.setOrderAmount(orderAnalysis.getOrderAmount() == null ? 0 : orderAnalysis.getOrderAmount());
        // fixme 以下无须统计
        // 当日退款数量
        statOrder.setRefundCount(0);
        // 当日退款金额
        statOrder.setRefundAmount(0.0);
        // 应收分润金额（已生成分润单的分润金额）
        statOrder.setGeneratedShareAmount(0.0);
        // 未生成分润单的订单金额
        statOrder.setUngenerateShareOrderAmount(0.0);

        insert(statOrder);
    }

    @Override
    public List<StatOrderAnalysisVo> getOrderAnalysis(StatOrderAnalysisDto statOrderAnalysisDto, SysUser currentUser, List<Integer> ids) {
        if (currentUser == null) {
            throw new SystemException(LeaseExceEnums.ENTITY_NOT_EXISTS.getCode(), "请以正确的参数请求");
        }

        // 初始化时间
        Calendar fromDate = Calendar.getInstance();
        Calendar toDate = Calendar.getInstance();
        if (statOrderAnalysisDto.getFromDate() != null) {
            fromDate.setTime(statOrderAnalysisDto.getFromDate());
        } else {
            Date earliestOrderTime = orderBaseDao.earliestOrderTime();
            fromDate.setTime(earliestOrderTime);
            statOrderAnalysisDto.setFromDate(fromDate.getTime());
        }
        if (statOrderAnalysisDto.getToDate() != null) {
            toDate.setTime(statOrderAnalysisDto.getToDate());
        } else {
            statOrderAnalysisDto.setToDate(toDate.getTime());
        }
        beZero(fromDate);
        beZero(toDate);

        SimpleDateFormat sdf = null;
        int calendarField = -1;
        // 超过30天按月显示
        if (toDate.getTime().getTime() - fromDate.getTime().getTime() > TimeUnit.DAYS.toMillis(30)) {
            statOrderAnalysisDto.setDateFormat("%Y-%m");
            sdf = new SimpleDateFormat("yyyy-MM");
            calendarField = Calendar.MONTH;
        } else {
            // 30天以内，按日显示
            statOrderAnalysisDto.setDateFormat("%Y-%m-%d");
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            calendarField = Calendar.DAY_OF_YEAR;
        }

        //默认情况下是通过sysUserId和productId来查询
        //如果dto里面的成员变量有值，则使用mybatis里面的语法来查询
        List<StatOrderAnalysisVo> voList = baseMapper.getOrderAnalysisByIds(statOrderAnalysisDto, ids);
        if (voList.size() == 0) {
            logger.warn("请查看stat_order表中是否含有当前用户：" + currentUser.getId()
                    + "时间为:" + DateKit.getTimestampString(fromDate.getTime())
                    + "~" + DateKit.getTimestampString(toDate.getTime()) + "的记录");
            // return voList;
        }

        // 逐一日期填充
        Map<String, StatOrderAnalysisVo> map = new LinkedHashMap<>();
        for (; fromDate.before(toDate); fromDate.add(calendarField, 1)) {
            String ctime = sdf.format(fromDate.getTime());
            map.put(ctime, new StatOrderAnalysisVo(ctime));
        }
        if (calendarField == Calendar.MONTH) {
            String ctime = sdf.format(toDate.getTime());
            map.put(ctime, new StatOrderAnalysisVo(ctime));
        }
        for (StatOrderAnalysisVo vo : voList) {
            map.put(vo.getCtime(), vo);
        }
        voList = new LinkedList<>(map.values());

        //计算增长率
        StatOrderAnalysisVo preVo = null;
        for (StatOrderAnalysisVo vo : voList) {
            if (preVo == null) {
                vo.setCountPercent(0.0);
                vo.setAmountPercent(0.0);
            } else {
                Double countPercent = getRate(preVo.getOrderCount(), vo.getOrderCount()) * 100.0;
                vo.setCountPercent(countPercent);
                Double amountPerecent = getRate(preVo.getOrderAmount(), vo.getOrderAmount()) * 100.0;
                vo.setAmountPercent(amountPerecent);
            }
            preVo = vo;
        }
        return voList;
    }

    private Double getRate(double numerator, double denominator) {
        if (ParamUtil.isNullOrZero(denominator)) {
            return 0.0;
        }
        return new BigDecimal((denominator - numerator) / denominator).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private Map<String, Object> resolveOperatorAndAgent(Integer ownerId) {
        Map<String, Object> result = new HashMap<>();

        SysUser owner = sysUserService.selectById(ownerId);
        if (owner == null) {
            logger.error("归属者{}已被删除", ownerId);
            return result;
        }
        result.put("owner", owner);
        String[] parentIds = (owner.getTreePath() + owner.getId()).split(",");
        // 根据treePath往上找运营商和代理商
        for (int i = parentIds.length - 1; i >= 0; i--) {
            if (result.containsKey("operator") && result.containsKey("agent")) {
                break;
            }

            String parentIdStr = parentIds[i];
            if (StringUtils.isBlank(parentIdStr)) continue;
            Integer parentId = Integer.valueOf(parentIdStr);

            // 代理商
            if (!result.containsKey("agent")) {
                EntityWrapper<Agent> agentWrapper = new EntityWrapper<>();
                agentWrapper.eq("sys_account_id", parentId);
                Agent agent = agentService.selectOne(agentWrapper);
                if (agent != null) {
                    result.put("agent", agent);
                    continue;
                }
            }
        }

        return result;
    }

    private void beZero(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
    }
}
