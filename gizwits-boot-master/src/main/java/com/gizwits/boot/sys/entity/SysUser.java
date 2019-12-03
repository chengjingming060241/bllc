package com.gizwits.boot.sys.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@TableName("sys_user")
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,自增长
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 工号
     */
    @TableId(value = "job_number")
    @NotNull
    private Integer jobNumber;

    /**
     * 添加时间
     */
    private Date ctime;
    /**
     * 更新时间
     */
    private Date utime;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;
    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;
    /**
     * 性别
     */
    private String gender;
    /**
     * 头像地址
     */
    private String avatar;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 手机
     */
    private String mobile;


    /**
     * 地址
     */
    private String address;

    /**
     * 邮箱地址
     */
    @Email
    private String email;

    /**
     * 备注
     */
    private String remark;

    @TableField("sys_user_id")
    private Integer sysUserId;

    @TableField("sys_user_name")
    private String sysUserName;

    /**
     * 启用禁用标识,1：启用，0：禁用
     */
    @TableField("is_enable")
    private Integer isEnable;

    /**
     * 用户树路径
     */
    @TableField("tree_path")
    private String treePath;
    /**
     * 验证码
     */
    private String code;

    /**
     * 系统名称
     */
    @TableField("sys_name")
    private String sysName;

    /**
     * 系统图标
     */
    @TableField("sys_logo")
    private String sysLogo;

    @TableField("is_deleted")
    private Integer isDeleted;

    @TableField("launch_area_id")
    private Integer launchAreaId;

    public Integer getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(Integer jobNumber) {
        this.jobNumber = jobNumber;
    }

    public Integer getLaunchAreaId() {
        return launchAreaId;
    }

    public void setLaunchAreaId(Integer launchAreaId) {
        this.launchAreaId = launchAreaId;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getSysLogo() {
        return sysLogo;
    }

    public void setSysLogo(String sysLogo) {
        this.sysLogo = sysLogo;
    }

    @TableField("is_admin")
    private Integer isAdmin;

    private Integer parentAdminId;

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getParentAdminId() {
        return parentAdminId;
    }

    public void setParentAdminId(Integer parentAdminId) {
        this.parentAdminId = parentAdminId;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }

    public String getCode() {return code;}

    public void setCode(String code) {this.code = code;}

    public Integer getIsDeleted() { return isDeleted; }

    public void setIsDeleted(Integer isDeleted) { this.isDeleted = isDeleted; }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
