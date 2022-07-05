package com.example.healkepdoctor.model;

import android.content.Context;

import com.example.healkepdoctor.util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Handler;

import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class globalUserInfo {
    //全局单例，用于存储登录状态相关信息
    private String account;
    private String sessionId;
    private doctorInfo info;
    private static globalUserInfo instance;
    private globalUserInfo(){

    }
    public static globalUserInfo getInstance(){
        if(instance == null){
            instance = new globalUserInfo();
        }
        return instance;
    }
    public String getAccount() {
        return account;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public synchronized doctorInfo getInfo() {
        if(info == null){
            new Thread(()->{
                try {//Response{protocol=http/1.1, code=405, message=, url=http://192.168.31.6:6666/doctorInfo/get?username=test}
                    RequestBody requestBody = new FormBody.Builder()
                            .add("username", account).build();
                    Response response =  HttpUtil.post("/doctorInfo/get",requestBody);
                    String json = response.body().string();
                    Gson gson = new Gson();
                    info = gson.fromJson(json, doctorInfo.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        return info;
    }

    /**
     *
     * @param info
     * @return 1 修改成功 0 修改失败  -1 网络异常
     */
    public synchronized int setInfo(doctorInfo info) {
        Semaphore sem = new Semaphore(0);
        AtomicInteger code = new AtomicInteger(-1);
        new Thread(()->{
            Gson gson = new Gson();
            String json = gson.toJson(info);
            MediaType mediaType = MediaType.parse("application/json; charset=UTF-8");
            RequestBody requestBody = RequestBody.create(json,mediaType);
            try {
                Response response = HttpUtil.post("/doctorInfo/update", requestBody);
                if(response.code()!=404){
                    JsonObject jsonObject = gson.fromJson(response.body().string(), JsonObject.class);
                    if(jsonObject.has("code")){
                        code.set((int)jsonObject.get("code").getAsLong());
                    }else{
                        code.set(-1);
                    }
                }else{
                    code.set(-1);
                }
            } catch (IOException e) {
                code.set(-1);
                e.printStackTrace();
            }finally {
                sem.release(1);
            }
        }).start();
        try {
            sem.acquire();
            if(code.get()==1) this.info = info;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return code.get();
    }
}
