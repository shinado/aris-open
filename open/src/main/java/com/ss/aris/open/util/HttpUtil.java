package com.ss.aris.open.util;

import android.os.Handler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class HttpUtil {

    public static void post(String url, String body, boolean raw, final OnSimpleStringResponse callback) {
        RequestBody rBody;
        if (raw) {
            rBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body);
        } else {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            try {
                JSONObject json = new JSONObject(body);
                Iterator<String> it = json.keys();
                while (it.hasNext()) {
                    String key = it.next();
                    String value = json.getString(key);
                    formBodyBuilder.add(key, value);
                }

                rBody = formBodyBuilder.build();
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }

        Request request = new Request.Builder()
                .url(url)
                .post(rBody)
                .build();

        post(request, callback);
    }

    public static void post(String url, OnSimpleStringResponse callback) {
        if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {

            Request request = new Request.Builder()
//                .addHeader("Content-Type", "application/json")
                    .url(url)
                    .build();

            post(request, callback);
        } else {
            callback.failed("Wrong url");
        }
    }

    private static void post(Request request, final OnSimpleStringResponse callback) {
        OkHttpClient client = new OkHttpClient();
        final Handler handler = new Handler();

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
    }

    public interface OnSimpleStringResponse {
        void onResponse(String string);

        void failed(String msg);
    }
}
