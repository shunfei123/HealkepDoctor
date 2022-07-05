package com.example.healkepdoctor.View.doctormain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healkepdoctor.R;
import com.example.healkepdoctor.View.doctormain.fragment.patientListFragment;
import com.example.healkepdoctor.View.login.helper.loginHelper;
import com.example.healkepdoctor.View.login.loginActivity;
import com.example.healkepdoctor.View.self.selfActivity;
import com.example.healkepdoctor.component.CircleImageView;
import com.example.healkepdoctor.model.doctorInfo;
import com.example.healkepdoctor.model.globalUserInfo;
import com.google.android.material.navigation.NavigationView;

public class doctorMainActivity extends AppCompatActivity implements View.OnClickListener{
    private CircleImageView civ_face;
    private TextView tv_name;
    private NavigationView navi;
    private DrawerLayout drawerLayout;
    private Fragment fragment = new patientListFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_doctor_main);
        initComponent();

    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    private void initComponent(){
        civ_face = findViewById(R.id.civ_face);
        tv_name = findViewById(R.id.tv_name);
        drawerLayout = findViewById(R.id.drawer_layout);
        navi = findViewById(R.id.navi);
        civ_face.setOnClickListener(this);
        tv_name.setOnClickListener(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.container, fragment)
                .setReorderingAllowed(false)
                .addToBackStack(null)
                .commit();
        navi.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.self){
                    Toast.makeText(doctorMainActivity.this, "个人信息", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(doctorMainActivity.this, selfActivity.class);
                    startActivity(intent);
                }else if(item.getItemId() == R.id.logout){
                    Toast.makeText(doctorMainActivity.this, "退出登录", Toast.LENGTH_SHORT).show();
                    loginHelper.logout();
                    Intent intent = new Intent(doctorMainActivity.this, loginActivity.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
    }

    private void update(){
        new Thread(()->{
            globalUserInfo gInfo = globalUserInfo.getInstance();
            doctorInfo info = gInfo.getInfo();
            int count = 5;
            while(count-->0 && info==null){
                info = gInfo.getInfo();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(info==null){
                runOnUiThread(()->{
                    Toast.makeText(doctorMainActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                });
            }else{
                runOnUiThread(()->{
                    doctorInfo info1= gInfo.getInfo();
                    tv_name.setText(info1.getName());
                    View headerLayout = navi.getHeaderView(0);
                    TextView view = headerLayout.findViewById(R.id.tv_name);
                    view.setText(info1.getName());
                });
            }
        }).start();
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public void onClick(View view) {
        if(view == civ_face){
            drawerLayout.openDrawer(Gravity.LEFT);
        }
    }

}