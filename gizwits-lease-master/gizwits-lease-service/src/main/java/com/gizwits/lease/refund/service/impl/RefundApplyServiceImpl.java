package com.gizwits.lease.refund.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.lease.constant.OrderStatus;
import com.gizwits.lease.constant.PayType;
import com.gizwits.lease.constant.RefundStatus;
import com.gizwits.lease.constant.TradeStatus;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.order.entity.OrderBase;
import com.gizwits.lease.order.service.OrderBaseService;
import com.gizwits.lease.redis.RedisService;
import com.gizwits.lease.refund.dao.RefundApplyDao;
import com.gizwits.lease.refund.dto.*;
import com.gizwits.lease.refund.entity.RefundApply;
import com.gizwits.lease.refund.service.RefundApplyService;
import com.gizwits.lease.trade.entity.TradeBase;
import com.gizwits.lease.trade.service.TradeAlipayService;
import com.gizwits.lease.trade.service.TradeBaseService;
import com.gizwits.lease.trade.service.TradeWeixinService;
import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.user.service.UserChargeCardService;
import com.gizwits.lease.user.service.UserService;
import com.gizwits.lease.wallet.service.UserWalletService;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 退款申请表 服务实现类
 * </p>
 *
 * @author Joke
 * @since 2018-02-08
 */
@Service
public class RefundApplyServiceImpl extends ServiceImpl<RefundApplyDao, RefundApply> implements RefundApplyService {

    private static final Logger log = LoggerFactory.getLogger(RefundApplyServiceImpl.class);

    @Autowired
    private OrderBaseService orderBaseService;
    @Autowired
    private TradeBaseService tradeBaseService;
    @Autowired
    private TradeWeixinService tradeWeixinService;
    @Autowired
    private TradeAlipayService tradeAlipayService;
    @Autowired
    private UserChargeCardService userChargeCardService;
    @Autowired
    private UserWalletService userWalletService;
    @Autowired
    private UserService userService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisService redisService;

    @Override
    public void apply(RefundAddDto dto, boolean fromUser) {
        OrderBase orderBase = orderBaseService.selectById(dto.getOrderNo());
        User user;
        if (fromUser) {
            user = userService.getCurrentUser();
        } else {
            user = userService.selectById(orderBase.getUserId());
        }
        if (user == null) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }

        // 订单只有异常、完成、退款失败的时候可以申请退款
        OrderStatus orderStatus = OrderStatus.getOrderStatus(orderBase.getOrderStatus());
        if (orderStatus != OrderStatus.ABNORMAL && orderStatus != OrderStatus.FINISH &&
                orderStatus != OrderStatus.REFUND_FAIL) {
            LeaseException.throwSystemException(LeaseExceEnums.REFUND_ORDER_STATUS);
        }

        // 订单存在申请中、已通过、已退款的退款单时，不能再次申请退款
        RefundApply refundApply =
                getByOrderNo(dto.getOrderNo(), RefundStatus.APPLY, RefundStatus.PASS, RefundStatus.REFUNDED);
        if (refundApply != null) {
            LeaseException.throwSystemException(LeaseExceEnums.REFUND_EXIST);
        }

        RefundApply forInsert = new RefundApply();
        forInsert.setRefundNo(generateRefundNo());
        forInsert.setStatus(RefundStatus.APPLY.getCode());
        forInsert.setOrderNo(dto.getOrderNo());
        forInsert.setAmount(orderBase.getAmount());
        forInsert.setUserId(user.getId());
        forInsert.setNickname(user.getNickname());
        forInsert.setUserMobile(StringUtils.isBlank(dto.getUserMobile()) ? user.getMobile() : dto.getUserMobile());
        // 没有指定支付宝收款帐号时，原路退款
        if (StringUtils.isBlank(dto.getUserAlipayAccount()) || StringUtils.isBlank(dto.getUserAlipayRealName())) {
            forInsert.setPath(orderBase.getPayType());
        } else {
            forInsert.setPath(PayType.ALIPAY.getCode());
            forInsert.setUserAlipayAccount(dto.getUserAlipayAccount());
            forInsert.setUserAlipayRealName(dto.getUserAlipayRealName());
        }
        forInsert.setRefundReason(dto.getRefundReason());
        forInsert.setSysUserId(orderBase.getSysUserId());
        Date now = new Date();
        forInsert.setCtime(now);
        forInsert.setUtime(now);
        insert(forInsert);
    }

    @Override
    public List<String> audit(RefundAuditDto dto) {
        SysUser currentUser = sysUserService.getCurrentUser();
        List<String> failIds = new LinkedList<>();
        List<RefundApply> forUpdate = new LinkedList<>();
        Integer status = dto.getAudit() ? RefundStatus.PASS.getCode() : RefundStatus.NO_PASS.getCode();
        Date now = new Date();
        for (String refundNo : dto.getRefundNos()) {
            RefundApply refundApply = selectById(refundNo);
            if (!ParamUtil.isNullOrEmptyOrZero(refundApply) && refundApply.getStatus().equals(RefundStatus.APPLY.getCode())) {
                RefundApply temp = new RefundApply();
                temp.setRefundNo(refundApply.getRefundNo());
                temp.setStatus(status);
                temp.setAuditorId(currentUser.getId());
                temp.setAuditReason(dto.getAuditReason());
                temp.setAuditTime(now);
                temp.setUtime(now);
                forUpdate.add(temp);
            } else {
                failIds.add(refundApply.getOrderNo());
            }
        }
        if (!ParamUtil.isNullOrEmptyOrZero(forUpdate)) {
            updateBatchById(forUpdate);
        }
        return failIds;
    }

    @Override
    public Page<RefundInfoDto> list(Pageable<RefundListQueryDto> pageable) {
        int pageSize = pageable.getSize();
        int current = pageable.getCurrent();
        int begin = (current - 1) * pageSize;
        RefundListQueryDto dto = pageable.getQuery();
        dto.setBeginPage(begin);
        dto.setPageSize(pageSize);
        if (dto.getCurrentId() == null) {
            SysUser sysUser = sysUserService.getCurrentUserOwner();
            dto.setCurrentId(sysUser.getId());
        }

        Page<RefundInfoDto> result = new Page<>();
        BeanUtils.copyProperties(pageable, result);
        List<RefundApply> records = baseMapper.selectPage(dto);
        if (ParamUtil.isNullOrEmptyOrZero(records)){
            return result;
        }
        List<RefundInfoDto> refundInfoDtos = new ArrayList<>(records.size());
        for (RefundApply refundApply:records) {
            RefundInfoDto infoDto = getInfo(refundApply);
            refundInfoDtos.add(infoDto);
        }
        result.setRecords(refundInfoDtos);
        result.setTotal(baseMapper.selectTotal(dto));
        return result;
    }

    @Override
    public RefundInfoDto detail(String id) {
        return getInfo(selectById(id));
    }

    private RefundInfoDto getInfo(RefundApply refundApply) {
        log.info("ferund detail id=" + refundApply.getRefundNo());
        RefundInfoDto info = new RefundInfoDto();
        BeanUtils.copyProperties(refundApply, info);
        info.setStatusStr(RefundStatus.get(info.getStatus()).getMsg());
        if (refundApply.getAuditorId() != null) {
            SysUser auditor = sysUserService.selectById(refundApply.getAuditorId());
            info.setAuditor(StringUtils.isBlank(auditor.getNickName()) ? auditor.getUsername() : auditor.getNickName());
        }
        if (refundApply.getRefunderId() != null) {
            SysUser refunder = sysUserService.selectById(refundApply.getRefunderId());
            info.setRefunder(
                    StringUtils.isBlank(refunder.getNickName()) ? refunder.getUsername() : refunder.getNickName());
        }
        OrderBase orderBase = orderBaseService.getOrderBaseByOrderNo(info.getOrderNo());
        if (!ParamUtil.isNullOrEmptyOrZero(orderBase)) {
            info.setPayTime(orderBase.getPayTime());
            info.setSno(orderBase.getSno());
        }
        String nickname = refundApply.getNickname();
        info.setUserName(nickname);
        if (ParamUtil.isNullOrEmptyOrZero(nickname)) {
            User user = userService.selectById(refundApply.getUserId());
            if (!ParamUtil.isNullOrEmptyOrZero(user)) {
                info.setUserName(user.getNickname());
            }
        }
        return info;
    }

    @Override
    public RefundInfoDto checkBeforeApply(String orderNo) {
        RefundApply refundApply = getByOrderNo(orderNo);
        if (refundApply == null) {
            return null;
        }
        return getInfo(refundApply);
    }

    @Override
    public RefundStatisticsDto checkedStatistics(List<String> ids) {
        return baseMapper.checkedStatistics(ids);
    }

    @Override
    public List<String> refund(List<String> ids) {
        List<String> failIds = new LinkedList<>();
        List<RefundApply> forUpdate = new LinkedList<>();
        SysUser currentUser = sysUserService.getCurrentUser();
        Date now = new Date();
        for (String refundNo : ids) {
            RefundApply refundApply = selectById(refundNo);
            Boolean success;
            if (StringUtils.isBlank(refundApply.getUserAlipayAccount()) ||
                    StringUtils.isBlank(refundApply.getUserAlipayRealName())) {
                // 没有填写完整支付宝信息，原路返回
                success = refund(refundApply.getOrderNo());
            } else {
                // 支付宝打款
                success = transfer(refundApply, refundApply.getOrderNo());
            }
            if (BooleanUtils.isTrue(success)) {
                // 退款成功
                RefundApply temp = new RefundApply();
                temp.setRefundNo(refundApply.getRefundNo());
                temp.setStatus(RefundStatus.REFUNDED.getCode());
                temp.setRefunderId(currentUser.getId());
                temp.setRefundTime(now);
                temp.setUtime(now);
                forUpdate.add(temp);
            } else {
                failIds.add(refundApply.getOrderNo());
            }
        }
        if (!forUpdate.isEmpty()) {
            updateBatchById(forUpdate);
        }
        return failIds;
    }

    private Boolean transfer(RefundApply refundApply, String orderNo) {
        log.info("=====>>> 执行打款操作，orderNo：{}，支付宝帐号：{}，支付宝真实姓名：{}", orderNo,
                refundApply.getUserAlipayAccount(), refundApply.getUserAlipayRealName());
        return redisService.lock(OrderBase.class, orderNo, () -> {
            OrderBase orderBase = orderBaseService.selectById(orderNo);
            // 避免重复退款
            if (orderBase.getOrderStatus().equals(OrderStatus.REFUNDED.getCode())) return true;
            // 尝试修改订单状态为退款中
            if (!updateRefunding(orderNo, orderBase)) return false;
            try {
                // 执行退款
                tradeAlipayService
                        .transfer(refundApply.getRefundNo(), "订单" + refundApply.getOrderNo() + "退款", refundApply.getAmount()
                                , refundApply.getUserAlipayAccount(), refundApply.getUserAlipayRealName(),
                                refundApply.getSysUserId());
            } catch (Exception e) {
                log.error("打款失败！！！", e);
                orderBase.setRefundResult(e.getMessage());
                orderBaseService.updateOrderStatusAndHandle(orderBase, OrderStatus.REFUND_FAIL.getCode());
                return false;
            }
            log.info("打款成功，orderNo：{}", orderBase.getOrderNo());
            orderBase.setRefundResult("退款成功");
            orderBaseService.updateOrderStatusAndHandle(orderBase, OrderStatus.REFUNDED.getCode());
            return true;
        });
    }

    @Override
    public Boolean refund(String orderNo) {
        log.info("=====>>> 执行退款操作，orderNo：{}", orderNo);
        return redisService.lock(OrderBase.class, orderNo, () -> {
            OrderBase orderBase = orderBaseService.selectById(orderNo);
            // 避免重复退款
            if (orderBase.getOrderStatus().equals(OrderStatus.REFUNDED.getCode())) return true;
            // 尝试修改订单状态为退款中
            if (!updateRefunding(orderNo, orderBase)) return false;
            try {
                // 执行退款
                TradeBase tradeBase =
                        tradeBaseService.selectOne(new EntityWrapper<TradeBase>().eq("order_no", orderBase.getOrderNo())
                                .eq("status", TradeStatus.SUCCESS.getCode()).orderBy("ctime", false).last("limit 1"));
                if (tradeBase == null) {
                    LeaseException.throwSystemException(LeaseExceEnums.TRADE_NO_LOSE);
                }
                switch (PayType.getPayType(orderBase.getPayType())) {
                    case WX_JSAPI:
                    case WX_APP:
                    case WEIXINPAY:
                    case WX_H5:
                        tradeWeixinService.refund(orderBase, tradeBase);
                        break;
                    case ALIPAY:
                        tradeAlipayService.refund(orderBase, tradeBase);
                        break;
                    case CARD:
                        userChargeCardService.refund(orderBase, tradeBase);
                        break;
                    case BALANCE:
                    case DISCOUNT:
                    case BALANCE_DISCOUNT:
                        userWalletService.refund(orderBase, tradeBase);
                        break;
                }
            } catch (Exception e) {
                log.error("退款失败！！！", e);
                orderBase.setRefundResult(e.getMessage());
                orderBaseService.updateOrderStatusAndHandle(orderBase, OrderStatus.REFUND_FAIL.getCode());
                return false;
            }
            log.info("退款成功，orderNo：{}", orderBase.getOrderNo());
            orderBase.setRefundResult("退款成功");
            orderBaseService.updateOrderStatusAndHandle(orderBase, OrderStatus.REFUNDED.getCode());
            return true;
        });
    }

    private boolean updateRefunding(String orderNo, OrderBase orderBase) {
        try {
            orderBaseService.updateOrderStatusAndHandle(orderBase, OrderStatus.REFUNDING.getCode());
            return true;
        }catch (Exception e) {
            log.error("订单"+orderNo+"无法进入退款中？", e);
            orderBase.setRefundResult(e.getMessage());
            orderBaseService.updateById(orderBase);
            return false;
        }
    }

    public String generateRefundNo() {
        return RandomStringUtils.randomNumeric(19);
    }

    public RefundApply getByOrderNo(String orderNo, RefundStatus... status) {
        Wrapper<RefundApply> wrapper = new EntityWrapper<RefundApply>();
        wrapper.eq("order_no", orderNo);
        if (status != null && status.length > 0) {
            Object[] statusCode = Arrays.stream(status).map(RefundStatus::getCode).toArray();
            wrapper.in("status", statusCode);
        }
        wrapper.orderBy("ctime").last("limit 1");
        return selectOne(wrapper);
    }

    @Override
    public String refundResult(List<String> ids) {
        StringBuilder sb = new StringBuilder();
        List<String> failIds = refund(ids);
        if (ParamUtil.isNullOrEmptyOrZero(failIds) || failIds.size() == 0) {
            return "退款成功！";
        }
        switch (failIds.size()) {
            case 1:
                sb.append("订单：" + failIds.get(0) + "退款失败，请在订单列表-退款失败列表中查看具体原因。");
                break;
            case 2:
                sb.append("订单：" + failIds.get(0) + "," + failIds.get(1) + "退款失败，请在订单列表-退款失败列表中查看具体原因。");
                break;
            default:
                sb.append("订单：" + failIds.get(0) + "," + failIds.get(1) + "等退款失败，请在订单列表-退款失败列表中查看具体原因。");
                break;
        }
        return sb.toString();
    }

}
