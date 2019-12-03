package com.gizwits.lease.message.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.sys.entity.SysRole;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.entity.SysUserExt;
import com.gizwits.boot.sys.entity.SysUserToRole;
import com.gizwits.boot.sys.service.SysRoleService;
import com.gizwits.boot.sys.service.SysUserExtService;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.sys.service.SysUserToRoleService;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.boot.utils.QueryResolverUtils;
import com.gizwits.lease.constant.BooleanEnum;
import com.gizwits.lease.constant.SysMessageEnum;
import com.gizwits.lease.enums.IsTrueEnum;
import com.gizwits.lease.enums.MessageType;
import com.gizwits.lease.enums.UserType;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.message.dao.SysMessageDao;
import com.gizwits.lease.message.entity.SysMessage;
import com.gizwits.lease.message.entity.SysMessageToUser;
import com.gizwits.lease.message.entity.dto.*;
import com.gizwits.lease.message.service.SysMessageService;
import com.gizwits.lease.message.service.SysMessageToUserService;
import com.gizwits.lease.model.DeleteDto;
import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.user.entity.UserWxExt;
import com.gizwits.lease.user.service.UserService;
import com.gizwits.lease.user.service.UserWxExtService;
import com.gizwits.lease.util.WxUtil;

import jdk.nashorn.internal.ir.EmptyNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统消息表 服务实现类
 * </p>
 *
 * @author yinhui
 * @since 2017-07-26
 */
@Service
public class SysMessageServiceImpl extends ServiceImpl<SysMessageDao, SysMessage> implements SysMessageService {

    protected final static Logger logger_wx = LoggerFactory.getLogger("WEIXIN_LOGGER");

    protected final static Logger logger = LoggerFactory.getLogger("MESSAGE_LOGGER");

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserToRoleService sysUserToRoleService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMessageToUserService sysMessageToUserService;

    @Autowired
    private SysUserExtService sysUserExtService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserWxExtService userWxExtService;


    @Override
    public Page<SysMessageListDto> getSysMessagePage(Pageable<SysMessageQueryDto> pageable) {
        SysUser sysUser = sysUserService.getCurrentUser();
        logger.info("查看用户：" + sysUser.getId());
        Integer typeId = pageable.getQuery().getType();
        if (typeId.equals(SysMessageEnum.ADDRESSER_ID.getCode())) {
            return getOutboxPage(pageable, sysUser);
        } else if (typeId.equals(SysMessageEnum.RECIPIENT_ID.getCode())) {
            return getInboxPage(pageable, sysUser);
        }
        return null;
    }

    /**
     * 根据openid查询这个用户相关的公众号曾经发送的消息列表
     */
    @Override
    public Page<SysMessageListDto> getMessagePage(Pageable pageable) {
        User user = userService.getCurrentUser();
        if (user == null) {
            return null;
        }
        Page<SysMessageListDto> result = new Page<>();

        Page<SysMessage> page = new Page<>();
        BeanUtils.copyProperties(pageable, page);
        EntityWrapper<SysMessage> messageEntityWrapper = new EntityWrapper<>();
        List<Integer> messageIds = sysMessageToUserService.listSysMessageId(user.getId(), UserType.USER.getCode());
        if (!ParamUtil.isNullOrEmptyOrZero(messageIds)) {
            messageEntityWrapper.in("id", messageIds).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("is_send", 1);
        } else {
            return result;
        }
        Page<SysMessage> page1 = selectPage(page, QueryResolverUtils.parse(pageable, messageEntityWrapper));
        List<SysMessage> list = page1.getRecords();
        List<SysMessageListDto> sysMessageListDtoList = new ArrayList<>(list.size());
        for (SysMessage message : list) {
            SysMessageListDto sysMessageListDto = getSysMessageListDto(message);
            sysMessageListDtoList.add(sysMessageListDto);
        }
        BeanUtils.copyProperties(page1, result);
        result.setRecords(sysMessageListDtoList);
        return result;
    }

    @Override
    public Integer countIsNotRead() {
        User user = userService.getCurrentUser();
        if (user == null) {
            return null;
        }
        Integer count = 0;
        EntityWrapper<SysMessage> messageEntityWrapper = new EntityWrapper<>();
        List<Integer> messageIds = sysMessageToUserService.listUnReadSysMessageIds(user.getId(), UserType.USER.getCode());
        if (!ParamUtil.isNullOrEmptyOrZero(messageIds)) {
            messageEntityWrapper.in("id", messageIds);
        } else {
            return 0;
        }

        messageEntityWrapper.eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        count = selectCount(messageEntityWrapper);
        return count;
    }

    @Override
    public SysMessage selectById(Integer id) {
        return selectOne(new EntityWrapper<SysMessage>().eq("id", id).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
    }

    @Override
    public DeleteDto delete(List<Integer> ids) {
        SysUser current = sysUserService.getCurrentUser();
        DeleteDto deleteDto = new DeleteDto();
        List<String> fails = new LinkedList<>();
        int count = 0;
        for (Integer id : ids) {
            SysMessage sysMessage = selectById(id);
            if (!ParamUtil.isNullOrEmptyOrZero(sysMessage)) {
                SysMessageToUser sysMessageToUser = sysMessageToUserService.selectByMessageId(id);
                boolean flag = !ParamUtil.isNullOrEmptyOrZero(sysMessageToUser) && sysMessageToUser.getUserId().equals(current.getId()) && sysMessageToUser.getUserType().equals(UserType.SYSTEM_USER.getCode());
                if (sysMessage.getAddresserId().equals(current.getId()) || flag) {
                    sysMessage.setIsDeleted(DeleteStatus.DELETED.getCode());
                    sysMessage.setUtime(new Date());
                    updateById(sysMessage);
                    SysMessageToUser sysMessageToUser1 = new SysMessageToUser();
                    sysMessageToUser1.setIsDeleted(DeleteStatus.DELETED.getCode());
                    EntityWrapper<SysMessageToUser> entityWrapper1 = new EntityWrapper<>();
                    entityWrapper1.eq("sys_message_id", id).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
                    sysMessageToUserService.update(sysMessageToUser1, entityWrapper1);
                    count++;
                } else {
                    fails.add(sysMessage.getTitle());
                }
            }
        }
        deleteDto.setSucceed(count);
        deleteDto.setFails(fails);
        return deleteDto;
    }

    @Override
    public void clear() {
        SysMessage sysMessage = new SysMessage();
        sysMessage.setIsDeleted(DeleteStatus.DELETED.getCode());
        User user = userService.getCurrentUser();
        if (user == null) {
            return;
        }
        EntityWrapper<SysUserExt> extEntityWrapper = new EntityWrapper<>();
        extEntityWrapper.eq("wx_id", user.getWxId());
        SysUserExt sysUserExt = sysUserExtService.selectOne(extEntityWrapper);
        if (sysUserExt == null) {
            return;
        }
        EntityWrapper<SysMessage> messageEntityWrapper = new EntityWrapper<>();
        messageEntityWrapper.eq("addresser_id", sysUserExt.getSysUserId()).eq("message_type", MessageType.WORK_ORDER_MESSAGE.getCode()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        messageEntityWrapper.eq("is_read", 0).eq("user_type", UserType.USER.getCode());
        update(sysMessage, messageEntityWrapper);
    }

    @Override
    public SysMessageForAddpage listRoles() {
        SysMessageForAddpage sysMessageForAddpage = new SysMessageForAddpage();
        SysUser sysUser = sysUserService.getCurrentUser();
        logger.info("用户{[]}创建角色", sysUser.getId());
        List<SysRole> sysRoles = sysRoleService.selectList(new EntityWrapper<SysRole>().eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("sys_user_id", sysUser.getId()));
        List<RoleDto> roleDtos = new ArrayList<>();
        for (SysRole role : sysRoles) {
            RoleDto roleDto = new RoleDto();
            roleDto.setRoleId(role.getId());
            roleDto.setRoleName(role.getRoleName());
            roleDtos.add(roleDto);
        }
        SysUserExt sysUserExt = sysUserExtService.selectById(sysUser.getId());
        if (!Objects.isNull(sysUserExt)) {
            sysMessageForAddpage.setIsBindWeChat(IsTrueEnum.TRUE.getCode());
        }
        sysMessageForAddpage.setRoleDtoList(roleDtos);
        return sysMessageForAddpage;
    }

    @Override
    public boolean addSysMessage(SysMessageAdd sysMessageAdd) {
        logger.info("添加消息");
        SysUser sysUser = sysUserService.getCurrentUser();
        sendMessage(sysMessageAdd, sysUser);
        return false;
    }

    @Override
    public void sendByTime(String date) {
        EntityWrapper<SysMessage> entityWrapper = new EntityWrapper<>();
        entityWrapper.like("ctime", date);
        List<SysMessage> sysMessages = selectList(entityWrapper);
        if (Objects.isNull(sysMessages)) {
            return;
        }
        for (SysMessage sysMessage : sysMessages) {
            if (Objects.equals(sysMessage.getIsSend(), IsTrueEnum.FALSE.getCode())) {
                sysMessage.setIsSend(IsTrueEnum.TRUE.getCode());
                updateById(sysMessage);
                List<SysMessageToUser> list = sysMessageToUserService.selectList(new EntityWrapper<SysMessageToUser>().eq("sys_message_id", sysMessage.getId()));
                for (SysMessageToUser sysMessageToUser : list) {
                    sysMessageToUser.setIsSend(IsTrueEnum.TRUE.getCode());
                    sysMessageToUserService.updateById(sysMessageToUser);
                }
                if (Objects.equals(IsTrueEnum.TRUE.getCode(), sysMessage.getIsBindWx())) {
                    SysUser sysUser = new SysUser();
                    sysUser.setId(sysMessage.getAddresserId());
                    sysUser.setUsername(sysMessage.getAddresserName());
                    SysMessageAdd sysMessageAdd = new SysMessageAdd();
                    sysMessageAdd.setTitle(sysMessage.getTitle());
                    sysMessageAdd.setContent(sysMessage.getContent());
                    SendMessageToWeChat(sysMessageAdd, sysUser);
                }
            }
        }
    }

    @Override
    public SysMessageListDto detail(Integer id) {
        logger.info("消息详情：" + id);
        SysMessage sysMessage = selectOne(new EntityWrapper<SysMessage>().eq("id", id)
                .eq("is_send", 1).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
        if (ParamUtil.isNullOrEmptyOrZero(sysMessage)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        sysMessage.setIsRead(IsTrueEnum.TRUE.getCode());
        updateById(sysMessage);
        SysMessageListDto sysMessageListDto = getSysMessageListDto(sysMessage);
        EntityWrapper<SysMessageToUser> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("sys_message_id", sysMessage.getId()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("is_send", 1);
        SysMessageToUser sysMessageToUser = new SysMessageToUser();
        sysMessageToUser.setIsRead(1);
        sysMessageToUserService.update(sysMessageToUser, entityWrapper);
        return sysMessageListDto;
    }

    /**
     * 发送消息
     */
    public void sendMessage(SysMessageAdd sysMessageAdd, SysUser sysUser) {

        logger.info("存储发送消息");
        //将消息插入数据库
        insert(sysMessageAdd, sysUser);


        if (Objects.equals(IsTrueEnum.TRUE.getCode(), sysMessageAdd.getIsSendNow())
                && Objects.equals(sysMessageAdd.getIsBindWeChat(), IsTrueEnum.TRUE.getCode())) {
            logger.info("发送消息到微信公众号");
            SendMessageToWeChat(sysMessageAdd, sysUser);

        }
    }

    /**
     * 推送微信消息
     */
    public void SendMessageToWeChat(SysMessageAdd sysMessageAdd, SysUser sysUser) {
        SysUserExt sysUserExt = sysUserExtService.selectById(sysUser.getId());
        if (ParamUtil.isNullOrEmptyOrZero(sysUserExt)) {
            LeaseException.throwSystemException(LeaseExceEnums.WEIXIN_SYS_USER_EXT_NOT_EXIST);
        }
        String wxId = sysUserExt.getWxId();
        List<UserWxExt> users = userWxExtService.selectList(new EntityWrapper<UserWxExt>().eq("is_deleted",DeleteStatus.NOT_DELETED.getCode()).eq("wx_id", wxId).eq("status", 1));
        if (ParamUtil.isNullOrEmptyOrZero(users)) {
            LeaseException.throwSystemException(LeaseExceEnums.WEIXXIN_USER_DONT_EXISTS);
        }
        List<String> openIds = users.stream().map(UserWxExt::getOpenid).collect(Collectors.toList());
        JSONObject sendData = new JSONObject();
        sendData.put("touser",openIds);
        sendData.put("msgtype", "text");
        JSONObject textData = new JSONObject();
        String value = "标题：" + sysMessageAdd.getTitle() + "，内容：" + sysMessageAdd.getContent();
        textData.put("content", value);
        sendData.put("text", textData);
        List<String> fails = new LinkedList<>();
        for (String openId:openIds) {
            //发送客服消息
            String result = WxUtil.customSendTextNews(openId,value,sysUserExt);
            JSONObject jsonObject = JSONObject.parseObject(result);
            Integer errcode = jsonObject.getInteger("errcode");
            if (errcode == null || !errcode.equals(0)) {
                fails.add(openId);
            }
        }

        //如果客服消息发送失败，发送文本消息到微信
        int size = fails.size();
        if (size==1){
            //群发接口的openId最少2个，可以相同
            fails.add(fails.get(0));
        }
        if (size>1) {
            WxUtil.sendManyNotices(sendData.toJSONString(), sysUserExt);
            logger_wx.info("推送消息时间：" + new Date() + "，微信id：" + wxId);
        }
    }

    /**
     * 插入系统消息表和消息用户表
     */
    public void insert(SysMessageAdd sysMessageAdd, SysUser sysUser) {
        SysMessage sysMessage = new SysMessage();
        sysMessage.setContent(sysMessageAdd.getContent());
        Date date = null;
        Integer isTrue = 0;
        Integer isBindWx = 0;
        if (Objects.equals(IsTrueEnum.FALSE.getCode(), sysMessageAdd.getIsSendNow())) {
            date = sysMessageAdd.getSendtime();
        } else if (Objects.equals(IsTrueEnum.TRUE.getCode(), sysMessageAdd.getIsSendNow())) {
            date = new Date();
            isTrue = 1;
        }

        if (Objects.equals(IsTrueEnum.TRUE.getCode(), sysMessageAdd.getIsBindWeChat())) {
            isBindWx = 1;
        }
        sysMessage.setCtime(date);
        sysMessage.setTitle(sysMessageAdd.getTitle());
        sysMessage.setAddresserId(sysUser.getId());
        sysMessage.setAddresserName(sysUser.getUsername());
        sysMessage.setIsSend(isTrue);
        sysMessage.setIsBindWx(isBindWx);
        logger.info("发送消息时间：" + date + ",发件人：" + sysUser.getId());
        insert(sysMessage);

        if (Objects.equals(sysMessageAdd.getIsSendToSysUser(), IsTrueEnum.TRUE.getCode())) {
            InsertSysMessageToUser(sysMessageAdd, sysUser, date, isTrue, sysMessage.getId());
        }
    }

    /**
     * 插入消息用户表
     */
    public void InsertSysMessageToUser(SysMessageAdd sysMessageAdd, SysUser sysUser, Date date, Integer isTrue, Integer sysMessageId) {
        List<RoleDto> roles = sysMessageAdd.getRoleDtoList();
        if (ParamUtil.isNullOrEmptyOrZero(roles)) {
            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
        }
        List<Integer> roleIds = new ArrayList<>(roles.size());
        for (RoleDto roleDto : roles) {
            roleIds.add(roleDto.getRoleId());
        }
        EntityWrapper<SysUserToRole> entityWrapper = new EntityWrapper<>();
        entityWrapper.in("role_id", roleIds).eq("is_deleted",DeleteStatus.NOT_DELETED.getCode()).groupBy("user_id");
        List<SysUserToRole> list = sysUserToRoleService.selectList(entityWrapper);

        for (SysUserToRole sysUserToRole : list) {
            SysMessageToUser sysMessageToUser = new SysMessageToUser();
            sysMessageToUser.setSysMessageId(sysMessageId);
            sysMessageToUser.setCtime(date);
            sysMessageToUser.setRoleId(sysUserToRole.getRoleId());
            String roleName = sysRoleService.selectById(sysUserToRole.getRoleId()).getRoleName();
            sysMessageToUser.setRoleName(roleName);
            sysMessageToUser.setUserId(sysUserToRole.getUserId());
            SysUser sysUser1 = sysUserService.selectById(sysUserToRole.getUserId());
            if (ParamUtil.isNullOrEmptyOrZero(sysUser1)) {
                LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
            }
            String username = sysUser1.getUsername();
            sysMessageToUser.setUsername(username);
            sysMessageToUser.setIsSend(isTrue);
            sysMessageToUser.setIsRead(0);
            sysMessageToUser.setUserType(UserType.SYSTEM_USER.getCode());
            sysMessageToUserService.insert(sysMessageToUser);
        }
    }

    /**
     * 收件箱
     */
    public Page<SysMessageListDto> getInboxPage(Pageable<SysMessageQueryDto> pageable, SysUser sysUser) {
        logger.info("收件箱");
        List<Integer> sysMessageIds = sysMessageToUserService.listSysMessageId(sysUser.getId(), UserType.SYSTEM_USER.getCode());
        if (ParamUtil.isNullOrEmptyOrZero(sysMessageIds)) {
            return new Page<SysMessageListDto>();
        }
        Page<SysMessage> page = new Page<>();
        BeanUtils.copyProperties(pageable, page);
        EntityWrapper<SysMessage> entityWrapper = new EntityWrapper<>();
        entityWrapper.in("id", sysMessageIds)
                .eq("is_send", 1).orderBy("ctime", false)
                .eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        Page<SysMessageListDto> result = getSysMessageListDtoPage(pageable, page, entityWrapper);
        return result;

    }

    /**
     * 发件箱
     */
    public Page<SysMessageListDto> getOutboxPage(Pageable<SysMessageQueryDto> pageable, SysUser sysUser) {
        logger.info("发件箱");
        sysUser = sysUserService.getSysUserOwner(sysUser);
        Page<SysMessage> page = new Page<>();
        BeanUtils.copyProperties(pageable, page);
        EntityWrapper<SysMessage> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("addresser_id", sysUser.getId())
                .eq("is_send", 1).orderBy("ctime", false)
                .eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        Page<SysMessageListDto> result = getSysMessageListDtoPage(pageable, page, entityWrapper);
        return result;
    }

    public Page<SysMessageListDto> getSysMessageListDtoPage(Pageable<SysMessageQueryDto> pageable, Page<SysMessage> page, EntityWrapper<SysMessage> entityWrapper) {
        Page<SysMessage> page1 = selectPage(page, QueryResolverUtils.parse(pageable.getQuery(), entityWrapper));
        List<SysMessageListDto> sysMessageListDtoList = new ArrayList<>();
        for (SysMessage message : page1.getRecords()) {
            SysMessageListDto sysMessageListDto = getSysMessageListDto(message);
            sysMessageListDtoList.add(sysMessageListDto);
        }
        Page<SysMessageListDto> result = new Page<>();
        BeanUtils.copyProperties(page1, result);
        result.setRecords(sysMessageListDtoList);
        return result;
    }

    public SysMessageListDto getSysMessageListDto(SysMessage message) {
        SysMessageListDto sysMessageListDto = new SysMessageListDto();
        sysMessageListDto.setId(message.getId());
        sysMessageListDto.setTitle(message.getTitle());
        sysMessageListDto.setContent(message.getContent());
        sysMessageListDto.setSentPerson(message.getAddresserName());
        String reciepient = sysMessageToUserService.concatRoleName(message.getId());
        sysMessageListDto.setRecepit(reciepient);
        sysMessageListDto.setTime(message.getCtime());
        sysMessageListDto.setIsRead(message.getIsRead());
        sysMessageListDto.setMessageType(message.getMessageType());
        sysMessageListDto.setMessageTypeDesc(MessageType.getName(message.getMessageType()));
        return sysMessageListDto;
    }

    @Override
    public Integer getUnreadCount(SysMessageCountDto dto) {
        SysUser currentUser = sysUserService.getCurrentUser();
        List<Integer> sysMessageIds = sysMessageToUserService.listSysMessageId(currentUser.getId(), UserType.SYSTEM_USER.getCode());
        EntityWrapper<SysMessage> entityWrapper = new EntityWrapper<>();
        entityWrapper.in("id", sysMessageIds)
                .eq("is_send", 1).eq("is_read", 0);
        return selectCount(entityWrapper);
    }

    @Override
    public Page<SysMessageListDto> getManageMessagePage(Pageable pageable) {
        SysUser user = sysUserService.getCurrentUser();

        Page<SysMessageToUser> messagePage = new Page<>();
        BeanUtils.copyProperties(pageable, messagePage);
        Wrapper<SysMessageToUser> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", user.getId()).eq("is_send", BooleanEnum.TRUE.getCode())
                .eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("user_type", UserType.SYSTEM_USER.getCode());
        messagePage = sysMessageToUserService.selectPage(messagePage, wrapper);

        Page<SysMessageListDto> result = new Page<>();
        BeanUtils.copyProperties(pageable, result);
        List<SysMessageListDto> list = new ArrayList<>();
        for (SysMessageToUser message : messagePage.getRecords()) {
            SysMessage sysMessage = selectById(message.getSysMessageId());
            if (sysMessage == null) {
                LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
            }
            SysMessageListDto dto = getSysMessageListDto(message, sysMessage);
            list.add(dto);
        }
        result.setRecords(list);
        return result;
    }

    @Override
    public SysMessageListDto getMessageDetail(Integer id) {
        SysMessageToUser messageForUpdate = new SysMessageToUser();
        messageForUpdate.setId(id);
        messageForUpdate.setIsRead(BooleanEnum.TRUE.getCode());
        boolean result = sysMessageToUserService.updateById(messageForUpdate);
        if (!result) {
            LeaseException.throwSystemException(LeaseExceEnums.OPERATION_FAIL);
        }

        SysMessageToUser message = sysMessageToUserService.selectById(id);
        if (message == null) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        SysMessage sysMessage = selectById(message.getSysMessageId());
        if (sysMessage == null) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        sysMessage.setUtime(new Date());
        sysMessage.setIsRead(1);
        updateById(sysMessage);
        return getSysMessageListDto(message, sysMessage);
    }

    private SysMessageListDto getSysMessageListDto(SysMessageToUser message, SysMessage sysMessage) {
        SysMessageListDto dto = new SysMessageListDto();
        dto.setId(message.getId());
        dto.setTitle(sysMessage.getTitle());
        dto.setContent(sysMessage.getContent());
        dto.setSentPerson(sysMessage.getAddresserName());
        dto.setRecepit(message.getUsername());
        dto.setTime(message.getCtime());
        dto.setIsRead(message.getIsRead());
        return dto;
    }

    @Override
    public Integer countManageIsNotRead() {
        SysUser user = sysUserService.getCurrentUser();
        Wrapper<SysMessageToUser> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", user.getId()).eq("is_send", BooleanEnum.TRUE.getCode())
                .eq("is_read", BooleanEnum.FALSE.getCode())
                .eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("user_type", UserType.SYSTEM_USER.getCode());
        return sysMessageToUserService.selectCount(wrapper);
    }

    @Override
    public void clearManage() {
        SysUser sysUser = sysUserService.getCurrentUser();

        SysMessageToUser messageForUpdate = new SysMessageToUser();
        messageForUpdate.setIsDeleted(BooleanEnum.TRUE.getCode());
        Wrapper<SysMessageToUser> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", sysUser.getId()).eq("is_send", BooleanEnum.TRUE.getCode())
                .eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("user_type", UserType.SYSTEM_USER.getCode());
        sysMessageToUserService.update(messageForUpdate, wrapper);
    }

    @Transactional
    public void sendMessage(String title, String content, List<Integer> recipientSysUserIds) {
        SysUser currentUser = sysUserService.getCurrentUser();
        sendMessage(currentUser.getId(), title, content, recipientSysUserIds);
    }

    @Override
    @Transactional
    public void sendMessage(Integer senderSysUserId, String title, String content, List<Integer> recipientSysUserIds) {
        SysMessage sysMessage = new SysMessage();
        sysMessage.setCtime(new Date());
        SysUser sender = sysUserService.selectById(senderSysUserId);
        if (sender == null) {
            LeaseException.throwSystemException(LeaseExceEnums.SEND_MESSAGE_FAIL);
        }
        sysMessage.setAddresserId(sender.getId());
        sysMessage.setAddresserName(sender.getUsername());
        sysMessage.setTitle(title);
        sysMessage.setContent(content);

        boolean result = insert(sysMessage);
        if (!result) {
            LeaseException.throwSystemException(LeaseExceEnums.SEND_MESSAGE_FAIL);
        }

        for (Integer sysUserId : recipientSysUserIds) {
            SysUser sysUser = sysUserService.selectById(sysUserId);
            if (sysUser == null) {
                LeaseException.throwSystemException(LeaseExceEnums.SEND_MESSAGE_FAIL);
            }
            SysMessageToUser sysMessageToUser = new SysMessageToUser();
            sysMessageToUser.setCtime(new Date());
            sysMessageToUser.setSysMessageId(sysMessage.getId());
            sysMessageToUser.setUserId(sysUser.getId());
            sysMessageToUser.setUsername(sysUser.getUsername());
            sysMessageToUser.setUserType(UserType.SYSTEM_USER.getCode());
            List<SysUserToRole> sysUserToRoleList = sysUserToRoleService.selectList(new EntityWrapper<SysUserToRole>().eq("user_id", sysMessageToUser.getUserId()).orderBy("ctime"));
            if (sysUserToRoleList.size() > 0) {
                SysRole sysRole = sysRoleService.selectById(sysUserToRoleList.get(0).getRoleId());
                sysMessageToUser.setRoleId(sysRole.getId());
                sysMessageToUser.setRoleName(sysRole.getRoleName());
            }
            result = sysMessageToUserService.insert(sysMessageToUser);
            if (!result) {
                LeaseException.throwSystemException(LeaseExceEnums.SEND_MESSAGE_FAIL);
            }
        }
    }
}
