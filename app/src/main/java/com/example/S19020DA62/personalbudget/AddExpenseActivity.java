package com.example.S19020DA62.personalbudget;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.S19020DA62.personalbudget.model.ExpenseAmount;
import com.example.S19020DA62.personalbudget.model.ExpenseItem;

import java.util.ArrayList;
import java.util.List;

public class AddExpenseActivity extends AppCompatActivity {
   //intial or define
    Spinner mySpinner;
    EditText nameTxt, amountText, remarksText;
    Button addToDb;
    ExpenseAmount monthAmount;
    TextView txtView;

// by klk
    int totalAmount;
    int totalExpense=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        setTitle("Add Daily Expenses");
//casting GUI components


        mySpinner =  findViewById(R.id.spinner_catagories);
        nameTxt =  findViewById(R.id.input_itemName);
        amountText =  findViewById(R.id.input_itemAmount);
        remarksText =  findViewById(R.id.input_itemRemarks);
        addToDb =  findViewById(R.id.btn_add_expense);
        totalAmount = Integer.valueOf(getIntent().getStringExtra("totalAmount"));
         txtView = (TextView) findViewById(R.id.txttotal);


//adding items in the spinner i.e. dropdown list

        List<String> list = new DBHelper(getApplicationContext()).getAllCatagories();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(dataAdapter);
        totalExpense= getTotalExpense(new DBHelper(getApplicationContext()).getAllExpenses());
        txtView.setText("Total Expense = " +totalAmount+" Total Expense = "+totalExpense+" Exceed Limit"+(totalAmount-totalExpense));
        addExpense();
    }
    int getTotalExpense(ArrayList<ExpenseItem> list){



        if(list.size()>0){

            for (int i=0;i<list.size();i++){
                totalExpense = totalExpense+(Integer.valueOf(list.get(i).getAmount()));
            }
            return totalExpense;
        }else {

            return  0;
        }

    }
void showAlert(String message){
    AlertDialog alertDialog = new AlertDialog.Builder(AddExpenseActivity.this).create();
    alertDialog.setTitle("Alert");
    alertDialog.setMessage(""+message);
    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    addInRecord();
                    dialog.dismiss();
                }
            });
    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //addInRecord();
                    dialog.dismiss();
                }
            });

    alertDialog.show();
}

    private void addInRecord() {


        ExpenseItem item = new ExpenseItem();


        //prepare item object from the fields that user has typed

        item.setName(nameTxt.getText().toString());
        item.setAmount(amountText.getText().toString());
        item.setRemarks(remarksText.getText().toString());
        item.setExpenseDate(System.currentTimeMillis());
        item.setCatagory(String.valueOf(mySpinner.getSelectedItem()));

        //now put this item into the Database
        new DBHelper(getApplicationContext()).addExpense(item);
        Toast.makeText(getApplicationContext(),"Record added successfully",Toast.LENGTH_SHORT).show();
        txtView.setText("Total Amount = " +totalAmount+" Total Expense = "+getTotalExpense(new DBHelper(getApplicationContext()).getAllExpenses())+" Exceed Limit"+(totalAmount-getTotalExpense(new DBHelper(getApplicationContext()).getAllExpenses())));
        Intent intent = new Intent(getApplicationContext(), ExpenseListActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    private void addExpense() {

        addToDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalExpense = 0;
                if (amountText.getText().toString()!=null && amountText.getText().toString().length()>0){
                totalExpense = getTotalExpense(new DBHelper(getApplicationContext()).getAllExpenses());
            if (totalAmount<(totalExpense+Integer.valueOf(amountText.getText().toString()))){
                showAlert("You Exceed your total Limit by "+(totalAmount-(totalExpense+Integer.valueOf(amountText.getText().toString())))+" amount");
               // Toast.makeText(AddExpenseActivity.this, "You Exceed your total Limit by "+(totalAmount-totalExpense)+" amount", Toast.LENGTH_SHORT).show();

            }else {
                addInRecord();
            }
                }else {
                    Toast.makeText(getApplicationContext(),"Please add some amount",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
