package edu.byui.budgetman.control;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.byui.budgetman.model.Budget;
import edu.byui.budgetman.model.ModelAPI;
import edu.byui.budgetman.model.NonInitializedBudgetException;

/**
 * This class is in charge of providing the only currentMonthBudget object that
 * needs to be in execution of the app
 */
public class BudgetControl {

    private static Context mainActivityContext;

    // Setting the context for this class to be able to deal with the ModelAPI
    public static void setContext(Context context) {
        if (mainActivityContext == null) {
            mainActivityContext = context;
        }
    }

    /** Requesting the current month budget from the model API */
    public static Budget getCurrentMonthBudget() {

        ModelAPI modelAPI = new ModelAPI(mainActivityContext);

        return modelAPI.getCurrentMonthBudget();

    }

    /** Saving the current month budget for the API */
    public static void saveCurrentMonthBudget() {
        ModelAPI modelAPI = new ModelAPI(mainActivityContext);

        try {
            modelAPI.saveAppBudgetChanges();
        } catch (NonInitializedBudgetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Printing the contents of the budget as Json to the console for debugging
     * purposes
     */
    public static void printCurrentMonthBudget() {

        ModelAPI modelAPI = new ModelAPI(mainActivityContext);

        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(modelAPI.getCurrentMonthBudget()));

    }

}
