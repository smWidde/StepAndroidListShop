package com.example.classlistview;

import android.app.Activity;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<Product> {
    private final Activity context;
    private final List<Product> list;
    public MyArrayAdapter(Activity context, List<Product> list)
    {
        super(context,R.layout.list_view_layout, list);
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_view_layout, null, true);
        ((TextView)rowView.findViewById(R.id.nameValue)).setText(list.get(position).Name);
        ((TextView)rowView.findViewById(R.id.priceValue)).setText(list.get(position).Price.toString()+"₴");
        ((TextView)rowView.findViewById(R.id.dateValue)).setText(DateUtils.formatDateTime(context, list.get(position).Date.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE));
        return rowView;
    }
}
