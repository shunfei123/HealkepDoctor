package com.example.healkepdoctor.View.diet.fragement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.healkepdoctor.R;
import com.example.healkepdoctor.model.dietPlan;
import com.google.gson.Gson;


public class dietShowFragment extends Fragment {

    private dietPlan plan;
    private View root;
    private TextView startYear,startMonth,startDay;
    private EditText endYear,endMonth,endDay;
    private RecyclerView lst;
    private TextView total;
    private TextView btnSave,btnUpload;
    public dietShowFragment(dietPlan plan){
        this.plan = plan;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(root == null){
            root = inflater.inflate(R.layout.fragment_diet_show, container, false);
            initComponent();
        }
        return root;
    }
    public void initComponent(){
        startYear = root.findViewById(R.id.et_start_year);
        startMonth = root.findViewById(R.id.et_start_month);
        startDay = root.findViewById(R.id.et_start_day);
    }
}