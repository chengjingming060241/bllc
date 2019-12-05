package com.gizwits.lease.device.vo;

import lombok.Data;

/**
 * Description:
 * Created by Sunny on 2019/12/5 13:46
 */
@Data
public class UserBindDeviceListVo {

    private String sno;

    private String mac;

    private String name;

    private String productName;

    private String bindDate;

    private Integer onlineStatus;
}
