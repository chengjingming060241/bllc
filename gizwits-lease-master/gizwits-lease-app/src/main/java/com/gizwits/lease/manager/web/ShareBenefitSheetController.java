package com.gizwits.lease.manager.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.lease.benefit.dto.ShareBenefitSheetForDetailDto;
import com.gizwits.lease.benefit.dto.ShareBenefitSheetForListDto;
import com.gizwits.lease.benefit.dto.ShareBenefitSheetForQueryDto;
import com.gizwits.lease.benefit.service.ShareBenefitSheetOrderService;
import com.gizwits.lease.benefit.service.ShareBenefitSheetService;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.order.entity.dto.SheetOrderForListDto;
import com.gizwits.lease.order.entity.dto.SheetOrderForQueryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.Objects;

/**
 * <p>
 * 分润账单表 前端控制器
 * </p>
 *
 * @author yinhui
 * @since 2017-08-03
 */
@EnableSwagger2
@Api(description = "管理端分润单")
@RestController
@RequestMapping("/app/benefit/shareBenefitSheet")
public class ShareBenefitSheetController extends BaseController {

    @Autowired
    private ShareBenefitSheetService shareBenefitSheetService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ShareBenefitSheetOrderService shareBenefitSheetOrderService;


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "分润单列表", consumes = "application/json")
    @PostMapping("/page")
    public ResponseObject<Page<ShareBenefitSheetForListDto>> page(@RequestBody
		    RequestObject<Pageable<ShareBenefitSheetForQueryDto>> requestObject) {
        Pageable<ShareBenefitSheetForQueryDto> pageable = requestObject.getData();
        if (Objects.isNull(pageable.getQuery())) {
            pageable.setQuery(new ShareBenefitSheetForQueryDto());
        }
        pageable.getQuery().setAccessableUserIds(Collections.singletonList(sysUserService.getCurrentUser().getId()));
        return success(shareBenefitSheetService.page(pageable));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "分润单详情", consumes = "application/json")
    @GetMapping("/detail")
    public ResponseObject<ShareBenefitSheetForDetailDto> detail(@RequestParam(value = "id", required = true)Integer id) {
        if (Objects.isNull(id)) {
            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
        }
        ShareBenefitSheetForQueryDto query = new ShareBenefitSheetForQueryDto();
        query.setId(id);
        return success(shareBenefitSheetService.detail(query));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "分润单下订单列表", consumes = "application/json")
    @PostMapping("/list")
    public ResponseObject<Page<SheetOrderForListDto>> list(@RequestBody
		    RequestObject<Pageable<SheetOrderForQueryDto>> requestObject) {

        return success(shareBenefitSheetOrderService.page(requestObject.getData()));
    }
}
