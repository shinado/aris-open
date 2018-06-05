package com.ss.widget.blue;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.BatteryManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import com.ss.aris.open.console.Console;
import com.ss.aris.open.console.impl.DeviceConsole;
import com.ss.aris.open.widget.ArisWidget;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BlueWidget extends ArisWidget{

    public static final int ID = 2;
    public static final String HEAD = "com.ss.widget.blue";
    private WidgetBlueLayout layout;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
    private static final int INTERVAL = 1000;
    private int count = 0;

    @Override
    public void onCreate(Context context, Console console) {
        super.onCreate(context, console);
        registerReceiver();

        registerIntervalTask(new Runnable() {
            @Override
            public void run() {
                refreshTime();
                if (count++ % 60 == 0) refreshDate();
            }
        }, INTERVAL);
    }

    @Override
    public View getView(ViewGroup parent, String value) {
        if (layout == null) layout = new WidgetBlueLayout(context, getResource());

        if (console instanceof DeviceConsole){
            setTypeface(((DeviceConsole) console).getTypeface());
        }
        animateMore();

        return layout;
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        context.registerReceiver(mReceiver, filter);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                        status == BatteryManager.BATTERY_STATUS_FULL;
//
                int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
                boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

                float batteryPct = level / (float) scale;
                int percent = (int) (batteryPct * 100);

                layout.getGrid_percent().setText(percent+"%");

                if (isCharging){
                    if (usbCharge){
                        layout.getGrid_status().setText("CONNECTED");
                    }else {
                        layout.getGrid_status().setText("CHARGING");
                    }
                }else {
                    layout.getGrid_status().setText("IDLE");
                }
            }
        }
    };

    private void refreshDate(){
        Date date = new Date();
        String month = new SimpleDateFormat("MMMM", Locale.ENGLISH).format(date);
        String week = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
        String day = new SimpleDateFormat("dd", Locale.ENGLISH).format(date);

        layout.getDate_month().setText(month);
        layout.getDate_weekday().setText(week);
        layout.getDate_day().setText(day);
    }

    private void refreshTime() {
        layout.getTime().setText(sdf.format(new Date()));
    }

    private void setTypeface(Typeface font){
        layout.getDate_day().setTypeface(font);
        layout.getDate_weekday().setTypeface(font);
        layout.getDate_month().setTypeface(font);
        layout.getTime().setTypeface(font);
        layout.getGrid_percent().setTypeface(font);
        layout.getGrid_status().setTypeface(font);
        layout.getPower().setTypeface(font);
    }

    private void animateMore(){
        Animation anim1 = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        anim1.setRepeatCount(Animation.INFINITE);
        anim1.setRepeatMode(Animation.RESTART);
        anim1.setInterpolator(new LinearInterpolator());
        anim1.setDuration(9000);
        layout.getMore_background_1().startAnimation(anim1);

        Animation anim2 = new RotateAnimation(0, -360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        anim2.setRepeatCount(Animation.INFINITE);
        anim2.setRepeatMode(Animation.RESTART);
        anim2.setInterpolator(new LinearInterpolator());
        anim2.setDuration(8000);
        layout.getMore_background_2().startAnimation(anim2);

        Animation anim3 = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        anim3.setDuration(7800);
        anim3.setRepeatCount(Animation.INFINITE);
        anim3.setInterpolator(new LinearInterpolator());
        anim3.setRepeatMode(Animation.RESTART);
        layout.getMore_background_3().startAnimation(anim3);

        Animation anim4 = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        anim4.setDuration(8000);
        anim4.setInterpolator(new LinearInterpolator());
        anim4.setRepeatCount(Animation.INFINITE);
        anim4.setRepeatMode(Animation.RESTART);
        layout.getDate_arc().startAnimation(anim4);
    }

}