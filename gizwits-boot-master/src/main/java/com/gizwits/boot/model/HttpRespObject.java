package com.gizwits.boot.model;

/**
 * Created by zhl on 2017/2/10.
 */
public class HttpRespObject {
    private boolean success;
    private int statusCode;
    private Object content;

    public HttpRespObject(boolean success, int statusCode, Object content){
        this.statusCode=statusCode;
        this.success = success;
        this.content = content;
    }
    public HttpRespObject(){

    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public int getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    public Object getContent() {
        return content;
    }
    public void setContent(Object content) {
        this.content = content;
    }

}
