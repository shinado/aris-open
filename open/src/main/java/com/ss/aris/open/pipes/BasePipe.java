package com.ss.aris.open.pipes;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.TreeSet;

import com.aris.open.R;
import com.ss.aris.open.TargetVersion;
import com.ss.aris.open.icons.AbsIconPackManager;
import com.ss.aris.open.pipes.configs.Configurations;
import com.ss.aris.open.pipes.entity.Keys;
import com.ss.aris.open.console.Console;
import com.ss.aris.open.pipes.entity.Instruction;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;
import com.ss.aris.open.pipes.search.translator.AbsTranslator;

public abstract class BasePipe {

    protected static final int TYPE_ALL = -1;
    protected static final int TYPE_NONE = 0;
    protected static final int TYPE_APPLICATION = 1;
    protected static final int TYPE_CONTACT = 2;
    protected static final int TYPE_PIPE = 4;
    protected static final int TYPE_TEXT = 8;

    protected boolean hasDestroyed = false;
    private boolean hasRunnable = false;
    protected int id;

    protected Context context;
    protected Configurations configurations;
    protected Console console;
    protected IPipeManager pipeManager;

    private OutputCallback mConsoleCallback;

    protected AbsIconPackManager ipManager;

    public BasePipe(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void startExecution(Pipe item) {
        execute(item, mConsoleCallback);
    }

    public void startExecution(Pipe item, OutputCallback callback) {
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

            if (rs.donotExecute()) {
//                setDonotExecute(rs);
                acceptInput(rs, "", newPrevious, callback);
            } else {
                BasePipe base = prev.getBasePipe();
                if (base != null) {
                    base.execute(prev, new OutputCallback() {
                        @Override
                        public void onOutput(String input) {
//                            getConsole().releaseInput();
                            acceptInput(rs, input, newPrevious, callback);
                        }
                    });
                }
            }
        } else {
            tryGetOutput(rs, callback);
        }
    }

    private void setDonotExecute(Pipe rs){
        rs.setDonotExecute(true);
        Pipe.PreviousPipes previous = rs.getPrevious();
        if (previous != null){
            TreeSet<Pipe> all = previous.getAll();
            for (Pipe p: all){
                setDonotExecute(p);
            }
        }
    }

    private void tryGetOutput(Pipe rs, OutputCallback callback) {
        if (callback != null && callback.equals(mConsoleCallback)) {
            execute(rs);
        } else {
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

    public void setPipeManager(IPipeManager pipeManager) {
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
        configurations = new Configurations(context);
    }

    @TargetVersion(1184)
    public void setIpManager(AbsIconPackManager ipManager){
        this.ipManager = ipManager;
    }

    protected OutputCallback getConsoleCallback() {
        return mConsoleCallback;
    }

    public void doSearch(String input, int length, Pipe previous, SearchResultCallback callback) {
        if (input.endsWith(Keys.PIPE)) {
            TreeSet<Pipe> results = getResultsOnConnect(input, previous);
            if (results != null) {
                callback.onSearchResult(results, new Instruction(input));
                return;
            }
        }

        search(input, length, previous, callback);
    }

    protected TreeSet<Pipe> getResultsOnConnect(String input, Pipe previous) {
        if (previous != null) {
            if (acceptable(previous)) {
                Pipe df = getDefaultPipe();
                if (df != null) {
                    fulfill(df, new Instruction(input));
                    TreeSet<Pipe> results = new TreeSet<>();
                    results.add(df);
                    return results;
                }
            }
        }
        return null;
    }

    protected boolean acceptable(Pipe previous) {
        int id = previous.getId();
        int acc = getAcceptTypeOnConnect();
        if (acc == TYPE_NONE) return false;
        if (acc == TYPE_ALL) return true;

        if (check(TYPE_APPLICATION, acc) &&
                id == PConstants.ID_APPLICATION &&
                previous.getPrevious().get() == null) {
            return true;
        }

        if (check(TYPE_CONTACT, acc) && id == PConstants.ID_CONTACT) {
            return true;
        }

        if (check(TYPE_TEXT, acc) && (id == PConstants.ID_TEXT || id == PConstants.ID_CLIPBOARD)) {
            return true;
        }

        return false;
    }

    private boolean check(int target, int value) {
        return target != 0 && (value & target) == target;
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

    public abstract Pipe getByValue(String value, String params);

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

    public void onInstalled() {
    }

    public void onDestroy() {
        hasDestroyed = true;
    }

    public void onCreate() {
        hasDestroyed = false;
    }

    @TargetVersion(1144)
    public void onResume(){}

    @TargetVersion(1144)
    public void onPause(){}


    public interface OnItemsLoadedListener {
        void onItemsLoaded(BasePipe pipe, int total);
    }

    public interface OnOutputClickListener {
        void onClick(String value);
    }

    @TargetVersion(1132)
    public interface DisplayOutputCallback extends OutputCallback {
    }

    public interface AdvancedOutputCallback extends OutputCallback {
        void onOutput(String output, OnOutputClickListener listener);
        void display(View view);
    }

    public interface OutputCallback {
        void onOutput(String output);
    }

    public interface SearchResultCallback {
        void onSearchResult(TreeSet<Pipe> results, Instruction input);
    }

    public Pipe getDefaultPipe() {
        return null;
    }

    protected int getAcceptTypeOnConnect() {
        return TYPE_NONE;
    }

    @TargetVersion(1132)
    protected void addNotifyTimeCircle(final long ms) {
        if (hasRunnable) return;

        hasRunnable = true;
        final Pipe dp = getDefaultPipe();
        if (dp != null) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (hasDestroyed) return;

                    getConsole().notify(dp);
                    handler.postDelayed(this, ms);
                }
            }, ms);
        }
    }

    @TargetVersion(1182)
    public int resolveDefaultIcon(Pipe pipe){
        return R.drawable.ic_pipe;
//        return new BitmapDrawable(context.getResources(),
//                BitmapFactory.decodeResource(
//                        context.getResources(), R.drawable.ic_pipe));
    }

    @TargetVersion(1182)
    public void displayIcon(Pipe pipe, ImageView imageView) {
        if (ipManager == null){
            imageView.setImageResource(resolveDefaultIcon(pipe));
        }else {
            boolean b = ipManager.loadIconForPackage(
                    imageView, new ComponentName("id="+getId(), ""));
            if (!b) imageView.setImageResource(resolveDefaultIcon(pipe));
        }
    }

}