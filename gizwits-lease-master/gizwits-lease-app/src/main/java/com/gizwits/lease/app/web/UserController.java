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
	private RedisService redisService;

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "分页查询", notes = "分页查询", consumes = "application/json")
    @RequestMapping(value = "/getListByPage", method = RequestMethod.POST)
    public ResponseObject getListByPage(@RequestBody @Valid RequestObject<Page<User>> requestObject) {
        EntityWrapper<User> entityWrapper = new EntityWrapper<>();
        Page<User> page = requestObject.getData();
        entityWrapper.orderBy(page.getOrderByField(), page.isAsc());
        Page<User> selectPage = userService.selectPage(page, entityWrapper);
        return success(selectPage);
    }

    @ApiOperation(value = "用户登录", notes = "用户登录",consumes = "application/json")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @DefaultVersion()
    public @ResponseBody ResponseObject<TokenDto> login(@RequestBody @Valid RequestObject<UserLoginDto> requestObject ){
        return success(userService.login(requestObject.getData()));
    }


    @Version(uri = "/login",version = "1.1")
    public @ResponseBody ResponseObject<TokenDto> login2(@RequestBody @Valid RequestObject<UserLoginDto> requestObject ){
        return success(userService.login2(requestObject.getData()));
    }

    @ApiOperation(value = "第三方登录", notes = "第三方登录",consumes = "application/json")
    @RequestMapping(value = "/thirdLogin",method = RequestMethod.POST)
    @DefaultVersion()
    public @ResponseBody ResponseObject thirdLogin(@RequestBody @Valid RequestObject<UserForThirdLoginDto> requestObject){
        UserForThirdLoginDto loginDto = requestObject.getData();
        return success(userService.thirdLogin(loginDto));
    }

    @Version(uri = "/thirdLogin",version = "1.1")
    public @ResponseBody ResponseObject thirdLogin2(@RequestBody @Valid RequestObject<UserForThirdLoginDto> requestObject){
        return success(userService.thirdLogin(requestObject.getData()));
    }

    @ApiOperation(value = "用户注册", notes = "用户注册",consumes = "application/json")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @DefaultVersion()
    public @ResponseBody ResponseObject<TokenDto> registerUser(@RequestBody @Valid RequestObject<UserForRegisterDto> requestObject , HttpServletRequest request){

        return success( userService.register(requestObject.getData(), BrowserUtil.getUserBrowserType(request), requestObject.getAppKey()));
    }

    @Version(uri = "/register",version = "1.1")
    public @ResponseBody ResponseObject<TokenDto> registerUser2(@RequestBody @Valid RequestObject<UserForRegisterDto> requestObject , HttpServletRequest request){

        return success(userService.register2(requestObject.getData(), BrowserUtil.getUserBrowserType(request), requestObject.getAppKey()));
    }

    @ApiOperation(value = "第三方注册", notes = "第三方注册",consumes = "application/json")
    @RequestMapping(value = "/thirdRegister",method = RequestMethod.POST)
    public @ResponseBody ResponseObject thirdRegisterUser(@RequestBody @Valid RequestObject<UserForThirdRegisterDto> requestObject){
        return success(userService.thirdRegister(requestObject.getData(), requestObject.getAppKey()));
    }

/*    @Version(uri = "/thirdRegister",version = "1.1")
    public @ResponseBody ResponseObject thirdRegisterUser2(@RequestBody @Valid RequestObject<UserForThirdRegisterDto> requestObject){
        return success(userService.thirdRegister2(requestObject.getData()));
    }*/

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "第三方绑定（通过业务token）", notes = "第三方绑定（通过业务token）",consumes = "application/json")
    @RequestMapping(value = "/thirdBindByToken",method = RequestMethod.POST)
    public @ResponseBody ResponseObject thirdBindByToken(@RequestBody @Valid RequestObject<UserForThirdBindDto> requestObject){
        userService.thirdBindByToken(requestObject.getData());
        return success();
    }

    @ApiOperation(value = "第三方绑定（通过手机号）", notes = "第三方绑定（通过手机号）",consumes = "application/json")
    @RequestMapping(value = "/thirdBindByMobile",method = RequestMethod.POST)
    public @ResponseBody ResponseObject thirdBindByMobile(@RequestBody @Valid RequestObject<UserForThirdBindDto> requestObject){
        return success(userService.thirdBindByMobile(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "第三方解绑", notes = "第三方解绑",consumes = "application/json")
    @RequestMapping(value = "/thirdUnbind",method = RequestMethod.POST)
    public @ResponseBody ResponseObject thirdUnbind(@RequestBody @Valid RequestObject<UserForThirdUnbindDto> requestObject){
        userService.thirdUnbind(requestObject.getData());
        return success();
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

    @ApiOperation(value = "短信验证码(注册）", notes = "短信验证码（注册）", consumes = "application/json")
    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public @ResponseBody ResponseObject messageAuthenticationCode111(@RequestBody @Valid RequestObject<String> requestObject) {
        userService.messageCodeForRegister(requestObject.getData());
        return success();
    }

    @ApiOperation(value = "短信验证码（忘记密码）", notes = "短信验证码（忘记密码）", consumes = "application/json")
    @RequestMapping(value = "/messageCode", method = RequestMethod.POST)
    public @ResponseBody ResponseObject messageCodeForApp(@RequestBody @Valid RequestObject<String> requestObject) {
        userService.messageCodeForForgetPassword(requestObject.getData());
        return success();
    }

    @ApiOperation(value = "管理员短信验证码（忘记密码）", notes = "短信验证码（忘记密码）", consumes = "application/json")
    @RequestMapping(value = "/messageCodeForAdmin", method = RequestMethod.POST)
    public @ResponseBody ResponseObject messageCodeForAdmin(@RequestBody @Valid RequestObject<String> requestObject) {
        userService.messageCode(requestObject.getData());
        return success();
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "修改密码", consumes = "application/json")
    @PostMapping("/resetPwd")
    @ResponseBody
    public ResponseObject resetPassword(@RequestBody @Valid RequestObject<UserForUpdatePwdDto> requestObject) {
        userService.resetPwd(requestObject.getData());
        return success();
    }

    @ApiOperation(value = "短信验证码（绑定微信公众号）", notes = "短信验证码（绑定微信公众号）", consumes = "application/json")
    @RequestMapping(value = "/messageCodeForBindWx", method = RequestMethod.POST)
    public @ResponseBody
    ResponseObject messageCodeForBindWx(@RequestBody @Valid RequestObject<String> requestObject) {
        userService.messageCodeForBindWx(requestObject.getData());
        return success();
    }

    @ApiOperation(value = "短信验证码（第三方绑定）", notes = "短信验证码（第三方绑定）", consumes = "application/json")
    @RequestMapping(value = "/messageCodeBind", method = RequestMethod.POST)
    public @ResponseBody
    ResponseObject messageCodeForBind(@RequestBody @Valid RequestObject<String> requestObject) {
        userService.messageCodeForBind(requestObject.getData());
        return success();
    }

    @ApiOperation(value = "获取机智云token", notes = "获取机智云token",consumes = "application/json")
    @RequestMapping(value = "/getGizwitsToken",method = RequestMethod.POST)
    @DefaultVersion()
    public @ResponseBody ResponseObject<TokenDto> getGizwitsToken(@RequestBody @Valid RequestObject<GizwitsTokenDto> requestObject ){
        return success(userService.getGizwitsToken(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "微信公众号用户绑定（手机号）", notes = "微信公众号用户绑定（手机号）", consumes = "application/json")
    @RequestMapping(value = "/bindUser", method = RequestMethod.POST)
    public @ResponseBody
    ResponseObject bindUser(@RequestBody @Valid RequestObject<UserBindMobileDto> requestObject) {

        return success(userService.bindMobile(requestObject.getData()));
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

    @ApiOperation(value = "图形验证码", notes = "图形验证码", consumes = "application/json")
    @RequestMapping(value = "/picture", method = RequestMethod.POST)
    public ResponseObject picture(@RequestBody @Valid RequestObject requestObject, HttpServletResponse httpServletResponse) {
        ID++;
        //产生图形
        String createText = defaultKaptcha.createText();
        //存入redis
        redisService.cachePictureCode(ID+"", createText);
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
        BufferedImage challenge = defaultKaptcha.createImage(createText);
        try {
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
            LeaseException.throwSystemException(LeaseExceEnums.PICTURE_FAIL);
        }
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        // 对字节数组Base64编码
        JSONObject jsonObject = new JSONObject();
        jsonObject.put( ID+"", Base64.getEncoder().encodeToString(captchaChallengeAsJpeg));// 返回Base64编码过的字节数组字符串
        return success(jsonObject);


        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
      /*  captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        try {
            ServletOutputStream responseOutputStream =
                    httpServletResponse.getOutputStream();
            responseOutputStream.write(captchaChallengeAsJpeg);
            responseOutputStream.flush();
            responseOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return success();*/
    }


}
