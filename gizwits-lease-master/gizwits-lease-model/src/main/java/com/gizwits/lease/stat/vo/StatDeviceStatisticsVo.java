package com.gizwits.lease.stat.vo;

import lombok.Data;

/**
 * Description: 设备统计，
 * Created by Sunny on 2019/12/4 15:13
 */
@Data
public class StatDeviceStatisticsVo {

    private Integer total;  //总个数
    private Integer addCount; //新增
    private Integer activeCount; //活跃
    private Integer faultCount; //故障
    private Integer alarmCount;  //报警
}
