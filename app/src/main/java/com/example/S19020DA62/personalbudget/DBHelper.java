package com.example.S19020DA62.personalbudget;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.S19020DA62.personalbudget.model.ExpenseAmount;
import com.example.S19020DA62.personalbudget.model.ExpenseItem;

import java.util.ArrayList;
import java.util.Date;


public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "pbmsDb";
    // Contacts table name
    private static final String TABLE_CATAGORIES = "catagoryTable";
    private static final String TABLE_ITEMS = "expenseTable";
    private static final String TABLE_AMOUNT = "amountTable";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_CATAGORY_NAME = "name";



    private static final String KEY_ITEM_ID = "id";
    private static final String KEY_ITEM_NAME = "itemName";
    private static final String KEY_ITEM_CATAGORY = "itemCatagory";
    private static final String KEY_DATE_EXPENSE = "date";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_REMARKS = "remarks";

    private static final String KEY_AMOUNT_ID = "id";
    private static final String KEY_BUDGET_AMOUNT = "monthAmount";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("constructor", "constructor called");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CATAGORY_TABLE = "CREATE TABLE " + TABLE_CATAGORIES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_CATAGORY_NAME + " TEXT" + ")";
        db.execSQL(CREATE_CATAGORY_TABLE);

        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
                + KEY_ITEM_ID + " INTEGER PRIMARY KEY,"
                + KEY_ITEM_NAME + " TEXT,"
                + KEY_ITEM_CATAGORY + " TEXT,"
                + KEY_DATE_EXPENSE + " INTEGER,"
                + KEY_AMOUNT + " INTEGER,"
                + KEY_REMARKS + " TEXT" + ")";
        db.execSQL(CREATE_ITEMS_TABLE);

        String CREATE_AMOUNT_TABLE = "CREATE TABLE " + TABLE_AMOUNT + "("
                + KEY_AMOUNT_ID + " INTEGER PRIMARY KEY,"
                + KEY_BUDGET_AMOUNT + " INTEGER" + ")";
        db.execSQL(CREATE_AMOUNT_TABLE);

        Log.i("oncreate", "oncreate called");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATAGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AMOUNT);
        // Create tables again
        onCreate(db);
        Log.i("upgrade", "upgrade called");
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    void addCatagory(String _catagory) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("log", "get writable database to add");

        ContentValues values = new ContentValues();
        values.put(KEY_CATAGORY_NAME, _catagory);

        // Inserting Row
        db.insert(TABLE_CATAGORIES, null, values);
        db.close(); // Closing database connection
    }

    void addAmount(ExpenseAmount item) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("log", "get writable database to add");

        ContentValues values = new ContentValues();
        values.put(KEY_BUDGET_AMOUNT, item. getmonthAmount());

        // Inserting Row
        db.insert(TABLE_AMOUNT, null, values);
        db.close(); // Closing database connection
    }


    void addExpense(ExpenseItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("log", "get writable database to add");

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_NAME, item.getName());
        values.put(KEY_ITEM_CATAGORY, item.getCatagory());
        values.put(KEY_DATE_EXPENSE, item.getExpenseDate());
        values.put(KEY_AMOUNT, item.getAmount());
        values.put(KEY_REMARKS, item.getRemarks());

        // Inserting Row
        db.insert(TABLE_ITEMS, null, values);
        db.close(); // Closing database connection
    }
    void deleteAllExpense() {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("delete", "Deleting ExpenseItem");

        // Inserting Row
        db.execSQL("delete from "+ (TABLE_ITEMS));
        db.close(); // Closing database connection
    }

    ExpenseItem getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Log.i("log", "get redable database to read");

        Cursor cursor = db.query(TABLE_CATAGORIES, new String[]{KEY_ID,
                        KEY_ITEM_NAME, KEY_ITEM_CATAGORY,
                        KEY_DATE_EXPENSE, KEY_AMOUNT,KEY_REMARKS}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ExpenseItem item = new ExpenseItem();
        // return number
        return item;
    }

    public ArrayList<String> getAllCatagories() {
        ArrayList<String> catagory_list = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CATAGORIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String catagory ;

                catagory= cursor.getString(1);

                // Adding contact to list
                catagory_list.add(catagory);
            } while (cursor.moveToNext());
        }

        // return contact list
        return catagory_list;
    }

    public ArrayList<ExpenseItem> getAllExpenses() {
        ArrayList<ExpenseItem> expenses = new ArrayList<ExpenseItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ITEMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ExpenseItem item = new ExpenseItem();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setName(cursor.getString(1));
                item.setCatagory(cursor.getString(2));
                item.setExpenseDate(Long.parseLong(cursor.getString(3)));
                item.setAmount(cursor.getString(4));
                item.setRemarks(cursor.getString(5));
                // Adding contact to list
                expenses.add(item);
            } while (cursor.moveToNext());
        }

        // return contact list
        return expenses;
    }


    // Getting numbers Count
    public int getnumbersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CATAGORIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public ArrayList<ExpenseItem> getDatedItems(Date startDate, Date endDate, String selectedCatagory) {
        ArrayList<ExpenseItem> expenses = new ArrayList<ExpenseItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ITEMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ExpenseItem item = new ExpenseItem();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setName(cursor.getString(1));
                item.setCatagory(cursor.getString(2));
                item.setExpenseDate(Long.parseLong(cursor.getString(3)));
                item.setAmount(cursor.getString(4));
                item.setRemarks(cursor.getString(5));
                // Adding contact to list
                if (
                        item.getCatagory().equals(selectedCatagory)
                        && new Date(item.getExpenseDate()).after(startDate)
                        && new Date(item.getExpenseDate()).before(endDate))
                {
                    expenses.add(item);
                }
            } while (cursor.moveToNext());
        }

        // return contact list
        return expenses;

    }

    public ArrayList<ExpenseItem> getCatagoryItems(String selectedCatagory) {
        ArrayList<ExpenseItem> expenses = new ArrayList<ExpenseItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ITEMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ExpenseItem item = new ExpenseItem();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setName(cursor.getString(1));
                item.setCatagory(cursor.getString(2));
                item.setExpenseDate(Long.parseLong(cursor.getString(3)));
                item.setAmount(cursor.getString(4));
                item.setRemarks(cursor.getString(5));
                // Adding contact to list
                if (
                        item.getCatagory().equals(selectedCatagory))
                {
                    expenses.add(item);
                }
            } while (cursor.moveToNext());
        }

        // return contact list
        return expenses;

    }

    public Cursor getExpenseType() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select amount from "+TABLE_ITEMS,null);
        return res;
    }

    public Cursor getExpenseTypeByName(String Type) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_ITEMS+" where itemName="+"'"+Type+"'",null);
        return res;
    }
    public Cursor getValueByExpenseNameAndId(String ExpName,String Amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_ITEMS+" where itemName="+"'"+ExpName+"' and amount="+"'"+Amount+"'",null);
        return res;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_ITEMS,null);
        return res;
    }

}
