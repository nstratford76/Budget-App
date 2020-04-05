package edu.byui.budgetman.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import edu.byui.budgetman.R;
import edu.byui.budgetman.control.BudgetControl;
import edu.byui.budgetman.control.ChartDrawingControl;
import edu.byui.budgetman.control.RealControl;
import edu.byui.budgetman.model.Budget;
import edu.byui.budgetman.model.BudgetAdapter;
import edu.byui.budgetman.model.Category;

/** Main class and starting point of our application */
public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private PieChart pieChart;
    private RecyclerView categories;
    private RecyclerView.Adapter adapter;

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

        // Ask the user to enter an income ammount if it equal or less than 0
        if (BudgetControl.getCurrentMonthBudget().getIncome().compareTo(new BigDecimal("0")) <= 0) {

            Intent intent = new Intent(this, GetIncome.class);
            startActivity(intent);
            return;
        }

        // Draw the categories summary
        drawCategoriesSumary();

        // drawing the graph
        drawPieChart();

    }

    /**
     * Method in charge of displaying the summary data of each category through a
     * recycler
     */
    private void drawCategoriesSumary() {

        Budget budget = BudgetControl.getCurrentMonthBudget();

        // Set those categories and linked them to the Recycler
        ArrayList<Category> cats = budget.getCategories();
        categories = (RecyclerView) findViewById(R.id.categoryList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        categories.setLayoutManager(mLayoutManager);

        adapter = new BudgetAdapter(cats);
        categories.setAdapter(adapter);

        // Show the income on the screen
        TextView view = findViewById(R.id.textView6);
        BigDecimal income = budget.getIncome();
        String incomeText = income.toString();

        view.setText(incomeText);

        // BudgetControl.printCurrentMonthBudget();
    }

    /** Draw a pie chart of the current month budget */
    private void drawPieChart() {

        pieChart = (PieChart) findViewById(R.id.pieChart);

        // Call control class to get the data
        List<PieEntry> pieEntries = ChartDrawingControl.getChartDataSet();

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");

        // Call control class to set the colors based on required size and if there is
        // income left
        pieDataSet.setColors(ChartDrawingControl.getRandomColorsArray(pieEntries.size(),
                pieEntries.get(pieEntries.size() - 1).getLabel().equals("INCOME LEFT")));

        // Set data and draw it
        pieChart.setData(new PieData(pieDataSet));
        pieChart.animateXY(1000, 1000);
        Description description = new Description();
        description.setText("Budget Status");
        pieChart.setDescription(description);
        pieChart.invalidate();
    }

    // Creates a pop up menu
    public void showPopUp(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    // This has different options for the drop down menu, links to different
    // activities
    // in app
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent(this, CategoryView.class);
                startActivity(intent);
                return true;
            case R.id.item2:
                Intent intent2 = new Intent(this, TransactionView.class);
                startActivity(intent2);
                return true;
            case R.id.option1:
                RealControl.resetBudget();
                Intent intent4 = new Intent(this, GetIncome.class);
                startActivity(intent4);
                return true;
            case R.id.option2:
                Intent intent5 = new Intent(this, RemoveCategoryView.class);
                startActivity(intent5);
                return true;
            case R.id.option3:
                Intent intent6 = new Intent(this, GetIncome.class);
                startActivity(intent6);
                return true;

            default:
                return false;
        }
    }

    // Another popup menu for basic settings within the app
    // The user can reset budget, remove categories, and edit
    // monthly income
    public void showPopUpSettings(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu_settings);
        popup.show();
    }
}
