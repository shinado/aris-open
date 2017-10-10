package indi.ss.pipes.website;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import java.util.Map;
import java.util.TreeSet;
import com.ss.aris.open.pipes.entity.Instruction;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;
import com.ss.aris.open.pipes.impl.interfaces.Helpable;
import com.ss.aris.open.pipes.search.SearchablePipe;
import com.ss.aris.open.pipes.search.translator.AbsTranslator;
import com.ss.aris.open.pipes.pri.PRI;
import com.ss.aris.open.util.VersionUtils;

@Deprecated
public class WebsitePipe extends SearchablePipe implements Helpable {

    private Pipe addPipe;
    private Pipe rmPipe;
    private Pipe defaultPipe;
    //    private HashMap<String, String> mWebsites = new HashMap<>();
    private SharedPreferences storage;

    public WebsitePipe(int id) {
        super(id);

        addPipe = new Pipe(id, "#wadd", new SearchableName("wadd"), "website.add");
        rmPipe = new Pipe(id, "#wrm", new SearchableName("wrm"), "website.rm");
        defaultPipe = new Pipe(id, "$bookmark", new SearchableName("bookmark"), "$#bookmark");
        register(addPipe, rmPipe, defaultPipe);
    }

    @Override
    public void setContext(Context context) {
        super.setContext(context);
        storage = context.getSharedPreferences("website", Context.MODE_PRIVATE);
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public Pipe getByValue(String value) {
        TreeSet<Pipe> result = resultMap.get(value);
        if (result.isEmpty()){
            String name = getSimpleHostFromUrl(value);
            Pipe pipe = new Pipe(getId(), name, new SearchableName(name), value);
            register(pipe);
            return pipe;
        }else {
            return (Pipe) result.toArray()[0];
        }
    }

    private String getSimpleHostFromUrl(String url) {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            try {
                String host = url.replace("http://", "")
                        .replace("https://", "");
                String[] split = host.split("\\.");
                if (split.length == 2) {
                    host = split[0];
                } else {
                    if (split.length > 2) {
                        host = split[1];
                    } else {
                        return null;
                    }
                }

                return host;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private void addBookmark(String url) {
        String host = getSimpleHostFromUrl(url);
        if (host != null) {
            storage.edit().putString(host, url).apply();
            putItemInMap(new Pipe(id, host, new SearchableName(host), url));
        }
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        if (result.equals(addPipe)) {
            if (input.contains(",")) {
                String[] split = input.split(",");
                for (String url : split) {
                    addBookmark(url);
                }

                callback.onOutput(split.length + " bookmarks added. ");
            } else {
                Instruction instruction = result.getInstruction();
                String host =
                        instruction.isParamsEmpty() ?
                                getSimpleHostFromUrl(input) :
                                instruction.params[0];

                if (host != null) {
                    storage.edit().putString(host, input).apply();
                    callback.onOutput("Bookmark <font color='#7E0009'>" + host + "</font> saved. ");
                    putItemInMap(new Pipe(id, "#" + host, new SearchableName(host), input));
                } else {
                    callback.onOutput("Website format wrong(" + input + ").");
                }
            }
        } else if (result.equals(rmPipe)) {
            Pipe prev = previous.get();
            if (prev.getId() == getId()) {
                removeItemInMap(prev);
                String name = prev.getDisplayName();
                storage.edit().remove(name.replace("#", "")).apply();
                callback.onOutput("Bookmark " + name + " removed.");
            }
        } else {
            String url = result.getExecutable();
            try {
                Uri uri = Uri.parse(url + input);
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                getContext().startActivity(it);
            } catch (Exception e) {
                e.printStackTrace();
                getConsole().input("Error in executing " + url);
            }
        }
    }

    @Override
    public void getOutput(Pipe result, OutputCallback callback) {
        callback.onOutput(result.getExecutable());
    }

    @Override
    protected void execute(Pipe rs) {
        if (rs.equals(defaultPipe)) {
            getConsole().display("Usage of $bookmark:\n" +
                    "[website].wadd to add a bookmark\n" +
                    "[name].wrm to remove a bookmark.\n");
        } else {
            String url = rs.getExecutable();
            if (url.contains("baidu")) {
                getConsole().input("对不起，朋友，魏则西是我朋友。");
            } else {
                try {
                    Uri uri = Uri.parse(url);
                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                    getContext().startActivity(it);
                } catch (Exception e) {
                    e.printStackTrace();
                    getConsole().input("Error in executing " + url);
                }
            }
        }
    }

    @Override
    public void onSelectedAsPrevious(Pipe result) {
        super.onSelectedAsPrevious(result);
        putItemInMap(rmPipe);
    }

    @Override
    public void onPreviousDeselected(Pipe result) {
        super.onPreviousDeselected(result);
        removeItemInMap(rmPipe);
    }

    @Override
    public void load(final AbsTranslator translator, final OnItemsLoadedListener listener, final int total) {
        Map<String, ?> all = storage.getAll();
        for (String key : all.keySet()) {
            String value = (String) all.get(key);
            Pipe pipe = new Pipe(getId(), "#" + key, new SearchableName(key), value);
            register(pipe);
        }

        listener.onItemsLoaded(this, total);
    }

    @Override
    public Pipe getDefaultPipe() {
        return defaultPipe;
    }

    @Override
    public String getHelp() {
        return new PRI("admin.ss.web",
                "http://arislauncher.com/aris/guide/2017/07/25/bookmark/").toString();
    }

    @Override
    protected int getAcceptTypeOnConnect() {
        return TYPE_TEXT;
    }

    @Override
    public void onInstalled() {
        super.onInstalled();

        if (VersionUtils.isChina()){
            addBookmark("http://www.bing.com/search?q=");
        }else {
            addBookmark("https://www.google.com/search?q=");
        }
    }

}
