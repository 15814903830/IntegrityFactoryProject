package com.xsylsb.integrity.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 数据格式化工具
 */
public class DataFormatUtil {

    /**
     * String转int
     * 如果格式错误，返回-1
     *
     * @param value
     * @return
     */
    public static int stringToInt(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * String转double
     * 如果格式错误，返回-1
     *
     * @param value
     * @return
     */
    public static double stringToDouble(String value) {
        try {
            return Double.valueOf(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * String转int
     * 如果格式错误，返回-1
     *
     * @param value
     * @return
     */
    public static long stringToLong(String value) {
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * 判断网络请求结果是否成功
     *
     * @param response
     * @return
     */
    public static boolean judgeResponseSuccess(String response) {
        try {
            JSONObject object = new JSONObject(response);
            boolean success = object.optBoolean("suc", false);
            return success;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断json返回的字符串是否正确
     *
     * @param string
     * @return
     */
    public static boolean jsonStringIsFormat(String string) {
        if (null == string || string.equals("") || string.equals("null")) {
            return false;
        }
        return true;
    }


    /**
     * 判断url是否是图片
     *
     * @param url
     * @return
     */
    public static boolean jsonUrlIsImage(String url) {
        if (url.contains(".jpg") || url.contains(".png")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 格式化时间
     *
     * @param date
     * @return
     */
    public static String formatDate(String date) {
        String format;
        if (date.contains("T")) {
            format = date.split("T")[0];
            String time = date.split("T")[1];
            if (time.contains(":") && time.split(":").length >= 2) {
                format = format + " " + time.split(":")[0] + ":" + time.split(":")[1];
            }
        } else {
            format = date;
        }
        return format;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     * 格式化时间戳
     *
     * @param timestamp
     * @return
     */
    public static String formatTimestamp(long timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //获取当前时间
        Date date = new Date(timestamp);
        return simpleDateFormat.format(date);
    }

    /**
     * 判断是否是同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(String date1, String date2) {
        if (date1.contains(" ")) {
            date1 = date1.split(" ")[0];
        }
        if (date2.contains(" ")) {
            date2 = date2.split(" ")[0];
        }
        if (date1.equals(date2)) {
            return true;
        }
        return false;
    }

    /**
     * 时间转日期
     *
     * @param time
     * @return
     */
    public static String timeToDate(String time) {
        if (time.contains(" ")) {
            time = time.split(" ")[0];
        }
        return time;
    }

    /**
     * 获取字符数组
     *
     * @param string
     * @return
     */
    public static String[] getArrayFromString(String string) {
        if (string.contains("|")) {
            return string.split("\\|");
        } else {
            return new String[]{string};
        }
    }

    /**
     * 数组转字符串
     *
     * @param strings
     * @return
     */
    public static String arrayToString(String[] strings) {
        String string = "";
        for (int i = 0; i < strings.length; i++) {
            if (i == 0) {
                string = string + strings[i];
            } else {
                string = string + "|" + strings[i];
            }
        }
        return string;
    }


    private static InputStream String2InputStream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

    /**
     * 格式化距离单位
     *
     * @param distance
     * @return
     */
    public static String formatDistance(double distance) {
        String str;
        if (distance > 1000) {
            distance = distance / 1000.0;
            str = distance + "km";
        } else {
            str = distance + "m";
        }
        return str;
    }

    /**
     * 格式化蓝牙mac地址
     *
     * @param address
     * @return
     */
    public static String formatBLEMac(String address) {
        if (address == null) {
            return "";
        }
        return address.replaceAll(":", "");
    }

}
