package indi.ss.pipes.qsetting;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import java.util.Map;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;
import com.ss.aris.open.pipes.search.FullSearchActionPipe;

// Failed resolution of: Lindi/shinado/piping/pipes/search/FullSearchActionPipe;
@TargetApi(8)
public class QuickSettingPipe extends FullSearchActionPipe {

    private String HELP = "Now you are in QuickSetting. Please choose from following commands:\n";
    private String DOMAIN = "qsetting:";
    private SharedPreferences pStorage;
    private Pipe starter;
    private static final String[] DISPLAY_NAMES = new String[]{
            "$ACCESSIBILITY", "$APPLICATION", "$DEVELOP",
            "$DATE", "$DISPLAY", "$LOCATION", "$WIFI",
            "$LANGUAGE", "$NOTIFICATION", "HOME"
    };
    private static final String[] SEARCH_NAMES = new String[]{
            "accessibility", "application", "develop",
            "date", "display", "location", "wifi",
            "language", "notification", "home"
    };
    private static final String[] VALUES = new String[]{
            Settings.ACTION_ACCESSIBILITY_SETTINGS,
            Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS,
            Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS,
            Settings.ACTION_DATE_SETTINGS,
            Settings.ACTION_DISPLAY_SETTINGS,
            Settings.ACTION_LOCATION_SOURCE_SETTINGS,
            Settings.ACTION_WIFI_SETTINGS,
            Settings.ACTION_LOCALE_SETTINGS,
            Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS,
            Settings.ACTION_HOME_SETTINGS
    };

    public QuickSettingPipe(int id) {
        super(id);

        load();
    }

    @Override
    public void setContext(Context context) {
        super.setContext(context);
        pStorage = context.getSharedPreferences(DOMAIN, Context.MODE_PRIVATE);
        Map<String, ?> all = pStorage.getAll();
        for (String key: all.keySet()){
            String value = (String) all.get(key);
            String display = key.replace(DOMAIN, "");
            Pipe p = new Pipe(id, display, new SearchableName("", display), value);
            p.setBasePipe(this);
            putItemInMap(p);
        }
    }

    @Override
    protected void doAcceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        //add
        if (input.contains(",")){
            String[] split = input.split(",");
            if (split.length == 2){
                if (pStorage != null){
                    pStorage.edit().putString(DOMAIN + split[0], split[1]).apply();
                }
            }else {

            }
        }else {

        }
    }

    private void load() {
        starter = new Pipe(id, "$qSetting", new SearchableName("q", "setting"), "$#qs");
        starter.setBasePipe(this);

        for (int i = 0; i < DISPLAY_NAMES.length; i++) {
            HELP += SEARCH_NAMES[i] + "\n";
            Pipe p = new Pipe(id, DISPLAY_NAMES[i], new SearchableName("", SEARCH_NAMES[i]), VALUES[i]);
            putItemInMap(p);
            p.setBasePipe(this);
        }
    }

    @Override
    protected void justStart() {
        super.justStart();
        getConsole().input(HELP);
    }

    @Override
    protected void doExecute(Pipe rs, OutputCallback callback) {
        if (callback == getConsoleCallback()) {
            try {
                getContext().startActivity(new Intent(rs.getExecutable()));
            } catch (Exception e) {
                e.printStackTrace();
                getConsole().input("Can not execute " + rs.getExecutable());
            }
            end();
            //WHAT????
//        }else {
//            callback.onOutput("qSetting");
        }
    }

    @Override
    public Pipe getDefaultPipe() {
        return starter;
    }

    @Override
    public void onDestroy() {

    }


}
