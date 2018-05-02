package com.ss.aris.open.pipes.action;

import com.ss.aris.open.pipes.entity.Instruction;
import com.ss.aris.open.pipes.entity.Keys;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;

public abstract class DefaultInputActionPipe extends ActionPipe {

    protected Pipe mResult;

    public DefaultInputActionPipe(int id) {
        super(id);
        mResult = new Pipe(id);
        mResult.setBasePipe(this);
        mResult.setDisplayName(getDisplayName());
        mResult.setSearchableName(getSearchable());
        mResult.setExecutable("$" + mResult.getDisplayName());
    }

    @Override
    public Pipe getResult() {
        return mResult;
    }

    @Override
    public void getOutput(Pipe result, OutputCallback callback) {
        execute(result, callback);
    }

    @Override
    protected void execute(Pipe rs) {
        execute(rs, getConsoleCallback());
    }

    @Override
    public void intercept() {
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        callback.onOutput(getDisplayName());
    }

    private void execute(Pipe rs, OutputCallback callback){
        Instruction value = rs.getInstruction();
        if (value == null || value.isParamsEmpty()){
            onParamsEmpty(rs, callback);
        }else {
            onParamsNotEmpty(rs, callback);
        }
    }

    /**
     * @return the name to be displayed in the screen
     */
    public abstract String getDisplayName();

    /**
     * @return a name for searching
     */
    public SearchableName getSearchable(){
        return new SearchableName(getDisplayName().replaceFirst(Keys.ACTION_REGEX, ""));
    }

    /**
     * when pre and params are both empty
     * e.g.
     * "b"
     */
    public abstract void onParamsEmpty(Pipe rs, OutputCallback callback);

    /**
     * when pre from user input is empty, while params is not
     * e.g.
     * "b c"
     */
    public abstract void onParamsNotEmpty(Pipe rs, OutputCallback callback);

}

