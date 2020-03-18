package edu.byui.budgetman.model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

            res.close();

            insertBudget(db);

        } else {
            res.moveToFirst();
            String income = res.getString(res.getColumnIndex("income"));
            uniqueBudget = new Budget(Integer.parseInt(currentMonthID),
                    new BigDecimal(income), month, new ArrayList<Category>());

            res.close();

            fillCategoriesObjectData(db);

            if (uniqueBudget.getCategories().size() > 0) {
                try {
                    fillTransactionsData(db);
                } catch (ParseException e) {
                    Log.e(getClass().getName(), e.getMessage());
                }
            }

        }

        return uniqueBudget;
    }

    private boolean fillTransactionsData(SQLiteDatabase db) throws ParseException {

        for (Category currentCategory : uniqueBudget.getCategories()) {

            Cursor result = db.rawQuery("select * from category_transaction where category = " +
                    currentCategory.getSqlId(), null);

            if (result.moveToFirst()) {

                List<Transaction> transactions = currentCategory.getTransactions();

                while (!result.isAfterLast()) {

                    Date date = Transaction.TRANSACTION_DATE_FORMAT.parse(
                            result.getString(result.getColumnIndex("date")));

                    String amount = result.getString(result.getColumnIndex("transaction_amount"));
                    String description = result.getString(result.getColumnIndex("description"));

                    Transaction currentTransaction = new Transaction(new BigDecimal(amount), description);
                    currentTransaction.setDate(date);

                    transactions.add(currentTransaction);

                    result.moveToNext();
                }
            }

            result.close();
        }


        return true;
    }

    private boolean fillCategoriesObjectData(SQLiteDatabase db) {

        Cursor res = db.rawQuery("select * from budget_category where budget=" +
                uniqueBudget.getSQL_BUDGET_ID(), null);

        if (res.getCount() > 0) {

            List<Category> categories = uniqueBudget.getCategories();

            res.moveToFirst();

            while (!res.isAfterLast()) {

                int categoryId = res.getInt(res.getColumnIndex("id"));
                String categoryName = res.getString(res.getColumnIndex("name"));
                String budgetedAmount = res.getString(res.getColumnIndex("budgeted_amount"));


                Category currentCategory = new Category(categoryName, new BigDecimal(budgetedAmount),
                        new ArrayList<Transaction>());

                currentCategory.setSqlId(categoryId);

                categories.add(currentCategory);

                res.moveToNext();
            }

            res.close();

        }
        return true;
    }

    private boolean insertBudget(SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put("id", uniqueBudget.getSQL_BUDGET_ID());
        values.put("month", uniqueBudget.getMonth());
        values.put("income", uniqueBudget.getIncome().toString());

        return db.insert("budget", null, values) > -1;
    }


    public void saveAppBudgetChanges() {

        // TODO write to DB

    }
}
