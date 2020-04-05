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
 * A basic, possibly not efficient model api for our application that gets the
 * job done
 */
public class ModelAPI extends SQLiteOpenHelper {

    private static Budget uniqueBudget;

    public ModelAPI(Context context) {
        super(context, "budgetman.db", null, 2);
    }

    /** Database filler for application's first usage */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists budget (id integer primary key, month integer, income text)");
        db.execSQL("create table if not exists budget_category (id integer primary key, budget integer, "
                + "name text, budgeted_amount text)");
        db.execSQL("create table if not exists category_transaction (id integer primary key, category integer, "
                + "date text, transaction_amount text, description text, budget integer)");
    }

    /** Cleanning the database for the times the application is upgraded */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Dropping tables for recreation on upgrade
        db.execSQL("drop table if exists budget");
        db.execSQL("drop table if exists budget_category");
        db.execSQL("drop table if exists category_transaction");

        // Recreate the database according to version requirements
        onCreate(db);
    }

    /**
     * Cleanning the database for the times the application is downgraded (rolling
     * back to a previous commit and debugging on a previously installed device)
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Dropping tables for recreation on downgrade
        db.execSQL("drop table if exists budget");
        db.execSQL("drop table if exists budget_category");
        db.execSQL("drop table if exists category_transaction");

        // Recreate the database according to version requirements
        onCreate(db);
    }

    // ***************************************************************

    /**
     * Model API to get the current month from database and load it to memory in the
     * application as a singleton It won't read from database if the object was
     * already loaded to memory.
     */
    public Budget getCurrentMonthBudget() {

        // It won't read from database if the object was already loaded to memory.
        if (uniqueBudget != null)
            return uniqueBudget;

        // Getting the budget for the current month from its id
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        String currentMonthID = "" + year + "" + String.format("%02d", month);

        // Getting the database
        SQLiteDatabase db = this.getWritableDatabase();

        // Querying for a stored budget
        Cursor res = db.rawQuery("select * from budget where id=" + currentMonthID, null);

        // If there isn't a budget stored just create a new one
        if (res.getCount() == 0) {
            res.close();
            uniqueBudget = new Budget(Integer.parseInt(currentMonthID), new BigDecimal("0"), month,
                    new ArrayList<Category>());

            res.close();

            // and insert it to the database
            insertBudget(db);

        } else {
            res.moveToFirst();
            String income = res.getString(res.getColumnIndex("income"));

            // filling the in-memory currentMonthBudget with the found data
            uniqueBudget = new Budget(Integer.parseInt(currentMonthID), new BigDecimal(income), month,
                    new ArrayList<Category>());

            res.close();

            // Query and fill categories from database if exists
            fillCategoriesObjectData(db);

            // Fill transactions if there where categories
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

    /**
     * Fill the transactions of the categories from database for the in-memory
     * current Month Budget
     */
    private static boolean fillTransactionsData(SQLiteDatabase db) throws ParseException {

        // Traversing each category
        for (Category currentCategory : uniqueBudget.getCategories()) {

            // Querying for it's transactions
            Cursor result = db.rawQuery(
                    "select * from category_transaction where category = " + currentCategory.getSqlId(), null);

            // If there where transactions
            if (result.moveToFirst()) {

                List<Transaction> transactions = currentCategory.getTransactions();

                while (!result.isAfterLast()) {

                    // Get the dates of the transactions
                    Date date = Transaction.TRANSACTION_DATE_FORMAT
                            .parse(result.getString(result.getColumnIndex("date")));

                    String amount = result.getString(result.getColumnIndex("transaction_amount"));
                    String description = result.getString(result.getColumnIndex("description"));

                    // Creating the transaction from the retrieved data
                    Transaction currentTransaction = new Transaction(new BigDecimal(amount), description);
                    currentTransaction.setDate(date);

                    // Saving it to the corresponding category transactions list
                    transactions.add(currentTransaction);

                    result.moveToNext();
                }
            }

            result.close();
        }

        return true;
    }

    /** Fill the categories of the current month object from database */
    private boolean fillCategoriesObjectData(SQLiteDatabase db) {

        Cursor res = db.rawQuery("select * from budget_category where budget=" + uniqueBudget.getSQL_BUDGET_ID(), null);

        // if there are categories
        if (res.getCount() > 0) {

            ArrayList<Category> categories = uniqueBudget.getCategories();

            res.moveToFirst();

            while (!res.isAfterLast()) {

                // Getting the data
                int categoryId = res.getInt(res.getColumnIndex("id"));
                String categoryName = res.getString(res.getColumnIndex("name"));
                String budgetedAmount = res.getString(res.getColumnIndex("budgeted_amount"));

                // creating the category
                Category currentCategory = new Category(categoryName, new BigDecimal(budgetedAmount),
                        new ArrayList<Transaction>());

                // Set the sqlid because that is going to be used to query for the transactions
                // tied to this in database
                currentCategory.setSqlId(categoryId);

                // Add the category to the budget
                categories.add(currentCategory);

                res.moveToNext();
            }

            res.close();

        }
        return true;
    }

    /** Insert the budget data to the database */
    private static boolean insertBudget(SQLiteDatabase db) {

        db.execSQL("delete from budget where id=" + uniqueBudget.getSQL_BUDGET_ID());

        ContentValues values = new ContentValues();
        values.put("id", uniqueBudget.getSQL_BUDGET_ID());
        values.put("month", uniqueBudget.getMonth());
        values.put("income", uniqueBudget.getIncome().toString());

        return db.insert("budget", null, values) > -1;
    }

    /**
     * Inserts all data corresponding to the current state of the current month
     * budget.
     * <hr>
     * WARNING, only call this if there has at least been one call to the
     * {@link #getCurrentMonthBudget() getCurrentMonthBudget}
     * 
     * @throws NonInitializedBudgetException if not done so
     */
    public void saveAppBudgetChanges() throws NonInitializedBudgetException {

        // Throwing error in case the budget is attempted to save without ever firs
        // initializing it
        if (uniqueBudget == null) {
            throw new NonInitializedBudgetException("Budget hasn't been initialized at this point of execution");
        }

        SQLiteDatabase db = this.getWritableDatabase();

        insertBudget(db);

        insertCategories(db);
        insertTransactions(db);

        db.close();
    }

    /**
     * Insert the transactions for the current month budget Categories. Preferably
     * to be called after inserting the categories.
     * <hr>
     * WARNING: It works with up to 100 transactions per category per month
     */
    private static void insertTransactions(SQLiteDatabase db) {

        // Delete previously existing transactions
        db.execSQL("delete from category_transaction where budget=" + uniqueBudget.getSQL_BUDGET_ID());

        // Insert them all
        for (Category category : uniqueBudget.getCategories()) {

            if (category.getTransactions().size() > 0) {

                List<Transaction> transactions = category.getTransactions();

                for (int i = 0; i < transactions.size(); i++) {

                    ContentValues values = new ContentValues();
                    // up to 100 transactions per category per month
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

    /**
     * Insert the categories for the current month budget. Preferably to be called
     * after inserting the budget
     * <hr>
     * WARNING: It works with up to 100 categories per month
     */
    private static void insertCategories(SQLiteDatabase db) {

        int budget_id = uniqueBudget.getSQL_BUDGET_ID();

        // First delete the previously stored categories
        db.execSQL("delete from budget_category where budget=" + budget_id);

        ArrayList<Category> categories = uniqueBudget.getCategories();

        // Insert them all
        int i = 0;
        for (Category category : categories) {

            // There is a limit of 100 categories per budget ... implement that restriction
            // on the categories activity
            int categorySQLid = Integer.parseInt("" + budget_id + String.format("%02d", i));

            category.setSqlId(categorySQLid);

            ContentValues values = new ContentValues();
            values.put("id", categorySQLid);
            values.put("budget", budget_id);
            values.put("name", category.getName());
            values.put("budgeted_amount", category.getBudgetedAmount().toString());

            db.insert("budget_category", null, values);

            i++;
        }
    }
}
