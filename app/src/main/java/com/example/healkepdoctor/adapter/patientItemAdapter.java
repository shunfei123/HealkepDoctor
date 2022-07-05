package com.example.healkepdoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healkepdoctor.R;
import com.example.healkepdoctor.View.patientInfo.patientInfoActivity;
import com.example.healkepdoctor.model.patientInfo;

import java.util.ArrayList;
import java.util.List;

public class patientItemAdapter extends RecyclerView.Adapter<patientItemAdapter.patientItemViewHolder> {
    private final List<patientInfo> patients = new ArrayList<>();
    private Context context;
    public patientItemAdapter(Context context,List<patientInfo> patients){
        this.context = context;
        this.patients.addAll(patients);
    }
    @NonNull
    @Override
    public patientItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.patient_list_item, parent,false);
        return new patientItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull patientItemViewHolder holder, int position) {
        patientInfo patient = patients.get(position);
        holder.set(patient);
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    class patientItemViewHolder extends RecyclerView.ViewHolder {
        private TextView name,age;
        private LinearLayout layout;
        public patientItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            age = itemView.findViewById(R.id.tv_age);
            layout =itemView.findViewById(R.id.layout);
        }
        public void set(patientInfo patient){
            this.age.setText(String.valueOf(patient.getAge()));
            if(patient.getUsername() == null){
                this.name.setText("Unknown");
            }else{
                this.name.setText(patient.getUsername());
            }

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, patientInfoActivity.class);
                    Bundle bundle =new Bundle();
                    bundle.putParcelable("patient", patient);
                    intent.putExtra("info", bundle);
                    context.startActivity(intent);
                }
            });
        }
    }
}
