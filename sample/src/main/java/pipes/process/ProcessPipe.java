package pipes.process;

import android.app.ActivityManager;
import android.content.Context;
import android.text.format.Formatter;

import java.io.IOException;
import java.util.List;

import com.ss.aris.open.pipes.action.DefaultInputActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;
import com.ss.aris.open.widgets.impl.process.ProcessManager;
import com.ss.aris.open.widgets.impl.process.models.AndroidAppProcess;
import com.ss.aris.open.widgets.impl.process.models.Status;

public class ProcessPipe extends DefaultInputActionPipe {

    private int mMaxProceesses = 5;

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
    public void onParamsEmpty(Pipe rs, OutputCallback callback) {
        ActivityManager actvityManager = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);

        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        actvityManager.getMemoryInfo(memoryInfo);

//        float use = (memoryInfo.totalMem-memoryInfo.availMem) / 1000f;
//        float total = memoryInfo.totalMem / 1000f;

        StringBuilder head = new StringBuilder();
        head
                .append("Idle:   ")
                .append(Formatter.formatFileSize(context, memoryInfo.availMem))
                .append("\n")
                .append("Total:  ")
                .append(Formatter.formatFileSize(context, memoryInfo.totalMem))
                .append("\n");

        /**
         * Name:   bash
         * State:  S (sleeping)
         * Tgid:   3515
         * Pid:    3515
         * PPid:   3452
         * TracerPid:      0
         * Uid:    1000    1000    1000    1000
         * Gid:    100     100     100     100
         * FDSize: 256
         * Groups: 16 33 100
         * VmPeak:     9136 kB
         * VmSize:     7896 kB
         */
        int count = 0;
        List<AndroidAppProcess> list = ProcessManager.getRunningAppProcesses(context);
        for (AndroidAppProcess item : list) {
            Status status;
            try {
                status = item.status();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            head
                    .append("Name:   ").append(status.getValue("Name")).append("\n")
                    .append("State:  ").append(status.getValue("State")).append("\n")
                    .append("Pid:    ").append(status.getValue("Pid")).append("\n")
                    .append("VmRSS:  ").append(status.getValue("VmRSS")).append("\n");

            if (++count >= mMaxProceesses) {
                break;
            }
        }

        callback.onOutput(head.toString());
    }

    @Override
    public void onParamsNotEmpty(Pipe rs, OutputCallback callback) {

    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {

    }
}
