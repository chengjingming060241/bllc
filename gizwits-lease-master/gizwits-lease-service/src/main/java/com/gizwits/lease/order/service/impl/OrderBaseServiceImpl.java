package com.gizwits.lease.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.common.WebCommonConfig;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.enums.SysUserType;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.entity.SysUserExt;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.*;
import com.gizwits.lease.app.utils.LeaseUtil;
import com.gizwits.lease.benefit.entity.OrderShareProfit;

import com.gizwits.lease.benefit.entity.ShareProfitBatch;

import com.gizwits.lease.constant.*;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.DeviceLaunchArea;
import com.gizwits.lease.device.entity.UserDevice;
import com.gizwits.lease.device.service.DeviceLaunchAreaService;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.device.service.UserDeviceService;
import com.gizwits.lease.device.vo.DeviceUsingVo;
import com.gizwits.lease.enums.*;
import com.gizwits.lease.event.BindGizwitsDeviceEvent;
import com.gizwits.lease.event.WxPayCallbackEvent;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.manager.entity.Agent;
import com.gizwits.lease.manager.service.AgentService;

import com.gizwits.lease.message.entity.MessageTemplate;
import com.gizwits.lease.message.service.MessageTemplateService;
import com.gizwits.lease.order.dao.OrderBaseDao;
import com.gizwits.lease.order.dto.*;
import com.gizwits.lease.order.entity.OrderBase;
import com.gizwits.lease.order.entity.OrderExtByQuantity;
import com.gizwits.lease.order.entity.OrderExtByTime;
import com.gizwits.lease.order.entity.OrderStatusFlow;
import com.gizwits.lease.order.entity.dto.EarlyEndDto;
import com.gizwits.lease.order.service.*;
import com.gizwits.lease.order.vo.AppOrderDetailVo;
import com.gizwits.lease.order.vo.AppOrderVo;
import com.gizwits.lease.product.dto.ProductServiceModeDetailForAppDto;
import com.gizwits.lease.product.entity.Product;
import com.gizwits.lease.product.entity.ProductCommandConfig;
import com.gizwits.lease.product.entity.ProductServiceDetail;
import com.gizwits.lease.product.entity.ProductServiceMode;
import com.gizwits.lease.product.service.ProductCommandConfigService;
import com.gizwits.lease.product.service.ProductService;
import com.gizwits.lease.product.service.ProductServiceDetailService;
import com.gizwits.lease.product.service.ProductServiceModeService;
import com.gizwits.lease.product.vo.AppProductServiceDetailVo;
import com.gizwits.lease.product.vo.ProductServiceDetailVo;
import com.gizwits.lease.redis.RedisService;
import com.gizwits.lease.trade.service.TradeAlipayService;
import com.gizwits.lease.trade.service.TradeBaseService;
import com.gizwits.lease.trade.service.TradeWeixinService;
import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.user.entity.UserChargeCard;
import com.gizwits.lease.user.entity.UserWxExt;
import com.gizwits.lease.user.service.UserChargeCardService;
import com.gizwits.lease.user.service.UserService;
import com.gizwits.lease.user.service.UserWxExtService;
import com.gizwits.lease.util.CommonUtil;
import com.gizwits.lease.util.DeviceControlAPI;
import com.gizwits.lease.util.GizwitsUtil;
import com.gizwits.lease.util.OrderUtil;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author rongmc
 * @since 2017-06-30
 */
@Service
public class OrderBaseServiceImpl extends ServiceImpl<OrderBaseDao, OrderBase> implements OrderBaseService {

    private static Logger loggerOrder = LoggerFactory.getLogger("ORDER_LOGGER");

    @Autowired
    private OrderBaseDao orderBaseDao;

    @Autowired
    private TradeBaseService tradeBaseService;

    @Autowired
    TradeWeixinService tradeWeixinService;

    @Autowired
    private OrderStatusFlowService orderStatusFlowService;

    @Autowired
    private OrderExtByQuantityService orderExtByQuantityService;

    @Autowired
    private ProductServiceDetailService productServiceDetailService;

    @Autowired
    private ProductServiceModeService productServiceModeService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private OrderPayRecordService orderPayRecordService;

    @Autowired
    private OrderExtByTimeService orderExtByTimeService;

    @Autowired
    TradeAlipayService tradeAlipayService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserWxExtService userWxExtService;

    @Autowired
    private ProductCommandConfigService productCommandConfigService;
    @Autowired
    private DeviceLaunchAreaService deviceLaunchAreaService;

    @Autowired
    private ProductService productService;

    @Autowired
    private RedisService redisService;

    @Autowired

    private SysUserService sysUserService;


    @Autowired
    private MessageTemplateService messageTemplateService;

    @Autowired
    private UserChargeCardService userChargeCardService;


    @Autowired
    private AgentService agentService;


    @Autowired
    private UserDeviceService userDeviceService;

    public OrderBase getOrderBaseByTradeNo(String tradeNo) {
        if (ParamUtil.isNullOrEmpty(tradeNo)) {
            return null;
        }
        return orderBaseDao.findByTradeNo(tradeNo);
    }

    /**
     * 获取设备最近的一个使用中订单
     */
    @Override
    public OrderBase getDeviceLastUsingOrder(String deviceId) {
        if (StringUtils.isBlank(deviceId)) {
            return null;
        }
        return orderBaseDao.findByDeviceIdAndStatus(deviceId, OrderStatus.USING.getCode());
    }

    /**
     * 获取设备上指定状态的最新订单
     *
     * @param deviceId
     * @param status
     * @return
     */
    @Override
    public OrderBase getDeviceLastOrderByStatus(String deviceId, Integer status) {
        if (StringUtils.isBlank(deviceId)) {
            return null;
        }
        return orderBaseDao.findByDeviceIdAndStatus(deviceId, status);
    }

    @Override
    public OrderBase getDeviceLastOrderEndByJob(String sno) {
        if (StringUtils.isBlank(sno)) {
            return null;
        }
        EntityWrapper<OrderBase> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("sno", sno).eq("order_status", OrderStatus.FINISH.getCode()).eq("end_type", OrderEndType.END_BY_JOB.getCode());
        return selectOne(entityWrapper);
    }

    @Override
    public OrderBase getOrderBaseByOrderNo(String orderNo) {
        return selectById(orderNo);
    }


    @Override
    public OrderListPageDto getOrderListDtoPage(OrderQueryDto query) {
        Pageable<OrderQueryDto> pageable = new Pageable<>();
        int current = query.getCurrentPage();
        int pageSize = query.getPagesize();
        query.setBegin((current - 1) * pageSize);
        pageable.setCurrent(current);
        pageable.setSize(pageSize);
        pageable.setQuery(query);

        OrderListPageDto pageDto = new OrderListPageDto();
        Page<OrderListDto> page = getOrderListDtoPage(pageable);
        if (page.getTotal() != 0) {
            BigDecimal totalMoney = baseMapper.countTotalMoney(query);
            pageDto.setTotalMoney(totalMoney);
        } else {
            pageDto.setTotalMoney(BigDecimal.ZERO);
        }
        pageDto.setPage(page);
        return pageDto;
    }

    @Override
    public Page<OrderListDto> getOrderListDtoPage(Pageable<OrderQueryDto> pageable) {
        OrderQueryDto query = pageable.getQuery();
        if (pageable.getCurrent() < 1) {
            pageable.setCurrent(1);
        }
        int current = pageable.getCurrent();
        int pageSize = pageable.getSize();
        query.setBegin((current - 1) * pageSize);
        SysUser currentUser = sysUserService.getCurrentUserOwner();
        if (currentUser.getIsAdmin().equals(SysUserType.MANUFACTURER.getCode())) {
            query.setIsMana(1);
        } else {
            query.setIsMana(-1);
        }
        List<OrderBaseListDto> orderBaseListDtos = orderBaseDao.listPage(query);
        List<OrderListDto> orderListDtos = new ArrayList<>(orderBaseListDtos.size());
        for (OrderBaseListDto o : orderBaseListDtos) {
            OrderListDto orderListDto = new OrderListDto();
            orderListDto.setOrderBaseListDto(o);
            orderListDto.setPay(o.getPay());
            String status = OrderStatus.getMsg(o.getStatus());
            orderListDto.setOrderStatus(status);
            orderListDto.setPayTypeDesc(PayType.getName(Integer.parseInt(o.getPayType())));
            if (o.getAbnormalReason() != null) {
                orderListDto.setAbnormalReasonDesc(OrderAbnormalReason.get(o.getAbnormalReason()).getDescription());
            }

            orderListDtos.add(orderListDto);
        }
        Page<OrderListDto> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(current);
        page.setRecords(orderListDtos);
        page.setTotal(orderBaseDao.findTotalSize(query));

        return page;
    }

    @Override
    public PageOrderAppList<OrderListDto> getOrderAppListDtoPage(OrderQueryDto orderQueryDto) {
        Integer currentPage = orderQueryDto.getCurrentPage();
        Integer pageSize = orderQueryDto.getPagesize();
        Integer begin = (currentPage - 1) * pageSize;
        Integer end = currentPage * pageSize;
        orderQueryDto.setBegin(begin);


       /* SysUser sysUser = sysUserService.getCurrentUser();
        orderQueryDto.setOperatorAccountId(sysUser.getId());*/
        User user = userService.getCurrentUser();
        orderQueryDto.setUserId(user.getId());
        // Integer[] orderStatuss = {OrderStatus.USING.getCode(), OrderStatus.FINISH.getCode(), OrderStatus.REFUNDED.getCode()};

        List<OrderBaseListDto> orderBaseListDtos = orderBaseDao.appListPage(orderQueryDto);
        List<OrderListDto> orderListDtos = new ArrayList<>();

        for (OrderBaseListDto o : orderBaseListDtos) {
            OrderListDto orderListDto = new OrderListDto();
            orderListDto.setOrderBaseListDto(o);
            String status = OrderStatus.getOrderStatus(o.getStatus()).getMsg();
            orderListDto.setOrderStatus(status);
            String sno = o.getSno();
            if (!StringUtils.isEmpty(sno)) {
                Device device = deviceService.getDeviceInfoBySno(sno);
                if (!ParamUtil.isNullOrEmptyOrZero(device)) {
                    orderListDto.setDeviceName(device.getName());
                }
            }
            orderListDto.setPayTypeDesc(o.getPayType());
            //只需要列出下单的时候的收费情况
            OrderExtByQuantity orderExtByQuantity = orderExtByQuantityService.selectById(o.getOrderNo());
            if (orderExtByQuantity != null) {
                orderListDto.setDays(orderExtByQuantity.getQuantity() + "");
                orderListDto.setService_mode_unit(orderExtByQuantity.getUnit());
                orderListDto.setServiceModeType(orderExtByQuantity.getServiceType());
            }
            orderListDtos.add(orderListDto);
        }
        PageOrderAppList<OrderListDto> page = new PageOrderAppList<>();
        page.setSize(orderQueryDto.getPagesize());
        page.setCurrent(orderQueryDto.getCurrentPage());
        page.setRecords(orderListDtos);
        page.setTotal(orderBaseDao.findAppListTotalSize(orderQueryDto));

        Double totalPay = orderBaseDao.appListPaySum(orderQueryDto);
        page.setTotalPay(totalPay);
        return page;
    }

    @Override
    public OrderDetailDto orderDetail(OrderBase orderBase) {
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setSno(orderBase.getSno());
        orderDetailDto.setDeviceMac(orderBase.getMac());
        orderDetailDto.setOrderNo(orderBase.getOrderNo());
        orderDetailDto.setStatus(orderBase.getOrderStatus());
        String status = OrderStatus.getOrderStatus(orderBase.getOrderStatus()).getMsg();
        orderDetailDto.setOrderStatus(status);
        orderDetailDto.setOrderTime(orderBase.getCtime());
        orderDetailDto.setPay(orderBase.getAmount());

        orderDetailDto.setCardDiscount(orderBase.getCardDiscount());

        orderDetailDto.setPayTime(orderBase.getPayTime());
        Integer payTypeCode = orderBase.getPayType();
        orderDetailDto.setPayTypeCode(payTypeCode);
        String payType = PayType.getPayType(payTypeCode).getName();
        orderDetailDto.setPayType(payType);
        if (orderBase.getAbnormalReason() != null) {
            orderDetailDto.setAbnormalReasonDesc(OrderAbnormalReason.get(orderBase.getAbnormalReason()).getDescription());
        }
        orderDetailDto.setUserName(orderBase.getUserName());
        Device device = deviceService.selectById(orderBase.getSno());
        orderDetailDto.setDeviceLaunchArea(orderBase.getLaunchAreaName() == null ? device.getLaunchAreaName() : orderBase.getLaunchAreaName());
        //只需要列出下单的时候的收费情况
        OrderExtByQuantity orderExtByQuantity = orderExtByQuantityService.selectById(orderBase.getOrderNo());
        if (!ParamUtil.isNullOrEmptyOrZero(orderExtByQuantity)) {
            String serviceType = orderExtByQuantity.getPrice() + "元" + orderExtByQuantity.getQuantity() + orderExtByQuantity.getUnit();
            orderDetailDto.setServicetype(serviceType);
        }
        orderDetailDto.setServiceMode(orderBase.getServiceModeName());
        Date ctime = orderBase.getCtime();
        if (Objects.equals(orderBase.getOrderStatus(), OrderStatus.FINISH.getCode())) {

            Date utime = orderBase.getUtime();
            Long time = utime.getTime() - ctime.getTime();
            orderDetailDto.setDeviceUseTime(time);
        }

        if (Objects.equals(orderBase.getOrderStatus(), OrderStatus.USING.getCode())) {
            Date utime = new Date();
            Long time = utime.getTime() - ctime.getTime();
            orderDetailDto.setDeviceUseTime(time);
        }
        return orderDetailDto;
    }

    public OrderAppDetailDto orderAppDetail(OrderBase orderBase) {
        OrderAppDetailDto orderDetailDto = new OrderAppDetailDto();
        orderDetailDto.setDeviceMac(orderBase.getMac());
        orderDetailDto.setOrderNo(orderBase.getOrderNo());
        orderDetailDto.setStatus(orderBase.getOrderStatus());
        String status = OrderStatus.getOrderStatus(orderBase.getOrderStatus()).getMsg();
        orderDetailDto.setOrderStatus(status);
        orderDetailDto.setOrderTime(orderBase.getCtime());
        orderDetailDto.setPay(orderBase.getAmount());
        orderDetailDto.setPayTime(orderBase.getPayTime());
        Integer payTypeCode = orderBase.getPayType();
        orderDetailDto.setPayTypeCode(payTypeCode);
        String payType = PayType.getPayType(payTypeCode).getName();
        orderDetailDto.setPayType(payType);
        orderDetailDto.setUserName(orderBase.getUserName());
        Device device = deviceService.selectById(orderBase.getSno());
        orderDetailDto.setDeviceLaunchArea(orderBase.getLaunchAreaName() == null ? device.getLaunchAreaName() : orderBase.getLaunchAreaName());
        orderDetailDto.setDeviceName(device.getName());
        //只需要列出下单的时候的收费情况
        OrderExtByQuantity orderExtByQuantity = orderExtByQuantityService.selectById(orderBase.getOrderNo());
        if (!ParamUtil.isNullOrEmptyOrZero(orderExtByQuantity)) {
            String serviceType = orderExtByQuantity.getPrice() + "元" + orderExtByQuantity.getQuantity() + orderExtByQuantity.getUnit();
            orderDetailDto.setServicetype(serviceType);
        }
        orderDetailDto.setServiceMode(orderBase.getServiceModeName());
        return orderDetailDto;
    }

    /**
     * 查找待分润的的订单
     */
    public List<OrderBase> getReadyForShareBenefit(String sno, Date lastExecuteTime) {
        return orderBaseDao.listReadyForShareBenefitOrder(lastExecuteTime, sno);
    }

    private OrderBase createOrderForWeiXin(ProductServiceDetail productServiceDetail, ProductServiceMode serviceMode, User user, Device device, SysUserExt sysUserExt) {
        //查询该用户是否有未完成支付的订单
//        List<OrderBase> unfinishOrderList = orderBaseDao.findByUserIdAndStatus(user.getId(), OrderStatus.USING.getCode());
//        if (unfinishOrderList != null && unfinishOrderList.size() > 0) {
//            loggerOrder.warn("====>>>用户[" + user.getOpenid() + "]存在未完成订单,不能下单");
//            LeaseException.throwSystemException(LeaseExceEnums.HAS_UNFINISH_ORDER);
//        }

        //查询该设备的收费模式详情
      /*  List<ProductServiceDetail> serviceDetailList = productServiceDetailService.getProductServiceDetailByServiceModelId(serviceMode.getId());
        if (ParamUtil.isNullOrEmptyOrZero(serviceDetailList)) {
            LeaseException.throwSystemException(LeaseExceEnums.SERVICE_MODE_CONFIG_ERROR);
        }
        //判断收费模式详情的serviceModeId与设备的seviceId是否一致
        if (!productServiceDetail.getServiceModeId().equals(device.getServiceId())) {
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_SERVICE_MODEL_DETAIL_NOT_EXIST);
        }*/

        return createOrderBase(productServiceDetail, serviceMode, user, device, sysUserExt.getSysUserId());
    }

    /**
     * 根据收费模式，用户，设备，订单归属人创建订单
     *
     * @param productServiceDetail 收费模式
     * @param serviceMode
     * @param user                 用户
     * @param device               设备
     * @param sysUserId            订单归属人
     * @return
     */
    private OrderBase createOrderBase(ProductServiceDetail productServiceDetail, ProductServiceMode serviceMode, User user, Device device, Integer sysUserId) {
        OrderBase orderBase = new OrderBase();
        orderBase.setOrderNo(LeaseUtil.generateOrderNo(TradeOrderType.CONSUME.getCode()));
        orderBase.setCtime(new Date());
        orderBase.setSno(device.getSno());
        orderBase.setMac(device.getMac());
        //将收费模式详情的价格填上去
        BigDecimal price = new BigDecimal(productServiceDetail.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
        orderBase.setAmount(price.doubleValue());
        orderBase.setOriginalPrice(price);
        //将订单状态设置为创建状态
        orderBase.setOrderStatus(OrderStatus.INIT.getCode());
        orderBase.setServiceModeId(serviceMode.getId());
        orderBase.setServiceModeDetailId(productServiceDetail.getId());
        orderBase.setServiceModeName(serviceMode.getName());
        orderBase.setServiceModeDetailId(productServiceDetail.getId());
        orderBase.setLaunchAreaId(device.getLaunchAreaId());
        orderBase.setLaunchAreaName(device.getLaunchAreaName());
        orderBase.setUserId(user.getId());
        orderBase.setUserName(user.getNickname());

        if (device.getOwnerId() != null) {
            orderBase.setSysUserId(device.getOwnerId());
        } else {
            orderBase.setSysUserId(device.getSysUserId());
        }
        //记录用户的路径
        SysUser sysUser = sysUserService.selectById(orderBase.getSysUserId());
        orderBase.setTreePath(sysUser.getTreePath());

        orderBase.setCommand(productServiceDetail.getCommand());
        insert(orderBase);
        orderStatusFlowService.saveOne(orderBase, OrderStatus.INIT.getCode(), user.getId() + "");

        //TODO 需要根据serviceMode来判断是按量,按时......收费,这样直接写肯定是有问题的
        OrderExtByQuantity orderExtByQuantity = new OrderExtByQuantity();
        orderExtByQuantity.setOrderNo(orderBase.getOrderNo());
        orderExtByQuantity.setCtime(new Date());
        orderExtByQuantity.setQuantity(productServiceDetail.getNum());
        orderExtByQuantity.setPrice(orderBase.getAmount());
        orderExtByQuantity.setUnit(productServiceDetail.getUnit());
        orderExtByQuantity.setServiceType(productServiceDetail.getServiceType());
        orderExtByQuantityService.insert(orderExtByQuantity);
        //在创建订单的时候记录分润信息，状态为创建，在订单完成后修改状态为待分润
        addOrderShareProfitInfo(orderBase);
        return orderBase;
    }





    /**
     * 记录订单分润信息并执行分润
     *
     * @param serviceOrder
     */
    private List<OrderShareProfit> addOrderShareProfitInfo(OrderBase serviceOrder) {
        Device device = deviceService.getDeviceInfoBySno(serviceOrder.getSno());
        Integer ownerId = device.getOwnerId();
        if (device == null || ownerId == null) {
            loggerOrder.error("order {} device not exist or no owner", serviceOrder.getOrderNo());
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_NOT_IN_OPERATOR);
        }

        List<OrderShareProfit> shareProfitList = new ArrayList<>();
        SysUser sysUser = sysUserService.selectById(ownerId);
        String str = "";
        boolean foundManufacturer = false;
        while (sysUser != null) {
            Integer sysUserId = sysUser.getId();
           if (agentService.isAgent(sysUserId)) {
                Agent agent = agentService.getAgentBySysAccountId(sysUserId);
                OrderShareProfit orderShareProfit = createOrderShareProfit(serviceOrder, sysUserId);
                orderShareProfit.setPersonal(agent.getName());
                shareProfitList.add(orderShareProfit);

            } else if (sysUser.getIsAdmin() == 2) {
                SysUser sys = sysUserService.selectById(sysUserId);
                OrderShareProfit orderShareProfit = createOrderShareProfit(serviceOrder, sysUserId);
                orderShareProfit.setPersonal(sys.getNickName());
                shareProfitList.add(orderShareProfit);
                foundManufacturer = true;
                break;
            }
// else if (manufacturerService.isManufacturer(sysUserId)) {
//                Manufacturer manufacturer = manufacturerService.getBySysAccountId(sysUserId);
//                OrderShareProfit orderShareProfit = createOrderShareProfit(serviceOrder, sysUserId);
//                orderShareProfit.setPersonal(manufacturer.getName());
//                shareProfitList.add(orderShareProfit);
//                foundManufacturer = true;
//                break;
//            }

            if (sysUser.getParentAdminId() != null && !sysUser.getSysUserId().equals(sysUser.getId())) {
                sysUser = sysUserService.selectById(sysUser.getParentAdminId());
            } else {
                break;
            }
        }

        if (!foundManufacturer) {
            loggerOrder.error("order {} share profit not found manufacturer", serviceOrder.getOrderNo());
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_NOT_IN_OPERATOR);
        }

        shareProfitList = Lists.reverse(shareProfitList);
        int level = 1;



        return shareProfitList;
    }



    private OrderShareProfit createOrderShareProfit(OrderBase serviceOrder, Integer sysUserId) {
        OrderShareProfit orderShareProfit = new OrderShareProfit();
        orderShareProfit.setOrderNo(serviceOrder.getOrderNo());
        orderShareProfit.setOrderMoney(new BigDecimal(serviceOrder.getAmount()));
        orderShareProfit.setCtime(new Date());
        orderShareProfit.setShareProfitUser(sysUserId);
        orderShareProfit.setStatus(ShareBenefitSheetStatusType.CREATED.getCode());
//        int random  = (int) (Math.random()) + 100;
//        String tradeNo = IdGenerator.generateTradeNo(random + sysUserId + orderShareProfit.getOrderNo());
        String shareBenefitPayType = SysConfigUtils.get(WebCommonConfig.class).getShareBenefitPayType();
        if (ShareBenefitPayType.ALIPAY.getCode().equals(shareBenefitPayType)) {
            orderShareProfit.setPayType(PayType.ALIPAY.getCode());
        } else {
            orderShareProfit.setPayType(PayType.WEIXINPAY.getCode());
        }
//        orderShareProfit.setTradeNo(tradeNo);
        return orderShareProfit;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<OrderShareProfit> updateOrderStatusAndHandleWithTransaction(OrderBase orderBase, Integer status) {
        //修改的标志
        Boolean flag = false;
        //判断订单状态是否存在
        if (Objects.isNull(orderBase.getOrderStatus()) || Objects.isNull(status)) {
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_STATUS_ERROR);
        }

        Date utime = new Date();
        //获取订单流程map
        OrderStatusMap map = new OrderStatusMap();
        //根据现有的订单状态获取要转换的订单状态
        List<Integer> statusList = map.get(orderBase.getOrderStatus());
        if (statusList != null && statusList.size() > 0) {
            for (Integer s : statusList) {
                if (s.equals(status)) {
                    //记录订单状态流向
                    loggerOrder.info("订单:" + orderBase.getOrderNo() + "从" + OrderStatus.getOrderStatus(orderBase.getOrderStatus()) + "修改成" + OrderStatus.getOrderStatus(status));
                    orderStatusFlowService.saveOne(orderBase, status, orderBase.getUserId() + "");
                    orderBase.setOrderStatus(status);
                    orderBase.setUtime(utime);
                    updateById(orderBase);
                    flag = true;
                    break;
                }
            }
        }
        //修改不成功
        if (!flag) {
            loggerOrder.error("订单状态修改失败:" + orderBase.getOrderNo() + "不能从" + OrderStatus.getOrderStatus(orderBase.getOrderStatus()) + "修改成" + OrderStatus.getOrderStatus(status));
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_CHANGE_STATUS_FAIL);
        }
        //修改成功后做相应的操作
        return handleDueToOrderStatus(orderBase, utime);
    }

    @Override
    public void updateOrderStatusAndHandle(OrderBase orderBase, Integer status) {

        List<OrderShareProfit> profits = updateOrderStatusAndHandleWithTransaction(orderBase, status);

        if (profits == null) {
            return;
        }

    }

    @Override
    public void updateOrderStatusAndHandle(String orderNo, Integer status) {
        if (StringUtils.isEmpty(orderNo)) {
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_DONT_EXISTS);
        }
        OrderBase orderBase = selectById(orderNo);
        //如果更新不成功
        updateOrderStatusAndHandle(orderBase, status);
    }


    public Page<WXOrderListDto> getWXOrderListPage(Pageable<WXOrderQueryDto> pageable) {
        Page<OrderBase> page = new Page<>();
        BeanUtils.copyProperties(pageable, page);
        EntityWrapper<OrderBase> entityWrapper = new EntityWrapper<>();

        // String openId = pageable.getQuery().getOpenId();
        User user = userService.getCurrentUser();
        // if (user == null) {
        //     loggerOrder.info("查询订单列表时,openId="+openId);
        //     LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
        // }
        Integer userId = user.getId();
        entityWrapper.eq("user_id", userId).eq("is_deleted", 0);
        if (pageable.getQuery().getCardNum() != null) {
            entityWrapper.eq("pay_card_num", pageable.getQuery().getCardNum());
        }
        if (pageable.getQuery().getStartTime() != null) {
            entityWrapper.ge("ctime", pageable.getQuery().getStartTime());
        }
        if (pageable.getQuery().getEndTime() != null) {
            entityWrapper.le("ctime", pageable.getQuery().getEndTime());
        }
        Integer[] orderStatuss = {OrderStatus.USING.getCode(), OrderStatus.FINISH.getCode(), OrderStatus.REFUNDED.getCode()};
        entityWrapper.in("order_status", orderStatuss);
        Page<OrderBase> basePage = selectPage(page,
                QueryResolverUtils.parse(pageable.getQuery(), entityWrapper));
        List<OrderBase> orderBases = basePage.getRecords();
        List<WXOrderListDto> wxOrderListDtos = new ArrayList<>();
        Page<WXOrderListDto> result = new Page<>();
        for (OrderBase orderBase : orderBases) {
            WXOrderListDto wxOrderListDto = getWxOrderListDto(orderBase);
            wxOrderListDtos.add(wxOrderListDto);
        }
        BeanUtils.copyProperties(basePage, result);
        result.setRecords(wxOrderListDtos);
        return result;
    }

    public WXOrderListDto getWxOrderListDto(OrderBase orderBase) {
        WXOrderListDto wxOrderListDto = new WXOrderListDto();
        wxOrderListDto.setCtime(orderBase.getCtime());
        String orderNo = orderBase.getOrderNo();
        wxOrderListDto.setOrderNo(orderNo);
        wxOrderListDto.setDeviceSno(orderBase.getSno());
        wxOrderListDto.setServiceModeName(orderBase.getServiceModeName());
        wxOrderListDto.setStatusCode(orderBase.getOrderStatus());
        String status = OrderStatus.getOrderStatus(orderBase.getOrderStatus()).getMsg();
        wxOrderListDto.setStatusCode(orderBase.getOrderStatus());
        wxOrderListDto.setStatus(status);
        Integer payTypeCode = orderBase.getPayType();
        String paytype = PayType.getPayType(payTypeCode).getName();
        wxOrderListDto.setPayType(paytype);
        wxOrderListDto.setPayMoney(orderBase.getAmount());
        OrderExtByTime orderExtByTime = orderExtByTimeService.selectById(orderNo);
        if (!ParamUtil.isNullOrEmptyOrZero(orderExtByTime)) {
            wxOrderListDto.setShouldPayMoney(orderExtByTime.getPrice());
        }
        Device device = deviceService.selectById(orderBase.getSno());
        if (!ParamUtil.isNullOrEmptyOrZero(device)) {
            wxOrderListDto.setDeviceArea(orderBase.getLaunchAreaName() == null ? device.getLaunchAreaName() : orderBase.getLaunchAreaName());
            wxOrderListDto.setDeviceName(device.getName());
        }

        //只需要列出下单的时候的收费情况
        OrderExtByQuantity orderExtByQuantity = orderExtByQuantityService.selectById(orderBase.getOrderNo());
        // ProductServiceMode serviceMode = productServiceModeService.selectById(orderBase.getServiceModeId());
        // if(Objects.nonNull(serviceMode)){
        //     List<ProductServiceDetail> list = productServiceDetailService.selectList(new EntityWrapper<ProductServiceDetail>().eq("service_mode_id", serviceMode.getId()).orderBy("num"));
        //     if(CollectionUtils.isNotEmpty(list)){
        List<ProductServiceDetailVo> resList = new ArrayList<>();
        // for (int i = 0; i < list.size(); ++i) {
        ProductServiceDetailVo productServiceDetailVo = new ProductServiceDetailVo();
        productServiceDetailVo.setNum(orderExtByQuantity.getQuantity());
        productServiceDetailVo.setPrice(orderExtByQuantity.getPrice());
        productServiceDetailVo.setUnit(orderExtByQuantity.getUnit());
        productServiceDetailVo.setServiceType(orderExtByQuantity.getServiceType());
        resList.add(productServiceDetailVo);
        wxOrderListDto.setPrice(productServiceDetailVo.getPrice());
        // }
        AppProductServiceDetailVo appServiceModeDetailDto = new AppProductServiceDetailVo();
        appServiceModeDetailDto.setList(resList);
        appServiceModeDetailDto.setUnit(orderExtByQuantity.getUnit());
        wxOrderListDto.setAppProductServiceDetailVo(appServiceModeDetailDto);
        // }
        // wxOrderListDto.setWorkingMode(serviceMode.getWorkingMode());
        // }
        wxOrderListDto.setPayTime(orderBase.getPayTime());
        //获取收费模式名称
        if (!ParamUtil.isNullOrEmptyOrZero(orderExtByQuantity)) {
            wxOrderListDto.setDuration(orderExtByQuantity.getQuantity() + orderExtByQuantity.getUnit());
        }

        ProductServiceDetail productServiceDetail = productServiceDetailService.selectById(orderBase.getServiceModeDetailId());
        if (productServiceDetail != null) {
            wxOrderListDto.setPrice(productServiceDetail.getPrice());
        }

        return wxOrderListDto;
    }

    @Override
    public WXOrderListDto getWxPayingOrder(String deviceSno) {
        if (StringUtils.isBlank(deviceSno)) {
            return null;
        }
        OrderBase orderBase = orderBaseDao.findByDeviceIdAndStatus(deviceSno, OrderStatus.PAYING.getCode());
        WXOrderListDto wxOrderListDto = getWxOrderListDto(orderBase);
        return wxOrderListDto;
    }

    @Override
    public void deleteUserShowOrder(List<String> orderNos) {
        List<OrderBase> orderBases = selectBatchIds(orderNos);
        for (OrderBase orderBase : orderBases) {
            orderBase.setIsDeleted(1);
            updateById(orderBase);
        }
    }

    /**
     * 检查设备和订单,是否满足下单和支付的条件
     *
     * @param sno
     * @return
     */
    @Override
    public Map<String, Object> checkBeforeOrder(String sno, Integer userBrowserAgentType) {
        /**
         * 1、检查设备是否存在
         * 2、检查设备是否投入运营,并获取投入运营的微信配置
         * 3、查看该设备对应的收费模式是否为免费
         * 4、检查设备是否空闲可用
         * 5、检查用户是否存在
         * 6、检查用户是否存在未完成的订单
         */
        Map<String, Object> result = new HashedMap();
        Device device = deviceService.getDeviceInfoBySno(sno);
        if (device == null) {
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_DONT_EXISTS);
        }

      /*  //查询该设备对应的收费模式
        ProductServiceMode serviceMode = productServiceModeService.selectById(device.getServiceId()) ;

        if (Objects.isNull(serviceMode) || ParamUtil.isNullOrEmptyOrZero(serviceMode.getUnit())) {//免费模式
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_SERVICE_MODEL_FREE);
        }*/

        SysUserExt sysUserExt = deviceService.getWxConfigByDeviceId(sno);

        if (sysUserExt == null) {
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_NOT_IN_OPERATOR);
        }

        if (device.getLock()) {
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_LOCKED);
        }

        User user = userService.getCurrentUser();

        boolean canRenew = false;
        if (!DeviceStatus.FREE.getCode().equals(device.getWorkStatus())) {
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_NOT_FREE);
//            // 获取设备当前订单
//            OrderBase orderBase = getDeviceLastUsingOrder(device.getSno());
//            // 如果是本人正在用，允许续费，否则报错设备非空闲
//            if (orderBase != null && orderBase.getUserId().equals(user.getId())) {
//                canRenew = true;
//                result.put("lastOrderNo", orderBase.getOrderNo());
//            } else {
//                LeaseException.throwSystemException(LeaseExceEnums.DEVICE_NOT_FREE);
//            }
        }

        // 检查是否需要支付初装费
//        InstallFeeOrder installFeeOrder = installFeeOrderService.getOrderBySnoAndStatus(device.getSno(), OrderStatus.PAYED);
//        if (installFeeOrder == null) {
//            InstallFeeRule installFeeRule = installFeeRuleService.getRuleByDevice(device);
//            if (installFeeRule != null) {
//                LeaseException.throwSystemException(LeaseExceEnums.INSTALL_FEE_NEED);
//            }
//        }

        result.put("device", device);
        result.put("sysUserExt", sysUserExt);
        result.put("user", user);
        result.put("canRenew", canRenew);
        return result;
    }

    private boolean hasUsingOrderOnProduct(Integer userId, Integer productId) {
        if (Objects.isNull(userId) || Objects.isNull(productId)) {
            loggerOrder.error("=====执行方法hasUsingOrderOnProduct的两个参数userId:{},productId:{}有异常====", userId, productId);
            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
            return true;
        }
        if (orderBaseDao.countUsingOrderByUserIdAndProductId(userId, productId) > 0) {
            loggerOrder.info("=====用户{},在产品{}已有在使用中的订单,不能重复下单======", userId, productId);
            return true;
        }

        return false;
    }


    @Override
    @Transactional
    public AppOrderVo createOrder(PayOrderDto payOrderDto, Integer userBrowserAgentType) {
        Map<String, Object> map = checkBeforeOrder(payOrderDto.getSno(), userBrowserAgentType);
        if (map == null) {
            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
        }
        User user = (User) map.get("user");
        Device device = (Device) map.get("device");
        SysUserExt sysUserExt = (SysUserExt) map.get("sysUserExt");
        Boolean canRenew = (Boolean) map.get("canRenew");
       /* //判断用户是否有使用中订单
        if (hasUsingOrderOnProduct(user.getId(),device.getProductId())) {
            OrderBase orderBase = orderBaseDao.findUsingOrderByUserIdAndProductId(user.getId(),device.getProductId());
            AppOrderVo appOrderVo = new AppOrderVo();
            appOrderVo.setPayMoney(new BigDecimal(orderBase.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
            appOrderVo.setOrderNo(orderBase.getOrderNo());
            appOrderVo.setCtime(orderBase.getCtime());
            appOrderVo.setStatusCode(orderBase.getOrderStatus());
            appOrderVo.setStatus(OrderStatus.getMsg(orderBase.getOrderStatus()));
            return appOrderVo;
        }*/
        //查询该设备对应的收费模式
        ProductServiceMode serviceMode = productServiceModeService.selectById(payOrderDto.getServiceModeId());
        if (Objects.isNull(serviceMode) || ParamUtil.isNullOrEmptyOrZero(serviceMode.getUnit())) {//免费模式
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_SERVICE_MODEL_FREE);
        }

        Double lastRest = 0.0;
        Double lastUsed = 0.0;

        ProductServiceDetail productServiceDetail = null;
        //获取时长
        Double duration = null;
        //选择数据设定好的收费模式
        if (!ParamUtil.isNullOrZero(payOrderDto.getProductServiceDetailId())) {
            productServiceDetail = productServiceDetailService.selectById(payOrderDto.getProductServiceDetailId());
            if (Objects.isNull(productServiceDetail)) {
                LeaseException.throwSystemException(LeaseExceEnums.DEVICE_SERVICE_MODEL_DETAIL_NOT_EXIST);
            }
            Double num = productServiceDetail.getNum();
            if (canRenew) {
                // 续费，获取现有的值，累加上去
                ProductCommandConfig commandConfig = productCommandConfigService.selectById(serviceMode.getServiceTypeId());
                lastRest = getRefDataPointValue(commandConfig, device).doubleValue();
                Double countNum = num + lastRest;
                String command = productCommandConfigService.getCommandByConfig(commandConfig, countNum);
                productServiceDetail.setCommand(command);
            }
            duration = num;
        } else if (!ParamUtil.isNullOrZero(payOrderDto.getQuantity())) {//选择自定义数量
            ProductCommandConfig commandConfig = productCommandConfigService.selectById(serviceMode.getServiceTypeId());
            Double price = serviceMode.getUnitPrice();
            productServiceDetail = new ProductServiceDetail();
            productServiceDetail.setServiceModeId(device.getServiceId());
            Double num = payOrderDto.getQuantity();
            String command = "";
            if (canRenew) {
                // 续费，获取现有的值，累加上去
                lastRest = getRefDataPointValue(commandConfig, device).doubleValue();
                Double countNum = num + lastRest;
                command = productCommandConfigService.getCommandByConfig(commandConfig, countNum);
            } else {
                command = productCommandConfigService.getCommandByConfig(commandConfig, num);
            }
            //增加冷热水的控制指令
            if (!ParamUtil.isNullOrEmptyOrZero(payOrderDto.getName()) &&
                    (!ParamUtil.isNullOrEmptyOrZero(payOrderDto.getValue()))) {
                JSONObject jsonObject = JSON.parseObject(command);
                jsonObject.put(payOrderDto.getName(), payOrderDto.getValue());
                command = jsonObject.toJSONString();
            }
            productServiceDetail.setCommand(command);
            productServiceDetail.setPrice(num * price);
            productServiceDetail.setNum(num);
            productServiceDetail.setUnit(serviceMode.getUnit());
            //TODO:有问题这种获取订单时长
            duration = num;
        } else {
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_SERVICE_MODEL_DETAIL_NOT_EXIST);
        }
        DeviceLaunchArea area = deviceLaunchAreaService.selectById(device.getLaunchAreaId());
        if (Objects.isNull(area)) {
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_WITHOUT_LAUNCH_AREA);
        }
        //创建订单
        OrderBase orderBase = createOrderForWeiXin(productServiceDetail, serviceMode, user, device, sysUserExt);

//        //卡券处理
//        if (!ParamUtil.isNullOrEmptyOrZero(payOrderDto.getCardId())) {
//            CardConsumeDto cardConsumeDto = new CardConsumeDto(payOrderDto.getCardId(), payOrderDto.getCardCode(), orderBase.getOrderNo());
//            cardService.useCardForOrder(user, cardConsumeDto);
//            orderBase = selectById(orderBase.getOrderNo());
//            if (orderBase == null) {
//                loggerOrder.error("order " + orderBase.getOrderNo() + "NOT EXIST for user " + user.getId() + " pay with card " + payOrderDto.getCardId());
//                LeaseException.throwSystemException(LeaseExceEnums.OPERATION_FAIL);
//            }
//        }

        if (orderBase.getAmount().equals(0.0)) {
            loggerOrder.info("order {} amount zero update status payed", orderBase.getAmount());
            updateOrderStatusAndHandle(orderBase, OrderStatus.PAYING.getCode());
            OrderBase updatePayTimeForOrder = new OrderBase();
            updatePayTimeForOrder.setOrderNo(orderBase.getOrderNo());
            Date payTime = new Date();
            updatePayTimeForOrder.setPayTime(payTime);
            updateById(updatePayTimeForOrder);
            orderBase.setPayTime(payTime);
            updateOrderStatusAndHandle(orderBase, OrderStatus.PAYED.getCode());
        }

        //续费单处理
//        OrderExtByQuantity quantity = orderExtByQuantityService.selectById(orderBase.getOrderNo());
//        String lastOrderNo = (String) map.get("lastOrderNo");
//        OrderBase lastOrderBase = selectById(lastOrderNo);
//        if (!ParamUtil.isNullOrEmptyOrZero(lastOrderBase)) {
//            //将续费单号记录到上一个订单中
//            lastOrderBase.setRenewOrderNo(orderBase.getOrderNo());
//            lastOrderBase.setUtime(new Date());
//            updateById(lastOrderBase);
//        }
//        OrderExtByQuantity oldQuantity = orderExtByQuantityService.selectById(lastOrderNo);
//        if (!ParamUtil.isNullOrEmptyOrZero(oldQuantity) && !ParamUtil.isNullOrEmptyOrZero(quantity)) {
//            //标记续费单
//            orderBase.setRemark("续费单");
//            updateById(orderBase);
//            lastUsed = oldQuantity.getQuantity() + (oldQuantity.getLastRest() == null ? 0 : oldQuantity.getLastRest()) - lastRest;
//            quantity.setLastRest(lastRest);
//            quantity.setLastUsed(lastUsed);
//            orderExtByQuantityService.updateById(quantity);
//        }

        AppOrderVo appOrderVo = new AppOrderVo();
        appOrderVo.setPayMoney(new BigDecimal(orderBase.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
        appOrderVo.setOrderNo(orderBase.getOrderNo());
        appOrderVo.setUnit(serviceMode.getUnit());
        appOrderVo.setCtime(orderBase.getCtime());
        appOrderVo.setDuration(duration + "");
        appOrderVo.setSno(device.getSno());
        appOrderVo.setAddress("地址不详");
        if (!Objects.isNull(area)) {
            appOrderVo.setAddress(area.getName());
        }
        return appOrderVo;
    }


    @Override
    public AppOrderVo createOrder(User user, PayOrderDto payOrderDto, Integer userBrowserAgentType) {
        Map<String, Object> map = checkBeforeOrder(payOrderDto.getSno(), userBrowserAgentType);
        if (map == null) {
            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
        }
        Device device = (Device) map.get("device");
//        ProductServiceMode serviceMode = (ProductServiceMode) map.get("serviceMode");
        SysUserExt sysUserExt = (SysUserExt) map.get("sysUserExt");

       /* //判断用户是否有使用中订单
        if (hasUsingOrderOnProduct(user.getId(),device.getProductId())) {
            OrderBase orderBase = orderBaseDao.findUsingOrderByUserIdAndProductId(user.getId(),device.getProductId());
            AppOrderVo appOrderVo = new AppOrderVo();
            appOrderVo.setPayMoney(new BigDecimal(orderBase.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
            appOrderVo.setOrderNo(orderBase.getOrderNo());
            appOrderVo.setCtime(orderBase.getCtime());
            appOrderVo.setStatusCode(orderBase.getOrderStatus());
            appOrderVo.setStatus(OrderStatus.getMsg(orderBase.getOrderStatus()));
            return appOrderVo;
        }*/
        //查询该设备对应的收费模式
        ProductServiceMode serviceMode = productServiceModeService.selectById(device.getServiceId());
        if (Objects.isNull(serviceMode) || ParamUtil.isNullOrEmptyOrZero(serviceMode.getUnit())) {//免费模式
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_SERVICE_MODEL_FREE);
        }

        ProductServiceDetail productServiceDetail = null;
        //获取时长
        Double duration = null;
        //选择数据设定好的收费模式
        if (!ParamUtil.isNullOrZero(payOrderDto.getProductServiceDetailId())) {
            productServiceDetail = productServiceDetailService.selectById(payOrderDto.getProductServiceDetailId());
            if (Objects.isNull(productServiceDetail)) {
                LeaseException.throwSystemException(LeaseExceEnums.DEVICE_SERVICE_MODEL_DETAIL_NOT_EXIST);
            }
            duration = productServiceDetail.getNum();
        } else if (!ParamUtil.isNullOrZero(payOrderDto.getQuantity())) {//选择自定义数量
            ProductCommandConfig commandConfig = productCommandConfigService.selectById(serviceMode.getServiceTypeId());
            Double price = serviceMode.getUnitPrice();
            productServiceDetail = new ProductServiceDetail();
            productServiceDetail.setServiceModeId(device.getServiceId());
            String command = productCommandConfigService.getCommandByConfig(commandConfig, payOrderDto.getQuantity());
            //增加冷热水的控制指令
            if (!ParamUtil.isNullOrEmptyOrZero(payOrderDto.getName()) && (!ParamUtil.isNullOrEmptyOrZero(payOrderDto.getValue()))) {
                JSONObject jsonObject = JSON.parseObject(command);
                jsonObject.put(payOrderDto.getName(), payOrderDto.getValue());
                command = jsonObject.toJSONString();
            }
            productServiceDetail.setCommand(command);
            productServiceDetail.setPrice(payOrderDto.getQuantity() * price);
            productServiceDetail.setNum(payOrderDto.getQuantity());
            productServiceDetail.setUnit(serviceMode.getUnit());
            //TODO:有问题这种获取订单时长
            duration = payOrderDto.getQuantity();
        } else {
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_SERVICE_MODEL_DETAIL_NOT_EXIST);
        }
        DeviceLaunchArea area = deviceLaunchAreaService.selectById(device.getLaunchAreaId());
        if (Objects.isNull(area)) {
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_WITHOUT_LAUNCH_AREA);
        }
        //创建订单
        OrderBase orderBase = createOrderForWeiXin(productServiceDetail, serviceMode, user, device, sysUserExt);

        AppOrderVo appOrderVo = new AppOrderVo();
        appOrderVo.setPayMoney(new BigDecimal(orderBase.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
        appOrderVo.setOrderNo(orderBase.getOrderNo());
        appOrderVo.setOrderStatus(orderBase.getOrderStatus());
        appOrderVo.setUnit(serviceMode.getUnit());
        appOrderVo.setCtime(orderBase.getCtime());
        appOrderVo.setDuration(duration + "");
        appOrderVo.setSno(device.getSno());
        appOrderVo.setAddress("地址不详");
        if (!Objects.isNull(area)) {
            appOrderVo.setAddress(area.getName());
        }
        return appOrderVo;
    }


    private Integer getRefDataPointValue(ProductCommandConfig commandConfig, Device device) {
        Product product = productService.selectById(device.getProductId());
        JSONObject deviceCurrentStatus =
                redisService.getDeviceCurrentStatus(product.getGizwitsProductKey(), device.getMac().toUpperCase());
        if (ParamUtil.isNullOrEmptyOrZero(deviceCurrentStatus)) {
            LeaseException.throwSystemException(LeaseExceEnums.REDIS_WITHOUT_DEVICE_INFO);
        }

        Integer value = deviceCurrentStatus.getInteger(commandConfig.getRefDataPoint());
        if (value == null) value = 0;
        return value;
    }


    private Boolean validParamForChargeCard(String cardNum, Device device) {
        if (StringUtils.isBlank(cardNum)) {
            loggerOrder.error("创建订单时，充值卡号为空");
            return false;
        }
        if (ParamUtil.isNullOrEmptyOrZero(device)) {
            loggerOrder.error("设备不存在，请查看数据库");
            return false;
        }
        return true;
    }

    @Override
    public OrderBase createOrderForChargeCard(String cardNum, Device device) {
        if (!validParamForChargeCard(cardNum, device)) {
            return null;
        }
        //获取设备的收费模式，详情，最终获取设备对应的消费金额
        ProductServiceMode serviceMode = productServiceModeService.selectById(device.getServiceId());
        if (ParamUtil.isNullOrEmptyOrZero(serviceMode)) {
            loggerOrder.error("收费模式" + device.getServiceId() + "不存在");
            return null;
        }
        List<ProductServiceDetail> serviceDetailList = productServiceDetailService.getProductServiceDetailByServiceModelId(serviceMode.getId());
        if (CollectionUtils.isEmpty(serviceDetailList)) {
            loggerOrder.error("收费模式" + serviceMode.getId() + "对应的收费详情不存在");
            return null;
        }
        //无论是定时，按次，按锅，按圈都是使用收费详情里面的第一个
        ProductServiceDetail productServiceDetail = serviceDetailList.get(0);
        //获取cardNum对应的用户
        UserChargeCard userChargeCard = userChargeCardService.selectOne(new EntityWrapper<UserChargeCard>().eq("card_num", cardNum));
        if (ParamUtil.isNullOrEmptyOrZero(userChargeCard)) {
            loggerOrder.error("充值卡:" + cardNum + "不存在");
            return null;
        }
        if (userChargeCard.getMoney().compareTo(productServiceDetail.getPrice()) < 0) {
            loggerOrder.error("===充值卡{}的剩余金额{}不够本地设备收费{}使用-====", userChargeCard.getCardNum(), userChargeCard.getMoney(), productServiceDetail.getPrice());
            return null;
        }

        User user = userService.selectById(userChargeCard.getUserId());
        if (ParamUtil.isNullOrEmptyOrZero(user)) {
            loggerOrder.error("用户" + userChargeCard.getUserId() + "不存在");
            return null;
        }
        //获取该订单的归属人员
        SysUserExt sysUserExt = deviceService.getWxConfigByDeviceId(device.getSno());
        if (ParamUtil.isNullOrEmptyOrZero(sysUserExt)) {
            loggerOrder.error("设备为投入运营，请查看数据库中是否有运营商与设备" + device.getSno() + "有关联");
            return null;
        }
        DeviceLaunchArea area = deviceLaunchAreaService.selectById(device.getLaunchAreaId());
        if (ParamUtil.isNullOrEmptyOrZero(area)) {
            loggerOrder.error("投放点：" + device.getLaunchAreaId() + "不存在");
            return null;
        }
        redisService.cacheDeviceLockByOrder(device.getSno(), userChargeCard.getCardNum(), 5L);//锁定5秒钟,防止重复刷卡问题
        OrderBase orderBase = createOrderBase(productServiceDetail, serviceMode, user, device, sysUserExt.getSysUserId());
        orderBase.setPayCardNum(cardNum);
        orderBase.setPayType(PayType.CARD.getCode());
        return orderBase;
    }

    @Override
    public AppOrderVo getUsingOrderByUserIdentify(String userIdentify) {
        //如果根据openid可用找到该用户在该公众号存在订单并且订单状态为使用中，则返回该订单信息
        User user = userService.getUserByIdOrOpenidOrMobile(userIdentify);
        List<OrderBase> list = selectList(new EntityWrapper<OrderBase>().eq("user_id", user.getId()).eq("order_status", OrderStatus.USING.getCode()));
        if (ParamUtil.isNullOrZero(list)) {//判断订单是否存在
            return null;
        }
        if (list.size() > 1) {//判断是否存在多个使用中的订单
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_IN_USING_REPEAT);
        }

        OrderBase orderBase = list.get(0);

        //如果不存在就返回null回去
        if (Objects.isNull(orderBase)) {
            return null;
        }
        ProductServiceMode produtServiceMode = productServiceModeService.selectById(orderBase.getServiceModeId());

        OrderExtByQuantity orderExtByQuantity = orderExtByQuantityService.selectById(orderBase.getOrderNo());
        if (Objects.isNull(orderExtByQuantity)) {
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_EXT_QUANTITY_NOT_EXIST);
        }
        AppOrderVo appOrderVo = new AppOrderVo();
        appOrderVo.setSno(orderBase.getSno());
        appOrderVo.setPayMoney(new BigDecimal(orderBase.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
        appOrderVo.setPayTime(orderBase.getPayTime());
        appOrderVo.setUnit(produtServiceMode.getUnit());
        appOrderVo.setCtime(orderBase.getCtime());
        appOrderVo.setOrderNo(orderBase.getOrderNo());
        appOrderVo.setDuration(orderExtByQuantity.getQuantity() + "");
        return appOrderVo;
    }

    public List<DeviceUsingVo> getUsingDeviceList(String name) {
        User user = userService.getCurrentUser();
        if (Objects.isNull(user)) {
            loggerOrder.error("=====用户{}不存在====");
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS, "用户不存在");
        }
        List<String> snos = new ArrayList<>();
        if (!ParamUtil.isNullOrEmptyOrZero(name)) {
            snos = deviceService.selectList(new EntityWrapper<Device>().like("name", name)).stream().map(Device::getSno).collect(Collectors.toList());
        }

        EntityWrapper<OrderBase> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_id", user.getId()).eq("order_status", OrderStatus.USING.getCode());
        if (!ParamUtil.isNullOrEmptyOrZero(snos)) {
            entityWrapper.in("sno", snos);
        }
        List<OrderBase> usingOrderList = selectList(entityWrapper);

        // 把最后是该用户使用的设备也添加上
        List<Integer> orderStatusList = Arrays.asList(OrderStatus.PAYED.getCode(), OrderStatus.USING.getCode(), OrderStatus.FINISH.getCode());
        List<OrderBase> orders = baseMapper.selectDeviceLastOrder(orderStatusList, user.getId(), snos);
        List<String> usingDeviceSnos = usingOrderList.stream().map(order -> order.getSno()).distinct().collect(Collectors.toList());
        for (OrderBase orderBase : orders) {
            if (!usingDeviceSnos.contains(orderBase.getSno())) {
                usingOrderList.add(orderBase);
            }
        }

        Date now = new Date();
        if (CollectionUtils.isNotEmpty(usingOrderList)) {
            List<DeviceUsingVo> deviceList = new ArrayList<>();
            for (OrderBase order : usingOrderList) {
                DeviceUsingVo deviceVo = new DeviceUsingVo();
                deviceVo.setOrderNo(order.getOrderNo());
                deviceVo.setPayTime(order.getPayTime());
                deviceVo.setServiceId(order.getServiceModeId());
                deviceVo.setServiceName(order.getServiceModeName());
                Device device = deviceService.getDeviceInfoBySno(order.getSno());
                if (Objects.isNull(device)) {
                    loggerOrder.error("====订单{}d关联的设备{}不存在=====", order.getOrderNo(), order.getSno());
                    continue;
                }
                Product product = productService.getProductByProductId(device.getProductId());
                if (Objects.isNull(product)) {
                    loggerOrder.error("====订单{}关联的设备{}相关的产品{}不存在{}=====", order.getOrderNo(), device.getSno(),
                            device.getProductId());
                    continue;
                }
                OrderExtByQuantity extByQuantity = orderExtByQuantityService.selectById(order.getOrderNo());
                if (Objects.nonNull(extByQuantity)) {
                    deviceVo.setQuantity(extByQuantity.getQuantity());
                    deviceVo.setUnit(extByQuantity.getUnit());
                }
                deviceVo.setProductKey(product.getGizwitsProductKey());
                deviceVo.setMac(device.getMac());
                deviceVo.setDeviceName(device.getName());
                deviceVo.setSno(device.getSno());
                deviceVo.setStatusCode(device.getWorkStatus() + "");
                deviceVo.setStatus(DeviceStatus.getShowName(device.getWorkStatus(), device.getFaultStatus(), device.getLock()));
                deviceVo.setOnlineStatus(device.getOnlineStatus());
                deviceVo.setOnlineStatusDescription(DeviceOnlineStatus.getName(device.getOnlineStatus()));
                deviceVo.setNow(now);
                deviceList.add(deviceVo);
            }
            return deviceList;
        }
        return null;
    }

    public AppOrderVo getOrderDetail(String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
        }
        OrderBase orderBase = selectById(orderNo);
        if (Objects.isNull(orderBase)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        User user = userService.getUserByIdOrOpenidOrMobile(orderBase.getUserId() + "");
        if (Objects.isNull(user)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        ProductServiceMode serviceMode = productServiceModeService.selectById(orderBase.getServiceModeId());
        Device device = deviceService.getDeviceInfoBySno(orderBase.getSno());

        AppOrderVo appOrderVo = new AppOrderVo();
        appOrderVo.setSno(orderBase.getSno());
        appOrderVo.setPayMoney(new BigDecimal(orderBase.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
        appOrderVo.setPayTime(orderBase.getPayTime());
        appOrderVo.setUnit(serviceMode.getUnit());
        appOrderVo.setWorkingMode(serviceMode.getWorkingMode());
        appOrderVo.setCtime(orderBase.getCtime());
        appOrderVo.setOrderNo(orderBase.getOrderNo());
        appOrderVo.setUsername(user.getNickname());
        appOrderVo.setMac(device.getMac());
        appOrderVo.setDeviceLaunchName(device.getLaunchAreaName());
        String status = OrderStatus.getOrderStatus(orderBase.getOrderStatus()).getMsg();
        appOrderVo.setStatusCode(orderBase.getOrderStatus());
        appOrderVo.setStatus(status);
        if (Objects.nonNull(serviceMode)) {
            EntityWrapper<ProductServiceDetail> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("id", orderBase.getServiceModeDetailId());
            ProductServiceDetail productServiceDetail = productServiceDetailService.selectOne(entityWrapper);
            if (Objects.nonNull(productServiceDetail)) {
                if (Objects.nonNull(serviceMode)) {
                    List<ProductServiceDetail> list = productServiceDetailService.selectList(new EntityWrapper<ProductServiceDetail>().eq("service_mode_id", serviceMode.getId()).orderBy("num"));
                    if (CollectionUtils.isNotEmpty(list)) {
                        List<ProductServiceDetailVo> resList = new ArrayList<>();
                        for (int i = 0; i < list.size(); ++i) {
                            ProductServiceDetailVo productServiceDetailVo = new ProductServiceDetailVo(list.get(i));
                            resList.add(productServiceDetailVo);
                        }
                        AppProductServiceDetailVo appServiceModeDetailDto = new AppProductServiceDetailVo();
                        appServiceModeDetailDto.setList(resList);
                        appServiceModeDetailDto.setUnit(serviceMode.getUnit());
                        appOrderVo.setAppServiceModeDetailDto(appServiceModeDetailDto);
                    }
                }

                appOrderVo.setServiceModeDetail(new ProductServiceDetailVo(productServiceDetail));
            }
        }
        return appOrderVo;
    }

    @Override
    public OrderBase getUsingOrderByOpenid(String sno, String openid) {
        //如果根据openid可用找到该用户在该公众号存在订单并且订单状态为使用中，则返回该订单信息
        User user = userService.getUserByIdOrOpenidOrMobile(openid);
        List<OrderBase> list = selectList(new EntityWrapper<OrderBase>().eq("user_id", user.getId())
                .eq("sno", sno)
                .eq("order_status", OrderStatus.USING.getCode()));
        if (ParamUtil.isNullOrZero(list)) {//判断订单是否存在
            return null;
        }
        if (list.size() > 1) {//判断是否存在多个使用中的订单
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_IN_USING_REPEAT);
        }
        return list.get(0);
    }

    @Override
    public AppOrderVo judgeOrder(String orderNo) {
        OrderBase orderBase = selectById(orderNo);
        if (Objects.isNull(orderBase)) {
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_DONT_EXISTS);
        }
        AppOrderVo appOrderVo = new AppOrderVo();
        appOrderVo.buildOrderVo(orderBase, null, null, null);
        return appOrderVo;
    }

    @Override
    @Transactional
    public void earlyEnd(EarlyEndDto earlyEndDto) {
        if (StringUtils.isBlank(earlyEndDto.getOrderNo())) {
            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
        }
        OrderBase orderBase = selectById(earlyEndDto.getOrderNo());
        if (Objects.isNull(orderBase)) {
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_DONT_EXISTS);
        }
        if (StringUtils.isNotBlank(earlyEndDto.getOpenid())) {
            User user = userService.getUserByIdOrOpenidOrMobile(orderBase.getUserId().toString());
            if (Objects.isNull(user)) {
                LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
            }
            if (!StringUtils.equals(earlyEndDto.getOpenid(), user.getOpenid())) {
                LeaseException.throwSystemException(LeaseExceEnums.ORDER_NOT_CURRENT_USER);
            }
        } else if (!OrderUtil.isAuthorizedOrder(orderBase, sysUserService)) {
            // 当前系统用户不能看到该订单
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_DONT_EXISTS);
        }

        // 订单是使用中的状态
        if (!orderBase.getOrderStatus().equals(OrderStatus.USING.getCode())) {
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_STATUS_ERROR);
        }

        // 控制设备结束使用
        Device device = deviceService.getDeviceInfoBySno(orderBase.getSno());
        if (device == null) {
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_DONT_EXISTS);
        }

        Product product = productService.getProductByProductId(device.getProductId());
        if (product == null) {
            LeaseException.throwSystemException(LeaseExceEnums.PRODUCT_DONT_EXISTS);
        }

        String productKey = product.getGizwitsProductKey();
        JSONObject command = redisService.getProductStatusCommand(productKey, StatusCommandType.FINISH.getCode());
        JSONObject finishCommand = new JSONObject();
        for (String key : command.keySet()) {
            JSONObject object = command.getJSONObject(key);
            if (object != null) {
                Object value = object.get("value");
                if (value != null) {
                    finishCommand.put(key, value);
                }
            }
        }
        if (finishCommand.isEmpty()) {
            finishCommand = command;
        }

        loggerOrder.info("early end order:" + earlyEndDto.getOrderNo() + " control device stop sno:" + device.getSno()
                + " productKey:{} did:{} command:{}", productKey, device.getGizDid(), finishCommand);

        if (finishCommand == null || finishCommand.isEmpty()) {
            LeaseException.throwSystemException(LeaseExceEnums.COMMAND_NOT_EXISTS);
        }

        boolean success = DeviceControlAPI.remoteControl(productKey, device.getGizDid(), finishCommand);
        if (!success) {
            // 控制失败
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_CONTROL_FAIL);
        }

        // 提前结束订单
        OrderBase earlyEndOrder = new OrderBase();
        earlyEndOrder.setOrderNo(earlyEndDto.getOrderNo());
        earlyEndOrder.setEarlyEnd(true);
        earlyEndOrder.setEarlyEndTime(new Date());
        updateById(earlyEndOrder);
        loggerOrder.info("early end order:" + earlyEndDto.getOrderNo() + " save early end time");
    }

    @Override
    public List<OrderBase> findByUserIdAndStatus(Integer id, Integer code) {
        return orderBaseDao.findByUserIdAndStatus(id, code);
    }

    private void sendMessage(OrderBase orderBase) {
        //判断是否是续费单,将上一个订单结束
        OrderBase old = selectOne(new EntityWrapper<OrderBase>().eq("renew_order_no", orderBase.getOrderNo()));
        if (old != null) {
            updateOrderStatusAndHandle(old, OrderStatus.FINISH.getCode());
        }
        //将该用户其他待支付订单置为过期
        OrderBase orderBase1 = new OrderBase();
        orderBase1.setUtime(new Date());
        orderBase1.setOrderStatus(OrderStatus.EXPIRE.getCode());
        EntityWrapper<OrderBase> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_id", orderBase.getUserId()).eq("order_status", OrderStatus.PAYING.getCode()).eq("sno", orderBase.getSno());

        List<OrderBase> toExpireOrderList = selectList(entityWrapper);
        update(orderBase1, entityWrapper);
        for (OrderBase expiredOrder : toExpireOrderList) {
            removeCardDiscount(expiredOrder); // 撤销卡券使用
        }

        resolveLeaseMessage(orderBase);

    }

    @Override
    public void resolveLeaseMessage(OrderBase orderBase) {
        //判断订单的到期提醒时间
        //查看是否设有消息提醒
        loggerOrder.info("处理订单{}的消息提醒", orderBase.getOrderNo());
        Product product = productService.getProductByDeviceSno(orderBase.getSno());
        ProductServiceMode serviceMode = productServiceModeService.getProductServiceMode(orderBase.getServiceModeId());
        OrderExtByQuantity orderExtByQuantity = orderExtByQuantityService.selectById(orderBase.getOrderNo());
//        MessageTemplate messageTemplate = messageTemplateService.getByProductIdAndServiceId(product.getId(), serviceMode.getId());
        List<MessageTemplate> messageTemplateList = messageTemplateService.selectList(new EntityWrapper<MessageTemplate>().eq("product_id", product.getId()).eq("service_id", serviceMode.getId())
                .eq("depend_on_data_point", 0).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
        double days = orderExtByQuantity.getQuantity();
        int quality = Integer.parseInt(new java.text.DecimalFormat("0").format(days));
        //将下单时长转为毫秒
        long qualityTime = 0;
        loggerOrder.info("判断收费模式的收费类型：serviceType=" + serviceMode.getServiceType());
        switch (serviceMode.getUnit()) {
            case "分":
            case "分钟":
                qualityTime = quality * 60 * 1000L;
                //订单到期时间
                Date deadLine = DateKit.addMinute(orderBase.getPayTime(), quality);
                loggerOrder.info("订单到期时间 deadLine = " + deadLine.toString());
                break;
            case "时":
            case "小时":
                qualityTime = quality * 60 * 60 * 1000L;
                //订单到期时间
                deadLine = DateKit.addHour(orderBase.getPayTime(), quality);
                loggerOrder.info("订单到期时间 deadLine = " + deadLine.toString());
                break;
            case "天":
                qualityTime = quality * 24 * 60 * 60 * 1000L;
                //订单到期时间
                deadLine = DateKit.addDate(orderBase.getPayTime(), quality);
                loggerOrder.info("订单到期时间 deadLine = " + deadLine.toString());
                break;
            case "月":
                int dayInMonth = DateKit.getDay(orderBase.getPayTime());
                qualityTime = quality * dayInMonth * 24 * 60 * 60 * 1000L;
                //订单到期时间
                deadLine = DateKit.addDate(orderBase.getPayTime(), dayInMonth);
                loggerOrder.info("订单到期时间 deadLine = " + deadLine.toString());
                break;
            case "周":
                qualityTime = quality * 7 * 24 * 60 * 60 * 1000L;
                deadLine = DateKit.addDate(orderBase.getPayTime(), 7);
                loggerOrder.info("订单到期时间 deadLine = " + deadLine.toString());
                break;
            case "年":
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(orderBase.getPayTime());
                calendar.add(Calendar.YEAR, 1);
                deadLine = calendar.getTime();
                qualityTime = DateKit.differentDays(orderBase.getPayTime(), deadLine);
                loggerOrder.info("订单到期时间 deadLine = " + deadLine.toString());
                break;
            default:
                break;

        }

        long pay = orderBase.getPayTime().getTime();

        if (!ParamUtil.isNullOrEmptyOrZero(messageTemplateList)) {
            for (MessageTemplate messageTemplate : messageTemplateList) {
                if (messageTemplate.getRateType().equals(RateType.PRECENT.getCode())) {
                    Double rate = messageTemplate.getRate();
                    long metion1 = (long) (pay + (1 - rate) * qualityTime);
                    String str = (new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(metion1);
                    loggerOrder.info("缓存订单到期时间：time = " + str + "，，订单号：orderNo = " + orderBase.getOrderNo());
                    redisService.cacheOrderLeaseRemind(str, orderBase);
                } else if (messageTemplate.getRateType().equals(RateType.NUM.getCode())) {
                    double rent = messageTemplate.getRate();
                    long metion2 = (long) (pay + (days - rent) * 24 * 60 * 60 * 1000L);
                    String str = (new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(metion2);
                    loggerOrder.info("缓存订单到期时间：time = " + str + "，，订单号：orderNo = " + orderBase.getOrderNo());
                    redisService.cacheOrderLeaseRemind(str, orderBase);
                }
            }
        }
    }


    private List<OrderShareProfit> handleDueToOrderStatus(OrderBase orderBase, Date updateTime) {
        if (Objects.isNull(orderBase)) {
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_DONT_EXISTS);
        }

        loggerOrder.info("====>订单状态为" + orderBase.getOrderStatus() + "执行操作");
        OrderStatus orderStatus = OrderStatus.getOrderStatus(orderBase.getOrderStatus());
        switch (orderStatus) {
            case EXPIRE: // 过期
                removeCardDiscount(orderBase); // 撤销卡券使用
                break;
            case PAYED://
                try {
                    CommonEventPublisherUtils.publishEvent(new WxPayCallbackEvent("WxPayCallback", orderBase));
                    createGizUserAndBindGizevice(orderBase);
                } catch (Exception e) {
                    loggerOrder.info("订单：{},设备:{}请求下发指令失败!!!", orderBase.getOrderNo(), orderBase.getMac());
                }
                break;
            case FAIL:
                loggerOrder.debug("订单支付失败操作，该状态无操作===");
                break;
            case USING:
                loggerOrder.debug("订单使用中操作，该状态无操作====");
                OrderBase updateOrderServiceTime = new OrderBase();
                updateOrderServiceTime.setOrderNo(orderBase.getOrderNo());
                updateOrderServiceTime.setServiceStartTime(updateTime);
                updateOrderServiceTime.setServiceEndTime(getOrderServiceEndTime(orderBase, updateTime));
                loggerOrder.debug("订单使用时间 {} {}", updateOrderServiceTime.getServiceStartTime(), updateOrderServiceTime.getServiceEndTime());
                updateById(updateOrderServiceTime);
                // 用户绑定设备记录表
                UserDevice userDevice = userDeviceService.selectOne(new EntityWrapper<UserDevice>().eq("user_id", orderBase.getUserId()).eq("mac", orderBase.getMac()));
                userDevice = userDevice == null ? new UserDevice(new Date()) : userDevice;
                userDevice.setUtime(new Date());
                userDevice.setSno(orderBase.getSno());
                userDevice.setIsBind(BindType.BIND.getCode());
                userDevice.setMac(orderBase.getMac());
                userDevice.setUserId(orderBase.getUserId());
                userDevice.setOwnerId(orderBase.getSysUserId());
                userDevice.setIsDeleted(0);
                userDeviceService.insertOrUpdate(userDevice);
//                sendMessage(orderBase);
                break;
            case FINISH: // 订单已完成
                loggerOrder.info("订单已完成");
                OrderBase update = new OrderBase();
                update.setOrderNo(orderBase.getOrderNo());
                update.setServiceEndTime(new Date());
                updateById(update);
                refundIfNeed(orderBase, updateTime);
                // 修改订单分润信息为待分润
                return updateOrderShareProfit(orderBase.getOrderNo());
        }
        return null;
    }

    @Override
    public List<OrderShareProfit> updateOrderShareProfit(String orderNo) {
        // 更新分润单状态为待分润,厂商名下为创建状态
        loggerOrder.info("修改订单状态为待分润状态 orderNo：" + orderNo);
          return null;
    }


    /**
     * 如果订单需要退款则执行退款操作
     *
     * @param orderBase
     * @param updateTime
     */
    private void refundIfNeed(OrderBase orderBase, Date updateTime) {

        if (!OrderUtil.isEarlyEnd(orderBase)) { // 不能退款
            return;
        }

        Integer availableTime = CommonUtil.getAvailableTime(orderBase.getCommand());

        if (availableTime == null) {
            loggerOrder.warn("orderNo={},command={} can not get Available_Time", orderBase.getOrderNo(), orderBase.getCommand());
            return;
        }

        Date set = orderBase.getServiceEndTime();
        orderBase.setServiceEndTime(updateTime);

        BigDecimal usedMoney = OrderUtil.calcUsedMoney(orderBase, availableTime);

        orderBase.setServiceEndTime(set);

        loggerOrder.info("orderNo={},usedMoney={},amount={}", orderBase.getOrderNo(), usedMoney, orderBase.getAmount());

        if (CommonUtil.isZero(usedMoney.subtract(CommonUtil.round(orderBase.getAmount(), 2)))) { // 不能退款
            return;
        }

        try {
            tradeBaseService.refund(orderBase, usedMoney, 0 /*只能退款一次*/);
        } catch (Exception e) {
            loggerOrder.error("orderNo=" + orderBase.getOrderNo(), e);
        }
    }

    private Date getOrderServiceEndTime(OrderBase orderBase, Date startTime) {
        Date serviceEndTime = null;
        Integer at = CommonUtil.getAvailableTime(orderBase.getCommand());
        if (at != null) {
            serviceEndTime = DateKit.addMinute(startTime, at);
        }
        return serviceEndTime;
    }

    private void createGizUserAndBindGizevice(OrderBase orderBase) {
        //获取当前设备的产品
        Product product = productService.getProductByDeviceSno(orderBase.getSno());
        User user = userService.getUserByIdOrOpenidOrMobile(orderBase.getUserId() + "");
        //支付回调成功就说明该订单可用
        //判断redis里面是否存在token和uid等信息
        if (StringUtils.isEmpty(redisService.getUserTokenByUserName(user.getUsername()))) {
            String res = GizwitsUtil.createUser(user.getUsername(), product.getGizwitsAppId());
            JSONObject json = JSONObject.parseObject(res);
            redisService.setUserTokenByUsername(user.getUsername(), String.valueOf(json.get("uid")),
                    String.valueOf(json.get("token")),
                    Long.valueOf(String.valueOf(json.get("expire_at"))));
            //异步绑定设备
            CommonEventPublisherUtils.publishEvent(new BindGizwitsDeviceEvent("BindGizwitsDevice", product.getId(), String.valueOf(json.get("token")), orderBase.getSno(), user.getUsername()));
        } else { //如果存在就绑定该设备
            String userToken = redisService.getUserTokenByUserName(user.getUsername());
            CommonEventPublisherUtils.publishEvent(new BindGizwitsDeviceEvent("BindGizwitsDevice", product.getId(), userToken, orderBase.getSno(), user.getUsername()));
        }
    }

    @Override
    public Page<WXOrderListDto> WxOrderListPage(Pageable<OrderQueryByMobileDto> pageable) {
        OrderQueryByMobileDto dto = pageable.getQuery();
        String mobile = dto.getMobile();
        User user = userService.getUserByIdOrOpenidOrMobile(mobile);
        dto.setUserId(user.getId());
        Page<OrderBase> page = new Page<>();
        BeanUtils.copyProperties(pageable, page);
        Page<OrderBase> page1 = selectPage(page, QueryResolverUtils.parse(dto, new EntityWrapper<OrderBase>()));
        List<OrderBase> list = page1.getRecords();
        List<WXOrderListDto> wxOrderListDtos = new ArrayList<>(list.size());
        for (OrderBase orderBase : list) {
            WXOrderListDto wxOrderListDto = getWxOrderListDto(orderBase);
            wxOrderListDtos.add(wxOrderListDto);
        }
        Page<WXOrderListDto> result = new Page<>();
        BeanUtils.copyProperties(page1, result);
        result.setRecords(wxOrderListDtos);
        return result;
    }


    @Transactional
    public Boolean checkAndUpdateConsumeOrder(String orderNo, Double totalFee) {
        loggerOrder.info("订单 orderNo ：{}处理", orderNo);
        OrderBase orderBase = getOrderBaseByOrderNo(orderNo);
        if (orderBase == null) {
            loggerOrder.error("====>>>>> 订单orderNo[" + orderBase.getOrderNo() + "]在系统中未找到");
            return false;
        }
        //检查订单的状态是否是未支付
        if (orderBase.getPayTime() != null || OrderStatus.PAYED.getCode().equals(orderBase.getOrderStatus())) {
            loggerOrder.warn("====>>>> 订单tradeNo[" + IdGenerator.generateTradeNo(orderBase.getOrderNo()) + "]的状态为已支付,本次支付回调不做处理");
            return false;
        }

        //检查订单金额是否一致,注:微信回调中的金额单位是分,需要转换为元
        if (!totalFee.equals(orderBase.getAmount())) {
            loggerOrder.error("====>>>>> 订单orderNo[" + orderNo + "]的金额为[" + orderBase.getAmount() + "]与支付回调的金额[" + totalFee + "]的金额不匹配,本次支付回调不做处理");
            return false;
        }

        orderBase.setPayTime(new Date());
        updateOrderStatusAndHandle(orderBase, OrderStatus.PAYED.getCode());
        orderPayRecordService.updateOne(orderBase.getOrderNo(), OrderStatus.PAYED.getCode());

        return true;
    }

    @Override
    public AppOrderVo getForAppOrder(AppUsingOrderDto orderDto) {
        User user = userService.getCurrentUser();
        UserWxExt userWxExt = userWxExtService.getByOpenid(user.getOpenid());
        OrderBase orderBase = getUsingOrderByOpenid(orderDto.getSno(), userWxExt.getOpenid());
        //如果不存在就返回null回去
        if (Objects.isNull(orderBase)) {
            return null;
        }
        AppOrderVo appOrderVo = getAppOrderVo(orderBase);
        return appOrderVo;
    }

    private AppOrderVo getAppOrderVo(OrderBase orderBase) {
        ProductServiceMode produtServiceMode = productServiceModeService.selectById(orderBase.getServiceModeId());

        OrderExtByQuantity orderExtByQuantity = orderExtByQuantityService.selectById(orderBase.getOrderNo());
        if (Objects.isNull(orderExtByQuantity)) {
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_EXT_QUANTITY_NOT_EXIST);
        }
        AppOrderVo appOrderVo = new AppOrderVo();
        appOrderVo.setSno(orderBase.getSno());
        appOrderVo.setPayMoney(new BigDecimal(orderBase.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
        appOrderVo.setPayTime(orderBase.getPayTime());
        appOrderVo.setUnit(produtServiceMode.getUnit());
        appOrderVo.setCtime(orderBase.getCtime());
        appOrderVo.setOrderNo(orderBase.getOrderNo());
        appOrderVo.setDuration(orderExtByQuantity.getQuantity() + "");
        return appOrderVo;
    }

    @Override
    public Page<AppOrderVo> getForAppOrderList(Pageable<AppOrderListDto> pageable) {
        Page<OrderBase> page = new Page<>();
        BeanUtils.copyProperties(pageable, page);
        EntityWrapper<OrderBase> entityWrapper = new EntityWrapper<>();
        User user = userService.getCurrentUser();
        entityWrapper.eq("user_id", user.getId())
                .eq("order_status", OrderStatus.USING.getCode());
        Page<OrderBase> page1 = selectPage(page, QueryResolverUtils.parse(pageable.getQuery(), entityWrapper));
        Page<AppOrderVo> result = new Page<>();
        BeanUtils.copyProperties(page1, result);
        page1.getRecords().forEach(itme -> {
            result.getRecords().add(getAppOrderVo(itme));
        });

        return result;
    }


    public AppOrderVo getForAppOrder(String orderNo) {
        OrderBase orderBase = selectById(orderNo);
        //如果不存在就返回null回去
        if (Objects.isNull(orderBase)) {
            return null;
        }
        ProductServiceMode produtServiceMode = productServiceModeService.selectById(orderBase.getServiceModeId());

        OrderExtByQuantity orderExtByQuantity = orderExtByQuantityService.selectById(orderBase.getOrderNo());
        if (Objects.isNull(orderExtByQuantity)) {
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_EXT_QUANTITY_NOT_EXIST);
        }
        AppOrderVo appOrderVo = new AppOrderVo();
        appOrderVo.setSno(orderBase.getSno());
        appOrderVo.setPayMoney(new BigDecimal(orderBase.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
        appOrderVo.setPayTime(orderBase.getPayTime());
        appOrderVo.setUnit(produtServiceMode.getUnit());
        appOrderVo.setCtime(orderBase.getCtime());
        appOrderVo.setOrderNo(orderBase.getOrderNo());
        appOrderVo.setDuration(orderExtByQuantity.getQuantity() + "");
        return appOrderVo;
    }

    public AppOrderDetailVo getOrderDetailForApp(String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR, "请传入订单号");
        }
        OrderBase orderBase = selectById(orderNo);
        if (Objects.isNull(orderBase)) {
            loggerOrder.error("====订单{}不存在====", orderBase.getOrderNo());
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS, "订单不存在");
        }
        Device device = deviceService.selectById(orderBase.getSno());
        if (Objects.isNull(device)) {
            device = deviceService.getDeviceByMac(orderBase.getMac());
            if (Objects.nonNull(device)) {
                loggerOrder.error("====订单{}关联的设备{}不存在====", orderBase.getOrderNo(), orderBase.getSno());
                LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS, "设备不存在");
            }
        }
        User user = userService.getUserByIdOrOpenidOrMobile(orderBase.getUserId() + "");
        if (Objects.isNull(user)) {
            loggerOrder.error("====订单{}关联的用户{}不存在===", orderBase.getOrderNo(), orderBase.getUserId());
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS, "订单用户不存在");
        }


        //只需要列出下单的时候的收费情况
        OrderExtByQuantity orderExtByQuantity = orderExtByQuantityService.selectById(orderNo);
        ProductServiceDetail serviceDetail = new ProductServiceDetail();
        if (orderExtByQuantity != null) {
            serviceDetail.setUnit(orderExtByQuantity.getUnit());
            serviceDetail.setNum(orderExtByQuantity.getQuantity());
            serviceDetail.setPrice(orderExtByQuantity.getPrice());
            serviceDetail.setServiceType(orderExtByQuantity.getServiceType());
        }
        AppOrderDetailVo appOrderDetailVo = new AppOrderDetailVo();
        appOrderDetailVo.setCtime(orderBase.getCtime());
        appOrderDetailVo.setPayTime(orderBase.getPayTime());
        appOrderDetailVo.setPayType(PayType.getName(orderBase.getPayType()));
        appOrderDetailVo.setServiceName(orderBase.getServiceModeName());
        appOrderDetailVo.setServiceId(orderBase.getServiceModeId());
        appOrderDetailVo.setMoney(orderBase.getAmount());
        appOrderDetailVo.setCardDiscount(orderBase.getCardDiscount());
        appOrderDetailVo.setOrderNo(orderNo);
        if (StringUtils.isBlank(user.getNickname())) {
            appOrderDetailVo.setUsername(user.getUsername());
        } else {
            appOrderDetailVo.setUsername(user.getNickname());
        }
        appOrderDetailVo.setMac(device.getMac());
        appOrderDetailVo.setSno(device.getSno());
        appOrderDetailVo.setDeviceName(device.getName());
        appOrderDetailVo.setLaunchName(orderBase.getLaunchAreaName() == null ? device.getLaunchAreaName() : orderBase.getLaunchAreaName());

        appOrderDetailVo.setServiceDetail(new ProductServiceModeDetailForAppDto(serviceDetail));
        appOrderDetailVo.setUnit(serviceDetail.getUnit());
        appOrderDetailVo.setDuration(serviceDetail.getNum());
        appOrderDetailVo.setStatusCode(orderBase.getOrderStatus());
        appOrderDetailVo.setStatus(OrderStatus.getMsg(orderBase.getOrderStatus()));
        appOrderDetailVo.setNow(new Date());
        return appOrderDetailVo;
    }

    @Override
    public void checkNeedClockCorrect(Device device, String productKey) {
        if (Objects.isNull(device)) {
            if (loggerOrder.isDebugEnabled()) {
                loggerOrder.debug("===checkNeedClockCorrect==设备不存在");
            }
            return;
        }
        //使用中订单
        OrderBase orderBase = getDeviceLastUsingOrder(device.getSno());
        if (Objects.isNull(orderBase)) {
            if (loggerOrder.isDebugEnabled()) {
                loggerOrder.debug("====设备{}不存在正在使用中的订单====", device.getSno());
            }
            orderBase = getDeviceLastOrderByStatus(device.getSno(), OrderStatus.PAYED.getCode());
            if (Objects.isNull(orderBase)) {
                orderBase = getDeviceLastOrderEndByJob(device.getSno());
                if (Objects.isNull(orderBase)) {
                    loggerOrder.debug("====设备{}不存在定时完成的订单,不执行时钟校准====", device.getSno());
                }
                loggerOrder.debug("====设备{}不存在支付完成的订单,不执行时钟校准====", device.getSno());
                return;
            }
        }
        //订单的收费模式
        ProductServiceMode serviceMode = productServiceModeService.selectById(orderBase.getServiceModeId());
        if (Objects.isNull(serviceMode)) {
            if (loggerOrder.isDebugEnabled()) {
                loggerOrder.debug("====设备{}的订单{}的收费模式{}不存在====", device.getSno(), orderBase.getOrderNo(), orderBase.getServiceModeId());
            }
            return;
        }
        //收费模式的收费指令
        ProductCommandConfig commandConfig = productCommandConfigService.selectById(serviceMode.getServiceTypeId());
        if (Objects.isNull(commandConfig)) {
            if (loggerOrder.isDebugEnabled()) {
                loggerOrder.debug("====设备{}的订单{}的收费模式{}的收费指令{}不存在====", device.getSno(), orderBase.getOrderNo(), orderBase.getServiceModeId(), serviceMode.getServiceTypeId());
            }
            return;
        }
        //收费指令是否需要时钟校准
        if (!commandConfig.getIsClockCorrect().equals(BooleanEnum.TRUE.getCode())) {
            if (loggerOrder.isDebugEnabled()) {
                loggerOrder.debug("====设备{}的订单{}的收费模式{}的收费指令{}不需要时钟校准功能====", device.getSno(), orderBase.getOrderNo(), orderBase.getServiceModeId(), serviceMode.getServiceTypeId());
            }
            return;
        }

        redisService.cacheNeedClockCorrectDevice(productKey, device.getMac());


    }

    @Override
    public boolean handleNeedClockCorrect(String productKey, Device device, JSONObject realTimeData) {

        //使用中订单
        OrderBase orderBase = getDeviceLastUsingOrder(device.getSno());
        if (Objects.isNull(orderBase)) {
            if (loggerOrder.isDebugEnabled()) {
                loggerOrder.debug("====设备{}不存在正在使用中的订单====", device.getSno());
            }
            orderBase = getDeviceLastOrderByStatus(device.getSno(), OrderStatus.PAYED.getCode());
            if (Objects.isNull(orderBase)) {
                loggerOrder.debug("====设备{}不存在支付完成的订单,不执行时钟校准====", device.getSno());
                orderBase = getDeviceLastOrderEndByJob(device.getSno());
                if (Objects.isNull(orderBase)) {
                    loggerOrder.debug("====设备{}不存在定时完成的订单,不执行时钟校准====", device.getSno());
                }

                return false;
            }
        }

        //订单的收费模式
        ProductServiceMode serviceMode = productServiceModeService.selectById(orderBase.getServiceModeId());
        if (Objects.isNull(serviceMode)) {
            if (loggerOrder.isDebugEnabled()) {
                loggerOrder.debug("====设备{}的订单{}的收费模式{}不存在====", device.getSno(), orderBase.getOrderNo(), orderBase.getServiceModeId());
            }
            return false;
        }

        if (Objects.isNull(serviceMode)) {
            if (loggerOrder.isDebugEnabled()) {
                loggerOrder.debug("====设备{}的订单{}的收费模式{}不存在====", device.getSno(), orderBase.getOrderNo(), orderBase.getServiceModeId());
            }
            return false;
        }
        //收费模式的收费指令
        ProductCommandConfig commandConfig = productCommandConfigService.selectById(serviceMode.getServiceTypeId());
        if (Objects.isNull(commandConfig)) {
            if (loggerOrder.isDebugEnabled()) {
                loggerOrder.debug("====设备{}的订单{}的收费模式{}的收费指令{}不存在====", device.getSno(), orderBase.getOrderNo(), orderBase.getServiceModeId(), serviceMode.getServiceTypeId());
            }
            return false;
        }

        //订单使用中的状态流
        OrderStatusFlow orderStatusFlow = orderStatusFlowService.selectOne(new EntityWrapper<OrderStatusFlow>().eq("order_no", orderBase.getOrderNo()).eq("now_status", OrderStatus.USING.getCode()));
        if (Objects.isNull(orderStatusFlow)) {
            if (loggerOrder.isDebugEnabled()) {
                loggerOrder.debug("====设备{}的订单{}的使用中时间未找到====", device.getSno(), orderBase.getOrderNo());
            }
            return false;
        }

        JSONObject orderCommand = JSONObject.parseObject(orderBase.getCommand());
        if (!orderCommand.containsKey(commandConfig.getClockCorrectDatapoint())) {
            loggerOrder.warn("====设备{}的订单{}的指令{}中不包含时钟校准的数据点{}====", device.getSno(), orderBase.getOrderNo(), orderBase.getCommand(), commandConfig.getClockCorrectDatapoint());
            return false;
        }

        if (!realTimeData.containsKey(commandConfig.getClockCorrectDatapoint())) {
            loggerOrder.error("=====设备{}上报的数据点:{}不包含校准的数据点:{}", device.getMac(), realTimeData.toJSONString(), commandConfig.getClockCorrectDatapoint());
        }

        //未配置数据点换算值,默认设置为1
        if (Objects.isNull(commandConfig.getCalculateValue()) || commandConfig.getCalculateValue() == 0) {
            commandConfig.setCalculateValue(1);
        }
        //未配置校准的误差范围,默认值设置为0
        if (Objects.isNull(commandConfig.getErrorRange())) {
            commandConfig.setErrorRange(0);
        }

        loggerOrder.info("=====设备{}的收费模式有时钟校准的需求,根据订单{}获取到购买时下发的指令{}===", device.getMac(), orderBase.getOrderNo(), orderCommand.toJSONString());

        // 计划使用时长（分）
        double plannedUseTime = orderCommand.getDoubleValue(commandConfig.getClockCorrectDatapoint()) * commandConfig.getCalculateValue();
        // 实际使用时长（分）
        double usedTime = (double) (System.currentTimeMillis() - orderStatusFlow.getCtime().getTime()) / (1000 * 60);
        // 计划剩余时间（分）
        double plannedRemainTime = plannedUseTime - usedTime;
        if (plannedRemainTime < 0d) {
            plannedRemainTime = 0d;
        }
        // 设备上报剩余时间（分）
        double lastRemainTime = realTimeData.getDoubleValue(commandConfig.getClockCorrectDatapoint()) * commandConfig.getCalculateValue();
        loggerOrder.info("=====设备{}的订单{}，计划使用时长{}分钟，实际使用时长{}分钟，计划剩余时间{}分钟，设备上报剩余时间{}分钟===",
                device.getMac(), orderBase.getOrderNo(), plannedUseTime, usedTime, plannedRemainTime, lastRemainTime);

        // 设备上报的剩余时间和计划中的剩余时间相差多少分钟
        double diffRemainTime = Math.abs(lastRemainTime - plannedRemainTime);
        loggerOrder.info("=====设备{}上报的剩余时间和和订单{}计划中的剩余时间相差{}分钟===", device.getMac(), orderBase.getOrderNo(), diffRemainTime);

        if (diffRemainTime > commandConfig.getErrorRange()) {
            Double sendValue = plannedRemainTime / commandConfig.getCalculateValue();
            orderCommand.put(commandConfig.getClockCorrectDatapoint(), sendValue);
            loggerOrder.info("=====设备{}下发指令{}====", device.getMac(), orderCommand.toJSONString());
            if (deviceService.remoteDeviceControl(device.getSno(), orderCommand)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void handleAbnormalOrder(OrderBase orderBase, OrderAbnormalReason reason) {
        orderBase.setAbnormalReason(reason.getCode());
        updateOrderStatusAndHandle(orderBase, OrderStatus.ABNORMAL.getCode());
    }

    @Override
    public void updateAmountByCardDiscount(String orderNo, String cardId, String cardCode, BigDecimal cardDiscount) {
        int rows = baseMapper.updateAmountByCardDiscount(orderNo, cardId, cardCode, cardDiscount);
        if (rows == 0) {
            LeaseException.throwSystemException(LeaseExceEnums.OPERATION_FAIL);
        }
    }

    @Override
    @Transactional
    public void removeCardDiscount(OrderBase orderBase) {
        loggerOrder.info("order {} remove card discount cardId {} code {}", orderBase.getSno(), orderBase.getCardId(), orderBase.getCardCode());
        int rows = baseMapper.updateByRemovingCardDiscount(orderBase.getOrderNo());
        if (rows > 0) {

            orderBase.setCardId(null);
            orderBase.setCardCode(null);
            orderBase.setCardDiscount(null);
        }
    }

    @Override
    public int updateByShareProfitBatch(ShareProfitBatch shareProfitBatch) {
        return baseMapper.updateShareProfitBatch(shareProfitBatch.getPeriodEndTime(), shareProfitBatch.getId());
    }

    @Override
    public List<ServiceOrderAmountDto> getOrderListByShareProfitBatch(Integer batchId) {
        return baseMapper.selectByShareProfitBatch(batchId);
    }
}
