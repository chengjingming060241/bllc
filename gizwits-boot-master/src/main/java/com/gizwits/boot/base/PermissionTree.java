package com.gizwits.boot.base;

/**
 * Created by Chloe on 2017/7/5.
 */
public class PermissionTree {

    private Integer key;

    private String value;

    public PermissionTree(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {return key;}

    public void setKey(Integer key) {this.key = key;}

    public String getValue() {return value;}

    public void setValue(String value) {this.value = value;}
}
