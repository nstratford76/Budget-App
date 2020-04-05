package edu.byui.budgetman.model;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Budget {

    // only getter methods provide for this variables since those shouldn't be
    // updated by anything in the program
    private final int SQL_BUDGET_ID;
    private final int month;

    private BigDecimal income;

    // EXISTS BEFORE INSERTING A NEW ONE
    private ArrayList<Category> categories;

    /**
     * Budget constructor, the budgetid is only filled by the Model API, users of
     * the class for control and view logic shouldnt be setting the budgetid value
     */
    public Budget(int budgetid, BigDecimal income, int month, ArrayList<Category> categories) {
        SQL_BUDGET_ID = budgetid;
        this.month = month;
        this.income = income;
        this.categories = categories;
    }

    public int getSQL_BUDGET_ID() {
        return SQL_BUDGET_ID;
    }

    public int getMonth() {
        return month;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    /**
     * Returns the category with the requested name for the calling Budget, or null
     * if not found
     */
    public Category getCategoryByName(String categoryName) {
        if (categoryName == null) {
            return null;
        } else {

            // Traversing the category list searching for the required category
            for (Category currentCategory : categories) {

                if (currentCategory.getName().equals(categoryName)) {
                    return currentCategory;
                }
            }
        }
        return null;
    }

}
