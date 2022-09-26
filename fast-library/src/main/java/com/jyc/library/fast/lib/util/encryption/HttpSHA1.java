package com.jyc.library.fast.lib.util.encryption;

import com.jyc.library.fast.lib.util.StringUtil;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author jyc
 * 创建日期：2019/3/11
 * 描述：HttpSHA1
 */
public class HttpSHA1 {

    /*
     * 对数组进行升序排序的方法
     */
    public static String getHttpSigString(Map<String, Object> paramsKey, String userToken, String baseUrl, String jsonBody) {
        List<String> params = new ArrayList<>(paramsKey.keySet());
        Collections.sort(params);

        StringBuilder returnString = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            returnString.append(params.get(i)).append("=").append(paramsKey.get(params.get(i)));
            returnString.append("&");
        }

        String sigStr = baseUrl + "?" + returnString.toString();

        //jsonBody 加入加密
        if (!StringUtil.isBlank(jsonBody)) {
            sigStr = sigStr + jsonBody + "&";
        }
        sigStr = sigStr + userToken;
        return sigStr;
    }

    public static String getSha1(String str) {
        if (null == str || 0 == str.length()) {
            return null;
        }
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    获取精确到秒的时间戳
    @param date
  @return
          */
    public static int getSecondTimestampTwo(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime() / 1000);
        return Integer.valueOf(timestamp);
    }
}
