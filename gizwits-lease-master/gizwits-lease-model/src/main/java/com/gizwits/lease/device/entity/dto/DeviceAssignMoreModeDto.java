package com.gizwits.lease.device.entity.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Description:设备分配多个收费模式Dto
 * User: yinhui
 * Date: 2018-01-05
 */
public class DeviceAssignMoreModeDto implements Serializable{

    /**设备sno*/
    private String sno;
    /**收费模式id*/
    private List<Integer> serviceModeId;

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public List<Integer> getServiceModeId() {
        return serviceModeId;
    }

    public void setServiceModeId(List<Integer> serviceModeId) {
        this.serviceModeId = serviceModeId;
    }
}
