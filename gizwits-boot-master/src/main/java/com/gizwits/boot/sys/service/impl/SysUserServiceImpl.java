package com.gizwits.boot.sys.service.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.api.MailApi;
import com.gizwits.boot.api.SmsApi;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.common.MessageCodeConfig;
import com.gizwits.boot.common.SysDeleteDto;
import com.gizwits.boot.common.WebCommonConfig;
import com.gizwits.boot.dto.*;
import com.gizwits.boot.enums.*;
import com.gizwits.boot.event.SysUserCreatedEvent;
import com.gizwits.boot.event.SysUserDeteledEvent;
import com.gizwits.boot.event.SysUserExtUpdatedEvent;
import com.gizwits.boot.event.SysUserNameModifyEvent;
import com.gizwits.boot.exceptions.SysExceptionEnum;
import com.gizwits.boot.exceptions.SystemException;
import com.gizwits.boot.sys.check.SysUserCheckerDto;
import com.gizwits.boot.sys.check.SysUserForAddChecker;
import com.gizwits.boot.sys.check.SysUserForUpdateChecker;
import com.gizwits.boot.sys.dao.SysUserDao;
import com.gizwits.boot.sys.entity.*;
import com.gizwits.boot.sys.service.*;
import com.gizwits.boot.utils.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.gizwits.boot.utils.CommonEventPublisherUtils.publishEvent;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger("SYS_LOGGER_APPENDER");

    @Autowired
    private SysUserToRoleService sysUserToRoleService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysRoleToPermissionService sysRoleToPermissionService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserExtService sysUserExtService;

    @Autowired
    private SimpleCacheManager cacheManager;

    @Autowired
    private SysUserDeleteService sysUserDeleteService;

    @Autowired
    private SysUserShareDataService sysUserShareDataService;

    @Autowired
    private List<SysUserForAddChecker> checkers = new ArrayList<>();

    @Autowired
    private List<SysUserForUpdateChecker> updateCheckers = new ArrayList<>();

    private static Map<String, String> map = new ConcurrentHashMap<>();

    @Override
    public Integer add(SysUserForAddDto dto) {
        preAdd(dto);
        SysUser sysUser = addSysUser(dto);
        LOGGER.info("add sysuser {} isAdmin {} parentAdminId {}", sysUser.getId(), sysUser.getIsAdmin(), sysUser.getParentAdminId());
        addSysUserToRole(sysUser, dto);
        addSysUserExt(sysUser, dto);
        publishEvent(new SysUserCreatedEvent(sysUser, dto.getRoleIds()));
        return sysUser.getId();
    }

    @Override
    public JwtAuthenticationDto login(com.gizwits.boot.dto.LoginDto loginDto) {
        EntityWrapper<SysUser> wrapper = new EntityWrapper<SysUser>();
        SysUser sysUser = new SysUser();
        sysUser.setUsername(loginDto.getUsername());
        sysUser.setIsDeleted(DeleteStatus.NOT_DELETED.getCode());
        wrapper.setEntity(sysUser);
        sysUser = selectOne(wrapper);
        if (ParamUtil.isNullOrEmptyOrZero(sysUser)) {
            throw new SystemException(SysExceptionEnum.USER_NOT_EXIST.getCode(), SysExceptionEnum.USER_NOT_EXIST.getMessage());
        }
        if (Objects.equals(sysUser.getIsEnable(), SysUserStatus.DISABLE.getCode())) {
            throw new SystemException(SysExceptionEnum.USER_DISABLED.getCode(), SysExceptionEnum.USER_DISABLED.getMessage());
        }
        String md5Pass = sysUser.getPassword();
        if (!PasswordUtil.verify(loginDto.getPassword(), md5Pass)) {
            throw new SystemException(SysExceptionEnum.ILLEGAL_USER.getCode(), SysExceptionEnum.ILLEGAL_USER.getMessage());
        }
        String accessToken = UUID.randomUUID().toString();
        Cache cache = cacheManager.getCache(Constants.ACCESS_TOKEN_CACHE_NAME);
        cache.put(accessToken, sysUser);
        return new JwtAuthenticationDto(accessToken);
    }

    @Override
    public JwtAuthenticationDto sysUserLogin(SysLoginDto userLoginDto) {
        String mobile = userLoginDto.getMobile();
        LOGGER.info("登录手机号：" + mobile);
        SysUser user = selectOne(new EntityWrapper<SysUser>().eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("mobile", mobile).eq("is_enable", 1));
        if (Objects.isNull(user)) {
            throw new SystemException(SysExceptionEnum.USER_NOT_EXIST.getCode(), SysExceptionEnum.USER_NOT_EXIST.getMessage());
        }
        if (!PasswordUtil.verify(userLoginDto.getPassword(), user.getPassword())) {
            throw new SystemException(SysExceptionEnum.ILLEGAL_USER.getCode(), SysExceptionEnum.ILLEGAL_USER.getMessage());
        }
        String accessToken = UUID.randomUUID().toString();
        Cache cache = cacheManager.getCache(Constants.ACCESS_TOKEN_CACHE_NAME);
        cache.put(accessToken, user);
        return new JwtAuthenticationDto(accessToken);
    }

    @Override
    public SysUser getSysUserByAccessToken(String accessToken) {
        if (ParamUtil.isNullOrEmptyOrZero(accessToken)) {
            throw new SystemException(SysExceptionEnum.NO_LOGIN.getCode(), SysExceptionEnum.NO_LOGIN.getMessage());
        }
        Cache cache = cacheManager.getCache(Constants.ACCESS_TOKEN_CACHE_NAME);
        SysUser sysUser = cache.get(accessToken, SysUser.class);
        if (Objects.isNull(sysUser)) {
            throw new SystemException(SysExceptionEnum.NO_LOGIN.getCode(), SysExceptionEnum.NO_LOGIN.getMessage());
        }
        return sysUser;
    }

    @Override
    public Page<SysUserForListDto> getListByPage(Pageable<SysUserForQueryDto> pageable) {
        if (Objects.isNull(pageable.getQuery())) {
            pageable.setQuery(new SysUserForQueryDto());
        }
        //设置条件
        Page<SysUser> page = new Page<SysUser>();
        BeanUtils.copyProperties(pageable, page);
        Wrapper<SysUser> wrapper = new EntityWrapper<>();
        wrapper.in("sys_user_id", resolveOwnerAccessableUserIds(getCurrentUserOwner()))
                .eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        wrapper.orderBy("ctime", false);
        Page<SysUser> selectPage = selectPage(page, QueryResolverUtils.parse(pageable.getQuery(), wrapper));
        Page<SysUserForListDto> result = new Page<>();
        BeanUtils.copyProperties(selectPage, result);
        result.setRecords(new LinkedList<>());
        selectPage.getRecords().forEach(user -> {
            SysUserForListDto dto = new SysUserForListDto(user);
            List<SysRole> list = sysRoleService.getRolesByUserId(dto.getId());
            if (CollectionUtils.isNotEmpty(list)) {
                dto.setRoleName(list.get(0).getRoleName());
            }
            dto.setStatusDesc(SysUserStatus.getDesc(user.getIsEnable()));
            result.getRecords().add(dto);
        });
        return result;
    }

    @Override
    public Map<String, String> getPermissionList(Integer userId) {
        Map<String, String> permissionMap = new HashMap<>();

        List<Integer> permissionIds = sysUserToRoleService.getPermissionsByUserId(userId);

        if (CollectionUtils.isNotEmpty(permissionIds)) {
            //根据权限ID查询确切权限
            List<SysPermission> sysPermissionList = sysPermissionService.selectBatchIds(permissionIds);
            if (CollectionUtils.isNotEmpty(sysPermissionList)) {
                sysPermissionList.forEach(item -> permissionMap.put(item.getUri(), item.getUri()));
            }
        }

        return permissionMap;
    }

    @Override
    public Map<String, String> getRolePermissionList(Integer roleId) {
        Map<String, String> permissionMap = new HashMap<>();
        Set<Integer> permissionIds = new HashSet<>();

        List<SysRoleToPermission> sysRoleToPermissions = sysRoleToPermissionService.getSysRoleToPermissionListByRoles(Arrays.asList(roleId));
        if (CollectionUtils.isNotEmpty(sysRoleToPermissions)) {
            permissionIds.addAll(sysRoleToPermissions.stream().map(SysRoleToPermission::getPermissionId).collect(Collectors.toList()));
        }
        if (CollectionUtils.isNotEmpty(permissionIds)) {
            //根据权限ID查询确切权限
            List<SysPermission> sysPermissionList = sysPermissionService.selectBatchIds(new ArrayList<>(permissionIds));
            if (CollectionUtils.isNotEmpty(sysPermissionList)) {
                sysPermissionList.forEach(item -> permissionMap.put(item.getUri(), item.getUri()));
            }
        }

        return permissionMap;
    }

    @Override
    public SysUser getCurrentUser() {
        return getSysUserByAccessToken(WebUtils.getHeader(Constants.TOKEN_HEADER_NAME));
    }

    @Override
    public SysUser getCurrentUserOwner() {
        SysUser currentUser = getCurrentUser();
        if (Objects.isNull(currentUser)) {
            LOGGER.warn("====无法获取当前登录用户===");
            return null;
        }
        if (!SysUserType.NORMAL.getCode().equals(currentUser.getIsAdmin())) {
            LOGGER.debug("===当前登录用户{},{}为管理员===", currentUser.getId(), currentUser.getUsername());
            return currentUser;
        }
        if (!ParamUtil.isNullOrEmptyOrZero(currentUser.getParentAdminId())) {
            LOGGER.debug("====当前登录用户{},{},不是管理员,返回用户所属的管理员{}====", currentUser.getId(), currentUser.getUsername(), currentUser.getParentAdminId());
            return selectById(currentUser.getParentAdminId());
        }
        return currentUser;
    }

    /**
     * 增加此方法是为了做AOP
     * 获取用户的直接管理者
     *
     * @param sysUser
     * @return
     */
    @Override
    public SysUser getSysUserOwner(SysUser sysUser) {
        SysUser adminUser = sysUser;
        if (SysUserType.NORMAL.getCode().equals(sysUser.getIsAdmin())) {
            LOGGER.debug("====当前参数用户{},{}不是管理员,查询参数用户所属的管理员====", sysUser.getId(), sysUser.getUsername());
            if (ParamUtil.isNullOrEmptyOrZero(sysUser.getParentAdminId())) {
                LOGGER.warn("===当前参数用户{},{}的管理员ID为空,无法获取所属层级管理员用户===", sysUser.getId(), sysUser.getUsername());
                return null;
            }
            adminUser = selectById(sysUser.getParentAdminId());
            if (Objects.isNull(adminUser)) {
                LOGGER.error("===根据当前参数用户{},{}的父级管理员ID{}无法获取用户====", sysUser.getId(), sysUser.getUsername(), sysUser.getParentAdminId());
                return null;
            }
        }
        return adminUser;
    }

    /**
     * 获取当前用户所属的组织层级中所有的用户ID,包括管理员
     *
     * @param sysUser
     * @return
     */
    @Override
    public List<Integer> resolveOwnerAccessableUserIds(SysUser sysUser) {
        SysUser adminUser = getSysUserOwner(sysUser);

        Set<Integer> result = new HashSet<>();
        result.add(adminUser.getId());
        EntityWrapper<SysUser> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("parent_admin_id", adminUser.getId()).eq("is_admin", SysUserType.NORMAL.getCode()).eq("is_deleted", 0);
        List<SysUser> list = selectList(entityWrapper);
        if (adminUser.getIsAdmin()>=3){
            result.add(adminUser.getSysUserId());
        }
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(item -> {
                result.add(item.getId());
                if (adminUser.getIsAdmin()==4){
                    result.add(item.getSysUserId());
                }
            });
        }
        LOGGER.debug("====根据参数用户{},{}获取本层级的所有系统用户{}", sysUser.getId(), sysUser.getUsername(), result.toArray());
        return new ArrayList<>(result);
    }


    /**
     * 获取当前用户以及下级用户ID列表
     *
     * @param sysUser
     * @return
     */
    @Override
    public List<Integer> ResolveAccessibleByOwnerAndSubordinateUserIds(SysUser sysUser) {
        SysUser adminUser = getSysUserOwner(sysUser);

        Set<Integer> result = new HashSet<>();
        result.add(adminUser.getId());
        EntityWrapper<SysUser> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("parent_admin_id", adminUser.getId()).eq("is_admin", SysUserType.NORMAL.getCode()).eq("is_deleted", 0);
        List<SysUser> list = selectList(entityWrapper);

        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(item -> {
                result.add(item.getId());
            });
        }
        LOGGER.debug("====根据参数用户{},{}获取本层级的所有系统用户{}", sysUser.getId(), sysUser.getUsername(), result.toArray());
        return new ArrayList<>(result);
    }

    /**
     * 获取用户的下一级所有管理者
     *
     * @param sysUser
     * @return
     */
    @Override
    public List<Integer> resolveOwnerDirectAdminUserIds(SysUser sysUser) {
        SysUser adminUser = getSysUserOwner(sysUser);

        Set<Integer> result = new HashSet<>();
        result.add(adminUser.getId());
        EntityWrapper<SysUser> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("parent_admin_id", adminUser.getId()).ne("is_admin", SysUserType.NORMAL.getCode()).eq("is_deleted", 0);
        List<SysUser> list = selectList(entityWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(item -> {
                result.add(item.getId());
            });
        }
        LOGGER.debug("====根据参数用户{},{}获取下一层级的所有管理用户{}", sysUser.getId(), sysUser.getUsername(), result.toArray());
        return new ArrayList<>(result);
    }

    @Override
    public List<Integer> resolveSysUserAllSubIds(SysUser sysUser) {
        return resolveSysUserAllSubIds(sysUser, true, false);
    }

    @Override
    public List<Integer> resolveSysUserAllSubIds(SysUser sysUser, boolean isIncludeSelf) {
        return resolveSysUserAllSubIds(sysUser, isIncludeSelf, false);
    }

    @Override
    public List<Integer> resolveSysUserAllSubAdminIds(SysUser sysUser) {
        return resolveSysUserAllSubIds(sysUser, true, true);
    }

    @Override
    public List<Integer> resolveSysUserAllSubAdminIds(SysUser sysUser, boolean isIncludeSelf) {
        return resolveSysUserAllSubIds(sysUser, isIncludeSelf, true);
    }


    /**
     * 获取用户下所有的子孙用户,包括自己
     *
     * @param sysUser
     * @return
     */
    @Override
    public List<Integer> resolveSysUserAllSubIds(SysUser sysUser, boolean isIncludeSelf, boolean onlyAdmin) {
        if (Objects.isNull(sysUser)) {
            LOGGER.error("====resolveSysUserAllSubIds 参数为空===");
            return null;
        }
        String userTreePath = sysUser.getTreePath() + sysUser.getId() + ","; /** @see SysUserServiceImpl#buildTreePath(SysUser, SysUser) */
        EntityWrapper<SysUser> entityWrapper = new EntityWrapper<>();
        entityWrapper.like("tree_path", userTreePath, SqlLike.RIGHT).eq("is_deleted", 0);
        if (onlyAdmin) {
            entityWrapper.ne("is_admin", SysUserType.NORMAL.getCode());
        }

        List<Integer> result = new ArrayList<>();
        if (isIncludeSelf) {
            result.add(sysUser.getId());
        }
        List<SysUser> list = selectList(entityWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(item -> {
                result.add(item.getId());
            });
        }
        LOGGER.debug("====根据参数用户{},{}的treePath:{}获取到的用户为:{}==", sysUser.getId(), sysUser.getUsername(), userTreePath, result.toArray());
        return result;
    }

    @Override
    public List<Integer> resolveShareDataSysUserIds(SysUser sysUser, SysUserShareDataEnum shareDataEnum) {
        List<Integer> sysUserIds = new ArrayList<>();
        SysUser userOwner = getSysUserOwner(sysUser);
        SysUser parentAdmin = selectById(userOwner.getParentAdminId());
        while (parentAdmin != null) {
            if (sysUserShareDataService.isSysUserShareData(parentAdmin, shareDataEnum)) {
                sysUserIds.addAll(resolveOwnerAccessableUserIds(parentAdmin));
            }
            parentAdmin = selectById(parentAdmin.getParentAdminId());
        }
        return sysUserIds;
    }

    @Override
    @Deprecated
    public List<Integer> resolveAccessableUserIds(SysUser sysUser) {
        return resolveAccessableUserIds(sysUser, true, true);
    }

    @Override
    @Deprecated
    public List<Integer> resolveAccessableUserIds(SysUser sysUser, boolean includeParent, boolean includeChildren) {
        Set<Integer> result = new HashSet<>();
        if (includeParent) {
            resolveParentUserId(sysUser, result);
        }
        if (includeChildren) {
            resolveChildrenUserId(sysUser, result);
        }
        if (!includeChildren) {
            result.add(sysUser.getId());
        }
        return new ArrayList<>(result);
    }

    @Override
    @Deprecated
    public List<Integer> resolveAccessableUserIds(SysUser sysUser, boolean includeParent) {
        Set<Integer> result = new HashSet<>();
        if (includeParent) {
            resolveServiceModeParentUserId(sysUser, result);
        }
        return new ArrayList<>(result);
    }

    @Override
    public List<Integer> resolveAccessableSuperiorIDds(SysUser sysUser, boolean includeParent) {
        Set<Integer> result = new HashSet<>();
        result.add(sysUser.getId());
        result.add(sysUser.getParentAdminId());
        if (includeParent) {
            result.add(selectById(sysUser.getSysUserId()).getSysUserId());
        }
        return new ArrayList<>(result);
    }

    @Override
    public boolean updateSysUserAdmin(Integer id, SysUserType isAdmin, Integer parentAdminId) {
        LOGGER.info("update sysuser {} isAdmin {} parentAdminId {}", id, isAdmin, parentAdminId);
        SysUser sysUserForUpdate = new SysUser();
        sysUserForUpdate.setId(id);
        sysUserForUpdate.setIsAdmin(isAdmin.getCode());
        sysUserForUpdate.setParentAdminId(parentAdminId);
        return updateById(sysUserForUpdate);
    }

    @Override
    public SysUserForDetailDto detail(Integer id) {
        SysUser sysUser = selectById(id);
        if (Objects.isNull(sysUser)) {
            return null;
        }
        SysUserForDetailDto result = new SysUserForDetailDto(sysUser);
        result.setStatusDesc(SysUserStatus.getDesc(result.getIsEnable()));
        result.setRoleIds(sysUserToRoleService.selectList(new EntityWrapper<SysUserToRole>().eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("user_id", result.getId())).stream().map(SysUserToRole::getRoleId).collect(Collectors.toList()));
        result.setShareBenefitType(sysRoleService.selectList(new EntityWrapper<SysRole>().eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).in("id", result.getRoleIds())).stream().map(SysRole::getShareBenefitType).collect(Collectors.toList()));
        if (StringUtils.isBlank(result.getSysName())) {
            List<Integer> parentIds =
                    Arrays.stream(sysUser.getTreePath().split(",")).filter(StringUtils::isNotEmpty).map(Integer::valueOf).collect(Collectors.toList());
            if (!parentIds.isEmpty()) {
                Map<String, String> systemNameAndLogo = baseMapper.getSystemNameAndLogo(parentIds);
                if (systemNameAndLogo != null && !systemNameAndLogo.isEmpty()) {
                    result.setSysName(systemNameAndLogo.get("sysName"));
                    result.setSysLogo(systemNameAndLogo.get("sysLogo"));
                }
            }
        }
//        boolean contains = result.getRoleIds().contains(sysRoleService.getSuperManagerRoleId());
        result.setIsAdmin(result.getRoleIds().get(0));
        fillSysUserExt(result);

        result.setShareData(sysUserShareDataService.getSysUserShareData(getSysUserOwner(sysUser)));
        if (getSysUserOwner(sysUser).getIsAdmin().equals(SysUserType.MANUFACTURER.getCode())) {
            result.setBelongManufacturer(true);
        }
        return result;
    }

    @Override
    public boolean update(SysUserForUpdateDto dto) {
        //更新SysUser
        //preUpdate(dto);

        /*if (StringUtils.isNotBlank(dto.getMobile()) && mobileExist(dto.getMobile())){
            throw new SystemException(SysExceptionEnum.DUP_PHONE_NUMBER.getCode(), SysExceptionEnum.DUP_PHONE_NUMBER.getMessage());
        }*/

        SysUser sysUser = updateSysUser(dto);
        if (CollectionUtils.isNotEmpty(dto.getRoleIds())) {
            updateUserToRole(dto, sysUser);
        }
        updateSysUserExt(dto);
        return true;
    }

    @Override
    public List<SysUser> getUsersByRoleId(Integer roleId) {
        List<Integer> userIds = sysUserToRoleService.selectList(new EntityWrapper<SysUserToRole>().eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("role_id", roleId))
                .stream().map(SysUserToRole::getUserId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(userIds)) {
            return selectBatchIds(userIds);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean updateInfoPersonal(SysUserPersonalInfoUpdateDto dto) {
        //更新个人设置
        SysUser current = getCurrentUser();
        String oldName = current.getNickName();
        current.setUtime(new Date());
        current.setNickName(dto.getNickName());
        current.setEmail(dto.getEmail());
        current.setSysName(dto.getSysName());
        current.setSysLogo(dto.getSysLogo());
        updateAllColumnById(current);
        if (!ParamUtil.isNullOrEmptyOrZero(dto.getNickName()) && (ParamUtil.isNullOrEmptyOrZero(oldName) || !oldName.equals(dto.getNickName()))) {
            SysUserNameModifyEvent<Integer> sysUserNameModifyEvent = new SysUserNameModifyEvent<Integer>(current, current.getId(), oldName, dto.getNickName());
            CommonEventPublisherUtils.publishEvent(sysUserNameModifyEvent);
        } else if (ParamUtil.isNullOrEmptyOrZero(dto.getNickName())) {
            //如果昵称为空，就用username代替nickname
            SysUserNameModifyEvent<Integer> sysUserNameModifyEvent = new SysUserNameModifyEvent<Integer>(current, current.getId(), oldName, current.getUsername());
            CommonEventPublisherUtils.publishEvent(sysUserNameModifyEvent);
        }
        updateSysUserExt(current.getId(), dto.getExt());
        sysUserShareDataService.updateSysUserShareData(getCurrentUserOwner(), dto.getShareData());
        return true;
    }

    @Override
    public boolean usernameExist(String username) {
        return exist("username", username);
    }

    @Override
    public boolean mobileExist(String mobile) {
        return exist("mobile", mobile);
    }

    private boolean isMobileOwnedByOtherUser(String mobile, Integer userId) {
        Wrapper<SysUser> wrapper = new EntityWrapper();
        wrapper.eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("mobile", mobile).ne("id", userId);
        return selectCount(wrapper) > 0;
    }

    @Override
    public List<SysPermission> getPermissionByUserId(Integer userId) {
        List<Integer> permissionIds = sysUserToRoleService.getPermissionsByUserId(userId);
        if (CollectionUtils.isNotEmpty(permissionIds)) {
            return sysPermissionService.selectList(new EntityWrapper<SysPermission>().in("id", permissionIds).eq("is_deleted", 0));
        }
        return Collections.emptyList();
    }

    @Override
    public String delete(List<Integer> ids) {
        List<String> fails = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        if (CollectionUtils.isNotEmpty(ids)) {
            for (Integer id : ids) {
                SysUser sysUser = selectById(id);
                if (!ParamUtil.isNullOrEmptyOrZero(sysUser)) {
                    if (sysUserDeleteService == null || sysUserDeleteService.beforeDelete(id)) {
                        //1.删除用户与角色关联记录
                        SysUserToRole sysUserToRole = new SysUserToRole();
                        sysUserToRole.setUtime(new Date());
                        sysUserToRole.setIsDeleted(DeleteStatus.DELETED.getCode());
                        sysUserToRoleService.update(sysUserToRole, new EntityWrapper<SysUserToRole>().eq("is_deleted", 0).eq("user_id", id));
                        LOGGER.info("删除用户{}与角色的关联记录", id);
                        //2.删除用户扩展表
                        SysUserExt sysUserExt = new SysUserExt();
                        sysUserExt.setUtime(new Date());
                        sysUserExt.setIsDeleted(DeleteStatus.DELETED.getCode());
                        sysUserExtService.update(sysUserExt, new EntityWrapper<SysUserExt>().eq("is_deleted", 0).eq("sys_user_id", id));
                        LOGGER.info("删除用户{}扩展表", id);
                        //3.删除用户
                        sysUser.setUtime(new Date());
                        sysUser.setIsDeleted(DeleteStatus.DELETED.getCode());
                        updateById(sysUser);
                        LOGGER.info("删除用户{}", id);
                    } else {
                        fails.add(sysUser.getUsername());
                    }
                }
            }
            //4.发布删除事件
            publishEvent(new SysUserDeteledEvent(ids));
            switch (fails.size()) {
                case 0:
                    sb.append("删除成功");
                    break;
                case 1:
                    sb.append("您欲删除的账号[" + fails.get(0) + "]存在下级or设备，请先解绑与其的关系。");
                    break;
                case 2:
                    sb.append("您欲删除的账号[" + fails.get(0) + "],[" + fails.get(1) + "]存在下级or设备，请先解绑与其的关系。");
                    break;
                default:
                    sb.append("您欲删除的账号[" + fails.get(0) + "],[" + fails.get(1) + "]等存在下级or设备，请先解绑与其的关系。");
                    break;
            }

        }
        return sb.toString();
    }


    @Override
    public SysUser selectById(Integer id) {
        return selectOne(new EntityWrapper<SysUser>().eq("id", id).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
    }

    @Override
    public SysUserForBasicDto basic(Integer id) {
        SysUser sysUser = selectById(id);
        if (Objects.isNull(sysUser)) {
            return null;
        }
        SysUserForBasicDto basic = new SysUserForBasicDto(sysUser);
        List<SysRole> roles = sysRoleService.getRolesByUserId(sysUser.getId());
        if (CollectionUtils.isNotEmpty(roles) && Objects.nonNull(roles.get(0))) {
            basic.setRoleName(roles.get(0).getRoleName());
            basic.setShareBenefitType(roles.get(0).getShareBenefitType());
        }
        SysUserExt ext = sysUserExtService.selectById(id);
        if (Objects.nonNull(ext)) {
            basic.setExt(new SysUserExtForAddDto(ext));
        }
        return basic;
    }

    private void preUpdate(SysUserForUpdateDto dto) {
        SysUserCheckerDto checkerDto = new SysUserCheckerDto(dto);
        updateCheckers.forEach(item -> item.check(checkerDto));
    }

    private boolean exist(String field, Object value) {
        return selectCount(new EntityWrapper<SysUser>().eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq(field, value)) > 0;
    }

    private void preAdd(SysUserForAddDto dto) {
        SysUserCheckerDto checkerDto = new SysUserCheckerDto(dto);
        checkers.forEach(checker -> checker.check(checkerDto));
    }


    @Override
    public void resolveOwnerChildrenUserId(SysUser sysUser, Set<Integer> result) {
        if (Objects.nonNull(sysUser)) {
            result.add(sysUser.getId());
            List<SysUser> children = selectList(new EntityWrapper<SysUser>().eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("sys_user_id", sysUser.getId()).ne("id", sysUser.getId()));
            if (CollectionUtils.isNotEmpty(children)) {
                children.forEach(child -> resolveOwnerChildrenUserId(child, result));
            }
        }
    }

    private void resolveChildrenUserId(SysUser sysUser, Set<Integer> result) {
        if (Objects.nonNull(sysUser)) {
            result.add(sysUser.getId());
            List<SysUser> children = selectList(new EntityWrapper<SysUser>().eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("sys_user_id", sysUser.getId()).ne("id", sysUser.getId()));
            if (CollectionUtils.isNotEmpty(children)) {
                children.forEach(child -> resolveChildrenUserId(child, result));
            }
        }
    }

    private void resolveParentUserId(SysUser current, Set<Integer> result) {
        //非顶级
        if (Objects.nonNull(current) && !Objects.equals(current.getId(), current.getSysUserId())) {
            Set<Integer> roleIds = sysUserToRoleService.selectList(new EntityWrapper<SysUserToRole>().eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("user_id", current.getId()))
                    .stream().map(SysUserToRole::getRoleId).collect(Collectors.toSet());
            if (CollectionUtils.isNotEmpty(roleIds)) {
                List<SysRole> roles = sysRoleService.selectBatchIds(new ArrayList<>(roleIds));
                long count = roles.stream().filter(role -> Objects.equals(ShareType.SHARE.getCode(), role.getIsShareData())).count();
                if (count > 0) {
                    result.add(current.getSysUserId());
                    //递归查找parent
                    resolveParentUserId(selectById(current.getSysUserId()), result);
                }
            }
        }
    }

    private void resolveServiceModeParentUserId(SysUser current, Set<Integer> result) {
        //非顶级
        if (Objects.nonNull(current) && !Objects.equals(current.getId(), current.getSysUserId())) {
            Set<Integer> roleIds = sysUserToRoleService.selectList(new EntityWrapper<SysUserToRole>().eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("user_id", current.getId()))
                    .stream().map(SysUserToRole::getRoleId).collect(Collectors.toSet());
            if (CollectionUtils.isNotEmpty(roleIds)) {
                List<SysRole> roles = sysRoleService.selectBatchIds(new ArrayList<>(roleIds));
                long count = roles.stream().filter(role -> Objects.equals(ShareType.SHARE.getCode(), role.getIsShareServiceMode())).count();
                if (count > 0) {
                    result.add(current.getSysUserId());
                    //递归查找parent
                    resolveParentUserId(selectById(current.getSysUserId()), result);
                }
            }
        }
    }

    @Override
    public boolean resolveAddMoreServiceMode(SysUser current) {
        Set<Integer> roleIds = sysUserToRoleService.selectList(new EntityWrapper<SysUserToRole>().eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("user_id", current.getId()))
                .stream().map(SysUserToRole::getRoleId).collect(Collectors.toSet());
        if (CollectionUtils.isNotEmpty(roleIds)) {
            List<SysRole> roles = sysRoleService.selectBatchIds(new ArrayList<>(roleIds));
            long count = roles.stream().filter(role -> Objects.equals(ShareType.SHARE.getCode(), role.getIsAddMoreServiceMode())).count();
            if (count > 0) {
                return true;
            }
        }
        return false;
    }

    private void updateSysUserExt(SysUserForUpdateDto dto) {
        updateSysUserExt(dto.getId(), dto.getExt());
    }

    private void updateSysUserExt(Integer id, SysUserExtForAddDto extDto) {
        if (Objects.nonNull(extDto)) {
            SysUserExt ext = sysUserExtService.selectById(id);
            if (Objects.isNull(ext)) {
                SysUser current = getCurrentUser();
                ext = new SysUserExt();
                ext.setSysUserId(current.getId());
                ext.setSysUserName(current.getUsername());
                ext.setUtime(new Date());
            }
            BeanUtils.copyProperties(extDto, ext/*, findNullOrEmptyFieldNameArray(extDto)*/);
            sysUserExtService.update(ext);
            //因为是实时打款所以无需更新分润单信息
//            SysUserNameModifyEvent<Integer> sysUserNameModifyEvent = new SysUserNameModifyEvent<Integer>(ext, ext.getSysUserId(), extDto.getReceiverWxName(), extDto.getReceiverOpenId(),extDto.getAlipayAccount(),ext.getAlipayAccountName(),extDto.getShareBenefitType());
//            CommonEventPublisherUtils.publishEvent(sysUserNameModifyEvent);

            CommonEventPublisherUtils.publishEvent(new SysUserExtUpdatedEvent(ext));
        }
    }

    private String[] findNullOrEmptyFieldNameArray(SysUserExtForAddDto extDto) {
        if (extDto == null) {
            return null;
        }

        Field[] fields = extDto.getClass().getDeclaredFields();
        List<String> nullOrEmptyName = new LinkedList<>();
        for (Field field : fields) {
            Object fieldValue = getFieldValue(field, extDto);

            if (fieldValue == null) {
                nullOrEmptyName.add(field.getName());
                continue;
            }

            if (fieldValue instanceof String && StringUtils.isBlank((String) fieldValue)) {
                nullOrEmptyName.add(field.getName());
                continue;
            }
        }

        String[] names = new String[nullOrEmptyName.size()];

        nullOrEmptyName.toArray(names);

        return names;
    }

    private Object getFieldValue(Field field, Object target) {
        field.setAccessible(true);

        try {
            return field.get(target);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    private SysUser updateSysUser(SysUserForUpdateDto dto) {
        SysUser sysUser = selectById(dto.getId());
        String oldName = sysUser.getNickName();
        BeanUtils.copyProperties(dto, sysUser);
        if (!sysUser.getMobile().equals(dto.getMobile()) && mobileExist(dto.getMobile())) {
            throw new SystemException(SysExceptionEnum.DUP_PHONE_NUMBER.getCode(), SysExceptionEnum.DUP_PHONE_NUMBER.getMessage());
        }
        if (!ParamUtil.isNullOrEmptyOrZero(dto.getNickName()) && (ParamUtil.isNullOrEmptyOrZero(oldName) || !oldName.equals(dto.getNickName()))) {
            SysUserNameModifyEvent<Integer> sysUserNameModifyEvent = new SysUserNameModifyEvent<Integer>(sysUser, sysUser.getId(), oldName, dto.getNickName());
            CommonEventPublisherUtils.publishEvent(sysUserNameModifyEvent);
        } else if (ParamUtil.isNullOrEmptyOrZero(dto.getNickName())) {
            SysUserNameModifyEvent<Integer> sysUserNameModifyEvent = new SysUserNameModifyEvent<Integer>(sysUser, sysUser.getId(), oldName, sysUser.getUsername());
            CommonEventPublisherUtils.publishEvent(sysUserNameModifyEvent);
        }
        //sysUser.setPassword(PasswordUtil.generate(dto.getPassword()));
        sysUser.setUtime(new Date());
        updateAllColumnById(sysUser);
        return sysUser;
    }

    private void updateUserToRole(SysUserForUpdateDto dto, SysUser sysUser) {
        //更新SysUserToRole
        Set<Integer> fromDb = sysUserToRoleService.selectList(new EntityWrapper<SysUserToRole>().eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("user_id", sysUser.getId()))
                .stream().map(SysUserToRole::getRoleId).collect(Collectors.toSet());
        List<Integer> fromFront = CollectionUtils.isNotEmpty(dto.getRoleIds()) ? dto.getRoleIds() : Collections.emptyList();
        List<Integer> toDelIds = fromDb.stream().filter(item -> !fromFront.contains(item)).collect(Collectors.toList());
        List<Integer> toAddIds = fromFront.stream().filter(item -> !fromDb.contains(item)).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(toDelIds)) {
            sysUserToRoleService.delete(new EntityWrapper<SysUserToRole>().eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("user_id", sysUser.getId()).in("role_id", toDelIds));
        }
        if (CollectionUtils.isNotEmpty(toAddIds)) {
            List<SysUserToRole> list = new LinkedList<>();
            toAddIds.forEach(id -> {
                SysUserToRole tmp = new SysUserToRole();
                tmp.setUserId(sysUser.getId());
                tmp.setCtime(new Date());
                tmp.setUtime(new Date());
                tmp.setSysUserId(sysUser.getSysUserId());
                tmp.setSysUserName(sysUser.getSysUserName());
                tmp.setRoleId(id);
                list.add(tmp);
            });
            sysUserToRoleService.insertBatch(list);
        }
    }

    private void fillSysUserExt(SysUserForDetailDto result) {
        SysUserExt sysUserExt = sysUserExtService.selectById(result.getId());
        if (Objects.nonNull(sysUserExt)) {
            String shareBenefitPayType = SysConfigUtils.get(WebCommonConfig.class).getShareBenefitPayType();
            SysUserExtForAddDto detailDto = new SysUserExtForAddDto(sysUserExt);
            detailDto.setShareBenefitType(shareBenefitPayType);
            result.setExt(detailDto);
        }
    }

    private void addSysUserExt(SysUser sysUser, SysUserForAddDto dto) {
        SysUserExtForAddDto ext = dto.getExt();
        if (Objects.isNull(ext)) {
            ext = new SysUserExtForAddDto();
        }
        SysUserExt sysUserExt = new SysUserExt();
        BeanUtils.copyProperties(ext, sysUserExt);
        sysUserExt.setCtime(new Date());
        sysUserExt.setUtime(new Date());
        sysUserExt.setSysUserId(sysUser.getId());
        sysUserExt.setSysUserName(sysUser.getUsername());
        sysUserExtService.insert(sysUserExt);
    }

    private void buildTreePath(SysUser sysUser, SysUser current) {
        sysUser.setTreePath(current.getTreePath() + current.getId() + ",");
    }

    private void addSysUserToRole(SysUser sysUser, SysUserForAddDto dto) {
        if (CollectionUtils.isEmpty(dto.getRoleIds())) {
            return;
        }
        List<SysUserToRole> sysUserToRoles = new LinkedList<>();
        SysUser current = getCurrentUser();
        dto.getRoleIds().forEach(roleId -> {
            SysUserToRole tmp = new SysUserToRole();
            tmp.setCtime(new Date());
            tmp.setUtime(tmp.getCtime());
            tmp.setRoleId(roleId);
            tmp.setUserId(sysUser.getId());
            tmp.setSysUserId(current.getId());
            tmp.setSysUserName(current.getSysUserName());
            sysUserToRoles.add(tmp);
        });
        sysUserToRoleService.insertBatch(sysUserToRoles);
    }

    private SysUser addSysUser(SysUserForAddDto dto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(dto, sysUser);
        sysUser.setCtime(new Date());
        sysUser.setUtime(new Date());
        sysUser.setPassword(PasswordUtil.generate(sysUser.getPassword()));

        SysUser currentOwnerUser = getCurrentUserOwner();
        sysUser.setIsAdmin(dto.getRoleIds().size()>0?dto.getRoleIds().get(0):SysUserType.NORMAL.getCode());
        sysUser.setParentAdminId(currentOwnerUser.getId());

        sysUser.setSysUserId(currentOwnerUser.getId());
        sysUser.setSysUserName(currentOwnerUser.getUsername());
        buildTreePath(sysUser, currentOwnerUser);
        insert(sysUser);
        return sysUser;
    }

    @Override
    public void JudgeByMessage(String mobile) {
        SysUser sysUser = selectOne(new EntityWrapper<SysUser>().eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("mobile", mobile).eq("is_enable", 1));
        if (ParamUtil.isNullOrEmptyOrZero(sysUser)) {
            throw new SystemException(SysExceptionEnum.USER_NOT_EXIST.getCode(), SysExceptionEnum.USER_NOT_EXIST.getMessage());
        }
        String appKey = SysConfigUtils.get(MessageCodeConfig.class).getMessageApiKey();
        String templateId = SysConfigUtils.get(MessageCodeConfig.class).getMessageCodeTemplateId();
        String templateValue = "";
//        String message = SmsApi.tplSendSms(mobile);
        Map<String, String> mapParam = new HashMap<>();
        String message = SmsApi.tplSendSms(appKey, templateId, mobile, templateValue, mapParam);
        map.put(mobile, message);
    }


    @Override
    public void EmailByMessage(String email) {
        SysUser sysUser = selectOne(new EntityWrapper<SysUser>().eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("email", email).eq("is_enable", 1));
        if (ParamUtil.isNullOrEmptyOrZero(sysUser)) {
            throw new SystemException(SysExceptionEnum.USER_NOT_EXIST.getCode(), SysExceptionEnum.USER_NOT_EXIST.getMessage());
        }
        try {
            String message = MailApi.sendEmail(email);
            map.put(email, message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean forgetPwd(SysUserForgetPasswordDto dto) {
        String mobile = dto.getMobile();
        String email = dto.getEmail();
        EntityWrapper<SysUser> entityWrapper = new EntityWrapper<SysUser>();
        entityWrapper.eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("mobile", mobile);
        Integer userId = dto.getUserId();
        if (!ParamUtil.isNullOrEmptyOrZero(userId)){
            entityWrapper.eq("id",userId);
        }
        SysUser user = selectOne(entityWrapper);
        if (ParamUtil.isNullOrEmptyOrZero(user)) {
            throw new SystemException(SysExceptionEnum.ILLEGAL_PARAM.getCode(), SysExceptionEnum.ILLEGAL_PARAM.getMessage());
        }
        if (!Objects.equals(map.get(mobile), dto.getMessage()) || !Objects.equals(map.get(email), dto.getMessage())) {
            throw new SystemException(SysExceptionEnum.MESSAGE_ERROR.getCode(), SysExceptionEnum.MESSAGE_ERROR.getMessage());
        }
        user.setMobile(mobile);
        user.setPassword(PasswordUtil.generate(dto.getNewPassword()));
        user.setUtime(new Date());
        map.remove(mobile);
        map.remove(email);
        return updateById(user);
    }

    @Override
    public boolean updateAvatar(MultipartFile file, Integer userId, String phone, String realName, String email) {
        boolean flag = false;
        SysUser sysUser = selectById(userId);
        if (ParamUtil.isNullOrEmptyOrZero(sysUser)) {
            throw new SystemException(SysExceptionEnum.USER_NOT_EXIST.getCode(), SysExceptionEnum.USER_NOT_EXIST.getMessage());
        }
        MessageCodeConfig messageCodeConfig = SysConfigUtils.get(MessageCodeConfig.class);
        String filename = file.getOriginalFilename();
        //判断是否为限制文件类型
        if (!PictureUtils.checkFile(filename)) {
            //限制文件类型，
            throw new SystemException(SysExceptionEnum.PICTURE_SUFFIX_ERROR.getCode(), SysExceptionEnum.PICTURE_SUFFIX_ERROR.getMessage());
        }
        //限制文件大小
        long size = file.getSize();
        if (!PictureUtils.checkSize(size)) {
            throw new SystemException(SysExceptionEnum.PICTURE_OUT_OF_SIZE.getCode(), SysExceptionEnum.PICTURE_OUT_OF_SIZE.getMessage());
        }

        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String newFilename = UUID.randomUUID() + suffix;

        try {
            String path = messageCodeConfig.getAvatarPath();
            File f = new File(path + newFilename);
            File parentFile = f.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            file.transferTo(f);
            flag = true;
        } catch (IOException e) {
            LOGGER.error("图片文件上传失败，原因：" + e);
            e.printStackTrace();
            throw new SystemException(SysExceptionEnum.UPLOAD_AEEOR.getCode(), SysExceptionEnum.UPLOAD_AEEOR.getMessage());

        }
        sysUser.setAvatar(newFilename);
        sysUser.setUtime(new Date());
        sysUser.setMobile(phone);
        sysUser.setRealName(realName);
        sysUser.setEmail(email);
        updateById(sysUser);
        return flag;
    }

    /**
     * 判断是否为允许的上传文件类型,true表示允许
     */
    private boolean checkFile(String fileName) {
        //设置允许上传文件类型
        String suffixList = SysConfigUtils.get(MessageCodeConfig.class).getPictureType();
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".")
                + 1, fileName.length());
        if (suffixList.contains(suffix.trim().toLowerCase())) {
            return true;
        }
        return false;
    }

    @Override
    public ManageUserInfoDto userInfo() {
        SysUser sysUser = getCurrentUser();
        ManageUserInfoDto dto = new ManageUserInfoDto();
        dto.setAdvater(sysUser.getAvatar());
        dto.setUsername(sysUser.getUsername());
        dto.setMobile(sysUser.getMobile());
        dto.setGenter(sysUser.getGender());
        return dto;
    }

    @Override
    public boolean updateMobile(ManageUserUpdateMobileDto dto) {
        EntityWrapper<SysUser> wrapper = new EntityWrapper<SysUser>();
        wrapper.eq("username", dto.getUsername()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        SysUser sysUser = selectOne(wrapper);
        if (ParamUtil.isNullOrEmptyOrZero(sysUser)) {
            throw new SystemException(SysExceptionEnum.USER_NOT_EXIST.getCode(), SysExceptionEnum.USER_NOT_EXIST.getMessage());
        }
        if (Objects.equals(sysUser.getIsEnable(), SysUserStatus.DISABLE.getCode())) {
            throw new SystemException(SysExceptionEnum.USER_DISABLED.getCode(), SysExceptionEnum.USER_DISABLED.getMessage());
        }
        String md5Pass = sysUser.getPassword();
        if (!PasswordUtil.verify(dto.getPassword(), md5Pass)) {
            throw new SystemException(SysExceptionEnum.ILLEGAL_USER.getCode(), SysExceptionEnum.ILLEGAL_USER.getMessage());
        }
        if (!Objects.equals(map.get(dto.getMobile()), dto.getCode())) {
            throw new SystemException(SysExceptionEnum.MESSAGE_ERROR.getCode(), SysExceptionEnum.MESSAGE_ERROR.getMessage());
        }
        sysUser.setUtime(new Date());
        sysUser.setMobile(dto.getMobile());
        return updateById(sysUser);
    }

    @Override
    public List<SysUser> getListBymobile(String mobile){
        return selectList(new EntityWrapper<SysUser>().eq("is_deleted",DeleteStatus.NOT_DELETED.getCode()).eq("mobile",mobile).eq("is_enable",1));
    }


    @Override
    public List<Integer> resolveParentUserIds(Integer sysUserId){
        Set<Integer> result = new HashSet<>();
        resolveParentUserId(sysUserId, result);
        return new ArrayList<>(result);
    }

    private void resolveParentUserId(Integer sysUserId, Set<Integer> result) {
        SysUser current = selectById(sysUserId);
        //非厂商
        if (Objects.nonNull(current) && !current.getIsAdmin().equals(SysUserType.MANUFACTURER.getCode())) {
           result.add(current.getId());
           //递归查找上级
            resolveParentUserId(current.getParentAdminId(),result);
        }

    }

}
