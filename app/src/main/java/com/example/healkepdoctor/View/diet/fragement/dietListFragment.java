package com.example.healkepdoctor.View.diet.fragement;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.healkepdoctor.R;
import com.example.healkepdoctor.View.diet.dietActivity;
import com.example.healkepdoctor.adapter.dietPlanItemAdapter;
import com.example.healkepdoctor.adapter.patientItemAdapter;
import com.example.healkepdoctor.model.dietPlan;

import java.util.List;

public class dietListFragment extends Fragment {

    private List<dietPlan> plans;
    private dietActivity.ClickListener listener;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private RecyclerView.Adapter adapter;
    private int type;
    private View root;
    public dietListFragment(List<dietPlan> plans,dietActivity.ClickListener listener,int type){
        this.plans = plans;
        this.listener = listener;
        this.type = type;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(root == null) {
            root = inflater.inflate(R.layout.fragment_diet_list, container, false);
            initComponent();
        }
        return root;
    }

    private void initComponent(){
        recyclerView = root.findViewById(R.id.rcv_plans);
        toolbar = root.findViewById(R.id.toolbar);
        if(type == 0){
            toolbar.setTitle("当前饮食方案");
        }else{
            toolbar.setTitle("历史饮食方案");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        adapter =new dietPlanItemAdapter(getContext(), plans, listener, type);
        recyclerView.setAdapter(adapter);
    }
}