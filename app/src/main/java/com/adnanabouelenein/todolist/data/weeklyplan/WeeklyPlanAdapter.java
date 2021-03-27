package com.adnanabouelenein.todolist.data.weeklyplan;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adnanabouelenein.todolist.R;
import com.adnanabouelenein.todolist.data.sqlitedatabase.DataBaseHelper;

import java.util.ArrayList;

public class WeeklyPlanAdapter extends RecyclerView.Adapter<WeeklyPlanAdapter.WeeklyPLanViewHolder> {

    private Activity activity;
    private ArrayList<String> titleText, dateText, timeText;
    private int id;

    public WeeklyPlanAdapter(Activity activity, ArrayList<String> titleText,
                             ArrayList<String> dateText, ArrayList<String> timeText) {
        this.activity = activity;
        this.titleText = titleText;
        this.dateText = dateText;
        this.timeText = timeText;
    }

    @NonNull
    @Override
    public WeeklyPlanAdapter.WeeklyPLanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View v = inflater.inflate(R.layout.card_view_periodic_tasks, parent, false);
        WeeklyPLanViewHolder weeklyPLanViewHolder = new WeeklyPLanViewHolder(v);
        return weeklyPLanViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeeklyPlanAdapter.WeeklyPLanViewHolder holder, int position) {
        holder.timeText.setText(timeText.get(position));
        holder.dateText.setText(dateText.get(position));
        holder.titleText.setText(titleText.get(position));
        holder.taskDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.taskDoneButton.setBackgroundResource(R.drawable.oval);
                holder.titleText.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);



                    DataBaseHelper helper = new DataBaseHelper(activity);
                    SQLiteDatabase db = helper.getWritableDatabase();
                    String[] args = {String.valueOf(titleText.get(position))};
                    int deletedRows = db.delete("ToDoList", "_id==?", args);
                    if (deletedRows > 0)
                        Toast.makeText(activity, "note deeleted", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return titleText.size();
    }

    public class WeeklyPLanViewHolder extends RecyclerView.ViewHolder {

        private Button taskDoneButton;
        private TextView titleText, dateText, timeText;

        public WeeklyPLanViewHolder(@NonNull View itemView) {
            super(itemView);

            taskDoneButton = itemView.findViewById(R.id.checkbox);
            titleText = itemView.findViewById(R.id.task_name);
            dateText = itemView.findViewById(R.id.task_day);
            timeText = itemView.findViewById(R.id.task_time);
        }
    }
}
