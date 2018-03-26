package indi.ss.pipes.appshortcut;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.os.UserHandle;
import com.ss.aris.open.pipes.PConstants;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.search.FullSearchActionPipe;
import com.ss.aris.open.pipes.search.translator.AbsTranslator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppShortcutPipe extends FullSearchActionPipe {

    private AbsTranslator mTranslator;
    private Pipe starter;
    private Map<String, ShortcutInfoCompat> shortcutInfos = new HashMap<>();

    public AppShortcutPipe(int id) {
        super(id);
    }

    @Override
    protected void doAcceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        Pipe prev = previous.get();
        if (prev.getId() == PConstants.ID_APPLICATION) {
            String[] split = prev.getExecutable().split(",");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                List<ShortcutInfoCompat> list =
                        DeepShortcutManager.getInstance(getContext()).queryForFullDetails(
                                split[0], null, new UserHandle(null));
                if (list != null && !list.isEmpty()) {
                    for (ShortcutInfoCompat shortcut : list) {
                        String label = shortcut.getShortLabel().toString();
                        String id = shortcut.getId();
                        shortcutInfos.put(id, shortcut);
                        putItemInMap(new Pipe(getId(), label, mTranslator.getName(label), id));
                    }
                    return;
                }
            } else {
                getConsole().input("Not supported for systems under Android 8.0. ");
            }
        } else {
            getConsole().input("previous is not an application");
        }

        end();
    }

    @Override
    protected void doExecute(Pipe rs, OutputCallback callback) {
        ShortcutInfoCompat shortcut = shortcutInfos.get(rs.getExecutable());
        if (shortcut != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                DeepShortcutManager.getInstance(context).startShortcut(
                        shortcut.getPackage(), shortcut.getId(), new Intent(),
                        new Bundle(), Process.myUserHandle());
            }
        }
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

    @Override
    protected void end() {
        super.end();
        shortcutInfos.clear();
    }
}
