package com.example.healkepdoctor.View.register.helper;

import android.os.Handler;

import com.example.healkepdoctor.model.Config;
import com.example.healkepdoctor.model.doctorInfo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class registerHelper {
    public static final int REGISTER_SUCCESS = 0;
    public static final int REGISTER_FAILED = 1;
    public static final int USERNAME_CONFLICT = 2;
    public static final int CONNECT_FAILED = 3;
    private Handler handler;
    private String account;
    private String password;
    private doctorInfo info;
    public registerHelper(Handler handler){
        this.handler = handler;
    }
    public void register(String account, String password, doctorInfo info) {
        this.account = account;
        this.password = password;
        this.info = info;
        Runnable thread = new registerThread();
        new Thread(thread).start();
    }

     class registerThread implements Runnable{
        @Override
        public void run(){
            OkHttpClient httpClient = new OkHttpClient.Builder().build();
            Map<String,Object> login = new HashMap<>();
            login.put("username", account);
            login.put("password", password);
            login.put("role", 2);
            Gson gson = new Gson();
            String json = gson.toJson(login);
            String infoJson = gson.toJson(info);
            JsonObject jsonObject1 = gson.fromJson(json, JsonObject.class);
            JsonObject jsonObject2 = gson.fromJson(infoJson, JsonObject.class);
            jsonObject2.remove("username");
            for(String i : jsonObject1.keySet()){
                jsonObject2.add(i, jsonObject1.get(i));
            }
            MediaType mediaType = MediaType.parse("application/json; charset=UTF-8");
            RequestBody requestBody = RequestBody.create(jsonObject2.toString(),mediaType);
            Request request = new Request.Builder()
                    .post(requestBody)
                    .url(Config.url+"/login/register/doctor")
                    .build();
            Call call = httpClient.newCall(request);
            try {
                Response response = call.execute();
                if(response.code()==404){
                    handler.sendEmptyMessage(CONNECT_FAILED);
                    return;
                }
                JsonObject jsonObject = gson.fromJson(response.body().string(), JsonObject.class);
                if(jsonObject.has("code")){
                    if(jsonObject.get("code").getAsLong() >= 1){
                        handler.sendEmptyMessage(REGISTER_SUCCESS);
                    }else if(jsonObject.get("code").getAsLong() == -1){
                        handler.sendEmptyMessage(USERNAME_CONFLICT);
                    }else{
                        handler.sendEmptyMessage(REGISTER_FAILED);
                    }
                }
            }catch (IOException e) {
                handler.sendEmptyMessage(CONNECT_FAILED);
                e.printStackTrace();
            }
        }
    }
}
