package com.ss.aris.open.util;

import android.os.Handler;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

public class HttpUtil {

    public static void post(String url, final OnSimpleStringResponse callback) {
        if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
            final Handler handler = new Handler();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
//                .addHeader("Content-Type", "application/json")
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    e.printStackTrace();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.failed(e.getMessage());
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final okhttp3.Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.failed(response.message());
                            }
                        });
                    }

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
                                callback.failed("Body is null");
                            }
                        });
                    }
                }
            });
        } else {
            callback.failed("Wrong url");
        }
    }

    public interface OnSimpleStringResponse {
        void onResponse(String string);

        void failed(String msg);
    }
}
