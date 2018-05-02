package indi.ss.pipes.most;

import com.ss.aris.open.pipes.BasePipe;
import com.ss.aris.open.pipes.action.DefaultInputActionPipe;
import com.ss.aris.open.pipes.entity.Instruction;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.search.SearchablePipe;
import java.util.Set;

public class MostPipe extends DefaultInputActionPipe{

    private final String USAGE = "To list the most frequently used apps/contacts." +
            "\nusage:" +
            "\n[apps/contacts]->most [(optional)size of list] ";

    public MostPipe(int id) {
        super(id);
    }

    @Override
    public String getDisplayName() {
        return "most";
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

        Set<Pipe> all = ((SearchablePipe) basePrev).getFrequents();

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
