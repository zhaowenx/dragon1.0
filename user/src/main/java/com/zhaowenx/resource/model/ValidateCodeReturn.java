package com.zhaowenx.resource.model;

import java.awt.image.BufferedImage;

public class ValidateCodeReturn {
    /**
     * 验证码
     */
    private String validateCode;
    /**
     * 验证码
     */
    private BufferedImage validateCodeImg;

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public BufferedImage getValidateCodeImg() {
        return validateCodeImg;
    }

    public void setValidateCodeImg(BufferedImage validateCodeImg) {
        this.validateCodeImg = validateCodeImg;
    }

}
