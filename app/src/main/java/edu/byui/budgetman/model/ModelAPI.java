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
                        "date text, transaction_amount text, description text, budget integer)"
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

        if (uniqueBudget != null)
            return uniqueBudget;

        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        String currentMonthID = "" + year + "" + String.format("%02d", month);

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

        db.close();

        return uniqueBudget;
    }

    private static boolean fillTransactionsData(SQLiteDatabase db) throws ParseException {

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

    private static boolean insertBudget(SQLiteDatabase db) {

        db.execSQL("delete from budget where id=" + uniqueBudget.getSQL_BUDGET_ID());

        ContentValues values = new ContentValues();
        values.put("id", uniqueBudget.getSQL_BUDGET_ID());
        values.put("month", uniqueBudget.getMonth());
        values.put("income", uniqueBudget.getIncome().toString());

        return db.insert("budget", null, values) > -1;
    }


    public void saveAppBudgetChanges() throws NonInitializedBudgetException {

        if (uniqueBudget == null) {
            throw new NonInitializedBudgetException("Budget hasn't been initialized at this point of execution");
        }
        // Delete budget with current id

        SQLiteDatabase db = this.getWritableDatabase();

        insertBudget(db);

        insertCategories(db);
        insertTransactions(db);

        db.close();
    }

    private static void insertTransactions(SQLiteDatabase db) {

        db.execSQL("delete from category_transaction where budget=" + uniqueBudget.getSQL_BUDGET_ID());

        for (Category category : uniqueBudget.getCategories()) {


            if (category.getTransactions().size() > 0) {

                List<Transaction> transactions = category.getTransactions();


                for (int i = 0; i < transactions.size(); i++) {


                    ContentValues values = new ContentValues();
                    // up to 100 transactions per category
                    values.put("id", "" + category.getSqlId() + String.format("%02d", i));
                    values.put("category", category.getSqlId());
                    values.put("date", Transaction.TRANSACTION_DATE_FORMAT.format(transactions.get(i).getDate()));
                    values.put("transaction_amount", transactions.get(i).getAmount().toString());
                    values.put("description", transactions.get(i).getDescription());
                    values.put("budget", uniqueBudget.getSQL_BUDGET_ID());

                    db.insert("category_transaction", null, values);

                }


            }

        }
    }

    private static void insertCategories(SQLiteDatabase db) {

        int budget_id = uniqueBudget.getSQL_BUDGET_ID();

        db.execSQL("delete from budget_category where budget=" + budget_id);

        List<Category> categories = uniqueBudget.getCategories();


        for (int i = 0; i < categories.size(); i++) {

            // There is a limit of 100 categories per budget ... implement that restriction on the categories activity
            int categorySQLid = Integer.parseInt("" + budget_id + String.format("%02d", i));

            categories.get(i).setSqlId(categorySQLid);

            ContentValues values = new ContentValues();
            values.put("id", categorySQLid);
            values.put("budget", budget_id);
            values.put("name", categories.get(i).getName());
            values.put("budgeted_amount", categories.get(i).getBudgetedAmount().toString());

            db.insert("budget_category", null, values);

        }
    }
}
