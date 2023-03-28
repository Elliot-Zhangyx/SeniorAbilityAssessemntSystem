package com.graduation.seniorabilityassessment.utils;

public class CharacterFilter {
    /**
     * 过滤出中文、数字、字母
     * @param str
     * @return
     */
    public static String stringFilter(String str) {
        return str.replaceAll("[^(a-zA-Z0-9\\\\u4e00-\\\\u9fa5)]","").trim();
    }

    /**
     * 过滤出中文
     * @param str
     * @return
     */
    public static String chineseFilter(String str) {
        return str.replaceAll("[^(\\u4e00-\\u9fa5)]","").trim();
    }

    /**
     * 过滤出数字
     * @param str
     * @return
     */
    public static String numberFilter(String str) {
        return str.replaceAll("[^0-9]","").trim();
    }
}


