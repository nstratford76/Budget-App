package edu.byui.budgetman.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.byui.budgetman.R;
import edu.byui.budgetman.control.BudgetControl;
import edu.byui.budgetman.model.Budget;
import edu.byui.budgetman.model.Category;
import edu.byui.budgetman.model.Transaction;

public class CategoryView extends AppCompatActivity {

    TextView input;
    TextView input2;
    String category;
    String amountText;


    List<Transaction> transactions = new ArrayList<Transaction>();

    // This Map will keep track of the categories and pair them up



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);
    }

    public void getCategory(View v) {

        input = (TextView) findViewById(R.id.editText);
        category = input.getText().toString();

        input2 = (TextView) findViewById(R.id.editText2);
        amountText = input.getText().toString();

        BigDecimal amount = new BigDecimal(amountText);
        Category cat = new Category(category, amount, transactions);
        Budget budget = BudgetControl.getCurrentMonthBudget();
        Set<Category> categories = budget.getCategories();
        categories.add(cat);
        BudgetControl.saveCurrentMonthBudget();
        input.setText("");
        input2.setText("");
    }
}
