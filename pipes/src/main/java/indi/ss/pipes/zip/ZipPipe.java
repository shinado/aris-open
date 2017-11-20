package indi.ss.pipes.zip;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import com.ss.aris.open.pipes.PConstants;
import com.ss.aris.open.pipes.action.SimpleActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import java.util.zip.ZipFile;

public class ZipPipe extends SimpleActionPipe {

    public ZipPipe(int id) {
        super(id);
    }

    @Override
    protected void doExecute(Pipe rs, OutputCallback callback) {

    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        if (previous.get().getId() == PConstants.ID_APPLICATION) {
            PackageManager pm = getContext().getPackageManager();
            try {
                String[] split = input.split(",");
                ActivityInfo ai = pm.getActivityInfo(new ComponentName(split[0], split[1]),
                        PackageManager.GET_META_DATA);

                ZipFile zipFile = new ZipFile(ai.applicationInfo.publicSourceDir);
            } catch (Exception e) {
                e.printStackTrace();
                callback.onOutput(e.getMessage());
            }
        } else {
            callback.onOutput("Target " + previous.get().getDisplayName() + " is not an application.");
        }
    }
}
