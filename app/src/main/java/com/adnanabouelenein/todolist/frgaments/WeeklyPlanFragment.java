package com.adnanabouelenein.todolist.frgaments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adnanabouelenein.todolist.R;
import com.adnanabouelenein.todolist.data.sqlitedatabase.DataBaseHelper;
import com.adnanabouelenein.todolist.data.weeklyplan.WeeklyPlanAdapter;

import java.util.ArrayList;

public class WeeklyPlanFragment extends Fragment {


    public WeeklyPlanFragment() {
        // Required empty public constructor
    }

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_weekly_plan, container, false);

        showWeeklyTasks();
        return view;
    }

    private void showWeeklyTasks() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String categoryName = bundle.getString("categoryName");

            DataBaseHelper db = new DataBaseHelper(getActivity());
            SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();

            String query = "SELECT * FROM ToDoList WHERE category = '" + categoryName + "'";
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);

            final ArrayList<String> taskTitle = new ArrayList<>();
            final ArrayList<String> taskTime = new ArrayList<>();
            final ArrayList<String> taskDate = new ArrayList<>();

            while (cursor.moveToNext()) {
                taskTitle.add(cursor.getString(1));
                taskDate.add(cursor.getString(4));
                taskTime.add(cursor.getString(3));
            }
            showRecyclerView(taskTitle, taskDate, taskTime);
        }
    }

    private void showRecyclerView(ArrayList<String> titleText, ArrayList<String> dateText,
                                  ArrayList<String> timeText){
        RecyclerView recyclerView = view.findViewById(R.id.weekly_plan_recycler_view);
        LinearLayoutManager manager =  new LinearLayoutManager(getActivity());
        WeeklyPlanAdapter adapter = new WeeklyPlanAdapter(getActivity(),titleText, dateText, timeText);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

}