package com.gizwits.lease.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.response.AlipayUserUserinfoShareResponse;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.api.SmsApi;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.common.MessageCodeConfig;
import com.gizwits.boot.dto.JwtAuthenticationDto;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.enums.SysUserType;
import com.gizwits.boot.exceptions.SysExceptionEnum;
import com.gizwits.boot.exceptions.SystemException;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.entity.SysUserExt;
import com.gizwits.boot.sys.service.SysUserExtService;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.*;
import com.gizwits.lease.china.entity.china.dto.AreaDto;
import com.gizwits.lease.china.entity.china.dto.UnifiedAddressDto;
import com.gizwits.lease.china.service.ChinaAreaService;
import com.gizwits.lease.config.CommonSystemConfig;
import com.gizwits.lease.constant.SexType;
import com.gizwits.lease.constant.ThirdPartyUserType;
import com.gizwits.lease.constant.UserStatus;
import com.gizwits.lease.device.dao.UserBindDeviceDao;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.UserBindDevice;
import com.gizwits.lease.device.service.UserBindDeviceService;
import com.gizwits.lease.device.vo.UserBindDeviceListVo;
import com.gizwits.lease.enums.MoveType;
import com.gizwits.lease.enums.ThirdPartyLoginType;
import com.gizwits.lease.enums.UserInfoState;
import com.gizwits.lease.event.NameModifyEvent;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.manager.entity.Manufacturer;
import com.gizwits.lease.manager.service.ManufacturerService;
import com.gizwits.lease.redis.RedisService;
import com.gizwits.lease.stat.vo.StatLocationVo;
import com.gizwits.lease.stat.vo.StatUserWidgetVo;
import com.gizwits.lease.user.dao.UserDao;
import com.gizwits.lease.user.dto.*;
import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.user.entity.UserWxExt;
import com.gizwits.lease.user.service.UserChargeCardService;
import com.gizwits.lease.user.service.UserService;
import com.gizwits.lease.user.service.UserWxExtService;
import com.gizwits.lease.util.*;
import com.gizwits.lease.util.DateUtil;
import com.gizwits.lease.wallet.service.UserWalletService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表,不要前缀,因为用户模块计划抽象成通用功能 服务实现类
 * </p>
 *
 * @author rongmc
 * @since 2017-06-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    protected final static Logger logger = LoggerFactory.getLogger("USER_LOGGER");

    @Autowired
    private UserWxExtService userWxExtService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private UserBindDeviceService userBindDeviceService;

    @Autowired
    private UserBindDeviceDao userBindDeviceDao;



    private static Map<MoveType, UserStatus> map = new HashMap<>();

    private static Map<String, String> code = new HashMap<>();

    static {
        map.put(MoveType.MOVE_IN_BLACK, UserStatus.BLACK);
        map.put(MoveType.MOVE_OUT_BLACK, UserStatus.NORMAL);
    }

    /**
     * 根据openid获取用户信息
     *
     * @param userIdentify 如果是微信用户,此处的openid对应的是各个公众号的生成的openid
     */
    @Autowired
    private UserWalletService userWalletService;

    @Autowired
    private UserChargeCardService userChargeCardService;

    public User selectById(Integer id) {
        return selectOne(new EntityWrapper<User>().eq("id", id).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
    }

    @Override
    public User getUserByIdOrOpenidOrMobile(String userIdentify) {
        if (ParamUtil.isNullOrEmptyOrZero(userIdentify)) {
            return null;
        }
        //先判断ID
        User dbUser = selectById(userIdentify);
        if (dbUser != null) {
            logger.info("根据userIdentify=" + userIdentify + ",查询到用户" + dbUser.getUsername());
            return dbUser;
        }


        //判断是否是微信用户
        EntityWrapper<UserWxExt> extEntityWrapper = new EntityWrapper<>();
        extEntityWrapper.eq("openid", userIdentify).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        UserWxExt dbUserWxExt = userWxExtService.selectOne(extEntityWrapper);
        if (dbUserWxExt != null) {
            dbUser = userDao.findByOpenid(dbUserWxExt.getUserOpenid());
            if (dbUser == null) {
                dbUser = userDao.findByOpenid(dbUserWxExt.getOpenid());
            }
            dbUser.setMoveInBlackTime(dbUserWxExt.getMoveInBlackTime());
            dbUser.setMoveOutBlackTime(dbUserWxExt.getMoveOutBlackTime());
            dbUser.setStatus(dbUserWxExt.getStatus());
            dbUser.setOpenid(dbUserWxExt.getOpenid());
            dbUser.setAuthorizationTime(dbUserWxExt.getAuthorizationTime());
            dbUser.setWxId(dbUserWxExt.getWxId());
            logger.info("根据openId=" + userIdentify + ",查询到用户" + dbUser.getUsername());
            return dbUser;
        }

        //在判断是否是手机号或第三方ID
        dbUser = userDao.findByOpenid(userIdentify);
        if (dbUser != null) {
            EntityWrapper<UserWxExt> userWxExtEntityWrapper = new EntityWrapper<>();
            userWxExtEntityWrapper.eq("user_openid", dbUser.getOpenid()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).orderBy("ctime", false);
            dbUserWxExt = userWxExtService.selectOne(userWxExtEntityWrapper);
            if (Objects.nonNull(dbUserWxExt)) {
                dbUser.setMoveInBlackTime(dbUserWxExt.getMoveInBlackTime());
                dbUser.setMoveOutBlackTime(dbUserWxExt.getMoveOutBlackTime());
                dbUser.setStatus(dbUserWxExt.getStatus());
                dbUser.setOpenid(dbUserWxExt.getOpenid());
                dbUser.setAuthorizationTime(dbUserWxExt.getAuthorizationTime());
                dbUser.setWxId(dbUserWxExt.getWxId());
            }
            logger.info("根据user_openid=" + dbUser.getOpenid() + ",查询到用户" + dbUser.getUsername());

            return dbUser;
        } else {
            return null;
        }
    }

    @Override
    public User getUserByOpenidAndExistAndNotInBlack(String openid) {
        EntityWrapper<UserWxExt> extEntityWrapper = new EntityWrapper<>();
        extEntityWrapper.eq("openid", openid).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        UserWxExt dbUserWxExt = userWxExtService.selectOne(extEntityWrapper);
        if (dbUserWxExt == null) {//说明不是微信用户
            User user = userDao.findByOpenid(openid);
            if (Objects.isNull(user) || user.getStatus().equals(UserStatus.BLACK)) {
                LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
            }
            return user;
        } else {
            if (dbUserWxExt.getStatus().equals(UserStatus.BLACK)) {
                LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
            }
            User user = userDao.findByOpenid(dbUserWxExt.getUserOpenid());
            if (user == null) {
                LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
            }
            user.setMoveInBlackTime(dbUserWxExt.getMoveInBlackTime());
            user.setMoveOutBlackTime(dbUserWxExt.getMoveOutBlackTime());
            user.setStatus(dbUserWxExt.getStatus());
            user.setAuthorizationTime(dbUserWxExt.getAuthorizationTime());
            user.setOpenid(dbUserWxExt.getOpenid());
            return user;
        }
    }

    /**
     * 根据微信用户openid获取微信主体信息,返回对象的openid,为用户的unionid
     *
     * @param wxOpenid
     * @return
     */
    @Override
    public User getUserNoRecoverByWxOpenid(String wxOpenid) {
        EntityWrapper<UserWxExt> extEntityWrapper = new EntityWrapper<>();
        extEntityWrapper.eq("openid", wxOpenid).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        UserWxExt dbUserWxExt = userWxExtService.selectOne(extEntityWrapper);
        if (dbUserWxExt == null) {//说明不是微信用户
            User user = userDao.findByOpenid(wxOpenid);
            if (Objects.isNull(user) || user.getStatus().equals(UserStatus.BLACK)) {
                LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
            }
        } else {
            EntityWrapper<User> userEntityWrapper = new EntityWrapper<>();
            userEntityWrapper.eq("openid", dbUserWxExt.getUserOpenid()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
            User user = selectOne(userEntityWrapper);
            if (user == null) {
                user = selectOne(new EntityWrapper<User>().eq("openid", dbUserWxExt.getOpenid()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
            }
            return user;
        }
        return null;
    }

    /**
     * 获取支付宝信息
     *
     * @param
     * @return
     */
    public User addUserByAlipay(AlipayUserUserinfoShareResponse userinfoShareResponse, SysUserExt sysUserExt) {
        String userid = userinfoShareResponse.getUserId();
        User dbUser = userDao.findByOpenid(userid);
        if (dbUser != null) {
            dbUser.setAvatar(userinfoShareResponse.getAvatar());
            dbUser.setNickname(userinfoShareResponse.getNickName());
            dbUser.setGender(getAlipayUserGender(userinfoShareResponse.getGender()));
            dbUser.setUtime(new Date());

            UnifiedAddressDto unified = chinaAreaService.unified(new UnifiedAddressDto(userinfoShareResponse.getProvince(), userinfoShareResponse.getCity()));
            dbUser.setProvince(unified.getProvince());
            dbUser.setCity(unified.getCity());

            updateById(dbUser);
        } else {
            dbUser = new User();
            String username = userid.substring(userid.length() - 5, userid.length()).replaceAll("-", "").replaceAll("_", "") + IdGenerator.getRandomString(5);
            dbUser.setCtime(new Date());
            dbUser.setUtime(new Date());
            dbUser.setAlipayUnionid(userid);
            dbUser.setNickname(userinfoShareResponse.getNickName());
            dbUser.setUsername(username);

            dbUser.setGender(getAlipayUserGender(userinfoShareResponse.getGender()));
            dbUser.setAvatar(userinfoShareResponse.getAvatar());
            dbUser.setThirdParty(ThirdPartyUserType.ALIPAY.getCode());
            dbUser.setLastLoginTime(new Date());

            UnifiedAddressDto unified = chinaAreaService.unified(new UnifiedAddressDto(userinfoShareResponse.getProvince(), userinfoShareResponse.getCity()));
            dbUser.setProvince(unified.getProvince());
            dbUser.setCity(unified.getCity());

            dbUser.setSysUserId(sysUserExt.getSysUserId());
            insert(dbUser);
        }
        return dbUser;
    }

    private int getAlipayUserGender(String gender) {
        if (ParamUtil.isNullOrEmptyOrZero(gender)) {
            return SexType.OTHER.getCode();
        } else if (gender.toLowerCase().equals("m")) {
            return SexType.MALE.getCode();
        } else if (gender.toLowerCase().equals("f")) {
            return SexType.FEMALE.getCode();
        } else {
            return SexType.OTHER.getCode();
        }
    }

    @Override
    public User addUserByWx(String wxUserinfoJson, SysUserExt sysUserExt, Integer sysUserid) {
        if (StringUtils.isBlank(wxUserinfoJson))
            return null;
        JSONObject jsonObject = JSON.parseObject(wxUserinfoJson);
        String openid = jsonObject.getString("openid");
        String unionid = jsonObject.getString("unionid");
        if (ParamUtil.isNullOrEmptyOrZero(unionid)) {
            unionid = openid;
        }
        /**
         * 注:由于微信用户体系的缘故,相同的用户在每个公众号的openid是不同的,因此需要使用unionId作为微信用户的唯一标示
         * 在User_wx_ext表中存储对应公众号的用户openid
         */
        //查询User主表的ID
        User dbUser = userDao.findByOpenid(unionid);

        EntityWrapper<UserWxExt> extEntityWrapper = new EntityWrapper<>();
        extEntityWrapper.eq("openid", openid).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        UserWxExt dbUserWxExt = userWxExtService.selectOne(extEntityWrapper);

        //用户第一次使用平台
        if (dbUser == null) {
            String username = openid.substring(openid.length() - 5, openid.length()).replaceAll("-", "").replaceAll("_", "") + IdGenerator.getRandomString(5);
            dbUser = new User();
            dbUser.setCtime(new Date());
            dbUser.setUtime(new Date());
            dbUser.setOpenid(unionid);
            dbUser.setNickname(jsonObject.getString("nickname"));
            dbUser.setUsername(username);
            dbUser.setAuthorizationTime(new Date());
            dbUser.setGender(jsonObject.getInteger("sex"));
            dbUser.setAvatar(jsonObject.getString("headimgurl"));
            dbUser.setThirdParty(ThirdPartyUserType.WEIXIN.getCode());

            UnifiedAddressDto unified = chinaAreaService.unified(new UnifiedAddressDto(jsonObject.getString("province"), jsonObject.getString("city")));
            dbUser.setProvince(unified.getProvince());
            dbUser.setCity(unified.getCity());

            dbUser.setInfoState(UserInfoState.THIRD.getValue());

            dbUserWxExt = new UserWxExt();
            dbUserWxExt.setUserOpenid(unionid);
            dbUserWxExt.setOpenid(openid);
            dbUserWxExt.setWxId(sysUserExt.getWxId());
            dbUserWxExt.setSysUserId(sysUserExt.getSysUserId());
            dbUserWxExt.setCtime(new Date());
            dbUserWxExt.setAuthorizationTime(new Date());
        } else {
            if (!Objects.equals(dbUser.getInfoState(), UserInfoState.SELF.getValue())) {
                dbUser.setNickname(jsonObject.getString("nickname"));
                dbUser.setGender(jsonObject.getInteger("sex"));
                dbUser.setAvatar(jsonObject.getString("headimgurl"));

                UnifiedAddressDto unified = chinaAreaService.unified(new UnifiedAddressDto(jsonObject.getString("province"), jsonObject.getString("city")));
                dbUser.setProvince(unified.getProvince());
                dbUser.setCity(unified.getCity());
            }
            dbUser.setOpenid(unionid);
            dbUser.setUtime(new Date());
            dbUser.setThirdParty(ThirdPartyUserType.WEIXIN.getCode());
            if (dbUserWxExt == null) {
                dbUserWxExt = new UserWxExt();
                dbUserWxExt.setUserOpenid(unionid);
                dbUserWxExt.setOpenid(openid);
                dbUserWxExt.setWxId(sysUserExt.getWxId());
                dbUserWxExt.setSysUserId(sysUserExt.getSysUserId());
                dbUserWxExt.setCtime(new Date());
                dbUserWxExt.setAuthorizationTime(new Date());
            } else {
                dbUserWxExt.setUtime(new Date());
            }
        }

        if (sysUserid != null) {
            dbUser.setSysUserId(sysUserid);
        } else {
            if (dbUser.getSysUserId() == null) {
                dbUser.setSysUserId(sysUserExt.getSysUserId());
            }
        }
        dbUser.setLastLoginTime(new Date());

        if (insertOrUpdate(dbUser)) {
            userWxExtService.insertOrUpdate(dbUserWxExt);
            insertOrUpdate(dbUser);
            return dbUser;
        }
        return null;
    }

    @Override
    public Page<UserForListDto> page1(Pageable<UserForQueryDto> pageable) {
        List<Integer> accessableIds = sysUserService.resolveSysUserAllSubIds(sysUserService.getCurrentUserOwner());

        UserForQueryDto queryDto = pageable.getQuery();
        queryDto.setSysUserIds(accessableIds);
        if (pageable.getCurrent() < 1) {
            pageable.setCurrent(1);
        }
        queryDto.setBegin(pageable.getOffsetCurrent());
        queryDto.setSize(pageable.getSize());
        List<User> list = userDao.listPage(queryDto);
        if (ParamUtil.isNullOrEmptyOrZero(list)) {
            return new Page<>();
        }

        Page<UserForListDto> result = new Page<>();
        result.setRecords(new ArrayList<>(list.size()));
        list.forEach(item -> {
            UserForListDto dto = new UserForListDto(item);
            dto.setAccount(item.getMobile());
            // 暂时将创建时间当作授权时间
            dto.setAuthorizationTime(item.getCtime());
            dto.setGenderDesc(SexType.getName(item.getGender()));
            result.getRecords().add(dto);
        });
        result.setCurrent(pageable.getCurrent());
        result.setSize(pageable.getSize());
        result.setTotal(userDao.findTotalSize(queryDto));
        return result;
    }


    @Override
    public Page<UserForListDto> page(Pageable<UserForQueryDto> pageable) {
        List<Integer> accessableIds = sysUserService.resolveSysUserAllSubIds(sysUserService.getCurrentUserOwner());

        UserForQueryDto queryDto = pageable.getQuery();
        queryDto.setSysUserIds(accessableIds);
        if (pageable.getCurrent() < 1) {
            pageable.setCurrent(1);
        }
        queryDto.setBegin(pageable.getOffsetCurrent());
        queryDto.setSize(pageable.getSize());
        List<User> list = userDao.selectPageGMDS(queryDto);
        if (ParamUtil.isNullOrEmptyOrZero(list)) {
            return new Page<>();
        }

        Page<UserForListDto> result = new Page<>();
        result.setRecords(new ArrayList<>(list.size()));
        list.forEach(item -> {
            UserForListDto dto = new UserForListDto(item);
            dto.setAccount(item.getMobile());
            result.getRecords().add(dto);
        });
        result.setCurrent(pageable.getCurrent());
        result.setSize(pageable.getSize());
        result.setTotal(userDao.findTotalSizeGMDS(queryDto));
        return result;
    }

    @Override
    public Page<UserForListDto> pageForAffiliation(Pageable<QueryForUserListDTO> pageable) {

//        SysUser parentAdmin = sysUserService.getCurrentUserOwner();
        Page<UserForListDto> resultPage = new Page<>();
        Page<User> page = new Page<>();
        BeanUtils.copyProperties(pageable, page);
        Wrapper<User> wrapper = QueryResolverUtils.parse(pageable.getQuery(), new EntityWrapper<>());
        wrapper.eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());

        // 1、超级管理员可以查看所有的C端用户
//        if (parentAdmin.getIsAdmin().equals(SysUserType.SUPERADMIN.getCode())) {
            page = selectPage(page, wrapper);
            List<UserForListDto> list = page.getRecords().stream().map(item -> {
                UserForListDto dto = new UserForListDto(item);
                dto.setAccount(item.getMobile());
                return dto;
            }).collect(Collectors.toList());
            BeanUtils.copyProperties(page, resultPage);
            resultPage.setRecords(list);
            return resultPage;
//        }

//        // 2、厂商以及其管理员可以查看其名下的所有C端用户
//        if (parentAdmin.getIsAdmin().equals(SysUserType.MANUFACTURER.getCode())) {
//            List<Integer> integers = sysUserService.resolveSysUserAllSubIds(parentAdmin);
//
//            wrapper.in("sys_user_id",integers);
//            page = selectPage(page, wrapper);
//            List<UserForListDto> list = page.getRecords().stream().map(item -> {
//                UserForListDto dto = new UserForListDto(item);
//                dto.setAccount(item.getMobile());
//                return dto;
//            }).collect(Collectors.toList());
//            BeanUtils.copyProperties(page, resultPage);
//            resultPage.setRecords(list);
//            return resultPage;
//        }
//        /**
//         *
//         * 3、经销商以及经销商管理员可以查看其自身还有其下级经销商的所有设备绑定的C端用户
//         * 4、运营商以及运营商管理员可以查看其自身还有其下级经销商的所有设备绑定的C端用户
//         */
//        if (parentAdmin.getIsAdmin().equals(SysUserType.AGENT.getCode()) || parentAdmin.getIsAdmin().equals(SysUserType.OPERATOR.getCode())) {
//            List<Integer> subIds = sysUserService.resolveSysUserAllSubIds(parentAdmin);
////            QueryForUserListDTO query = pageable.getQuery();
////            query.setOwnerIds(subIds);
////            // TODO 需要优化的地方
////            query.setOrderByField(pageable.getOrderByField());
////            query.setAsc(pageable.isAsc() ? "Asc" : "DESC");
////            List<User> users = userDao.listAffiliation(query, pageable.getOffsetCurrent(), pageable.getSize());
//            wrapper.in("sys_user_id",subIds);
//            page = selectPage(page, wrapper);
//            List<UserForListDto> list = page.getRecords().stream().map(item -> {
//                UserForListDto dto = new UserForListDto(item);
//                dto.setAccount(item.getMobile());
//                return dto;
//            }).collect(Collectors.toList());
//            BeanUtils.copyProperties(pageable, resultPage);
//            resultPage.setRecords(list);
//            resultPage.setTotal(userDao.countAffiliation(pageable.getQuery()));
//            return resultPage;
//        }
//        return resultPage;
    }

    @Override
    public Page<UserForListDto> searchUser(Pageable<QueryForUserListDTO> pageable) {

        Page<UserForListDto> resultPage = new Page<>();
        //如果没有搜索条件，测不显示
        if (pageable.getQuery().getNickname()!=null && !pageable.getQuery().getNickname().equals("")) {
            Page<User> page = new Page<>();
            BeanUtils.copyProperties(pageable, page);
            Wrapper<User> wrapper = QueryResolverUtils.parse(pageable.getQuery(), new EntityWrapper<>());
            wrapper.eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());


            page = selectPage(page, wrapper);
            List<UserForListDto> list = page.getRecords().stream().map(item -> {
                UserForListDto dto = new UserForListDto(item);
                dto.setAccount(item.getMobile());
                return dto;
            }).collect(Collectors.toList());
            BeanUtils.copyProperties(page, resultPage);
            resultPage.setRecords(list);
            return resultPage;
        }
        return resultPage;
    }

    @Override
    public UserForDetailDto detail(String openid) {
        User dbUser= getUserByIdOrOpenidOrMobile(openid);
        if (dbUser == null) {
            LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
        }
        UserForDetailDto result = new UserForDetailDto(dbUser);
        result.setGenderDesc(SexType.getName(dbUser.getGender()));
        if (StringUtils.isBlank(dbUser.getProvince()) && StringUtils.isBlank(dbUser.getCity())) {
            result.setRegion("未知");
        } else {
            result.setRegion(dbUser.getProvince() + "/" + dbUser.getCity());
        }
        return result;
    }

    /**
     * 移入移出黑名单
     *
     * @param dto 传递的是openid
     */
    @Override
    public boolean move(UserForMoveDto dto, MoveType moveType) {
        UserStatus toUserStatus = map.get(moveType);
        if (Objects.isNull(toUserStatus)) {
            return false;
        }
        List<User> users = userDao.findByUnionids(dto.getUserIds(), sysUserService.resolveSysUserAllSubIds(sysUserService.getCurrentUserOwner()));
        if (CollectionUtils.isNotEmpty(users)) {
            List<User> needToMove = users.stream().filter(user -> !Objects.equals(user.getStatus(), toUserStatus.getCode())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(needToMove)) {
                needToMove.forEach(item -> {
                    item.setStatus(toUserStatus.getCode());
                    item.setUtime(new Date());
                    if (Objects.equals(MoveType.MOVE_IN_BLACK, moveType)) {
                        //移入
                        item.setMoveInBlackTime(new Date());
                        item.setMoveOutBlackTime(null);
                    } else {
                        //移出
                        item.setMoveOutBlackTime(new Date());
                        item.setMoveInBlackTime(null);
                    }
                });
                updateBatchById(needToMove);
            }
        } else {
            return false;
        }


        EntityWrapper<UserWxExt> entityWrapper = new EntityWrapper<>();
        List openids = users.stream().map(user -> user.getOpenid()).collect(Collectors.toList());
        entityWrapper.in("user_openid", openids).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        List<UserWxExt> wxExtList = userWxExtService.selectList(entityWrapper);

        if (CollectionUtils.isNotEmpty(wxExtList)) {
            List<UserWxExt> needToMove = wxExtList.stream().filter(user -> !Objects.equals(user.getStatus(), toUserStatus.getCode())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(needToMove)) {
                needToMove.forEach(item -> {
                    item.setStatus(toUserStatus.getCode());
                    item.setUtime(new Date());
                    if (Objects.equals(MoveType.MOVE_IN_BLACK, moveType)) {
                        //移入
                        item.setMoveInBlackTime(new Date());
                        item.setMoveOutBlackTime(null);
                    } else {
                        //移出
                        item.setMoveOutBlackTime(new Date());
                        item.setMoveInBlackTime(null);
                    }
                });
                // 操作记录
                userWxExtService.updateBatchById(needToMove);
            }
        }
        return true;
    }

    @Override
    public TokenDto login(UserLoginDto userLoginDto) {
        String mobile = userLoginDto.getMobile();
        logger.info("登录手机号：" + mobile);

        if(userLoginDto.getCode()!=null){
            String code=redisService.getMobileCode("loginOrRegister"+mobile);
            if(ParamUtil.isNullOrEmptyOrZero(code)){
                LeaseException.throwSystemException(LeaseExceEnums.MOBILE_CODE_ERROR_OR_EXPIRE);
            }
            if(!code.equals(userLoginDto.getCode())) {
                throw new SystemException(SysExceptionEnum.MESSAGE_ERROR.getCode(), SysExceptionEnum.MESSAGE_ERROR.getMessage());
            }
            }
        User user = selectOne(new EntityWrapper<User>().eq("username", mobile).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
        if (Objects.isNull(user)) {
            //没有用户就注册个账号
              user=register(mobile);
        }
        if(userLoginDto.getPassword()!=null&&userLoginDto.getCode()==null){

            if (user.getPassword()==null||!PasswordUtil.verify(userLoginDto.getPassword(), user.getPassword())) {
                LeaseException.throwSystemException(LeaseExceEnums.PHONE_OR_PASSWORD_ERROR);
            }
        }
        String accessToken = UUID.randomUUID().toString();
        redisService.cacheAppUser(accessToken, user);
        TokenDto tokenDto = new TokenDto();
        tokenDto.setJwtAuthenticationDto(new JwtAuthenticationDto(accessToken));
        return tokenDto;
    }

    private User register(String mobile){
         User user=new User();
         user.setMobile(mobile);
         user.setUsername(mobile);
         user.setCtime(new Date());
         user.setLastLoginTime(new Date());
         insert(user);
         return user;
    }
    @Override
    public TokenDto login2(UserLoginDto userLoginDto) {
        String mobile = userLoginDto.getMobile();
        logger.info("登录手机号：" + mobile);
        User user = getUserByMobile(mobile);
        if (Objects.isNull(user)) {
            LeaseException.throwSystemException(LeaseExceEnums.PHONE_NOT_REGISTER);
        }
        if (!PasswordUtil.verify(userLoginDto.getPassword(), user.getPassword())) {
            LeaseException.throwSystemException(LeaseExceEnums.PHONE_OR_PASSWORD_ERROR);
        }
        String accessToken = UUID.randomUUID().toString();
        redisService.cacheAppUser(accessToken, user);
        String res = GizwitsUtil.createUser(user.getUsername(), userLoginDto.getAppId());
        JSONObject json = JSONObject.parseObject(res);
        String userToken = String.valueOf(json.get("token"));
        String uid = String.valueOf(json.get("uid"));
        TokenDto tokenDto = new TokenDto();
        tokenDto.setJwtAuthenticationDto(new JwtAuthenticationDto(accessToken));
        tokenDto.setUserToken(userToken);
        tokenDto.setUid(uid);
        return tokenDto;
    }


    @Override
    public TokenDto thirdLogin(UserForThirdLoginDto dto) {
        Integer thirdType = dto.getThirdType();
        ThirdPartyLoginType thirdPartyLoginType = ThirdPartyLoginType.get(thirdType);
        if (thirdPartyLoginType == null) {
            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
        }
        logger.info("第三方登录类型：" + thirdPartyLoginType.getName());
        String openId = dto.getOpenId();
        User user = null;
        switch (thirdPartyLoginType) {
            case WX:
                user = selectOne(new EntityWrapper<User>().eq("openid", openId).isNotNull("mobile").eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
                break;
            case TENCENT:
                user = selectOne(new EntityWrapper<User>().eq("tencent_unionid", openId).isNotNull("mobile").eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
        }
        if (Objects.isNull(user)) {
            LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
        }
        String accessToken = UUID.randomUUID().toString();
        redisService.cacheAppUser(accessToken, user);
        TokenDto tokenDto = new TokenDto();
        if (!ParamUtil.isNullOrEmptyOrZero(dto.getAppId())) {
            String res = GizwitsUtil.createUser(user.getUsername(), dto.getAppId());
            JSONObject json = JSONObject.parseObject(res);
            String userToken = String.valueOf(json.get("token"));
            String uid = String.valueOf(json.get("uid"));
            tokenDto.setUserToken(userToken);
            tokenDto.setUid(uid);
        }

        tokenDto.setJwtAuthenticationDto(new JwtAuthenticationDto(accessToken));
        return tokenDto;
    }

    @Override
    @Deprecated
    public void register(UserForRegisterDto userForRegisterDto) {
        if (!Objects.equals(userForRegisterDto.getMessage(), redisService.getRegisterMessageCode(userForRegisterDto.getMobile()))) {
            throw new SystemException(SysExceptionEnum.MESSAGE_ERROR.getCode(), SysExceptionEnum.MESSAGE_ERROR.getMessage());
        }
        if (StringUtils.isEmpty(userForRegisterDto.getWeChatUnionId())) {
            LeaseException.throwSystemException(LeaseExceEnums.WEIXIN_OPENID_IS_NULL);
        }
        checkMobileExist(userForRegisterDto.getMobile());
        User user = selectOne(new EntityWrapper<User>().eq("openid", userForRegisterDto.getWeChatUnionId()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
        if (Objects.isNull(user)) {
            user = new User();
            String mobile = userForRegisterDto.getMobile();
            String username = mobile.substring(mobile.length() - 5, mobile.length()).replaceAll("-", "").replaceAll("_", "") + IdGenerator.getRandomString(5);
            user.setUsername(username);
        }
        Date now = new Date();
        user.setMobile(userForRegisterDto.getMobile());
        user.setPassword(PasswordUtil.generate(userForRegisterDto.getPassword()));
        user.setOpenid(userForRegisterDto.getWeChatUnionId());
        user.setSinaUnionid(userForRegisterDto.getSinaUnionid());
        user.setBaiduUnionid(userForRegisterDto.getBaiduUniond());
        user.setCtime(now);
        user.setUtime(now);
        user.setLastLoginTime(now);
        user.setAuthorizationTime(now);
        insertOrUpdate(user);
        code.clear();

    }

    @Override
    public TokenDto register(UserForRegisterDto userForRegisterDto, Integer browserAgentType, String appKey) {
        //验证图形
        if (!ParamUtil.isNullOrEmptyOrZero(userForRegisterDto.getPictureId())) {
            String pictrue = redisService.getPictureCode(userForRegisterDto.getPictureId());
            if (!Objects.equals(pictrue.toUpperCase(), userForRegisterDto.getPictureCode().toUpperCase())) {
                LeaseException.throwSystemException(LeaseExceEnums.PICTURE_CODE_ERROR);
            }
        }

        if (!Objects.equals(userForRegisterDto.getMessage(), redisService.getRegisterMessageCode(userForRegisterDto.getMobile()))) {
            throw new SystemException(SysExceptionEnum.MESSAGE_ERROR.getCode(), SysExceptionEnum.MESSAGE_ERROR.getMessage());
        }
        User user = null;
        //微信端绑定
        if (browserAgentType.equals(ThirdPartyUserType.WEIXIN.getCode())) {
            user = getUserNoRecoverByWxOpenid(userForRegisterDto.getWeChatUnionId());
            //普通浏览器注册
        } else if (browserAgentType.equals(ThirdPartyUserType.NORMAL.getCode())) {
            user = new User();
            String mobile = userForRegisterDto.getMobile();
            String username = mobile.substring(mobile.length() - 5, mobile.length()).replaceAll("-", "").replaceAll("_", "") + IdGenerator.getRandomString(5);
            user.setUsername(username);
            user.setMobile(userForRegisterDto.getMobile());
            user.setInfoState(UserInfoState.SELF.getValue());

        } else if (browserAgentType.equals(ThirdPartyUserType.ALIPAY.getCode())
                || browserAgentType.equals(ThirdPartyUserType.BAIDU.getCode())
                || browserAgentType.equals(ThirdPartyUserType.SINA.getCode())) {
            user = getUserByIdOrOpenidOrMobile(userForRegisterDto.getAlipayUnionid());

        }
        if (user == null) {
            LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
        }
        Date now = new Date();
        user.setMobile(userForRegisterDto.getMobile());
        user.setPassword(PasswordUtil.generate(userForRegisterDto.getPassword()));
        user.setCtime(now);
        user.setUtime(now);
        user.setLastLoginTime(now);
        user.setAuthorizationTime(now);
        setUserOwner(user, appKey);
        insertOrUpdate(user);
        UserLoginDto dto = new UserLoginDto();
        dto.setMobile(user.getMobile());
        dto.setPassword(userForRegisterDto.getPassword());
        return login(dto);
    }


    @Override
    public TokenDto register2(UserForRegisterDto userForRegisterDto, Integer browserAgentType, String appKey) {
        //验证图形
        if (!ParamUtil.isNullOrEmptyOrZero(userForRegisterDto.getPictureId())) {
            String pictrue = redisService.getPictureCode(userForRegisterDto.getPictureId());
            if (!Objects.equals(pictrue.toUpperCase(), userForRegisterDto.getPictureCode().toUpperCase())) {
                LeaseException.throwSystemException(LeaseExceEnums.PICTURE_CODE_ERROR);
            }
        }

        if (!Objects.equals(userForRegisterDto.getMessage(), redisService.getRegisterMessageCode(userForRegisterDto.getMobile()))) {
            throw new SystemException(SysExceptionEnum.MESSAGE_ERROR.getCode(), SysExceptionEnum.MESSAGE_ERROR.getMessage());
        }
        User user = null;
        //微信端绑定
        if (browserAgentType.equals(ThirdPartyUserType.WEIXIN.getCode())) {
            user = getUserNoRecoverByWxOpenid(userForRegisterDto.getWeChatUnionId());
            //普通浏览器注册
        } else if (browserAgentType.equals(ThirdPartyUserType.NORMAL.getCode())) {
            user = new User();
            String mobile = userForRegisterDto.getMobile();
            String username = mobile.substring(mobile.length() - 5, mobile.length()).replaceAll("-", "").replaceAll("_", "") + IdGenerator.getRandomString(5);
            user.setUsername(username);
            user.setMobile(userForRegisterDto.getMobile());
            user.setInfoState(UserInfoState.SELF.getValue());

        } else if (browserAgentType.equals(ThirdPartyUserType.ALIPAY.getCode())
                || browserAgentType.equals(ThirdPartyUserType.BAIDU.getCode())
                || browserAgentType.equals(ThirdPartyUserType.SINA.getCode())) {
            user = getUserByIdOrOpenidOrMobile(userForRegisterDto.getAlipayUnionid());

        }
        if (user == null) {
            LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
        }
        Date now = new Date();
        user.setMobile(userForRegisterDto.getMobile());
        user.setPassword(PasswordUtil.generate(userForRegisterDto.getPassword()));
        user.setCtime(now);
        user.setUtime(now);
        user.setLastLoginTime(now);
        user.setAuthorizationTime(now);
        setUserOwner(user, appKey);
        insertOrUpdate(user);
        UserLoginDto dto = new UserLoginDto();
        dto.setMobile(user.getMobile());
        dto.setPassword(userForRegisterDto.getPassword());
        dto.setAppId(userForRegisterDto.getAppId());
        return login2(dto);
    }

    @Transactional
    @Override
    public TokenDto thirdRegister(UserForThirdRegisterDto dto, String appKey) {
        String thirdPartRegisterWithMobile =
                SysConfigUtils.get(CommonSystemConfig.class).getThirdPartRegisterWithMobile();
        Boolean needMobile = null;
        try {
            needMobile = Boolean.parseBoolean(thirdPartRegisterWithMobile);
        } catch (Exception e) {
            logger.error("不能判断是否需要绑定手机号", e);
        }

        User user = new User();

        if (BooleanUtils.isTrue(needMobile)) {
            // 注册需要手机号、验证码、密码
            if (StringUtils.isBlank(dto.getMobile()) || StringUtils.isBlank(dto.getMessage()) || StringUtils.isBlank(dto.getPassword())) {
                LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
            }
            user.setMobile(dto.getMobile());
            // 校验验证码
            if (!Objects.equals(dto.getMessage(), redisService.getRegisterMessageCode(dto.getMobile()))) {
                throw new SystemException(SysExceptionEnum.MESSAGE_ERROR.getCode(), SysExceptionEnum.MESSAGE_ERROR.getMessage());
            }
            // 设置密码
            user.setPassword(PasswordUtil.generate(dto.getPassword()));

        }

        String username = RandomStringUtils.randomAlphanumeric(10);
        user.setUsername(username);
        logger.info("======>第三方注册， openId = " + dto.getOpenId());
        String nickname = null;
        Integer sex = null;
        String avatar = null;
        if (dto.getThirdType().equals(ThirdPartyLoginType.WX.getCode())) {
            //判断用户是否已通过微信公众号注册用户
            User userDb = selectOne(new EntityWrapper<User>().eq("openid", dto.getOpenId()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
            if (!ParamUtil.isNullOrEmptyOrZero(userDb)) {
                userDb.setMobile(dto.getMobile());
                // 设置密码
                userDb.setPassword(PasswordUtil.generate(dto.getPassword()));
                userDb.setUsername(username);
                updateById(userDb);
                String accessToken = UUID.randomUUID().toString();
                redisService.cacheAppUser(accessToken, userDb);
                TokenDto tokenDto = new TokenDto();
                tokenDto.setJwtAuthenticationDto(new JwtAuthenticationDto(accessToken));
                if (!ParamUtil.isNullOrEmptyOrZero(dto.getAppId())) {
                    String res = GizwitsUtil.createUser(userDb.getUsername(), dto.getAppId());
                    JSONObject json = JSONObject.parseObject(res);
                    String userToken = String.valueOf(json.get("token"));
                    String uid = String.valueOf(json.get("uid"));
                    tokenDto.setUserToken(userToken);
                    tokenDto.setUid(uid);
                }
                return tokenDto;
                /*user.setOpenid(userDb.getOpenid());
                //假删除微信公众号用户
                userDb.setIsDeleted(DeleteStatus.DELETED.getCode());
                userDb.setUtime(new Date());
                updateById(userDb);
                UserWxExt userWxExt = userWxExtService.selectOne(new EntityWrapper<UserWxExt>().eq("user_openid",userDb.getOpenid()).eq("is_deleted",DeleteStatus.NOT_DELETED.getCode()));
                if (!ParamUtil.isNullOrEmptyOrZero(userWxExt)){
                    userWxExt.setUtime(new Date());
                    userWxExt.setIsDeleted(DeleteStatus.DELETED.getCode());
                    userWxExtService.updateById(userWxExt);
                }*/
            } else {
                //微信
                String userInfo = WxUtil.getUserInfoByToken(dto.getOpenId(), dto.getToken());
                JSONObject jsonObject = JSONObject.parseObject(userInfo);
                nickname = jsonObject.getString("nickname");
                user.setWxNickname(jsonObject.getString("nickname"));
                sex = jsonObject.getInteger("sex");
                avatar = jsonObject.getString("headimgurl");
                String unionid = jsonObject.getString("unionid");
                user.setOpenid(unionid);
            }


        } else if (dto.getThirdType().equals(ThirdPartyLoginType.TENCENT.getCode())) {
            // 腾讯
            String userInfo = TencentUtil.getUserInfoByToken(dto.getOpenId(), dto.getToken());
            JSONObject jsonObject = JSONObject.parseObject(userInfo);
            nickname = jsonObject.getString("nickname");
            sex = jsonObject.getString("gender").equals(SexType.MALE.getName()) ? SexType.MALE.getCode() : SexType.FEMALE.getCode();
            avatar = jsonObject.getString("figureurl_qq_1");
            user.setTencentUnionid(dto.getOpenId());
            user.setTencentNickname(jsonObject.getString("nickname"));
        }
        Date now = new Date();
        user.setNickname(nickname);
        user.setGender(sex);
        user.setAvatar(avatar);
        user.setCtime(now);
        user.setUtime(now);
        user.setLastLoginTime(now);
        user.setAuthorizationTime(now);
        setUserOwner(user, appKey);
        insert(user);
        code.clear();
        // 第三方注册后自动登录
        String accessToken = UUID.randomUUID().toString();
        redisService.cacheAppUser(accessToken, user);
        TokenDto tokenDto = new TokenDto();
        tokenDto.setJwtAuthenticationDto(new JwtAuthenticationDto(accessToken));
        if (!ParamUtil.isNullOrEmptyOrZero(dto.getAppId())) {
            String res = GizwitsUtil.createUser(user.getUsername(), dto.getAppId());
            JSONObject json = JSONObject.parseObject(res);
            String userToken = String.valueOf(json.get("token"));
            String uid = String.valueOf(json.get("uid"));
            tokenDto.setUserToken(userToken);
            tokenDto.setUid(uid);
        }
        return tokenDto;
    }

    private void setUserOwner(User user, String appkey) {
        if (StringUtils.isBlank(appkey)) {
            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
        }

        Manufacturer manufacturer = manufacturerService.selectByEnterpriseId(appkey);
        if (Objects.isNull(manufacturer)) {
            LeaseException.throwSystemException(LeaseExceEnums.MANUFACTURER_NOT_EXIST);
        }

        user.setSysUserId(manufacturer.getSysAccountId());
    }

  /*  @Transactional
    @Override
    public JwtAuthenticationDto thirdRegister2(UserForThirdRegisterDto dto) {
        String thirdPartRegisterWithMobile =
                SysConfigUtils.get(CommonSystemConfig.class).getThirdPartRegisterWithMobile();
        Boolean needMobile = null;
        try {
            needMobile = Boolean.parseBoolean(thirdPartRegisterWithMobile);
        } catch (Exception e) {
            logger.error("不能判断是否需要绑定手机号", e);
        }

        User user = getUserByIdOrOpenidOrMobile(dto.getOpenId());

        if (BooleanUtils.isTrue(needMobile)) {
            // 注册需要手机号、验证码、密码
            if (StringUtils.isBlank(dto.getMobile()) || StringUtils.isBlank(dto.getMessage()) || StringUtils.isBlank(dto.getPassword())) {
                LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
            }
            user.setMobile(dto.getMobile());
            // 校验验证码
            if (!Objects.equals(dto.getMessage(), redisService.getRegisterMessageCode(dto.getMobile()))) {
                throw new SystemException(SysExceptionEnum.MESSAGE_ERROR.getCode(), SysExceptionEnum.MESSAGE_ERROR.getMessage());
            }
            // 设置密码
            user.setPassword(PasswordUtil.generate(dto.getPassword()));
        }

        logger.info("======> 微信第三方注册，绑定手机号， openId = " +dto.getOpenId());

        user.setUtime(new Date());
        updateById(user);
        code.clear();
        // 第三方注册后自动登录
        String accessToken = uuidUtil.randomUUID().toString();
        redisService.cacheAppUser(accessToken, user);
        return new JwtAuthenticationDto(accessToken);
    }
*/

    @Override
    public void messageCodeForRegister(String mobile) {
        checkMobileExist(mobile);

        String message = sendMessageCode(mobile);
        redisService.cacheRegisterMessageCode(mobile, message, 60L);

//        code.put(mobile, message);
    }

    private String sendMessageCode(String mobile) {
//        String appMessage = SysConfigUtils.get(CommonSystemConfig.class).getMessageCodeParamApp();
        String tplValue = SysConfigUtils.get(CommonSystemConfig.class).getMessageCodeTemplate();
        String apiKey = SysConfigUtils.get(CommonSystemConfig.class).getMessageApiKey();
        String templageId = SysConfigUtils.get(CommonSystemConfig.class).getMessageCodeTemplateId();

        Map<String, String> params = new HashedMap();
//        params.put("app", appMessage);

        return SmsApi.tplSendSms(apiKey, templageId, mobile, tplValue, params);
    }

    @Override
    public void messageCode(String mobile) {

        SysUser user = sysUserService.selectOne(new EntityWrapper<SysUser>().eq("mobile", mobile).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
        if (ParamUtil.isNullOrEmptyOrZero(user)) {
            throw new SystemException(SysExceptionEnum.ILLEGAL_PARAM.getCode(), SysExceptionEnum.ILLEGAL_PARAM.getMessage());
        }
        String _code = sendMessageCode(mobile);
        code.put(mobile, _code);
        user.setCode(_code);
        sysUserService.updateById(user);
    }

    private void checkMobileExist(String mobile) {
        int count = selectCount(new EntityWrapper<User>().eq("mobile", mobile).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
        if (count > 0) {
            LeaseException.throwSystemException(LeaseExceEnums.USER_PHONE_EXISTS);
        }
    }

    @Override
    public User getUserByMobile(String mobile) {
        User user = selectOne(new EntityWrapper<User>().eq("mobile", mobile).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
        if (user == null) {
            LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
        }
        return user;
    }

    @Override
    public void updateUsername(UserUpdateDto data) {
        User user = getCurrentUser();
        String oldname = user.getNickname();
        if (!data.getNickname().equals(user.getNickname())) {
            User forUpdate = new User();
            forUpdate.setId(user.getId());
            forUpdate.setNickname(data.getNickname());
            forUpdate.setInfoState(UserInfoState.SELF.getValue());
            updateById(forUpdate);
            NameModifyEvent<Integer> nameModifyEvent = new NameModifyEvent<Integer>(user, user.getId(), oldname, data.getNickname());
            CommonEventPublisherUtils.publishEvent(nameModifyEvent);
        }
    }

    @Override
    public UserForDetailDto detail() {
        User user = getCurrentUser();
        UserForDetailDto result = getUserForDetailDto(user);
        return result;
    }

    private UserForDetailDto getUserForDetailDto(User user) {

        if (Objects.isNull(user)) {
            LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
        }
        UserForDetailDto result = new UserForDetailDto(user);
        result.setGenderDesc(SexType.getName(user.getGender()));
        result.setWxOpenId(user.getOpenid());
        result.setWxNickname(user.getWxNickname());
        result.setTencentOpenid(user.getTencentUnionid());
        result.setTencentNickname(user.getTencentNickname());
        return result;
    }

    @Override
    public User getUserByOpenid(String openid) {
        User user = userDao.findByOpenid(openid);
        return user;
    }

    @Override
    public void messageCodeForForgetPassword(String mobile) {

        User user = selectOne(new EntityWrapper<User>().eq("mobile", mobile).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
        if (ParamUtil.isNullOrEmptyOrZero(user)) {
            LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
        }
        String _code = sendMessageCode(mobile);
        redisService.cacheForgetMessageCode(mobile, _code, 60L);
       /* code.put(mobile, _code);

        user.setCode(_code);
        updateById(user);*/
    }

    @Override
    public void forgetPwd(UserResetPasswordDto userForgetPasswordDto) {
        String mobile = userForgetPasswordDto.getMobile();
        User user = getUserByMobile(mobile);
        //验证验证码
        String code=redisService.getMobileCode("password"+mobile);
        if(ParamUtil.isNullOrEmptyOrZero(code)){
            LeaseException.throwSystemException(LeaseExceEnums.MOBILE_CODE_ERROR_OR_EXPIRE);
        }
        if (!Objects.equals(userForgetPasswordDto.getMessage(), code)) {
            throw new SystemException(SysExceptionEnum.MESSAGE_ERROR.getCode(), SysExceptionEnum.MESSAGE_ERROR.getMessage());
        }
        user.setPassword(PasswordUtil.generate(userForgetPasswordDto.getNewPassword()));
        user.setUtime(new Date());
        updateById(user);
    }

    @Override
    public void resetPwd(UserForUpdatePwdDto dto) {
        User user = getCurrentUser();
        String code=redisService.getMobileCode("password"+user.getMobile());
        if(ParamUtil.isNullOrEmptyOrZero(code)){
            LeaseException.throwSystemException(LeaseExceEnums.MOBILE_CODE_ERROR_OR_EXPIRE);
        }
        if(!code.equals(dto.getNewPassword())){
            throw new SystemException(SysExceptionEnum.MESSAGE_ERROR.getCode(), SysExceptionEnum.MESSAGE_ERROR.getMessage());
        }
        user.setId(user.getId());
        user.setPassword(PasswordUtil.generate(dto.getNewPassword()));
        user.setUtime(new Date());
        updateById(user);
    }

    @Override
    public UserForInfoDto userInfo(String mobile) {
        User user = getUserByMobile(mobile);
        UserForInfoDto userForInfoDto = new UserForInfoDto();
        userForInfoDto.setUsername(user.getUsername());
        userForInfoDto.setAvatar(user.getAvatar());
        userForInfoDto.setGender(user.getGender());
        userForInfoDto.setMobile(user.getMobile());
        userForInfoDto.setNickname(user.getNickname());
        userForInfoDto.setHasPassword(0);
        if (!ParamUtil.isNullOrEmptyOrZero(user.getOpenid())) {
            //需要更改
            userForInfoDto.setIsBindWeChat(1);
        }
        if (!ParamUtil.isNullOrEmptyOrZero(user.getAlipayUnionid())) {
            userForInfoDto.setIsBindAlipay(1);
        }
        if (!ParamUtil.isNullOrEmptyOrZero(user.getPassword())) {
            userForInfoDto.setHasPassword(1);
        }

        return userForInfoDto;
    }

    @Override
    public UserForInfoDto getUserInfo(UserChargeCardOpenidDto openidDto) {
        User user = getUserByIdOrOpenidOrMobile(openidDto.getOpenid());
        if (user == null) {
            LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
        }
        UserForInfoDto userForInfoDto = new UserForInfoDto();
        userForInfoDto.setAvatar(user.getAvatar());
        userForInfoDto.setNickname(user.getNickname());

        userForInfoDto.setCardCount(userChargeCardService.countChargeCard(user.getId()));

        return userForInfoDto;
    }

    public User getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public boolean update(UserForInfoDto userForInfoDto) {
        User user = getCurrentUser();
        User forUpdate = new User();
        forUpdate.setId(user.getId());
        forUpdate.setUtime(new Date());
        if (StringUtils.isNotBlank(userForInfoDto.getNickname())) {
            forUpdate.setNickname(userForInfoDto.getNickname());
            forUpdate.setInfoState(UserInfoState.SELF.getValue());
        }
        if (userForInfoDto.getGender() != null) {
            forUpdate.setGender(userForInfoDto.getGender());
            forUpdate.setInfoState(UserInfoState.SELF.getValue());
        }
        if (StringUtils.isNotBlank(userForInfoDto.getProvince())) {
            forUpdate.setProvince(userForInfoDto.getProvince());
            forUpdate.setInfoState(UserInfoState.SELF.getValue());
        }
        if (StringUtils.isNotBlank(userForInfoDto.getCity())) {
            forUpdate.setCity(userForInfoDto.getCity());
            forUpdate.setInfoState(UserInfoState.SELF.getValue());
        }
        if (Objects.equals(userForInfoDto.getIsBindWeChat(), 0)) {
            userWxExtService.delete(new EntityWrapper<UserWxExt>().eq("openid", user.getOpenid()));//删除微信用户扩展表信息
            forUpdate.setOpenid(" ");
        }
        if (Objects.equals(userForInfoDto.getIsBindAlipay(), 0)) {
            forUpdate.setAlipayUnionid(" ");
        }

        return updateById(forUpdate);
    }

    private void thirdBind(User userFromDb, UserForThirdBindDto dto) {
        Integer thirdType = dto.getThirdType();
        ThirdPartyLoginType thirdPartyLoginType = ThirdPartyLoginType.get(thirdType);
        if (thirdPartyLoginType == null) {
            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
        }
        logger.info("第三方绑定类型：" + thirdPartyLoginType.getName());
        String openId = dto.getOpenId();
        User userForUpdate = new User();
        userForUpdate.setId(userFromDb.getId());
        String nickname = null;
        Integer sex = null;
        String avatar = null;
        switch (thirdPartyLoginType) {
            case WX:
                //判断微信是否已被其他账号绑定
                EntityWrapper<User> entity = new EntityWrapper<>();
                entity.eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("openid", openId).isNotNull("mobile");
                if (!ParamUtil.isNullOrEmptyOrZero(selectOne(entity))) {
                    LeaseException.throwSystemException(LeaseExceEnums.CANT_BIND);
                }
                //判断用户是否已通过公众号扫码过
                EntityWrapper<User> entityWrapper = new EntityWrapper<>();
                entityWrapper.eq("openid", openId).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
                User user = selectOne(entityWrapper);
                if (!ParamUtil.isNullOrEmptyOrZero(user)) {
                    //假删除微信公众号用户
                    user.setIsDeleted(DeleteStatus.DELETED.getCode());
                    user.setUtime(new Date());
                    updateById(user);
                    /*UserWxExt userWxExt = userWxExtService.selectOne(new EntityWrapper<UserWxExt>().eq("user_openid",user.getOpenid()).eq("is_deleted",DeleteStatus.NOT_DELETED.getCode()));
                    if (!ParamUtil.isNullOrEmptyOrZero(userWxExt)){
                        userWxExt.setUtime(new Date());
                        userWxExt.setIsDeleted(DeleteStatus.DELETED.getCode());
                        userWxExtService.updateById(userWxExt);
                    }*/
                }
                String userInfoWx = WxUtil.getUserInfoByToken(openId, dto.getToken());
                JSONObject jsonObjectWx = JSONObject.parseObject(userInfoWx);
                nickname = jsonObjectWx.getString("nickname");
                userForUpdate.setWxNickname(nickname);
                sex = jsonObjectWx.getInteger("sex");
                avatar = jsonObjectWx.getString("headimgurl");
                userForUpdate.setOpenid(openId);
                break;
            case TENCENT:
                //qq是否已被其他账号绑定绑定
                EntityWrapper<User> entity1 = new EntityWrapper<>();
                entity1.eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("tencent_unionid", openId).isNotNull("mobile");
                if (!ParamUtil.isNullOrEmptyOrZero(selectOne(entity1))) {
                    LeaseException.throwSystemException(LeaseExceEnums.CANT_BIND);
                }
                String userInfoTc = TencentUtil.getUserInfoByToken(openId, dto.getToken());
                JSONObject jsonObjectTc = JSONObject.parseObject(userInfoTc);
                nickname = jsonObjectTc.getString("nickname");
                userForUpdate.setTencentNickname(nickname);
                sex = jsonObjectTc.getString("gender").equals(SexType.MALE.getName()) ? SexType.MALE.getCode() : SexType.FEMALE.getCode();
                avatar = jsonObjectTc.getString("figureurl_qq_1");
                userForUpdate.setTencentUnionid(openId);
                break;
        }
   /* // 自动从第三方帐号补全信息
    if (StringUtils.isBlank(userFromDb.getNickname())) {
        userForUpdate.setNickname(nickname);
    }*/
        if (userFromDb.getGender() == null) {
            userForUpdate.setGender(sex);
        }
        if (StringUtils.isBlank(userFromDb.getAvatar())) {
            userForUpdate.setAvatar(avatar);
        }
        userForUpdate.setUtime(new Date());
        updateById(userForUpdate);
    }

    @Override
    public void thirdBindByToken(UserForThirdBindDto dto) {
        User userFromDb = getCurrentUser();
        thirdBind(userFromDb, dto);
    }


    @Override
    public void messageCodeForBind(String mobile) {
        User user = selectOne(new EntityWrapper<User>().eq("mobile", mobile).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
        if (ParamUtil.isNullOrEmptyOrZero(user)) {
            LeaseException.throwSystemException(LeaseExceEnums.MOBILE_NOT_EXIT);
        }

        String code = sendMessageCode(mobile);
        redisService.cacheBindMessageCode(mobile, code, 300L);

    }

    /**
     * 微信公众号绑定手机号
     *
     * @param userForRegisterDto
     */
    @Override
    public JwtAuthenticationDto bindMobile(UserBindMobileDto userForRegisterDto) {
        String mobile = userForRegisterDto.getMobile();
        User mobileUser = selectOne(new EntityWrapper<User>().eq("mobile", mobile).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
        if (!Objects.equals(userForRegisterDto.getCode() + "", redisService.getBindMessageCode(userForRegisterDto.getMobile()))) {
            throw new SystemException(SysExceptionEnum.MESSAGE_ERROR.getCode(), SysExceptionEnum.MESSAGE_ERROR.getMessage());
        }
        User user = getCurrentUser();

        String accessToken = UUID.randomUUID().toString();

        //手机已注册app用户(未绑定微信）和微信公众号用户是不同的用户,需要进行用户合并,将微信公众号用户的信息合并到app用户
        if (mobileUser != null && user != null && user.getId() != mobileUser.getId() && (mobileUser.getOpenid() == null || mobileUser.getOpenid().equals(user.getOpenid()))) {
            mobileUser.setOpenid(user.getOpenid());
            mobileUser.setAddress(user.getAddress());
            mobileUser.setAlipayUnionid(user.getAlipayUnionid());
            if (ParamUtil.isNullOrEmptyOrZero(mobileUser.getGender())) {
                mobileUser.setGender(user.getGender());
            }
            if (ParamUtil.isNullOrEmptyOrZero(mobileUser.getProvince())) {
                mobileUser.setProvince(user.getProvince());
                mobileUser.setCity(user.getCity());
            }
            mobileUser.setAvatar(user.getAvatar());
            mobileUser.setBaiduUnionid(user.getBaiduUnionid());
            mobileUser.setSinaUnionid(user.getSinaUnionid());
            mobileUser.setWxId(user.getWxId());
            mobileUser.setThirdParty(user.getThirdParty());
            mobileUser.setSysUserId(user.getSysUserId());
            if (ParamUtil.isNullOrEmptyOrZero(mobileUser.getOpenid()) || ParamUtil.isNullOrEmptyOrZero(mobileUser.getWxNickname())) {
                mobileUser.setWxNickname(user.getNickname());
                mobileUser.setOpenid(user.getOpenid());
            }
            //假删除微信用户
            user.setIsDeleted(DeleteStatus.DELETED.getCode());
            user.setUtime(new Date());
            updateById(user);
            UserWxExt userWxExt = userWxExtService.selectOne(new EntityWrapper<UserWxExt>().eq("user_openid", user.getOpenid()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
            if (!ParamUtil.isNullOrEmptyOrZero(userWxExt)) {
                userWxExt.setUtime(new Date());
                userWxExt.setIsDeleted(DeleteStatus.DELETED.getCode());
                userWxExtService.updateById(userWxExt);
            }
            mobileUser.setUtime(new Date());
            updateById(mobileUser);
            redisService.cacheAppUser(accessToken, mobileUser);

            //如果手机号未注册，需要发送app初始账号，密码到公众号
        } else if (ParamUtil.isNullOrEmptyOrZero(mobileUser)) {
            String code = RandomUtils.createRandomCharData(6);
            user.setPassword(PasswordUtil.generate(code));
            user.setMobile(mobile);
            user.setUtime(new Date());
            updateById(user);
            redisService.cacheAppUser(accessToken, user);
            UserWxExt userWxExt = userWxExtService.getByOpenid(user.getOpenid());
            List<String> openIds = new ArrayList<>(2);
            openIds.add(userWxExt.getOpenid());
            openIds.add(userWxExt.getOpenid());
            SysUserExt sysUserExt = sysUserExtService.selectOne(new EntityWrapper<SysUserExt>().eq("wx_id", userWxExt.getWxId()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
            JSONObject sendData = new JSONObject();
            sendData.put("touser", userWxExt.getOpenid());
            sendData.put("msgtype", "text");
            JSONObject textData = new JSONObject();
            String value = "您已成功绑定手机号：" + mobile + "，初始密码为：" + code + "，您可使用此手机号和初始密码登录APP";
            textData.put("content", value);
            sendData.put("text", textData);
            //发送文本消息到微信
            String result = WxUtil.sendTextMessage(sendData.toJSONString(), sysUserExt);
            JSONObject jsonObject = JSONObject.parseObject(result);
            Integer errcode = jsonObject.getInteger("errcode");
            // 客户消息发送失败，转为群发消息接口发送
            if (errcode == null || !errcode.equals(0)) {
                sendData.replace("touser", openIds);
                WxUtil.sendManyNotices(sendData.toJSONString(), sysUserExt);
            }
        } else if (mobileUser.getOpenid() != null && !mobileUser.getOpenid().equals(user.getOpenid())) {
            LeaseException.throwSystemException(LeaseExceEnums.MOBILE_BINDED);
        }
        logger.info("绑定账号后登录");

        return new JwtAuthenticationDto(accessToken);
    }

    /**
     * 修改用户手机号
     *
     * @param mobileDto
     * @return
     */
    public boolean updateUserMobile(UserForUpdateMobileDto mobileDto) {
        if(mobileDto.getNewMobile()==null||mobileDto.getNewCode()==null||mobileDto.getPassword()==null){
            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
        }
        checkMobileExist(mobileDto.getNewMobile());

        User user = getCurrentUser();
        String code=redisService.getMobileCode("password"+user.getMobile());
        if(ParamUtil.isNullOrEmptyOrZero(code)){
            LeaseException.throwSystemException(LeaseExceEnums.MOBILE_CODE_ERROR_OR_EXPIRE);
        }
        //验证验证码
        if (!Objects.equals(mobileDto.getNewCode(), code)) {
            throw new SystemException(SysExceptionEnum.MESSAGE_ERROR.getCode(), SysExceptionEnum.MESSAGE_ERROR.getMessage());
        }
        user.setMobile(mobileDto.getNewMobile());
        user.setPassword(PasswordUtil.generate(mobileDto.getPassword()));
        user.setUtime(new Date());
        return updateById(user);
    }

    @Override
    public User getCurrentUser() {
        User user = redisService.getAppUser(WebUtils.getHeader(Constants.TOKEN_HEADER_NAME));
        if (user == null)
            LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
        return selectById(user.getId());
//        return selectById(2);
    }

    @Autowired
    private SysUserExtService sysUserExtService;

    @Autowired
    private ChinaAreaService chinaAreaService;

    @Override
    public void insertWxUser(Date ctime) {
        CommonSystemConfig commonSystemConfig = SysConfigUtils.get(CommonSystemConfig.class);
        String str = commonSystemConfig.getDefaultManufacturerRoleId();
        String[] ids = str.split(",");
        Random random = new Random();
        String id = ids[random.nextInt(ids.length)];
        Integer roleId = Integer.parseInt(id);
        SysUser currentUser = sysUserService.getUsersByRoleId(roleId).get(0);
        SysUserExt sysUserExt = sysUserExtService.selectById(currentUser.getId());
        String openid = RandomUtils.createRandomCharData(28);
        String unionid = openid;
        String username = openid.substring(openid.length() - 5, openid.length()).replaceAll("-", "").replaceAll("_", "") + IdGenerator.getRandomString(5);
        User dbUser = new User();
        dbUser.setCtime(ctime);
        dbUser.setUtime(ctime);
        dbUser.setOpenid(unionid);
        dbUser.setUsername(username);
        dbUser.setLastLoginTime(ctime);
        dbUser.setSysUserId(currentUser.getId());
        Map<String, String> map = RandomUtils.getUserInfo();
        String mobile = map.get("tel");
        dbUser.setMobile(mobile);
        String nickName = map.get("name");
        dbUser.setNickname(nickName);
        dbUser.setNickname(nickName);
        String gender = map.get("sex");
        dbUser.setGender(Integer.parseInt(gender));
        String avater = map.get("avatar");
        dbUser.setAvatar(avater);
        AreaDto areaDto = chinaAreaService.getArea(0);
        if (!ParamUtil.isNullOrEmptyOrZero(areaDto)) {
            dbUser.setProvince(areaDto.getName());
            areaDto = chinaAreaService.getArea(areaDto.getCode());
            if (!ParamUtil.isNullOrEmptyOrZero(areaDto)) {
                dbUser.setCity(areaDto.getName());
            }
        }
        //标记假数据
        dbUser.setRemark("aep");
        dbUser.setThirdParty(ThirdPartyUserType.WEIXIN.getCode());
        dbUser.setInfoState(UserInfoState.THIRD.getValue());

        UserWxExt dbUserWxExt = new UserWxExt();
        dbUserWxExt.setUserOpenid(unionid);
        dbUserWxExt.setOpenid(openid);
        if (ParamUtil.isNullOrEmptyOrZero(sysUserExt.getWxId())) {
            dbUserWxExt.setWxId("gdms_wxId");
        } else {
            dbUserWxExt.setWxId(sysUserExt.getWxId());
        }
        dbUserWxExt.setSysUserId(sysUserExt.getSysUserId());
        dbUserWxExt.setCtime(ctime);
        dbUserWxExt.setUtime(ctime);
        insert(dbUser);
        userWxExtService.insert(dbUserWxExt);
    }

    @Override
    public void insertAppUser(Date ctime) {
        CommonSystemConfig commonSystemConfig = SysConfigUtils.get(CommonSystemConfig.class);
        String str = commonSystemConfig.getDefaultManufacturerRoleId();
        String[] ids = str.split(",");
        Random random = new Random();
        String id = ids[random.nextInt(ids.length)];
        Integer roleId = Integer.parseInt(id);
        SysUser currentUser = sysUserService.getUsersByRoleId(roleId).get(0);
        User user = new User();
        user.setCtime(ctime);
        user.setUtime(ctime);
        String openid = RandomUtils.createRandomCharData(11);
        String username = openid.substring(openid.length() - 5, openid.length()).replaceAll("-", "").replaceAll("_", "") + IdGenerator.getRandomString(5);
        user.setUsername(username);
        //随机生成6-18为密码
        String password = RandomUtils.getStringRandom(random.nextInt(12) + 6);
        user.setPassword(PasswordUtil.generate(password));
        Map<String, String> map = RandomUtils.getUserInfo();
        String mobile = map.get("tel");
        user.setMobile(mobile);
        String nickName = map.get("name");
        user.setNickname(nickName);
        String gender = map.get("sex");
        String avater = map.get("avatar");
        user.setAvatar(avater);
        AreaDto areaDto = chinaAreaService.getArea(0);
        if (!ParamUtil.isNullOrEmptyOrZero(areaDto)) {
            user.setProvince(areaDto.getName());
            areaDto = chinaAreaService.getArea(areaDto.getCode());
            if (!ParamUtil.isNullOrEmptyOrZero(areaDto)) {
                user.setCity(areaDto.getName());
            }
        }
        user.setGender(Integer.parseInt(gender));
        user.setInfoState(UserInfoState.SELF.getValue());
        user.setLastLoginTime(ctime);
        user.setSysUserId(currentUser.getId());
        //标记假数据
        user.setRemark("aep");
        insert(user);
    }

    @Override
    public boolean isOpenidOwnedByUser(String openid, User user) {
        if (openid == null || user == null) {
            return false;
        }

        if (StringUtils.equals(openid, user.getOpenid())) {
            return true;
        }

        UserWxExt userWxExt = userWxExtService.getByOpenid(openid);
        return userWxExt != null && StringUtils.equals(user.getOpenid(), userWxExt.getUserOpenid());
    }

    @Override
    public TokenDto getGizwitsToken(GizwitsTokenDto data) {
        User user = getCurrentUser();

        String res = GizwitsUtil.createUser(user.getUsername(), data.getAppid());
        logger.info("获取机智云token： " + res);
        JSONObject json = JSONObject.parseObject(res);
        String userToken = String.valueOf(json.get("token"));
        String uid = String.valueOf(json.get("uid"));
        TokenDto tokenDto = new TokenDto();
        tokenDto.setUserToken(userToken);
        tokenDto.setUid(uid);

        return tokenDto;
    }

    @Override
    public void messageCodeForBindWx(String mobile) {
        String code = sendMessageCode(mobile);
        redisService.cacheBindMessageCode(mobile, code, 300L);
    }

    //==================================//

    @Override
    public StatUserWidgetVo getUserWidget() {
        StatUserWidgetVo vo=new StatUserWidgetVo();
         Date yesterDay= DateUtil.addDay(new Date(),-1);
            vo=userDao.getUserWidget(yesterDay);
        return vo;
    }

    @Override
    public List<StatLocationVo> ditribution() {
        List<StatLocationVo> list=userDao.ditribution();
        if(ParamUtil.isNullOrEmptyOrZero(list)){
            return new LinkedList<>();
        }
        Integer total=selectCount(new EntityWrapper<User>().eq("is_deleted",0));
        if(total==0){
            return new LinkedList<>();
        }
        list.stream().forEach(item->{
             if(item.getProvince()==null||item.getProvince().equals("")) item.setProvince("其他");
             item.setCount(item.getDeviceCount());
             item.setProportion(BigDecimal.valueOf(item.getDeviceCount().doubleValue()/ total).setScale(1, BigDecimal.ROUND_HALF_UP)
                     .doubleValue());
        });
        return list;
    }

    @Override
    public List<StatLocationVo> userDitributionByCity(String province) {
        List<StatLocationVo> list=userDao.ditributionByCity(province);
        if(ParamUtil.isNullOrEmptyOrZero(list)){
            return new LinkedList<>();
        }
        Integer total=selectCount(new EntityWrapper<User>().eq("province",province).eq("is_deleted",0));
        if(total==0){
            return new LinkedList<>();
        }
        list.stream().forEach(item->{
            if(item.getProvince()==null||item.getProvince().equals("")) item.setProvince("其他");
            item.setCount(item.getDeviceCount());
            item.setProportion(BigDecimal.valueOf(item.getDeviceCount().doubleValue()/ total).setScale(1, BigDecimal.ROUND_HALF_UP)
                    .doubleValue());
        });
        return list;
    }

    @Override
    public Boolean add(UserAddDto userAddDto) {
        //判断手机号是否已使用
        User user=selectOne(new EntityWrapper<User>().eq("mobile",userAddDto.getMobile()).eq("is_deleted",DeleteStatus.NOT_DELETED.getCode()));
         if(!ParamUtil.isNullOrEmptyOrZero(user)){
             LeaseException.throwSystemException(LeaseExceEnums.USER_PHONE_EXISTS);
         }
          user=new User();
         user.setCtime(new Date());
         user.setUsername(userAddDto.getUsername());
         user.setMobile(userAddDto.getMobile());
         user.setGender(userAddDto.getGender());
         user.setPassword(PasswordUtil.generate(userAddDto.getPassword()));
         user.setLastLoginTime(new Date());
        return insert(user);
    }

    @Override
    public Boolean delete(List<Integer> ids) {

        List<Integer> errorIds=new ArrayList<>();
        Date date=new Date();
        for(Integer id:ids){
            User user=selectById(id);
            //判断是否存在
            if(ParamUtil.isNullOrEmptyOrZero(user)){
                errorIds.add(id);
            }
            //判断是否有设备
            Integer count=userBindDeviceService.selectCount(new EntityWrapper<UserBindDevice>().eq("user_id",id).eq("is_deleted",DeleteStatus.NOT_DELETED.getCode()));
           if(count>0){
               errorIds.add(id);
           }
           user.setUtime(date);
           user.setIsDeleted(DeleteStatus.DELETED.getCode());
           updateById(user);
        }
        if(errorIds!=null&&errorIds.size()>0){
            LeaseException.throwSystemException(LeaseExceEnums.DELETE_USER_ERROR);
        }
        return true;
    }

    @Override
    public Page<UserBindDeviceListVo> bindDeviceList(Pageable pageable) {

        Integer userId=(Integer)pageable.getQuery();
        Integer current=(pageable.getCurrent()-1)*pageable.getSize();
        Integer size=pageable.getSize();
         Page<UserBindDeviceListVo> page=new Page<>();
         List<UserBindDeviceListVo> list=new ArrayList<>();
         list=userBindDeviceDao.findBindDeviceByUserId(userId,current,size);
         Integer total =userBindDeviceDao.findBindDeviceByUserIdCount(userId);
         page.setRecords(list);
         page.setTotal(total);
        return page;
    }

    @Override
    public User getBindUser(String mac) {
        return userDao.getBindUser(mac);
    }

    @Override
    public Boolean sendCode(SendCodeDto sendCodeDto) {
        if(sendCodeDto.getMobile()==null||sendCodeDto.getType()==null){
            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
        }
        String mobile=StringUtil.decode("mobile_"+sendCodeDto.getMobile());
        if(mobile.length()<11){
            LeaseException.throwSystemException(LeaseExceEnums.PHONE_ERROR);
        }
         mobile=mobile.substring(mobile.length()-11,mobile.length());
        logger.info("发送手机号：{}",mobile);
        if(!MobileCheckUtils.isChinaPhoneLegal(mobile)){
             LeaseException.throwSystemException(LeaseExceEnums.PHONE_ERROR);
        }
//        String appKey = SysConfigUtils.get(MessageCodeConfig.class).getMessageApiKey();
        String appKey ="a714a43e8a14dfe5252a6b55cd966506";
//        String templateId = SysConfigUtils.get(MessageCodeConfig.class).getMessageCodeTemplateId();
        String templateId="3327468";
        String templateValue = "";
        Map<String, String> mapParam = new HashMap<>();
        String message = SmsApi.tplSendSms(appKey, templateId, mobile, templateValue, mapParam);
        logger.info("code:{}",message);
        redisService.cacheMobileCode(sendCodeDto.getType()+mobile,message);
        return true;
    }
    //===================================//

}
