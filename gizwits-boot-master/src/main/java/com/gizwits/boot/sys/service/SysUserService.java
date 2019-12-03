package com.gizwits.boot.sys.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.gizwits.boot.common.SysDeleteDto;
import com.gizwits.boot.dto.*;
import com.gizwits.boot.enums.SysUserShareDataEnum;
import com.gizwits.boot.enums.SysUserType;
import com.gizwits.boot.sys.entity.SysLoginDto;
import com.gizwits.boot.sys.entity.SysPermission;
import com.gizwits.boot.sys.entity.SysUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
public interface SysUserService extends IService<SysUser> {


    /**
     * 添加系统用户
     */
    Integer add(SysUserForAddDto sysUser);


    /**
     * 登陆
     */
    JwtAuthenticationDto login(LoginDto loginDto);

    JwtAuthenticationDto sysUserLogin(SysLoginDto loginDto);

    /**
     * 根据用户名查询
     */
    SysUser getSysUserByAccessToken(String accessToken);

    /**
     * 分页查询
     */
    Page<SysUserForListDto> getListByPage(Pageable<SysUserForQueryDto> pageable);

    /**
     * 查询用户权限
     *
     * @param userId
     */
    Map<String, String> getPermissionList(Integer userId);

    /**
     * 获取角色权限
     * @param roleId
     * @return
     */
    Map<String, String> getRolePermissionList(Integer roleId);;


    SysUser getCurrentUser();

    SysUser getCurrentUserOwner();

    /**
     * 获取用户的直接管理者
     * @param sysUser
     * @return
     */
    SysUser getSysUserOwner(SysUser sysUser);

    /**
     * 获取当前用户所属组织的所有用户ID列表,需要业务进行AOP处理
     * @param sysUser
     * @return
     */
    List<Integer> resolveOwnerAccessableUserIds(SysUser sysUser);

    /**
     * 获取当前用户以及下级用户ID列表
     * @param sysUser
     * @return
     */
    List<Integer> ResolveAccessibleByOwnerAndSubordinateUserIds(SysUser sysUser);

    /**
     * 获取用户的下一级所有管理者
     * @param sysUser
     * @return
     */
    List<Integer> resolveOwnerDirectAdminUserIds(SysUser sysUser);

    List<Integer> resolveSysUserAllSubIds(SysUser sysUser);

    List<Integer> resolveSysUserAllSubIds(SysUser sysUser, boolean isIncludeSelf);

    List<Integer> resolveSysUserAllSubAdminIds(SysUser sysUser);

    List<Integer> resolveSysUserAllSubAdminIds(SysUser sysUser, boolean isIncludeSelf);

    /**
     *
     * @param sysUser
     * @param includeParent 为true 返回上上级id
     * @return 根据当前登录用户，获取其所在的代理商帐号
     */
    List<Integer> resolveAccessableSuperiorIDds(SysUser sysUser, boolean includeParent);
    /**
     * 根据传递用户获取用户下所有的子用户包含自己
     * @param sysUser
     * @return
     */
    List<Integer> resolveSysUserAllSubIds(SysUser sysUser, boolean isIncludeSelf, boolean onlyAdmin);

    /**
     * 获取共享数据的上级用户
     */
    List<Integer> resolveShareDataSysUserIds(SysUser sysUser, SysUserShareDataEnum sysUserShareDataEnum);

    /**
     * 根据当前登录用户，获取其所在的厂商帐号或运营商帐号或代理商帐号
     */
    List<Integer> resolveAccessableUserIds(SysUser sysUer);


    List<Integer> resolveAccessableUserIds(SysUser sysUser, boolean includeParent, boolean includeChildren);


    List<Integer> resolveAccessableUserIds(SysUser sysUser, boolean includeParent);




    /**
     * 更新 SysUser 的 isAdmin 和 parentAdminId 字段
     */
    boolean updateSysUserAdmin(Integer id, SysUserType isAdmin, Integer parentAdminId);

    /**
     * 详情
     */
    SysUserForDetailDto detail(Integer id);

    /**
     * 更新
     */
    boolean update(SysUserForUpdateDto dto);

    /**
     * 根据角色id查找用户
     */
    List<SysUser> getUsersByRoleId(Integer roleId);

    boolean updateInfoPersonal(SysUserPersonalInfoUpdateDto data);

    boolean usernameExist(String username);

    boolean mobileExist(String mobile);

    List<SysPermission> getPermissionByUserId(Integer userId);

    String delete(List<Integer> ids);

    SysUser selectById(Integer id);

    /**
     * 获取系统账号基本信息
     *
     * @param id 账号id
     * @return 信息
     */
    SysUserForBasicDto basic(Integer id);

    /**
     * 忘记密码
     * @param dto
     * @return
     */
    boolean forgetPwd(SysUserForgetPasswordDto dto);

    boolean resolveAddMoreServiceMode(SysUser current);

    void resolveOwnerChildrenUserId(SysUser sysUser, Set<Integer> result);
    /**
     * 短信验证码
     * @param mobile
     */
    void JudgeByMessage(String mobile);

    /**
     * 邮箱验证码
     * @param email  收件人邮箱
     */
    void EmailByMessage(String email);



    /**
     * 修改头像和其他信息
     * @param file
     * @param userId
     * @param phone
     * @param realName
     * @param email
     * @return
     */
    boolean updateAvatar(MultipartFile file, Integer userId, String phone, String realName, String email);

    /**
     * 管理端个人中心
     * @return
     */
    ManageUserInfoDto userInfo();

    /**
     * 管理端修改手机号码
     * @param dto
     * @return
     */
    boolean updateMobile(ManageUserUpdateMobileDto dto);

    List<SysUser> getListBymobile(String mobile);

    List<Integer> resolveParentUserIds(Integer sysUserId);
}
