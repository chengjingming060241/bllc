package com.gizwits.lease.tmallLink.entity;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.io.Serializable;
import java.util.Date;


/**
 * 天猫链接实体类
 */
@TableName("tmall_link")
public class TmallLink extends Model<TmallLink> {

    @TableId(value = "id" ,type = IdType.AUTO)
    private Integer id;

    /**添加时间*/
    private Date ctime;

    /**更新时间*/
    private Date utime;

    /**链接名称*/
    @TableField("link_name")
    private String linkName;

    /**产品id*/
    @TableField("category_id")
    private String categoryId;

    /**产品型号*/
    @TableField("category_name")
    private String categoryName;

    /**链接地址*/
    @TableField("link_url")
    private String linkUrl;

    /** 操作人*/
    @TableField("sys_user_id")
    private Integer sysUserId;

    /** 创建人名称*/
    @TableField("sys_user_name")
    private String sysUserName;

    /**描述*/
    private String remark;

    /** 删除标识*/
    @TableField("is_deleted")
    private Integer isDeleted;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Integer getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getSysUserName() {
        return sysUserName;
    }

    public void setSysUserName(String sysUserName) {
        this.sysUserName = sysUserName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TmallLink{" +
                "id=" + id +
                ", ctime=" + ctime +
                ", utime=" + utime +
                ", linkName='" + linkName + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", sysUserId=" + sysUserId +
                ", remark='" + remark + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
