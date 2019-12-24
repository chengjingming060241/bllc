package com.gizwits.lease.app.web;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.lease.app.utils.BrowserUtil;
import com.gizwits.lease.common.version.DefaultVersion;
import com.gizwits.lease.common.version.Version;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.redis.RedisService;
import com.gizwits.lease.user.dto.*;
import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.user.service.UserRoomService;
import com.gizwits.lease.user.service.UserService;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * <p>
 * 用户表,不要前缀,因为用户模块计划抽象成通用功能 前端控制器
 * </p>
 *
 * @author rongmc
 * @since 2017-06-28
 */
@Api(value = "用户管理")
@RestController
@RequestMapping("/app/user")
public class UserController extends BaseController {

    protected  static Integer ID = 0;

    @Autowired
    private UserService userService;

	@Autowired
	private DefaultKaptcha defaultKaptcha;

	@Autowired
	private UserRoomService userRoomService;

	@Autowired
	private RedisService redisService;

    @ApiOperation(value = "用户登录(注册)", notes = "用户登录（注册）",consumes = "application/json")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @DefaultVersion()
    public @ResponseBody ResponseObject<TokenDto> login(@RequestBody @Valid RequestObject<UserLoginDto> requestObject ){
        return success(userService.login(requestObject.getData()));
    }

    @ApiOperation(value = "第三方登录", notes = "第三方登录",consumes = "application/json")
    @RequestMapping(value = "/thirdLogin",method = RequestMethod.POST)
    @DefaultVersion()
    public @ResponseBody ResponseObject thirdLogin(@RequestBody @Valid RequestObject<UserForThirdLoginDto> requestObject){
        UserForThirdLoginDto loginDto = requestObject.getData();
        return success(userService.thirdLogin(loginDto));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "用户详情", notes = "用户详情",consumes = "application/json")
    @RequestMapping(value = "/get",method = RequestMethod.POST)
    public @ResponseBody ResponseObject<UserForDetailDto> getUser(@RequestBody @Valid RequestObject requestObject ){
        return success(userService.detail());
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "修改用户名", notes = "修改用户名",consumes = "application/json")
    @RequestMapping(value = "/updateUsername",method = RequestMethod.POST)
    public @ResponseBody ResponseObject updateUsername(@RequestBody @Valid RequestObject<UserUpdateDto> requestObject ){
        userService.updateUsername(requestObject.getData());
        return success();
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "忘记密码", notes = "忘记密码",consumes = "application/json")
    @RequestMapping(value = "/forgetPassword",method = RequestMethod.POST)
    public @ResponseBody ResponseObject forgetPassword(@RequestBody @Valid RequestObject<UserResetPasswordDto> requestObject ){
        userService.forgetPwd(requestObject.getData());
        return success();
    }
    @ApiOperation(value = "短信验证码", notes = "短信验证码", consumes = "application/json")
    @RequestMapping(value = "/sendCode", method = RequestMethod.POST)
    public @ResponseBody ResponseObject sendCode(@RequestBody @Valid RequestObject<SendCodeDto> requestObject) {

        return success(userService.sendCode(requestObject.getData()));
    }


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "修改密码", consumes = "application/json")
    @PostMapping("/resetPwd")
    @ResponseBody
    public ResponseObject resetPassword(@RequestBody @Valid RequestObject<UserForUpdatePwdDto> requestObject) {
        userService.resetPwd(requestObject.getData());
        return success();
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息", consumes = "application/json")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseObject update(@RequestBody @Valid RequestObject<UserForInfoDto> requestObject) {

        return success(userService.update(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "更新用户手机号", notes = "更新用户手机号信息", consumes = "application/json")
    @RequestMapping(value = "/updatePhone", method = RequestMethod.POST)
    public ResponseObject updateUserMobile(@RequestBody @Valid RequestObject<UserForUpdateMobileDto> requestObject) {

        return success(userService.updateUserMobile(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "获取当前用户的家庭信息", notes = "获取当前用户的家庭信息", consumes = "application/json")
    @RequestMapping(value = "/getUserFamily", method = RequestMethod.GET)
    public ResponseObject getUserFamily() {

        return success(userService.getUserFamily());
    }
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "修改当前用户的家庭信息", notes = "获取当前用户的家庭信息", consumes = "application/json")
    @RequestMapping(value = "/updateUserFamily", method = RequestMethod.POST)
    public ResponseObject updateUserFamily(@RequestBody  RequestObject<UserFamilyUpdateDto> requestObject) {

        return success(userService.updateUserFamily(requestObject.getData()));
    }


}
