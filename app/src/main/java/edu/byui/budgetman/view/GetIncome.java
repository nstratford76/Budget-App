package edu.byui.budgetman.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;

import edu.byui.budgetman.R;
import edu.byui.budgetman.control.BudgetControl;
import edu.byui.budgetman.model.Budget;

public class GetIncome extends AppCompatActivity {

    TextView input;
    String incomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_income);
    }

    // Gets the user's monthly income and passes in the view
    public void getIncome(View v) {

        // Convert input into string and then to BigDecimal
        Budget budget = BudgetControl.getCurrentMonthBudget();
        input = (TextView) findViewById(R.id.editText3);
        incomeText = input.getText().toString();
        BigDecimal income = new BigDecimal(incomeText);

        // Set income to budget and save it, move to main activity

        budget.setIncome(income);
        BudgetControl.saveCurrentMonthBudget();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
