package com.gizwits.boot.base;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by rongmc on 16/3/22.
 */
public class RequestObject<T> {

    @NotBlank(message = "appKey值不能为空")
    private String appKey ;

    @NotBlank(message = "version值不能为空")
    private String version ;

    @Valid
    private T data ;



    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
