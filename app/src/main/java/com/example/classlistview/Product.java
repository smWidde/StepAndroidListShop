package com.example.classlistview;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    public Product(byte[] bytes)
    {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            Object o = in.readObject();
            Product p = (Product)o;
            this.Name = p.Name;
            this.Price = p.Price;
            this.Date = Calendar.getInstance();
            this.Date.set(Calendar.YEAR, p.Date.get(Calendar.YEAR));
            this.Date.set(Calendar.MONTH, p.Date.get(Calendar.MONTH));
            this.Date.set(Calendar.DAY_OF_MONTH, p.Date.get(Calendar.DAY_OF_MONTH));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
            }
        }
    }
    public byte[] getBytes()
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(this);
            out.flush();
            byte[] yourBytes = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
            }
        }
        return bos.toByteArray();
    }
}
