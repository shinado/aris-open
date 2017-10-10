package com.ss.aris.open.payment;


import android.content.Context;
import android.content.pm.PackageManager;

import static com.ss.aris.open.util.ManifestUtil.getMetaData;

public class PaymentManager {

    public static IPayment getPayment(Context context) {
        try {
            String clsName = getMetaData(context, "payment");
            Class cls = Class.forName(clsName);
            return (IPayment) cls.newInstance();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return new AbsPayment();
    }

}
