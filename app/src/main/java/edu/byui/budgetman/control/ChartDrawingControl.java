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

            // We are checking here because a category could have transactions with amounts of 0
            // so only avoid to display those for which the sum totals to 0 or for which the sum
            // results in a negative number ... if that happens, those will add up to the income in the graph ..
            // in the line after the following "if"
            // because that means there is even more money available from that category
            if (categorySum.compareTo(new BigDecimal(0)) > 0) {
                pieChartEntries.add(new PieEntry(categorySum.floatValue(), category.getName()));
            }
            incomeLeft = incomeLeft.subtract(categorySum);

            // todolaterinameeting: add android:inputType="numberDecimal|numberSigned" to the amount field in
            // the transactions amount input, to allow for signed-decimal transactions (allowing so to have refunds
            // or money added to the categories)
        }


        if (incomeLeft.doubleValue() > 0)
            pieChartEntries.add(new PieEntry(incomeLeft.floatValue(), "INCOME LEFT"));


        return pieChartEntries;
    }

}
