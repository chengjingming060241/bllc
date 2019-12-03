package com.gizwits.lease.util;

import com.google.code.kaptcha.GimpyEngine;

import java.awt.image.BufferedImage;

public class NoDistortion implements GimpyEngine {
    @Override
    public BufferedImage getDistortedImage(BufferedImage bufferedImage) {
        return bufferedImage;
    }
}
