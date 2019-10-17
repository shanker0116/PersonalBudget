package com.example.S19020DA62.personalbudget;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.database.Cursor;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExpenseChartActivity extends AppCompatActivity {

    DBHelper dbHelper;
    Cursor initial_cursor, cursor;


    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_chart);

        dbHelper = new DBHelper(getApplicationContext());
        initial_cursor = dbHelper.getExpenseType();

        List<String> ls = new ArrayList<String>();
        Set<String> set = new HashSet<String>();

        if(initial_cursor.moveToFirst()){
            do{
                String ExpType = initial_cursor.getString(0);
                set.add(ExpType);
            }while (initial_cursor.moveToNext());

        }
        System.out.println(set);
        List<String> Expense_Type = new ArrayList<String>();
        List<Integer> Expense_Value = new ArrayList<Integer>();
        for(String ExpType : set){
            cursor =  dbHelper.getExpenseTypeByName(ExpType);
            Expense_Type.add(ExpType);
            int sum = 0;
            if(cursor.moveToFirst()){
                do{
                    String expValue = cursor.getString(2);
                    sum += Integer.parseInt(expValue);
                }while (cursor.moveToNext());
            }
            Expense_Value.add(sum);
        }
        String [] ExpenseType_Arr = new String[Expense_Type.size()];
        ExpenseType_Arr = Expense_Type.toArray(ExpenseType_Arr);
        Integer [] ExpenseValue_Arr = new Integer[Expense_Value.size()];
        ExpenseValue_Arr = Expense_Value.toArray(ExpenseValue_Arr);
        List<PieEntry> pieEntries = new ArrayList<PieEntry>();
        for(int i=0;i<ExpenseType_Arr.length;i++){
            pieEntries.add(new PieEntry(ExpenseValue_Arr[i],ExpenseType_Arr[i]));
        }

        cursor = dbHelper.getAllData();
        cursor.moveToFirst();
        String expenseName = cursor.getString(2);
        PieDataSet pieDataSet = new PieDataSet(pieEntries,"Value in Rupees");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(5f);
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(15f);
        pieData.setValueTextColor(Color.YELLOW);



        com.github.mikephil.charting.charts.PieChart pieChart = (com.github.mikephil.charting.charts.PieChart)findViewById(R.id.expensechart);
        pieChart.setTransparentCircleRadius(61f);

        Legend legend = pieChart.getLegend();
        legend.setTextSize(15f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        Description description = new Description();
        description.setText("Expenses of: "+expenseName);
        description.setTextSize(35);
        description.setPosition(800,1700);
        pieChart.setDescription(description);
        pieChart.animateY(1000,Easing.EaseInOutCubic);
        pieChart.invalidate();
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(true);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.99f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(60f);
        pieChart.animateY(1000, Easing.EaseInOutCubic);
        pieChart.setData(pieData);








       // pieChart =(PieChart) findViewById(R.id.expensechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(true);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.99f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(60f);






        //Description description = new Description();
      /*  description.setText("Expense List PieChart");
        description.setTextSize(10f);
        pieChart.setDescription(description);*/

       // pieChart.animateY(1000, Easing.EaseInOutCubic);


        ArrayList<PieEntry> yValues =  new ArrayList<>();


        yValues.add(new PieEntry(23f,""));

        yValues.add(new PieEntry(43f,""));

        yValues.add(new PieEntry(13f,""));

        yValues.add(new PieEntry(33f,""));


        yValues.add(new PieEntry(26f,""));

        yValues.add(new PieEntry(63f,""));

      /* PieDataSet dataSet = new PieDataSet(yValues,"");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);*/
    }
}
