package com.university.gradcloudnotes.utils;

import lombok.experimental.UtilityClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@UtilityClass
public class PubFun {
    /**
     * java.util.Date类型转成只包含年月日的字符串
     */
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
    /**
     * java.util.Date类型转成只包含时分秒的字符串
     */
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * 将字符串转换成时间格式
     */
    public static Date toDate(String str) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.parse(str);
    }

    public static void main(String[] args) throws ParseException {
        Date date = toDate("2022-12-01 18:23:32");
        System.out.println(date);
    }
}
