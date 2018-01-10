package com.ss.aris.open.dialog;

import android.content.Context;
import android.content.pm.PackageManager;
import com.ss.aris.open.util.ManifestUtil;

public class DialogHelper {

    public static void show(Context context, int titleId, int contentId,
                            IDialog.OnClickListener listener){
        try {
            String clsName = ManifestUtil.getMetaData(context, "dialog");
            IDialog dialog = (IDialog) Class.forName(clsName).newInstance();
            dialog.show(context, titleId, contentId, listener);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}