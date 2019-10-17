package com.example.S19020DA62.personalbudget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.S19020DA62.personalbudget.model.ExpenseAmount;

public class MonthyPlanActivity extends AppCompatActivity {

    //intial or define

    EditText monthAmount;
    Button addAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthy_plan);
        setTitle("Add Monthly Plan");

        //casting GUI components

        monthAmount = (EditText) findViewById(R.id.et_input1);

        addAmount =  (Button) findViewById(R.id.bt_add_plan);

        addAmount();

    }


    private void addAmount() {
        addAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DBHelper(getApplicationContext()).deleteAllExpense();
                if (monthAmount.getText().toString()!=null && monthAmount.getText().toString().length()>0){

                    ExpenseAmount item = new ExpenseAmount();
                    //prepare item object from the fields that user has typed

                    item.setmonthAmount(monthAmount.getText().toString());

                    //now put this item into the Database
                    new DBHelper(getApplicationContext()).addAmount(item);
                    Toast.makeText(getApplicationContext(),"Amount Added",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), AddCatagoriesActivity.class);
                    intent.putExtra("totalAmount",monthAmount.getText().toString());
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }else {
                    Toast.makeText(getApplicationContext(),"Please add some amount",Toast.LENGTH_SHORT).show();

                }


            }
        });

    }
}
