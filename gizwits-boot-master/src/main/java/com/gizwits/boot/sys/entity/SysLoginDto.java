package com.gizwits.boot.sys.entity;

import com.gizwits.boot.validators.Mobile;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Description:
 * User: yinhui
 * Date: 2017-12-22
 */
public class SysLoginDto implements Serializable {
    @NotBlank(message ="请输入手机号码!")
    @Mobile
    private String mobile ;
    @NotBlank(message ="请输入密码!")
    @Pattern(regexp = "^[0-9a-zA-Z\\-_%`~!@#\\u0024\\u005E&\\u002A\\u0028\\u0029\\u002B=]+$", message = "参数格式错误")
    @Length(min = 6, max = 18)
    private String password ;

    public String getMobile() {return mobile;}

    public void setMobile(String mobile) {this.mobile = mobile;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}
}