package edu.byui.budgetman.model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A basic, possibly not efficient model api for our application
 * that gets the job done
 */
public class ModelAPI extends SQLiteOpenHelper {

    private static Budget uniqueBudget;


    public ModelAPI(Context context) {
        super(context, "budgetman.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table if not exists budget (id integer primary key, month integer, income text)"
        );
        db.execSQL(
                "create table if not exists budget_category (id integer primary key, budget integer, " +
                        "name text, budgeted_amount text)"
        );
        db.execSQL(
                "create table if not exists category_transaction (id integer primary key, category integer, " +
                        "date text, transaction_amount text, description text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Dropping tables for recreation on upgrade
        db.execSQL("drop table if exists budget");
        db.execSQL("drop table if exists budget_category");
        db.execSQL("drop table if exists category_transaction");

        onCreate(db);
    }


// ***************************************************************

    public Budget getCurrentMonthBudget() {


        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        String currentMonthID = "" + year + "" + month;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from budget where id=" + currentMonthID, null);

        if (res.getCount() == 0) {
            res.close();
            uniqueBudget = new Budget(Integer.parseInt(currentMonthID),
                    new BigDecimal("0"), month, new ArrayList<Category>());
            insertBudget(db);

        } else {
            res.moveToFirst();
            String income = res.getString(res.getColumnIndex("income"));
            uniqueBudget = new Budget(Integer.parseInt(currentMonthID),
                    new BigDecimal(income), month, new ArrayList<Category>());

            fillBudgetObjecData(db);
        }

        return uniqueBudget;
    }

    private boolean fillBudgetObjecData(SQLiteDatabase db) {

        // TODO fill categories and transactions

        return true;
    }

    private boolean insertBudget(SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put("id", uniqueBudget.getSQL_BUDGET_ID());
        values.put("month", uniqueBudget.getMonth());
        values.put("income", uniqueBudget.getIncome().toString());
        db.insert("budget", null, values);

        return true;
    }


    public void saveAppBudgetChanges() {


    }
}
