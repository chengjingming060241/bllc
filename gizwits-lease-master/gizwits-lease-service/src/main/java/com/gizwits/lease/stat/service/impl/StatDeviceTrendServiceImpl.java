package com.gizwits.lease.stat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.enums.SysUserType;
import com.gizwits.boot.exceptions.SystemException;
import com.gizwits.boot.model.HttpRespObject;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.DateKit;
import com.gizwits.boot.utils.HttpUtil;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.lease.device.dao.DeviceDao;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.order.dao.OrderBaseDao;
import com.gizwits.lease.product.entity.Product;
import com.gizwits.lease.product.service.ProductService;
import com.gizwits.lease.redis.RedisService;
import com.gizwits.lease.stat.dao.StatDeviceTrendDao;
import com.gizwits.lease.stat.dto.StatDeviceTrendDto;
import com.gizwits.lease.stat.entity.StatDeviceTrend;
import com.gizwits.lease.stat.entity.StatUserTrend;
import com.gizwits.lease.stat.service.StatDeviceTrendService;
import com.gizwits.lease.stat.vo.StatDeviceStatisticsVo;
import com.gizwits.lease.stat.vo.StatTrendVo;
import com.gizwits.lease.util.DateUtil;
import com.gizwits.lease.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * <p>
 * 设备趋势统计表 服务实现类
 * </p>
 *
 * @author gagi
 * @since 2017-07-11
 */
@Service
public class StatDeviceTrendServiceImpl extends ServiceImpl<StatDeviceTrendDao, StatDeviceTrend> implements StatDeviceTrendService {

    //这两个日志不合理
    protected static Logger loggerStat = LoggerFactory.getLogger(StatUserTrend.class);

    protected static Logger loggerDevice = LoggerFactory.getLogger("DEVICE_LOGGER");

    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private StatDeviceTrendDao statDeviceTrendDao;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ProductService productService;

    // private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public void setDataForStatDeviceTrend() {
        loggerStat.info(DateKit.getTimestampString(new Date()) + "<=============开始录入信息到stat_device_trend===============>");
        Date today = new Date();
        Date yesterday = DateKit.addDate(today, -1);

        EntityWrapper<SysUser> entityWrapper = new EntityWrapper<>();
        entityWrapper.setSqlSelect("id");
        entityWrapper.ne("is_admin", SysUserType.NORMAL.getCode()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        List<SysUser> sysUsers = sysUserService.selectList(entityWrapper);
        if (null == sysUsers || sysUsers.size() == 0) {
            return;
        }
        List<Integer> idList = sysUsers.stream().map(SysUser::getId).collect(Collectors.toList());
        Iterator<Integer> iterator = idList.iterator();

        Integer ownerId = 0;
        while (iterator.hasNext()) {
            try {
                ownerId = iterator.next();
                loggerDevice.info(ownerId + "的statDeviceTrend录入开始");
                setDataForDeviceTrendForOwnerId(today, yesterday, ownerId);
                loggerDevice.info(ownerId + "的statDeviceTrend录入结束");
            } catch (Exception e) {
                loggerDevice.error("入库失败的sysUserId:" + ownerId + "=========" + e);
                e.printStackTrace();
            }
            // });
        }
    }

    @Override
    public void setDataForDeviceTrendForOwnerId(Date today, Date yesterday, Integer ownerId) {
        Date yesterdayStart = DateKit.getDayStartTime(yesterday);
        Date yesterDayEnd = DateKit.getDayEndTime(yesterday);

        Date todayStartTime = DateUtil.getDayStartTime(today);
        //查询sysUserId对应的所有productId
        List<Integer> productIdList = deviceDao.getProductId(ownerId);
        for (int j = 0; j < productIdList.size(); ++j) {
            Integer productId = productIdList.get(j);
            StatDeviceTrend statDeviceTrend = new StatDeviceTrend();
            // Integer newCountForToday = deviceDao.getNewCount(ownerId, productId, today);
            Integer newCount = deviceDao.getNewCount(ownerId, productId, yesterdayStart,yesterDayEnd);
            Integer orderedCount = deviceDao.getOrderedCount(ownerId, productId, yesterdayStart,yesterDayEnd);
            Integer activeCount = deviceDao.getActiveCount(ownerId, productId, yesterdayStart,yesterDayEnd);
            // Integer totalCount = deviceDao.getTotalBySysUserIdAndIsNotDeleted(ownerId, productId, todayStartTime);
            Integer previousDeviceTotal = deviceDao.getTotalBySysUserIdAndIsNotDeleted(ownerId, productId, todayStartTime);
            Integer newActivatedCount = deviceDao.selectActivatedCount(ownerId, productId, DateUtil.getDayStartTime(yesterday), todayStartTime);
            Integer faultCount = deviceDao.selectFaultCount(ownerId, productId, todayStartTime);
            Integer alertCount = deviceDao.selectAlertCount(ownerId, productId, todayStartTime);

            //建议改成big decimal 类型!!
//            BigDecimal orderedPercent = (previousDeviceTotal) <= 0 ? BigDecimal.ZERO : new BigDecimal(((double) orderedCount / (previousDeviceTotal)));
            //将设备趋势表的sysUserId设为ownerId
            statDeviceTrend.setSysUserId(ownerId);
            statDeviceTrend.setCtime(yesterday);
            statDeviceTrend.setNewCount(newCount);
            statDeviceTrend.setOrderedCount(orderedCount);
            statDeviceTrend.setOrderedPercent(BigDecimal.ZERO);
            statDeviceTrend.setProductId(productId);
            statDeviceTrend.setActiveCount(activeCount);
            statDeviceTrend.setPreviousDeviceTotal(previousDeviceTotal);
            statDeviceTrend.setNewActivatedCount(ObjectUtils.ifNull(newActivatedCount, 0));
            statDeviceTrend.setFaultCount(ObjectUtils.ifNull(faultCount, 0));
            statDeviceTrend.setAlertCount(ObjectUtils.ifNull(alertCount, 0));
            insertOrUpdate(statDeviceTrend);
        }
    }

    @Override
    public List<StatTrendVo> getNewTrend(StatDeviceTrendDto statDeviceTrendDto) {
        List<StatTrendVo> result=new ArrayList<>();
        List<StatDeviceTrend> list=statDeviceTrendDao.getNewTrend(statDeviceTrendDto);
        list.stream().forEach(item->{
            StatTrendVo vo=new StatTrendVo();
            vo.setCount(item.getNewCount()==null?0:item.getNewCount());
            vo.setTime(DateUtil.dateToString(item.getCtime(),"yyyy-MM-dd"));
            result.add(vo);
        });
        return result;
    }

    @Override
    public List<StatTrendVo> getActiveTrend(StatDeviceTrendDto statDeviceTrendDto) {
        List<StatTrendVo> result=new ArrayList<>();
        List<StatDeviceTrend> list=statDeviceTrendDao.getActiveTrend(statDeviceTrendDto);
        list.stream().forEach(item->{
            StatTrendVo vo=new StatTrendVo();
            vo.setCount(item.getActiveCount()==null?0:item.getActiveCount());
            vo.setTime(DateUtil.dateToString(item.getCtime(),"yyyy-MM-dd"));
            result.add(vo);
        });
        return result;
    }


    @Override
    public List<StatTrendVo> getUserPercentTrend(StatDeviceTrendDto statDeviceTrendDto, SysUser currentUser, List<Integer> ids) {
        if (currentUser == null) {
            throw new SystemException(LeaseExceEnums.ENTITY_NOT_EXISTS.getCode(), "请以正确的参数请求");
        }
        List<StatTrendVo> statTrendVoList = new ArrayList<>();
        List<StatDeviceTrend> list = statDeviceTrendDao.getUsePecentTrendByIds(ids, statDeviceTrendDto);
        if (list.size() == 0) {
            loggerDevice.warn("请查看stat_device_trend表中是否含有当前用户：" + currentUser.getId() + "日期在" + DateKit.getTimestampString(statDeviceTrendDto.getFromDate()) + "和" + DateKit.getTimestampString(statDeviceTrendDto.getToDate()) + "的订单率数量记录");
        }
        for (int i = 0; i < list.size(); ++i) {
            StatTrendVo statTrendVo = new StatTrendVo();
            StatDeviceTrend statDeviceTrend = list.get(i);
            statTrendVo.setTime(DateKit.getTimestampString(statDeviceTrend.getCtime()));
            Integer orderedCount = statDeviceTrend.getOrderedCount();
            Integer previousDeviceTotal = statDeviceTrend.getPreviousDeviceTotal();
            BigDecimal percent = getRate(orderedCount,previousDeviceTotal);
            statTrendVo.setCount(orderedCount);
            statTrendVo.setPrecent(percent);
            statTrendVoList.add(statTrendVo);
        }
        return statTrendVoList;
    }

    private BigDecimal getRate(double numerator, double denominator) {
        if (ParamUtil.isNullOrZero(denominator)) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal((denominator-numerator) / denominator).setScale(4, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public List<StatTrendVo> getNewActivatedTrend(StatDeviceTrendDto statDeviceTrendDto, SysUser currentUser, List<Integer> ids) {
        if (currentUser == null) {
            throw new SystemException(LeaseExceEnums.ENTITY_NOT_EXISTS.getCode(), "请以正确的参数请求");
        }
        List<StatTrendVo> statTrendVoList = new ArrayList<>();
        List<StatDeviceTrend> list = statDeviceTrendDao.getNewActivatedTrend(ids, statDeviceTrendDto);
        if (list.size() == 0) {
            loggerDevice.warn("请查看stat_device_trend表中是否含有当前用户：" + currentUser.getId() + "日期在" + DateKit.getTimestampString(statDeviceTrendDto.getFromDate()) + "和" + DateKit.getTimestampString(statDeviceTrendDto.getToDate()) + "的订单率数量记录");
        }
        for (int i = 0; i < list.size(); ++i) {
            StatTrendVo statTrendVo = new StatTrendVo();
            StatDeviceTrend statDeviceTrend = list.get(i);
            statTrendVo.setTime(DateKit.getTimestampString(statDeviceTrend.getCtime()));
            statTrendVo.setCount(statDeviceTrend.getNewActivatedCount());
            statTrendVoList.add(statTrendVo);
        }
        return statTrendVoList;
    }

    @Override
    public List<StatTrendVo> getFaultDeviceTrend(StatDeviceTrendDto statDeviceTrendDto) {
        List<StatTrendVo> result=new ArrayList<>();
        List<StatDeviceTrend> list=statDeviceTrendDao.getFaultDeviceTrend(statDeviceTrendDto);
        list.stream().forEach(item->{
            StatTrendVo vo=new StatTrendVo();
            vo.setCount(item.getFaultCount()==null?0:item.getFaultCount());
            vo.setTime(DateUtil.dateToString(item.getCtime(),"yyyy-MM-dd"));
            result.add(vo);
        });
        return result;
    }

    @Override
    public List<StatTrendVo> getAlertDeviceTrend(StatDeviceTrendDto statDeviceTrendDto) {
        List<StatTrendVo> result=new ArrayList<>();
        List<StatDeviceTrend> list=statDeviceTrendDao.getAlertDeviceTrend(statDeviceTrendDto);
        list.stream().forEach(item->{
            StatTrendVo vo=new StatTrendVo();
            vo.setCount(item.getAlertCount()==null?0:item.getAlertCount());
            vo.setTime(DateUtil.dateToString(item.getCtime(),"yyyy-MM-dd"));
            result.add(vo);
        });
        return result;
    }

    @Override
    public void statisticsDeviceTrend() {
        Date today=new Date();
        String strToday=DateUtil.dateToString(today,"yyyy-MM-dd");
        //获取所有产品
        List<Product> products=productService.getAllUseableProduct();
        if(ParamUtil.isNullOrEmptyOrZero(products)){
            return ;
        }
        List<Integer> productIds=products.stream().map(item->item.getId()).collect(Collectors.toList());
        //删除今日的旧数据
         delete(new EntityWrapper<StatDeviceTrend>().like("ctime",strToday, SqlLike.RIGHT).in("product_id",productIds));
        //根据产品区分统计
        List<StatDeviceTrend> vos=new ArrayList<>();
        for (Product p:products) {
            StatDeviceStatisticsVo vo=deviceDao.getDeviceStatictics(p.getId(),today);
             StatDeviceTrend deviceTrend=new StatDeviceTrend();
              deviceTrend.setCtime(new Date());
              deviceTrend.setProductId(p.getId());
              deviceTrend.setNewCount(vo.getAddCount()==null?0:vo.getAddCount());
              deviceTrend.setActiveCount(vo.getActiveCount()==null?0:vo.getActiveCount());
              deviceTrend.setFaultCount(vo.getFaultCount()==null?0:vo.getFaultCount());
              deviceTrend.setAlertCount(vo.getAlarmCount()==null?0:vo.getAlarmCount());
              deviceTrend.setPreviousDeviceTotal(vo.getTotal()==null?0:vo.getTotal());
              vos.add(deviceTrend);
        }
        //一键插入
        insertBatch(vos);

    }

    @Override
    public List<StatTrendVo> allTotalTrend(StatDeviceTrendDto statDeviceTrendDto) {
         List<StatTrendVo> result=new ArrayList<>();
         List<StatDeviceTrend> list=statDeviceTrendDao.getTotalTrend(statDeviceTrendDto);
          list.stream().forEach(item->{
              StatTrendVo vo=new StatTrendVo();
               vo.setCount(item.getPreviousDeviceTotal());
               vo.setTime(DateUtil.dateToString(item.getCtime(),"yyyy-MM-dd"));
               result.add(vo);
          });
        return result;
    }
}
