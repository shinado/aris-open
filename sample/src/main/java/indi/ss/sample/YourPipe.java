package indi.ss.sample;

import indi.shinado.piping.pipes.action.SimpleActionPipe;
import indi.shinado.piping.pipes.entity.Pipe;

/**
 * write your pipe here
 */
public class YourPipe extends SimpleActionPipe{

    public YourPipe(int id) {
        super(id);
    }

    @Override
    public String getDisplayName() {
        return "name";
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        callback.onOutput(result.getDisplayName() + " accept: "+input);
    }

    @Override
    protected void doExecute(Pipe rs, OutputCallback callback) {
        callback.onOutput("do execute " + rs.getDisplayName());
    }

}
