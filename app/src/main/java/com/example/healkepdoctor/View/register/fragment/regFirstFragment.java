package com.example.healkepdoctor.View.register.fragment;

import android.os.Bundle;

import androidx.core.view.KeyEventDispatcher;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.healkepdoctor.R;
import com.example.healkepdoctor.util.MD5;

public class regFirstFragment extends Fragment {

    private EditText et_account,et_password,et_password_repeat;
    private TextView tv_account,tv_password,tv_password_repeat;
    private String account="",password="",passwordRepeat="";
    private View view;
    public regFirstFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view==null){
            view = inflater.inflate(R.layout.fragment_reg_first, container, false);
            initComponent();
        }

        return view;

    }
    private void initComponent(){
        et_account = view.findViewById(R.id.et_account);
        et_password = view.findViewById(R.id.et_password);
        et_password_repeat = view.findViewById(R.id.et_password_repeat);

        tv_account = view.findViewById(R.id.tv_account);
        tv_password = view.findViewById(R.id.tv_password);
        tv_password_repeat = view.findViewById(R.id.tv_password_repeat);

        et_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                account = editable.toString();
                if(account.length()<=0){
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
                password = editable.toString();
                if(password.length()<=5){
                    tv_password.setText("密码太短");
                    tv_password.setVisibility(View.VISIBLE);
                }else{
                    tv_password.setVisibility(View.INVISIBLE);
                }
                if(!password.equals(passwordRepeat)){
                    tv_password_repeat.setText("两次密码不一致");
                    tv_password_repeat.setVisibility(View.VISIBLE);
                }else{
                    tv_password_repeat.setVisibility(View.INVISIBLE);
                }
            }
        });

        et_password_repeat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               passwordRepeat = editable.toString();
               if(password.equals(passwordRepeat)){
                   tv_password_repeat.setVisibility(View.INVISIBLE);
               }else{
                   tv_password_repeat.setText("两次密码不一致");
                   tv_password_repeat.setVisibility(View.VISIBLE);
               }
            }
        });
    }

    /**
     * 获取用户名
     * @return 用户名，空串返回null
     */
    public String getAccount() {
        if(account.length()>0)
            return account;
        else return null;
    }

    /**
     * 获取MD5散列后的密码
     * @return 如果两次密码不一致返回null 否则返回散列后的密码
     */
    public String getPassword() {
        if(passwordRepeat.equals(password)){
            return MD5.MD5Encode(password);
        }
        return null;
    }
}