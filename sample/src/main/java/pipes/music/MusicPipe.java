package pipes.music;

import android.content.Intent;
import com.ss.aris.open.pipes.action.ExecuteOnlyPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;

public class MusicPipe extends ExecuteOnlyPipe{

    public MusicPipe(int id) {
        super(id);
    }

    @Override
    public String getDisplayName() {
        return "$music";
    }

    @Override
    public SearchableName getSearchable() {
        return new SearchableName("music");
    }

    @Override
    public void onParamsEmpty(Pipe rs, OutputCallback callback) {
        context.startActivity(new Intent("android.intent.action.MUSIC_PLAYER"));
    }

}
