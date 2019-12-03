package com.gizwits.boot.common;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 * User: yinhui
 * Date: 2018-02-05
 */
public class SysDeleteDto implements Serializable{
    /**未删除*/
    private List<String> fails;
    /**删除数据条数*/
    private Integer succeed;

    public List<String> getFails() {
        return fails;
    }

    public void setFails(List<String> fails) {
        this.fails = fails;
    }

    public Integer getSucceed() {
        return succeed;
    }

    public void setSucceed(Integer succeed) {
        this.succeed = succeed;
    }
}
