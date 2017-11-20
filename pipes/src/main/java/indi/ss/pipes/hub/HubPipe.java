package indi.ss.pipes.hub;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.ss.aris.open.console.Console;
import com.ss.aris.open.pipes.action.SimpleActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.widget.IWidget;

public class HubPipe extends SimpleActionPipe implements IWidget{

    public HubPipe(int id) {
        super(id);
    }

    @Override
    protected void doExecute(Pipe rs, OutputCallback callback) {

    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {

    }

    @Override
    public void onCreate(Context context, Console console) {

    }

    @Override
    public View getView(ViewGroup parent, String value) {
        return null;
    }

}
