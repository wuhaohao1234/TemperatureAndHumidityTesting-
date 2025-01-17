package com.example.temperatureandhumiditytesting.sleep;


public class StringUtil {
    public static String getMultiNumber(int number) {
        return number < 10 ? "0" + number : Integer.toString(number);
    }

    public static String getLocalMonth(int month) {
        return getMultiNumber(month + 1);
    }

    public static boolean isBlank(String src) {
        return src == null || src.trim().length() == 0;
    }
}
