package indi.ss.pipes.tts;

import android.speech.tts.TextToSpeech;
import com.ss.aris.open.pipes.action.SimpleActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import java.util.Locale;

public class TtsPipe extends SimpleActionPipe {

    private TextToSpeech ttobj;

    public TtsPipe(int id) {
        super(id);
    }

    @Override
    public void acceptInput(Pipe result, final String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        super.acceptInput(result, input, previous, callback);

        ttobj = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    ttobj.setLanguage(Locale.KOREAN);
                    ttobj.speak(input, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }

    @Override
    protected void doExecute(Pipe rs, OutputCallback callback) {

    }

    @Override
    public String getDisplayName() {
        return null;
    }

}