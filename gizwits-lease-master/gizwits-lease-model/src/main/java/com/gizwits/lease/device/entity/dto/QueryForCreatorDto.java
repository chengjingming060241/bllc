package com.gizwits.lease.device.entity.dto;

/**
 * @author lilh
 * @date 2017/8/29 10:40
 */
public class QueryForCreatorDto {

    /** 创建人 */
    private Integer creatorId;

    private Integer id;

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }
}
