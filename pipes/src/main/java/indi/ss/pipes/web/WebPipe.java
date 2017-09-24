package indi.ss.pipes.web;

import com.ss.aris.open.pipes.action.SimpleActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.pri.PRI;

public class WebPipe extends SimpleActionPipe{

    public WebPipe(int id) {
        super(id);
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        if (input.startsWith("http://") || input.startsWith("https://")){
            callback.onOutput(new PRI("admin.ss.web", input).toString());
        }
    }

    @Override
    protected void doExecute(Pipe rs, OutputCallback callback) {

    }

    @Override
    public String getDisplayName() {
        return "web";
    }

}
