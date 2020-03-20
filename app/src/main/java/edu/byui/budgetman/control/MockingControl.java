package edu.byui.budgetman.control;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.byui.budgetman.model.Budget;
import edu.byui.budgetman.model.Category;
import edu.byui.budgetman.model.Transaction;

public class MockingControl {
    public static void fillBudgetWithMockData() {

        Budget budget = BudgetControl.getCurrentMonthBudget();

        // Showing the existing budget on screen
        BudgetControl.printCurrentMonthBudget();

        // Setting some income to 10,000
        budget.setIncome(new BigDecimal("10000"));

        // Getting hold of the categories reference
        Set<Category> categories = budget.getCategories();


        // Clearing Budget Transactional Data if that exists
        categories.clear();

        categories.add(new Category("Food", new BigDecimal("2000"), new ArrayList<Transaction>()));

        categories.add(new Category("Transportation", new BigDecimal("500"), new ArrayList<Transaction>()));

        categories.add(new Category("Movies", new BigDecimal("700"), new ArrayList<Transaction>()));


        Category foodCategory = budget.getCategoryByName("Food");
        if (foodCategory != null) {
            List<Transaction> foodTransactions = foodCategory.getTransactions();

            foodTransactions.add(new Transaction(new BigDecimal("13.50"), "Subway Sandwich"));

            foodTransactions.add(new Transaction(new BigDecimal("13.50"), "Subway Sandwich"));

            foodTransactions.add(new Transaction(new BigDecimal("13.50"), "Subway Sandwich"));

            foodTransactions.add(new Transaction(new BigDecimal("13.50"), "Subway Sandwich"));

            // TODO keep on building the mock
        }


    }
}
