package com.ss.aris.open.pipes.search;

import com.ss.aris.open.pipes.PConstants;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.search.translator.AbsTranslator;

public class InstantResultPipe extends SearchablePipe{

    public InstantResultPipe(int id) {
        super(id);
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {

    }

    @Override
    public void getOutput(Pipe result, OutputCallback callback) {

    }

    @Override
    protected void execute(Pipe rs) {

    }

    @Override
    public void load(AbsTranslator translator, OnItemsLoadedListener listener, int total) {
        listener.onItemsLoaded(this, total);
    }

    @Override
    public Pipe getByValue(String value, String params) {
        return null;
    }

    @Override
    public void onConnected(Pipe.PreviousPipes previous) {
        //display all maybe
        Pipe prev = previous.get();
        if (prev.getId() == PConstants.ID_APPLICATION){

        }
    }

}
