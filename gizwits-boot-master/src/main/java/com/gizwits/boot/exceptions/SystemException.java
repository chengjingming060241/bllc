package com.gizwits.boot.exceptions;

/**
 * Created by rongmc on 16/5/31.
 */
public class SystemException extends RuntimeException {

    private String code ;

    private String message;


    public SystemException(String code,String message) {
        super(message);
        this.code = code;
        this.message = message;
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
}
