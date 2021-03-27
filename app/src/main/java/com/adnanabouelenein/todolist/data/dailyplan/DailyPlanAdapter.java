package com.adnanabouelenein.todolist.data.dailyplan;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.adnanabouelenein.todolist.R;

import java.util.ArrayList;

public class DailyPlanAdapter extends RecyclerView.Adapter<DailyPlanAdapter.DailyPlanViewHolder> {

    private Activity activity;
    private ArrayList<String> taskTitle, taskBrief, taskTime;


    public DailyPlanAdapter(Activity activity, ArrayList<String> taskTitle, ArrayList<String> taskBrief, ArrayList<String> taskTime) {
        this.activity = activity;
        this.taskTitle = taskTitle;
        this.taskBrief = taskBrief;
        this.taskTime = taskTime;
    }

    @NonNull
    @Override
    public DailyPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.card_view_plan, parent, false);
        DailyPlanViewHolder dailyPlanViewHolder = new DailyPlanViewHolder(view);
        return dailyPlanViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DailyPlanViewHolder holder, int position) {
        holder.taskTimeText.setText(String.valueOf(taskTime.get(position)));
        holder.taskBriefText.setText(String.valueOf(taskBrief.get(position)));
        holder.taskTitleText.setText(String.valueOf(taskTitle.get(position)));
        holder.editTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("taskTitle", taskTitle.get(position));
                bundle.putString("taskBrief", taskBrief.get(position));
                bundle.putString("taskTime", taskTime.get(position));
                Navigation.findNavController(v).navigate(R.id.action_dailyPlanFragment_to_createPlanFragment,bundle);

            }
        });

    }

    @Override
    public int getItemCount() {
        return taskTitle.size();
    }

    public class DailyPlanViewHolder extends RecyclerView.ViewHolder {
        private ImageButton notificationButton, editTaskButton;
        public TextView taskTitleText, taskBriefText, taskTimeText;
        public DailyPlanViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationButton = itemView.findViewById(R.id.notification_btn);
            editTaskButton = itemView.findViewById(R.id.edit_task_btn);
            taskTitleText = itemView.findViewById(R.id.task_title);
            taskBriefText = itemView.findViewById(R.id.task_brief);
            taskTimeText = itemView.findViewById(R.id.task_time_daily_tasks);
        }
    }
}
