package edu.byui.budgetman.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;

import edu.byui.budgetman.R;
import edu.byui.budgetman.control.BudgetControl;
import edu.byui.budgetman.control.MockingControl;
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

        // Temporal
        MockingControl.fillBudgetWithMockData();


    }


}
