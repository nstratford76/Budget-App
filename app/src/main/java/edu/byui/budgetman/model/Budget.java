package edu.byui.budgetman.model;

import java.math.BigDecimal;
import java.util.List;

public class Budget {

    // only getter methods provide for this variables since those shouldn't be updated by anything in the program
    private final int SQL_BUDGET_ID;
    private final int month;

    private BigDecimal income;
    private List<Category> categories;


    public Budget(int budgetid, BigDecimal income, int month, List<Category> categories) {
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
