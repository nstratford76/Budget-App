package edu.byui.budgetman.model;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Class representing the notion of a transaction of a category within a budget
 */
public class Transaction {

    public static final DateFormat TRANSACTION_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    // Only getter method will be provided for this since it will always default to
    // current date
    // from the constructor
    private Date date; // rolled backwards to Date instead of LocalDate for API compatibility
    private BigDecimal amount;
    private String description;

    /** Transactions are stored with a time stamp of their creation time */
    public Transaction(BigDecimal amount, String description) {

        // When a Transaction is created it defaults its date to the current date
        this.date = new Date();
        this.amount = amount;
        this.description = description == null ? "" : description;

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        this.description = description == null ? "" : description;
    }
}
