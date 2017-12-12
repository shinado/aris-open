package indi.ss.pipes.process;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import java.util.Locale;

import com.ss.aris.open.pipes.BasePipe;
import com.ss.aris.open.pipes.action.DefaultInputActionPipe;
import com.ss.aris.open.pipes.action.SimpleActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;

public class ProcessPipe extends SimpleActionPipe {

    public ProcessPipe(int id) {
        super(id);
    }

    @Override
    public String getDisplayName() {
        return "$rProcess";
    }

    @Override
    public SearchableName getSearchable() {
        return new SearchableName("r", "process");
    }

    @Override
    protected void doExecute(Pipe rs, OutputCallback callback) {
        ActivityManager activityManager = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);

        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            callback.onOutput(String.format(Locale.getDefault(),
                    "MEMORY USAGE\nAvailable %.2f MB Total %.2fMB\n%s",
                    memoryInfo.availMem / 2014f, memoryInfo.totalMem / 2014f,
                    getBar((int) (100 * (memoryInfo.availMem / (float) memoryInfo.totalMem)))));
        }else {
            callback.onOutput(String.format(Locale.getDefault(), "Available %.2f MB",
                    memoryInfo.availMem / 2014f));
        }
    }

    private String getBar(int percent) {
        percent = percent / 3;
        String s = "";
        for (int i = 0; i < percent; i++) {
            if (i < percent) {
                s += "█";
            } else {
                s += "░";
            }
        }
        return s;
    }

}
