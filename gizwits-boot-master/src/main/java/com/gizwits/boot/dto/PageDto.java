package com.gizwits.boot.dto;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gizwits.boot.utils.ParamUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rongmc on 2017/6/30.
 */
public class PageDto extends Pagination {

    @JsonProperty
    private boolean searchCount;
    @JsonProperty
    private boolean openSort;
    @JsonProperty
    private boolean optimizeCount;
    @JsonProperty
    private boolean isAsc;

    private String condition;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;


    @Override
    public boolean isSearchCount() {
        return searchCount;
    }

    @Override
    public void setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
    }

    @Override
    public boolean isOpenSort() {
        return openSort;
    }

    @Override
    public void setOpenSort(boolean openSort) {
        this.openSort = openSort;
    }

    @Override
    public boolean isOptimizeCount() {
        return optimizeCount;
    }

    @Override
    public void setOptimizeCount(boolean optimizeCount) {
        this.optimizeCount = optimizeCount;
    }

    @Override
    public boolean isAsc() {
        return isAsc;
    }

    @Override
    public void setAsc(boolean asc) {
        isAsc = asc;
    }

    /**
     * 返回查询条件的Map,并且此条件中各项值需要与数据库值相等
     * @return
     */
    public Map<String,Object>  getCondition() {
        Map<String,Object> conditionMap = new HashMap<String, Object>();
        if(!ParamUtil.isNullOrEmptyOrZero(this.condition)){
            conditionMap = (Map<String,Object>) JSON.parse(this.condition);
        }
        return conditionMap;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }


}
