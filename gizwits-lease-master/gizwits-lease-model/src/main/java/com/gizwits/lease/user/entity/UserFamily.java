package com.gizwits.lease.user.entity;

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
 * Created by Sunny on 2019/12/24 14:25
 */
@Data
@TableName("user_family")
public class UserFamily extends Model<UserFamily> {

    /**
     * 主键,自增长
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Date ctime;

    private Date utime;

    private String name;

    @TableField("user_id")
    private Integer userId;

    private String province;

    private String city;

    private String area;
    @Override
    protected Serializable pkVal() {
        return id;
    }
}
