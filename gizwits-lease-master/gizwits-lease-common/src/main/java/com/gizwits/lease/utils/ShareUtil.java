package com.gizwits.lease.utils;

import com.gizwits.boot.utils.SysConfigUtils;
import com.gizwits.lease.config.CommonSystemConfig;
import com.gizwits.lease.enums.ImageType;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.share.dto.SavePictureRequest;
import com.gizwits.lease.share.dto.SavePictureResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.UUID;

/**
 * @author yuqing
 * @date 2018-05-24
 */
public class ShareUtil {

    private static final Logger logger = LoggerFactory.getLogger(ShareUtil.class);

    public static SavePictureResponse savePicture(SavePictureRequest param) {
        CommonSystemConfig commonSystemConfig = SysConfigUtils.get(CommonSystemConfig.class);
        String url = commonSystemConfig.getSharedPictureDirectory();
        String fileExtension = extractFileExtension(param.getFileName());
        String fn = UUID.randomUUID().toString();
        byte[] data = null;
        try {
            data = Base64.getDecoder().decode(param.getData());
        } catch (IllegalArgumentException e) {
            logger.error(param.getData(), e);
            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
        }

        if (fileExtension == null) {
            fileExtension = determineImageExtension(data);
        }

        String fileName = String.format("%s/%s.%s", url, fn, fileExtension);
        File file = new File(fileName);
        mkdirs(file);

        if (logger.isDebugEnabled()) {
            logger.debug("Save picture {} which data in BASE64 type is {}",
                    fileName, param.getData());
        }

        if (!writeTo(file, data)) {
            LeaseException.throwSystemException(LeaseExceEnums.UNSUPPORTED_OPERATION);
        }

        SavePictureResponse response = new SavePictureResponse();
        response.setUrl(fileName);
        return response;
    }

    private static String determineImageExtension(byte[] imageData) {
        ImageType type = ImageUtil.getImageType(imageData);
        switch (type) {
            case INVALID:
                logger.info("Can not determine image type from image data.");
                LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
        }
        return type.name().toLowerCase();
    }

    private static boolean writeTo(File file, byte[] data) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            writeTo(fos, data);
            return true;
        } catch (IOException e) {
            logger.error(file.getAbsolutePath(), e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    logger.error(file.getAbsolutePath(), e);
                }
            }
        }
        return false;
    }

    private static boolean writeTo(OutputStream out, byte[] data) throws IOException {
        out.write(data);
        return true;
    }

    private static boolean mkdirs(File file) {
        File parent = file.getParentFile();
        return parent.mkdirs();
    }

    private static String extractFileExtension(String fn) {
        if (fn == null) {
            return null;
        }
        int pos = fn.lastIndexOf(".");
        if (pos == -1) {
            return null;
        }

        return fn.substring(pos + 1);
    }

}
