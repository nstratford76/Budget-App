package edu.byui.budgetman.model;

import java.math.BigDecimal;
import java.util.Date;


public class Transaction {

    // Only getter method will be provided for this since it will always default to current date
    // from the constructor
    private Date date; // rolled backwards to Date instead of LocalDate for API compatibility
    private BigDecimal amount;
    private String description;

    public Transaction(BigDecimal amount, String description) {

        // When a Transaction is created it defaults its date to the current date
        this.date = new Date();
        this.amount = amount;
        this.description = description;

    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
