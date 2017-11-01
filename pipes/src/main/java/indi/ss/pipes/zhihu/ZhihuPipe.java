package indi.ss.pipes.zhihu;

import android.content.Intent;

import com.ss.aris.open.pipes.action.DefaultInputActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.impl.ShareIntent;
import com.ss.aris.open.pipes.pri.PRI;
import com.ss.aris.open.util.HttpUtil;
import com.ss.aris.open.util.JsonUtil;
import com.ss.aris.open.widget.WidgetHeads;

public class ZhihuPipe extends DefaultInputActionPipe {

    private static final String BASE_URL = "https://news-at.zhihu.com/api/4/";

    public ZhihuPipe(int id) {
        super(id);
    }

    @Override
    public String getDisplayName() {
        return "zhihu";
    }

    @Override
    public void onParamsEmpty(Pipe rs, final OutputCallback callback) {
        if (rs.equals(getDefaultPipe())) {
            HttpUtil.post(BASE_URL + "theme/11", new HttpUtil.OnSimpleStringResponse() {
                @Override
                public void onResponse(String string) {
                    ZhihuList list = JsonUtil.fromJson(string, ZhihuList.class);

                    for (final Zhihu zhihu : list.stories) {
                        callback.onOutput(
                                new PRI(WidgetHeads.HEAD_FEED,
                                        JsonUtil.toJson(zhihu.toFeed(getId())))
                                        .toString());
                    }
                }

                @Override
                public void failed() {
                }
            });
        } else {
            ShareIntent shareIntent = JsonUtil.fromJson(
                    rs.getExecutable(), ShareIntent.class);
            if (shareIntent != null) {
                if (callback == getConsoleCallback()) {
                    context.startActivity(shareIntent.toIntent());
                    callback.onOutput(shareIntent.toString());
                } else {
                    shareIntent.action = Intent.ACTION_SEND;
                    shareIntent.data = null;
                    callback.onOutput(shareIntent.toString());
                }
            }else {
                callback.onOutput(rs.getExecutable());
            }
        }
    }

    @Override
    public void onParamsNotEmpty(Pipe rs, OutputCallback callback) {

    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
    }

    @Override
    public Pipe getByValue(String value) {
        ShareIntent shareIntent = JsonUtil.fromJson(value, ShareIntent.class);
        if (shareIntent == null) {
            return super.getByValue(value);
        } else {
            Pipe result = new Pipe(getResult());
            result.setDisplayName("zhihu feed");
            result.setExecutable(value);
            return result;
        }
    }

}
