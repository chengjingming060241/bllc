package com.gizwits.lease.device.entity.dto;

/**
 * Description:
 * User: yinhui
 * Date: 2018-01-19
 */
public class DeviceExport {

    private String qrcode;

    public DeviceExport(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }
}
