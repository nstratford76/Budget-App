package edu.byui.budgetman.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import edu.byui.budgetman.R;
import edu.byui.budgetman.model.Category;

import edu.byui.budgetman.model.Transaction;

public class CategoryView extends AppCompatActivity {

    TextView input;
    TextView input2;
    String category;
    String amountText;
    Category cat;

    // This Map will keep track of the categories and pair them up

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);
    }

    public void getCategory(View view) {


        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        input = (TextView) findViewById(R.id.editText);
        category = input.getText().toString();

        input2 = (TextView) findViewById(R.id.editText2);
        amountText = input.getText().toString();

        Float amount = Float.parseFloat(amountText);
        Transaction transaction = new Transaction(amount, category);
        transactions.add(transaction);
        cat.setTransactions(transactions);
        input.setText("");
        input2.setText("");
    }
}
