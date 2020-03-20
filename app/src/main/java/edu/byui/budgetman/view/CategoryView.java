package edu.byui.budgetman.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

import edu.byui.budgetman.R;

public class CategoryView extends AppCompatActivity {

    TextView input;
    TextView input2;
    String category;
    String amountText;

    // This Map will keep track of the categories and pair them up

    HashMap<Float,String> hm= new HashMap<Float,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);
    }

    public void getCategory() {

        input = (TextView) findViewById(R.id.editText);
        category = input.getText().toString();

        input2 = (TextView) findViewById(R.id.editText2);
        amountText = input.getText().toString();

        Float amount = Float.parseFloat(amountText);
        hm.put(amount, category);
        input.setText("");
        input2.setText("");
    }
}
