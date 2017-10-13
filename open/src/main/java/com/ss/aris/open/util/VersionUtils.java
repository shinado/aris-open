package com.ss.aris.open.util;

import java.util.Locale;

public class VersionUtils {

    public static boolean isChina(){
        String lang = Locale.getDefault().getLanguage();
        return lang != null && (lang.contains("zh") || lang.contains("cn"));
    }

}
