package edu.byui.budgetman.model;

/**
 * Exception when trying to save a current month budget without first knowing if
 * it at least exists
 */
public class NonInitializedBudgetException extends Exception {

    public NonInitializedBudgetException(String message) {
        super(message);
    }
}
