package com.adnanabouelenein.todolist.frgaments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.adnanabouelenein.todolist.R;
import com.adnanabouelenein.todolist.data.sqlitedatabase.DataBaseHelper;

public class CreatePlanFragment extends Fragment implements View.OnClickListener {


    public CreatePlanFragment() {
        // Required empty public constructor
    }

    private View view;
    private Button timePickerBtn, datePickerBtn, deleteTextBtn, categoryBtn, completePlanBtn;
    private int YEAR, MONTH, day, hour, min, id;
    private EditText planContent, planTitle;
    private String  category;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        view = inflater.inflate(R.layout.fragment_create_plan, container, false);
        timePickerBtn = view.findViewById(R.id.time_btn);
        datePickerBtn = view.findViewById(R.id.date_btn);
        deleteTextBtn = view.findViewById(R.id.delete_text_btn);
        categoryBtn = view.findViewById(R.id.category_btn);
        completePlanBtn = view.findViewById(R.id.complete_plan_btn);

        planContent = view.findViewById(R.id.plan_details_create_plan_activity);
        planTitle = view.findViewById(R.id.plan_title);


        timePickerBtn.setOnClickListener(this);
        datePickerBtn.setOnClickListener(this);
        deleteTextBtn.setOnClickListener(this);
        categoryBtn.setOnClickListener(this);
        completePlanBtn.setOnClickListener(this);

        Bundle bundle = getArguments();

        if (bundle != null){
            planTitle.setText(bundle.getString("taskTitle"));
            planContent.setText(bundle.getString("taskBrief"));
            timePickerBtn.setText(bundle.getString("taskTime"));
        }

        return view;
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        ContentValues values = new ContentValues();
        DataBaseHelper dataBaseHelper;
        SQLiteDatabase sql;


        switch (v.getId()) {
            case R.id.date_btn:
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                YEAR = year;
                                MONTH = month + 1;
                                day = dayOfMonth;

                                datePickerBtn.setText(day + "/" + MONTH + "/" + YEAR);
                            }
                        }, YEAR, MONTH, day);

                datePickerDialog.show();
//                DateAndTimeDialog dateDialog = new DateAndTimeDialog();
//                dateDialog.setCancelable(false);
//                dateDialog.show(getChildFragmentManager(),"dateDialog");

                break;

            case R.id.time_btn:
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                hour = hourOfDay;
                                min = minute;
                                timePickerBtn.setText(hour + ":" + min);

                            }
                        }, hour, min, false);
                timePickerDialog.show();
                break;

            case R.id.delete_text_btn:
                planTitle.getText().clear();
                planContent.getText().clear();
                dataBaseHelper = new DataBaseHelper(getActivity());
                sql = dataBaseHelper.getWritableDatabase();
                sql.delete("ToDoList", "_id == ?", new String[]{String.valueOf(id)});
                    Toast.makeText(getActivity(), "Task is Deleted", Toast.LENGTH_SHORT).show();
                    timePickerBtn.setEnabled(false);
                    datePickerBtn.setEnabled(false);
                    deleteTextBtn.setEnabled(false);
                    categoryBtn.setEnabled(false);
                    completePlanBtn.setEnabled(false);

                break;

            case R.id.category_btn:
                PopupMenu popupMenu = new PopupMenu(getActivity(), categoryBtn);
                popupMenu.getMenuInflater().inflate(R.menu.categories_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(item -> {
                    categoryBtn.setText(item.getTitle());
                    category = String.valueOf(item.getTitle());

                    return true;
                });

                popupMenu.show();
                break;

            case R.id.complete_plan_btn:
                String taskName = planTitle.getText().toString();
                String taskContent = planContent.getText().toString();
                values.put("taskTitle", taskName);
                values.put("taskContent", taskContent);
                values.put("time", hour + ":" + min);
                values.put("date", day + "/" + MONTH + "/" + YEAR);
                values.put("category", category);


                dataBaseHelper = new DataBaseHelper(getActivity());
                sql = dataBaseHelper.getWritableDatabase();

                long id = sql.insert("ToDoList", null, values);


                if (id != -1)
                    Toast.makeText(getActivity(), "Data saved", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), "Data didn't saved", Toast.LENGTH_SHORT).
                            show();

                Navigation.findNavController(v).navigate(R.id.action_createPlanFragment_to_dailyPlanFragment);
                break;
        }


    }
}