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
        // change the icon for Windows and iPhone
        return rowView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        values.clear();
        if (charText.length() == 0) {
            values.addAll(allGarages);
        }
        else
        {
            for (Garage garage : allGarages)
            {
                if (garage.name.toLowerCase(Locale.getDefault()).contains(charText))
                {
                    values.add(garage);
                }
            }
        }
        notifyDataSetChanged();
    }
}

