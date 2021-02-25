package com.example.classlistview;

import android.content.Context;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class ProductsSaveManager {
    final static String SPLITTER = "&♦◘♣9♦D◘6пA♦87";
    public static boolean SaveIncomes(ArrayList<Product> incomes, Context context, Calendar Date)
    {
        if(incomes!=null)
        {
            FileOutputStream fos = null;
            try {
                String res_string = "";
                ArrayList<Product> costs = GetCosts(context, Date);
                for(int i=0; i<incomes.size(); i++) {
                    res_string += incomes.get(i).Name+SPLITTER+incomes.get(i).Price+SPLITTER+"incomes\n";
                }
                for(int i=0; i<costs.size(); i++) {
                    res_string += costs.get(i).Name+SPLITTER+costs.get(i).Price+SPLITTER+"costs\n";
                }
                String path = Date.get(Calendar.YEAR)+"."+Date.get(Calendar.MONTH)+"."+Date.get(Calendar.DAY_OF_MONTH)+".txt";
                fos = context.openFileOutput(path, MODE_PRIVATE);
                fos.write(res_string.getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                if(fos!=null)
                {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    public static boolean SaveCosts(ArrayList<Product> costs, Context context, Calendar Date)
    {
        if(costs!=null)
        {
            FileOutputStream fos = null;
            try {

                String res_string = "";
                ArrayList<Product> incomes = GetCosts(context, Date);
                for(int i=0; i<costs.size(); i++) {
                    res_string += costs.get(i).Name+SPLITTER+costs.get(i).Price+SPLITTER+"costs\n";
                }
                for(int i=0; i<incomes.size(); i++) {
                    res_string += incomes.get(i).Name+SPLITTER+incomes.get(i).Price+SPLITTER+"incomes\n";
                }
                String path = Date.get(Calendar.YEAR)+"."+Date.get(Calendar.MONTH)+"."+Date.get(Calendar.DAY_OF_MONTH)+".txt";
                fos = context.openFileOutput(path, MODE_PRIVATE);
                fos.write(res_string.getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                if(fos!=null)
                {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    public static ArrayList<Product> GetIncomes(Context context, Calendar date)
    {
        ArrayList<Product> arr = new ArrayList<>();
        String path = date.get(Calendar.YEAR)+"."+date.get(Calendar.MONTH)+"."+date.get(Calendar.DAY_OF_MONTH)+".txt";
        FileInputStream fin = null;
        try {
            fin = context.openFileInput(path);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String res = new String(bytes);
            String[] objs = res.split("\\n");
            for(String i: objs)
            {
                String[] curr_obj = i.split(SPLITTER);
                if(curr_obj.length==3)
                {
                    if(curr_obj[2].equals("incomes"))
                    {
                        arr.add(new Product(curr_obj[0], Integer.parseInt(curr_obj[1]), date));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }
    public static ArrayList<Product> GetCosts(Context context, Calendar date)
    {
        ArrayList<Product> arr = new ArrayList<>();
        String path = date.get(Calendar.YEAR)+"."+date.get(Calendar.MONTH)+"."+date.get(Calendar.DAY_OF_MONTH)+".txt";
        FileInputStream fin = null;
        try {
            fin = context.openFileInput(path);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String res = new String(bytes);
            String[] objs = res.split("\\n");
            for(String i: objs)
            {
                String[] curr_obj = i.split(SPLITTER);
                if(curr_obj.length==3)
                {
                    if(curr_obj[2].equals("costs"))
                    {
                        arr.add(new Product(curr_obj[0], Integer.parseInt(curr_obj[1]), date));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }
}
