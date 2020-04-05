package edu.byui.budgetman.control;

import android.graphics.Color;

import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import edu.byui.budgetman.model.Budget;
import edu.byui.budgetman.model.Category;
import edu.byui.budgetman.model.Transaction;

/**
 * Control class in charge of dealing with the calculations required for the Pie
 * Chart to be drawn on the view
 */
public class ChartDrawingControl {

    /**
     * Fills an returns a List of Pie entries containing the required data to draw
     * the pie graph
     */
    public static List<PieEntry> getChartDataSet() {

        // Working on the current month budget
        Budget budget = BudgetControl.getCurrentMonthBudget();

        // Helper to determine income left
        BigDecimal incomeLeft = budget.getIncome();

        List<PieEntry> pieChartEntries = new ArrayList<>();

        // Getting data from each category
        for (Category category : budget.getCategories()) {

            BigDecimal categorySum = category.getTransactionsSum();

            // We are checking here because a category could have transactions with amounts
            // of 0
            // so only avoid to display those for which the sum totals to 0 or for which the
            // sum
            // results in a negative number ... if that happens, those will add up to the
            // income in the graph ..
            // in the line after the following "if"
            // because that means there is even more money available from that category
            if (categorySum.compareTo(new BigDecimal(0)) > 0) {
                pieChartEntries.add(new PieEntry(categorySum.floatValue(), category.getName()));
            }

            // Substract the sum of each category from the income
            incomeLeft = incomeLeft.subtract(categorySum);

        }

        // Add an entry for the income left if there is some
        if (incomeLeft.doubleValue() > 0)
            pieChartEntries.add(new PieEntry(incomeLeft.floatValue(), "INCOME LEFT"));

        return pieChartEntries;
    }

    /**
     * Producing random colors preserving a unique green color for the income left,
     * and reducing greenish colors for categories if the income left exists
     */
    public static List<Integer> getRandomColorsArray(int size, boolean hasIncomeLeft) {

        // Avoiding duplicated colors
        Set<Integer> colorIntegersSet = new HashSet<>();

        Random random = new Random();

        // reserving a green color for income
        int reservedGreenColor = Color.rgb(40, 150, 40);

        // Creating an array of random colors of the required size
        for (int i = 0; i < size; i++) {

            int r, g, b, color;

            do {
                // setting random level for each color channel
                r = random.nextInt(255);
                g = random.nextInt(255);
                b = random.nextInt(255);
                color = Color.rgb(r, g, b);

                // Avoiding colors tending to white and with many green if there was an income
                // or if the color already exists
            } while ((r + g + b) > 570 || (hasIncomeLeft && g > 180) || color == reservedGreenColor
                    || colorIntegersSet.contains(color));

            // Colors are set without alpha channel
            colorIntegersSet.add(Color.rgb(r, g, b));

        }

        List<Integer> colorsList = new ArrayList<>(colorIntegersSet);

        // Set a good green for the income if it has income
        if (hasIncomeLeft) {
            colorsList.set(colorsList.size() - 1, reservedGreenColor);
        }

        return colorsList;
    }
}
