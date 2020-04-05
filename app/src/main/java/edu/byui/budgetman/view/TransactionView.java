package edu.byui.budgetman.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.util.ArrayList;

import edu.byui.budgetman.R;
import edu.byui.budgetman.model.Category;
import edu.byui.budgetman.model.Transaction;
import edu.byui.budgetman.model.Budget;
import edu.byui.budgetman.control.BudgetControl;

public class TransactionView extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // private member variables

    TextView input;
    TextView input2;

    // loads the view

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_transaction_view);

    }

    public void getTransaction(View view) {


    	// This variables are for the method more than the class
    	// keeping them in here makes it more testable and possibly reusable
        String category;
        String amountT;
        Spinner categories = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.possible_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories.setAdapter(adapter);
        categories.setOnItemSelectedListener(this);



        // grabs the user entered text and converts them into strings

        //input = (TextView) findViewById(R.id.editText5);
        //category = input.getText().toString();

        input2 = (TextView) findViewById(R.id.editText4);
        amountT = input2.getText().toString();

        // converting the string to a bidDecimal

        BigDecimal amount = new BigDecimal(amountT);

        // grabbing the current months budget

        Budget budget = BudgetControl.getCurrentMonthBudget();

        for (int i = 0; i < budget.getCategories().size(); i++) {
            categories.getAdapter().getItem()
        }

        // The get category by name, gets the category, or returns null if not found ...
        Category cate = budget.getCategoryByName("Food");

        // If the category doesn't exist create a new one with the transaction amount
        // and add it to the budget
        if (cate == null) {
            cate = new Category("food", amount, new ArrayList<Transaction>());
            budget.getCategories().add(cate);
        }

		// Then just add the transaction to the category
        cate.getTransactions().add(new Transaction(amount, null));

        // save the changes

        BudgetControl.saveCurrentMonthBudget();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}