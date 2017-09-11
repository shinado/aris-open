package com.ss.aris.open.pipes.action;

import com.ss.aris.open.pipes.entity.Instruction;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;

public abstract class ExecuteOnlyPipe extends ActionPipe {

    protected Pipe mResult;

    public ExecuteOnlyPipe(int id) {
        super(id);
        mResult = new Pipe(id);
        mResult.setBasePipe(this);
        mResult.setDisplayName(getDisplayName());
        mResult.setSearchableName(getSearchable());
        mResult.setExecutable("action://" + id);
    }

    @Override
    public Pipe getResult() {
        return mResult;
    }

    @Override
    public void getOutput(Pipe result, OutputCallback callback) {
        getConsole().input("Output not supported in " + getDisplayName() );
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        getConsole().input(getDisplayName() + " does not accept input. ");
    }

    @Override
    protected void execute(Pipe rs) {
        execute(rs, getConsoleCallback());
    }

    @Override
    public void intercept() {
    }

    private void execute(Pipe rs, OutputCallback callback){
        Instruction value = rs.getInstruction();
        if (value == null || value.isParamsEmpty()){
            onParamsEmpty(rs, callback);
        }else {
            onParamsNotEmpty();
        }
    }

    /**
     * @return the email to be displayed in the screen
     * better to justStart with $, I mean, it looks cooler
     */
    public abstract String getDisplayName();

    /**
     * @return a email for searching
     */
    public abstract SearchableName getSearchable();

    /**
     * when pre and params are both empty
     * e.g.
     * "b"
     */
    public abstract void onParamsEmpty(Pipe rs, OutputCallback callback);

    /**
     * when pre from user input is empty, while params is not
     * e.g.
     * "b -c"
     */
    public void onParamsNotEmpty(){
        getConsole().input(getDisplayName() + " does not accept parameters. ");
    }


}

