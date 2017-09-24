package indi.ss.pipes.system;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import com.ss.aris.open.pipes.action.DefaultInputActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;

public class IPAdressPipe extends DefaultInputActionPipe{

    public IPAdressPipe(int id) {
        super(id);
    }

    @Override
    public String getDisplayName() {
        return "$ip";
    }

    @Override
    public SearchableName getSearchable() {
        return new SearchableName("ip");
    }

    @Override
    public void onParamsEmpty(Pipe rs, OutputCallback callback) {
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        callback.onOutput(ip);
    }

    @Override
    public void onParamsNotEmpty(Pipe rs, OutputCallback callback) {
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
    }

}
