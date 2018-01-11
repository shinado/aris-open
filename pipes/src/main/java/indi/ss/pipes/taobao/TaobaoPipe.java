package indi.ss.pipes.taobao;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.ss.aris.open.pipes.action.SimpleActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.util.HttpUtil;
import com.ss.aris.open.util.JsonUtil;

import java.util.List;

public class TaobaoPipe extends SimpleActionPipe {

    private String URL = "https://s.m.taobao.com/search?q=$s" +
            "&tab=1&sst=1&n=20&buying=buyitnow&m=api4h5&abtest=6&wlsort=6&sort=%s&page=1";

    public TaobaoPipe(int id) {
        super(id);
    }

    @Override
    protected void doExecute(Pipe rs, OutputCallback callback) {
    }

    @Override
    public void acceptInput(Pipe result, String input,
                            Pipe.PreviousPipes previous, final OutputCallback callback) {
        String url = URL.replace("$s", input);
        HttpUtil.post(url, new HttpUtil.OnSimpleStringResponse() {
            @Override
            public void onResponse(String string) {
                TaobaoResponse response = null;
                try {
                    response = JsonUtil.fromJson(string, TaobaoResponse.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (response != null) onDataLoaded(response.listItem, callback);
            }

            @Override
            public void failed(String msg) {
            }
        });
    }

    private void onDataLoaded(List<TaobaoItem> list, OutputCallback callback) {
        if (callback instanceof AdvancedOutputCallback) {
            RecyclerView recyclerView = new RecyclerView(context, null);
            recyclerView.setLayoutManager(new LinearLayoutManager(
                    context, LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(new TaobaoAdapter(context, list));

            ((AdvancedOutputCallback) callback).display(recyclerView);
        }
    }

    @Override
    public String getDisplayName() {
        return "Taobao";
    }

}