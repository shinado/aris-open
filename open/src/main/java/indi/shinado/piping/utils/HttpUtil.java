package indi.shinado.piping.utils;

import android.os.Handler;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

public class HttpUtil {

    public static void post(String url, final OnSimpleStringResponse callback) {
        final Handler handler = new Handler();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.failed();
                    }
                });
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                ResponseBody body = response.body();
                if (body != null) {
                    final String string = body.string();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                                callback.onResponse(string);
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.failed();
                        }
                    });
                }
            }
        });
    }

    public interface OnSimpleStringResponse {
        void onResponse(String string);

        void failed();
    }
}
