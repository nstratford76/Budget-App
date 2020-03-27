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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_transaction_view);

    }

    public void getTransaction(View view) {

    	// This variables are for the method more than the class
    	// keeping them in here makes it more testable and possibly reusable
        String category;
        String amountT;

        input = (TextView) findViewById(R.id.editText5);
        category = input.getText().toString();

        input2 = (TextView) findViewById(R.id.editText4);
        amountT = input2.getText().toString();

        BigDecimal amount = new BigDecimal(amountT);

        Budget budget = BudgetControl.getCurrentMonthBudget();

        // The get category by name, gets the category, or returns null if not found ...
        Category cate = budget.getCategoryByName(category);

        // If the category doesn't exist create a new one with the transaction amount
        // and add it to the budget
        if (cate == null) {
            cate = new Category(category, amount, new ArrayList<Transaction>());
            budget.getCategories().add(cate);
        }

		// Then just add the transaction to the category
        cate.getTransactions().add(new Transaction(amount, null));

        BudgetControl.saveCurrentMonthBudget();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}