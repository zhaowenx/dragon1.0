/**
 * DateTimeUtil.java
 * Date：12-4-18
 * Time: 上午9:58
 * Copyright 途牛科技有限公司 2012 版权所有
 */
package com.zhaowenx.manage.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhaowenx on 2018/8/23.
 * 时间处理类
 */
public class DateTimeUtil {

    public static final int TIME_DAY_MILLISECOND = 86400000;
    public static final String TIME_FORMAT_PUBLIC = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_PAY = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_PAY_2Y = "yyMMddHHmmss";
    public static final String DATE_FORMAT_MS = "yyyyMMddHHmmssSSS";
    /**
     * change by bbq
     */
    public static final String DT_SIMPLE = "yyyy-MM-dd";
    private static final Logger LOGGER = LoggerFactory.getLogger(DateTimeUtil.class);
    /**
     * 定义时间日期显示格式
     */
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATE_FORMAT_CN = "yyyy年MM月dd日";
    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String TIME_FORMAT_CN = "yyyy年MM月dd日 HH:mm:ss";
    private static final String MONTH_FORMAT = "yyyy-MM";
    private static final String DAY_FORMAT = "yyyyMMdd";
    private static final String TIMESTAMP_FORMAT = "HHmmss";
    public static final String DATE_HOUR = "yyyyMMddHH";
    // private final static String TIME_FORMAT_MILLI = "yyyy-MM-dd
    // HH:mm:ss.SSS";

    private DateTimeUtil() {

    }

    /**
     * 取得当前系统时间，返回java.util.Date类型
     *
     * @return java.util.Date 返回服务器当前系统时间
     * @see Date
     */
    public static Date getCurrDate() {
        return new Date();
    }

    /**
     * 取得当前系统时间戳
     *
     * @return java.sql.Timestamp 系统时间戳
     * @see java.sql.Timestamp
     */
    public static java.sql.Timestamp getCurrTimestamp() {
        return new java.sql.Timestamp(System.currentTimeMillis());
    }

    /**
     * 将2007-12-1变成2007-12-01。将2007-9-1变为2007-09-01。
     *
     * @param date
     * @return
     */
    public static String getFormatDateV2(String date) {
        if (date == null) {
            return null;
        }

        String[] datearr = StringUtils.split(date, "-");
        int length = 3;
        if (datearr == null || datearr.length != length) {
            return date;
        }

        StringBuffer ret = new StringBuffer();
        ret.append(datearr[0]);
        ret.append("-");
        ret.append(Integer.parseInt(datearr[1]) < 10 ? "0" + Integer.parseInt(datearr[1]) : datearr[1]);
        ret.append("-");
        ret.append(Integer.parseInt(datearr[2]) < 10 ? "0" + Integer.parseInt(datearr[2]) : datearr[2]);
        return ret.toString();
    }

    /**
     * 从时间串中获取小时数。
     *
     * @param timestr "2007-10-12 13:25:00"
     * @return
     */
    public static int getHourFromTimeString(String timestr) {
        if (StringUtils.isBlank(timestr)) {
            return 0;
        }

        return Integer.parseInt(timestr.substring(timestr.length() - 8, timestr.length() - 6));
    }

    /**
     * 返回当前时间是上午还是下午
     *
     * @return
     * @author lenghao
     * @createTime 2008-8-2 下午04:22:07
     * @see Calendar.AM 0
     * @see Calendar.PM 1
     */
    public static Integer getCurrDateAMorPM() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.AM_PM);
    }

    /**
     * 得到格式化后的日期，格式为yyyy-MM-dd，如2006-02-15
     *
     * @param currDate 要格式化的日期
     * @return String 返回格式化后的日期，默认格式为为yyyy-MM-dd，如2006-02-15
     * @see #getFormatDate(Date, String)
     */
    public static String getFormatDate(Date currDate) {
        return getFormatDate(currDate, DATE_FORMAT);
    }

    /**
     * 得到格式化后的日期，格式为yyyy-MM-dd，如2006-02-15
     *
     * @param currDate 要格式化的日期
     * @return Date 返回格式化后的日期，默认格式为为yyyy-MM-dd，如2006-02-15
     * @see #getFormatDate(Date)
     */
    public static Date getFormatDateToDate(Date currDate) {
        return getFormatDate(getFormatDate(currDate));
    }

    /**
     * 得到格式化后的日期，格式为yyyy年MM月dd日，如2006年02月15日
     *
     * @param currDate 要格式化的日期
     * @return String 返回格式化后的日期，默认格式为yyyy年MM月dd日，如2006年02月15日
     * @see #getFormatDate(Date, String)
     */
    public static String getFormatDateCn(Date currDate) {
        return getFormatDate(currDate, DATE_FORMAT_CN);
    }

    /**
     * 得到格式化后的日期，格式为yyyy年MM月dd日，如2006年02月15日
     *
     * @param currDate 要格式化的日期
     * @return Date 返回格式化后的日期，默认格式为yyyy年MM月dd日，如2006年02月15日
     * @see #getFormatDate_CN(String)
     */
    public static Date getFormatDateToDateCn(Date currDate) {
        return getFormatDateCn(getFormatDateCn(currDate));
    }

    /**
     * 得到格式化后的日期，格式为yyyy-MM-dd，如2006-02-15
     *
     * @param currDate 要格式化的日期
     * @return Date 返回格式化后的日期，默认格式为yyyy-MM-dd，如2006-02-15
     * @see #getFormatDate(String, String)
     */
    public static Date getFormatDate(String currDate) {
        return getFormatDate(currDate, DATE_FORMAT);
    }

    /**
     * 得到格式化后的日期，格式为yyyy年MM月dd日，如2006年02月15日
     *
     * @param currDate 要格式化的日期
     * @return 返回格式化后的日期，默认格式为yyyy年MM月dd日，如2006年02月15日
     * @see #getFormatDate(String, String)
     */
    public static Date getFormatDateCn(String currDate) {
        return getFormatDate(currDate, DATE_FORMAT_CN);
    }

    /**
     * 根据格式得到格式化后的日期
     *
     * @param currDate 要格式化的日期
     * @param format   日期格式，如yyyy-MM-dd
     * @return String 返回格式化后的日期，格式由参数<code>format</code>
     * 定义，如yyyy-MM-dd，如2006-02-15
     * @see SimpleDateFormat#format(Date)
     */
    public static String getFormatDate(Date currDate, String format) {
        if (currDate == null) {
            return "";
        }
        SimpleDateFormat dtFormatdB = null;
        try {
            dtFormatdB = new SimpleDateFormat(format);
            return dtFormatdB.format(currDate);
        } catch (Exception e) {
            dtFormatdB = new SimpleDateFormat(DATE_FORMAT);
            try {
                return dtFormatdB.format(currDate);
            } catch (Exception ex) {
            }
        }
        return null;
    }

    /**
     * 得到格式化后的时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
     *
     * @param currDate 要格式化的时间
     * @return String 返回格式化后的时间，默认格式为yyyy-MM-dd HH:mm:ss，如2006-02-15
     * 15:23:45
     * @see #getFormatDateTime(Date, String)
     */
    public static String getFormatDateTime(Date currDate) {
        return getFormatDateTime(currDate, TIME_FORMAT);
    }

    /**
     * 得到格式化后的时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
     *
     * @param currDate 要格式环的时间
     * @return Date 返回格式化后的时间，默认格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
     * @see #getFormatDateTime(String)
     */
    public static Date getFormatDateTimeToTime(Date currDate) {
        return getFormatDateTime(getFormatDateTime(currDate));
    }

    /**
     * 得到格式化后的时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
     *
     * @param currDate 要格式化的时间
     * @return Date 返回格式化后的时间，默认格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
     * @see #getFormatDateTime(String, String)
     */
    public static Date getFormatDateTime(String currDate) {
        return getFormatDateTime(currDate, TIME_FORMAT);
    }

    public static Date getFormatDayTime(String currDate) {
        return getFormatDateTime(currDate, DAY_FORMAT);
    }

    /**
     * 得到格式化后的时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
     *
     * @param currDate 要格式化的时间
     * @return String 返回格式化后的时间，默认格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日
     * 15:23:45
     * @see #getFormatDateTime(Date, String)
     */
    public static String getFormatDateTimeCn(Date currDate) {
        return getFormatDateTime(currDate, TIME_FORMAT_CN);
    }

    public static String getFormatDateTimePay(Date currDate) {
        return getFormatDateTime(currDate, DATE_FORMAT_PAY);
    }

    public static String getFormatDateTimeMs(Date currDate) {
        return getFormatDateTime(currDate, DATE_FORMAT_MS);
    }

    public static String getFormatDateTimePay2Y(Date currDate) {
        return getFormatDateTime(currDate, DATE_FORMAT_PAY_2Y);
    }

    /**
     * 得到格式化后的时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
     *
     * @param currDate 要格式化的时间
     * @return Date 返回格式化后的时间，默认格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日
     * 15:23:45
     * @see #getFormatDateTime_CN(String)
     */
    public static Date getFormatDateTimeToTimeCn(Date currDate) {
        return getFormatDateTimeCn(getFormatDateTimeCn(currDate));
    }

    /**
     * 得到格式化后的时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
     *
     * @param currDate 要格式化的时间
     * @return Date 返回格式化后的时间，默认格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日
     * 15:23:45
     * @see #getFormatDateTime(String, String)
     */
    public static Date getFormatDateTimeCn(String currDate) {
        return getFormatDateTime(currDate, TIME_FORMAT_CN);
    }

    /**
     * 根据格式得到格式化后的时间
     *
     * @param currDate 要格式化的时间
     * @param format   时间格式，如yyyy-MM-dd HH:mm:ss
     * @return String 返回格式化后的时间，格式由参数<code>format</code>定义，如yyyy-MM-dd
     * HH:mm:ss
     * @see SimpleDateFormat#format(Date)
     */
    public static String getFormatDateTime(Date currDate, String format) {
        if (currDate == null) {
            return "";
        }
        SimpleDateFormat dtFormatdB = null;
        try {
            dtFormatdB = new SimpleDateFormat(format);
            return dtFormatdB.format(currDate);
        } catch (Exception e) {
            dtFormatdB = new SimpleDateFormat(TIME_FORMAT);
            try {
                return dtFormatdB.format(currDate);
            } catch (Exception ex) {
            }
        }
        return "";
    }

    /**
     * 根据格式得到格式化后的日期
     *
     * @param currDate 要格式化的日期
     * @param format   日期格式，如yyyy-MM-dd
     * @return Date 返回格式化后的日期，格式由参数<code>format</code>
     * 定义，如yyyy-MM-dd，如2006-02-15
     * @see SimpleDateFormat#parse(String)
     */
    public static Date getFormatDate(String currDate, String format) {
        if (currDate == null) {
            return null;
        }
        SimpleDateFormat dtFormatdB = null;
        try {
            dtFormatdB = new SimpleDateFormat(format);
            return dtFormatdB.parse(currDate);
        } catch (Exception e) {
            dtFormatdB = new SimpleDateFormat(DATE_FORMAT);
            try {
                return dtFormatdB.parse(currDate);
            } catch (Exception ex) {
            }
        }
        return null;
    }

    /**
     * 根据格式得到格式化后的时间
     *
     * @param currDate 要格式化的时间
     * @param format   时间格式，如yyyy-MM-dd HH:mm:ss
     * @return Date 返回格式化后的时间，格式由参数<code>format</code>定义，如yyyy-MM-dd
     * HH:mm:ss
     * @see SimpleDateFormat#parse(String)
     */
    public static Date getFormatDateTime(String currDate, String format) {
        if (currDate == null) {
            return null;
        }
        SimpleDateFormat dtFormatdB = null;
        try {
            dtFormatdB = new SimpleDateFormat(format);
            return dtFormatdB.parse(currDate);
        } catch (Exception e) {
            dtFormatdB = new SimpleDateFormat(TIME_FORMAT);
            try {
                return dtFormatdB.parse(currDate);
            } catch (Exception ex) {
            }
        }
        return null;
    }

    /**
     * 得到本日的上月时间 如果当日为2007-9-1,那么获得2007-8-1
     */
    public static String getDateBeforeMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        return getFormatDate(cal.getTime(), DATE_FORMAT);
    }

    /**
     * 得到本日的前几个月时间 如果number=2当日为2007-9-1,那么获得2007-7-1
     */
    public static String getDateBeforeMonth(int number) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -number);
        return getFormatDate(cal.getTime(), DATE_FORMAT);
    }

    public static long getDaysOfDates(Date first, Date second) {
        Date d1 = getFormatDateTime(getFormatDate(first), DATE_FORMAT);
        Date d2 = getFormatDateTime(getFormatDate(second), DATE_FORMAT);

        long dl1 = 0;
        long dl2 = 0;
        if(d1 != null){
        	dl1 = d1.getTime();
        }
        if(d2 != null){
        	dl2 = d2.getTime();
        }
        long mils = dl1 - dl2;

        return mils / (TIME_DAY_MILLISECOND);
    }

    /**
     * 获得两个Date型日期之间相差的天数（第2个减第1个）
     *
     * @param first, Date second
     * @return int 相差的天数
     */
    public static int getDaysBetweenDates(Date first, Date second) {
        Date d1 = getFormatDateTime(getFormatDate(first), DATE_FORMAT);
        Date d2 = getFormatDateTime(getFormatDate(second), DATE_FORMAT);

        long dl1 = 0;
        long dl2 = 0;
        if(d1 != null){
        	dl1 = d1.getTime();
        }
        if(d2 != null){
        	dl2 = d2.getTime();
        }

        Long mils = (dl2 - dl1) / (TIME_DAY_MILLISECOND);

        return mils.intValue();
    }

    /**
     * 获得两个String型日期之间相差的天数（第2个减第1个）
     *
     * @param first, String second
     * @return int 相差的天数
     */
    public static int getDaysBetweenDates(String first, String second) {
        Date d1 = getFormatDateTime(first, DATE_FORMAT);
        Date d2 = getFormatDateTime(second, DATE_FORMAT);

        long dl1 = 0;
        long dl2 = 0;
        if(d1 != null){
        	dl1 = d1.getTime();
        }
        if(d2 != null){
        	dl2 = d2.getTime();
        }

        Long mils = (dl2 - dl1) / (TIME_DAY_MILLISECOND);

        return mils.intValue();
    }

    /**
     * @param first
     * @param second
     * @return 获取两个Date之间的天数的列表
     * @author lenghao
     * @createTime 2008-8-5 下午01:57:09
     */
    public static List<Date> getDaysListBetweenDates(Date first, Date second) {
        List<Date> dateList = new ArrayList<>();
        Date d1 = getFormatDateTime(getFormatDate(first), DATE_FORMAT);
        Date d2 = getFormatDateTime(getFormatDate(second), DATE_FORMAT);

        if(d1 != null){
        	if (d1.compareTo(d2) > 0) {
                return dateList;
            }
        }
        do {
            dateList.add(d1);
            d1 = getDateBeforeOrAfter(d1, 1);
        } while (d1.compareTo(d2) <= 0);
        return dateList;
    }

    /**
     *
     *
     */
    public static String getDateBeforeDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        return getFormatDate(cal.getTime(), DATE_FORMAT);
    }

    /**
     * 得到格式化后的当前系统日期，格式为yyyy-MM-dd，如2006-02-15
     *
     * @return String 返回格式化后的当前服务器系统日期，格式为yyyy-MM-dd，如2006-02-15
     * @see #getFormatDate(Date)
     */
    public static String getCurrDateStr() {
        return getFormatDate(getCurrDate());
    }

    /**
     * 得到格式化后的当前系统时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
     *
     * @return String 返回格式化后的当前服务器系统时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15
     * 15:23:45
     * @see #getFormatDateTime(Date)
     */
    public static String getCurrDateTimeStr() {
        return getFormatDateTime(getCurrDate());
    }

    /**
     * 得到格式化后的当前系统日期，格式为yyyy年MM月dd日，如2006年02月15日
     *
     * @return String 返回当前服务器系统日期，格式为yyyy年MM月dd日，如2006年02月15日
     * @see #getFormatDate(Date, String)
     */
    public static String getCurrDateStrCn() {
        return getFormatDate(getCurrDate(), DATE_FORMAT_CN);
    }

    /**
     * 得到格式化后的当前系统时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
     *
     * @return String 返回格式化后的当前服务器系统时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日
     * 15:23:45
     * @see #getFormatDateTime(Date, String)
     */
    public static String getCurrDateTimeStrCn() {
        return getFormatDateTime(getCurrDate(), TIME_FORMAT_CN);
    }

    /**
     * 得到系统当前日期的前或者后几天
     *
     * @param iDate 如果要获得前几天日期，该参数为负数； 如果要获得后几天日期，该参数为正数
     * @return Date 返回系统当前日期的前或者后几天
     * @see Calendar#add(int, int)
     */
    public static Date getDateBeforeOrAfter(int iDate) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, iDate);
        return cal.getTime();
    }

    /**
     * 得到日期的前或者后几天
     *
     * @param iDate 如果要获得前几天日期，该参数为负数； 如果要获得后几天日期，该参数为正数
     * @return Date 返回参数<code>curDate</code>定义日期的前或者后几天
     * @see Calendar#add(int, int)
     */
    public static Date getDateBeforeOrAfter(Date curDate, int iDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        cal.add(Calendar.DAY_OF_MONTH, iDate);
        return cal.getTime();
    }

    /**
     * 得到格式化后的月份，格式为yyyy-MM，如2006-02
     *
     * @param currDate 要格式化的日期
     * @return String 返回格式化后的月份，格式为yyyy-MM，如2006-02
     * @see #getFormatDate(Date, String)
     */
    public static String getFormatMonth(Date currDate) {
        return getFormatDate(currDate, MONTH_FORMAT);
    }

    /**
     * 得到格式化后的日，格式为yyyyMMdd，如20060210
     *
     * @param currDate 要格式化的日期
     * @return String 返回格式化后的日，格式为yyyyMMdd，如20060210
     * @see #getFormatDate(Date, String)
     */
    public static String getFormatDay(Date currDate) {
        return getFormatDate(currDate, DAY_FORMAT);
    }

    public static String getTimestampDay(Date currDate) {
        return getFormatDate(currDate, TIMESTAMP_FORMAT);
    }

    /**
     * 得到格式化后的当月第一天，格式为yyyy-MM-dd，如2006-02-01
     *
     * @return String 返回格式化后的当月第一天，格式为yyyy-MM-dd，如2006-02-01
     * @see Calendar#getMinimum(int)
     * @see #getFormatDate(Date, String)
     */
    public static String getFirstDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        int firstDay = cal.getMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        return getFormatDate(cal.getTime(), DATE_FORMAT);
    }

    /**
     * 得到格式化后的下月第一天，格式为yyyy-MM-dd，如2006-02-01
     *
     * @return String 返回格式化后的下月第一天，格式为yyyy-MM-dd，如2006-02-01
     * @see Calendar#getMinimum(int)
     * @see #getFormatDate(Date, String)
     */
    public static String getFirstDayOfNextMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, +1);
        int firstDay = cal.getMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        return getFormatDate(cal.getTime(), DATE_FORMAT);
    }

    /**
     * 得到格式化后的当月第一天，格式为yyyy-MM-dd，如2006-02-01
     *
     * @param currDate 要格式化的日期
     * @return String 返回格式化后的当月第一天，格式为yyyy-MM-dd，如2006-02-01
     * @see Calendar#getMinimum(int)
     * @see #getFormatDate(Date, String)
     */
    public static String getFirstDayOfMonth(Date currDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currDate);
        int firstDay = cal.getMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        return getFormatDate(cal.getTime(), DATE_FORMAT);
    }

    /**
     * 得到格式化后的当月最后一天，格式为yyyy-MM-dd，如2006-02-28
     *
     * @param currDate 要格式化的日期
     * @return String 返回格式化后的当月最后一天，格式为yyyy-MM-dd，如2006-02-28
     * @see Calendar#getMinimum(int)
     * @see #getFormatDate(Date, String)
     */
    public static String getLastDayOfMonth(Date currDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currDate);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        return getFormatDate(cal.getTime(), DATE_FORMAT);
    }

    /**
     * 得到格式化后的当月最后一天，格式为yyyy-MM-dd，如2006-02-28
     *
     * @return String 返回格式化后的当月最后一天，格式为yyyy-MM-dd，如2006-02-28
     * @see Calendar#getMinimum(int)
     * @see #getFormatDate(Date, String)
     */
    public static String getLastDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        return getFormatDate(cal.getTime(), DATE_FORMAT);
    }

    /**
     * 得到日期的前或者后几小时
     *
     * @param iHour 如果要获得前几小时日期，该参数为负数； 如果要获得后几小时日期，该参数为正数
     * @return Date 返回参数<code>curDate</code>定义日期的前或者后几小时
     * @see Calendar#add(int, int)
     */
    public static Date getDateBeforeOrAfterHours(Date curDate, int iHour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        cal.add(Calendar.HOUR_OF_DAY, iHour);
        return cal.getTime();
    }

    /**
     * 判断日期是否在当前周内
     *
     * @param curDate
     * @param compareDate
     * @return
     */
    public static boolean isSameWeek(Date curDate, Date compareDate) {
        if (curDate == null || compareDate == null) {
            return false;
        }

        Calendar calSun = Calendar.getInstance();
        calSun.setTime(getFormatDateToDate(curDate));
        calSun.set(Calendar.DAY_OF_WEEK, 1);

        Calendar calNext = Calendar.getInstance();
        calNext.setTime(calSun.getTime());
        calNext.add(Calendar.DATE, 7);

        Calendar calComp = Calendar.getInstance();
        calComp.setTime(compareDate);
        if (calComp.after(calSun) && calComp.before(calNext)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 时间查询时,结束时间的 23:59:59
     */
    public static String addDateEndfix(String datestring) {
        if ((datestring == null) || "".equals(datestring)) {
            return null;
        }
        return datestring + " 23:59:59";
    }

    /**
     * 返回格式化的日期
     *
     * @param dateStr 格式"yyyy-MM-dd 23:59:59";
     * @return
     */
    public static Date getFormatDateEndfix(String dateStr) {
        dateStr = addDateEndfix(dateStr);
        return getFormatDateTime(dateStr);
    }

    /**
     * 返回格式化的日期
     *
     * @param datePre 格式"yyyy-MM-dd HH:mm:ss";
     * @return
     */
    public static Date formatEndTime(String datePre) {
        if (datePre == null) {
            return null;
        }
        String dateStr = addDateEndfix(datePre);
        return getFormatDateTime(dateStr);
    }

    /**
     * date1加上compday天数以后的日期与当前时间比较，如果大于当前时间返回true，否则false
     *
     * @param date1
     * @param compday
     * @return
     */
    public static Boolean compareDay(Date date1, int compday) {
        if (date1 == null) {
            return false;
        }
        Date dateComp = getDateBeforeOrAfter(date1, compday);
        Date nowdate = new Date();
        if (dateComp.after(nowdate)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * date1加上compday天数以后的日期与date2比较，如果大于date2返回true，否则false
     *
     * @param date1
     * @param compday
     * @return
     */
    public static Boolean compareDay(Date date1, int compday, Date date2) {
        if (date1 == null) {
            return false;
        }
        Date dateComp = getDateBeforeOrAfter(date1, compday);
        if (dateComp.after(date2)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 进行时段格式转换，对于输入的48位的01串，将进行如下操作： <li>
     * 1.先将输入中每个0变成两个0，每个1变成2个1，形成一个96位的二进制串。</li> <li>
     * 2.将上述的96位的二进制串分成3组，每组32位。</li> <li>3.将每个32位的二进制串转换成一个8位的16进制串。</li>
     * <li>4.将3个8位的16进制串合并成一个串，中间以","分割。</li>
     *
     * @param timespan 一个48位的二进制串，如："011111111011111111111111111111111111111111111110"
     * @return 一个16进制串，每位间以","分割。如："3fffcfff,ffffffff,fffffffc"
     */
    public static String convertBinaryTime2Hex(String timespan) {
        if (timespan == null || "".equals(timespan)) {
            return "";
        }


        StringBuilder ret = new StringBuilder("");
        StringBuilder tmp = new StringBuilder("");
        for (int i = 0; i < timespan.length(); i++) {
            tmp.append(timespan.charAt(i));
            tmp.append(timespan.charAt(i));
            if ((i + 1) % 16 == 0) {
                if (!"".equals(ret)) {
                    ret.append(",");
                }
                Long t = Long.parseLong(tmp.toString(), 2);
                StringBuilder hexStr = new StringBuilder(Long.toHexString(t));
                int lengthMax = 8;
                if (hexStr.length() < lengthMax) {
                    int length = hexStr.length();
                    for (int n = 0; n < lengthMax - length; n++) {
                        hexStr = new StringBuilder("0").append(hexStr);
                    }
                }
                ret.append(hexStr);
                tmp = new StringBuilder("");
            }
        }
        return ret.toString();
    }

    /**
     * 进行时段格式转换，将输入的26位的2进制串转换成48位的二进制串。
     *
     * @param timespan 一个16进制串，每位间以","分割。如："3fffcfff,ffffffff,fffffffc"
     * @return 一个48位的二进制串，如："011111111011111111111111111111111111111111111110"
     */
    public static String convertHexTime2Binary(String timespan) {
        if (timespan == null || "".equals(timespan)) {
            return "";
        }

        String tmp = "";
        String ret = "";
        String[] strArr = timespan.split(",");
        for (int i = 0; i < strArr.length; i++) {
            StringBuilder binStr =new StringBuilder(Long.toBinaryString(Long.parseLong(strArr[i], 16)));
            int lengthMax = 32;
            if (binStr.length() < lengthMax) {
                int length = binStr.length();
                for (int n = 0; n < lengthMax - length; n++) {
                    binStr = new StringBuilder("0").append(binStr);
                }
            }
            tmp += binStr;
        }

        int lengthMax = 48;
        for (int i = 0; i < lengthMax; i++) {
            ret += tmp.charAt(i * 2);
        }

        return ret;
    }

    /**
     * 进行时段格式转换，将输入的32位的10进制串转换成48位的二进制串。
     *
     * @param timespan 一个16进制串，每位间以","分割。如："1234567890,1234567890,1234567890c"
     * @return 一个48位的二进制串，如："011111111011111111111111111111111111111111111110"
     */
    public static String convertDecTime2Binary(String timespan) {
        if (timespan == null || "".equals(timespan)) {
            return "";
        }

        String tmp = "";
        String ret = "";
        String[] strArr = timespan.split(",");
        for (int i = 0; i < strArr.length; i++) {
            StringBuilder binStr =new StringBuilder(Long.toBinaryString(Long.parseLong(strArr[i], 10)));
            int lengthMax = 32;
            if (binStr.length() < lengthMax) {
                int length = binStr.length();
                for (int n = 0; n < lengthMax - length; n++) {
                    binStr = new StringBuilder("0").append(binStr);
                }
            }
            tmp += binStr;
        }

        int max = 48;
        for (int i = 0; i < max; i++) {
            ret += tmp.charAt(i * 2);
        }

        return ret;
    }

    /**
     * 进行时段格式转换，对于输入的48位的01串，将进行如下操作： <li>
     * 1.先将输入中每个0变成两个0，每个1变成2个1，形成一个96位的二进制串。</li> <li>
     * 2.将上述的96位的二进制串分成3组，每组32位。</li> <li>3.将每个32位的二进制串转换成一个10位的10进制串。</li>
     * <li>4.将3个8位的16进制串合并成一个串，中间以","分割。</li>
     *
     * @param timespan 一个48位的二进制串，如："011111111011111111111111111111111111111111111110"
     * @return 一个16进制串，每位间以","分割。如："1234567890,1234567890,1234567890"
     */
    public static String convertBinaryTime2Dec(String timespan) {
        if (timespan == null || "".equals(timespan)) {
            return "";
        }

        String ret = "";
        String tmp = "";
        for (int i = 0; i < timespan.length(); i++) {
            tmp += timespan.charAt(i);
            tmp += timespan.charAt(i);
            // tmp += i;
            if ((i + 1) % 16 == 0) {
                if (!"".equals(ret)) {
                    ret += ",";
                }
                Long t = Long.parseLong(tmp, 2);
                StringBuilder decStr = new StringBuilder(Long.toString(t));
                int lengthMax = 10;
                if (decStr.length() < lengthMax) {
                    int length = decStr.length();
                    for (int n = 0; n < lengthMax - length; n++) {
                        decStr = new StringBuilder("0").append(decStr);
                    }
                }

                ret += decStr;
                tmp = "";
            }
        }

        return ret;
    }

    /**
     * 计算指定日期+addMonth月+15号 返回格式"2008-02-15"
     *
     * @param date
     * @param addMonth
     * @param monthDay
     * @return
     */
    public static String genericSpecdate(Date date, int addMonth, int monthDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, addMonth);
        cal.set(Calendar.DAY_OF_MONTH, monthDay);
        return getFormatDate(cal.getTime(), DATE_FORMAT);
    }

    /**
     * 获得以今天为单位若干天以前或以后的日期的标准格式"Wed Feb 20 00:00:00 CST 2008"，是0点0分0秒。
     *
     * @param idx
     * @return
     */
    public static Date getDateBeforeOrAfterV2(int idx) {
        return getDateBeforeOrAfter(getFormatDateToDate(getCurrDate()), idx);
    }

    /**
     * 获得给定时间若干秒以前或以后的日期的标准格式。
     *
     * @param curDate
     * @param seconds
     * @return curDate
     */
    public static Date getSpecifiedDateTimeBySeconds(Date curDate, int seconds) {
        long time = (curDate.getTime() / 1000) + seconds;
        curDate.setTime(time * 1000);
        return curDate;
    }

    /**
     * 获得给定日期当天23点59分59秒的标准格式。
     *
     * @param curDate
     * @return curDate
     */
    /**public static Date getSpecifiedDateTime_235959(Date curDate) {
    	if(getFormatDateToDate(curDate) != null){
    		return getSpecifiedDateTimeBySeconds(getFormatDateToDate(curDate), 24 * 60 * 60 - 1);
    	}
        return null;
    }
      */
    public static String getSpecifiedDateTimeMonth(Date curDate) {
        return getFormatDateTime(curDate, "MM.dd");
    }

    /**
     * @param dt   传入日期，可以为空
     * @param diff 需要获取相隔diff天的日期 如果为正则取以后的日期，否则时间往前推
     * @return
     */
    public static String getDiffStringDate(Date dt, int diff) {
        Calendar ca = Calendar.getInstance();

        if (dt == null) {
            ca.setTime(new Date());
        } else {
            ca.setTime(dt);
        }

        ca.add(Calendar.DATE, diff);
        return dtSimpleFormat(ca.getTime());
    }

    /**
     * yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static final String dtSimpleFormat(Date date) {
        if (date == null) {
            return "";
        }

        return getFormat(DT_SIMPLE).format(date);
    }

    /**
     * SimpleDateFormat("yyyy-MM-dd HH:mm");
     */
    private static final DateFormat getFormat(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * 取得多个日期中间隔的最大天数
     *
     * @param startDateAndEndDate
     * @return
     * @author Alvise
     */
    public static int maxContinuousDays(Date[][] startDateAndEndDate) {
        // 冒泡排序
        for (int i = 0; i < startDateAndEndDate.length - 1; i++) {
            for (int j = 0; j < startDateAndEndDate.length - i - 1; j++) {
                if (DateTimeUtil.getDaysBetweenDates(startDateAndEndDate[j + 1][0],
                        startDateAndEndDate[j][0]) > 0) {
                    Date[] tempDate = startDateAndEndDate[j];
                    startDateAndEndDate[j] = startDateAndEndDate[j + 1];
                    startDateAndEndDate[j + 1] = tempDate;
                }
            }
        }


        // 合并连续的时间段
        int j = 0;
        Date[][] startDateAndEndDateNew = new Date[startDateAndEndDate.length][2];
        for (int i = 0; i < startDateAndEndDateNew.length; i++) {
            if (j >= startDateAndEndDate.length) {
                break;
            }

            startDateAndEndDateNew[i] = startDateAndEndDate[j];
            j++;
            while (j < startDateAndEndDate.length) {
                if (DateTimeUtil.getDaysBetweenDates(startDateAndEndDateNew[i][1],
                        startDateAndEndDate[j][0]) > 0) {
                    break;
                } else if (DateTimeUtil.getDaysBetweenDates(startDateAndEndDateNew[i][1],
                        startDateAndEndDate[j][1]) > 0) {
                    startDateAndEndDateNew[i][1] = startDateAndEndDate[j][1];
                    j++;
                } else if (DateTimeUtil.getDaysBetweenDates(startDateAndEndDateNew[i][1],
                        startDateAndEndDate[j][1]) <= 0) {
                    j++;
                }

            }
        }


        // 选择法排序
        int maxDays = 0;
        for (int i = 0; i < startDateAndEndDateNew.length - 1; i++) {
            Date curEndDate = startDateAndEndDateNew[i][1];
            Date nextStartDate = startDateAndEndDateNew[i + 1][0];
            if (curEndDate == null || nextStartDate == null) {
                break;
            }

            int temDays = DateTimeUtil.getDaysBetweenDates(curEndDate, nextStartDate);
            if (temDays > maxDays) {
                maxDays = temDays;
            }
        }
        return maxDays;
    }

    /**
     * 取得多个日期中间隔的最大天数,这里的参数是用 ","和";"分割的字符字符串例如 "2008-08-03,2008-08-04;"
     *
     * @param dateStr
     * @return
     * @author Alvise
     */
    public static int maxContinuousDays(String dateStr) {
        String[] seDate = dateStr.split(";");
        Date[][] startDateAndEndDate = new Date[seDate.length][2];

        for (int i = 0; i < seDate.length; i++) {
            String[] tempDate = seDate[i].split(",");
            startDateAndEndDate[i][0] = DateTimeUtil.getFormatDate(tempDate[0]);
            startDateAndEndDate[i][1] = DateTimeUtil.getFormatDate(tempDate[1]);
        }

        return maxContinuousDays(startDateAndEndDate);

    }

    /**
     * 将 HH:mm:ss 格式的时分秒，转化成秒数
     */
    public static int getSecondFromStr(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
        try {
            Date thisTime = sdf.parse("2015-10-01 " + time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(thisTime);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minue = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);
            return hour * 60 * 60 + minue * 60 + second;
        } catch (ParseException e) {
            LOGGER.error("时间转换发生错误");
        }
        return 0;
    }

    /**
     * 将 Date中时分秒，转化成秒数
     */
    public static int getSecondFromStr(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minue = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return hour * 60 * 60 + minue * 60 + second;
    }


    /**
     * 判断时间段1和时间段2是否有交集
     *
     * @param begintimeOne
     * @param endtimeOne
     * @param begintimeTwo
     * @param endtimeTwo
     * @return true:有交集,false:没有交集
     */
    public static boolean isConfilct(String begintimeOne, String endtimeOne, String begintimeTwo,
                                     String endtimeTwo) {
        Date beginOne = getFormatDate(begintimeOne);
        Date endOne = getFormatDate(endtimeOne);
        Date beginTwo = getFormatDate(begintimeTwo);
        Date endTwo = getFormatDate(endtimeTwo);
        if(beginOne != null && endOne != null && beginTwo != null && endTwo != null){
            boolean one= (beginOne.compareTo(beginTwo) <= 0 && endOne.compareTo(beginTwo) >= 0)
                    || (beginOne.compareTo(endTwo) <= 0 && endOne.compareTo(endTwo) >= 0)
                    || (beginTwo.compareTo(beginOne) <= 0 && endTwo.compareTo(beginOne) >= 0)
                    || (beginTwo.compareTo(endOne) <= 0 && endTwo.compareTo(endOne) >= 0);
        	if (one) {
                return true;
            }
        }
        return false;
    }

    /**
     * 取得最早可购买时间
     *
     * @param busytimes 被购买时间,格式为2008-08-06,2008-08-06;2008-08-9,2008-08-12;2008-08-14,2008-08-22;2008-09-04,2008-09-04
     * @param days      购买时长
     * @return 最高可购买时间
     */
    public static String getCansellTime(String busytimes, int days) {
        Map<String, Integer> dayMap = new HashMap<String, Integer>(10);
        String[] busytimeArr = StringUtils.split(busytimes, ";");
        for (int i = 0; i < busytimeArr.length; i++) {
            String[] time = StringUtils.split(busytimeArr[i], ",");
            Date d1 = getFormatDateTime(time[0], DATE_FORMAT);
            Date d2 = getFormatDateTime(time[1], DATE_FORMAT);
            if(d1 != null){
            	while (d1.compareTo(d2) <= 0) {
                    dayMap.put(getFormatDate(d1), null);
                    d1 = getDateBeforeOrAfter(d1, 1);
                }
            }
        }

        Date lastDate = getFormatDateTime(getFormatDate(getDateBeforeOrAfter(29)), DATE_FORMAT);
        Date beginDate = getFormatDateTime(getFormatDate(getDateBeforeOrAfter(2)), DATE_FORMAT);
        Date endDate = getDateBeforeOrAfter(beginDate, days - 1);

        if(beginDate != null){
        	while (beginDate.compareTo(lastDate) <= 0) {
                boolean conflict = false;
                List<Date> daysList = getDaysListBetweenDates(beginDate, endDate);
                for (Date d : daysList) {
                    if (dayMap.containsKey(getFormatDate(d))) {
                        conflict = true;
                        break;
                    }
                }
                if (!conflict) {
                    break;
                }
                beginDate = getDateBeforeOrAfter(beginDate, 1);
                endDate = getDateBeforeOrAfter(beginDate, days - 1);
            }
        }
        return getFormatDate(beginDate);
    }

    public static Date getFormatDatePay(String dateStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            LOGGER.error("datetime ParseException", e);
            return null;
        }
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            long betweenDays = (time2 - time1) / (1000 * 3600 * 24);

            return Integer.parseInt(String.valueOf(betweenDays));
        } catch (Exception e) {
            LOGGER.error("parse date Exception", e);
            return 0;
        }
    }

    /**
     * 字符串的日期格式的计算
     */
    public static int daysBetween(String smdate, String bdate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(smdate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(betweenDays));
        } catch (Exception e) {
            LOGGER.error("parse date Exception", e);
            return 0;
        }
    }

    /**
     * 计算会员注册时长  （年）
     *
     * @throws ParseException
     */
    public static double yearBetween(Date smdate, Date bdate) {
        int days = daysBetween(smdate, bdate);
        BigDecimal b = BigDecimal.valueOf(days / 365.0);
        double year = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return year;
    }

    /**
     * 获取半小时区间
     * @return
     */
    public static Calendar getTimeHalfHourBefore(){
    	Calendar c = Calendar.getInstance();
		//计算当前时间所在时间分区。
        int minter = 30;
		if (c.get(Calendar.MINUTE)>=minter) {
			//如果当前时间分超过30则去前半个小时区间即可   
			//如:当前时间 16:31:01  则取区间 16:00:00-16:29:59
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
		}else {
			//如果当前时间是00:01:11，则取23:30:00-00:00:00区间的值
			//并把日往前推一天，若是月初，则把月往前推1月，若是年初，则再把年往前推1年
			int offset=c.get(Calendar.MINUTE)+30;
			c.add(Calendar.MINUTE, -offset);
			c.set(Calendar.SECOND, 0);
		}
		return c;
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String formatDateTime(Date date){
        return getFormatDate(date, TIME_FORMAT_PUBLIC);
    }
    /**
     * String to Date
     * @param date
     * @return
     */
    public static Date parseStringToDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_PUBLIC);
        try {
            Date d = sdf.parse(date);
            return d;
        } catch (ParseException e) {
            return new Date();
        }
    }
   
    /**
     * yyyy-MM-dd 00:00:00
     * @param d
     * @param days 变化天数
     * @return
     */
    public static String getZeroDate(Date d,int days){
        Calendar date = Calendar.getInstance();
        if(null != d){
            date.setTime(d);
        }
        date.add(Calendar.DAY_OF_MONTH, days);
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        return formatDateTime(date.getTime());
    }
    public static Date getZeroDate2(Date d,int days){
        Calendar date = Calendar.getInstance();
        if(null != d){
            date.setTime(d);
        }
        date.add(Calendar.DAY_OF_MONTH, days);
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        return date.getTime();
    }
    /**
     * yyyy-MM-dd HH:mm:ss
     * @param d
     * @param days 变化天数
     * @return
     */
    public static String getLastDateTime(Date d,int days){
        Calendar date = Calendar.getInstance();
        if(null != d){
            date.setTime(d);
        }
        date.add(Calendar.DAY_OF_MONTH, days);
        return formatDateTime(date.getTime());
    }
    /**
     * 
     * @param date
     * @param format
     * @return
     */
    public static Date parseStringToDate(String date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date d = sdf.parse(date);
            return d;
        } catch (ParseException e) {
            return new Date();
        }
    }
    
    /**
     * 获取偏移的日期到现在的日期
     * @param offset
     * @return
     */
    public static Date[] getOffsetTime(Integer offset){
    	Date[] dates = new Date[2];
    	Calendar calendar = Calendar.getInstance();
    	Calendar calendar2 = (Calendar) calendar.clone();
		dates[0]=calendar.getTime();
		calendar2.add(Calendar.MINUTE, offset);
		calendar2.set(Calendar.SECOND, 0);
		dates[1]=calendar2.getTime();
		if (dates[0].getTime()>dates[1].getTime()) {
			Date temp = dates[0];
			dates[0]=dates[1];
			dates[1]=temp;
		}
		return dates;
    	
    }
}

