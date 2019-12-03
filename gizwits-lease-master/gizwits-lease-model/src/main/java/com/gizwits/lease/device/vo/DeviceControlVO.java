package com.gizwits.lease.device.vo;

import com.alibaba.fastjson.JSONObject;

/**
 * <p>
 *     设备控制数据点信息
 * </p>
 * @author kailuo
 * @since 2018-02-05
 */
public class DeviceControlVO {

    private String productKey;

    private String did;

    /** 数据点 */
    private JSONObject attrs;

    public DeviceControlVO() {
    }

    public DeviceControlVO(String productKey, String did, JSONObject attrs) {
        this.productKey = productKey;
        this.did = did;
        this.attrs = attrs;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public JSONObject getAttrs() {
        return attrs;
    }

    public void setAttrs(JSONObject attrs) {
        this.attrs = attrs;
    }
}
