package indi.ss.pipes.monitor;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.text.format.Formatter;

import com.ss.aris.open.console.impl.DeviceConsole;
import com.ss.aris.open.pipes.action.SimpleActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.util.TimeUtil;

/**
 * Time    2017-11-12 13:10:12
 * Battery █████████████████████░░░░░░░░░░
 * Memory  ███████████████████████████░░░░
 * WiFi    192.168.1.21
 */
public class MonitorPipe extends SimpleActionPipe {

    private String battery = "Battery loading";
    private String wifi = "WiFi  loading";

    private boolean hasRegistered = false;

    public MonitorPipe(int id) {
        super(id);
    }

    @Override
    protected void doExecute(Pipe rs, OutputCallback callback) {
        if (callback == getConsoleCallback()) {
            if (!hasRegistered) {
                hasRegistered = true;
                IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
                context.registerReceiver(mReceiver, filter);
                wifi = getWiFiStatus();
            }

            StringBuilder sb = new StringBuilder();
            sb.append(TimeUtil.getTimeWithSecond()).append("\n")
                    .append(getMemoryInfo()).append("\n")
                    .append(battery).append("\n")
                    .append(wifi);
            callback.onOutput(sb.toString());

            addNotifyTimeCircle(60 * 1000);
        }
    }

    private String getMemoryInfo() {
        ActivityManager actvityManager = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);

        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        actvityManager.getMemoryInfo(memoryInfo);

        return "Memory  " + getBar((int) (100 *(memoryInfo.availMem / memoryInfo.totalMem)));
//        return "Memory idle:   " +
//                Formatter.formatFileSize(context, memoryInfo.availMem) +
//                "\n" +
//                "Memory total:  ";
    }

    private String getWiFiStatus() {
        WifiManager wm = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        if (wm == null) {
            return "WiFi not available";
        } else {
            switch (wm.getWifiState()) {
                case WifiManager.WIFI_STATE_ENABLED:
                case WifiManager.WIFI_STATE_ENABLING:
                    return "WiFi connected";
                case WifiManager.WIFI_STATE_DISABLING:
                case WifiManager.WIFI_STATE_DISABLED:
                default:
                    return "WiFi not connected";
            }
        }
    }

    @Override
    public String getDisplayName() {
        return "monitor";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (hasRegistered) context.unregisterReceiver(mReceiver);
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
//                int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
//                boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
//                boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

                float batteryPct = level / (float) scale;
                battery = "Battery " + getBar((int) (batteryPct * 100));

//                battery = "Battery:" + (int) (batteryPct * 100) + "%" +
//                        (isCharging ? " charging" : "");
            } else if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action)) {
                if (NetworkUtil.TYPE_WIFI == NetworkUtil.getConnectivityStatus(context)) {
                    wifi = "WiFi connected";
                    WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    if (wm != null) {
                        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
                        wifi = "WiFi    " + ip;
                    }
                } else {
                    wifi = "WiFi not connected";
                }
            }

            if (getConsole() instanceof DeviceConsole){
                ((DeviceConsole) getConsole()).notify(getDefaultPipe());
            }
        }
    };

    private String getBar(int percent){
        percent = percent/5;
        String s = "";
        for (int i=0; i<20; i++){
            if (i<percent){
                s += "█";
            }else {
                s += "░";
            }
        }
        return s;
    }

}
