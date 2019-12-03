package com.gizwits.boot.exceptions;

/**
 * Created by rongmc on 16/3/23.
 *
 */
public enum SysExceptionEnum {

    /****************************/
    /***** 5XX 系统异常提示  *****/
    /****************************/

    NO_PRIVILEGE("401", "无权限访问接口"),

    INTERNAL_ERROR("500", "系统内部异常"),
    APP_KEY_ERROR("502", "非法应用,AppKey错误"),
    SIGN_ERROR("503", "签名错误"),
    ILLEGAL_PARAM("504", "请求参数不合法"),
    NO_LOGIN("505", "未登录"),
    ONLY_HTTPS("506", "只支持HTTPS访问"),
    REPEAT_REQUEST("507", "重复操作"),
    SQL_ERROR("508", "数据库操作异常,字段不合格或者主键冲突等!"),
    ILLEGAL_USER("509","用户名或者密码有误!"),
    ILLEGAL_OPRATION("510","操作已达上限"),
    DUP_ROLE_NAME("512", "角色名重复"),
    DUP_USER_NAME("513", "账号名重复"),
    DUP_PHONE_NUMBER("514", "手机号重复"),
    ROLE_NOT_EXIST("525", "角色不存在"),
    DUP_MAC("515","mac地址重复"),
    SEND_FAUIL("516","短信发送失败"),
    MESSAGE_ERROR("517","验证码错误"),
    USER_DISABLED("518", "您的账号已暂停使用，请联系客服"),
    USER_NOT_EXIST("519","账号不存在"),
    PICTURE_SUFFIX_ERROR("520", "图片格式有误"),
    PICTURE_OUT_OF_SIZE("521","图片格式太大"),
    UPLOAD_AEEOR("522","图片上传失败"),
    SYS_ROLE_ASSOCIATE_USER("523","角色有关联已有用户"),
    ILLEGAL_USER_PASSWORD("524","用户密码不正确"),
    TOO_FREQUENT_OPERATION("525","操作太频繁,请稍后再试"),
    ;



    private String code;
    private String message;


    SysExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static SysExceptionEnum get(Long code) {
        if(code == null) {
            return null;
        } else {
            SysExceptionEnum[] arr$ = values();
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                SysExceptionEnum appResultCodeEnum = arr$[i$];
                if(appResultCodeEnum.getCode().equals(code)) {
                    return appResultCodeEnum;
                }
            }

            return null;
        }
    }

    public static boolean is(SysExceptionEnum appResultCodeEnum, Long code) {
        return appResultCodeEnum != null && code != null?appResultCodeEnum.getCode().equals(code):false;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
