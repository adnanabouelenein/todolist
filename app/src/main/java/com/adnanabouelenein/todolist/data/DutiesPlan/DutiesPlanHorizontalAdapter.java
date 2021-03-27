package com.adnanabouelenein.todolist.data.DutiesPlan;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adnanabouelenein.todolist.R;

import java.util.ArrayList;

public class DutiesPlanHorizontalAdapter extends RecyclerView.Adapter<DutiesPlanHorizontalAdapter.DutiesPlanViewHolder> {

    private Activity activity;
    private ArrayList<DutiesPlanHorizontalModel> dutiesPlanHorizontalModels;
    private OnItemClick onItemClick;
    public void setOnItemClickListener(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void onItemClick(int position);
    }

    public DutiesPlanHorizontalAdapter(Activity activity, ArrayList<DutiesPlanHorizontalModel> dutiesPlanHorizontalModels) {
        this.activity = activity;
        this.dutiesPlanHorizontalModels = dutiesPlanHorizontalModels;
    }

    @NonNull
    @Override
    public DutiesPlanHorizontalAdapter.DutiesPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View v = inflater.inflate(R.layout.card_view_weekly_plan_horizontal, parent, false);
        DutiesPlanViewHolder dutiesPlanViewHolder = new DutiesPlanViewHolder(v,onItemClick);
        return dutiesPlanViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DutiesPlanHorizontalAdapter.DutiesPlanViewHolder holder, int position) {
        holder.categoryIcon.setImageResource(dutiesPlanHorizontalModels.get(position).getCategoryImage());
        holder.categoryName.setText(dutiesPlanHorizontalModels.get(position).getCategoryName());
        holder.categoryItemsCount.setText(String.valueOf(dutiesPlanHorizontalModels.get(position).getNumberOfTasks() + " items"));
    }

    @Override
    public int getItemCount() {
        return dutiesPlanHorizontalModels.size();
    }

    public class DutiesPlanViewHolder extends RecyclerView.ViewHolder {
        private ImageView categoryIcon;
        private TextView categoryName, categoryItemsCount;
        public DutiesPlanViewHolder(@NonNull View itemView, OnItemClick listener) {
            super(itemView);
            categoryIcon = itemView.findViewById(R.id.category_icon);
            categoryName = itemView.findViewById(R.id.category_name);
            categoryItemsCount = itemView.findViewById(R.id.category_items_count);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
