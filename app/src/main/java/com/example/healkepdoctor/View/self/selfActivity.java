package com.example.healkepdoctor.View.self;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healkepdoctor.model.globalUserInfo;
import com.example.healkepdoctor.R;
import com.example.healkepdoctor.model.doctorInfo;

public class selfActivity extends AppCompatActivity implements View.OnLongClickListener,View.OnFocusChangeListener,View.OnClickListener{
    private EditText et_name,et_age,et_domain,et_profile;
    private TextView tv_name,tv_age,tv_domain,tv_profile;
    private Button accept;
    private doctorInfo info;
    private int isEdit = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selft);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("个人信息");
        }
        initComponent();
    }
    private void initComponent(){
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_domain = findViewById(R.id.et_domain);
        et_profile = findViewById(R.id.et_profile);
        tv_name = findViewById(R.id.tv_name);
        tv_age = findViewById(R.id.tv_age);
        tv_domain = findViewById(R.id.tv_domain);
        tv_profile = findViewById(R.id.tv_profile);
        accept = findViewById(R.id.btn_accept);
        info = globalUserInfo.getInstance().getInfo();

        if(info == null){
            finish();
        }
        tv_name.setText(info.getName());
        tv_age.setText(String.valueOf(info.getAge()));
        tv_domain.setText(info.getDomain());
        tv_profile.setText(info.getProfile());

        tv_name.setOnLongClickListener(this);
        tv_age.setOnLongClickListener(this);
        tv_domain.setOnLongClickListener(this);
        tv_profile.setOnLongClickListener(this);
        accept.setOnClickListener(this);
       /* et_name.setOnFocusChangeListener(this);
        et_domain.setOnFocusChangeListener(this);
        et_age.setOnFocusChangeListener(this);
        et_profile.setOnFocusChangeListener(this);*/
    }

    @Override
    public boolean onLongClick(View v) {
        if(isEdit == 0){
            tv_name.setVisibility(View.INVISIBLE);
            et_name.setText(info.getName(), TextView.BufferType.NORMAL);
            et_name.setVisibility(View.VISIBLE);
            et_name.setEnabled(true);
            tv_age.setVisibility(View.INVISIBLE);
            et_age.setText(String.valueOf(info.getAge()), TextView.BufferType.NORMAL);
            et_age.setVisibility(View.VISIBLE);
            et_age.setEnabled(true);
            tv_domain.setVisibility(View.INVISIBLE);
            et_domain.setText(info.getDomain(), TextView.BufferType.NORMAL);
            et_domain.setVisibility(View.VISIBLE);
            et_domain.setEnabled(true);
            tv_profile.setVisibility(View.INVISIBLE);
            et_profile.setText(info.getProfile(), TextView.BufferType.NORMAL);
            et_profile.setVisibility(View.VISIBLE);
            et_profile.setEnabled(true);

            isEdit = 1;
            accept.setVisibility(View.VISIBLE);
            Toast.makeText(selfActivity.this, "进入修改模式", Toast.LENGTH_SHORT).show();
        }
        /*if(v == tv_name){
            tv_name.setVisibility(View.INVISIBLE);
            et_name.setText(info.getName(), TextView.BufferType.NORMAL);
            et_name.setVisibility(View.VISIBLE);
            et_name.setEnabled(true);
        }else if(v == tv_age){
            tv_age.setVisibility(View.INVISIBLE);
            et_age.setText(String.valueOf(info.getAge()), TextView.BufferType.NORMAL);
            et_age.setVisibility(View.VISIBLE);
            et_age.setEnabled(true);
        }else if(v == tv_domain){
            tv_domain.setVisibility(View.INVISIBLE);
            et_domain.setText(info.getDomain(), TextView.BufferType.NORMAL);
            et_domain.setVisibility(View.VISIBLE);
            et_domain.setEnabled(true);
        }if(v == tv_profile){
            tv_profile.setVisibility(View.INVISIBLE);
            et_profile.setText(info.getProfile(), TextView.BufferType.NORMAL);
            et_profile.setVisibility(View.VISIBLE);
            et_profile.setEnabled(true);
        }*/
        return false;
    }
    @Override
    public void onClick(View v) {
        if(v == accept && isEdit == 1){
            et_name.setVisibility(View.INVISIBLE);
            et_name.setEnabled(false);
            String str = et_name.getText().toString();
            if(str.length()>0)
                info.setName(et_name.getText().toString());

            et_age.setVisibility(View.INVISIBLE);
            et_age.setEnabled(false);
            str = et_age.getText().toString();
            if(str.length()>0)
                info.setAge(Integer.parseInt(et_age.getText().toString()));

            et_domain.setVisibility(View.INVISIBLE);
            et_domain.setEnabled(false);
            str = et_domain.getText().toString();
            if(str.length()>0)
                info.setDomain(et_domain.getText().toString());

            et_profile.setVisibility(View.INVISIBLE);
            et_profile.setEnabled(false);
            str = et_profile.getText().toString();
            if(str.length()>0)
                info.setProfile(et_profile.getText().toString());

            globalUserInfo ginfo = globalUserInfo.getInstance();
            int flag = ginfo.setInfo(info);
            isEdit = 0;
            accept.setVisibility(View.INVISIBLE);
            if(flag == 1){
                Toast.makeText(selfActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
            }else if(flag == 0){
                Toast.makeText(selfActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                info = ginfo.getInfo();
            }else{
                Toast.makeText(selfActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                info = ginfo.getInfo();
            }
            tv_name.setText(info.getName());
            tv_name.setVisibility(View.VISIBLE);
            tv_age.setText(String.valueOf(info.getAge()));
            tv_age.setVisibility(View.VISIBLE);
            tv_domain.setText(info.getDomain());
            tv_domain.setVisibility(View.VISIBLE);
            tv_profile.setText(info.getProfile());
            tv_profile.setVisibility(View.VISIBLE);
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
    @Deprecated
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus){
            if(v==et_name){
                et_name.setVisibility(View.INVISIBLE);
                et_name.setEnabled(false);
                info.setName(et_name.getText().toString());
                tv_name.setText(info.getName());
                tv_name.setVisibility(View.VISIBLE);
            }else if(v == et_age){
                et_age.setVisibility(View.INVISIBLE);
                et_age.setEnabled(false);
                info.setAge(Integer.parseInt(et_age.getText().toString()));
                tv_age.setText(String.valueOf(info.getAge()));
                tv_age.setVisibility(View.VISIBLE);
            }else if(v == et_domain){
                et_domain.setVisibility(View.INVISIBLE);
                et_domain.setEnabled(false);
                info.setDomain(et_domain.getText().toString());
                tv_domain.setText(info.getDomain());
                tv_domain.setVisibility(View.VISIBLE);
            }else if(v == et_profile){
                et_profile.setVisibility(View.INVISIBLE);
                et_profile.setEnabled(false);
                info.setProfile(et_profile.getText().toString());
                tv_profile.setText(info.getProfile());
                tv_profile.setVisibility(View.VISIBLE);
            }
        }
    }
}