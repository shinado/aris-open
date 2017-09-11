package indi.ss.sample.action;

import com.ss.aris.open.pipes.action.SimpleActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;

/**
 * write your pipe here
 */
public class YourPipe extends SimpleActionPipe {

    public YourPipe(int id) {
        super(id);
    }

    @Override
    public String getDisplayName() {
        return "your pipe";
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        callback.onOutput(result.getDisplayName() + " accept: " + input + " from " + previous.get().getDisplayName());
    }

    @Override
    protected void doExecute(Pipe rs, OutputCallback callback) {
        callback.onOutput("executing " + rs.getDisplayName());
    }

}
