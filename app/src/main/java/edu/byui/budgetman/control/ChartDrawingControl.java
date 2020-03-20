package edu.byui.budgetman.control;

import com.github.mikephil.charting.data.PieEntry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import edu.byui.budgetman.model.Budget;
import edu.byui.budgetman.model.Category;
import edu.byui.budgetman.model.Transaction;

public class ChartDrawingControl {


    public static List<PieEntry> getChartDataSet() {

        Budget budget = BudgetControl.getCurrentMonthBudget();

        BigDecimal incomeLeft = budget.getIncome();


        List<PieEntry> pieChartEntries = new ArrayList<>();

        for (Category category : budget.getCategories()) {

            BigDecimal categorySum = new BigDecimal(0);

            List<Transaction> transactions = category.getTransactions();

            for (Transaction transaction : transactions) {

                categorySum = categorySum.add(transaction.getAmount());

            }

            pieChartEntries.add(new PieEntry(categorySum.floatValue(), category.getName()));

            incomeLeft = incomeLeft.subtract(categorySum);

        }


        if (incomeLeft.doubleValue() > 0)
            pieChartEntries.add(new PieEntry(incomeLeft.floatValue(), "INCOME LEFT"));


        return pieChartEntries;
    }

}
