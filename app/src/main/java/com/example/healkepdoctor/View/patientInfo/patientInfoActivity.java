package com.example.healkepdoctor.View.patientInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.healkepdoctor.R;
import com.example.healkepdoctor.View.diet.dietActivity;
import com.example.healkepdoctor.model.patientInfo;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class patientInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private patientInfo info;
    private TextView tv_name,tv_age,tv_weight,tv_preWeek,detail;
    private NavigationView navi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("info");
        info = bundle.getParcelable("patient");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("孕妇信息");
        }
        initComponent();
    }
    private void initComponent(){
        tv_name = findViewById(R.id.tv_name);
        tv_age = findViewById(R.id.tv_age);
        tv_weight = findViewById(R.id.tv_weight);
        tv_preWeek = findViewById(R.id.tv_preWeek);
        detail = findViewById(R.id.tv_detail);
        navi = findViewById(R.id.navi);
        tv_name.setText(info.getUsername()==null?"Unknown":info.getUsername());
        tv_age.setText(String.valueOf(info.getAge()));
        tv_weight.setText(info.getWeight1() +"kg");
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-M-d");
        Date start;
        if(info.getDate() == null){
            tv_preWeek.setText("Unknown");
        }else{
            try {
                start = ft.parse(info.getDate());
                Date end = new Date();
                long delta = end.getTime()-start.getTime();
                long week = delta/1000/60/60/24/7;
                tv_preWeek.setText(String.valueOf(week));
            } catch (ParseException e) {
                e.printStackTrace();
                tv_preWeek.setText("Unknown");
            }
        }
        detail.setOnClickListener(this);
        navi.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.diet_current){
                    Intent intent = new Intent(patientInfoActivity.this, dietActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "now");
                    bundle.putParcelable("patientInfo",info);
                    intent.putExtra("info", bundle);
                    startActivity(intent);
                }else if(item.getItemId() == R.id.diet_history){
                    Intent intent = new Intent(patientInfoActivity.this, dietActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "history");
                    bundle.putParcelable("patientInfo",info);
                    intent.putExtra("info", bundle);
                    startActivity(intent);
                }else if(item.getItemId() == R.id.diet_future){
                    Intent intent = new Intent(patientInfoActivity.this, dietActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "create");
                    bundle.putParcelable("patientInfo",info);
                    intent.putExtra("info", bundle);
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == detail){
            Intent intent = new Intent(this,patientInfoDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("patient",info);
            intent.putExtra("info", bundle);
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //返回按钮点击事件
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}