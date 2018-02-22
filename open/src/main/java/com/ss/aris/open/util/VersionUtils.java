package com.ss.aris.open.util;

import android.content.Context;
import android.content.pm.PackageManager;

import java.util.Locale;

public class VersionUtils {

    public static boolean isChinese(){
        String lang = Locale.getDefault().getLanguage();
        return lang != null && (lang.contains("zh") || lang.contains("cn"));
    }

    public static boolean isChinaVersion(Context context){
        try {
            return "china".equals(ManifestUtil.getMetaData(context, "version"));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

}
