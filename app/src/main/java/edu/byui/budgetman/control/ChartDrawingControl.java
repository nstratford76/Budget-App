package edu.byui.budgetman.control;

import com.github.mikephil.charting.data.PieEntry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import edu.byui.budgetman.model.Budget;
import edu.byui.budgetman.model.Category;

public class ChartDrawingControl {


    public static List<PieEntry> getChartDataSet() {

        Budget budget = BudgetControl.getCurrentMonthBudget();

        List<PieEntry> pieChartEntries = new ArrayList<>();

        BigDecimal incomeLeft = budget.getIncome();

        for (Category category : budget.getCategories()) {

            category.getTransactions()



        }


        pieChartEntries.add(new PieEntry(25f, "Some"));
        pieChartEntries.add(new PieEntry(25f, "Label"));

        pieChartEntries.add(new PieEntry(50f, "Here"));
        return pieChartEntries;
    }

}
