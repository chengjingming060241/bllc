package com.gizwits.lease.app.web;

import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.share.dto.SavePictureRequest;
import com.gizwits.lease.share.dto.SavePictureResponse;
import com.gizwits.lease.utils.ShareUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 分享相关功能
 * @author yuqing
 * @date 2018-05-24
 */
@RestController
@Api(description = "分享相关功能")
@EnableSwagger2
@RequestMapping("/app/share")
public class ShareController extends BaseController {

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "保存BASE64格式的图片", notes = "保存BASE64格式的图片", consumes = "application/json")
    @RequestMapping(value = "/savePicture", method = RequestMethod.POST)
    public ResponseObject<SavePictureResponse> savePicture(RequestObject<SavePictureRequest> param) {
        if (param == null || param.getData() == null || param.getData().getData() == null) {
            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
        }
        return success(ShareUtil.savePicture(param.getData()));
    }

}
