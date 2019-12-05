package com.gizwits.lease.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description: 添加用户
 * Created by Sunny on 2019/12/5 10:56
 */
@Data
public class UserAddDto {


    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("联系方式")
    private String username;

    @ApiModelProperty("绑定手机号")
    private String mobile;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("性别，1男2女")
    private Integer gender;
}
