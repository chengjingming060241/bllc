package com.gizwits.boot.sys.web;

import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.common.MessageCodeConfig;
import com.gizwits.boot.exceptions.SysExceptionEnum;
import com.gizwits.boot.exceptions.SystemException;
import com.gizwits.boot.utils.PictureUtils;
import com.gizwits.boot.utils.SysConfigUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@EnableSwagger2
@Api(description = "文件")
@RequestMapping("/sys/file")
public class SysFileController extends BaseController{
	private static final Logger log = LoggerFactory.getLogger("SYS_LOGGER_APPENDER");

	@ApiOperation(value = "上传文件", notes = "上传文件", consumes = "application/json")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseObject upload(@RequestParam(value = "file",required = false) MultipartFile file) {
		String filename = file.getOriginalFilename();
		//判断是否为限制文件类型
		if (!PictureUtils.checkFile(filename)) {
			//限制文件类型，
			throw new SystemException(SysExceptionEnum.PICTURE_SUFFIX_ERROR.getCode(), SysExceptionEnum.PICTURE_SUFFIX_ERROR.getMessage());
		}
		//限制文件大小
		long size = file.getSize();
		if(!PictureUtils.checkSize(size)){
			throw new SystemException(SysExceptionEnum.PICTURE_OUT_OF_SIZE.getCode(), SysExceptionEnum.PICTURE_OUT_OF_SIZE.getMessage());
		}

		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String newFilename = UUID.randomUUID() + suffix;

		String uri = null;
		try {
			MessageCodeConfig messageCodeConfig = SysConfigUtils.get(MessageCodeConfig.class);
			String path = messageCodeConfig.getAvatarPath();
			File f = new File(path + newFilename);
			File parentFile = f.getParentFile();
			if (!parentFile.exists()){
				parentFile.mkdirs();
			}
			file.transferTo(f);
			uri = parentFile.getName()+"/"+f.getName();
		} catch (IOException e) {
			log.error("图片文件上传失败，原因：" + e);
			throw new SystemException(SysExceptionEnum.UPLOAD_AEEOR.getCode(), SysExceptionEnum.UPLOAD_AEEOR.getMessage());

		}
		return success(uri);
	}

}
