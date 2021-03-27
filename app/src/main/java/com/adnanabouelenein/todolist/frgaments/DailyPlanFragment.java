package com.adnanabouelenein.todolist.frgaments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adnanabouelenein.todolist.R;
import com.adnanabouelenein.todolist.data.dailyplan.DailyPlanAdapter;
import com.adnanabouelenein.todolist.data.sqlitedatabase.DataBaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DailyPlanFragment extends Fragment {

    private View view;
    private String daily = "Daily";

    public DailyPlanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_daily_plan, container, false);
        getData();
        currentDate();
        return view;
    }

    public String currentDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String todayDate = simpleDateFormat.format(new Date());
        TextView date = view.findViewById(R.id.date_daily_text);
        date.setText(todayDate);
        return todayDate;
    }



    private void getData() {
        DataBaseHelper db = new DataBaseHelper(getActivity());
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();

        String query = "SELECT * FROM ToDoList WHERE category = '"+ daily + "'";
        Log.d("date", currentDate());

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        final ArrayList<String> taskTitle = new ArrayList<>();
        final ArrayList<String> taskBrief = new ArrayList<>();
        final ArrayList<String> taskTime = new ArrayList<>();

        while (cursor.moveToNext()) {
            taskTitle.add(cursor.getString(1));
            taskBrief.add(cursor.getString(2));
            taskTime.add(cursor.getString(3));
        }
        showRecyclerView(taskTitle, taskBrief, taskTime);
    }

    private void showRecyclerView(ArrayList<String> taskTitle, ArrayList<String> taskBrief,
                                  ArrayList<String> taskTime) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_daily_plan);
        DailyPlanAdapter adapter = new DailyPlanAdapter(getActivity(), taskTitle, taskBrief, taskTime);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

    }


}