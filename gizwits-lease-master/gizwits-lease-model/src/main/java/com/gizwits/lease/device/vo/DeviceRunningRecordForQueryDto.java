package com.gizwits.lease.device.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gizwits.boot.annotation.Query;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Dto - 运行日志查询
 *
 * @author lilh
 * @date 2017/7/26 11:11
 */
public class DeviceRunningRecordForQueryDto {

    @Query(field = "sno")
    @NotBlank
    private String deviceSno;

    @Query(field = "ctime", operator = Query.Operator.ge)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @Query(field = "ctime", operator = Query.Operator.le)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    public String getDeviceSno() {
        return deviceSno;
    }

    public void setDeviceSno(String deviceSno) {
        this.deviceSno = deviceSno;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
