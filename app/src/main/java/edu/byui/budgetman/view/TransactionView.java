package edu.byui.budgetman.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.util.ArrayList;

import edu.byui.budgetman.R;
import edu.byui.budgetman.model.Category;
import edu.byui.budgetman.model.Transaction;
import edu.byui.budgetman.model.Budget;
import edu.byui.budgetman.control.BudgetControl;

public class TransactionView extends AppCompatActivity {
    TextView input;
    TextView input2;
    String category;
    String amountT;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_transaction_view);

    }

    public void getTransaction(View view) {
        input = (TextView)findViewById(R.id.editText3);
        category = input.getText().toString();

        input2 = (TextView)findViewById(R.id.editText4);
        amountT = input2.getText().toString();

        BigDecimal amount = new BigDecimal(amountT);

        Budget budget = BudgetControl.getCurrentMonthBudget();
        ArrayList<Category> categories = budget.getCategories();

        int i = 0;

        for (; i < categories.size(); i++) {
            if (budget.getCategoryByName(categories.get(i).getName()).equals(category)) {
                Category cat = budget.getCategoryByName(category);
                cat.addTransaction(amount);
            }
        }

        if (i == categories.size()) {
            Category cat = new Category(input2.toString(), amount, new ArrayList<Transaction>());
            cat.addTransaction(amount);
            categories.add(cat);
        }

        BudgetControl.saveCurrentMonthBudget();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}