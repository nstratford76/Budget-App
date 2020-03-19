package edu.byui.budgetman.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Category {

    private int sqlId;
    private String name;
    private BigDecimal budgetedAmount;
    private List<Transaction> transactions;

    public Category(String name, BigDecimal budgetedAmount, List<Transaction> transactions) {
        this.name = name;
        this.budgetedAmount = budgetedAmount;
        this.transactions = transactions;
    }

    public int getSqlId() {
        return sqlId;
    }

    public void setSqlId(int sqlId){
        this.sqlId = sqlId;
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

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
