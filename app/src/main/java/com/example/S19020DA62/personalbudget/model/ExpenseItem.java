package com.example.S19020DA62.personalbudget.model;

public class ExpenseItem {
    int id;
    String name, amount, catagory, remarks;


    public ExpenseItem()
    {

    }

    public ExpenseItem(int id, String name, String amount, String catagory, String remarks,long expenseDate) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.catagory = catagory;
        this.remarks = remarks;
        this.expenseDate = expenseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    long expenseDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public long getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(long expenseDate) {
        this.expenseDate = expenseDate;
    }

}
