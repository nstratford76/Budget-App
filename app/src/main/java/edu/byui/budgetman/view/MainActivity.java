package edu.byui.budgetman.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import edu.byui.budgetman.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Intent intentForCategoryView = new Intent(this, CategoryView.class);
        startActivity(intentForCategoryView);
        */


    }


}
