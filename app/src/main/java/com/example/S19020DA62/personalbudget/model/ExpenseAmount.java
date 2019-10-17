package com.example.S19020DA62.personalbudget.model;

public class ExpenseAmount {


    int id;
    String monthAmount;


public ExpenseAmount() {

}

    public ExpenseAmount(int id, String monthAmount) {
        this.id = id;

        this.monthAmount = monthAmount;
    }


    public String getmonthAmount() {

        return monthAmount;
    }

    public void setmonthAmount(String toString) {

        this.monthAmount = monthAmount;

    }
}
