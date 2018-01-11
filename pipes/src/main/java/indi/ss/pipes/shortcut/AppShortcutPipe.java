package indi.ss.pipes.shortcut;

import android.os.Build;
import android.os.UserHandle;

import com.ss.aris.open.TargetVersion;
import com.ss.aris.open.pipes.PConstants;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.search.FullSearchActionPipe;
import com.ss.aris.open.pipes.search.translator.AbsTranslator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppShortcutPipe extends FullSearchActionPipe{

    private AbsTranslator mTranslator;
    private Pipe starter;
    private Map<String, ShortcutInfoCompat> shortcutInfos = new HashMap<>();

    public AppShortcutPipe(int id) {
        super(id);
    }

    @Override
    protected void doAcceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        ShortcutInfoCompat shortcut = shortcutInfos.get(result.getExecutable());
        if (shortcut != null){
//            DeepShortcutManager.getInstance(context).startShortcut();
        }
    }

    @Override
    protected void doExecute(Pipe rs, OutputCallback callback) {

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
        super.start(result);
        doStart(result);
    }

    @Override
    protected void startAsSelected(Pipe result) {
        super.startAsSelected(result);
        doStart(result);
    }

    private void doStart(Pipe result){
        if (result.getPrevious().isEmpty()) {
            getConsole().input("previous is empty");
        } else {
            Pipe prev = result.getPrevious().get();
            if (prev.getId() == PConstants.ID_APPLICATION) {
                String[] split = prev.getExecutable().split(",");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    List<ShortcutInfoCompat> list =
                            DeepShortcutManager.getInstance(getContext()).queryForFullDetails(
                                    split[0], null, new UserHandle(null));
                    for (ShortcutInfoCompat shortcut: list){
                        String label = shortcut.getShortLabel().toString();
                        String id = shortcut.getId();
                        shortcutInfos.put(id, shortcut);
                        putItemInMap(new Pipe(getId(), label, mTranslator.getName(label), id));
                    }
                }else {
                    getConsole().input("Not supported below Android8.0. ");
                }
                return;
            }else {
                getConsole().input("previous is not an application");
            }
        }
        end();
    }

}
