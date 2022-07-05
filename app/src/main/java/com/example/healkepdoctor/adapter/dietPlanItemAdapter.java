package com.example.healkepdoctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healkepdoctor.R;
import com.example.healkepdoctor.View.diet.dietActivity;
import com.example.healkepdoctor.model.dietPlan;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class dietPlanItemAdapter extends RecyclerView.Adapter<dietPlanItemAdapter.dietPlanItemViewHolder> {
    private Context context;
    private List<dietPlan> plans;
    private dietActivity.ClickListener listener;
    private int type;
    public dietPlanItemAdapter(Context context, List<dietPlan> plans , dietActivity.ClickListener listener, int type){
        this.context = context;
        this.plans = plans;
        this.listener = listener;
    }
    @NonNull
    @Override
    public dietPlanItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.diet_plan_list_item, parent,false);
        return new dietPlanItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull dietPlanItemViewHolder holder, int position) {
        final dietPlan plan = plans.get(position);
        holder.init(plan);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.callback(type, plan);
            }
        });
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    static class dietPlanItemViewHolder extends RecyclerView.ViewHolder{
        private final TextView name;
        private final TextView start;
        private final TextView end;
        private final LinearLayout layout;
        public dietPlanItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            start = itemView.findViewById(R.id.tv_start);
            end = itemView.findViewById(R.id.tv_end);
            layout = itemView.findViewById(R.id.layout);
        }
        public void init(dietPlan plan){
            this.name.setText(plan.getTargetUsername());
            this.start.setText(plan.getStart());
            this.end.setText(plan.getEnd());
        }
    }
}
