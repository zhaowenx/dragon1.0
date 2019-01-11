package com.zhaowenx.manage.util;

import java.util.Random;

/**
 * Created by zhaowenx on 2018/8/26.
 */
public class RandomUtil {

    /**
     * 生成随机数
     * @param max 随机数的位数
     * @return
     */
    public static String createRandom(int max){
        Random random = new Random();
        StringBuffer randomNum = new StringBuffer();
        for (int i = 0; i < max; i++) {
            randomNum.append(random.nextInt(10));
        }
        return randomNum.toString();
    }
}
