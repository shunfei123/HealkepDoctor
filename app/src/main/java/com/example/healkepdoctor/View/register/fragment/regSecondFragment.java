package com.example.healkepdoctor.View.register.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.healkepdoctor.R;
import com.example.healkepdoctor.model.doctorInfo;


public class regSecondFragment extends Fragment {

    private EditText et_name,et_age,et_domain,et_profile;
    private TextView tv_name,tv_age;
    private RadioGroup rg_sex;
    private View view;
    private doctorInfo info = new doctorInfo();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view == null){
            view = inflater.inflate(R.layout.fragment_reg_second, container, false);
            initComponent();
        }
        return view;
    }
    private void initComponent(){
        et_name = view.findViewById(R.id.et_name);
        et_age = view.findViewById(R.id.et_age);
        et_domain = view.findViewById(R.id.et_domain);
        et_profile = view.findViewById(R.id.et_profile);
        tv_name = view.findViewById(R.id.tv_name);
        tv_age = view.findViewById(R.id.tv_age);
        rg_sex = view.findViewById(R.id.rg_sex);

        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                info.setName(editable.toString());
                if(info.getName().length()<=0){
                    tv_name.setText("请填写用户名");
                    tv_name.setVisibility(View.VISIBLE);
                }else{
                    tv_name.setVisibility(View.INVISIBLE);
                }
            }
        });

        et_age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String age = editable.toString();
                if(age.length()<=0){
                    info.setAge(-1);
                    tv_age.setText("请填写年龄");
                    tv_age.setVisibility(View.VISIBLE);
                }else{
                    info.setAge(Integer.valueOf(age));
                    tv_age.setVisibility(View.INVISIBLE);
                }
            }
        });

        rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_female:
                        info.setSex(1);
                        break;
                    case R.id.rb_male:
                        info.setSex(0);
                        break;
                }
            }
        });
    }

    /**
     * 获取info信息 未校验
     * @return 医生信息
     */
    public doctorInfo getInfo(){
        info.setDomain(et_domain.getText().toString());
        info.setProfile(et_profile.getText().toString());
        return info;
    }
}