package indi.ss.pipes.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import com.ss.aris.open.console.functionality.IAppWidget;
import com.ss.aris.open.pipes.PConstants;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;
import com.ss.aris.open.pipes.search.SearchablePipe;
import com.ss.aris.open.pipes.search.translator.AbsTranslator;
import java.util.List;

public class WidgetPipe extends SearchablePipe {

    private Pipe addPipe;
    private Pipe rmPipe;
    private Pipe defaultPipe;

    public WidgetPipe(int id) {
        super(id);

        defaultPipe = new Pipe(id, "$AppWidget", new SearchableName("app", "widget"), "$#appWidget");
        addPipe = new Pipe(id, "#iadd", new SearchableName("iadd"), "widget.add");
        rmPipe = new Pipe(id, "#irm", new SearchableName("irm"), "widget.rm");
        register(defaultPipe, addPipe, rmPipe);
    }

    @Override
    public void setContext(Context context) {
        super.setContext(context);
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        Pipe prev = previous.get();
        if (prev != null) {
            if (prev.getId() == PConstants.ID_APPLICATION) {
                String packageName = prev.getExecutable().split(",")[0];
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                List<AppWidgetProviderInfo> all = manager.getInstalledProviders();
                for (AppWidgetProviderInfo info : all) {
                    String pkg = info.provider.getPackageName();

                    if (pkg.equals(packageName)) {
                        if (info.configure != null) {
                            if (getConsole() instanceof IAppWidget) {
                                ((IAppWidget) getConsole()).addWidget(id, info);
                            }
                            return;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void getOutput(Pipe result, OutputCallback callback) {

    }

    @Override
    protected void execute(Pipe rs) {
        if (getConsole() instanceof IAppWidget) {
            ((IAppWidget) getConsole()).pickWidget();
        }
    }

    @Override
    public void load(AbsTranslator translator, OnItemsLoadedListener listener, int total) {
        listener.onItemsLoaded(this, total);
    }

    @Override
    public Pipe getByValue(String value, String params) {
        return null;
    }

    @Override
    public Pipe getDefaultPipe() {
        return defaultPipe;
    }
}
