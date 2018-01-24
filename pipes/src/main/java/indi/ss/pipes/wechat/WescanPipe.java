package indi.ss.pipes.wechat;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import com.ss.aris.open.pipes.action.SimpleActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;

public class WescanPipe extends SimpleActionPipe {

    public WescanPipe(int id) {
        super(id);
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void doExecute(Pipe rs, OutputCallback callback) {
        if (callback == getConsoleCallback()) {
            try {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI"));
                intent.putExtra("LauncherUI.From.Scaner.Shortcut", true);
                intent.setFlags(335544320);
                intent.setAction("android.intent.action.VIEW");
                context.startActivity(intent);
            } catch (Exception e) {
                callback.onOutput(e.getMessage());
            }
        }else {
            callback.onOutput("wescan");
        }
    }

    @Override
    public String getDisplayName() {
        return "WeScan";
    }
}
