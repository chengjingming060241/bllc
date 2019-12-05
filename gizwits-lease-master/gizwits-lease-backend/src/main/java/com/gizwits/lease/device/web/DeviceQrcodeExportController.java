package com.gizwits.lease.device.web;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.enums.SysUserType;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.SysConfigUtils;
import com.gizwits.lease.RequestLock;
import com.gizwits.lease.common.version.DefaultVersion;
import com.gizwits.lease.common.version.Version;
import com.gizwits.lease.config.CommonSystemConfig;
import com.gizwits.lease.constant.DeviceExcelTemplate;
import com.gizwits.lease.constant.DeviceLaunchAreaExcelTemplate;
import com.gizwits.lease.constant.DeviceStockTemplate;
import com.gizwits.lease.constant.ProductCategoryExcelTemplate;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.dto.DeviceExport;
import com.gizwits.lease.device.entity.dto.DeviceExportResultDto;
import com.gizwits.lease.device.entity.dto.DeviceLaunchAreaExportResultDto;
import com.gizwits.lease.device.service.DeviceLaunchAreaAssignService;
import com.gizwits.lease.device.service.DeviceQrcodeService;
import com.gizwits.lease.device.vo.DeviceQrcodeExportDto;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.product.dto.ProductExportResultDto;
import com.gizwits.lease.product.service.ProductQrcodeService;
import com.gizwits.lease.utils.ImportExcelUtils;
import com.gizwits.lease.utils.ZipUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller - 导出设备二维码
 *
 * 企业-设备入库功能：
 *
 * 1、设备入库：导入excel表的时候，系统会有设备mac的检验，若系统不存在则导入成功，若系统存在，会提示这些设备已存在系统。
 *
 * （关于导入失败的设备会有相应的提示和原因说明）
 *
 *
 * 经销商-设备导入功能：
 *
 * 1、企业可以先发货给经销商，再由经销商通过设备导入功能，告知企业 他/她 拿到哪些设备。
 *
 * 2、经销商在导入excel表前，在excel表中填写他/她所拿到的设备mac，点击《导入》，即可。
 *
 * 3、在经销商导入excel表后，系统会有设备mac的检验，若设备mac不存在系统，则导入失败；若设备mac存在系统，系统会判断设备是否已有归属，若已有归属，则导入失败；若没有归属，则导入成功。
 *
 * （关于导入失败的设备会有相应的提示和原因说明）
 *
 * 4、应用场景：厂商线下分配100台设备给省级代理商，省级代理商再将50台设备给市级代理商，各级代理商只需通过导入设备，即可告知上级 他/她 拿到哪些设备。
 *
 *
 * @author lilh
 * @date 2017/8/30 13:59
 */
@Api(description = "导出设备二维码")
@RestController
@RequestMapping("/device/qrcode")
public class DeviceQrcodeExportController extends BaseController {

    @Autowired
    private DeviceQrcodeService deviceQrcodeService;
    @Autowired
    private ProductQrcodeService productQrcodeService;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private DeviceLaunchAreaAssignService deviceLaunchAreaAssignService;

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "导出二维码", consumes = "application/json")
    @PostMapping(value = "/export")
    @DefaultVersion
    public void export(@RequestBody @Valid RequestObject<DeviceQrcodeExportDto> requestObject, HttpServletResponse response) throws IOException {
        SysUser currentUserOwner = sysUserService.getCurrentUserOwner();
        if(currentUserOwner.getIsAdmin().equals(SysUserType.MANUFACTURER.getCode())) {
            String zipFileName = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".zip";
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + URLEncoder.encode(zipFileName, "utf-8"));
            try (ZipOutputStream out = new ZipOutputStream(response.getOutputStream())) {
                //1.生成二维码图片：增加设备，生成二维码
                // 一边生成一边压缩发送，否则会超时
                deviceQrcodeService.resolveDevices(requestObject.getData(), fileName -> {
                    try {
                        ZipUtils.doCompress(fileName, out);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    }
                    return true;
                });
                //2.生成模板文件
                deviceQrcodeService.initTemplateExcel();
                CommonSystemConfig commonSystemConfig = SysConfigUtils.get(CommonSystemConfig.class);
                String template = commonSystemConfig.getQrcodePath() + commonSystemConfig.getDefaultDeviceExcelTemplateFile();
                ZipUtils.doCompress(template, out);
            }
        } else {
            //  代理商运营商的导入功能实际上是进行自动分配功能，只需要二维码一列
            export2(requestObject, response);
        }
    }

//    @Version(uri = "/export",version = "1.1")
    public void export2(@RequestBody @Valid RequestObject<DeviceQrcodeExportDto> requestObject, HttpServletResponse response) throws IOException {
        //2.生成模板文件
        deviceQrcodeService.gdmsInitTemplateExcel();
        //压缩
        CommonSystemConfig commonSystemConfig = SysConfigUtils.get(CommonSystemConfig.class);
        String name = commonSystemConfig.getDeviceExportTemplateFile();
        Path path = Paths.get(commonSystemConfig.getQrcodePath() + name);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(name, "utf-8"));
        Files.copy(path, response.getOutputStream());
    }


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "导入设备")
    @PostMapping("/upload")
    @DefaultVersion
    @RequestLock
    public ResponseObject< List<DeviceLaunchAreaExportResultDto>> upload(@RequestParam("file") MultipartFile file,
                                                                         @RequestParam(value = "productId",required = false) Integer productId) throws Exception {
        List<List<Object>> originData = ImportExcelUtils.parse(file.getInputStream(), file.getOriginalFilename());
        SysUser currentUserOwner = sysUserService.getCurrentUserOwner();
//        if(currentUserOwner.getIsAdmin().equals(SysUserType.MANUFACTURER.getCode())) {

            return success(deviceLaunchAreaAssignService.importExcel(convertLaunchArea(originData)));
//        }
//        else{
//            return success(productQrcodeService.importDeviceExcelForAssign(convert2(originData)));
//        }
    }

    private List<DeviceLaunchAreaExcelTemplate> convertLaunchArea(List<List<Object>> originData) {
        if (CollectionUtils.isEmpty(originData)) {
            LeaseException.throwSystemException(LeaseExceEnums.EXCEL_NO_DATA);
        }
        return originData.stream().filter(this::isValidLaunchArea).map(list ->
                new DeviceLaunchAreaExcelTemplate(String.valueOf(list.get(0)), String.valueOf(list.get(1)),String.valueOf(list.get(2)),String.valueOf(list.get(3)),String.valueOf(list.get(4)))).collect(Collectors.toList());
    }

    private boolean isValidLaunchArea(List<Object> list) {
        return CollectionUtils.isNotEmpty(list) && list.size() == 5 && Objects.nonNull(list.get(0))
                && Objects.nonNull(list.get(2)) && Objects.nonNull(list.get(3))&& Objects.nonNull(list.get(4));
    }

//    @Version(uri = "/upload",version = "1.1")
//    public ResponseObject<Boolean> upload2(@RequestParam("file") MultipartFile file,
//                                          @RequestParam(value = "productId",required = true) Integer productId) throws Exception {
//        List<List<Object>> originData = ImportExcelUtils.parse(file.getInputStream(), file.getOriginalFilename());SysUser currentUserOwner = sysUserService.getCurrentUserOwner();
//        if(currentUserOwner.getIsAdmin().equals(SysUserType.MANUFACTURER.getCode())) {
//            return success(deviceQrcodeService.importDeviceExcel(convert2(originData), productId));
//        }else {
//            return success(deviceQrcodeService.importDeviceExcelForAssign(convert2(originData)));
//        }
//    }

    private List<DeviceExport> convert2(List<List<Object>> originData) {
        if (CollectionUtils.isEmpty(originData)) {
            LeaseException.throwSystemException(LeaseExceEnums.EXCEL_NO_DATA);
        }
        return originData.stream().filter(this::isValid2).map(list -> new DeviceExport(String.valueOf(list.get(0)))).collect(Collectors.toList());
    }

    private boolean isValid2(List<Object> list) {
        return CollectionUtils.isNotEmpty(list) && list.size() == 1 && Objects.nonNull(list.get(0));
    }


}
