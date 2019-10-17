package com.example.S19020DA62.personalbudget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.S19020DA62.personalbudget.model.ExpenseItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExpenseAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<ExpenseItem> expenseList;

    public ExpenseAdapter(Context context, int layout, ArrayList<ExpenseItem> itemsList) {
        this.context = context;
        this.layout = layout;
        this.expenseList = itemsList;
    }

    @Override
    public int getCount() {
        return expenseList.size();
    }

    @Override
    public Object getItem(int position) {
        return expenseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView txtName, txtAmount, txtDate, txtRemarks, txtCatagory;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtName = (TextView) row.findViewById(R.id.txtName);
            holder.txtAmount = (TextView) row.findViewById(R.id.txtAmount);
            holder.txtDate = (TextView) row.findViewById(R.id.txtDate);
            holder.txtRemarks = (TextView) row.findViewById(R.id.txtRemarks);
            holder.txtCatagory = (TextView) row.findViewById(R.id.txtCaatagory);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        ExpenseItem item = expenseList.get(position);

        //lets format the date from milli seocnds to date

        long ms = item.getExpenseDate();
        String _date;

        Date date = new Date(ms);
        SimpleDateFormat dateformat = new SimpleDateFormat("MMM dd, yyyy");
       _date= dateformat.format(date);


              holder.txtName.setText("Name:\n"+item.getName());
        holder.txtCatagory.setText("Catagory:\n"+item.getCatagory());
            holder.txtAmount.setText("Amount:\n"+item.getAmount());
              holder.txtDate.setText("Date:\n"+ _date);
           holder.txtRemarks.setText("Remarks:\n"+item.getRemarks());

        return row;
    }
}
