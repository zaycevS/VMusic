package com.zaycev.vmusic.utils;

public class Util {

    public static String getDateFromStr(long sec) {

        String result = "";

        long min = sec / 60;
        long hour = min / 60;
        sec %= 60;

        if (hour != 0) result += hour + ":";
        if (min < 10) result += "0";
        result += min + ":";
        if (sec < 10) result += "0";
        result += sec;

        return result;
    }
}
