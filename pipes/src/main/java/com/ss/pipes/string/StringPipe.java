package com.ss.pipes.string;

import android.content.Intent;
import java.io.File;

import com.ss.aris.open.array.PipeArray;
import com.ss.aris.open.pipes.entity.Instruction;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;
import com.ss.aris.open.pipes.impl.ShareIntent;
import com.ss.aris.open.pipes.impl.interfaces.Helpable;
import com.ss.aris.open.pipes.search.SearchablePipe;
import com.ss.aris.open.pipes.search.translator.AbsTranslator;
import com.ss.aris.open.pri.PRI;
import com.ss.aris.open.util.JsonUtil;

public class StringPipe extends SearchablePipe implements Helpable{

    private static final String HELP = "Use of $s:\n[input].$s to ";
    private Pipe startWith;
    private Pipe endWith;

    public StringPipe(int id) {
        super(id);
        startWith = new Pipe(id, "$startw", new SearchableName("start", "with"), "$#startw");
        endWith = new Pipe(id, "$endw", new SearchableName("end", "with"), "$#endw");
        register(startWith, endWith);
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public Pipe getByValue(String value) {
        return null;
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        try {
            ShareIntent shareIntent = JsonUtil.fromJson(input, ShareIntent.class);
            if (Intent.ACTION_SEND.equals(shareIntent.action)) {
                String stream = shareIntent.extras.get(Intent.EXTRA_STREAM);
                if (stream != null) {
                    //for directory
                    File file = new File(stream);
                    if (file.exists() && file.isDirectory()) {
                        String[] list = file.list();
                        Instruction instruction = result.getInstruction();
                        if (!instruction.isParamsEmpty()) {
                            PipeArray array = new PipeArray();

                            if (result.equals(startWith)) {
                                for (String child : list) {
                                    if (child.startsWith(instruction.params[0])) {
                                        add(array, previous, stream + File.separator + child);
                                    }
                                }
                            } else if (result.equals(endWith)) {
                                for (String child : list) {
                                    if (child.endsWith(instruction.params[0])) {
                                        add(array, previous, stream + File.separator + child);
                                    }
                                }
                            }

                            callback.onOutput(JsonUtil.toJson(array));
                        }
                    }
                }
            }

            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

        Instruction instruction = result.getInstruction();
        if (!instruction.isParamsEmpty()) {
            String param = instruction.params[0];
            String[] lines = input.split("\n");

            StringBuilder sb = new StringBuilder();

            if (result.equals(startWith)) {
                for (String line : lines) {
                    if (line.startsWith(param)){
                        sb.append(line).append("\n");
                    }
                }
            } else if (result.equals(endWith)){
                for (String line : lines) {
                    if (line.endsWith(param)){
                        sb.append(line).append("\n");
                    }
                }
            }

            callback.onOutput(sb.toString());
        }
    }

    //TODO
    private void add(PipeArray array, Pipe.PreviousPipes previous, String file){
//        Pipe p = DirectoryPipe.ofFilePath(file);
//        Pipe prev = previous.get();
//        p.setBasePipe(prev.getBasePipe());
//        p.setId(prev.getId());
//        array.add(p);
    }

    @Override
    public void getOutput(Pipe result, OutputCallback callback) {
        callback.onOutput(HELP);
    }

    @Override
    protected void execute(Pipe rs) {
        getConsole().input(HELP);
    }

    @Override
    public void load(AbsTranslator translator, OnItemsLoadedListener listener, int total) {
        listener.onItemsLoaded(this, total);
    }

    @Override
    public String getHelp() {
        return new PRI("admin.ss.web",
                "http://arislauncher.com/aris/guide/2017/07/25/string/").toString();
    }

}
