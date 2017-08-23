package indi.ss.sample;

import indi.shinado.piping.pipes.action.DefaultInputActionPipe;
import indi.shinado.piping.pipes.entity.Pipe;
import indi.shinado.piping.pipes.entity.SearchableName;

public class YourPipe extends DefaultInputActionPipe{

    public YourPipe(int id) {
        super(id);
    }

    @Override
    public String getDisplayName() {
        return "name";
    }

    @Override
    public SearchableName getSearchable() {
        return new SearchableName("na", "me");
    }

    @Override
    public void onParamsEmpty(Pipe rs, OutputCallback callback) {

    }

    @Override
    public void onParamsNotEmpty(Pipe rs, OutputCallback callback) {

    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {

    }

}
