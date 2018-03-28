package com.ss.aris.open.pipes;

import com.ss.aris.open.pipes.action.DefaultInputActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;

public class NullPipe extends DefaultInputActionPipe {

    public NullPipe(int id) {
        super(id);
    }

    @Override
    public String getDisplayName() {
        return "NULL";
    }

    @Override
    public SearchableName getSearchable() {
        return new SearchableName("NULL");
    }

    @Override
    public void onParamsEmpty(Pipe rs, OutputCallback callback) {
        callback.onOutput("NULL");
    }

    @Override
    public void onParamsNotEmpty(Pipe rs, OutputCallback callback) {
        callback.onOutput("NULL");
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        callback.onOutput(input + " -> NULL");
    }
}
