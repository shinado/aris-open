package indi.ss.pipes.lock;

import com.ss.aris.open.console.Console;
import com.ss.aris.open.console.functionality.ILock;
import com.ss.aris.open.pipes.action.ExecuteOnlyPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;

public class LockPipe extends ExecuteOnlyPipe{

    public LockPipe(int id) {
        super(id);
    }

    @Override
    public String getDisplayName() {
        return "$LOCK";
    }

    @Override
    public SearchableName getSearchable() {
        return new SearchableName("lock");
    }

    @Override
    public void onParamsEmpty(Pipe rs, OutputCallback callback) {
        Console console = getConsole();
        if (console instanceof ILock){
            ((ILock) console).lock();
        }
    }

}
