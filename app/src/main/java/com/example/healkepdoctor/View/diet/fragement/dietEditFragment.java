package com.example.healkepdoctor.View.diet.fragement;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healkepdoctor.R;
import com.example.healkepdoctor.View.diet.helper.dietDataHelper;
import com.example.healkepdoctor.adapter.dietItemAdapter;
import com.example.healkepdoctor.adapter.dietPlanItemAdapter;
import com.example.healkepdoctor.model.dietPlan;
import com.example.healkepdoctor.model.globalUserInfo;
import com.example.healkepdoctor.model.patientInfo;
import com.google.gson.Gson;
import com.google.gson.ToNumberStrategy;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;


public class dietEditFragment extends Fragment implements View.OnClickListener{
    private dietPlan plan = new dietPlan();
    private View root;
    private TextView startYear,startMonth,startDay;
    private TextView endYear,endMonth,endDay;
    private RecyclerView lst;
    private TextView total;
    private TextView btnSave,btnUpload;
    private dietItemAdapter adapter;
    private Dialog startDateDialog,endDateDialog;
    private patientInfo patientInfo;
    private ProgressBar progressBar;
    public dietEditFragment(patientInfo patient){
        this.patientInfo = patient;


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
            initData();
            //initComponent();
        }
        return root;
    }
    public void initData(){
        Context context = getContext();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(patientInfo.getUsername(), Context.MODE_PRIVATE);
        globalUserInfo info = globalUserInfo.getInstance();
        plan.setDoctorUsername(info.getAccount());
        plan.setTargetUsername(patientInfo.getUsername());
        plan.setStart(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        plan.setEnd(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        if(sharedPreferences!=null){
            String json = sharedPreferences.getString("dietPlan_temp","");
            if(!json.equals("")){
                Dialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle("提示")
                        .setMessage("你有一份未上传的营养计划，是否继续编辑？")
                        .setPositiveButton("继续编辑",(d,which)->{
                            Gson gson = new Gson();
                            plan = gson.fromJson(json, plan.getClass());
                            initComponent();
                        })
                        .setNegativeButton("重新开始",(d,which)->{
                            SharedPreferences.Editor editor = getContext().getSharedPreferences(patientInfo.getUsername(), Context.MODE_PRIVATE).edit();
                            editor.remove("dietPlan_temp");
                            editor.apply();
                            globalUserInfo infos = globalUserInfo.getInstance();
                            plan.setDoctorUsername(infos.getAccount());
                            plan.setTargetUsername(patientInfo.getUsername());
                            plan.setStart(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                            plan.setEnd(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                            initComponent();
                            autoFill();
                        })
                        .create();
                dialog.show();
                return;
            }
        }
        initComponent();
        autoFill();

    }
    public void autoFill(){
        Toast.makeText(getContext(), "推荐算法运行中...", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);
        dietDataHelper helper = new dietDataHelper(new autoHandler());
        helper.getDietPlan(patientInfo);
    }
    public void initComponent(){
        startYear = root.findViewById(R.id.tv_start_year);
        startMonth = root.findViewById(R.id.tv_start_month);
        startDay = root.findViewById(R.id.tv_start_day);
        endYear = root.findViewById(R.id.tv_end_year);
        endMonth = root.findViewById(R.id.tv_end_month);
        endDay = root.findViewById(R.id.tv_end_day);
        lst = root.findViewById(R.id.rcv_lst);
        total = root.findViewById(R.id.tv_total);
        btnSave = root.findViewById(R.id.tv_save);
        btnUpload = root.findViewById(R.id.tv_upload);
        progressBar = root.findViewById(R.id.progress);

        progressBar.setVisibility(View.GONE);
        lst.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        lst.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        adapter =new dietItemAdapter(getContext(),notify,plan);
        lst.setAdapter(adapter);

        startYear.setOnClickListener(this);
        startMonth.setOnClickListener(this);
        startDay.setOnClickListener(this);

        endYear.setOnClickListener(this);
        endMonth.setOnClickListener(this);
        endDay.setOnClickListener(this);

        btnSave.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
        updateStart(LocalDate.parse(plan.getStart()));
        updateEnd(LocalDate.parse(plan.getEnd()));
        Calendar calendar = Calendar.getInstance();
        startDateDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            LocalDate date = LocalDate.of(year, month+1, dayOfMonth);
            if(date.isAfter(LocalDate.now()) || date.equals(LocalDate.now())){
                updateStart(date);
                LocalDate end = LocalDate.parse(plan.getEnd(),DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                if(end.isBefore(date)){
                    updateEnd(date);
                }
            }

        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

        endDateDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            LocalDate date = LocalDate.of(year, month+1, dayOfMonth);
            LocalDate start = LocalDate.parse(plan.getStart(),DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if(date.isAfter(start) || date.equals(start)){
                updateEnd(date);
                if(start.isAfter(date)){
                    updateStart(date);
                }
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
    }
    private void updateStart(LocalDate date){
        plan.setStart(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        startYear.setText(String.valueOf(date.getYear()));
        startMonth.setText(String.valueOf(date.getMonth().getValue()));
        startDay.setText(String.valueOf(date.getDayOfMonth()));

    }
    private void updateEnd(LocalDate date){
        plan.setEnd(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        endYear.setText(String.valueOf(date.getYear()));
        endMonth.setText(String.valueOf(date.getMonth().getValue()));
        endDay.setText(String.valueOf(date.getDayOfMonth()));
    }
    @Override
    public void onClick(View v) {
        if(v == startYear || v == startDay || v == startMonth){
            startDateDialog.show();
        }else if(v == endYear || v==endMonth || v==endDay){
            endDateDialog.show();
        }else if(v == btnSave){
            adapter.getData(plan);
            SharedPreferences.Editor editor = getContext().getSharedPreferences(patientInfo.getUsername(), Context.MODE_PRIVATE).edit();
            Gson gson = new Gson();
            editor.putString("dietPlan_temp",gson.toJson(plan));
            editor.apply();
            Toast.makeText(getContext(),"保存成功，下一次打开可继续编辑",Toast.LENGTH_SHORT ).show();
        }else if(v == btnUpload){
            adapter.getData(plan);
            progressBar.setVisibility(View.VISIBLE);
            dietDataHelper helper = new dietDataHelper(new uploadHandler());
            helper.upload(plan);
        }
    }
    private notify notify = new notify() {
        @Override
        public void callback(int total) {
            dietEditFragment.this.total.setText(String.valueOf(total));
        }
    };
    public interface notify{
        void callback(int total);
    }
    class autoHandler extends Handler{

        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==dietDataHelper.SUCCESS){
               Bundle bundle = msg.getData();
               dietPlan plan = (dietPlan) bundle.get("dietPlan");
               dietEditFragment.this.plan.setTotalEnergy(plan.getTotalEnergy());
               dietEditFragment.this.plan.setTotalFood(plan.getTotalFood());
               dietEditFragment.this.plan.setMainFood(plan.getMainFood());
               dietEditFragment.this.plan.setVegetables(plan.getVegetables());
               dietEditFragment.this.plan.setFruit(plan.getFruit());
               dietEditFragment.this.plan.setMeatAndEgg(plan.getMeatAndEgg());
               dietEditFragment.this.plan.setBean(plan.getBean());
               dietEditFragment.this.plan.setMilk(plan.getMilk());
               dietEditFragment.this.plan.setNut(plan.getNut());
               dietEditFragment.this.plan.setOil(plan.getOil());
               dietEditFragment.this.plan.setSalt(plan.getSalt());
               initComponent();
               progressBar.setVisibility(View.GONE);
               Toast.makeText(getContext(), "推荐算法已填入", Toast.LENGTH_SHORT).show();
            }else if(msg.what == dietDataHelper.FAILED){
                Toast.makeText(getContext(), "推荐失败", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "网络连接异常，推荐失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
    class uploadHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == dietDataHelper.SUCCESS){
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),"上传成功", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = getContext().getSharedPreferences(patientInfo.getUsername(), Context.MODE_PRIVATE).edit();
                editor.remove("dietPlan_temp");
                editor.apply();
            }else if(msg.what == dietDataHelper.CONNECT_FAILED){
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),"网络连接异常", Toast.LENGTH_SHORT).show();
            }else{
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),"未知错误，请重试", Toast.LENGTH_SHORT).show();
            }
        }
    }
}