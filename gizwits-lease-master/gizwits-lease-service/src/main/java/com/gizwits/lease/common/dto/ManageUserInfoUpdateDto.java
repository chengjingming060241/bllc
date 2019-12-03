package com.gizwits.lease.common.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class ManageUserInfoUpdateDto {

    @ApiModelProperty("昵称")
    @Length(max = 20, message = "超过最大长度")
    @NotEmpty(message = "昵称不能为空")
    private String nickName;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("地区省")
    private String province;

    @ApiModelProperty("地区市")
    private String city;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
