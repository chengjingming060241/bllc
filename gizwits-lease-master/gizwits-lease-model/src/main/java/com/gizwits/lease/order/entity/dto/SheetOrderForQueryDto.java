package com.gizwits.lease.order.entity.dto;

import com.gizwits.boot.annotation.Query;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author lilh
 * @date 2017/8/4 17:26
 */
public class SheetOrderForQueryDto {

    @NotBlank
    @Query(field = "sheet_no")
    private String sheetNo;

    @ApiModelProperty("分润成功查询条件：4分润成功")
    @Query(field = "status")
    private Integer successStatus;

    @ApiModelProperty("分润失败查询状态：4分润成功")
    @Query(field = "status", operator = Query.Operator.ne)
    private Integer failStatus;

    public String getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(String sheetNo) {
        this.sheetNo = sheetNo;
    }

    public Integer getSuccessStatus() {
        return successStatus;
    }

    public void setSuccessStatus(Integer successStatus) {
        this.successStatus = successStatus;
    }

    public Integer getFailStatus() {
        return failStatus;
    }

    public void setFailStatus(Integer failStatus) {
        this.failStatus = failStatus;
    }
}
