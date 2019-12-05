package com.gizwits.lease.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gizwits.boot.annotation.Query;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.validators.Mobile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.Date;
import java.util.List;

/**
 * @author Qiuhua Lai
 * @email qhlai@gizwits
 */
@Data
public class QueryForUserListDTO {

    @ApiModelProperty("用户id")
    @Query(field = "id")
    private Integer userId;

    @ApiModelProperty("微信openid")
    @Query(field = "openid", operator = Query.Operator.like)
    private String openid;

    @ApiModelProperty("用户昵称")
    @Query(field = "nickname", operator = Query.Operator.like)
    private String nickname;

    @ApiModelProperty("绑定手机号")
    @Mobile
    @Query(field = "mobile",operator = Query.Operator.like)
    private String mobile;

    @ApiModelProperty("联系方式")
    @Mobile
    @Query(field = "username",operator = Query.Operator.like)
    private String username;

    @ApiModelProperty("用户状态：1：正常，2：黑名单")
    @Query(field = "status")
    private Integer status = 1;

    @ApiModelProperty("性别")
    @Query(field = "gender")
    private Integer gender;

    @ApiModelProperty("是否绑定手机号")
    @Query(field = "mobile", operator = Query.Operator.isNotNull, condition = "1", mutex = true)
    private Integer hasMobile;

    @ApiModelProperty("开始时间: yyyy-MM-dd HH:mm:ss")
    @Query(field = "ctime", operator = Query.Operator.ge)
    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN,timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty("结束时间：yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN,timezone = "GMT+8")
    @Query(field = "ctime", operator = Query.Operator.le)
    private Date endTime;

    @Transient
    private String orderByField;
    @Transient
    private String asc;

    private List<Integer> ownerIds;

}
