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

    // private memeber variables

    TextView input;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_category_view);
    }

    public void removeCategory(View view) {

        // These variables are for the method more than the class
        // keeping them in here makes it more testable and possibly reusable
        String category;
        String amountT;

        // getting the text from the user and placing it into a string

        input = (TextView) findViewById(R.id.editText6);
        category = input.getText().toString();

        // getting the current budget

        Budget budget = BudgetControl.getCurrentMonthBudget();

        // checking if the category exists within all entered categories

        Category cat = budget.getCategoryByName(category);

        // looping through all the categories within the budget and checking where it is

        for (int i = 0; i < budget.getCategories().size(); i++) {
            if (budget.getCategories().get(i) == cat) {
                budget.getCategories().remove(i);
            }
        }

        // if it existed we delete it if it didn't we do not have to delete it
        // saving the changes

        BudgetControl.saveCurrentMonthBudget();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
