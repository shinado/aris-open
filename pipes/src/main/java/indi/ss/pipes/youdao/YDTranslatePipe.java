package indi.ss.pipes.youdao;

import android.media.MediaPlayer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import com.ss.aris.open.TargetVersion;
import com.ss.aris.open.pipes.action.AcceptablePipe;
import com.ss.aris.open.pipes.entity.Instruction;
import com.ss.aris.open.pipes.entity.Keys;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;
import com.ss.aris.open.pipes.impl.interfaces.Helpable;
import com.ss.aris.open.util.HttpUtil;

@TargetVersion(1170)
public class YDTranslatePipe extends AcceptablePipe implements Helpable {

    private static final String NAME = "$translating";

    //52f61e48a7384721
    private static final String KEY = "52f61e48a7384721";
    private static final String SECRET = "7QjyoXKoFbFPKRF0hBTD2lcAsUqvg7Jj";

    //&
    private static final String URL = "https://openapi.youdao.com/api?appKey=" +
            KEY +
            "&q=%s&from=auto&to=%s&salt=%s&sign=%s";

    private static final String HELP = "Usage of " + NAME + ":\n" +
            "[your words]" + Keys.PIPE + "translating";

    public YDTranslatePipe(int id) {
        super(id);
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
//        callback.onOutput("Translating...");
        try {
            String to = null;
            boolean needSpeak = false;
            Instruction instruction = result.getInstruction();
            if (!instruction.isParamsEmpty()) {
                to = instruction.params[0];
                needSpeak = instruction.params.length >= 2 &&
                        "-s".equals(instruction.params[1]);
            }
            requestTranslation(input, to, needSpeak, callback);
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

    private void requestTranslation(String q, String to, final boolean needSpeak, final OutputCallback callback) throws UnsupportedEncodingException {
        int salt = new Random().nextInt();
        String key = KEY + q + salt + SECRET;
        String sign = md5(key);

        String url = String.format(URL, URLEncoder.encode(q, "utf-8"),
                to == null ? "auto" : to, "" + salt, sign);

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

                    if (needSpeak) {
                        String speakUrl = json.getString("tSpeakUrl");
                        MediaPlayer player = new MediaPlayer();
                        player.setDataSource(speakUrl);
                        player.prepareAsync();
                        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.start();
                            }
                        });
                        getConsole().input("Playing audio...");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    sb.append(e.getMessage());
                }

                callback.onOutput(sb.toString());
            }

            @Override
            public void failed(String msg) {
                callback.onOutput(msg);
            }
        });
    }

    private static String md5(String string) {
        if (string == null) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};

        try {
            byte[] btInput = string.getBytes("utf-8");
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            return null;
        }
    }

    @Override
    public String getHelp() {
        return HELP;
    }

}
