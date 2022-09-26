package com.jyc.library.fast.lib.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/2
 *     desc  : 时间相关工具类
 * </pre>
 */
public class TimeUtils {


    /******************** 时间相关常量 ********************/
    /**
     * 毫秒与毫秒的倍数
     */
    public static final int MSEC = 1;
    /**
     * 秒与毫秒的倍数
     */
    public static final int SEC = 1000;
    /**
     * 分与毫秒的倍数
     */
    public static final int MIN = 60000;
    /**
     * 时与毫秒的倍数
     */
    public static final int HOUR = 3600000;
    /**
     * 天与毫秒的倍数
     */
    public static final int DAY = 86400000;

    public static final String FIRST_SECOND = " 00:00:00";
    public static final String LAST_SECOND = " 23:59:59";


    public enum TimeUnit {
        MSEC,
        SEC,
        MIN,
        HOUR,
        DAY
    }

    private TimeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * <p>在工具类中经常使用到工具类的格式化描述，这个主要是一个日期的操作类，所以日志格式主要使用 SimpleDateFormat的定义格式.</p>
     * 格式的意义如下： 日期和时间模式 <br>
     * <p>日期和时间格式由日期和时间模式字符串指定。在日期和时间模式字符串中，未加引号的字母 'A' 到 'Z' 和 'a' 到 'z'
     * 被解释为模式字母，用来表示日期或时间字符串元素。文本可以使用单引号 (') 引起来，以免进行解释。"''"
     * 表示单引号。所有其他字符均不解释；只是在格式化时将它们简单复制到输出字符串，或者在分析时与输入字符串进行匹配。
     * </p>
     * 定义了以下模式字母（所有其他字符 'A' 到 'Z' 和 'a' 到 'z' 都被保留）： <br>
     * <table border="1" cellspacing="1" cellpadding="1" summary="Chart shows pattern letters, date/time component,
     * presentation, and examples.">
     * <tr>
     * <th align="left">字母</th>
     * <th align="left">日期或时间元素</th>
     * <th align="left">表示</th>
     * <th align="left">示例</th>
     * </tr>
     * <tr>
     * <td><code>G</code></td>
     * <td>Era 标志符</td>
     * <td>Text</td>
     * <td><code>AD</code></td>
     * </tr>
     * <tr>
     * <td><code>y</code> </td>
     * <td>年 </td>
     * <td>Year </td>
     * <td><code>1996</code>; <code>96</code> </td>
     * </tr>
     * <tr>
     * <td><code>M</code> </td>
     * <td>年中的月份 </td>
     * <td>Month </td>
     * <td><code>July</code>; <code>Jul</code>; <code>07</code> </td>
     * </tr>
     * <tr>
     * <td><code>w</code> </td>
     * <td>年中的周数 </td>
     * <td>Number </td>
     * <td><code>27</code> </td>
     * </tr>
     * <tr>
     * <td><code>W</code> </td>
     * <td>月份中的周数 </td>
     * <td>Number </td>
     * <td><code>2</code> </td>
     * </tr>
     * <tr>
     * <td><code>D</code> </td>
     * <td>年中的天数 </td>
     * <td>Number </td>
     * <td><code>189</code> </td>
     * </tr>
     * <tr>
     * <td><code>d</code> </td>
     * <td>月份中的天数 </td>
     * <td>Number </td>
     * <td><code>10</code> </td>
     * </tr>
     * <tr>
     * <td><code>F</code> </td>
     * <td>月份中的星期 </td>
     * <td>Number </td>
     * <td><code>2</code> </td>
     * </tr>
     * <tr>
     * <td><code>E</code> </td>
     * <td>星期中的天数 </td>
     * <td>Text </td>
     * <td><code>Tuesday</code>; <code>Tue</code> </td>
     * </tr>
     * <tr>
     * <td><code>a</code> </td>
     * <td>Am/pm 标记 </td>
     * <td>Text </td>
     * <td><code>PM</code> </td>
     * </tr>
     * <tr>
     * <td><code>H</code> </td>
     * <td>一天中的小时数（0-23） </td>
     * <td>Number </td>
     * <td><code>0</code> </td>
     * </tr>
     * <tr>
     * <td><code>k</code> </td>
     * <td>一天中的小时数（1-24） </td>
     * <td>Number </td>
     * <td><code>24</code> </td>
     * </tr>
     * <tr>
     * <td><code>K</code> </td>
     * <td>am/pm 中的小时数（0-11） </td>
     * <td>Number </td>
     * <td><code>0</code> </td>
     * </tr>
     * <tr>
     * <td><code>h</code> </td>
     * <td>am/pm 中的小时数（1-12） </td>
     * <td>Number </td>
     * <td><code>12</code> </td>
     * </tr>
     * <tr>
     * <td><code>m</code> </td>
     * <td>小时中的分钟数 </td>
     * <td>Number </td>
     * <td><code>30</code> </td>
     * </tr>
     * <tr>
     * <td><code>s</code> </td>
     * <td>分钟中的秒数 </td>
     * <td>Number </td>
     * <td><code>55</code> </td>
     * </tr>
     * <tr>
     * <td><code>S</code> </td>
     * <td>毫秒数 </td>
     * <td>Number </td>
     * <td><code>978</code> </td>
     * </tr>
     * <tr>
     * <td><code>z</code> </td>
     * <td>时区 </td>
     * <td>General time zone </td>
     * <td><code>Pacific Standard Time</code>; <code>PST</code>; <code>GMT-08:00</code> </td>
     * </tr>
     * <tr>
     * <td><code>Z</code> </td>
     * <td>时区 </td>
     * <td>RFC 822 time zone </td>
     * <td><code>-0800</code> </td>
     * </tr>
     * </table>
     * <pre>
     *                          HH:mm    15:44
     *                         h:mm a    3:44 下午
     *                        HH:mm z    15:44 CST
     *                        HH:mm Z    15:44 +0800
     *                     HH:mm zzzz    15:44 中国标准时间
     *                       HH:mm:ss    15:44:40
     *                     yyyy-MM-dd    2016-08-12
     *               yyyy-MM-dd HH:mm    2016-08-12 15:44
     *            yyyy-MM-dd HH:mm:ss    2016-08-12 15:44:40
     *       yyyy-MM-dd HH:mm:ss zzzz    2016-08-12 15:44:40 中国标准时间
     *  EEEE yyyy-MM-dd HH:mm:ss zzzz    星期五 2016-08-12 15:44:40 中国标准时间
     *       yyyy-MM-dd HH:mm:ss.SSSZ    2016-08-12 15:44:40.461+0800
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     *   yyyy.MM.dd G 'at' HH:mm:ss z    2016.08.12 公元 at 15:44:40 CST
     *                         K:mm a    3:44 下午
     *               EEE, MMM d, ''yy    星期五, 八月 12, '16
     *          hh 'o''clock' a, zzzz    03 o'clock 下午, 中国标准时间
     *   yyyyy.MMMMM.dd GGG hh:mm aaa    02016.八月.12 公元 03:44 下午
     *     EEE, d MMM yyyy HH:mm:ss Z    星期五, 12 八月 2016 15:44:40 +0800
     *                  yyMMddHHmmssZ    160812154440+0800
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     * EEEE 'DATE('yyyy-MM-dd')' 'TIME('HH:mm:ss')' zzzz    星期五 DATE(2016-08-12) TIME(15:44:40) 中国标准时间
     * </pre>
     * 注意SimpleDateFormat不是线程安全的
     */
    public static final SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    public static final SimpleDateFormat DEPOSIT_SDF = new SimpleDateFormat("MM-dd", Locale.getDefault());
    public static final SimpleDateFormat USER_FREE_SDF = new SimpleDateFormat("yyyy - MM - dd", Locale.getDefault());
    public static final SimpleDateFormat LIVING_SDF = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
    public static final SimpleDateFormat USER_24_HOUR_SDF = new SimpleDateFormat("HH:mm", Locale.getDefault());
    public static final SimpleDateFormat ORDER_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    public static final SimpleDateFormat PAY_ORDER_SDF = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault());
    public static final SimpleDateFormat CHINESE_LABEL_SDF = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
    public static final SimpleDateFormat CHINESE_MDHM = new SimpleDateFormat("MM月dd日 HH:mm", Locale.getDefault());
    public static final SimpleDateFormat CHINESE_YM = new SimpleDateFormat("yyyy年MM月", Locale.getDefault());
    public static final SimpleDateFormat BANK_YM = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
    public static final SimpleDateFormat TIME_YM = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
    public static final SimpleDateFormat REFUND_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    public static final SimpleDateFormat INVITE_SDF = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static final SimpleDateFormat LOGISTICS_SDF = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());

    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param milliseconds 毫秒时间戳
     * @return 时间字符串
     */
    public static String milliseconds2String(long milliseconds) {
        return milliseconds2String(milliseconds, DEFAULT_SDF);
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为用户自定义</p>
     *
     * @param milliseconds 毫秒时间戳
     * @param format       时间格式
     * @return 时间字符串
     */
    public static String milliseconds2String(long milliseconds, SimpleDateFormat format) {
        if (milliseconds == 0) {
            return "--";
        }
        return format.format(new Date(milliseconds));
    }

    /**
     * 将时间字符串转为时间戳
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 毫秒时间戳
     */
    public static long string2Milliseconds(String time) {
        return string2Milliseconds(time, DEFAULT_SDF);
    }

    /**
     * 将时间字符串转为时间戳
     * <p>格式为用户自定义</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 毫秒时间戳
     */
    public static long string2Milliseconds(String time, SimpleDateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 将时间字符串转为Date类型
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return Date类型
     */
    public static Date string2Date(String time) {
        return string2Date(time, DEFAULT_SDF);
    }

    /**
     * 将时间字符串转为Date类型
     * <p>格式为用户自定义</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return Date类型
     */
    public static Date string2Date(String time, SimpleDateFormat format) {
        return new Date(string2Milliseconds(time, format));
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time Date类型时间
     * @return 时间字符串
     */
    public static String date2String(Date time) {
        return date2String(time, DEFAULT_SDF);
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为用户自定义</p>
     *
     * @param time   Date类型时间
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String date2String(Date time, SimpleDateFormat format) {
        return format.format(time);
    }

    /**
     * 将Date类型转为时间戳
     *
     * @param time Date类型时间
     * @return 毫秒时间戳
     */
    public static long date2Milliseconds(Date time) {
        return time.getTime();
    }

    /**
     * 将时间戳转为Date类型
     *
     * @param milliseconds 毫秒时间戳
     * @return Date类型时间
     */
    public static Date milliseconds2Date(long milliseconds) {
        return new Date(milliseconds);
    }

    /**
     * 毫秒时间戳单位转换（单位：unit）
     *
     * @param milliseconds 毫秒时间戳
     * @param unit         <ul>
     *                     <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *                     <li>{@link TimeUnit#SEC }: 秒</li>
     *                     <li>{@link TimeUnit#MIN }: 分</li>
     *                     <li>{@link TimeUnit#HOUR}: 小时</li>
     *                     <li>{@link TimeUnit#DAY }: 天</li>
     *                     </ul>
     * @return unit时间戳
     */
    public static long milliseconds2Unit(long milliseconds, TimeUnit unit) {
        switch (unit) {
            case MSEC:
                return milliseconds / MSEC;
            case SEC:
                return milliseconds / SEC;
            case MIN:
                return milliseconds / MIN;
            case HOUR:
                return milliseconds / HOUR;
            case DAY:
                return milliseconds / DAY;
        }
        return -1;
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time1和time2格式都为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time0 时间字符串1
     * @param time1 时间字符串2
     * @param unit  <ul>
     *              <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *              <li>{@link TimeUnit#SEC }: 秒</li>
     *              <li>{@link TimeUnit#MIN }: 分</li>
     *              <li>{@link TimeUnit#HOUR}: 小时</li>
     *              <li>{@link TimeUnit#DAY }: 天</li>
     *              </ul>
     * @return unit时间戳
     */
    public static long getIntervalTime(String time0, String time1, TimeUnit unit) {
        return getIntervalTime(time0, time1, unit, DEFAULT_SDF);
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time1和time2格式都为format</p>
     *
     * @param time0  时间字符串1
     * @param time1  时间字符串2
     * @param unit   <ul>
     *               <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *               <li>{@link TimeUnit#SEC }: 秒</li>
     *               <li>{@link TimeUnit#MIN }: 分</li>
     *               <li>{@link TimeUnit#HOUR}: 小时</li>
     *               <li>{@link TimeUnit#DAY }: 天</li>
     *               </ul>
     * @param format 时间格式
     * @return unit时间戳
     */
    public static long getIntervalTime(String time0, String time1, TimeUnit unit, SimpleDateFormat format) {
        return milliseconds2Unit(Math.abs(string2Milliseconds(time0, format)
                - string2Milliseconds(time1, format)), unit);
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time1和time2都为Date类型</p>
     *
     * @param time0 Date类型时间1
     * @param time1 Date类型时间2
     * @param unit  <ul>
     *              <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *              <li>{@link TimeUnit#SEC }: 秒</li>
     *              <li>{@link TimeUnit#MIN }: 分</li>
     *              <li>{@link TimeUnit#HOUR}: 小时</li>
     *              <li>{@link TimeUnit#DAY }: 天</li>
     *              </ul>
     * @return unit时间戳
     */
    public static long getIntervalTime(Date time0, Date time1, TimeUnit unit) {
        return milliseconds2Unit(Math.abs(date2Milliseconds(time1)
                - date2Milliseconds(time0)), unit);
    }

    /**
     * 获取当前时间
     *
     * @return 毫秒时间戳
     */
    public static long getCurTimeMills() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @return 时间字符串
     */
    public static String getCurTimeString() {
        return date2String(new Date());
    }

    /**
     * 获取当前时间
     * <p>格式为用户自定义</p>
     *
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String getCurTimeString(SimpleDateFormat format) {
        return date2String(new Date(), format);
    }

    /**
     * 获取当前时间
     * <p>Date类型</p>
     *
     * @return Date类型时间
     */
    public static Date getCurTimeDate() {
        return new Date();
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @param unit <ul>
     *             <li>{@link TimeUnit#MSEC}:毫秒</li>
     *             <li>{@link TimeUnit#SEC }:秒</li>
     *             <li>{@link TimeUnit#MIN }:分</li>
     *             <li>{@link TimeUnit#HOUR}:小时</li>
     *             <li>{@link TimeUnit#DAY }:天</li>
     *             </ul>
     * @return unit时间戳
     */
    public static long getIntervalByNow(String time, TimeUnit unit) {
        return getIntervalByNow(time, unit, DEFAULT_SDF);
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param unit   <ul>
     *               <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *               <li>{@link TimeUnit#SEC }: 秒</li>
     *               <li>{@link TimeUnit#MIN }: 分</li>
     *               <li>{@link TimeUnit#HOUR}: 小时</li>
     *               <li>{@link TimeUnit#DAY }: 天</li>
     *               </ul>
     * @param format 时间格式
     * @return unit时间戳
     */
    public static long getIntervalByNow(String time, TimeUnit unit, SimpleDateFormat format) {
        return getIntervalTime(getCurTimeString(), time, unit, format);
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <p>time为Date类型</p>
     *
     * @param time Date类型时间
     * @param unit <ul>
     *             <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *             <li>{@link TimeUnit#SEC }: 秒</li>
     *             <li>{@link TimeUnit#MIN }: 分</li>
     *             <li>{@link TimeUnit#HOUR}: 小时</li>
     *             <li>{@link TimeUnit#DAY }: 天</li>
     *             </ul>
     * @return unit时间戳
     */
    public static long getIntervalByNow(Date time, TimeUnit unit) {
        return getIntervalTime(getCurTimeDate(), time, unit);
    }

    /**
     * 判断闰年
     *
     * @param year 年份
     * @return {@code true}: 闰年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * 获取星期
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 星期
     */
    public static String getWeek(String time) {
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(string2Date(time));
    }

    /**
     * 获取星期
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 星期
     */
    public static String getWeek(String time, SimpleDateFormat format) {
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(string2Date(time, format));
    }

    /**
     * 获取星期
     *
     * @param time Date类型时间
     * @return 星期
     */
    public static String getWeek(Date time) {
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(time);
    }

    /**
     * 获取星期
     * <p>注意：周日的Index才是1，周六为7</p>
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 1...5
     */
    public static int getWeekIndex(String time) {
        Date date = string2Date(time);
        return getWeekIndex(date);
    }

    /**
     * 获取星期
     * <p>注意：周日的Index才是1，周六为7</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 1...7
     */
    public static int getWeekIndex(String time, SimpleDateFormat format) {
        Date date = string2Date(time, format);
        return getWeekIndex(date);
    }

    /**
     * 获取星期
     * <p>注意：周日的Index才是1，周六为7</p>
     *
     * @param time Date类型时间
     * @return 1...7
     */
    public static int getWeekIndex(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取月份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 1...5
     */
    public static int getWeekOfMonth(String time) {
        Date date = string2Date(time);
        return getWeekOfMonth(date);
    }

    /**
     * 获取月份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 1...5
     */
    public static int getWeekOfMonth(String time, SimpleDateFormat format) {
        Date date = string2Date(time, format);
        return getWeekOfMonth(date);
    }

    /**
     * 获取月份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param time Date类型时间
     * @return 1...5
     */
    public static int getWeekOfMonth(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 1...54
     */
    public static int getWeekOfYear(String time) {
        Date date = string2Date(time);
        return getWeekOfYear(date);
    }

    public static String getChinesYMD(Date times) {

        return CHINESE_LABEL_SDF.format(times);
    }

    public static String getChinesYM(Date times) {

        return CHINESE_YM.format(times);
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 1...54
     */
    public static int getWeekOfYear(String time, SimpleDateFormat format) {
        Date date = string2Date(time, format);
        return getWeekOfYear(date);
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param time Date类型时间
     * @return 1...54
     */
    public static int getWeekOfYear(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    public static String formatWeekString(String time) {
        if (StringUtil.isBlank(time)) {
            return "无";
        }
        String weekStr = getWeek(milliseconds2String(Long.parseLong(time)));
        return milliseconds2String(Long.parseLong(time), TimeUtils.LIVING_SDF) + " (" + weekStr + ")";
    }

    /**
     * 获取后一个月
     */

    public static Calendar getBeforeDay(int year, int month, int day) {
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.set(Calendar.YEAR, year);
        ca.set(Calendar.MONTH, month - 1);
        ca.set(Calendar.DAY_OF_MONTH, day);
        ca.add(Calendar.DAY_OF_MONTH, -1);
        return ca;
    }


    /**
     * 获取前一个月
     */
    public static Calendar getNextDay(int year, int month, int day) {
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.set(Calendar.YEAR, year);
        ca.set(Calendar.MONTH, month - 1);
        ca.set(Calendar.DAY_OF_MONTH, day);
        ca.add(Calendar.DAY_OF_MONTH, 1);
        return ca;
    }

    /**
     * 获取后一个月
     */

    public static Calendar getBeforeMonth(int year, int month) {
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.set(Calendar.YEAR, year);
        ca.set(Calendar.MONTH, month - 1);
        ca.add(Calendar.MONTH, -1);
        return ca;
    }


    /**
     * 获取前一个月
     */
    public static Calendar getNextMonth(int year, int month) {
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.set(Calendar.YEAR, year);
        ca.set(Calendar.MONTH, month - 1);
        ca.add(Calendar.MONTH, 1);
        return ca;
    }

    /**
     * 获取一个月的最后一天
     */
    public static String getLastDay(int year, int month) {
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.set(Calendar.YEAR, year);
        ca.set(Calendar.MONTH, month - 1);
        ca.set(Calendar.DATE, ca.getActualMaximum(ca.DATE));
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String ss = sf.format(ca.getTime());
        return ss + LAST_SECOND;
    }

    /**
     * 获取一个年月日的时间戳
     */
    public static String getFirstDay(int year, int month, int day) {
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.set(Calendar.YEAR, year);
        ca.set(Calendar.MONTH, month - 1);
        ca.set(Calendar.DATE, day);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String ss = sf.format(ca.getTime());
        return ss + TimeUtils.FIRST_SECOND;
    }

    /**
     * 获取一个月的第一天
     */
    public static String getFirstDay(int year, int month) {
        return getFirstDay(year, month, 1);
//        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
//        ca.set(Calendar.YEAR, year);
//        ca.set(Calendar.MONTH, month - 1);
//        ca.set(Calendar.DATE, 1);
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//        String ss = sf.format(ca.getTime());
//        return ss + TimeUtils.FIRST_SECOND;
    }

    /**
     * 获取前一年
     */
    public static Calendar getBeforeYear(int year) {

        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.set(year, 1, 1);//月份是从0开始的，所以11表示12月
        ca.add(Calendar.YEAR, -1); //月份减1
        ca.set(Calendar.MINUTE, 00);
        ca.set(Calendar.HOUR, 00);
        ca.set(Calendar.SECOND, 00);

        return ca;
    }

    /**
     * 获取后一年
     */
    public static Calendar getNextYear(int year) {

        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.set(year, 1, 1);//月份是从0开始的，所以11表示12月
        ca.add(Calendar.YEAR, 1); //月份减1
        ca.set(Calendar.MINUTE, 00);
        ca.set(Calendar.HOUR, 00);
        ca.set(Calendar.SECOND, 00);

        return ca;
    }

    /**
     * 获取后一天
     *
     * @return
     */
    public static String getBeforeDay() {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, -1);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        return formatter.format(date);
    }

    /**
     * 获取前一天
     *
     * @return
     */
    public static String getNextDay() {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        return formatter.format(date);
    }

    /**
     * 返回两个string类型日期之间相差的天数
     *
     * @return
     */
    public static int daysBetween(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        long time1 = 0;
        long time2 = 0;

        try {
            cal.setTime(sdf.parse(milliseconds2String(time)));
            time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(getCurTimeString()));
            time2 = cal.getTimeInMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }


    /**
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
     *
     * @param timeStamp
     * @return
     */
    public static String convertTimeToFormat(long timeStamp) {
        long curTime = System.currentTimeMillis();
        long time = curTime - timeStamp;

        if (time < 60 && time >= 0) {
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + "小时前";
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
            return time / 3600 / 24 + "天前";
        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 + "个月前";
        } else if (time >= 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 / 12 + "年前";
        } else {
            return "刚刚";
        }
    }

    /**
     * 获取时间段小时
     */
    public static int getDurationHour(long duration) {
        return (int) Math.floor(duration / 3600);
    }

    /**
     * 获取时间段分钟
     */
    public static int getDurationMin(long duration) {
        int minute;
        if (duration < 60) {
            minute = 0;
        } else {
            double second = duration - getDurationHour(duration) * 3600;
            minute = (int) Math.floor(second / 60);
        }
        return minute;
    }

    /**
     * 获取时间段秒数
     *
     * @param duration
     * @return
     */
    public static int getDurationSecond(long duration) {
        int second;
        if (duration <= 60) {
            second = (int) duration;
        } else {
            second = getDurationMin(duration) % 60;
        }
        return second;
    }

    //毫秒换成00:00:00
    public static String getCountTimeByLong(long finishTime) {
        int totalTime = (int) (finishTime / 10);//秒
        int minute = 0, second = 0;


        if (60 <= totalTime) {
            minute = totalTime / 60;
            totalTime = totalTime - 60 * minute;
        }
        if (0 <= totalTime) {
            second = totalTime;
        }
        StringBuilder sb = new StringBuilder();

        if (minute < 10) {
            sb.append("0").append(minute).append(":");
        } else {
            sb.append(minute).append(":");
        }
        if (second < 10) {
            sb.append("0").append(second);
        } else {
            sb.append(second);
        }
        return sb.toString();

    }

    /**
     * 格式化时间格式
     * 消息时间在此页显示采用三类划分，
     * 今天的日期使用小时分钟显示（24小时计时制），
     * 昨天的信息显示为“昨天”，
     * 昨天之前的时间，本月内的按月份日期显示，
     * 本月以上的按年月日显示。
     */

    public static String getFormatNumberString(long time) {
        int day = daysBetween(time);
        if (day == 0) {
            return milliseconds2String(time, USER_24_HOUR_SDF);
        } else if (day == 1) {
            return "昨天";
        } else {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
            String date = df.format(new Date());
            String MM = date.substring(4, 6);//截取系统月份
            String mm = milliseconds2String(time, df).substring(4, 6);//要判断的月份，需要自己截取处理
            if (MM.equals(mm)) {
                //在本月份
                return milliseconds2String(time, DEPOSIT_SDF);
            } else {
                //不在本月份
                return milliseconds2String(time, USER_FREE_SDF);
            }
        }
    }

    public static String turnDayHourMinuteString(Long seconds) {
        //如果传入的秒是0，默认24小时
        if (0 == seconds) {
            return 24 + "时";
        }
        //如果秒数小于60，默认返回秒
        if (0 < seconds && seconds < 60) {
            return seconds + "秒";
        }

        //如果秒数小于3600，返回分钟
        if (60 <= seconds && seconds < 3600) {
            Long m = seconds % 60;
            return m + "分";
        }

        //如果秒数小于24小时（86400分钟），返回小时和分钟
        if (3600 <= seconds && seconds < 86400) {

            if (seconds % 3600 == 0) {
                Long h = seconds / 3600;
                return h + "时";
            } else {
                Long h = seconds / 3600;
                Long m = seconds / 60 % 60;
//                return h + "时" + m + "分";
                return h + "时";
            }

        }
        //如果分钟大于1天
        if (seconds >= 86400) {

            Long d = seconds / 3600 / 24;
            Long h = seconds / 3600 % 24;
            Long m = seconds / 60 % 60;
            String s1 = null;
            if (d > 0) {
                s1 = d + "天";
            }
            //h如果计算大于等于1再展示，否则只展示天和分钟
            if (h >= 1) {
                s1 += h + "时";
            }
//            if (m >= 1) {
//                s1 += m + "分";
//            }

            return s1;
        }
        return null;
    }
}