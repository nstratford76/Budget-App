package edu.byui.budgetman.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.util.ArrayList;

import edu.byui.budgetman.R;
import edu.byui.budgetman.model.Budget;
import edu.byui.budgetman.model.Category;
import edu.byui.budgetman.model.Transaction;

import static edu.byui.budgetman.control.BudgetControl.getCurrentMonthBudget;
import static edu.byui.budgetman.control.BudgetControl.saveCurrentMonthBudget;

public class CategoryView extends AppCompatActivity {

    TextView input;
    TextView input2;
    String category;
    String amountText;


    // This Map will keep track of the categories and pair them up

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);
    }

    public void getCategory(View v) {

        input = (TextView) findViewById(R.id.editText);
        category = input.getText().toString();

        input2 = (TextView) findViewById(R.id.editText2);
        amountText = input2.getText().toString();

        BigDecimal amount = new BigDecimal(amountText);

        Budget budget = getCurrentMonthBudget();
        ArrayList<Category> categories = budget.getCategories();

        if (budget.getCategoryByName(category) != null) {
            Category cat = budget.getCategoryByName(category);
            cat.setBudgetedAmount(amount);
        }
        else {
            Category cat = new Category(category, amount, new ArrayList<Transaction>());
            categories.add(cat);
        }
        saveCurrentMonthBudget();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
