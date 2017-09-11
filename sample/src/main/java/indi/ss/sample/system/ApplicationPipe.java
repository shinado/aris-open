package indi.ss.sample.system;

import com.ss.aris.open.pipes.action.SimpleActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.impl.ShareIntent;
import com.ss.aris.open.util.JsonUtil;

public class ApplicationPipe extends SimpleActionPipe{

    public ApplicationPipe(int id) {
        super(id);
    }

    @Override
    protected void doExecute(Pipe rs, OutputCallback callback) {
        callback.onOutput("Application " + rs.getDisplayName());
    }

    @Override
    public String getDisplayName() {
        return "";
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        try {
            //if input is an instance of ShareIntent,
            ShareIntent shareIntent = JsonUtil.fromJson(input, ShareIntent.class);
            context.startActivity(shareIntent.toIntent());
        } catch (Exception e) {
            e.printStackTrace();
            callback.onOutput(result.getDisplayName() + " accept: " + input);
        }
    }

}
