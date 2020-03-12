package edu.byui.budgetman.model;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Budget {

    // only getter methods provide for this variables since those shouldn't be updated by anything in the program
    private final int SQL_BUDGET_ID;
    private int month;

    private BigDecimal income;
    private ArrayList<Category> categories;


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

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
}
