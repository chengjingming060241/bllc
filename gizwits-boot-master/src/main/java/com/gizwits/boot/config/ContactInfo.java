package com.gizwits.boot.config;

/**
*@Author rongmc
*@Date 2017/5/24 20:09
*接口信息
*/
public class ContactInfo {
    private String name;
    private String url;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "author:"+name+" <br/> url:"+url + "<br/> email:"+email;
    }
}
