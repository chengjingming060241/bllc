package com.gizwits.boot.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Dto - 个人设置更新
 *
 * @author lilh
 * @date 2017/7/29 14:35
 */
public class SysUserPersonalInfoUpdateDto {

    private String nickName;

    private String email;

    private SysUserExtForAddDto ext;
    /**
     * 系统名称
     */
    private String sysName;

    /**
     * 系统图标
     */
    private String sysLogo;

    @ApiModelProperty("共享数据")
    private SysUserShareDataDto shareData;

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SysUserExtForAddDto getExt() {
        return ext;
    }

    public void setExt(SysUserExtForAddDto ext) {
        this.ext = ext;
    }

    public SysUserShareDataDto getShareData() {
        return shareData;
    }

    public void setShareData(SysUserShareDataDto shareData) {
        this.shareData = shareData;
    }
}
