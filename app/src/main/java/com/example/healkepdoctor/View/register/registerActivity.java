package com.example.healkepdoctor.View.register;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.KeyEventDispatcher;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.healkepdoctor.R;
import com.example.healkepdoctor.View.login.helper.loginHelper;
import com.example.healkepdoctor.View.login.loginActivity;
import com.example.healkepdoctor.View.register.fragment.regFirstFragment;
import com.example.healkepdoctor.View.register.fragment.regSecondFragment;
import com.example.healkepdoctor.View.register.helper.registerHelper;
import com.example.healkepdoctor.model.doctorInfo;

public class registerActivity extends AppCompatActivity implements View.OnClickListener{
    private ProgressBar progressBar;
    private FragmentContainerView container;
    private Button back,next;
    private Fragment fragment_first = new regFirstFragment(),
            fragment_second = new regSecondFragment();
    private int index = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //显示返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("注册");
        }
        initComponent();
        initView();
    }
    private void initView(){
        progressBar.setMax(2);
        progressBar.setProgress(1);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.container, fragment_first)
                .setReorderingAllowed(false)
                .addToBackStack(null)
                .commit();
        back.setVisibility(View.INVISIBLE);
    }
    private void initComponent(){
        progressBar = findViewById(R.id.pb_progress);
        container = findViewById(R.id.container);
        back = findViewById(R.id.btn_back);
        next = findViewById(R.id.btn_next);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //返回按钮点击事件
        if (item.getItemId() == android.R.id.home) {
            back();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void next(){
        if(index == 1){
            changeFragment(fragment_second);
            back.setVisibility(View.VISIBLE);
            next.setText("完成");
            progressBar.setProgress(2);
            index = 2;
        }else if(index==2){
            register();
        }
    }
    private void back(){
        if(index == 2){
            getSupportFragmentManager().popBackStack();
            back.setVisibility(View.INVISIBLE);
            next.setText("下一步");
            progressBar.setProgress(1);
            index = 1;
        }else if(index == 1){
            finish();
        }
    }
    private void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_right_in,
                        R.anim.slide_left_out,
                        R.anim.slide_left_in,
                        R.anim.slide_right_out)
                .setReorderingAllowed(false)
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
    private Handler handler;
    private void register(){
        String account = ((regFirstFragment)fragment_first).getAccount();
        if(account==null){
            Toast.makeText(this,"账号不能为空",Toast.LENGTH_SHORT).show();
            back();
            return;
        }
        String password = ((regFirstFragment)fragment_first).getPassword();
        if(password==null){
            Toast.makeText(this,"密码太短或两次密码不一致",Toast.LENGTH_SHORT).show();
            back();
            return;
        }
        doctorInfo info = ((regSecondFragment)fragment_second).getInfo();
        if(info.getName().length()<=0){
            Toast.makeText(this,"请输入姓名",Toast.LENGTH_SHORT).show();
            return;
        }
        if(info.getAge()<0){
            Toast.makeText(this,"请输入年龄",Toast.LENGTH_SHORT).show();
            return;
        }
        handler = new registerHandler(account, password);
        registerHelper helper = new registerHelper(handler);
        helper.register(account, password, info);
    }
    class registerHandler extends Handler{
        private final String account;
        private final String password;
        public registerHandler(String account,String password){
            this.account = account;
            this.password = password;
        }
        @Override
        public void handleMessage(Message msg){
            int result = msg.what;
            switch (result){
                case registerHelper.REGISTER_SUCCESS:
                    Toast.makeText(registerActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(registerActivity.this, loginActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("account", account);
                    bundle.putString("password", password);
                    intent.putExtra("login", bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case registerHelper.REGISTER_FAILED:
                    Toast.makeText(registerActivity.this,"注册失败，请稍后重试",Toast.LENGTH_SHORT).show();
                    break;
                case registerHelper.CONNECT_FAILED:
                    Toast.makeText(registerActivity.this,"网络异常",Toast.LENGTH_SHORT).show();
                    break;
                case registerHelper.USERNAME_CONFLICT:
                    Toast.makeText(registerActivity.this,"用户名重复",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
    @Override
    public void onClick(View view) {
        if(view == next){
            next();
        }else if(view == back){
            back();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        if (keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            back();
        }
        return false;
    }

}