package com.example.S19020DA62.personalbudget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.S19020DA62.personalbudget.model.ExpenseItem;

import java.util.ArrayList;

public class ExpenseListActivity extends AppCompatActivity {
    GridView gridView;
    ArrayList<ExpenseItem> list;
    ExpenseAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);

        gridView = (GridView) findViewById(R.id.gridView);
        list = new DBHelper(getApplicationContext()).getAllExpenses();
        adapter = new ExpenseAdapter(this, R.layout.expense_item, list);
        gridView.setAdapter(adapter);

        // get all data from sqlite
        adapter.notifyDataSetChanged();
    }
}
