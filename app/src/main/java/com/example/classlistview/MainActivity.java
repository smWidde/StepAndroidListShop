package com.example.classlistview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ArrayList<Product> products = new ArrayList<>();
    Calendar cal = Calendar.getInstance();
    MyArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        products.add(new Product("Шляпа", 100, cal));
        products.add(new Product("Обувь", 100, cal));
        products.add(new Product("Глаз собаки", Integer.MAX_VALUE, cal));
        final ListView fruitsList = findViewById(R.id.NumbersListView);
        adapter = new MyArrayAdapter(this, products);
        fruitsList.setAdapter(adapter);

        fruitsList.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(getApplicationContext(), "You bought "+products.get(position), Toast.LENGTH_SHORT).show();
            products.remove(position);
            adapter.notifyDataSetChanged();
        });
    }
    public void AddClick(View view)
    {
        try {
            products.add(new Product(((EditText)findViewById(R.id.NameText)).getText().toString(), Integer.parseInt(((EditText)findViewById(R.id.PriceText)).getText().toString()), cal));
            adapter.notifyDataSetChanged();
        }
        catch (NumberFormatException ex)
        {
            Toast.makeText(this, "Number format exception", Toast.LENGTH_SHORT).show();
        }

    }
    DatePickerDialog.OnDateSetListener t= (view, year, month, dayOfMonth) -> {
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        ((TextView)findViewById(R.id.textViewDateValue)).setText(DateUtils.formatDateTime(this, cal.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE));
    };
    public void setTime(View v) {
        new DatePickerDialog(MainActivity.this, t, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();

    }

}