package edu.byui.budgetman.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.util.ArrayList;

import edu.byui.budgetman.R;
import edu.byui.budgetman.control.BudgetControl;
import edu.byui.budgetman.model.Budget;
import edu.byui.budgetman.model.Category;
import edu.byui.budgetman.model.Transaction;

public class RemoveCategoryView extends AppCompatActivity {

    TextView input;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_category_view);
    }

    public void removeCategory(View view) {

        // This variables are for the method more than the class
        // keeping them in here makes it more testable and possibly reusable
        String category;
        String amountT;

        input = (TextView) findViewById(R.id.editText6);
        category = input.getText().toString();

        Budget budget = BudgetControl.getCurrentMonthBudget();
        Category cat = budget.getCategoryByName(category);

        for (int i = 0; i < budget.getCategories().size(); i++) {
            if (budget.getCategories().get(i) == cat) {
                budget.getCategories().remove(i);
            }
        }

        BudgetControl.saveCurrentMonthBudget();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
