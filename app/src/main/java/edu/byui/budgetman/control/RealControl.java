package edu.byui.budgetman.control;

import java.util.ArrayList;

import edu.byui.budgetman.model.Budget;
import edu.byui.budgetman.model.Category;

public class RealControl {
    /**
     * Fill budget with the data that it needs (this was also for testing, doing
     * nothing right now)
     */
    public static void fillBudgetWithData() {

        Budget budget = BudgetControl.getCurrentMonthBudget();
        ArrayList<Category> categories = budget.getCategories();
        BudgetControl.printCurrentMonthBudget();
    }

    /**
     * Resets the budget so that the user can start from scratch Clear everything
     */
    public static void resetBudget() {
        Budget budget = BudgetControl.getCurrentMonthBudget();
        ArrayList<Category> categories = budget.getCategories();
        categories.clear();
        budget.setIncome(null);
    }
}
