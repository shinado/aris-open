package indi.ss.sample.system;

import indi.shinado.piping.pipes.action.SimpleActionPipe;
import indi.shinado.piping.pipes.entity.Pipe;
import indi.shinado.piping.pipes.impl.ShareIntent;
import indi.shinado.piping.util.JsonUtil;

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
