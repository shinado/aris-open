package indi.ss.pipes.stocks;

import com.ss.aris.open.pipes.action.SimpleActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.util.HttpUtil;

public class StockPipe extends SimpleActionPipe{

    public StockPipe(int id) {
        super(id);
    }

    @Override
    protected void doExecute(Pipe rs, OutputCallback callback) {

    }

    @Override
    public String getDisplayName() {
        return "stocks";
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        HttpUtil.post("http://hq.sinajs.cn/list=", new HttpUtil.OnSimpleStringResponse() {
            @Override
            public void onResponse(String string) {
                if (string != null){

                }
            }

            @Override
            public void failed() {
            }
        });
    }

}
