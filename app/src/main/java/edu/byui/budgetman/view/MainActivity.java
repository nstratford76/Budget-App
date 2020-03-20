package edu.byui.budgetman.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;

import edu.byui.budgetman.R;
import edu.byui.budgetman.control.BudgetControl;
import edu.byui.budgetman.control.ChartDrawingControl;
import edu.byui.budgetman.control.MockingControl;
import edu.byui.budgetman.model.Budget;
import edu.byui.budgetman.model.Category;
import edu.byui.budgetman.model.Transaction;

public class MainActivity extends AppCompatActivity {

    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DO NOT TOUCH THIS TWO LINES OF CODE
        // THIS LINES ENABLE THE APP TO HAVE A CURRENT MONTH BUDGET PRELOADED
        BudgetControl.setContext(this);
        BudgetControl.getCurrentMonthBudget();
        // END OF THE MENTIONED TWO LINES OF CODE

        /*******************************/


        if (BudgetControl.getCurrentMonthBudget().getIncome().compareTo(new BigDecimal("0")) == 0) {
            // TODO redirect to set income activity
        }


        // Temporal mocking filling for testing
        MockingControl.fillBudgetWithMockData();

        drawPieChart();


    }

    private void drawPieChart() {


        pieChart = (PieChart) findViewById(R.id.pieChart);
        PieDataSet pieDataSet = new PieDataSet(ChartDrawingControl.getChartDataSet(), "Budget Status");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.setData(new PieData(pieDataSet));
        pieChart.animateXY(1000, 1000);
        pieChart.invalidate();
    }


}
