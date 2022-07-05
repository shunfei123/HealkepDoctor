package com.example.healkepdoctor.View.doctormain.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healkepdoctor.R;
import com.example.healkepdoctor.View.doctormain.helper.patientListHelper;
import com.example.healkepdoctor.adapter.patientItemAdapter;
import com.example.healkepdoctor.model.patientInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class patientListFragment extends Fragment {
    private SearchView search;
    private RecyclerView recyclerView;
    private View view;
    private patientItemAdapter adapter;
    private Handler handler = new MyHandler();
    private List<patientInfo> info;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       if(view == null){
            view = inflater.inflate(R.layout.fragment_patient_list, container, false);
            //initComponent();
            patientListHelper helper = new patientListHelper(handler);
            helper.getPatientInfo();
        }

        return view;
    }

    private void initComponent(){
        search = view.findViewById(R.id.search);
        recyclerView  = view.findViewById(R.id.rcv_patient);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        //设置分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        //创建适配器
        adapter = new patientItemAdapter(getContext(),info);
        //设置适配器
        recyclerView.setAdapter(adapter);
    }
    private List<patientInfo> getPatientInfo(){
        Handler handler = new Handler();
        patientListHelper helper = new patientListHelper(handler);
        helper.getPatientInfo();
        List<patientInfo> list = new ArrayList<>();
        patientInfo info1 = new patientInfo();
        info1.setAge(22);
        info1.setUsername("张三");
        info1.setDate("2022-5-6");
        info1.setWeight1(64.0);
        patientInfo info2 = new patientInfo();
        info2.setAge(24);
        info2.setUsername("李四");
        info2.setDate("2022-4-6");
        info2.setWeight1(64.0);
        list.add(info1);
        list.add(info2);
        return list;
    }

    class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == patientListHelper.SUCCESS){
                Bundle bundle = msg.getData();
                patientInfo[] infos = (patientInfo[])bundle.getParcelableArray("info");
                info = Arrays.asList(infos);
                initComponent();
            }
        }
    }
}