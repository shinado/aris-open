package indi.ss.pipes.shell;

import com.ss.aris.open.pipes.entity.Instruction;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;
import com.ss.aris.open.pipes.search.FullSearchActionPipe;
import java.util.TreeSet;

public class ShellPipe extends FullSearchActionPipe {

    private Pipe starter;

    public ShellPipe(int id) {
        super(id);

        starter = new Pipe(id, "shell", new SearchableName("shell"), "_$shell");
        starter.setBasePipe(this);
    }

    @Override
    protected void doExecute(Pipe rs, OutputCallback callback) {
        CommandResult result = Shell.run(rs.getExecutable());
        if (result.isSuccessful()) {
            callback.onOutput(result.getStdout());
        } else {
            callback.onOutput(result.getStderr());
        }
    }

    @Override
    public Pipe getDefaultPipe() {
        return starter;
    }

    @Override
    protected void doAcceptInput(Pipe rs, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
    }

    @Override
    protected void start(Pipe p) {
        super.start(p);
        getConsole().input("You're in shell now. Please use 'exit' to exit");
    }

    @Override
    protected TreeSet<Pipe> search(Instruction value) {
        TreeSet<Pipe> set = super.search(value);
        if (hasStarted) {
            Pipe result = get();
            String body = value.fullBody();
            result.setExecutable(body);
            result.setDisplayName(body);
            set.add(result);
        }

        return set;
    }

    private Pipe get() {
        Pipe p = new Pipe(id);
        p.setBasePipe(this);
        p.setDisplayName("");
        SearchableName searchableName = new SearchableName();
        searchableName.setSyntac("\"");
        p.setSearchableName(searchableName);
        return p;
    }
}