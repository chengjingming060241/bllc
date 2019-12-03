package com.gizwits.boot.dto;

import com.gizwits.boot.sys.entity.SysPermission;

import java.io.Serializable;
import java.util.List;

/**
 * 根据权限查询的菜单列表
 * Created by Chloe on 2017/7/3.
 */
public class MenuDto implements Serializable {
   private Integer id;
    private Integer bpId;
    private Integer mpId;
    private String name;
    private String icon;
    private  String router;

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public Integer getBpId() {return bpId;}

    public void setBpId(Integer bpId) {this.bpId = bpId;}

    public Integer getMpId() {return mpId;}

    public void setMpId(Integer mpId) {this.mpId = mpId;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getIcon() {return icon;}

    public void setIcon(String icon) {this.icon = icon;}

    public String getRouter() {return router;}

    public void setRouter(String router) {this.router = router;}
}
