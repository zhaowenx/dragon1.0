package com.zhaowenx.manage.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;

/**
 * 3DES加密工具类
 *
 * @author 感谢原始作者liufeng
 * @date 2014-7-9
 */
public class EncodeDes3 {
    /**
     * 密钥
     */
    private final static String SECRET_KEY = "0123456789abcd0123456789";
    /**
     * 向量
     */
    private final static String IV = "12345678";
    /**
     * 加解密统一使用的编码方式
     */
    private final static String ENCODING = "UTF-8";

    public static void main(String[] args) throws Exception {
        // 实现3DES加密算法，工作模式CBC，填充模式PKCS5
        String str = "123456";
        String encstr = encode(str);
        System.out.println("加密后：" + encstr);
        System.out.println("解密后：" + decode("ztNv+z/tbQs="));
    }

    /**
     * 加密
     *
     * @param plainText
     *            普通文本
     * @return
     * @throws Exception
     */
    public static String encode(String plainText) throws Exception {
        return encode(plainText, SECRET_KEY);
    }

    public static String encode(String plainText, String secretKey)
            throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, deskey,ips);
        byte[] encryptData = cipher.doFinal(plainText.getBytes(ENCODING));
        return Base64.encode(encryptData);
    }

    /**
     * 解密
     *
     * @param encryptText
     *            加密文本
     * @return
     * @throws Exception
     */
    public static String decode(String plainText) throws Exception {
        if (StringUtils.isBlank(plainText)) {
            return "";
        }
        return decode(plainText, SECRET_KEY);
    }

    public static String decode(String encryptText, String secretKey)
            throws Exception {
        if (StringUtils.isBlank(encryptText)||StringUtils.isBlank(secretKey)) {
            return "";
        }
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/NoPadding");
        IvParameterSpec ips = new IvParameterSpec(
                IV.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, deskey,ips);

        byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));

        return new String(decryptData, ENCODING).trim();
    }
}
