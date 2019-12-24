package com.gizwits.lease.device.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description:
 * Created by Sunny on 2019/12/24 9:21
 */
@Data
@TableName("device_plan")
public class DevicePlan extends Model<DevicePlan> {

    @TableId(value="id", type= IdType.AUTO)
    private Integer id;

    private Date ctime;

    private Date utime;

    private String content;

    private String mac;
    @TableField("user_id")
    private Integer userId;

    @TableField("is_deleted")
    private Integer isDeleted;
    /**
     * 是否使用，0不使用，1使用，默认使用
     * */
    @TableField("is_used")
    private Integer isUsed;

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
