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
 * Created by Sunny on 2019/12/16 11:37
 */
@Data
@TableName(value = "user_room")
public class UserRoom extends Model<UserRoom> {

    private static final long serialVersionUID = 1L;
    /**
     * 主键,自增长
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 房间名
     */
    private String name;
    /**
     * 添加时间
     */
    private Date ctime;
    /**
     * 更新时间
     */
    private Date utime;

    @TableField("user_id")
    private Integer userId;

    private Integer is_deleted;

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
