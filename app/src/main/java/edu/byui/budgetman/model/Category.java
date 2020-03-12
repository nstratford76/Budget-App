package edu.byui.budgetman.model;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Category {

    private String name;
    private BigDecimal budgetedAmount;
    private ArrayList<Transaction> transactions;

    public Category(String name, BigDecimal budgetedAmount, ArrayList<Transaction> transactions) {
        this.name = name;
        this.budgetedAmount = budgetedAmount;
        this.transactions = transactions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBudgetedAmount() {
        return budgetedAmount;
    }

    public void setBudgetedAmount(BigDecimal budgetedAmount) {
        this.budgetedAmount = budgetedAmount;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }
}
