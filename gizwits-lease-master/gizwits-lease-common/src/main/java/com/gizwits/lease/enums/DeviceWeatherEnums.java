package com.gizwits.lease.enums;

/**
 * <p>
 *     设备天气
 * </p>
 */
public enum DeviceWeatherEnums {

    TEMPERATURE(1, "温度"),
    HUMIDITY(2, "湿度"),
    PM2D5(3, "pm2.5"),
    AIR_QUALITY(4, "空气质量");

    private int param;

    private String desc;

    DeviceWeatherEnums(int param, String desc) {
        this.param = param;
        this.desc = desc;
    }

    public int getParam() {
        return param;
    }

    public void setParam(int param) {
        this.param = param;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
