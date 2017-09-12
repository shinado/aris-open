package com.ss.pipes.time;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.ss.aris.open.pipes.action.DefaultInputActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;
import com.ss.aris.open.pipes.impl.interfaces.Helpable;

public class TimePipe extends DefaultInputActionPipe implements Helpable{

    public TimePipe(int id) {
        super(id);
    }

    @Override
    public String getDisplayName() {
        return "$time";
    }

    @Override
    public SearchableName getSearchable() {
        return new SearchableName("time");
    }

    @Override
    public void onParamsEmpty(Pipe rs, OutputCallback callback) {
        callback.onOutput(
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",
                Locale.getDefault()).format(new Date()));
    }

    @Override
    public void onParamsNotEmpty(Pipe rs, OutputCallback callback) {

    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(input, Locale.getDefault());
            callback.onOutput(sdf.format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
            callback.onOutput(e.getMessage());
        }
    }

    @Override
    public String getHelp() {
        return "To format current time, please use Java SimpleDateFormat as input.\n" +
                "e.g. yyyy-MM-dd hh:mm:ss.time";
    }

}
