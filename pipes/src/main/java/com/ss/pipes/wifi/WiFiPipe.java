package com.ss.pipes.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;

import com.ss.aris.open.pipes.action.DefaultInputActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;

public class WiFiPipe extends DefaultInputActionPipe {

    public WiFiPipe(int id) {
        super(id);
    }

    @Override
    public String getDisplayName() {
        return "$WiFi";
    }

    @Override
    public SearchableName getSearchable() {
        return new SearchableName("wi", "fi");
    }

    @Override
    public void onParamsEmpty(Pipe rs, OutputCallback callback) {
        roll(callback);
    }

    @Override
    public void onParamsNotEmpty(Pipe rs, OutputCallback callback) {
        roll(callback);
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        roll(callback);
    }

    private void roll(OutputCallback callback) {
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        switch (wm.getWifiState()) {
            case WifiManager.WIFI_STATE_DISABLING:
                callback.onOutput("WiFi is being disabled");
                break;
            case WifiManager.WIFI_STATE_DISABLED:
                if (callback == getConsoleCallback()) {
                    callback.onOutput("WiFi is off, enabling now...");
                    wm.setWifiEnabled(true);

                    context.registerReceiver(mWifiScanReceiver,
                            new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            context.unregisterReceiver(mWifiScanReceiver);
                        }
                    }, 10 * 1000);
                } else {
                    callback.onOutput("WiFi is off.");
                }
                break;
            case WifiManager.WIFI_STATE_ENABLING:
                callback.onOutput("WiFi is being enabled");
                break;
            case WifiManager.WIFI_STATE_ENABLED:
                if (callback == getConsoleCallback()) {
                    callback.onOutput("WiFi is on, now disabling...");
                    wm.setWifiEnabled(false);
                } else {
                    callback.onOutput("WiFi is on.");
                }
                break;
            case WifiManager.WIFI_STATE_UNKNOWN:
                callback.onOutput("Unknown error");
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private final BroadcastReceiver mWifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            String action = intent.getAction();
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
                ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = conMan.getActiveNetworkInfo();
                if (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    WifiInfo info = wm.getConnectionInfo();
                    String ssid = info.getSSID();
                    getConsoleCallback().onOutput("WiFi connected to " + ssid);
                }
            }

//            if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)) {
//                List<ScanResult> mScanResults = wm.getScanResults();
//                StringBuilder sb = new StringBuilder();
//            }
        }
    };

}
