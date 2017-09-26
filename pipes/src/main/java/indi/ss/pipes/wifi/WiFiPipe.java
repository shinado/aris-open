package indi.ss.pipes.wifi;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import com.ss.aris.open.console.SingleLineInputCallback;
import com.ss.aris.open.pipes.action.DefaultInputActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;
import java.util.List;

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
        WifiManager wm = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        if (wm == null) {
            getConsole().input("Error accessing WiFi status. ");
            return;
        }
        switch (wm.getWifiState()) {
            case WifiManager.WIFI_STATE_DISABLING:
                callback.onOutput("WiFi is being disabled");
                break;
            case WifiManager.WIFI_STATE_DISABLED:
                if (callback == getConsoleCallback()) {
                    callback.onOutput("WiFi is off, now enabling...");
                    wm.setWifiEnabled(true);

                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                    intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
                    context.registerReceiver(mWifiScanReceiver, intentFilter);

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
            WifiManager wm = (WifiManager) c.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
                ConnectivityManager conMan = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (conMan != null) {
                    @SuppressLint("MissingPermission")
                    NetworkInfo netInfo = conMan.getActiveNetworkInfo();
                    if (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        if (wm != null) {
                            WifiInfo info = wm.getConnectionInfo();
                            String ssid = info.getSSID();
                            getConsoleCallback().onOutput("WiFi connected to " + ssid);
                        }
                    }
                }
            }

            if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)) {
                if (wm != null) {
                    List<ScanResult> mScanResults = wm.getScanResults();
                    getConsoleCallback().onOutput("WiFi available, please click on SSID to join. ");
                    for (ScanResult result : mScanResults) {
                        OutputCallback callback = getConsoleCallback();
                        if (callback instanceof AdvancedOutputCallback) {
                            ((AdvancedOutputCallback) callback).onOutput(result.SSID, new OnOutputClickListener() {
                                @Override
                                public void onClick(final String value) {
                                    getConsole().input("Password for WiFi " + value + " please. ");
                                    getConsole().waitForPasswordInput(new SingleLineInputCallback() {
                                        @Override
                                        public void onUserInput(String userInput) {
                                            WifiConfiguration conf = new WifiConfiguration();
                                            conf.SSID = "\"" + value + "\"";

                                            //WEP
                                            conf.wepKeys[0] = "\"" + userInput + "\"";
                                            conf.wepTxKeyIndex = 0;
                                            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                                            conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);

                                            //WPA
                                            conf.preSharedKey = "\"" + userInput + "\"";

                                            //open network
                                            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

                                            WifiManager wifiManager = (WifiManager) context.getApplicationContext()
                                                    .getSystemService(Context.WIFI_SERVICE);
                                            if (wifiManager != null) {
                                                wifiManager.addNetwork(conf);

                                                List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
                                                for (WifiConfiguration i : list) {
                                                    if (i.SSID != null && i.SSID.equals("\"" + value + "\"")) {
                                                        wifiManager.disconnect();
                                                        wifiManager.enableNetwork(i.networkId, true);
                                                        wifiManager.reconnect();

                                                        break;
                                                    }
                                                }
                                            }

                                        }
                                    }, true);
                                }
                            });
                        }
                    }
                }

            }
        }
    };

}
