package com.example.healkepdoctor.View.login.helper;

import android.os.Handler;

import com.example.healkepdoctor.model.Config;
import com.example.healkepdoctor.model.globalUserInfo;
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

public class loginHelper {
    public static final int LOGIN_SUCCESS = 1;
    public static final int LOGIN_FAILED = 2;
    public static final int CONNECT_FAILED = 3;
    private Handler handler;
    public loginHelper(Handler handler){
        this.handler = handler;
    }

    public void login(String account,String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Map<String,Object> login = new HashMap<>();
                login.put("username",account);
                login.put("password",password);
                login.put("role",2);
                Gson gson = new Gson();
                String json = gson.toJson(login);
                MediaType mediaType = MediaType.parse("application/json; charset=UTF-8");
                RequestBody requestBody = RequestBody.create(json,mediaType);
                Request request = new Request.Builder()
                        .post(requestBody)
                        .url(Config.url+"/login/loginDoctor")
                        .build();
                Call call = client.newCall(request);
                try {
                    Response response = call.execute();
                    if(response.code()==404){
                        handler.sendEmptyMessage(CONNECT_FAILED);
                        return;
                    }
                    JsonObject jsonObject = gson.fromJson(response.body().string(), JsonObject.class);
                    if(jsonObject.has("code")) {
                        if (jsonObject.get("code").getAsLong() == 1) {
                            handler.sendEmptyMessage(LOGIN_SUCCESS);
                            //保存登录信息
                            globalUserInfo info = globalUserInfo.getInstance();
                            info.setAccount(account);
                        } else if (jsonObject.get("code").getAsLong() == 0) {
                            handler.sendEmptyMessage(LOGIN_FAILED);
                        }
                    }
                } catch (IOException e) {
                    handler.sendEmptyMessage(CONNECT_FAILED);
                    e.printStackTrace();
                }

            }
        }).start();
    }

    /**
     * 登出，清理所有保存登录的状态信息
     */
    public static void logout(){}


}
