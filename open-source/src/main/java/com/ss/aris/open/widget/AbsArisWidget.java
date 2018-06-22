package com.ss.aris.open.widget;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;

public abstract class AbsArisWidget extends ArisWidget {

    protected final String CONNECTED = "CONNECTED";
    protected final String IDLE = "IDLE";
    protected final String CHARGING = "CHARGING";

    protected final int ON = 1;
    protected final int OFF = 0;
    protected final int DK = -1;

    private int count = 0;

    protected void start() {
        registerReceiver();

        registerIntervalTask(new Runnable() {
            @Override
            public void run() {
                refreshTime();
                if (count++ % 100 == 0) {
                    refreshDate();
                    refreshMemory(getMemoryInfo());
                }
            }
        }, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        context.unregisterReceiver(mReceiver);
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        context.registerReceiver(mReceiver, filter);
    }

    private int getMemoryInfo() {
        ActivityManager activityManager = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);

        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        if (activityManager != null) {
            activityManager.getMemoryInfo(memoryInfo);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                return (int) (100 * (memoryInfo.availMem / (float) memoryInfo.totalMem));
            } else {
                return -1;
//                String.format(Locale.getDefault(), "Available %d MB",
//                        memoryInfo.availMem / 1048576L);
            }
        } else {
            return -1;
        }
    }

    protected abstract void onBatteryStatusChanged(String status);
    protected abstract void onBatteryPercentChanged(int p);
    protected abstract void onWiFiStatusChanged(int status);
    protected abstract void onWiFiSignalChanged(int p);
    protected abstract void onBluetoothStatusChanged(int status);
    protected abstract void onBluetoothSignalChanged(int p);
    protected abstract void refreshTime();
    protected abstract void refreshDate();
    protected abstract void refreshMemory(int p);

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                        status == BatteryManager.BATTERY_STATUS_FULL;
//
                int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
//                boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

                if (isCharging) {
                    if (usbCharge) {
                        onBatteryStatusChanged(CONNECTED);
                    } else {
                        onBatteryStatusChanged(CHARGING);
                    }
                } else {
                    onBatteryStatusChanged(IDLE);
                }

                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

                float batteryPct = level / (float) scale;
                int percent = (int) (batteryPct * 100);
                onBatteryPercentChanged(percent);
            } else if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action)) {
                int wifi = DK;
                int status = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                switch (status) {
                    case WifiManager.WIFI_STATE_DISABLED:
                    case WifiManager.WIFI_STATE_DISABLING:
                        wifi = OFF;
                        break;
                    case WifiManager.WIFI_STATE_ENABLED:
                    case WifiManager.WIFI_STATE_ENABLING:
                        wifi = ON;
                }

                NetworkInfo info = getNetworkInfo(context);
                if (info != null && info.isConnected()) {
                    wifi = ON;
                    WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    if (wm != null) {
                        onWiFiSignalChanged(getWiFiSignal());
                    }
                }

                onWiFiStatusChanged(wifi);
            } else if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
                onBluetoothSignalChanged(rssi);
//                String name = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
            } else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)){
                int status = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)
                        == BluetoothAdapter.STATE_OFF ? OFF: ON;
                onBluetoothStatusChanged(status);
            }
        }

        private NetworkInfo getNetworkInfo(Context context) {
            ConnectivityManager connManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        }

        private int getWiFiSignal() {
            ConnectivityManager conMan = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            WifiManager wm = (WifiManager) context.getApplicationContext()
                            .getSystemService(Context.WIFI_SERVICE);
            if (conMan != null) {
                NetworkInfo netInfo = conMan.getActiveNetworkInfo();
                if (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    if (wm != null) {
                        int total = 100;
                        WifiInfo info = wm.getConnectionInfo();
                        return WifiManager.calculateSignalLevel(info.getRssi(),
                                total);
                    }
                }
            }

            return 0;
        }
    };

}