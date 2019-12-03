package com.gizwits.lease.manager.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gizwits.boot.api.SmsApi;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.common.MessageCodeConfig;
import com.gizwits.boot.dto.JwtAuthenticationDto;
import com.gizwits.boot.dto.SysUserForgetPasswordDto;
import com.gizwits.boot.enums.SysUserStatus;
import com.gizwits.boot.exceptions.SysExceptionEnum;
import com.gizwits.boot.exceptions.SystemException;
import com.gizwits.boot.sys.entity.SysLoginDto;
import com.gizwits.boot.sys.entity.SysRole;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysRoleService;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.boot.utils.PasswordUtil;
import com.gizwits.boot.utils.SysConfigUtils;
import com.gizwits.lease.common.dto.ManageUserInfoDto;
import com.gizwits.lease.common.dto.ManageUserInfoUpdateDto;
import com.gizwits.lease.common.dto.ManageUserUpdateMobileDto;
import com.gizwits.lease.config.CommonSystemConfig;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.manager.service.ManufacturerService;
import com.gizwits.lease.redis.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.*;

@RestController
@EnableSwagger2
@Api(description = "管理端用户接口")
@RequestMapping("/app/manage/user")
public class ManagerUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ManufacturerService manufacturerService;

    @ApiOperation(value = "登陆", notes = "登陆", consumes = "application/json")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseObject<JwtAuthenticationDto> login(@RequestBody @Valid RequestObject<SysLoginDto> requestObject) {
//        JudgeRole(requestObject.getData());
        return success(sysUserService.sysUserLogin(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "修改手机号码", notes = "修改手机号码", consumes = "application/json")
    @RequestMapping(value = "/updateMobile", method = RequestMethod.POST)
    public ResponseObject update(@RequestBody @Valid RequestObject<ManageUserUpdateMobileDto> requestObject) {
        ManageUserUpdateMobileDto dto = requestObject.getData();
        Wrapper<SysUser> wrapper = new EntityWrapper().eq("mobile", dto.getUpdateMobile());
        if (sysUserService.selectCount(wrapper) > 0) {
            LeaseException.throwSystemException(LeaseExceEnums.USER_PHONE_EXISTS);
        }

        wrapper = new EntityWrapper().eq("mobile", dto.getMobile());
        SysUser sysUser = sysUserService.selectOne(wrapper);
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

        String code = redisService.getMobileCode(dto.getUpdateMobile());
        if (code == null || !code.equals(dto.getCode())) {
            LeaseException.throwSystemException(LeaseExceEnums.MOBILE_CODE_ERROR_OR_EXPIRE);
        }
        redisService.expireMobileCode(dto.getUpdateMobile());

        SysUser userForUpdate = new SysUser();
        userForUpdate.setId(sysUser.getId());
        userForUpdate.setUtime(new Date());
        userForUpdate.setMobile(dto.getUpdateMobile());
        sysUserService.updateById(userForUpdate);
        return success();
    }

    @ApiOperation(value = "忘记密码", notes = "忘记密码", consumes = "application/json")
    @PostMapping(value = "/forgetPwd")
    public ResponseObject forgetPwd(@RequestBody @Valid RequestObject<SysUserForgetPasswordDto> requestObject) {
        SysUserForgetPasswordDto dto = requestObject.getData();
        Wrapper<SysUser> wrapper = new EntityWrapper().eq("mobile", dto.getMobile());
        SysUser sysUser = sysUserService.selectOne(wrapper);
        if (ParamUtil.isNullOrEmptyOrZero(sysUser)) {
            throw new SystemException(SysExceptionEnum.USER_NOT_EXIST.getCode(), SysExceptionEnum.USER_NOT_EXIST.getMessage());
        }

        if (Objects.equals(sysUser.getIsEnable(), SysUserStatus.DISABLE.getCode())) {
            throw new SystemException(SysExceptionEnum.USER_DISABLED.getCode(), SysExceptionEnum.USER_DISABLED.getMessage());
        }

        String code = redisService.getMobileCode(dto.getMobile());
        if (code == null || !code.equals(dto.getMessage())) {
            LeaseException.throwSystemException(LeaseExceEnums.MOBILE_CODE_ERROR_OR_EXPIRE);
        }
        redisService.expireMobileCode(dto.getMobile());

        SysUser userForUpdate = new SysUser();
        userForUpdate.setId(sysUser.getId());
        userForUpdate.setUtime(new Date());
        userForUpdate.setPassword(PasswordUtil.generate(dto.getNewPassword()));
        sysUserService.updateById(userForUpdate);
        return success();
    }

    @ApiOperation(value = "短信验证码", notes = "短信验证码", consumes = "application/json")
    @RequestMapping(value = "/messageAuthenticationCode", method = RequestMethod.POST)
    public ResponseObject messageAuthenticationCode(@RequestBody @Valid RequestObject<String> requestObject) {
        String mobile = requestObject.getData();

        String appKey = (SysConfigUtils.get(MessageCodeConfig.class)).getMessageApiKey();
        String templateId = (SysConfigUtils.get(MessageCodeConfig.class)).getMessageCodeTemplateId();
        String templateValue = "";
        Map<String, String> mapParam = new HashMap();
        String message = SmsApi.tplSendSms(appKey, templateId, mobile, templateValue, mapParam);
        redisService.cacheMobileCode(mobile, message);
        return success();
    }


    @ApiOperation(value = "查询个人信息", consumes = "application/json")
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @RequestMapping(value = "/userInfo", method = RequestMethod.POST)
    public ResponseObject<ManageUserInfoDto> userInfo(@RequestBody @Valid RequestObject requestObject) {
        SysUser sysUser = sysUserService.selectById(sysUserService.getCurrentUser().getId());
        ManageUserInfoDto dto = new ManageUserInfoDto();
        BeanUtils.copyProperties(sysUser, dto);
        if (StringUtils.isNotBlank(sysUser.getAddress())) {
            String[] address = StringUtils.splitByWholeSeparator(sysUser.getAddress(), " ");
            if (address.length >= 2) {
                dto.setProvince(address[0]);
                dto.setCity(address[1]);
            }
        }
        dto.setCompanyName(manufacturerService.getCompanyName(sysUser));
        return success(dto);
    }

    @ApiOperation(value = "修改个人信息", consumes = "application/json")
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @PostMapping(value = "/userInfo/update")
    public ResponseObject updateUserInfo(@RequestBody @Valid RequestObject<ManageUserInfoUpdateDto> requestObject) {
        Integer currentUserId = sysUserService.getCurrentUser().getId();
        ManageUserInfoUpdateDto dto = requestObject.getData();
        SysUser updateForSysUser = new SysUser();
        BeanUtils.copyProperties(requestObject.getData(), updateForSysUser);
        updateForSysUser.setId(currentUserId);
        if (StringUtils.isNotEmpty(dto.getProvince()) && StringUtils.isNotEmpty(dto.getCity())) {
            updateForSysUser.setAddress(String.format("%s %s", dto.getProvince(), dto.getCity()));
        }
        sysUserService.updateById(updateForSysUser);
        return success();
    }

    @ApiOperation(value = "修改头像", notes = "修改头像", consumes = "application/json")
    @RequestMapping(value = "/updateAvatar", method = RequestMethod.POST)
    public ResponseObject updateAvatatar(@RequestParam(value = "file", required = false) MultipartFile file,
                                         @RequestParam(value = "phone", required = false) String phone,
                                         @RequestParam(value = "address",required = false)String address) {
        SysUser sysUser = sysUserService.getCurrentUser();
        sysUserService.updateAvatar(file, sysUser.getId(), phone, sysUser.getRealName(), sysUser.getEmail());

        if (address != null) {
            SysUser userForUpdate = new SysUser();
            userForUpdate.setId(sysUser.getId());
            userForUpdate.setAddress(address);
            sysUserService.updateById(sysUser);
        }
        return success();
    }

    private void JudgeRole(SysLoginDto loginDto) {
        EntityWrapper<SysUser> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("mobile", loginDto.getMobile());
        SysUser sysUser = sysUserService.selectOne(entityWrapper);
        if (ParamUtil.isNullOrEmptyOrZero(sysUser)) {
            LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
        } else if (!PasswordUtil.verify(loginDto.getPassword(), sysUser.getPassword())) {
            LeaseException.throwSystemException(LeaseExceEnums.PHONE_OR_PASSWORD_ERROR);
        }
        List<SysRole> sysRoles = sysRoleService.getRolesByUserId(sysUser.getId());
        if(ParamUtil.isNullOrEmptyOrZero(sysRoles)){
            throw  new SystemException(LeaseExceEnums.USER_DONT_EXISTS.getCode(),"用户无权限登录");
        }
        CommonSystemConfig commonSystemConfig = SysConfigUtils.get(CommonSystemConfig.class);
        String manatiner = commonSystemConfig.getMaintenanceRole();
        boolean flag = false;
        for(SysRole role:sysRoles){
            if(role.getRoleName().indexOf(manatiner)>=0){
                flag = true;
            }
        }
        if(!flag){
            throw  new SystemException(LeaseExceEnums.USER_DONT_EXISTS.getCode(),"用户无权限登录");
        }
    }

}
