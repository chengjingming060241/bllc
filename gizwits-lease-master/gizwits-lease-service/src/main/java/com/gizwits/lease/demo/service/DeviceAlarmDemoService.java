package com.gizwits.lease.demo.service;

import java.util.Date;

public interface DeviceAlarmDemoService {

    int demoGenerateAlarms(Date time);

    int countDeviceForDemoAlarm(Date time);

    void fixDeviceAlarms(Date time);
}
