package com.gizwits.lease.message.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.entity.SysUserExt;
import com.gizwits.boot.sys.service.SysUserExtService;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.boot.utils.QueryResolverUtils;
import com.gizwits.boot.utils.SysConfigUtils;
import com.gizwits.lease.config.CommonSystemConfig;
import com.gizwits.lease.constant.BooleanEnum;
import com.gizwits.lease.constant.OrderStatus;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.enums.MessageType;
import com.gizwits.lease.enums.UserType;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.message.entity.MessageTemplate;
import com.gizwits.lease.message.dao.MessageTemplateDao;
import com.gizwits.lease.message.entity.SysMessage;
import com.gizwits.lease.message.entity.SysMessageToUser;
import com.gizwits.lease.message.entity.dto.MessageTemplateAddDto;
import com.gizwits.lease.message.entity.dto.MessageTemplateDetailDto;
import com.gizwits.lease.message.service.MessageTemplateService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.lease.message.service.SysMessageService;
import com.gizwits.lease.message.service.SysMessageToUserService;
import com.gizwits.lease.order.entity.OrderBase;
import com.gizwits.lease.order.service.OrderBaseService;
import com.gizwits.lease.order.service.OrderExtByQuantityService;
import com.gizwits.lease.redis.RedisService;
import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.user.entity.UserWxExt;
import com.gizwits.lease.user.service.UserService;
import com.gizwits.lease.user.service.UserWxExtService;
import com.gizwits.lease.util.JiGuangUtil;
import com.gizwits.lease.util.WxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 消息模版表 服务实现类
 * </p>
 *
 * @author yinhui
 * @since 2018-01-03
 */
@Service
public class MessageTemplateServiceImpl extends ServiceImpl<MessageTemplateDao, MessageTemplate> implements MessageTemplateService {
    protected static Logger logger = LoggerFactory.getLogger("MESSAGE_LOGGER");

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private MessageTemplateDao messageTemplateDao;

    @Autowired
    private UserWxExtService userWxExtService;

    @Autowired
    private OrderBaseService orderBaseService;

    @Autowired
    private SysUserExtService sysUserExtService;

    @Autowired
    private SysMessageService sysMessageService;

    @Autowired
    private UserService userService;
    @Autowired
    private SysMessageToUserService sysMessageToUserService;

    @Autowired
    private OrderExtByQuantityService orderExtByQuantityService;

    @Autowired
    private RedisService redisService;

    @Override
    public boolean add(MessageTemplateAddDto dto) {
        SysUser sysUser = sysUserService.getCurrentUser();
        MessageTemplate template = new MessageTemplate(dto);

        template.setCtime(new Date());
        template.setUtime(new Date());
        template.setSysUserId(sysUser.getId());
        template.setSysUserName(sysUser.getUsername());
        template.setIsDeleted(0);
        return insert(template);
    }

    @Override
    public Page<MessageTemplateDetailDto> list(Pageable pageable) {
        SysUser sysUser = sysUserService.getCurrentUserOwner();
//        List<Integer> ids = sysUserService.resolveAccessableUserIds(sysUser);
        Page<MessageTemplate> page = new Page<>();
        BeanUtils.copyProperties(pageable, page);
        Page<MessageTemplate> page1 = selectPage(page, QueryResolverUtils.parse(pageable, new EntityWrapper<MessageTemplate>().eq("sys_user_id", sysUser.getId()).eq("is_deleted", 0)));
        List<MessageTemplate> list = page1.getRecords();
        List<MessageTemplateDetailDto> detailDtos = new ArrayList<>(list.size());
        if (!ParamUtil.isNullOrEmptyOrZero(list)) {
            list.forEach(item -> {
                MessageTemplateDetailDto detailDto = new MessageTemplateDetailDto(item);
                detailDto.setTypedesc(MessageType.getName(item.getType()));
                detailDtos.add(detailDto);
            });
        }
        Page<MessageTemplateDetailDto> result = new Page<>();
        BeanUtils.copyProperties(page1, result);
        result.setRecords(detailDtos);
        return result;
    }

    public MessageTemplate getByCurrentUser(Integer id, BigDecimal rate, Integer type) {
        EntityWrapper<MessageTemplate> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("sys_user_id", id).eq("rate", rate).eq("type", type).eq("is_deleted", 0);
        return selectOne(entityWrapper);
    }


    @Override
    public boolean update(MessageTemplateAddDto dto) {
        MessageTemplate template = selectOne(new EntityWrapper<MessageTemplate>().eq("id", dto.getTemplateId()).eq("is_deleted", 0));
        if (ParamUtil.isNullOrEmptyOrZero(template)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        BeanUtils.copyProperties(dto, template);
        template.setUtime(new Date());
        //判断是否已经修改了租赁消息提醒
        if (dto.getType().equals(MessageType.LEASE_MESSAGE.getCode()) && dto.getDependOnDataPoint().equals(BooleanEnum.FALSE.getCode()) && !ParamUtil.isNullOrEmptyOrZero(dto.getServiceId())) {
            //1。删除旧的推送
            redisService.deleteAllOrderLeaseRemind();
            //2.重新计算推送时间
            List<OrderBase> orderBases = orderBaseService.selectList(new EntityWrapper<OrderBase>().eq("order_status", OrderStatus.USING.getCode()));
            if (!ParamUtil.isNullOrEmptyOrZero(orderBases)){
                for (OrderBase orderBase:orderBases){
                    orderBaseService.resolveLeaseMessage(orderBase);
                }
            }
        }
        return updateById(template);
    }

    @Override
    public String delete(List<Integer> ids) {
        MessageTemplate template = new MessageTemplate();
        template.setUtime(new Date());
        template.setIsDeleted(1);
        EntityWrapper<MessageTemplate> entityWrapper = new EntityWrapper<>();
        entityWrapper.in("id", ids);
        update(template, entityWrapper);
        return "消息提醒删除后，一旦符合消息触发条件，也不会推送对应的自定义消息";
    }

    @Override
    public MessageTemplateDetailDto detail(Integer id) {
        MessageTemplate template = selectOne(new EntityWrapper<MessageTemplate>().eq("id", id).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
        if (ParamUtil.isNullOrEmptyOrZero(template)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        MessageTemplateDetailDto detailDto = new MessageTemplateDetailDto(template);
        detailDto.setTypedesc(MessageType.getName(template.getType()));
        return detailDto;
    }

    @Override
    public List<String> getTriggerByProduct(Integer productId) {
        return messageTemplateDao.getTriggerByOnlyProductId(productId);
    }

    @Override
    public MessageTemplate getByProductIdAndServiceId(Integer productId, Integer serviceId) {
        return selectOne(new EntityWrapper<MessageTemplate>().eq("product_id", productId).eq("service_id", serviceId)
                .eq("depend_on_data_point", 0).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
    }


    /**
     * 发送系统消息
     *
     * @param device
     * @param messageTemplate
     */
    @Override
    public SysMessage sendSysMessage(Device device, MessageTemplate messageTemplate) {
        SysMessage sysMessage = new SysMessage();
        OrderBase orderBase = orderBaseService.getDeviceLastUsingOrder(device.getSno());
        SendLeaseRemind(messageTemplate,sysMessage, orderBase);
        return sysMessage;
    }

    private void SendLeaseRemind(MessageTemplate messageTemplate, SysMessage sysMessage, OrderBase orderBase) {
        if (!ParamUtil.isNullOrEmptyOrZero(orderBase)) {
            Date now = new Date();
            sysMessage.setCtime(now);
            sysMessage.setUtime(now);
            SysUser sysUser = sysUserService.selectById(orderBase.getSysUserId());
            sysMessage.setAddresserName(sysUser.getUsername());
            sysMessage.setAddresserId(orderBase.getSysUserId());
            sysMessage.setTitle(messageTemplate.getTitle());
            sysMessage.setContent(messageTemplate.getContent());
            sysMessage.setMac(orderBase.getMac());
            sysMessage.setIsSend(1);
            sysMessage.setIsFixed(0);
            sysMessage.setIsRead(0);
            sysMessage.setCommand(messageTemplate.getCommand());
            sysMessage.setMessageType(messageTemplate.getType());
            if (sysMessageService.insert(sysMessage)) {
                SysMessageToUser sysMessageToUser = new SysMessageToUser();
                sysMessageToUser.setUtime(now);
                sysMessageToUser.setCtime(now);
                sysMessageToUser.setSysMessageId(sysMessage.getId());
                sysMessageToUser.setUserId(orderBase.getUserId());
                sysMessageToUser.setUsername(orderBase.getUserName());
                sysMessageToUser.setIsSend(1);
                sysMessageToUser.setIsDeleted(0);
                sysMessageToUser.setIsRead(0);
                sysMessageToUser.setUserType(UserType.USER.getCode());
                sysMessageToUserService.insert(sysMessageToUser);
            }
            //推送到用户微信公众号或app
            User user = userService.getUserByIdOrOpenidOrMobile(orderBase.getUserId() + "");
            if (!ParamUtil.isNullOrEmptyOrZero(user)) {
//                sendMessageToWx(user,sysMessage.getContent());
                //推送到app
                if (!ParamUtil.isNullOrEmptyOrZero(user.getMobile())) {
                    Map<String, String> map = new HashMap<>();
                    map.put("message_id", sysMessage.getId() + "");
                    map.put("message_type", sysMessage.getMessageType() + "");
                    CommonSystemConfig commonSystemConfig = SysConfigUtils.get(CommonSystemConfig.class);
                    String appkey = commonSystemConfig.getJiGuangAppUserKey();
                    String appSecret = commonSystemConfig.getjiGuangAppUserSecret();
                    JiGuangUtil.testSendPush(appkey, appSecret, sysMessage.getContent(), user.getMobile(), map);
                }
            }
        }
    }

    /**
     * 发送消息到微信公众号
     *
     * @param user
     * @param sendMessage
     */
    private void sendMessageToWx(User user, String sendMessage) {
        UserWxExt userWxExt = userWxExtService.getByOpenid(user.getOpenid());
        List<String> openIds = new ArrayList<>(2);
        openIds.add(userWxExt.getOpenid());
        openIds.add(userWxExt.getOpenid());
        SysUserExt sysUserExt = sysUserExtService.selectById(user.getSysUserId());
        //微信公众号
        JSONObject firstData = new JSONObject();
        String first = "";
        firstData.put("value", first);
        firstData.put("color", "#173177");

        JSONObject keyword1 = new JSONObject();
        keyword1.put("value", "");
        keyword1.put("color", "#173177");

        JSONObject keyword2 = new JSONObject();
        keyword2.put("value", "");
        keyword2.put("color", "#173177");


        JSONObject keyword3 = new JSONObject();
        keyword3.put("value", "");
        keyword3.put("color", "#173177");

        JSONObject remarkData = new JSONObject();
        remarkData.put("value", "");
        remarkData.put("color", "#173177");

        JSONObject data = new JSONObject();
        data.put("first", firstData);
        data.put("keyword1", keyword1);
        data.put("keyword1", keyword2);
        data.put("keyword1", keyword3);
        data.put("remark", remarkData);
        try {
            WxUtil.sendTemplateNotice(data.toJSONString(), sysUserExt);
        } catch (Exception e) {
            logger.info("推送自定义模版消息失败，原因 e =" + e);
            e.printStackTrace();
        }
    }


    @Override
    public SysMessage sendSysMessage(OrderBase orderBase, MessageTemplate messageTemplate) {
        SysMessage sysMessage = new SysMessage();
        SendLeaseRemind(messageTemplate, sysMessage, orderBase);
        return sysMessage;
    }
}
