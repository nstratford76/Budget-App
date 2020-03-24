package edu.byui.budgetman.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;
import java.util.ArrayList;

import edu.byui.budgetman.R;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewHolder> {

    private ArrayList<Category> categories;

    public BudgetAdapter(ArrayList<Category> categories) {
        this.categories = categories;
    }


    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category cat =  categories.get(position);

        BigDecimal amount = cat.getBudgetedAmount();

        String number = amount.toString();

        holder.category.setText(cat.getName());
        holder.remaining.setText(number);

    }

    @Override
    public int getItemCount() {
        if (categories != null)
            return categories.size();
        else
           return 0;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        public TextView category;
        public TextView remaining;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.view = view;
            category = view.findViewById(R.id.category);
            remaining = view.findViewById(R.id.remaining);
        }
    }





}
