package com.example.S19020DA62.personalbudget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.S19020DA62.personalbudget.model.ExpenseItem;

import java.util. ArrayList;

public class AddCatagoriesActivity extends AppCompatActivity {

    //declare fields
    ArrayList<ExpenseItem> list;
    Button addCatagory;
    EditText catagoryText;
    int totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_catagories);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //calling through field IDs

        addCatagory = findViewById(R.id.btn_add_catagory);
        catagoryText = findViewById(R.id.input_catagory);
        totalAmount = Integer.valueOf(getIntent().getStringExtra("totalAmount"));

        //setOnClickListener for action on button wether its empty , or not unique
        //requestFocus method ha set error from category text(is category empy or not)
        //DBHelper request from database (wethere category is uniqe or not)
        //getApplicationContext() when you call  process/service from activity


        addCatagory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String catagory = catagoryText.getText().toString();
                if(catagory.isEmpty()){
                    catagoryText.setError("Please type a catagory here");
                    catagoryText.requestFocus();
                }
                else if(new DBHelper(getApplicationContext()).getAllCatagories().contains(catagory)){
                    catagoryText.setError("Please type a unique catagory");
                    catagoryText.requestFocus();
                }
                else{
                    new DBHelper(getApplicationContext()).addCatagory(catagory);
                    Toast.makeText(getApplicationContext(), "new catagory added successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    //menu relation
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //call through IDs
        // == comparison or eqivalent for values
        //intent communicate between two activities like from login actovity to category activty
        //overridePendingTransition()  translation or slide menu ke option ko anim =animation
        if (id == R.id.action_addExpense) {
            Intent intent = new Intent(getApplicationContext(), AddExpenseActivity.class);
            intent.putExtra("totalAmount",getIntent().getStringExtra("totalAmount"));
            startActivity(intent);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            return true;
        }
        else if(id == R.id.action_allItems){
            Intent intent = new Intent(getApplicationContext(), ExtraActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            return true;
        }
        else if(id == R.id.action_search){
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            return true;
        }

        else if(id == R.id.action_chart){
            Intent intent = new Intent(getApplicationContext(), ExpenseChartActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
