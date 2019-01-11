//package com.zhaowenx.manage.util;
//
//import com.alibaba.fastjson.JSONObject;
//import com.zhaowenx.manage.constant.CookieNameConstant;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//
///**
// * Created by zhaowenx on 2018/8/26.
// */
//public class UserUtil {
//
//    private final static Logger logger = LoggerFactory.getLogger(UserUtil.class);
//
//    public static UserVo getLoginUser(HttpServletRequest req,RedisUtil redisUtil){
//        Cookie[] cookies = req.getCookies();
//        if (cookies == null) {
//            logger.error("获取cookie失败");
//            return null;
//        }
//        String redisKey = "";
//        for (Cookie cookie : cookies) {
//            // 找到匹配的COOKIE信息
//            if (CookieNameConstant.LOGIN_COOKIE.equals(cookie.getName())) {
//                redisKey = cookie.getValue();
//            }
//        }
//        if(StringUtils.isBlank(redisKey)){
//            logger.error("获取redisKey失败");
//            return null;
//        }
//        String userVoJson = redisUtil.get(redisKey);
//        UserVo userVo = JSONObject.parseObject(userVoJson,UserVo.class);
//        if(userVo == null){
//            logger.error("该用户未登录");
//            return null;
//        }
//        return userVo;
//    }
//
//    public static void refreshUser(UserVo userVo,RedisUtil redisUtil){
//        redisUtil.remove(CookieNameConstant.LOGIN_COOKIE);
//        redisUtil.set(CookieNameConstant.LOGIN_COOKIE,userVo);
//    }
//
//    public static Integer getUserId(HttpServletRequest req,RedisUtil redisUtil){
//        Cookie[] cookies = req.getCookies();
//        if (cookies == null) {
//            logger.error("获取cookie失败");
//            return null;
//        }
//        String redisKey = "";
//        for (Cookie cookie : cookies) {
//            // 找到匹配的COOKIE信息
//            if (CookieNameConstant.LOGIN_COOKIE.equals(cookie.getName())) {
//                redisKey = cookie.getValue();
//            }
//        }
//        if(StringUtils.isBlank(redisKey)){
//            logger.error("获取redisKey失败");
//            return null;
//        }
//        String userVoJson = redisUtil.get(redisKey);
//        UserVo userVo = JSONObject.parseObject(userVoJson,UserVo.class);
//        if(userVo == null){
//            logger.error("该用户未登录");
//            return null;
//        }
//        logger.info("userId:"+userVo.getId());
//        return userVo.getId();
//    }
//}
