package indi.shinado.piping.saas;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import indi.shinado.piping.saas.abs.AbsAuth;
import indi.shinado.piping.saas.abs.AbsFile;
import indi.shinado.piping.saas.abs.AbsObject;
import indi.shinado.piping.saas.abs.AbsQuery;

import static com.ss.aris.open.util.ManifestUtil.getMetaData;

public class SaasFactory {

    public static ISFile getFile(Context context, String name, byte[] bytes) {
        try {
            String clsName = getMetaData(context, "sFile");
            Class cls = Class.forName(clsName);
            ISFile object = (ISFile) cls.newInstance();
            object.setup(name, bytes);
            return object;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return new AbsFile();
    }

    public static ISObject getObject(Context context, String name) {
        try {
            String clsName = getMetaData(context, "sObject");
            Class cls = Class.forName(clsName);
            ISObject object = (ISObject) cls.newInstance();
            object.setName(name);
            return object;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return new AbsObject();
    }

    public static ISQuery getQuery(Context context, String name) {
        try {
            String clsName = getMetaData(context, "sQuery");
            Class cls = Class.forName(clsName);
            ISQuery object = (ISQuery) cls.newInstance();
            object.setName(name);
            return object;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return new AbsQuery();
    }


    public static IAuth getAuth(Context context) {
        return new AbsAuth();
    }
}
