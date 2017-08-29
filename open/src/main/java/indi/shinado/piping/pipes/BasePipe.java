package indi.shinado.piping.pipes;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.Log;
import java.util.TreeSet;
import indi.shinado.piping.launcher.Console;
import indi.shinado.piping.pipes.entity.Instruction;
import indi.shinado.piping.pipes.entity.Pipe;
import indi.shinado.piping.pipes.entity.SearchableName;
import indi.shinado.piping.pipes.search.translator.AbsTranslator;

public abstract class BasePipe {

    protected int id;

    protected Context context;
    protected Console console;
    protected IPipeManager pipeManager;

    private OutputCallback mConsoleCallback;

    public BasePipe(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void startExecution(Pipe item) {
        execute(item, mConsoleCallback);
    }

    public void startExecution(Pipe item, OutputCallback callback){
        execute(item, callback);
    }

    /**
     * execute an instruction
     * if rs has previous items, accept input from the previous ones.
     */
    private void execute(final Pipe rs, final OutputCallback callback) {
//        Instruction instruction = rs.getInstruction();
        Pipe.PreviousPipes previous = rs.getPrevious();
        if (previous == null) {
            tryGetOutput(rs, callback);
            return;
        }

        if (!previous.isEmpty()) {
            Pipe prev = previous.get();
            //create a new copy of previous
            //since previous will be set null
            //better not to get previous from rs
            final Pipe.PreviousPipes newPrevious
                    = new Pipe.PreviousPipes(previous);
            if (rs.ignoreInput()) {
                acceptInput(rs, "", newPrevious, callback);
            } else {
                BasePipe base = prev.getBasePipe();
                if (base != null) {
                    prev.getBasePipe().execute(prev, new OutputCallback() {
                        @Override
                        public void onOutput(String input) {
//                            getConsole().releaseInput();
                            acceptInput(rs, input, newPrevious, callback);
                        }
                    });
                }
            }
        } else {
//                acceptInput(rs, instruction.pre, null, callback);
//            if (!hasNext) {
//                execute(rs);
//            } else {
                tryGetOutput(rs, callback);
//            }
        }
    }

    private void tryGetOutput(Pipe rs, OutputCallback callback){
        if (callback != null && callback.equals(mConsoleCallback)){
            execute(rs);
        }else {
            getOutput(rs, callback);
        }
    }

    //fulfill with KeyIndex and Instruction
    protected void fulfill(Pipe item, Instruction instruction) {
        int keyIndex = getKeyIndex(item, instruction.body);
        Log.d("KeyIndex", "key index: " + instruction.body
                + ", " + item.getDisplayName() + ", " + keyIndex);
        item.setKeyIndex(keyIndex);
        item.setInstruction(instruction);
        //only set previous for the first item
        //pass it on to next when shifting
//            item.setPrevious(prev);
    }

    private int getKeyIndex(Pipe item, String body, boolean first) {
        int i = 2;
        //set key index
        SearchableName searchableName = item.getSearchableName();
        if (searchableName != null) {
            //with highest priority
            if (first) {
                if (body.equals(searchableName.toString())) {
                    return 0;
                }
                if (searchableName.toString().startsWith(body)) {
                    return 1;
                }
            }

            for (String str : searchableName.getNames()) {
                if (str.isEmpty()) continue;
                if (str.startsWith(body)) break;

                if (body.startsWith(str)) {
                    i = getKeyIndex(item, body.replace(str, ""), false);
                    break;
                }
                i++;
            }
        }

        return i;
    }

    /**
     * return the key index to be searched
     * e.g. input "k"
     * ["k"] -> 0
     * ["kakao", "talk"] -> 1
     * ["we", "kite"] -> 2
     * ["we", "chat"] -> 3
     * so that "kakao talk" will come first
     */
    private int getKeyIndex(Pipe item, String body) {
//        return item.getSearchableName().getKeyIndex(body);
        return getKeyIndex(item, body, true);
    }

    public IPipeManager getPipeManager() {
        return pipeManager;
    }

    void setPipeManager(IPipeManager pipeManager) {
        this.pipeManager = pipeManager;
    }

    public Console getConsole() {
        return console;
    }

    public void setConsole(Console console) {
        this.console = console;
        mConsoleCallback = console.getOutputCallback();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    protected OutputCallback getConsoleCallback() {
        return mConsoleCallback;
    }

    /**
     * @param input    user input
     * @param length   the length of the input change, e.g.
     *                 "" -> "a" : 1
     *                 "a" -> "aoa" : 2
     *                 "aoa" -> "ao" : -1
     * @param callback to receive results
     */
    public abstract void search(String input, int length, Pipe previous, SearchResultCallback callback);

    /**
     * accept input from the successors of result
     * when this is called, pre is not empty
     *
     * @param result   this pipe
     * @param input    input from previous
     * @param previous the previous items. input would be plain text from user input when it is null
     * @param callback use callback.onOutput() to input text of execution
     */
    public abstract void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback);

    /**
     * get output for the next Pipe
     *
     * @param result   this pipe
     * @param callback use callback.onOutput() to input text of execution
     */
    public abstract void getOutput(Pipe result, OutputCallback callback);

    /**
     * execute with no previous
     */
    protected abstract void execute(Pipe rs);

    public abstract void load(AbsTranslator translator, OnItemsLoadedListener listener, int total);

    public abstract Pipe getByValue(String value);

    //added in 2016-03-16
    //intercept
    //since version 3
    public void intercept() {

    }

    @TargetApi(5)
    public void onSelectedAsPrevious(Pipe result) {
    }

    @TargetApi(12)
    public void onSelectedAsStart(Pipe result) {
    }

    @TargetApi(12)
    public void onUnselectedAsStart(Pipe result) {
    }

    @TargetApi(10)
    public void onPreviousDeselected(Pipe result) {
    }

    protected Context getContext() {
        return context;
    }

    public void reset() {
    }

    public void refresh() {
    }

    public interface OnItemsLoadedListener {
        void onItemsLoaded(int id, int total);
    }

    //added since version 3
    public interface OutputCallback {
        void onOutput(String output);
    }

    protected interface SearchResultCallback {
        void onSearchResult(TreeSet<Pipe> results, Instruction input);
    }

    public Pipe getDefaultPipe(){
        return null;
    }

}
