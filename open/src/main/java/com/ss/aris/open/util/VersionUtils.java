package com.ss.aris.open.util;


import java.util.Locale;

public class VersionUtils {
    public static boolean isChina(){
        Locale locale = Locale.getDefault();
        return Locale.CHINA.equals(locale) || Locale.SIMPLIFIED_CHINESE.equals(locale) ||
                Locale.TRADITIONAL_CHINESE.equals(locale);
    }
}
