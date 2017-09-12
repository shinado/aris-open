package com.ss.pipes.youdao;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import com.ss.aris.open.pipes.action.DefaultInputActionPipe;
import com.ss.aris.open.pipes.entity.Keys;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;
import com.ss.aris.open.pipes.impl.interfaces.Helpable;
import com.ss.aris.open.util.HttpUtil;

public class YDTranslatePipe extends DefaultInputActionPipe implements Helpable{

    private static final String NAME = "$translating";

    private static final String KEY = "52f61e48a7384721";
    private static final String SECRET = "7QjyoXKoFbFPKRF0hBTD2lcAsUqvg7Jj";

    //&
    private static final String URL = "https://openapi.youdao.com/api?appKey=" +
            KEY +
            "&q=%s&from=auto&to=auto&salt=%s&sign=%s";

    private static final String HELP = "Usage of " + NAME + ":\n" +
            "[your words]" + Keys.PIPE + "translating";

    public YDTranslatePipe(int id) {
        super(id);
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
//        callback.onOutput("Translating...");
        try {
            requestTranslation(input, callback);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDisplayName() {
        return NAME;
    }

    @Override
    public SearchableName getSearchable() {
        return new SearchableName("translating");
    }

    @Override
    public void onParamsNotEmpty(Pipe rs, OutputCallback callback) {
        callback.onOutput("You got to translate something, dude\n" + HELP);
    }

    @Override
    public void onParamsEmpty(Pipe rs, OutputCallback callback) {
        callback.onOutput("You got to translate something, dude\n" + HELP);
    }

    private void requestTranslation(String q, final OutputCallback callback) throws UnsupportedEncodingException {
        int salt = new Random().nextInt();
        String key = KEY + q + salt + SECRET;
        String sign = getMD5(key);

        String url = String.format(URL, URLEncoder.encode(q, "utf-8"), "" + salt, sign);

        HttpUtil.post(url, new HttpUtil.OnSimpleStringResponse() {
            @Override
            public void onResponse(String string) {
                StringBuilder sb = new StringBuilder();
                try {
                    JSONObject json = new JSONObject(string);
                    JSONArray array = json.getJSONArray("translation");
                    for (int i = 0; i < array.length(); i++) {
                        sb.append(array.getString(i))
                                .append("\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    sb.append(e.getMessage());
                }

                callback.onOutput(sb.toString());
            }

            @Override
            public void failed() {
                callback.onOutput("error");
            }
        });
    }

    private static String getMD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuilder buf = new StringBuilder();
            for (byte aB : b) {
                i = aB;
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String getHelp() {
        return HELP;
    }
}
