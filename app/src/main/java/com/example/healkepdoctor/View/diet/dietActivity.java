package com.example.healkepdoctor.View.diet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.healkepdoctor.R;
import com.example.healkepdoctor.View.diet.fragement.dietEditFragment;
import com.example.healkepdoctor.View.diet.fragement.dietListFragment;
import com.example.healkepdoctor.View.diet.fragement.dietShowFragment;
import com.example.healkepdoctor.View.diet.helper.dietDataHelper;
import com.example.healkepdoctor.model.dietPlan;
import com.example.healkepdoctor.model.patientInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class dietActivity extends AppCompatActivity {
    private List<dietPlan> plans;
    private int routeTag = 0;
    private Handler handler = new MyHandler();
    private patientInfo info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);
        Intent intent = getIntent();
        if(intent == null){
            finish();
        }
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        Bundle bundle = intent.getBundleExtra("info");
        String type = bundle.getString("type");
        info = bundle.getParcelable("patientInfo");
        if(info == null || type == null ) finish();
        if(type.equals("now")){
            //当前饮食方案
            routeTag = 1;
        }else if(type.equals("history")){
            //历史饮食方案
            routeTag = 2;
        }else if(type.equals("create")){
            //饮食方案指定
            routeTag = 3;
        }else{
            finish();
        }
        dietDataHelper helper = new dietDataHelper(handler);
        helper.getData(info);
    }
    private ClickListener nowListener = new ClickListener() {
        @Override
        public void callback(int type, dietPlan plan) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new dietShowFragment(plan))
                    .setReorderingAllowed(false)
                    .addToBackStack(null)
                    .commit();
        }
    };
    private ClickListener historyListener = new ClickListener() {
        @Override
        public void callback(int type, dietPlan plan) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new dietShowFragment(plan))
                    .setReorderingAllowed(false)
                    .addToBackStack(null)
                    .commit();
        }
    };
    private void route(){
        if(routeTag == 1){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.container, new dietListFragment(filterNow(), nowListener,0))
                    .setReorderingAllowed(false)
                    .addToBackStack(null)
                    .commit();
        }else if(routeTag == 2){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.container, new dietListFragment(filterHistory(), historyListener,1))
                    .setReorderingAllowed(false)
                    .addToBackStack(null)
                    .commit();
        }else{
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.container, new dietEditFragment(info))
                    .setReorderingAllowed(false)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private List<dietPlan> filterNow(){
        List<dietPlan> lst = new ArrayList<>();
        for(dietPlan i : plans){
            LocalDate start = LocalDate.parse(i.getStart());
            LocalDate end = LocalDate.parse(i.getEnd());
            if(start.equals(LocalDate.now()) ||
                    end.equals(LocalDate.now()) ||
                    start.isAfter(LocalDate.now())){
                lst.add(i);
            }
        }
        return lst;
    }
    private List<dietPlan> filterHistory(){
        List<dietPlan> lst = new ArrayList<>();
        for(dietPlan i : plans){
            LocalDate start = LocalDate.parse(i.getStart());
            LocalDate end = LocalDate.parse(i.getEnd());
            if(end.isBefore(LocalDate.now())){
                lst.add(i);
            }
        }
        return lst;
    }
    public interface ClickListener{
        void callback(int type,dietPlan plan);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        super.onKeyDown(keyCode,event);
        if (keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
           FragmentManager manager =  getSupportFragmentManager();
           if(manager.getBackStackEntryCount()<=1){
               finish();
           }else{
               manager.popBackStack();
           }
        }
        return false;
    }
    class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            int type = msg.what;
            if(type == dietDataHelper.SUCCESS){
                Bundle bundle = msg.getData();
                dietPlan[] temp = (dietPlan[]) bundle.getParcelableArray("plans");
                dietActivity.this.plans = Arrays.asList(temp);
                route();
            }else if(type == dietDataHelper.FAILED){
                Toast.makeText(dietActivity.this, "获取信息失败，请稍后重试", Toast.LENGTH_SHORT).show();
            }else if(type == dietDataHelper.CONNECT_FAILED){
                Toast.makeText(dietActivity.this, "连接失败，请检查网路后重试", Toast.LENGTH_SHORT).show();
            }
        }
    }

}