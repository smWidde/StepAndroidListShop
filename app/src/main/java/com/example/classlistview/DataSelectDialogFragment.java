package com.example.classlistview;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.Calendar;



public class DataSelectDialogFragment extends DialogFragment {
    private class PriceDate
    {
        Calendar cal;
        Integer price;
        public PriceDate()
        {
            cal = Calendar.getInstance();
            price = 0;
        }
        public PriceDate(Calendar cal, Integer price)
        {
            this.cal = cal;
            this.price = price;
        }

        @NonNull
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Дата: ");
            sb.append(cal.get(Calendar.YEAR));
            sb.append(".");
            sb.append(cal.get(Calendar.MONTH));
            sb.append(".");
            sb.append(cal.get(Calendar.DAY_OF_MONTH));
            sb.append("\nЦена: ");
            sb.append(price);
            return sb.toString();
        }
    }
    private Calendar cal = Calendar.getInstance();
    private FragmentActivity cont;
    private DialogAction act;
    private ArrayList<PriceDate> arr;
    private View myView;
    public DataSelectDialogFragment()
    {
        super();
        cont = getActivity();
    }
    public DataSelectDialogFragment(DialogAction act)
    {
        super();
        this.act = act;
    }
    @NonNull
    public Dialog onCreateDialog(Bundle savedInst)
    {
        cont = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = cont.getLayoutInflater();
        myView = inflater.inflate(R.layout.choose_date, null);
        ListView lv = myView.findViewById(R.id.listViewOccupiedDates);
        String[] files = cont.fileList();
        arr = new ArrayList<>();
        for(String i : files)
        {
            Calendar cal = getDateFromString(i);
            Integer overall = getOverPrice(ProductsSaveManager.GetIncomes(cont, cal)) - getOverPrice(ProductsSaveManager.GetCosts(cont, getDateFromString(i)));
            arr.add(new PriceDate(cal, overall));
        }
        for(int i=0; i<arr.size(); i++)
        {
            for(int j=0; j<arr.size(); j++)
            {
                if(arr.get(i).cal.get(Calendar.YEAR)>arr.get(j).cal.get(Calendar.YEAR))
                {
                    if(arr.get(i).cal.get(Calendar.YEAR)>arr.get(j).cal.get(Calendar.YEAR))
                    {
                        if(arr.get(i).cal.get(Calendar.YEAR)<arr.get(j).cal.get(Calendar.YEAR))
                        {
                            swap(arr, i, j);
                        }
                    }
                    else
                        swap(arr, i, j);
                }
                else
                    swap(arr, i, j);
            }
        }
        if(getArguments()!=null)
        {
            cal = (Calendar)getArguments().get("date");
            if(cal==null)
                cal = Calendar.getInstance();
            ((Button)myView.findViewById(R.id.buttonDatePicker)).setText(DateUtils.formatDateTime(cont, cal.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE));
        }

        ArrayAdapter<PriceDate> aapd = new ArrayAdapter<PriceDate>(cont, R.layout.support_simple_spinner_dropdown_item, arr);
        lv.setAdapter(aapd);
        ((Button)myView.findViewById(R.id.buttonDatePicker)).setOnClickListener((view)->{
            new DatePickerDialog(cont, t, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
        });
        lv.setOnItemClickListener((parent, view, position, id) -> {
            cal = arr.get(position).cal;
            ((Button)myView.findViewById(R.id.buttonDatePicker)).setText(DateUtils.formatDateTime(cont, cal.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE));
        });
        ((Button)myView.findViewById(R.id.buttonSubmit)).setOnClickListener((view)->{

            ImageView img = myView.findViewById(R.id.imageView);
            img.setVisibility(View.VISIBLE);
            img.setBackgroundResource(R.drawable.calendar_animation);
            final AnimationDrawable myAnim = (AnimationDrawable)img.getBackground();
            myAnim.start();
            view.postDelayed(()-> {
                    Intent intent = new Intent(cont, MainActivity.class);
                    intent.putExtra("date", cal);
                    cont.startActivity(intent);
            }, 500);
        });
        return builder.setView(myView).setTitle("Выберите дату").create();
    }
    private void swap(ArrayList list, int first, int second)
    {
        Object tmp = list.get(first);
        list.set(first, list.get(second));
        list.set(second, tmp);
    }
    private Integer getOverPrice(ArrayList<Product> arr)
    {
        Integer money = 0;
        for(Product i : arr)
        {
            money += i.Price;
        }
        return money;
    }

    private Calendar getDateFromString(String file_name)
    {
        String[] filename = file_name.split("\\.");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(filename[0]));
        cal.set(Calendar.MONTH, Integer.parseInt(filename[1]));
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(filename[2]));
        return cal;
    }
    DatePickerDialog.OnDateSetListener t = (view, year, month, dayOfMonth) -> {
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        ((Button)myView.findViewById(R.id.buttonDatePicker)).setText(DateUtils.formatDateTime(cont, cal.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE));
    };
    public void setTime(View v) {
    }
}
