package com.ss.pipes.note;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Map;
import java.util.TreeMap;
import com.ss.aris.open.pipes.action.DefaultInputActionPipe;
import com.ss.aris.open.pipes.entity.Keys;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;
import com.ss.aris.open.pipes.impl.interfaces.Clearable;
import com.ss.aris.open.pipes.impl.interfaces.Helpable;

//something.note
//note.clear
//note 1.clear
//note >1.clear
//note >=1.clear
public class NotePipe extends DefaultInputActionPipe implements Clearable, Helpable{

    private static final String NAME = "anote";

    private static final String HELP = "Usage of " + NAME + "\n" +
            "<note>" + Keys.PIPE + "note to add a new note\n";

    private SharedPreferences sp;

    public NotePipe(int id) {
        super(id);
    }

    @Override
    public void setContext(Context context) {
        super.setContext(context);
        sp = context.getSharedPreferences("anote", Context.MODE_PRIVATE);
    }

    @Override
    public String getDisplayName() {
        return NAME;
    }

    @Override
    public SearchableName getSearchable() {
        return new SearchableName("anote");
    }

    @Override
    public void onParamsEmpty(Pipe rs, OutputCallback callback) {
        StringBuilder sb = new StringBuilder();
        Map<String, ?> map = sp.getAll();
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        for (String key : map.keySet()) {
            if ("index".equals(key)) continue;
            try {
                treeMap.put(Integer.parseInt(key), (String) map.get(key));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        for (int key : treeMap.keySet()){
            sb.append(key);
            sb.append(".");
            sb.append(map.get(key+""));
            sb.append("\n");
        }

        callback.onOutput(sb.toString());
    }

    @Override
    public void onParamsNotEmpty(Pipe rs, final OutputCallback callback) {
        callback.onOutput(rs.getExecutable());
    }

    private void addNewNote(String note) {
        int index = sp.getInt("index", 0);
        sp.edit().putString(index+"", note)
                .putInt("index", index+1)
                .apply();
        getConsole().notify(getDefaultPipe());
    }

    @Override
    public void acceptInput(Pipe rs, final String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        addNewNote(input);
    }

    @Override
    public void clear(Pipe rs) {
        String[] params = rs.getInstruction().params;
        if (params.length == 0){
            sp.edit().clear().apply();
            getConsole().notify(getDefaultPipe());
        }else {
            try {
                int index = Integer.parseInt(params[0]);
                sp.edit().remove(index+"").apply();
                getConsole().notify(getDefaultPipe());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getHelp() {
        return "Use <your-note>.note to add new note, " +
                "\"note <index>.clear\" to delete a certain note by its index. ";
    }

}
