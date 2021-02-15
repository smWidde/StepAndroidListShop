package com.example.classlistview;

import java.util.Calendar;

import androidx.annotation.NonNull;

public class Product
{
    public String Name;
    public Integer Price;
    public Calendar Date;
    public Product(String Name, Integer Price, Calendar Date)
    {
        this.Name=Name;
        this.Price=Price;
        this.Date = Calendar.getInstance();
        this.Date.set(Calendar.YEAR, Date.get(Calendar.YEAR));
        this.Date.set(Calendar.MONTH, Date.get(Calendar.MONTH));
        this.Date.set(Calendar.DAY_OF_MONTH, Date.get(Calendar.DAY_OF_MONTH));
    }
    @NonNull
    @Override
    public String toString() {
        return Name;
    }
}
