package edu.byui.budgetman.control;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.byui.budgetman.model.Budget;
import edu.byui.budgetman.model.ModelAPI;
import edu.byui.budgetman.model.NonInitializedBudgetException;

public class BudgetControl {

    private static Context mainActivityContext;

    public static void setContext(Context context) {
        if (mainActivityContext == null) {
            mainActivityContext = context;
        }
    }

    public static Budget getCurrentMonthBudget() {

        ModelAPI modelAPI = new ModelAPI(mainActivityContext);


        return modelAPI.getCurrentMonthBudget();

    }

    public static void saveCurrentMonthBudget() {
        ModelAPI modelAPI = new ModelAPI(mainActivityContext);

        try {
            modelAPI.saveAppBudgetChanges();
        } catch (NonInitializedBudgetException e) {
            e.printStackTrace();
        }
    }

    public static void printCurrentMonthBudget() {

        ModelAPI modelAPI = new ModelAPI(mainActivityContext);

        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(modelAPI.getCurrentMonthBudget()));

    }

}
