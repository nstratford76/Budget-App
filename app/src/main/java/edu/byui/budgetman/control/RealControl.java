package edu.byui.budgetman.control;

import java.util.Set;

import edu.byui.budgetman.model.Budget;
import edu.byui.budgetman.model.Category;

public class RealControl {
    public static void fillBudgetWithData() {

        Budget budget = BudgetControl.getCurrentMonthBudget();
        Set<Category> categories = budget.getCategories();
        BudgetControl.printCurrentMonthBudget();
    }

    public static void resetBudget() {
        Budget budget = BudgetControl.getCurrentMonthBudget();
        Set<Category> categories = budget.getCategories();
        categories.clear();
        budget.setIncome(null);
    }
}
