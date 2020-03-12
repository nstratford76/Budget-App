package edu.byui.budgetman.model;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Budget {

    private BigDecimal income;
    private int month;
    private ArrayList<Category> categories;


    public Budget(BigDecimal income, int month, ArrayList<Category> categories) {
        this.income = income;
        this.month = month;
        this.categories = categories;
    }


    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }


    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
}
