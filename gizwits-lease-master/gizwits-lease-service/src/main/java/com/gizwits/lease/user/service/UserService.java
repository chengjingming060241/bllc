package com.gizwits.lease.user.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.gizwits.boot.dto.JwtAuthenticationDto;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.sys.entity.SysUserExt;
import com.gizwits.lease.device.entity.UserBindDevice;
import com.gizwits.lease.device.vo.UserBindDeviceListVo;
import com.gizwits.lease.stat.vo.StatLocationVo;
import com.gizwits.lease.stat.vo.StatUserWidgetVo;
import com.gizwits.lease.user.dto.*;
import com.gizwits.lease.user.dto.UserForDetailDto;
import com.gizwits.lease.user.dto.UserForInfoDto;
import com.gizwits.lease.user.dto.UserForListDto;
import com.gizwits.lease.user.dto.UserForMoveDto;
import com.gizwits.lease.user.dto.UserForQueryDto;
import com.gizwits.lease.user.dto.UserForRegisterDto;
import com.gizwits.lease.user.dto.UserForUpdatePwdDto;
import com.gizwits.lease.user.dto.UserLoginDto;
import com.gizwits.lease.user.dto.UserUpdateDto;
import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.enums.MoveType;
import com.gizwits.lease.user.entity.UserFamily;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * 用户表,不要前缀,因为用户模块计划抽象成通用功能 服务类
 * </p>
 *
 * @author rongmc
 * @since 2017-06-28
 */
public interface UserService extends IService<User> {

    User getUserByUsername(String username);

    User getUserByIdOrOpenidOrMobile(String openid);

    User getUserByOpenidAndExistAndNotInBlack(String openid);

    /**
     * 获取支付宝用户信息
     * @param userinfoShareResponse
     * @return
     */
//    User addUserByAlipay(AlipayUserUserinfoShareResponse userinfoShareResponse, SysUserExt sysUserExt);

    /**
     * 根据微信用户openid获取微信主体信息,返回对象的openid,为用户的unionid
     * @param wxOpenid
     * @return
     */
    User getUserNoRecoverByWxOpenid(String wxOpenid);
    /**
     * 添加微信用户
     * @param wxUserinfoJson 从微信获取的用户信息JSON字符串
     * @param sysUserExt 微信配置信息
     * @param sysUserid  设备所属的直接运营商SysUserid
     * @return
     */
    User addUserByWx(String wxUserinfoJson, SysUserExt sysUserExt, Integer sysUserid);

    Page<UserForListDto> page1(Pageable<UserForQueryDto> pageable);

    /**
     * 分页列表
     */
    Page<UserForListDto> page(Pageable<UserForQueryDto> pageable);

    Page<UserForListDto> pageForAffiliation(Pageable<QueryForUserListDTO> pageable);

    Page<UserForListDto> searchUser(Pageable<QueryForUserListDTO> pageable);

    /**
     * 详情
     */
    UserForDetailDto detail(String openid);

    /**
     * 移入/出黑名单
     */
    boolean move(UserForMoveDto dto, MoveType moveType);

    /**
     * 用户登录
     */
    TokenDto login(UserLoginDto userLoginDto);

    TokenDto login2(UserLoginDto userLoginDto);

    /**
     * 第三方登录
     */
    TokenDto thirdLogin(UserForThirdLoginDto dto);

    /**
     * 用户注册
     * @param userForRegisterDto
     */
    void register(UserForRegisterDto userForRegisterDto);
    /**
     * 用户注册
     * @param userForRegisterDto
     * @param browserAgentType 用户浏览器类型
     */
    TokenDto register(UserForRegisterDto userForRegisterDto,Integer browserAgentType, String appKey);

    TokenDto register2(UserForRegisterDto userForRegisterDto, Integer browserAgentType, String appKey);

    /**
     * 第三方注册
     * @param dto
     */
    TokenDto thirdRegister(UserForThirdRegisterDto dto, String appKey);

    /**
     * 第三方绑定（登陆后）
     * @param dto
     */
    void thirdBindByToken(UserForThirdBindDto dto);

    /**
     * 短信验证码（注册）
     * @param mobile
     * @return
     */
    void messageCodeForRegister(String mobile);

    /**
     * 短信验证码（ 忘记密码）
     * @param mobile
     * @return
     */
    void messageCode(String mobile);
    /**
     * 忘记密码
     * @param userForgetPasswordDto
     */
    void forgetPwd(UserResetPasswordDto userForgetPasswordDto);

    /**
     * 修改密码
     * @param userForUpdatePwdDto
     */
    void resetPwd(UserForUpdatePwdDto userForUpdatePwdDto);

    /**
     * 用户个人信息
     * @param mobile
     * @return
     */
    UserForInfoDto userInfo(String mobile);

    /**
     * 获取用户信息
     * @param openid
     * @return
     */
    UserForInfoDto getUserInfo(UserChargeCardOpenidDto openid);

    /**
     * 通过手机号查询用户
     * @param mobile
     * @return
     */
    User getUserByMobile(String mobile);


    /**
     * 更新用户信息
     * @param userForInfoDto
     * @return
     */
    boolean update(UserForInfoDto userForInfoDto);

    void messageCodeForBind(String mobile);

    /**
     * 绑定用户手机
     * @param bindMobileDto
     */
    JwtAuthenticationDto bindMobile(UserBindMobileDto bindMobileDto);


    void updateUsername(UserUpdateDto data);

    UserForDetailDto detail();

    User getUserByOpenid(String openid);

    void messageCodeForForgetPassword(String mobile);

    /**
     * 修改用户手机号
     * @param mobileDto
     * @return
     */
    boolean updateUserMobile(UserForUpdateMobileDto mobileDto);

    User getCurrentUser();

    void insertWxUser(Date ctime);

    void insertAppUser(Date ctime);

  /*  *//**
     * 获取机智云token
     *//*
    GizwitsTokenDto getGizwitsToken(User user);*/

    /**
     * 查询openid是否对应该用户
     */
    boolean isOpenidOwnedByUser(String openid, User user);

    TokenDto getGizwitsToken(GizwitsTokenDto tokenDto);

    void messageCodeForBindWx(String mobile);

    //=================START=====================//
    /**
     * 用户看板，统计app用户总数，昨日新增app用户数
     */
    StatUserWidgetVo getUserWidget();
    /**
     * 用户分布图，省级别
     */
    List<StatLocationVo>  ditribution();
    /**
     * 用户分布图，市级别
     */
    List<StatLocationVo> userDitributionByCity(String province);
    /**
     * 添加用户
     */
    Boolean add(UserAddDto userAddDto);
    /**
     * 删除用户
     */
    Boolean delete(List<Integer> ids);
    /**
     * 获取用户绑定的设备
     */
    Page<UserBindDeviceListVo> bindDeviceList(Pageable pageable);

    User getBindUser(String mac);
    /**
     * 发送验证码
     */
    Boolean sendCode(SendCodeDto sendCodeDto);
    /**
     * APP获取登陆用户的家庭
     */
    UserFamilyDto getUserFamily();

    Boolean updateUserFamily(UserFamilyUpdateDto dto);

//=================END=====================//
}
