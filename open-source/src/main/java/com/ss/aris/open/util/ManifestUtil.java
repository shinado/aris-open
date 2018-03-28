package com.ss.aris.open.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

/**
 * Created by ss on 10/9/17.
 */

public class ManifestUtil {

    public static String getMetaData(Context context, String key) throws PackageManager.NameNotFoundException {
        ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                context.getPackageName(), PackageManager.GET_META_DATA);
        Bundle bundle = ai.metaData;
        return bundle.getString(key);
    }

}
