package com.jyc.library.fast.lib.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Base64;

import com.jyc.library.fast.lib.util.view.FastDisplayUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringUtil {

    public static char split = 0x01;// 分隔符

    public static char feed = 0x0A;// 换行

    public static String KEY = "zgpg";

    /**
     * 判断字符串是否为 null/空/无内容
     *
     * @param str
     * @return
     * @author wwy
     */
    public static boolean isBlank(String str) {
        if (null == str)
            return true;
        if ("".equals(str.trim()))
            return true;
        if (str.equals("null"))
            return true;
        return false;
    }

    /**
     * 判断是否为空 并且返回一个空字符串
     */
    public static String checkStringBlank(String string) {
        if (isBlank(string)) {
            return "";
        } else {
            return string;
        }
    }

    /**
     * 判断是否为空 并且返回一个空字符串
     */
    public static double checkStringBlankDouble(String string) {
        if (isBlank(string) || string.equals("None")) {
            return 0;
        } else {
            return Double.valueOf(string);
        }
    }

    public static int checkStringBlankInt(String string) {
        if (isBlank(string) || string.equals("None")) {
            return 0;
        } else {
            return Integer.parseInt(string);
        }
    }

    /**
     * 判断是否为空 并且返回一个空字符串
     */
    public static String stringBlankNoComment(String string) {
        if (isBlank(string)) {
            return "";
        } else {
            return string;
        }
    }

    /**
     *
     */
    public static String replaceString(String text, String string) {
        if (isBlank(text)) {
            return "";
        } else {
            if (text.contains(string)) {
                return text.replace(string, "");
            }

            return text;
        }
    }


    /**
     * 字符串相等 null和空字符串认为相等，忽略字符串前后空格
     *
     * @param str1
     * @param str2
     * @return
     * @author wwx
     */
    public static boolean compareString(String str1, String str2) {
        if (null == str1) {
            str1 = "";
        }
        if (null == str2) {
            str2 = "";
        }
        if (str1.trim().equals(str2.trim())) {
            return true;
        }
        return false;
    }

    public static String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母、数字和汉字
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 将对象转成String
     *
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        if (obj == null) {
            return "";
        }
        return obj.toString().trim();
    }

    /*加密*/
    public static String encodePassword(String password, String algorithm) {
        if (algorithm == null)
            return password;
        byte unencodedPassword[] = password.getBytes();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            return password;
        }
        md.reset();
        md.update(unencodedPassword);
        byte encodedPassword[] = md.digest();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < encodedPassword.length; i++) {
            if ((encodedPassword[i] & 0xff) < 16)
                buf.append("0");
            buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
        }
        return buf.toString();
    }


    public static String getEncryptPasswordMD5(String password) {
        return encodePassword(password, "MD5");
    }

    public static boolean isInTime(String sourceTime, String curTime) {
        if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }
        if (curTime == null || !curTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + curTime);
        }
        String[] args = sourceTime.split("-");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            long now = sdf.parse(curTime).getTime();
            long start = sdf.parse(args[0]).getTime();
            long end = sdf.parse(args[1]).getTime();
            if (args[1].equals("00:00")) {
                args[1] = "24:00";
            }
            if (end < start) {
                if (now >= end && now < start) {
                    return false;
                } else {
                    return true;
                }
            } else {
                if (now >= start && now < end) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }

    }

    /**
     * <li>功能描述：时间相减得到天数
     *
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate;
        Date endDate;
        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
            day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
            //System.out.println("相隔的天数="+day);
        } catch (ParseException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
        return day;
    }

    /**
     * <li>功能描述：时间相减得到天数
     *
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author Administrator
     */
    public static long getHourSub(String beginDateStr, String endDateStr) {
        long time = 0;
        long day = 0;
        long s = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate;
        Date endDate;


        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
            time = endDate.getTime() - beginDate.getTime();
            day = time / (24 * 60 * 60 * 1000);
            long hour = (time / (60 * 60 * 1000) - day * 24);
            long min = ((time / (60 * 1000)) - day * 24 * 60 - hour * 60);
            s = (time / 1000);

            //System.out.println("相隔的天数="+day);
        } catch (ParseException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
        return s;
    }

    public static long getDaySubYear(String beginDateStr, String endDateStr) {
        long day = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate;
        Date endDate;
        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
            day = (endDate.getYear() - beginDate.getYear());
            //System.out.println("相隔的天数="+day);
        } catch (ParseException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
        return day;
    }

    /**
     * 获取json节点值
     *
     * @param jsonObject
     * @param jsonNode
     * @return
     */
    public static String getJSONObject(JSONObject jsonObject, String jsonNode) {
        try {

            if (jsonObject.has(jsonNode))
                return jsonObject.get(jsonNode).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static JSONObject getJSONNode(JSONObject jsonObject, String jsonNode) {
        try {
            if (jsonObject.has(jsonNode))
                return jsonObject.getJSONObject(jsonNode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 隐藏手机中间4位号码
     *
     * @param mobile_phone
     * @return
     */
    public static String hideMobilePhone(String mobile_phone) {
        if (StringUtil.isBlank(mobile_phone)) return "";
        return mobile_phone.substring(0, 3) + "****"
                + mobile_phone.substring(7, 11);
    }

    /**
     * 字符串转整数
     *
     * @param l_ser
     * @return
     */

    public static int strToInt(String l_ser) {
        int covs = 0;
        try {
            covs = new Integer(l_ser);
        } catch (Exception e) {
        }
        return covs;
    }

    /**
     * 字符串转double
     *
     * @param gis
     * @return
     */

    public static double strToDouble(String gis) {
        double covs = 0d;
        try {
            covs = new Double(gis).doubleValue();
        } catch (Exception e) {
        }
        return covs;
    }

    /**
     * 字符串转long
     *
     * @param time
     * @return
     */

    public static long strToLong(String time) {
        long covs = 0l;
        try {
            covs = new Long(time).longValue();
        } catch (Exception e) {
        }
        return covs;
    }

//    /**
//     * 验证手机电话号码
//     * <p/>
//     * 手机号码 移动：134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188
//     * 联通：130,131,132,152,155,156,185,186 电信：133,1349,153,180,189
//     *
//     * @param mobilephone 需要验证的手机号码
//     * @return
//     */
//    public static boolean checkMobilephone(String mobilephone) {
//        String cm = "^1(34[0-8]|(3[5-9]|5[017-9]|8[278]|7[0-9])\\d)\\d{7}$";// 中国移动正则
//        String cu = "^1(3[0-2]|5[256]|8[56])\\d{8}$";// 中国联通正则
//        String ct = "^1((33|53|8[09])[0-9]|349)\\d{7}$";// 中国电信正则
//        if (Pattern.matches(cm, mobilephone) || Pattern.matches(cu, mobilephone) || Pattern.matches(ct, mobilephone)) {
//            return true;
//        }
//        return false;
//    }


    public static boolean checkMobilePhone(String mobilePhone) {
//        String isPhone = "^(13\\d|14[5|7]|15\\d|166|17[3|6|7|8]|18\\d|19[8|9])\\d{8}$";
//        return Pattern.matches(isPhone, mobilePhone);
        if (StringUtil.isBlank(mobilePhone)) {
            return false;
        } else {
            return mobilePhone.length() >= 11;
        }
    }


    public static boolean checkName(String name) {
        String maName = "^[A-Za-z0-9\\u4e00-\\u9fa5]+$";
        return Pattern.matches(maName, name);
    }


    /*验证身份证*/
    public static boolean isIDcard(String text) {
        String regx = "[0-9]{17}x";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(reg1) || text.matches(regex);
    }

    /**
     * 验证密码
     *
     * @param psw
     * @return 【除英文和数字外无其他字符(只有英文数字的字符串)】返回true 否则false
     */
    public static boolean checkPassword(String psw) {
//        String isPhone = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$";
//        return Pattern.matches(isPhone, psw);
        if (StringUtil.isBlank(psw)) {
            return false;
        } else {
            return psw.length() >= 6;
        }
    }

    /**
     * 返回原型图
     *
     * @param thumbnial
     * @return
     */
    public static String convertPrototype(String thumbnial) {
        try {
            if (null == thumbnial)
                return "";
            return (new StringBuilder()).append(thumbnial.substring(0, thumbnial.lastIndexOf('.'))).append("_prototype")
                    .append(thumbnial.substring(thumbnial.lastIndexOf('.'))).toString();
        } catch (Exception e) {
            return thumbnial;
        }
    }

    public static String getCurTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static String getCurDtae() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static String getCurYMDHM() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static String getCurOvalDtae() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static String getCurHours() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static int getRandom() {
        int x = (int) (Math.random() * 10000);
        return x;

    }
    /**
     * 将多个对象进行组装<br>
     *
     * @param parts
     * @return
     * @author wwy
     */
    // public static String assemblingString(Object... parts) {
    // StringBuilder builder = new StringBuilder();
    // if (CollectionUtil.isEmpty(parts)) {
    // return null;
    // }
    // for (Object part : parts) {
    // if(part == null){
    // part = Constant.EMPTY_STRING;
    // }
    // builder.append(part);
    // }
    // return builder.toString();
    // }

    /**
     * 转化时间字符转 类型：\/Date(1395396358000)\/
     */
    public static String date(String date) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#¥%……&*（）——+|{}【】‘；：”“’。，、？Date]";
        try {

            if (null == date || date.equals("")) {
                return "";
            } else {

                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(date);
                System.out.println(m.replaceAll(""));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String sd = sdf.format(new Date(Long.parseLong(m.replaceAll("").trim())));
                return sd;

            }

        } catch (Exception e) {
            return "";
        }

    }

    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static String getDate(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /*将字符串转为时间戳*/
    public static long getStringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStampHs(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }


    /*
     * 在String数组中添加指定连接符
     * 数组转String
     * */
    public static String join(String join, List<String> strAry) {
        if (strAry == null || strAry.size() == 0) return "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strAry.size(); i++) {
            if (i == strAry.size() - 1) {
                sb.append(strAry.get(i));
            } else {
                sb.append(strAry.get(i)).append(join);
            }
        }
        return new String(sb);
    }


    /**
     * 是否包含特殊字符
     */
    public static boolean containsAny(String str) {

        String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#¥%……&*（）——+|{}【】‘；：”“’。，、？]";

        // System.out.println("++++++++++++++++++++++++++++++++"+str.contains(regEx));
        if (str != null) {
            Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(str);
            return m.find();
        } else {
            return false;
        }

    }

    public static boolean isCardId(String str) {
        if (str != null && !str.equals("")) {
            Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
            Matcher idNumMatcher = idNumPattern.matcher(str);
            return idNumMatcher.matches();
        } else {
            return false;
        }

    }


    /*String 类型数字
     * 保留两位小数*/

    public static Double formatValue(double strValue) {
        BigDecimal bd = new BigDecimal(String.valueOf(strValue));
        BigDecimal setScale = bd.setScale(2, bd.ROUND_DOWN);
        double v = setScale.doubleValue();
        return v;
    }

    public static String formatValueToString(double strValue) {
        DecimalFormat format = new DecimalFormat("0.00");
        String result = format.format(new BigDecimal(strValue));
        return result;
    }


    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     *           表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static BigDecimal div(String v1, String v2) {

        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        BigDecimal b3 = b1.divide(b2);
        return b3;
    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
            }
            return hexString.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 根据年月日计算年龄,birthTimeString:"1994-11-14"
    public static int getAgeFromBirthTime(String birthTimeString) {
        // 先截取到字符串中的年、月、日
        String strs[] = birthTimeString.trim().split("-");
        int selectYear0 = Integer.parseInt(strs[0]);
        int selectMonth = Integer.parseInt(strs[1]);
        int selectDay = Integer.parseInt(strs[2]);
        int selectYear = selectYear0 + 16;

        // 得到当前时间的年、月、日
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayNow = cal.get(Calendar.DATE);

        // 用当前年月日减去生日年月日
        int yearMinus = yearNow - selectYear;
        int monthMinus = monthNow - selectMonth;
        int dayMinus = dayNow - selectDay;

        int age = yearMinus;// 先大致赋值
        if (yearMinus > 0) {//满16岁
            age = 0;
        } else if (yearMinus == 0) {
            if (monthMinus > 0) {//满16岁
                age = 0;
            } else if (monthMinus == 0) {
                if (dayMinus >= 0) {
                    age = 0;
                } else if (dayMinus < 0) {
                    age = 1;
                }
            } else if (monthMinus < 0) {//不满16
                age = 1;
            }
        } else if (yearMinus < 0) {//不满16
            age = 1;
        }
       /* if (yearMinus < 0) {
            // 选了未来的年份
            age = 0;
        } else if (yearMinus == 0) {// 同年的，要么为1，要么为0
            if (monthMinus < 0) {// 选了未来的月份
                age = 0;
            } else if (monthMinus == 0) {// 同月份的
                if (dayMinus < 0) {// 选了未来的日期
                    age = 0;
                } else if (dayMinus >= 0) {
                    age = 1;
                }
            } else if (monthMinus > 0) {
                age = 1;
            }
        } else if (yearMinus > 0) {
            if (monthMinus < 0) {// 当前月>生日月
            } else if (monthMinus == 0) {// 同月份的，再根据日期计算年龄
                if (dayMinus < 0) {
                } else if (dayMinus >= 0) {
                    age = age + 1;
                }
            } else if (monthMinus > 0) {
                age = age + 1;
            }
        }*/
        return age;
    }

    // 根据时间戳计算年龄
    public static int getAgeFromBirthTime(long birthTimeLong) {
        Date date = new Date(birthTimeLong * 1000l);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String birthTimeString = format.format(date);
        return getAgeFromBirthTime(birthTimeString);
    }


    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014年06月14日16时09分00秒"）
     *
     * @param time
     * @return
     */
    public static String times(String time) {
        if (StringUtil.isBlank(time))
            return "";
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        String times = sdr.format(new Date(lcc));
        return times;

    }

    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014年06月14日16时09分00秒"）
     *
     * @param time
     * @return
     */
    public static String timesYMD(String time) {
        if (StringUtil.isBlank(time))
            return "";
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        String times = sdr.format(new Date(lcc));
        return times;

    }

    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014年06月14日16时09分00秒"）
     *
     * @param time
     * @return
     */
    public static String timesOvalYMD(String time) {
        if (StringUtil.isBlank(time))
            return "";
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy.MM.dd");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        String times = sdr.format(new Date(lcc));
        return times;

    }

    public static String timesChineseYMD(String time) {
        if (StringUtil.isBlank(time) || time.equals("0"))
            return "--年--月--日";
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time)*1000;
        String times = sdr.format(new Date(lcc));
        return times;

    }

    public static String timesChineseYM(String time) {
        if (StringUtil.isBlank(time) || time.equals("0"))
            return "--年--月";
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        String times = sdr.format(new Date(lcc));
        return times;

    }


    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014年06月14日16时09分00秒"）
     *
     * @param time
     * @return
     */
    public static String timesOvalYMDHM(String time) {
        if (StringUtil.isBlank(time))
            return "";
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        String times = sdr.format(new Date(lcc));
        return times;

    }

    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014年06月14日16时09分00秒"）
     *
     * @param time
     * @return
     */
    public static String timesOvalYMDHMS(String time) {
        if (StringUtil.isBlank(time))
            return "";
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        String times = sdr.format(new Date(lcc));
        return times;

    }

    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"06月14日"）
     *
     * @param time
     * @return
     */
    public static String timesMD(String time) {
        if (StringUtil.isBlank(time))
            return "";
        SimpleDateFormat sdr = new SimpleDateFormat("MM-dd");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        String times = sdr.format(new Date(lcc));
        return times;

    }

    /**
     * 字符串拼接
     * lxy
     */
    public static StringBuffer stringBuffer(String... sss) {
        StringBuffer buffer = new StringBuffer();
        int length = sss.length;
        for (int i = 0; i < length; i++) {
            if (TextUtils.isEmpty(sss[i])) {
                continue;
            }

            buffer.append(sss[i]);
            if (i < length - 1) {
                buffer.append(TextUtils.isEmpty(sss[i + 1]) ? "" : "-");
            }
        }
        return buffer;
    }


    public static Bitmap stringToBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 2  * 获取版本号
     * 3  * @return 当前应用的版本号
     * 4
     */
    public static int getVersion(Context mContext) {
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 2  * 获取版本号
     * 3  * @return 当前应用的版本号
     * 4
     */
    public static String getVersionName(Context mContext) {
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    //把yyyymmdd转成yyyy-MM-dd格式
    public static String formatDate(String str) {
        String sfstr = "";
        if (!StringUtil.isBlank(str)) {
            SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sf2 = new SimpleDateFormat("yyyy/MM/dd");

            try {
                sfstr = sf2.format(sf1.parse(str));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return sfstr;
    }

    //把yyyymmdd转成yyyy-MM-dd格式
    public static String formatDate(String str, String formatStr) {
        String sfstr = "";
        if (!StringUtil.isBlank(str)) {
            SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sf2 = new SimpleDateFormat(formatStr);

            try {
                sfstr = sf2.format(sf1.parse(str));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return sfstr;
    }


    //格式化性别字符串 1男 2女
    public static String formatSexData(String str) {
        String sexStr = "";
        if (!StringUtil.isBlank(str)) {
            if (str.equals("1")) {
                sexStr = "男";
            } else if (str.equals("2")) {
                sexStr = "女";
            }
        }
        return sexStr;
    }

    public static Calendar stringToCalendar(String birthDay) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(birthDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        return calendar;
    }

    /**
     * 获取资源字符串
     */
    public static String getResourceStr(Context context, int resourceId, String title) {
        return String.format(context.getString(resourceId), StringUtil.checkStringBlank(title));
    }

    /**
     * 获取物料单位
     */
    public static String getGoodUnitStr(String str) {
        String unitName;
        if (isBlank(str)) {
            unitName = "件";
        } else {
            unitName = str;
        }
        return unitName;
    }

    /**
     * 截取指定字符 超过多少位显示省略号
     *
     * @return
     */
    public static String cutLengthStr(String str) {
        String cutStr = "余杭区";
        if (!isBlank(str)) {
            if (str.length() > 4) {
                cutStr = str.substring(0, 3).concat("...");
            } else {
                cutStr = str;
            }
        }
        return cutStr;
    }

    private static final String REGEX_MOBILE = "^[1][3-9][0-9]{9}$";

    public static boolean valid(String regex, String source) {
        if (isBlank(source))
            return false;
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(source);
        return m.matches();
    }

    public static boolean validMobile(String mobile) {
        return valid(REGEX_MOBILE, mobile);
    }


    /**
     * 格式化价格
     */
    public static String formatDouble(double price) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        return nf.format(price);
    }


    /**
     * 将毫秒转化为 分钟：秒 的格式
     *
     * @param millisecond 毫秒
     * @return
     */
    public static String formatTime(long millisecond) {
        int hours; //小时
        int minute;//分钟
        int second;//秒数
        hours = (int) ((millisecond / 1000) / 60 / 60);
        minute = (int) ((millisecond - hours * 60 * 60 * 1000) / 1000 / 60);
//        minute = (int) ((millisecond / 1000));
        second = (int) ((millisecond / 1000) % 60);
//        if (hours < 10) {
//            if (minute < 10) {
//                return "0" + hours + ":" + "0" + minute;
//            } else {
//                return "0" + hours + ":" + minute;
//            }
//        } else {
//            if (minute < 10) {
//                return hours + ":" + "0" + minute;
//            } else {
//                return hours + ":" + minute;
//            }
//        }


        if (minute < 10) {
            if (second < 10) {
                return "0" + minute + ":" + "0" + second;
            } else {
                return "0" + minute + ":" + second;
            }
        } else {
            if (second < 10) {
                return minute + ":" + "0" + second;
            } else {
                return minute + ":" + second;
            }
        }
    }

    public static String getImageCornersUrl(int w, int h) {
        return "?x-oss-process=image/resize,w_" + w + ",h_" + h + "/quality,Q_85";
    }

    /**
     * flags:
     * <p>
     * FROM_HTML_MODE_COMPACT：html块元素之间使用一个换行符分隔
     * FROM_HTML_MODE_LEGACY：html块元素之间使用两个换行符分隔
     *
     * @param htmlStr
     * @return
     */
    public static Spanned htmlString(String htmlStr) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(htmlStr, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(htmlStr);
        }
    }

    /**
     * 格式化金额
     */
    private static DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public static <T> String formatMoneyString(T money) {
        if (StringUtil.isBlank(String.valueOf(money))) {
            return "0.00";
        }

        if (money instanceof String) {
            return decimalFormat.format(Double.parseDouble((String) money));
        }

        if (money instanceof Number) {
            return decimalFormat.format(money);
        }

        return "0.00";
    }

    public static String subMoneyString(String money) {
        if (StringUtil.isBlank(money)) {
            return "0.00";
        }
        Double a = StringUtil.checkStringBlankDouble(money) * 100;
        double v = Math.floor(a) / 100;
        return String.valueOf(v);

    }


    public static String getHeadText(String name) {
        if (StringUtil.isBlank(name)) {
            return "";
        } else if (name.length() > 2) {
            return name.substring(0, 2);
        } else {
            return name;
        }
    }

    public static String getShaParam(TreeMap<String, String> treeMap) {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<Map.Entry<String, String>> it = treeMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            stringBuffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        String signParam = stringBuffer.substring(0, stringBuffer.length() - 1);


        return signParam;
    }

    public static String sigTokenStr(String token) {
        StringBuilder sigToken = new StringBuilder();

        for (int i = 0; i < token.length(); i++) {
            int sigStr = token.charAt(i) + 3;
            sigToken.append(String.valueOf(sigStr));
            if (i < token.length() - 1) {
                sigToken.append(",");
            }
        }
        System.out.println(sigToken);
        return sigToken.toString();
    }

    public static String getMoney(String intBagNum, String unitNum) {
        if (StringUtil.isBlank(intBagNum) || StringUtil.isBlank(unitNum)) {
            return "";
        }
        BigDecimal bigDecimal = new BigDecimal(intBagNum);
        BigDecimal bigDecimal1 = new BigDecimal(unitNum);

        return bigDecimal.multiply(bigDecimal1).toString();
    }

    public static String bigMultiply(int intBagNum, Double unitNum) {
        BigDecimal bigDecimal = new BigDecimal(intBagNum);
        BigDecimal bigDecimal1 = new BigDecimal(unitNum);

        return bigDecimal.multiply(bigDecimal1).toString();
    }

    public static String getSubtractMoney(String intBagNum, String unitNum) {
        if (StringUtil.isBlank(intBagNum) || StringUtil.isBlank(unitNum)) {
            return "";
        }
        BigDecimal bigDecimal = new BigDecimal(intBagNum);
        BigDecimal bigDecimal1 = new BigDecimal(unitNum);

        return bigDecimal.subtract(bigDecimal1).toString();
    }

    public static Double getDoubleMoney(String intBagNum, String unitNum) {
        if (StringUtil.isBlank(intBagNum) || StringUtil.isBlank(unitNum)) {
            return 0.0;
        }
        BigDecimal bigDecimal = new BigDecimal(intBagNum);
        BigDecimal bigDecimal1 = new BigDecimal(unitNum);

        return bigDecimal.multiply(bigDecimal1).doubleValue();
    }

    public static Double getDoubleMoney(Double intBagNum, Double unitNum) {
        BigDecimal bigDecimal = new BigDecimal(intBagNum);
        BigDecimal bigDecimal1 = new BigDecimal(unitNum);

        return bigDecimal.multiply(bigDecimal1).doubleValue();
    }

    public static String timeLongMulit1000(String demand_time) {
        if (StringUtil.isBlank(demand_time)) return "";
        long time = Long.valueOf(demand_time) * 1000;
        return String.valueOf(time);
    }

    public static SpannableString spanMoney(String text, int moneyUnitSize) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new AbsoluteSizeSpan(moneyUnitSize, true), text.indexOf("¥"), text.indexOf("¥") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }


    /**
     * @param mContext
     * @param money        字符串
     * @param originalSize 原始字体大小
     * @return SpannableStringBuilder
     */
    public static SpannableStringBuilder spanMoneyFontSize(Context mContext, String money, float originalSize) {
        int startIndex;
        int endIndex;
        int textSizeRange;
        int originalSizeDP;

        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(money);

        String[] spitMoney;
        if (money.contains(".")) {
            spitMoney = money.split("\\.");
            startIndex = spitMoney[0].length() + 1;
            endIndex = money.length();
            originalSizeDP = FastDisplayUtil.px2dp(mContext, originalSize);
            textSizeRange = originalSizeDP >= 16 ? originalSizeDP - 4 : originalSizeDP - 2;
            AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(FastDisplayUtil.dp2px(textSizeRange));
            spannableString.setSpan(absoluteSizeSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }


        return spannableString;
    }
}
