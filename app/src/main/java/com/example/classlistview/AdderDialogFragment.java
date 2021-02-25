package com.example.classlistview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class AdderDialogFragment extends DialogFragment {
    private Calendar cal;
    private FragmentActivity cont;
    private DialogAction act;
    private View myView;
    private ArrayList products;
    private String what_added;
    public AdderDialogFragment()
    {
        super();
        cont = getActivity();
    }
    public AdderDialogFragment(DialogAction act)
    {
        super();
        this.act = act;
    }
    @NonNull
    public Dialog onCreateDialog(Bundle savedInst)
    {
        cont = getActivity();
        products = new ArrayList();
        Bundle bundle = getArguments();
        if(bundle!=null)
        {
            cal = (Calendar)bundle.get("date");
            if(cal==null)
                cal = Calendar.getInstance();
            what_added = bundle.getString("what_added");
            if(what_added!=null)
            {
                if(what_added.equals("incomes"))
                {
                    products = ProductsSaveManager.GetIncomes(cont, cal);
                }
                else if(what_added.equals("costs"))
                {
                    products = ProductsSaveManager.GetCosts(cont, cal);
                }
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = cont.getLayoutInflater();
        myView = inflater.inflate(R.layout.adder, null);
        ((Button)myView.findViewById(R.id.buttonAdd)).setOnClickListener(this::AddProduct);
        return builder.setView(myView).create();
    }
    public void AddProduct(View view)
    {
        String name = ((TextView)myView.findViewById(R.id.editTextName)).getText().toString();
        Integer price = Integer.parseInt(((TextView)myView.findViewById(R.id.editTextPrice)).getText().toString());
        Product prod = new Product(name, price, cal);
        products.add(prod);
        if(what_added!=null)
        {
            if(what_added.equals("incomes"))
            {
                ProductsSaveManager.SaveIncomes(products, cont, cal);
            }
            else if(what_added.equals("costs"))
            {
                ProductsSaveManager.SaveCosts(products, cont, cal);
            }
        }
        act.Do();
        dismiss();
    }
}
