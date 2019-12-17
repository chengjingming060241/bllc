package com.gizwits.lease.device.entity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by Sunny on 2019/12/17 15:39
 */
@Data
public class AppUpdateDeviceNameDto {

    private List<String> macs=new ArrayList<>();

    private String name;
}
