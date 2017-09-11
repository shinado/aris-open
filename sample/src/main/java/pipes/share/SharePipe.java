package pipes.share;

import android.content.Intent;
import com.ss.aris.open.pipes.PConstants;
import com.ss.aris.open.pipes.action.SimpleActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.impl.ShareIntent;
import com.ss.aris.open.util.JsonUtil;

public class SharePipe extends SimpleActionPipe {

    public SharePipe(int id) {
        super(id);
    }

    @Override
    protected void doExecute(Pipe rs, OutputCallback callback) {

    }

    @Override
    public String getDisplayName() {
        return "share";
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);

        ShareIntent shareIntent = null;
        try {
            shareIntent = JsonUtil.fromJson(input, ShareIntent.class);
            //still fail
            if (Intent.ACTION_VIEW.equals(shareIntent.action)) {
                shareIntent = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (shareIntent != null) {
            sharingIntent = shareIntent.toIntent();
        } else {
            sharingIntent.setType("text/plain");
            Pipe prev = previous.get();
            if (prev.getId() == PConstants.ID_APPLICATION) {
                input = "https://play.google.com/store/apps/details?id="
                        + input.split(",")[0];
            }
            sharingIntent.putExtra(Intent.EXTRA_TEXT, input);
        }

        getContext().startActivity(sharingIntent);
    }
}
