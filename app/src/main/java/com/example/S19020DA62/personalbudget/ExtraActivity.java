package com.example.S19020DA62.personalbudget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android. widget.TableLayout;
import android. widget.TableRow;
import android. widget.TextView;
import android. graphics.Color;
import android. view.Gravity;

import com.example.S19020DA62.personalbudget.model.ExpenseItem;

import java.util. ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtraActivity extends AppCompatActivity {

    ArrayList<ExpenseItem> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);
        init();


    }

     public void init() {
        list= new DBHelper(getApplicationContext()).getAllExpenses();
            android.widget.TableLayout stk = (TableLayout) findViewById(R.id.table_main);
            stk.setColumnStretchable(0, true);
            stk.setColumnStretchable(1, true);
            stk.setColumnStretchable(2, true);
            stk.setColumnStretchable(3, true);
            stk.setColumnStretchable(4, true);
            stk.setColumnStretchable(5, true);
            TableRow tbrow0 = new TableRow(this);
            TextView tv0 = new TextView(this);
            tv0.setText(" Sl.No ");
            tv0.setTextColor(Color.WHITE);
            tv0.setTextSize(22);
            tbrow0.addView(tv0);
            TextView tv1 = new TextView(this);
            tv1.setText(" Name ");
            tv1.setTextColor(Color.WHITE);
            tv1.setTextSize(22);
            tbrow0.addView(tv1);
            TextView tv2 = new TextView(this);
            tv2.setText(" Catagory ");
            tv2.setTextColor(Color.WHITE);
            tv2.setTextSize(22);
            tbrow0.addView(tv2);
            TextView tv3 = new TextView(this);
            tv3.setText(" Amount ");
            tv3.setTextColor(Color.WHITE);
            tv3.setTextSize(22);
            tbrow0.addView(tv3);
            TextView tv4 = new TextView(this);
            tv4.setText(" Remarks ");
            tv4.setTextColor(Color.WHITE);
            tv4.setTextSize(22);
            tbrow0.addView(tv4);
            TextView tv5 = new TextView(this);
            tv5.setText(" Date ");
            tv5.setTextColor(Color.WHITE);
            tv5.setTextSize(22);
            tbrow0.addView(tv5);
            stk.addView(tbrow0);
            for (int i = 0; i < list.size(); i++) {
                ExpenseItem item=list.get(i);
                TableRow tbrow = new TableRow(this);
                TextView t1v = new TextView(this);
                t1v.setText(item.getId()+"");
                t1v.setTextColor(Color.WHITE);
                t1v.setGravity(Gravity.CENTER);
                t1v.setTextSize(17);
                tbrow.addView(t1v);

                TextView t2v = new TextView(this);
                t2v.setText(item.getName());
                t2v.setTextColor(Color.WHITE);
                t2v.setGravity(Gravity.CENTER);
                t2v.setTextSize(17);
                tbrow.addView(t2v);

                TextView t3v = new TextView(this);
                t3v.setText(item.getCatagory());
                t3v.setTextColor(Color.WHITE);
                t3v.setGravity(Gravity.CENTER);
                t3v.setTextSize(17);
                tbrow.addView(t3v);

                TextView t4v = new TextView(this);
                t4v.setText(item.getAmount()+"");
                t4v.setTextColor(Color.WHITE);
                t4v.setGravity(Gravity.CENTER);
                t4v.setTextSize(17);
                tbrow.addView(t4v);

                TextView t5v = new TextView(this);
                t5v.setText(item.getRemarks());
                t5v.setTextColor(Color.WHITE);
                t5v.setGravity(Gravity.CENTER);
                t5v.setTextSize(17);
                tbrow.addView(t5v);


                TextView t6v = new TextView(this);
                long ms = item.getExpenseDate();
                String _date;

        java.util.Date date = new Date(ms);
        SimpleDateFormat dateformat = new SimpleDateFormat("MMM dd, yyyy");
       _date= dateformat.format(date);

                t6v.setText(_date);
                t6v.setTextColor(Color.WHITE);
                t6v.setGravity(Gravity.CENTER);
                t6v.setTextSize(17);
                tbrow.addView(t6v);


                stk.addView(tbrow);
            }

        }

    }


