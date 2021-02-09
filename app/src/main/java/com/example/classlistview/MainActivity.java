package com.example.classlistview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
class Product
{
    public String Name;
    public Integer Price;
    public Product(String Name, Integer Price)
    {
        this.Name=Name;
        this.Price=Price;
    }
    @NonNull
    @Override
    public String toString() {
        return "Name: "+Name+"\t"+"Price: "+Price+"₴";
    }
}
public class MainActivity extends AppCompatActivity {

    ArrayList<Product> products = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        products.add(new Product("Шляпа", 100));
        products.add(new Product("Обувь", 100));
        products.add(new Product("Глаз собаки", Integer.MAX_VALUE));
        final ListView fruitsList = (ListView) findViewById(R.id.NumbersListView);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, products);
        fruitsList.setAdapter(adapter);

        fruitsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "You bought "+products.get(position), Toast.LENGTH_SHORT).show();
                products.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
    }
    public void AddClick(View view)
    {
        products.add(new Product(((EditText)findViewById(R.id.NameText)).getText().toString(), Integer.parseInt(((EditText)findViewById(R.id.PriceText)).getText().toString())));
    }
}