package com.gizwits.lease.user.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.lease.common.version.DefaultVersion;
import com.gizwits.lease.common.version.Version;
import com.gizwits.lease.enums.MoveType;
import com.gizwits.lease.user.dto.QueryForUserListDTO;
import com.gizwits.lease.user.dto.UserForDetailDto;
import com.gizwits.lease.user.dto.UserForListDto;
import com.gizwits.lease.user.dto.UserForMoveDto;
import com.gizwits.lease.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;


/**
 * <p>
 * 用户表,不要前缀,因为用户模块计划抽象成通用功能 前端控制器
 * </p>
 *
 * @author rongmc
 * @since 2017-06-28
 */
@EnableSwagger2
@Api(value = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;




    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "用户列表", notes = "分页查询", consumes = "application/json")
    @PostMapping(value = "/page")
    @DefaultVersion(display = {"id","avatar","nickname","region","openid","authorizationTime","mobile"})
    public ResponseObject<Page<UserForListDto>> page(@RequestBody @Valid RequestObject<Pageable<QueryForUserListDTO>> requestObject) {

        Pageable pageable = requestObject.getData();
        pageable.setAsc(false);
        pageable.setOrderByField("authorization_time");
        return success(userService.pageForAffiliation(requestObject.getData()));
    }

    @Version(uri = "/page",version = "1.1",display = {"id","nickname","mobile","region","ctime","lastLoginTime"})
    public ResponseObject<Page<UserForListDto>> page2(@RequestBody @Valid RequestObject<Pageable<QueryForUserListDTO>> requestObject) {
        Pageable pageable = requestObject.getData();
        pageable.setAsc(false);
        pageable.setOrderByField("last_login_time");
        return success(userService.pageForAffiliation(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "详情", consumes = "application/json")
    @PostMapping("/detail")
    @DefaultVersion(display = {"avatar","nickname","openid","region","genderDesc","bindMobile","authorizationTime"})
    public ResponseObject<UserForDetailDto> detail(@RequestBody RequestObject<String> requestObject) {

        return success(userService.detail(requestObject.getData()));
    }

    @Version(uri = "/detail",version = "1.1",display = {"id","nickname","ctime","region","genderDesc","mobile","lastLoginTime","bindWechat"})
    public ResponseObject<UserForDetailDto> detail2(@RequestBody RequestObject<String> requestObject) {
        return success(userService.detail(requestObject.getData()));
    }


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "移入黑名单", consumes = "application/json")
    @PostMapping("/moveIn")
    public ResponseObject moveInBlack(@RequestBody @Valid RequestObject<UserForMoveDto> requestObject) {
        return success(userService.move(requestObject.getData(), MoveType.MOVE_IN_BLACK));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "移出黑名单", consumes = "application/json")
    @PostMapping("/moveOut")
    public ResponseObject moveOutBlack(@RequestBody @Valid RequestObject<UserForMoveDto> requestObject) {
        return success(userService.move(requestObject.getData(), MoveType.MOVE_OUT_BLACK));
    }

    @ApiOperation(value = "短信验证码（忘记密码）", notes = "短信验证码（忘记密码）", consumes = "application/json")
    @RequestMapping(value = "/messageCode", method = RequestMethod.POST)
    public @ResponseBody
    ResponseObject messageCode(@RequestBody @Valid RequestObject<String> requestObject) {
        userService.messageCode(requestObject.getData());
        return success();
    }


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "根据用户昵称搜索用户，此搜索不受权限限制", notes = "分页查询", consumes = "application/json")
    @PostMapping(value = "/searchAllUser")
    public ResponseObject<Page<UserForListDto>> searchUser(@RequestBody @Valid RequestObject<Pageable<QueryForUserListDTO>> requestObject) {
        Pageable pageable = requestObject.getData();
        pageable.setAsc(false);
        pageable.setOrderByField("authorization_time");
        return success(userService.searchUser(requestObject.getData()));
    }








}
