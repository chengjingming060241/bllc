package com.gizwits.lease.message.entity.dto;

import javax.validation.constraints.NotNull;

public class MessageDetailQueryDto {
    @NotNull(message = "id may not be null")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
