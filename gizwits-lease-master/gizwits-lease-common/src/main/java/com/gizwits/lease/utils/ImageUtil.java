package com.gizwits.lease.utils;

import com.gizwits.lease.enums.ImageType;

/**
 * @author yuqing
 * @date 2018-05-24
 */
public class ImageUtil {

    /**
     * 根据图片二进制数据获得其格式
     * @param imageData
     * @return
     */
    public static ImageType getImageType(byte[] imageData) {
        // see https://oroboro.com/image-format-magic-bytes/
        if (imageData.length < 16) {
            return ImageType.INVALID;
        }

        int fd = uint(imageData[0]);

        switch (fd) {
            case 0xFF:
                if (strncmp(imageData,
                        new byte[]{(byte)0xFF, (byte)0xDB, (byte)0xFF},
                        0)) {
                    return ImageType.JPG;
                }
                break;
            case 0x89:
                if (strncmp(imageData,
                        new byte[]{(byte)0x89, (byte)0x50, (byte)0x4E, (byte)0x47
                                , (byte)0x0D, (byte)0x0A, (byte)0x1A, (byte)0x0A},
                        0)) {
                    return ImageType.PNG;
                }
                break;
            case 'G':
                if (strncmp(imageData,
                        "GIF87a",
                        0)
                        || strncmp(imageData,
                        "GIF89a",
                        0)) {
                    return ImageType.GIF;
                }
                break;
            case 'I':
                if (strncmp(imageData,
                        new byte[]{(byte)0x49, (byte)0x49, (byte)0x2A, (byte)0x00},
                        0)) {
                    return ImageType.TIFF;
                }
                break;
            case 'M':
                if (strncmp(imageData,
                        new byte[]{(byte)0x4D, (byte)0x4D, (byte)0x00, (byte)0x2A},
                        0)) {
                    return ImageType.TIFF;
                }
                break;
            case 'B':
                if (imageData[1] == 'M') {
                    return ImageType.BMP;
                }
                break;
            case 'R':
                if (strncmp(imageData,
                        "RIFF",
                        0)) {
                    return ImageType.WEBP;
                }
                if (strncmp(imageData,
                        "WEBP".getBytes(),
                        0,
                        4)) {
                    return ImageType.WEBP;
                }
                break;
            case 0:
                if (strncmp(imageData,
                        new byte[]{(byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00},
                        0)
                        || strncmp(imageData,
                        new byte[]{(byte)0x00, (byte)0x00, (byte)0x02, (byte)0x00},
                        0)) {
                    return ImageType.ICO;
                }
                break;
        }

        return ImageType.INVALID;
    }

    private static int uint(byte d) {
        return d & 0xFF;
    }

    private static boolean strncmp(byte[] imageData, String chars, int length) {
        byte[] srcs = chars.getBytes();

        return strncmp(imageData, srcs, length);
    }

    private static boolean strncmp(byte[] imageData, byte[] srcs, int length) {
        return strncmp(imageData, srcs, length, 0);
    }

    private static boolean strncmp(byte[] imageData, byte[] srcs, int length, int start) {
        int i = 0;
        if (length <= 0) {
            length = srcs.length;
        }
        for (; i < length && i < srcs.length && i + start < imageData.length; i++) {
            if (srcs[i] != imageData[i +  start]) {
                break;
            }
        }

        if (i == length) {
            return true;
        }
        return false;
    }
}
