package com.example.classlistview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private Calendar cal = Calendar.getInstance();
    private Button buttonDate;
    private ArrayList<Product> costs;
    private ArrayList<Product> incomes;
    private ListView costs_lv;
    private ListView incomes_lv;
    private MyArrayAdapter costs_adapter;
    private MyArrayAdapter incomes_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonDate = findViewById(R.id.buttonDate);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Calendar tmp_cal = (Calendar)bundle.get("date");
            if(tmp_cal!=null)
            {
                cal = tmp_cal;
            }
        }
        SetTime();
        costs = ProductsSaveManager.GetCosts(this, cal);
        incomes = ProductsSaveManager.GetIncomes(this, cal);
        costs_lv = findViewById(R.id.listCosts);
        incomes_lv = findViewById(R.id.listIncomes);
        costs_adapter = new MyArrayAdapter(this, costs);
        incomes_adapter = new MyArrayAdapter(this, incomes);
        costs_lv.setAdapter(costs_adapter);
        incomes_lv.setAdapter(incomes_adapter);
        CountPrice();
        costs_lv.setOnItemClickListener((parent, view, position, id) -> {
            DeleteDialogFragment ddf = new DeleteDialogFragment(()->{
                Toast.makeText(getApplicationContext(), costs.get(position).Name + " удалено", Toast.LENGTH_SHORT).show();
                costs.remove(position);
                ProductsSaveManager.SaveCosts(costs, this, cal);
                costs_adapter.notifyDataSetChanged();
                CountPrice();
            });
            ddf.show(getFragmentManager(), "custom");
        });
        incomes_lv.setOnItemClickListener((parent, view, position, id) -> {
            DeleteDialogFragment ddf = new DeleteDialogFragment(()->{
                Toast.makeText(getApplicationContext(), incomes.get(position).Name + " удалено", Toast.LENGTH_SHORT).show();
                incomes.remove(position);
                ProductsSaveManager.SaveIncomes(incomes, this, cal);
                incomes_adapter.notifyDataSetChanged();
                CountPrice();
            });
            ddf.show(getFragmentManager(), "custom");
        });

    }
    public void SetTime()
    {
        buttonDate.setText(DateUtils.formatDateTime(this, cal.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE));
    }
    DatePickerDialog.OnDateSetListener t = (view, year, month, dayOfMonth) -> {
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SetTime();
    };
    public void SetDate(View view)
    {
        DataSelectDialogFragment dsdf = new DataSelectDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("date", cal);
        dsdf.setArguments(bundle);
        dsdf.show(getSupportFragmentManager(), "myDatePicker");
    }
    public void CallAdder(View view)
    {
        AdderDialogFragment adf = new AdderDialogFragment(this::SaveChanges);
        Bundle bundle = new Bundle();
        if(view.equals(findViewById(R.id.buttonCostsAdd)))
        {
            bundle.putSerializable("date", cal);
            bundle.putSerializable("what_added", "costs");
            adf.setArguments(bundle);
            adf.show(getSupportFragmentManager(),"custom");
        }
        if(view.equals(findViewById(R.id.buttonIncomesAdd)))
        {
            bundle.putSerializable("date", cal);
            bundle.putSerializable("what_added", "incomes");
            adf.setArguments(bundle);
            adf.show(getSupportFragmentManager(),"custom");
        }
    }
    private void SaveChanges()
    {
        ChangeCollection(costs, ProductsSaveManager.GetCosts(this, cal));
        costs_adapter.notifyDataSetChanged();
        ChangeCollection(incomes, ProductsSaveManager.GetIncomes(this, cal));
        incomes_adapter.notifyDataSetChanged();
        CountPrice();
    }
    private void CountPrice()
    {
        Integer money = 0;
        for(Product i : incomes)
        {
            money += i.Price;
        }
        for(Product i : costs)
        {
            money -= i.Price;
        }
        ((TextView)findViewById(R.id.textViewOverallValue)).setText(money.toString()+"₴");
    }
    private void ChangeCollection(ArrayList<Product> olds, ArrayList<Product> news)
    {
        olds.clear();
        for(Product i : news)
        {
            olds.add(i);
        }
    }

}