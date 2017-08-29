package indi.ss.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import indi.shinado.piping.launcher.Console;
import indi.shinado.piping.pipes.BasePipe;
import indi.shinado.piping.pipes.entity.Pipe;
import indi.shinado.piping.pipes.entity.SearchableName;
import indi.shinado.piping.pipes.search.translator.AbsTranslator;
import indi.ss.sample.system.ApplicationPipe;

public class MainActivity extends AppCompatActivity {

    private Console console;
    private BasePipe application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        console = new LogConsole();
        loadSystemPipes();

        //------------- your pipe here ---------------//
        BasePipe pipe = new YourPipe(1001);
        Pipe defaultPipe = pipe.getDefaultPipe();
        register(pipe);

        //to start with no input
        pipe.startExecution(defaultPipe);

        Pipe wechat = getWechat();

        //to accept WeChat as input
        //code: wechat.yourpipe
        pipe.acceptInput(defaultPipe, "hello",
                new Pipe.PreviousPipes(wechat),
                console.getOutputCallback());

        //to output to wechat
        //code: yourpipe.wechat
        wechat.getBasePipe().acceptInput(wechat, defaultPipe.getExecutable(),
                new Pipe.PreviousPipes(defaultPipe), console.getOutputCallback());
    }

    private void loadSystemPipes(){
        application = new ApplicationPipe(1);
        register(application);
    }

    private Pipe getWechat(){
        Pipe wechat = new Pipe(application.getId(),
                "wechat", "com.tencent.mm,com.tencent.mm.ui.LauncherUI");
        wechat.setBasePipe(application);
        return wechat;
    }

    public void register(final BasePipe basePipe) {
        if (basePipe == null) {
            return;
        }
        basePipe.setConsole(console);
        basePipe.setContext(this);

        basePipe.load(new AbsTranslator(this){

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