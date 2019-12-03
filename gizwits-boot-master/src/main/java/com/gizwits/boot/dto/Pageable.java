package com.gizwits.boot.dto;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * @author lilh
 * @date 2017/7/5 15:24
 */
public class Pageable<T> extends Pagination {

    private T query;

    public T getQuery() {
        return query;
    }

    public void setQuery(T query) {
        this.query = query;
    }
}
