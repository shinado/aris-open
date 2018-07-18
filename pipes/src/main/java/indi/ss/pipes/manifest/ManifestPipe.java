package indi.ss.pipes.manifest;

import android.annotation.TargetApi;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ss.aris.open.pipes.PConstants;
import com.ss.aris.open.pipes.action.DefaultInputActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;

@TargetApi(1061)
public class ManifestPipe extends DefaultInputActionPipe {

    public ManifestPipe(int id) {
        super(id);
    }

    @Override
    public String getDisplayName() {
        return "$manifest";
    }

    @Override
    public SearchableName getSearchable() {
        return new SearchableName("manifest");
    }

    @Override
    public void onParamsEmpty(Pipe rs, OutputCallback callback) {
        if (callback == getConsoleCallback()){
            callback.onOutput("Use [application].manifest to display detailed information. ");
        }else {
            callback.onOutput("manifest");
        }
    }

    @Override
    public void onParamsNotEmpty(Pipe rs, OutputCallback callback) {

    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        Pipe prev = previous.get();
        String[] split = prev.getExecutable().split(",");
        if (prev.getId() == PConstants.ID_APPLICATION && split.length == 2) {
            String packageName = split[0];
            String activityName = split[1];
            try {
                PackageInfo pInfo = context.getPackageManager().getPackageInfo(
                        packageName, 0);
                if (pInfo != null){
                    StringBuilder sb = new StringBuilder();
                    sb.append(prev.getDisplayName())
                            .append("\n")
                            .append("Package: ").append(packageName)
                            .append("\n")
                            .append("Activity: ").append(activityName)
                            .append("\n")
                            .append("Version: ").append(pInfo.versionName).append("(").append(pInfo.versionCode).append(")")
                            .append("\n")
                            .append("Installed: ").append(new SimpleDateFormat("yyyy-MM-dd hh:mm")
                                    .format(new Date(pInfo.firstInstallTime)))
                            .append("\n")
                            .append("Last update: ").append(new SimpleDateFormat("yyyy-MM-dd hh:mm")
                                    .format(new Date(pInfo.lastUpdateTime)));
                    callback.onOutput(sb.toString());
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected int getAcceptTypeOnConnect() {
        return TYPE_APPLICATION;
    }

}
