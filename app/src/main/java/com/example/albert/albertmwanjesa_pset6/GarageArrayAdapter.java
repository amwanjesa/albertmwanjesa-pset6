package com.example.albert.albertmwanjesa_pset6;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
* Albert Mwanjesa 15/12/2016
* This is an custom ArrayAdapter taking in a Garage list.
* It also has a filter feature. 
*/

public class GarageArrayAdapter extends ArrayAdapter<Garage> {
    private final Context context;
    private final ArrayList<Garage> values;
    private List<Garage> allGarages;

    public GarageArrayAdapter(Context context, ArrayList<Garage> values) {
        super(context, android.R.layout.simple_list_item_1, values);
        this.context = context;
        this.values = values;
        this.allGarages = new ArrayList<Garage>();
        allGarages.addAll(values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        TextView textView = (TextView) rowView.findViewById(android.R.id.text1);
        textView.setText(values.get(position).name);
        return rowView;
    }


    // Filter list items according to the entered serach String
    public void filter(String searchString) {
        searchString = searchString.toLowerCase(Locale.getDefault());
        values.clear();
        if (searchString.length() == 0) {
            values.addAll(allGarages);
        }
        else
        {
            for (Garage garage : allGarages)
            {
                if (garage.name.toLowerCase(Locale.getDefault()).contains(searchString))
                {
                    values.add(garage);
                }
            }
        }
        notifyDataSetChanged();
    }
}
