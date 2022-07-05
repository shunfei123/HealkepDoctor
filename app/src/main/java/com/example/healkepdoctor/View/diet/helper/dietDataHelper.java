package com.example.healkepdoctor.View.diet.helper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.healkepdoctor.R;
import com.example.healkepdoctor.model.Config;
import com.example.healkepdoctor.model.dietPlan;
import com.example.healkepdoctor.model.globalUserInfo;
import com.example.healkepdoctor.model.patientInfo;
import com.example.healkepdoctor.util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;


public class dietDataHelper {
    public static int SUCCESS = 1;
    public static int FAILED = 2;
    public static int CONNECT_FAILED = 3;
    private Handler handler;
    public dietDataHelper(Handler handler){
        this.handler = handler;
    }
    public void getData(patientInfo info){
        new Thread(()->{
            //todo
            Gson gson = new Gson();
            Map<String,String> map = new HashMap<>();
            map.put("targetUsername",info.getUsername());
            MediaType mediaType = MediaType.parse("application/json; charset=UTF-8");
            RequestBody requestBody = RequestBody.create(gson.toJson(map),mediaType);
            try {
                Response response = HttpUtil.post("/diet/getByPatient",requestBody);
                if(response.code()!=200){
                    handler.sendEmptyMessage(CONNECT_FAILED);
                }else{
                    String json = response.body().string();
                    List<dietPlan> plans = new ArrayList<>();
                    JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();
                    for(JsonElement i : jsonArray){
                        plans.add(gson.fromJson(i, dietPlan.class));
                    }
                    Message msg = new Message();
                    msg.what = SUCCESS;
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArray("plans", plans.toArray(new dietPlan[0]));
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(CONNECT_FAILED);
            }
        }).start();
    }
    public void upload(dietPlan plan){
        new Thread(()->{
            Gson gson =new Gson();
            MediaType mediaType = MediaType.parse("application/json; charset=UTF-8");
            RequestBody requestBody = RequestBody.create(gson.toJson(plan),mediaType);
            try {
                Response response = HttpUtil.post("/diet/add",requestBody);
                if(response.code()!=200){
                    handler.sendEmptyMessage(CONNECT_FAILED);
                }else{
                    JsonObject jsonObject = gson.fromJson(response.body().string(), JsonObject.class);
                    if(jsonObject.has("code")) {
                        if (jsonObject.get("code").getAsLong() == 1) {
                            handler.sendEmptyMessage(SUCCESS);
                        } else if (jsonObject.get("code").getAsLong() == 0) {
                            handler.sendEmptyMessage(FAILED);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(CONNECT_FAILED);
            }
        }).start();
    }
    public void getDietPlan(patientInfo info){
        new Thread(()->{
            Gson gson = new Gson();
            Map<String,String> map = new HashMap<>();
            map.put("Username",info.getUsername());
            MediaType mediaType = MediaType.parse("application/json; charset=UTF-8");
            RequestBody requestBody = RequestBody.create(gson.toJson(map),mediaType);
            try{
                Response response = HttpUtil.post("/diet/getDietPlan", requestBody);
                if(response.code() != 200){
                    handler.sendEmptyMessage(CONNECT_FAILED);
                }else{
                    String str = response.body().string();
                    if(str.length() == 0 ){
                        handler.sendEmptyMessage(FAILED);
                    }else{
                        dietPlan plan = gson.fromJson(str,dietPlan.class);
                        Message msg = new Message();
                        msg.what = SUCCESS;
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("dietPlan",plan);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
                }
            }catch (IOException e){
                handler.sendEmptyMessage(CONNECT_FAILED);
            }
        }).start();
    }
}
