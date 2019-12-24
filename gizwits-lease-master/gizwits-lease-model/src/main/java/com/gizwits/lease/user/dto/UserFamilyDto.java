package com.gizwits.lease.user.dto;

import lombok.Data;

/**
 * Description:
 * Created by Sunny on 2019/12/24 14:47
 */
@Data
public class UserFamilyDto {

    private Integer id;

    private Integer userId;
    private String name;
    private String province;
    private String city;
    private String area;
    private Integer deviceCount=0;
}
