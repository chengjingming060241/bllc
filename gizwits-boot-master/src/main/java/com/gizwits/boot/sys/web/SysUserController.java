package com.gizwits.boot.sys.web;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.PermissionTypeEnum;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.common.SysDeleteDto;
import com.gizwits.boot.dto.*;
import com.gizwits.boot.enums.SysUserStatus;
import com.gizwits.boot.exceptions.SysExceptionEnum;
import com.gizwits.boot.exceptions.SystemException;
import com.gizwits.boot.sys.entity.*;
import com.gizwits.boot.sys.service.*;
import com.gizwits.boot.utils.PasswordUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@RestController
@EnableSwagger2
@Api(description = "系统用户接口")
@RequestMapping("/sys/sysUser")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserToRoleService sysUserToRoleService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysVersionService sysVersionService;

    @Autowired
    private SysLaunchAreaService sysLaunchAreaService;


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "添加页面数据", consumes = "application/json")
    @GetMapping("/add")
    public ResponseObject<SysUserForAddPageDto> add() {
        List<SysRole> roles = sysRoleService.getCreatedRolesBySysUserId(sysUserService.getCurrentUser().getId());
        List<SysLaunchArea> sysLaunchAreas = sysLaunchAreaService.selectAllSysLaunchAreaById();
        List<SysRoleForPullDto> sysRoleForPullDtos = new LinkedList<>();
        List<SysLaunchForPullDto> sysLaunchForPullDtos = new LinkedList<>();
        if (CollectionUtils.isNotEmpty(roles)) {
            roles.forEach(role -> sysRoleForPullDtos.add(new SysRoleForPullDto(role)));
        }
        if (CollectionUtils.isNotEmpty(sysLaunchAreas)) {
            sysLaunchAreas.forEach(sysLaunch -> sysLaunchForPullDtos.add(new SysLaunchForPullDto(sysLaunch)));
        }
        SysUserForAddPageDto result = new SysUserForAddPageDto();
        result.setRoles(sysRoleForPullDtos);
        result.setLaunchs(sysLaunchForPullDtos);
        result.initStatus(SysUserStatus.values());

        return success(result);
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "添加", notes = "添加", consumes = "application/json")
    @PostMapping(value = "/add")
    public ResponseObject add(@RequestBody @Valid RequestObject<SysUserForAddDto> requestObject) {
        sysUserService.add(requestObject.getData());
        return success();

    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "分页查询", notes = "分页查询", consumes = "application/json")
    @PostMapping(value = "/getListByPage")
    public ResponseObject<Page<SysUserForListDto>> getListByPage(@RequestBody @Valid RequestObject<Pageable<SysUserForQueryDto>> requestObject) {
        Page<SysUserForListDto> selectPage = sysUserService.getListByPage(requestObject.getData());
        return success(selectPage);
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "重置密码", consumes = "application/json")
    @PostMapping("/resetPwd")
    public ResponseObject resetPassword(@RequestBody @Valid RequestObject<SysUserForUpdatePwdDto> requestObject) {
        SysUserForUpdatePwdDto dto = requestObject.getData();
        SysUser current = sysUserService.getCurrentUser();
        if (!PasswordUtil.verify(dto.getOldPassword(), current.getPassword())) {
            throw new SystemException(SysExceptionEnum.ILLEGAL_USER_PASSWORD.getCode(), SysExceptionEnum.ILLEGAL_USER_PASSWORD.getMessage());
        }
        current.setPassword(PasswordUtil.generate(dto.getNewPassword()));
        current.setUtime(new Date());
        sysUserService.updateById(current);
        return success();
    }


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "查询手机号绑定的账号", consumes = "application/json")
    @PostMapping("/getListBymobile")
    public ResponseObject<List<SysUser>> getListBymobile(@RequestBody @Valid RequestObject<String> requestObject) {

      return success(sysUserService.getListBymobile(requestObject.getData()));
    }


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "账号管理的更新", consumes = "application/json")
    @PostMapping("/update")
    public ResponseObject update(@RequestBody @Valid RequestObject<SysUserForUpdateDto> requestObject) {
        return success(sysUserService.update(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "更新个人设置", consumes = "application/json")
    @PostMapping("/updateInfoPersonal")
    public ResponseObject updateInfoPersonal(@RequestBody @Valid RequestObject<SysUserPersonalInfoUpdateDto> requestObject) {
        return success(sysUserService.updateInfoPersonal(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "详情", consumes = "application/json")
    @PostMapping("/detail")
    public ResponseObject<SysUserForDetailDto> detail(@RequestBody RequestObject<Integer> requestObject) {
        return success(sysUserService.detail(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "删除", notes = "删除", consumes = "application/json")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseObject<String> delete(@RequestBody @Valid RequestObject<List<Integer>> requestObject) {

        return success( sysUserService.delete(requestObject.getData()));
    }

    @ApiOperation(value = "登陆", notes = "登陆", consumes = "application/json")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseObject<JwtAuthenticationDto> login(@RequestBody @Valid RequestObject<LoginDto> requestObject) {
        LoginDto loginDto = requestObject.getData();
        JwtAuthenticationDto tokenDto = sysUserService.login(loginDto);
        return success(tokenDto);
    }

    @ApiOperation(value = "菜单", notes = "菜单", consumes = "application/json")
    @RequestMapping(value = "/menu", method = RequestMethod.POST)
    public ResponseObject<List<MenuDto>> menu() {
        SysUser current = sysUserService.getCurrentUser();
        List<Integer> roleIds =
                sysUserToRoleService.getSysUserToRoleListByUserId(current.getId()).stream()
                        .map(SysUserToRole::getId).collect(Collectors.toList());
        List<Integer> permissionIds = null;
        if(roleIds.contains(sysRoleService.getSuperManagerRoleId())){
            // AEP1.4需求：管理员只显示系统管理和产品管理
            SysPermission sysManage =
                    sysPermissionService.selectOne(new EntityWrapper<SysPermission>().eq("permission_name", "系统管理"));
            SysPermission productManage =
                    sysPermissionService.selectOne(new EntityWrapper<SysPermission>().eq("permission_name", "产品管理"));
            if(sysManage!=null && productManage!=null) {
                permissionIds = new ArrayList<>();
                permissionIds.add(sysManage.getId());
                permissionIds.add(productManage.getId());
                List<SysPermission> childPermission = sysPermissionService
                        .selectList(new EntityWrapper<SysPermission>().in("p_permission_id", permissionIds));
                permissionIds.addAll(childPermission.stream().map(SysPermission::getId).collect(Collectors.toList()));
            }
        }
        if(permissionIds == null){
            permissionIds = sysUserToRoleService.getPermissionsByUserId(current.getId());
        }
        //根据权限ID查询权限
        List<MenuDto> menuDtos = sysPermissionService.getSysPermissionListByIdsAndType(
                permissionIds,PermissionTypeEnum.NAVIGATION_MENU.getKey());
        return success(menuDtos);
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "个人信息详情", notes = "个人信息详情", consumes = "application/json")
    @RequestMapping(value = "/userDetailInfo", method = RequestMethod.POST)
    public ResponseObject<SysUserForDetailDto> userDetailInfo() {
        SysUser user = sysUserService.getCurrentUser();
        SysUserForDetailDto result = sysUserService.detail(user.getId());
        if(Objects.nonNull(result)) {
            result.setRoles(sysRoleService.selectBatchIds(result.getRoleIds()).stream().map(SysRoleForDetailDto::new).collect(Collectors.toList()));
            if (CollectionUtils.isNotEmpty(result.getRoles())){
                result.setShareBenefitType(result.getRoles().stream().map(SysRoleForDetailDto::getShareBenefitType).collect(Collectors.toList()));
            }
            List<SysPermission> permissionList = sysUserService.getPermissionByUserId(user.getId());
            //由于只有admin和admin新建账号会记录与版本的对应关系，因此非admin建立的用户需要追溯到admin建立的roleId去判断
            List<SysVersion> versionList = sysVersionService.getVersionsByRoleIds(result.getRoleIds());
            result.setPermissions(sysVersionService.mergePermissionVersion(permissionList, versionList));
        }
        return success(result);
    }


    @ApiOperation(value = "权限树 ", notes = "权限树", consumes = "application/json")
    @RequestMapping(value = "/permissionTree", method = RequestMethod.POST)
    public ResponseObject<List<MenuDto>> permissionTree(@RequestBody @Valid RequestObject<Integer> requestObject) {
        Integer parentPermissionId = requestObject.getData();
        SysUser current = sysUserService.getCurrentUser();
        //根据权限ID查询权限
        List<MenuDto> menuDtos = sysPermissionService.getSysPermissionListByIdsAndPPermissionId(
                sysUserToRoleService.getPermissionsByUserId(current.getId()), parentPermissionId);
        return success(menuDtos);
    }

    @ApiOperation(value = "短信验证码", notes = "短信验证码", consumes = "application/json")
    @RequestMapping(value = "/messageAuthenticationCode", method = RequestMethod.POST)
    public ResponseObject messageAuthenticationCode(@RequestBody @Valid RequestObject<String> requestObject) {
        sysUserService.JudgeByMessage(requestObject.getData());
        return success();
    }

    @ApiOperation(value = "邮箱验证码", notes = "邮箱验证码", consumes = "application/json")
    @RequestMapping(value = "/emailVerificationCode", method = RequestMethod.POST)
    public ResponseObject emailVerificationCode(@RequestBody @Valid RequestObject<String> requestObject) {
        sysUserService.EmailByMessage(requestObject.getData());
        return success();
    }


    @ApiOperation(value = "忘记密码", notes = "忘记密码", consumes = "application/json")
    @RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
    public ResponseObject forgetPassword(@RequestBody @Valid RequestObject<SysUserForgetPasswordDto> requestObject) {
        sysUserService.forgetPwd(requestObject.getData());
        return success();
    }

    @ApiOperation(value = "修改头像", notes = "修改头像", consumes = "application/json")
    @RequestMapping(value = "/updateAvatar", method = RequestMethod.POST)
    public ResponseObject updateAvatatar(@RequestParam(value = "file",required = false) MultipartFile file,
                                       @RequestParam(value = "userId",required = true) Integer usetId,
                                       @RequestParam(value = "phone",required = false) String phone,
                                       @RequestParam(value = "realName",required = false) String realName,
                                       @RequestParam(value = "email",required = false) String email
    ) {
        sysUserService.updateAvatar(file,usetId,phone,realName,email);
        return success();
    }
}
