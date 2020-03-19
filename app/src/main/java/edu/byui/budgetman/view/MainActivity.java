package edu.byui.budgetman.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.math.BigDecimal;
import java.util.ArrayList;

import edu.byui.budgetman.R;
import edu.byui.budgetman.control.BudgetControl;
import edu.byui.budgetman.model.Budget;
import edu.byui.budgetman.model.Category;
import edu.byui.budgetman.model.Transaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DO NOT TOUCH THIS TWO LINES OF CODE
        // THIS LINES ENABLE THE APP TO HAVE A CURRENT MONTH BUDGET PRELOADED
        BudgetControl.setContext(this);
        BudgetControl.getCurrentMonthBudget();
        // END OF THE MENTIONED TWO LINES OF CODE

        /*******************************/


        BudgetControl.printCurrentMonthBudget();

        Budget budget =
                BudgetControl.getCurrentMonthBudget();

        budget.setCategories(new ArrayList<Category>());

        budget.setIncome(new BigDecimal("5300.50"));

        BudgetControl.printCurrentMonthBudget();

        Category firstCategory = new Category("myFirstCategory", new BigDecimal("250"), new ArrayList<Transaction>());

        budget.getCategories().add(firstCategory);

        firstCategory.getTransactions().add(new Transaction(new BigDecimal("22"), "I just wanted it"));

        BudgetControl.saveCurrentMonthBudget();


    }


}
