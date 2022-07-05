package com.example.healkepdoctor.View.doctormain.helper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.healkepdoctor.model.patientInfo;
import com.example.healkepdoctor.util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class patientListHelper {
    public static int SUCCESS = 1;
    public static int FAILED = 2;
    public static int CONNECT_FAILED = 3;
    private Handler handler;
    public patientListHelper(Handler handler){
        this.handler = handler;
    }

    public void getPatientInfo(){
       new Thread(()->{
           try {
               RequestBody requestBody = new FormBody(new ArrayList<>(), new ArrayList<>());
               Response response = HttpUtil.post("/information/getAll", requestBody);
                if(response.code()!=200){
                    handler.sendEmptyMessage(CONNECT_FAILED);
                    return;
                }
                String json = response.body().string();
                Gson gson = new Gson();
                List<patientInfo> patientInfos = new ArrayList<>();
                JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();
                for(JsonElement i : jsonArray){
                    patientInfos.add(gson.fromJson(i, patientInfo.class));
                }
                Message msg = new Message();
                msg.what = SUCCESS;
                Bundle bundle = new Bundle();
                bundle.putParcelableArray("info", patientInfos.toArray(new patientInfo[0]));
                msg.setData(bundle);
                handler.sendMessage(msg);
           } catch (IOException e) {
               e.printStackTrace();
               handler.sendEmptyMessage(CONNECT_FAILED);
           }
       }).start();
    }
}
