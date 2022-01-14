package com.university.gradcloudnotes.utils;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class UUIDUtil {

    /**生成一个去除-的UUID字符串*/
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
