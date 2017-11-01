package indi.ss.pipes.http;

import com.ss.aris.open.pipes.action.SimpleActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.util.HttpUtil;

public class HttpPipe extends SimpleActionPipe{

    public HttpPipe(int id) {
        super(id);
    }

    @Override
    protected void doExecute(Pipe rs, OutputCallback callback) {
    }

    @Override
    public String getDisplayName() {
        return "post";
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, final OutputCallback callback) {
        HttpUtil.post(input, new HttpUtil.OnSimpleStringResponse() {
            @Override
            public void onResponse(String string) {
                callback.onOutput(string);
            }

            @Override
            public void failed() {
                callback.onOutput("Failed. ");
            }
        });
    }

}
