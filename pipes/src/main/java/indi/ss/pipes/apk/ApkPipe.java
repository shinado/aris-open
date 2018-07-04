package indi.ss.pipes.apk;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.ss.aris.open.pipes.PConstants;
import com.ss.aris.open.pipes.action.DefaultInputActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;
import com.ss.aris.open.pipes.impl.ShareIntent;

@TargetApi(8)
public class ApkPipe extends DefaultInputActionPipe {

    public ApkPipe(int id) {
        super(id);
    }

    @Override
    public String getDisplayName() {
        return "$apk";
    }

    @Override
    public SearchableName getSearchable() {
        return new SearchableName("apk");
    }

    @Override
    public void onParamsEmpty(Pipe rs, OutputCallback callback) {
        callback.onOutput("Usage of $apk:\n" +
                "[application].apk.[target] to extract apk and send it to target");
    }

    @Override
    public void onParamsNotEmpty(Pipe rs, OutputCallback callback) {
        callback.onOutput("Usage of $apk:\n" +
                "[application].apk.[target] to extract apk and send it to target");
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        if (previous.get().getId() == PConstants.ID_APPLICATION) {
            if (callback == getConsoleCallback()) {
                getConsole().hold(result, "Share with");
            } else {
                ShareIntent shareIntent = new ShareIntent(Intent.ACTION_SEND);
                shareIntent.setType("application/vnd.android.package-archive");
                PackageManager pm = getContext().getPackageManager();
                try {
                    ApplicationInfo appInfo = pm.getApplicationInfo(input.split(",")[0], 0);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, "file://" + appInfo.publicSourceDir);
                    callback.onOutput(shareIntent.toString());
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    callback.onOutput("Error. Can not find application.");
                }
            }
        } else {
            callback.onOutput("Target " + previous.get().getDisplayName() + " is not an application.");
        }
    }

    @Override
    protected int getAcceptTypeOnConnect() {
        return TYPE_APPLICATION;
    }

    @Override
    protected boolean asOutput() {
        return false;
    }

}
