package edu.byui.budgetman.control;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import edu.byui.budgetman.model.Budget;
import edu.byui.budgetman.model.Category;
import edu.byui.budgetman.model.Transaction;

/** Class to help with debugging and testing */
public class MockingControl {

    /** Filling the current month budget with some mocking data */
    public static void fillBudgetWithMockData() {

        Budget budget = BudgetControl.getCurrentMonthBudget();

        // Showing the existing budget on screen
        BudgetControl.printCurrentMonthBudget();

        // Setting some income to 10,000
        budget.setIncome(new BigDecimal("700"));

        // Getting hold of the categories reference
        ArrayList<Category> categories = budget.getCategories();

        // Clearing Budget Transactional Data if that exists
        categories.clear();

        // Adding categories
        categories.add(new Category("Food", new BigDecimal("2000"), new ArrayList<Transaction>()));

        categories.add(new Category("Transportation", new BigDecimal("500"), new ArrayList<Transaction>()));

        categories.add(new Category("Movies", new BigDecimal("700"), new ArrayList<Transaction>()));

        // Filling transactions for categories
        Category foodCategory = budget.getCategoryByName("Food");
        if (foodCategory != null) {
            List<Transaction> foodTransactions = foodCategory.getTransactions();

            foodTransactions.add(new Transaction(new BigDecimal("13.50"), "Subway Sandwich"));

            foodTransactions.add(new Transaction(new BigDecimal("7.25"), "Pop Corn Bag"));

            foodTransactions.add(new Transaction(new BigDecimal("20.75"), "SuperBurger Burger"));

            foodTransactions.add(new Transaction(new BigDecimal("50.23"), "Caesar Salad"));

        }

        Category transportationCategory = budget.getCategoryByName("Transportation");
        if (transportationCategory != null) {
            List<Transaction> transportTransactions = transportationCategory.getTransactions();

            transportTransactions.add(new Transaction(new BigDecimal("75.60"), "Gas Filling and storage"));

            transportTransactions.add(new Transaction(new BigDecimal("17.25"), "Flat tire fix"));

            transportTransactions.add(new Transaction(new BigDecimal("60"), "Wipers replacement"));

            transportTransactions.add(new Transaction(new BigDecimal("100.78"), "Broken WindShield Replacement"));
        }

        Category moviesCategory = budget.getCategoryByName("Movies");
        if (moviesCategory != null) {
            List<Transaction> moviesTransactions = moviesCategory.getTransactions();

            moviesTransactions.add(new Transaction(new BigDecimal("7.75"), "Disney+ monthly service"));
        }

        // Just print it to console
        BudgetControl.printCurrentMonthBudget();

    }
}
