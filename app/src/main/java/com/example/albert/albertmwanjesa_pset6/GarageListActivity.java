package com.example.albert.albertmwanjesa_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;

/**
* In this activity, the list of parking garages is displayed with an option to filter the list. 
*
*/


public class GarageListActivity extends AppCompatActivity {

    private ArrayList<Garage> mGarages;
    private GarageArrayAdapter mAdapter;
    private ListView mListView;
    private EditText mFilterText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GarageAsyncTask garageTask = new GarageAsyncTask(this);
        garageTask.execute(getString(R.string.amsterdamGarageURL));

    }

    // Check for filter string and filter accordingly.
    public void filterByString(){
        mFilterText = (EditText) findViewById(R.id.filter_string);
        mFilterText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                String text = mFilterText.getText().toString().toLowerCase(Locale.getDefault());
                mAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

        });
    }

    // Setting the data ad initiating the list view.
    public void setGarages(ArrayList<Garage> foundGarages){
        mGarages = foundGarages;
        mAdapter = new GarageArrayAdapter(this, mGarages);
        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> mAdapterView, View view, int position, long id) {
                Garage thisGarage = (Garage) mListView.getItemAtPosition(position);
                Intent mapIntent = new Intent(GarageListActivity.this, GarageMapsActivity.class);
                mapIntent.putExtra("garage", thisGarage);
                startActivity(mapIntent);


            }
        });

        filterByString();
    }
}
