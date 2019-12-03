package com.gizwits.lease.device.entity;

import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 设备运行记录表
 * </p>
 *
 * @author zhl
 * @since 2017-07-12
 */
@Data
@Accessors(chain = true)
@TableName("device_running_record")
public class DeviceRunningRecord extends Model<DeviceRunningRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,自增长
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 添加时间
     */
    private Date ctime;
    /**
     * 更新时间
     */
    private Date utime;
    /**
     * 对应设备序列号
     */
    private String sno;
    /**
     * MAC地址
     */
    private String mac;
    /**
     * 设备在线状态,0:离线,1:空闲 2:使用中 3:故障中 4:禁用
     */
    @TableField("work_status")
    private Integer workStatus;
    /**
     * 报文内容
     */
    private String content;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
