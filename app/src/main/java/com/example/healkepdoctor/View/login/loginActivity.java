package com.example.healkepdoctor.View.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healkepdoctor.R;
import com.example.healkepdoctor.View.doctormain.doctorMainActivity;
import com.example.healkepdoctor.View.forget.forgetActivity;
import com.example.healkepdoctor.View.login.helper.loginHelper;
import com.example.healkepdoctor.View.register.registerActivity;
import com.example.healkepdoctor.model.Config;
import com.example.healkepdoctor.model.globalUserInfo;
import com.example.healkepdoctor.util.MD5;

public class loginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText et_account,et_password;
    private TextView tv_account,tv_password,tv_forget,tv_register,tv_rule;
    private CheckBox accept;
    private Button login;
    private Button config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        Bundle bundle = getIntent().getBundleExtra("login");
        if(bundle !=null){
            String account = bundle.getString("account");
            String pwd = bundle.getString("password");
            login(account,pwd);
        }
        setContentView(R.layout.activity_login);
        initComponent();
    }
    private void initComponent(){
        et_account = findViewById(R.id.et_account);
        et_password = findViewById(R.id.et_password);
        tv_account = findViewById(R.id.tv_account);
        tv_forget = findViewById(R.id.tv_forget);
        tv_register = findViewById(R.id.tv_register);
        tv_password = findViewById(R.id.tv_password);
        tv_rule = findViewById(R.id.tv_rule);
        accept = findViewById(R.id.ck_rule);
        login = findViewById(R.id.btn_login);
        config = findViewById(R.id.btn_config);

        config.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        login.setOnClickListener(this);
        tv_rule.setOnClickListener(this);
        et_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String account = editable.toString();
                if (account.equals("")){
                    tv_account.setText("用户名不能为空");
                    tv_account.setVisibility(View.VISIBLE);
                }else{
                    tv_account.setVisibility(View.INVISIBLE);
                }
            }
        });

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String account = editable.toString();
                if (account.length()<=5){
                    tv_password.setText("密码太短");
                    tv_password.setVisibility(View.VISIBLE);
                }else{
                    tv_password.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == tv_forget){
            forget();
        }else if(view == tv_register){
            register();
        }else if(view == login){
            login();
        }else if(view == tv_rule){
            readRule();
        }else if(view == config){
            EditText edit = new EditText(this);
            edit.setText(Config.url);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setView(edit)
                    .setNegativeButton("取消",null)
                    .setPositiveButton("确认", (d,which)->{
                        Config.url = edit.getText().toString();
                    })
                    .setTitle("输入服务器IP及其对应的端口")
                    .create();
            dialog.show();
        }
    }
    private void login(){
        //登录事件的处理
        String password = et_password.getText().toString();
        String account = et_account.getText().toString();
        if(account.length()==0){
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length()<=5){
            Toast.makeText(this, "密码太短", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!accept.isChecked()){
            Toast.makeText(this, "请阅读用户协议并勾选", Toast.LENGTH_SHORT).show();
            return;
        }
        login(account,MD5.MD5Encode(password));
    }
    private void login(String account,String password){
        loginHelper helper = new loginHelper(new loginHandler());
        helper.login(account, password);
    }
    class loginHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case loginHelper.LOGIN_SUCCESS:
                    Intent intent = new Intent(loginActivity.this, doctorMainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case loginHelper.LOGIN_FAILED:
                    Toast.makeText(loginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                    break;
                case loginHelper.CONNECT_FAILED:
                    Toast.makeText(loginActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
    private void register(){
        //注册事件的处理
        Intent intent = new Intent(this, registerActivity.class);
        startActivity(intent);
    }
    private void forget(){
        //忘记密码事件的处理
        Intent intent = new Intent(this, forgetActivity.class);
        startActivity(intent);
    }
    private void readRule(){
        Toast.makeText(this, "阅读用户协议", Toast.LENGTH_SHORT).show();
    }

}