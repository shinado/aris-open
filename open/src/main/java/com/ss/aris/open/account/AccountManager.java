package com.ss.aris.open.account;


import android.content.Context;
import android.content.pm.PackageManager;
import static com.ss.aris.open.util.ManifestUtil.getMetaData;

public class AccountManager {

    public static IAccount getAccount(Context context) {
        try {
            String clsName = getMetaData(context, "account");
            Class cls = Class.forName(clsName);
            return (IAccount) cls.newInstance();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return new AbsAccount();
    }

}
