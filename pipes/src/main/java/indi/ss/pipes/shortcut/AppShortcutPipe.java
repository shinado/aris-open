package indi.ss.pipes.shortcut;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.ss.aris.open.pipes.PConstants;
import com.ss.aris.open.pipes.action.SimpleActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import java.util.zip.ZipFile;

public class AppShortcutPipe extends SimpleActionPipe {

    public AppShortcutPipe(int id) {
        super(id);
    }

    @Override
    protected void doExecute(Pipe rs, OutputCallback callback) {

    }

    @Override
    public String getDisplayName() {
        return "shortcut";
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        if (previous.get().getId() == PConstants.ID_APPLICATION) {
            PackageManager pm = getContext().getPackageManager();
            try {
                String[] split = input.split(",");
                ActivityInfo ai = pm.getActivityInfo(new ComponentName(split[0], split[1]),
                        PackageManager.GET_META_DATA);
                Bundle metaData = ai.metaData;
                if (metaData.containsKey("android.app.shortcuts")){
                    String value = metaData.getString("android.app.shortcuts");
                    ZipFile zipFile = new ZipFile(ai.applicationInfo.publicSourceDir);
                    zipFile.getEntry(value);
                }
            } catch (Exception e) {
                e.printStackTrace();
                callback.onOutput(e.getMessage());
            }
        } else {
            callback.onOutput("Target " + previous.get().getDisplayName() + " is not an application.");
        }
    }

}
