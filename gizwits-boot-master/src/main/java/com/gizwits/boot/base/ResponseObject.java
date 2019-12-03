package com.gizwits.boot.base;

import com.gizwits.boot.exceptions.SysExceptionEnum;

import java.util.List;

/**
 * Created by rongmc on 16/3/22.
 */
public class ResponseObject<T> {


    private String code ="200" ;

    private String message="本次请求成功!";

    private T data = null;

    private String[] display = null;

    public ResponseObject() {
    }

    public ResponseObject(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseObject(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T>  ResponseObject<T>  response(SysExceptionEnum sysExceptionEnum){
        return new ResponseObject(sysExceptionEnum.getCode(),sysExceptionEnum.getMessage());
    }

    public static <T>  ResponseObject<T>  response(SysExceptionEnum sysExceptionEnum, T data){
        return new ResponseObject(sysExceptionEnum.getCode(),sysExceptionEnum.getMessage(),data);
    }

    public static <T> ResponseObject<T> ok(T data){
        return new ResponseObject<T>("200", "本次请求成功", data);
    }

    public ResponseObject(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String[] getDisplay() {
        return display;
    }

    public void setDisplay(String[] display) {
        this.display = display;
    }
}
