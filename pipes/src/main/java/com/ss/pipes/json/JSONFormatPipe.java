package com.ss.pipes.json;

import com.ss.aris.open.pipes.action.DefaultInputActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;

public class JSONFormatPipe extends DefaultInputActionPipe{

    public JSONFormatPipe(int id) {
        super(id);
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public SearchableName getSearchable() {
        return null;
    }

    @Override
    public void onParamsEmpty(Pipe rs, OutputCallback callback) {

    }

    @Override
    public void onParamsNotEmpty(Pipe rs, OutputCallback callback) {

    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {

    }
}
