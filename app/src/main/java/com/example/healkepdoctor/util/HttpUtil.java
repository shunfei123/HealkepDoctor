package com.example.healkepdoctor.util;

import com.example.healkepdoctor.model.Config;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {

    public static Response post(String url, RequestBody requestBody) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .post(requestBody)
                .url(Config.url+url)
                .build();
        Call call = client.newCall(request);
        return call.execute();
    }

    public static Response get(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = client.newCall(request);
        return call.execute();
    }
}
