package com.ss.aris.open.pipes.action;

import com.ss.aris.open.pipes.entity.Instruction;
import com.ss.aris.open.pipes.entity.Pipe;

public abstract class GhostActionPipe extends DefaultInputActionPipe {

    public GhostActionPipe(int id) {
        super(id);
    }

    @Override
    public void onParamsEmpty(Pipe rs, OutputCallback callback) {
        doExecute(rs, callback);
    }

    @Override
    public void onParamsNotEmpty(Pipe rs, OutputCallback callback) {
        doExecute(rs, callback);
    }

    @Override
    public Pipe search(Instruction input) {
        return null;
    }

    protected abstract void doExecute(Pipe rs, OutputCallback callback);
}
