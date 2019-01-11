package com.zhaowenx.manage.util;

import java.util.regex.Pattern;

/**
 * Created by zhaowenx on 2018/8/30.
 */
public class CommenUtil {

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
