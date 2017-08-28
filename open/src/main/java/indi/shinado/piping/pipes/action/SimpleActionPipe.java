package indi.shinado.piping.pipes.action;


import indi.shinado.piping.pipes.entity.Pipe;

public abstract class SimpleActionPipe extends DefaultInputActionPipe{

    public SimpleActionPipe(int id) {
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

    protected abstract void doExecute(Pipe rs, OutputCallback callback);
}
