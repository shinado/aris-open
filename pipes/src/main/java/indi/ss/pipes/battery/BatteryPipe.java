package indi.ss.pipes.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.ss.aris.open.console.impl.DeviceConsole;
import com.ss.aris.open.pipes.action.SimpleActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;

public class BatteryPipe extends SimpleActionPipe {

    private String battery = "Battery loading...";
    private boolean hasRegistered = false;

    public BatteryPipe(int id) {
        super(id);
    }

    @Override
    protected void doExecute(Pipe rs, OutputCallback callback) {
        if (!hasRegistered) {
            hasRegistered = true;
            IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            context.registerReceiver(mReceiver, filter);
        }

        callback.onOutput(battery);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (hasRegistered) context.unregisterReceiver(mReceiver);
    }

    @Override
    public String getDisplayName() {
        return "battery";
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
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
                battery = "Battery " + getBar((int) (batteryPct * 100))  + " " + (int)batteryPct + "%";

                if (getConsole() instanceof DeviceConsole){
                    ((DeviceConsole) getConsole()).notify(getDefaultPipe());
                }
            }
        }
    };

    private String getBar(int percent) {
        percent = percent / 3;
        String s = "";
        for (int i = 0; i < percent; i++) {
            s += "█";
        }
        return s;
    }

}
