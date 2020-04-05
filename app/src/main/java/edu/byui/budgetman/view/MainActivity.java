package edu.byui.budgetman.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

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

        if (BudgetControl.getCurrentMonthBudget().getIncome().compareTo(new BigDecimal("0")) == 0) {

            Intent intent = new Intent(this, GetIncome.class);
            startActivity(intent);
            return;
        }

        drawCategoriesSumary();

        drawPieChart();

    }

    private void drawCategoriesSumary() {

        Budget budget = BudgetControl.getCurrentMonthBudget();

        ArrayList<Category> cats = budget.getCategories();
        categories = (RecyclerView) findViewById(R.id.categoryList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        categories.setLayoutManager(mLayoutManager);

        adapter = new BudgetAdapter(cats);
        categories.setAdapter(adapter);

        TextView view = findViewById(R.id.textView6);
        BigDecimal income = budget.getIncome();
        String incomeText = income.toString();

        view.setText(incomeText);

        BudgetControl.printCurrentMonthBudget();
    }

    private void drawPieChart() {

        pieChart = (PieChart) findViewById(R.id.pieChart);

        List<PieEntry> pieEntries = ChartDrawingControl.getChartDataSet();

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");

        pieDataSet.setColors(ChartDrawingControl.getRandomColorsArray(pieEntries.size(),
                pieEntries.get(pieEntries.size() - 1).getLabel().equals("INCOME LEFT")));

        pieChart.setData(new PieData(pieDataSet));
        pieChart.animateXY(1000, 1000);
        Description description = new Description();
        description.setText("Budget Status");
        pieChart.setDescription(description);
        pieChart.invalidate();
    }

    public void showPopUp(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

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

    public void showPopUpSettings(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu_settings);
        popup.show();
    }
}
