package com.example.S19020DA62.personalbudget;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.S19020DA62.personalbudget.model.ExpenseItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    Spinner listOfCatagories;
    EditText startDate, endDate;
    boolean startDateClicked, endDateClicked;
    ListView listview;
    Button searchBtn;
    TextView totalText;
    Switch dateSelector;
    int year_x, month_x, date_x;
static final int Dialog_id=0;
    ArrayList<ExpenseItem> listItem;
ExpenseAdapter adapter =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle("Search Items");
        final Calendar cal=Calendar.getInstance();

        listOfCatagories=findViewById(R.id.slecet_spinner);
        startDate=findViewById(R.id.inputStartDate);
        endDate=findViewById(R.id.inputEndtDate);
        dateSelector=findViewById(R.id.switch_date);
        searchBtn=findViewById(R.id.btn_search);
        listview=findViewById(R.id.list_items);
        totalText=findViewById(R.id.total);



        //adding items in the spinner i.e. dropdown list

        List<String> list = new DBHelper(getApplicationContext()).getAllCatagories();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listOfCatagories.setAdapter(dataAdapter);

// disable date fields initially
        startDate.setEnabled(false);
        endDate.setEnabled(false);

        year_x=cal.get(Calendar.YEAR);
        month_x=cal.get(Calendar.MONTH);
        date_x=cal.get(Calendar.DAY_OF_MONTH);


        showStartDate();
        showEndDate();
        togleRange();
        search();
    }

    private void search() {
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateStart=startDate.getText().toString();
                String dateend=endDate.getText().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date start = null;
                Date after = null;

                try {
                    start = sdf.parse(dateStart);   //this is start date
                    after=sdf.parse(dateend);        // this is end date of the range
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String selectedCatagory= String.valueOf(listOfCatagories.getSelectedItem()); //this is selected catagory
                if(dateSelector.isChecked()){
                    listItem=new DBHelper(getApplicationContext()).getDatedItems( start,  after,  selectedCatagory);

                }
                else if(!dateSelector.isChecked()){
                    listItem = new DBHelper(getApplicationContext()).getCatagoryItems(selectedCatagory );

                }

                adapter = new ExpenseAdapter(SearchActivity.this, R.layout.expense_item, listItem);
                listview.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                //now we find total amount

                int total = 0;

                ExpenseItem itm;
                for(int i=0;i<listItem.size();i++){
                    itm=listItem.get(i);
                    total+=Integer.parseInt(itm.getAmount());
                }
               totalText.setVisibility(View.VISIBLE);
                totalText.setText(total+"");
            }
        });
    }

    public void showStartDate(){
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDateClicked=true;
                endDateClicked=false;
                showDialog(Dialog_id);
            }
        });
    }
    public void showEndDate(){
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDateClicked=true;
                startDateClicked=false;
                showDialog(Dialog_id);
            }
        });
    }

    public void togleRange(){
    dateSelector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(dateSelector.isChecked()){
                startDate.setEnabled(true);
                endDate.setEnabled(true);
            }
            else if(!dateSelector.isChecked()){
                startDate.setEnabled(false);
                endDate.setEnabled(false);

            }
        }
    });
    }


    @Override
    protected Dialog onCreateDialog(int id){
        if(id==Dialog_id)
            return new DatePickerDialog(this, dpickerListner, year_x, month_x, date_x);
        return null;

    }

    private DatePickerDialog.OnDateSetListener dpickerListner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x=year;
            month_x=month+1;
            date_x=dayOfMonth;
            if(startDateClicked) {
                startDate.setText(date_x + "/" + month_x + "/" + year);
                startDateClicked=false;
            }
            else if (endDateClicked) {
                endDate.setText(date_x + "/" + month_x + "/" + year);
                endDateClicked=false;
            }

            Toast.makeText(getApplicationContext(),year_x+"/"+month_x+"/"+date_x,Toast.LENGTH_SHORT).show();

        }
    };
}
