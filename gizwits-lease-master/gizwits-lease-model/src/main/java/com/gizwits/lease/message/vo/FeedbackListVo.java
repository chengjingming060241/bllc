package com.gizwits.lease.message.vo;

import lombok.Data;

import java.util.Date;

/**
 * Description:
 * Created by Sunny on 2019/12/5 15:48
 */
@Data
public class FeedbackListVo {

    private Integer id;
    private String nickName;
    private String mobile;
    private Integer type;
    private String content;
    private Integer imgCount;
    private Integer status;
    private Date ctime;
}
