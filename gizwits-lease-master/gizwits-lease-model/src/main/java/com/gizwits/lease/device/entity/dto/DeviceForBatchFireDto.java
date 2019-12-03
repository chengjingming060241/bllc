package com.gizwits.lease.device.entity.dto;

import com.alibaba.fastjson.JSONObject;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * Dto - 批量发送控制指令
 *
 * @author Joke
 */
public class DeviceForBatchFireDto {

    /** 设备id */
    @NotEmpty
    private List<String> snoList;

    private JSONObject attrs;

    public List<String> getSnoList() {
        return snoList;
    }

    public void setSnoList(List<String> snoList) {
        this.snoList = snoList;
    }

    public JSONObject getAttrs() {
        return attrs;
    }

    public void setAttrs(JSONObject attrs) {
        this.attrs = attrs;
    }
}
