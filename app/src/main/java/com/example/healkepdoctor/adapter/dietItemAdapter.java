package com.example.healkepdoctor.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cuiweiyou.numberpickerdialog.NumberPickerDialog;
import com.example.healkepdoctor.R;
import com.example.healkepdoctor.View.diet.fragement.dietEditFragment;
import com.example.healkepdoctor.model.dietPlan;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class dietItemAdapter extends RecyclerView.Adapter<dietItemAdapter.dietItemViewHolder> {
    private final String[] itemNames= {"mainFood","vegetables","fruit","meatAndEgg","bean","milk","nut","oil","salt"};
    private final String[] itemNamesToString= {"主食","蔬菜","水果","肉蛋","豆类","奶制品","坚果类","油","盐"};
    private int[] min = new int[itemNames.length];
    private int[] max = new int[itemNames.length];
    private Context context;
    private dietEditFragment.notify notify;
    public dietItemAdapter(Context context, dietEditFragment.notify notify){
        this.context = context;
        this.notify = notify;
    }
    public dietItemAdapter(Context context, dietEditFragment.notify notify,dietPlan plan){
        this(context,notify);
        parse(plan);

    }
    private void parse(dietPlan plan){
        List<String> indexes = Arrays.asList(itemNames);
        Class c1 = plan.getClass();
        Field[] fields = c1.getDeclaredFields();
        for(Field i : fields){
            i.setAccessible(true);
            if(indexes.contains(i.getName())){
                String key = i.getName();
                int index = indexes.indexOf(key);
                int min=0,max=0;
                String value = "";
                try {
                    value = (String)i.get(plan);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    continue;
                }
                if(value.matches("^\\d*-\\d*$")){
                    String[] temp = value.split("-");
                    min = Integer.parseInt(temp[0]);
                    max = Integer.parseInt(temp[1]);
                }else{
                    min = Integer.parseInt(value);
                    max = min;
                }
                this.min[index] = min;
                this.max[index] = max;
            }
        }
    }
    @NonNull
    @Override
    public dietItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.diet_item, parent,false);
        return new dietItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull dietItemViewHolder holder, int position) {
        holder.tv_itemName.setText(itemNamesToString[position]);
        holder.et_max.setText(String.valueOf(max[position]));
        holder.et_min.setText(String.valueOf(min[position]));
        holder.et_min.setOnClickListener((view)->{
            new NumberPickerDialog(
                    context,
                    new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                            min[holder.getAdapterPosition()]  = newVal;
                            holder.et_min.setText(String.valueOf(newVal));
                            if(newVal>max[holder.getAdapterPosition()]){
                                max[holder.getAdapterPosition()] = newVal;
                                holder.et_max.setText(String.valueOf(newVal));
                            }
                            update();
                        }
                    },
                    20, // 最大值
                    0, // 最小值
                    0) // 默认值
                    .setCurrentValue(min[holder.getAdapterPosition()]) // 更新默认值
                    .show();
        });
        holder.et_max.setOnClickListener((view)->{
            new NumberPickerDialog(
                    context,
                    new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                            max[holder.getAdapterPosition()]  = newVal;
                            holder.et_max.setText(String.valueOf(newVal));
                            if(newVal<min[holder.getAdapterPosition()]){
                                min[holder.getAdapterPosition()] = newVal;
                                holder.et_min.setText(String.valueOf(newVal));
                            }
                            update();
                        }
                    },
                    20, // 最大值
                    0, // 最小值
                    0) // 默认值
                    .setCurrentValue(max[holder.getAdapterPosition()]) // 更新默认值
                    .show();
        });
        update();
    }

    @Override
    public int getItemCount() {
        return itemNames.length;
    }

    private void update(){
        int sum = Arrays.stream(min).sum();
        sum= sum-min[7]-min[8];
        notify.callback(sum*337);
    }
    public void getData(dietPlan plan){
        Map<String,String> map = new HashMap<>();
        for(int i=0;i<itemNames.length;i++){
            String value = null;
            if(min[i] == max[i]){
                value = String.valueOf(min[i]);
            }else{
                value = min[i]+"-"+max[i];
            }
            map.put(itemNames[i],value);
        }

        Class c1 = plan.getClass();
        try{
            for(Map.Entry<String,String> i : map.entrySet()){
                Field field = c1.getDeclaredField(i.getKey());
                field.setAccessible(true);
                field.set(plan,i.getValue());
            }
        }catch (NoSuchFieldException | IllegalAccessException e){
            e.printStackTrace();
        }
        int sum = Arrays.stream(min).sum();
        sum= sum-min[7]-min[8];
        plan.setTotalFood(sum);
        plan.setTotalEnergy(sum*337);
    }
    static class dietItemViewHolder extends RecyclerView.ViewHolder{
        private TextView et_min;
        private TextView et_max;
        private TextView tv_itemName;
        public dietItemViewHolder(@NonNull View itemView) {
            super(itemView);
            et_min = itemView.findViewById(R.id.et_min);
            et_max = itemView.findViewById(R.id.et_max);
            tv_itemName = itemView.findViewById(R.id.tv_itemName);
        }
    }
}
