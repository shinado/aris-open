package indi.ss.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import indi.shinado.piping.pipes.BasePipe;
import indi.shinado.piping.pipes.entity.SearchableName;
import indi.shinado.piping.pipes.search.translator.AbsTranslator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BasePipe pipe = new YourPipe(1001);
        register(pipe);

        pipe.startExecution(pipe.getDefaultPipe());
    }

    public void register(final BasePipe basePipe) {
        if (basePipe == null) {
            return;
        }
        basePipe.setConsole(new LogConsole());
        basePipe.setContext(this);

        basePipe.load(
                new AbsTranslator(this){

            @Override
            public SearchableName getName(String name) {
                return new SearchableName(name);
            }

            @Override
            public void destroy() {

            }

            @Override
            public boolean ready() {
                return true;
            }
        }, new BasePipe.OnItemsLoadedListener() {
            @Override
            public void onItemsLoaded(int id, int total) {

            }
        }, 1);
    }
}