package com.ss.aris.open.pipes.search;

import android.annotation.TargetApi;
import android.util.Log;
import java.util.TreeSet;
import com.ss.aris.open.pipes.BasePipe;
import com.ss.aris.open.pipes.entity.Instruction;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;
import com.ss.aris.open.pipes.search.translator.AbsTranslator;

//start
//-> dosth
//start dosth

public abstract class FullSearchActionPipe extends SearchablePipe {

    private String TAG = "FullSearchActionPipe ";
    protected Pipe defaultExitPipe;
    protected boolean hasStarted = false;
    protected boolean startedAsSelected = false;
    private boolean onSecondStart = false;

    public FullSearchActionPipe(int id) {
        super(id);
        defaultExitPipe = new Pipe(getId(), "$exit", new SearchableName("exit"), "$#exit");
    }

    @Override
    public Pipe getByValue(String value, String params) {
        return null;
    }

    protected void end() {
        onSecondStart = false;
        hasStarted = false;
        removeItemInMap(defaultExitPipe);
        pipeManager.reenableSearchAll();

        endAsSelected();
        getConsole().setIndicator("");
    }

    @TargetApi(1050)
    protected void endAsSelected() {
        startedAsSelected = false;
    }

    private void start() {
        hasStarted = true;
        putItemInMap(defaultExitPipe);
        pipeManager.searchAction(this);
    }

    protected void justStart() {
        start();
    }

    @TargetApi(8)
    protected void startAsSelected() {
        startedAsSelected = true;
        start();
        Log.d("FullSearch", "startAsSelected: ");
    }

    @Override
    protected TreeSet<Pipe> search(String input) {
        Log.d("FullSearch", "search: " + startedAsSelected);
        if (startedAsSelected) {
            //cd folder
            Instruction instruction = new Instruction(input);
            Instruction newInstruction = instruction.params.length > 0 ?
                    new Instruction(instruction.params[0]) :
                    new Instruction("");
            return search(newInstruction);
        } else {
            if (hasStarted) {
                return super.search(input);
            } else {
                TreeSet<Pipe> results = new TreeSet<>();
                if (!input.isEmpty()) {
                    //create a new pipe
                    Pipe defaultPipe = getDefaultPipe();
                    if (defaultPipe != null) {
                        Pipe result = new Pipe(defaultPipe);
                        fulfill(result, new Instruction(input));
                        if (result.getSearchableName().contains(result.getInstruction().body)) {
                            results.add(result);
                        }
                    }
                } else {
                    if (configurations != null && configurations.needHistory() && asOutput()) {
                        Pipe defaultPipe = getDefaultPipe();
                        if (defaultPipe != null) {
                            results.add(new Pipe(defaultPipe));
                        }
                    }
                }
                return results;
            }
        }
    }

    @Override
    public void onSelectedAsStart(Pipe result) {
        super.onSelectedAsStart(result);
        Log.d(TAG, "onSelectedAsStart: " + hasStarted + ", " +
                (result.equals(getDefaultPipe())));

//        if (result.equals(getDefaultPipe())) {
            if (hasStarted) {
                Log.d(TAG, "second start");
                onSecondStart = true;
            } else {
                getConsole().setIndicator(result.getDisplayName());
                startAsSelected();
            }
//        }
    }

    @Override
    public void onUnselectedAsStart(Pipe result) {
        super.onUnselectedAsStart(result);
        Log.d(TAG, "when unselected: " + onSecondStart);

        if (!onSecondStart) {
            getConsole().setIndicator("");
            end();
        }
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, BasePipe.OutputCallback callback) {
        //accept anyway
        //so script executor shall work
//        if (hasStarted) {
        doAcceptInput(result, input, previous, callback);
//        }
    }

    @Override
    public void getOutput(Pipe result, BasePipe.OutputCallback callback) {
        //TODO for this case: cd folder?
        if (result.equals(getDefaultPipe())) {
            callback.onOutput("starting " + result.getDisplayName());
        } else {
            doExecute(result, callback);
            if (oneTime()) end();
        }
    }

    protected boolean oneTime() {
        return true;
    }

    @Override
    protected void execute(Pipe rs) {
        if (rs.equals(getDefaultPipe())) {
            justStart();
        } else if (rs.equals(defaultExitPipe)) {
            end();
        } else {
            doExecute(rs, getConsoleCallback());
            if (startedAsSelected) {
                endAsSelected();
            }
        }
    }

    @Override
    public void load(AbsTranslator translator, BasePipe.OnItemsLoadedListener listener, int total) {
        listener.onItemsLoaded(this, total);
    }

    protected abstract void doAcceptInput(Pipe result, String input, Pipe.PreviousPipes previous, BasePipe.OutputCallback callback);

    protected abstract void doExecute(Pipe rs, BasePipe.OutputCallback callback);

    public abstract Pipe getDefaultPipe();

    @SuppressWarnings("WeakerAccess")
    protected boolean asOutput() {
        return true;
    }
}
