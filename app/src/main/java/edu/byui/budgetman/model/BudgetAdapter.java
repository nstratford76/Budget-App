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
import edu.byui.budgetman.control.BudgetControl;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewHolder> {

    private ArrayList<Category> categories;

    public BudgetAdapter(ArrayList<Category> categories) {
        this.categories = categories;
    }


    // Gets the view holder and allows it to keep going with more categories added
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Get current month budget
        Budget budget = BudgetControl.getCurrentMonthBudget();

        // Allocates the new position for each new category

        BigDecimal income = budget.getIncome();

        // Convert BigDecimal to string
        String mIncome = income.toString();
        Category cat = categories.get(position);

        BigDecimal amount = cat.getBudgetedAmount();

        String number = amount.toString();

        //holder.income.setText(mIncome);
        holder.category.setText(cat.getName());
        BigDecimal transactionsSum = cat.getTransactionsSum();
        
        holder.remaining.setText(number);
        // todolater ... just uncomment the following lines to allow for categories summaries
        // holder.remaining.setText("Set: " + amount + ", Spent: " + transactionsSum +
        // " . [Left: " + amount.subtract(transactionsSum) + " ]");

    }

    @Override
    public int getItemCount() {
        if (categories != null)
            return categories.size();
        else
           return 0;
    }


    // Make a holder for the recycler view, the area where the view is at
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        public final TextView category;
        public final TextView remaining;
        public final TextView income;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.view = view;
            category = view.findViewById(R.id.category);
            remaining = view.findViewById(R.id.remaining);
            income = view.findViewById(R.id.info);
        }
    }





}
