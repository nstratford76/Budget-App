package edu.byui.budgetman.view;

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

    public void getIncome(View view) {

        Budget budget = BudgetControl.getCurrentMonthBudget();
        input = (TextView) findViewById(R.id.editText3);
        incomeText = input.getText().toString();
        BigDecimal income = new BigDecimal(incomeText);

        budget.setIncome(income);
        BudgetControl.saveCurrentMonthBudget();

    }
}
