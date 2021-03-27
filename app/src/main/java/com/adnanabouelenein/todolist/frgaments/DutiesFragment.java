package com.adnanabouelenein.todolist.frgaments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adnanabouelenein.todolist.R;
import com.adnanabouelenein.todolist.data.DutiesPlan.DutiesPlanHorizontalAdapter;
import com.adnanabouelenein.todolist.data.DutiesPlan.DutiesPlanHorizontalModel;
import com.adnanabouelenein.todolist.data.sqlitedatabase.DataBaseHelper;
import com.adnanabouelenein.todolist.data.weeklyplan.WeeklyPlanAdapter;

import java.util.ArrayList;

public class DutiesFragment extends Fragment {

    public DutiesFragment() {
        // Required empty public constructor
    }

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_duties, container, false);
        showCategories();
        showWeeklyTasks();
        return view;
    }

    private void showCategories() {
        ArrayList<DutiesPlanHorizontalModel> dutiesPlanHorizontalModels = new ArrayList<>();
        dutiesPlanHorizontalModels.add(new DutiesPlanHorizontalModel(R.drawable.shopping_cart,
                10, "Shopping"));
        dutiesPlanHorizontalModels.add(new DutiesPlanHorizontalModel(R.drawable.work,
                7, "Work"));
        dutiesPlanHorizontalModels.add(new DutiesPlanHorizontalModel(R.drawable.sports,
                5, "Sports"));
        dutiesPlanHorizontalModels.add(new DutiesPlanHorizontalModel(R.drawable.hobbies,
                10, "Hobbies"));

        RecyclerView recyclerView = view.findViewById(R.id.rv_categories);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),
                RecyclerView.HORIZONTAL, false);
        DutiesPlanHorizontalAdapter adapter = new DutiesPlanHorizontalAdapter(getActivity(),
                dutiesPlanHorizontalModels);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new DutiesPlanHorizontalAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                String categoryNameInSqliteDatabase = dutiesPlanHorizontalModels.get(position).getCategoryName();
                bundle.putString("categoryName", categoryNameInSqliteDatabase);
                Navigation.findNavController(view).navigate(R.id.action_dutiesFragment_to_weeklyPlanFragment, bundle);
            }
        });
    }

    private void showWeeklyTasks() {
            DataBaseHelper db = new DataBaseHelper(getActivity());
            SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();

            String query = "SELECT * FROM ToDoList";
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

    private void showRecyclerView(ArrayList<String> titleText, ArrayList<String> dateText,
                                  ArrayList<String> timeText){
        RecyclerView recyclerView = view.findViewById(R.id.rv_my_tasks);
        LinearLayoutManager manager =  new LinearLayoutManager(getActivity());
        WeeklyPlanAdapter adapter = new WeeklyPlanAdapter(getActivity(),titleText, dateText, timeText);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }
}