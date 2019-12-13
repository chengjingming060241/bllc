package com.gizwits.lease.app.web;

import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.lease.file.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Description:
 * Created by Sunny on 2019/9/17 18:41
 */
@EnableSwagger2
@Api(description = "文件上传")
@RestController
@RequestMapping("/app/file")
public class FileController extends BaseController {


    @Autowired
    private FileService fileService;

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "图片上传", consumes = "application/json")
    @PostMapping("/imgUpload")
    public ResponseObject<String> imgUpload(@RequestParam(value = "file",required = false) MultipartFile file) {
      return success(fileService.uploadImg(file));
    }

}
