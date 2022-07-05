package com.example.healkepdoctor.View.patientInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.healkepdoctor.R;
import com.example.healkepdoctor.model.patientInfo;
import com.google.gson.Gson;

public class patientInfoDetailActivity extends AppCompatActivity {
    private patientInfo info;
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info_detail);
        Intent intent = getIntent();
        if(intent == null) finish();
        Bundle bundle = intent.getBundleExtra("info");
        info = bundle.getParcelable("patient");
        initComponent();
    }
    private void initComponent(){
        text = findViewById(R.id.text);
        Gson gson = new Gson();
        text.setText(gson.toJson(info));
    }
}