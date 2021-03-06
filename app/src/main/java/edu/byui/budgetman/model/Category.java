package edu.byui.budgetman.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/** Model class representing the notiono of a category in a budget */
public class Category {

    private int sqlId;
    private String name;
    private BigDecimal budgetedAmount;
    private List<Transaction> transactions;

    /**
     * Non default constructor. Only this data is required for the reation of a
     * Category
     */
    public Category(String name, BigDecimal budgetedAmount, List<Transaction> transactions) {
        this.name = name;
        this.budgetedAmount = budgetedAmount;
        this.transactions = transactions;
    }

    public int getSqlId() {
        return sqlId;
    }

    /** WARNING to be used only by the Model API */
    public void setSqlId(int sqlId) {
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

    public void addTransaction(BigDecimal amount) {
        Transaction newTransaction = new Transaction(amount, null);
        transactions.add(newTransaction);
    }

    /**
     * Helper method to get the sum of all the transactions whether those make for a
     * positive or negative result. Negative result means there is money extra in
     * the category
     */
    public BigDecimal getTransactionsSum() {

        BigDecimal categorySum = new BigDecimal(0);

        for (Transaction transaction : transactions) {

            categorySum = categorySum.add(transaction.getAmount());

        }

        return categorySum;
    }

    /** Helper class to compare categories */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Category category = (Category) o;
        return name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
