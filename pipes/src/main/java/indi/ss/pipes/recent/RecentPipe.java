package indi.ss.pipes.recent;

import com.ss.aris.open.pipes.BasePipe;
import com.ss.aris.open.pipes.action.DefaultInputActionPipe;
import com.ss.aris.open.pipes.entity.Instruction;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.search.SearchablePipe;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecentPipe extends DefaultInputActionPipe{

    private final String USAGE = "To list the most recently installed apps/modified files.\n" +
            "usage:\n" +
            "apps->recent [(optional)size of list]\n" +
            "[directory]->recent [(optional)size of list]";

    public RecentPipe(int id) {
        super(id);
    }

    @Override
    public String getDisplayName() {
        return "recent";
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        Pipe prev = previous.get();
        BasePipe basePrev = prev.getBasePipe();
        if (basePrev == null){
            super.acceptInput(result, input, previous, callback);
            return;
        }

        if (!(basePrev instanceof SearchablePipe)){
            callback.onOutput("not applicable");
            return;
        }

        List<Pipe> all = ((SearchablePipe) basePrev).getAll();

        Collections.sort(all, new Comparator<Pipe>() {
            @Override
            public int compare(Pipe o1, Pipe o2) {
                return (int) (o2.lastModified - o1.lastModified);
            }
        });

        int size = 10;
        Instruction is = result.getInstruction();
        if (!is.isParamsEmpty()){
            try {
                size = Integer.parseInt(is.params[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        int i=0;
        StringBuilder sb = new StringBuilder();
        for (Pipe p: all){
            if (i++ >= size){
                break;
            }

            sb.append(p.getDisplayName())
                    .append("\n");
        }

        callback.onOutput(sb.toString());
    }

    @Override
    public void onParamsEmpty(Pipe rs, OutputCallback callback) {
        callback.onOutput(USAGE);
    }

    @Override
    public void onParamsNotEmpty(Pipe rs, OutputCallback callback) {
        callback.onOutput(USAGE);
    }

}
