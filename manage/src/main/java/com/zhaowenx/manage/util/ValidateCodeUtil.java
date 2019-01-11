package com.zhaowenx.manage.util;

import com.zhaowenx.manage.model.ValidateCodeReturn;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by zhaowenx on 2018/8/26.
 */
public class ValidateCodeUtil {
    // 验证码可选字符
    private static char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    public static ValidateCodeReturn getValidateCode(int width, int height, int codeCount, Color backgroundColor) {
        // 定义图像buffer
        BufferedImage buffImg = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为背景色
        g.setColor(backgroundColor);
        g.fillRect(0, 0, width, height);
        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Fixedsys", Font.BOLD, height - 2);
        // 设置字体。
        g.setFont(font);
        // 画边框。
        g.setColor(backgroundColor);
        g.drawRect(0, 0, width - 1, height - 1);
        // 随机产生10条干扰线，使图象中的认证码不易被其它程序探测到。
        for (int i = 0; i < 10; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            g.drawLine(x, y, x + xl, y + yl);
        }
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();
        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。
            String strRand = String.valueOf(codeSequence[random.nextInt(34)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            // 用随机产生的颜色将验证码绘制到图像中。
            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            g.drawString(strRand, i * width / codeCount, height - 4);
            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }
        ValidateCodeReturn validateCodeReturn = new ValidateCodeReturn();
        validateCodeReturn.setValidateCode(randomCode.toString());
        validateCodeReturn.setValidateCodeImg(buffImg);
        return validateCodeReturn;
    }
}
