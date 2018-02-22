package indi.ss.pipes.shortcut;


import android.app.AppOpsManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;

import com.ss.aris.open.TargetVersion;
import com.ss.aris.open.console.impl.ResultCallback;
import com.ss.aris.open.pipes.PConstants;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.search.FullSearchActionPipe;
import com.ss.aris.open.pipes.search.translator.AbsTranslator;

import java.util.List;
import java.util.TreeSet;

public class ShortcutPipe extends FullSearchActionPipe {

    private AbsTranslator mTranslator;
    private Pipe starter;

    public ShortcutPipe(int id) {
        super(id);
    }

    @Override
    protected void doAcceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        if (previous.isEmpty()) {
            getConsole().input("previous is empty");
        } else {
            Pipe prev = previous.get();
            if (prev.getId() == PConstants.ID_APPLICATION) {
                String[] split = prev.getExecutable().split(",");

                PackageManager packageManager = context.getPackageManager();
                Intent shortcutIntent = new Intent(Intent.ACTION_CREATE_SHORTCUT);
                shortcutIntent.setPackage(split[0]);

                List<ResolveInfo> shortcuts = packageManager.queryIntentActivities(shortcutIntent, 0);
                if (shortcuts != null && !shortcuts.isEmpty()) {
                    for (ResolveInfo sc : shortcuts) {
                        String label = sc.activityInfo.loadLabel(packageManager).toString();
                        String packageName = sc.activityInfo.packageName;
                        String activityName = sc.activityInfo.name;

                        Pipe item = new Pipe(getId(), label, mTranslator.getName(label), packageName + "," + activityName);
                        putItemInMap(item);
                        Log.d("Shortcut", String.format("load: %s, %s, %s", label, packageName, activityName));
                    }

                    super.start(null);
                } else {
                    getConsole().input("No shortcut found. ");
                }

            } else {
                getConsole().input("previous is not an application");
            }
        }
    }

    @Override
    protected void doExecute(Pipe rs, OutputCallback callback) {
        try {
            launch(rs.getExecutable());
        } catch (Exception e) {
            callback.onOutput(e.getMessage());
        }
    }

    private void launch(String value) throws Exception {
        String[] split = value.split(",");
        ComponentName cn = new ComponentName(split[0], split[1]);

        final Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
        //with FLAG_ACTIVITY_MULTIPLE_TASK, QQLite would not launch occasionally
//        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setComponent(cn);
        getConsole().requestResult(intent, new ResultCallback() {
            @Override
            public void onActivityResult(int resultCode, Intent intent) {
                Bundle bundle = intent.getExtras();

            }
        });
    }

    @Override
    public void load(AbsTranslator translator, OnItemsLoadedListener listener, int total) {
        mTranslator = translator;

        starter = new Pipe(id, "Shortcut");
        starter.setBasePipe(this);
        super.load(translator, listener, total);
    }

    @Override
    public Pipe getDefaultPipe() {
        return starter;
    }

    @TargetVersion(1192)
    @Override
    protected void start(Pipe result) {
        getConsole().input("previous is empty");
    }

    @Override
    protected void startAsSelected(Pipe result) {
        getConsole().input("previous is empty");
    }

    @Override
    protected void end() {
        super.end();
    }
}
