package com.gizwits.lease.stat.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by GaGi on 2017/7/19.
 */
public class StatUserWidgetVo {
    private Integer totalCount;
    // private Double newPercent;
    @ApiModelProperty("昨日活跃用户数")
     private Integer activeCount;
    @ApiModelProperty("昨日活跃用户比例")
     private Double activePercent;

    @ApiModelProperty("昨日新增用户数")
    private Integer newCount;
    @ApiModelProperty("累计租赁人数")
    private Integer orderedCount;
    @ApiModelProperty("新增租赁人数")
    private Integer newOrderedCount;

    public StatUserWidgetVo() {
        this.totalCount=0;
//         this.newPercent=0.0;
         this.activeCount=0;
         this.activePercent=0.0;
        this.newCount=0;
        this.orderedCount=0;
        this.newOrderedCount=0;
    }

    public StatUserWidgetVo(Integer totalCount, Integer newCount, Integer orderedCount, Integer newOrderedCount) {
        this.totalCount = totalCount;
        this.newCount = newCount;
        this.orderedCount = orderedCount;
        this.newOrderedCount = newOrderedCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getNewCount() {
        return newCount;
    }

    public void setNewCount(Integer newCount) {
        this.newCount = newCount;
    }

    public Integer getOrderedCount() {
        return orderedCount;
    }

    public void setOrderedCount(Integer orderedCount) {
        this.orderedCount = orderedCount;
    }

    public Integer getNewOrderedCount() {
        return newOrderedCount;
    }

    public void setNewOrderedCount(Integer newOrderedCount) {
        this.newOrderedCount = newOrderedCount;
    }

    // public Double getNewPercent() {
    //     return newPercent;
    // }
    //
    // public void setNewPercent(Double newPercent) {
    //     this.newPercent = newPercent;
    // }
    //
     public Integer getActiveCount() {
         return activeCount;
     }

     public void setActiveCount(Integer activeCount) {
         this.activeCount = activeCount;
     }

     public Double getActivePercent() {
         return activePercent;
     }

     public void setActivePercent(Double activePercent) {
         this.activePercent = activePercent;
     }
}
