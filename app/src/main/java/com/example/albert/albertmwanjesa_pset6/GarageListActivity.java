package com.example.albert.albertmwanjesa_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


public class GarageListActivity extends AppCompatActivity {

    private ArrayList<Garage> garages;
    private GarageArrayAdapter adapter;
    private ListView lv;
    private EditText filterEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        filterByString();
        GarageAsyncTask garageTask = new GarageAsyncTask(this);
        garageTask.execute(getString(R.string.amsterdamGarageURL));



    }

    public void filterByString(){
        filterEditText = (EditText) findViewById(R.id.filter_string);
        filterEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                String text = filterEditText.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
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


    public void setGarages(ArrayList<Garage> foundGarages){
        garages = foundGarages;
        adapter = new GarageArrayAdapter(this, garages);
        lv = (ListView) findViewById(R.id.list_view);
        lv.setAdapter(adapter);
        //displayDoneTasks();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Garage thisGarage = (Garage) lv.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), thisGarage.name, Toast.LENGTH_SHORT).show();
                Intent mapIntent = new Intent(GarageListActivity.this, GarageMapsActivity.class);
                mapIntent.putExtra("garage", thisGarage);
                startActivity(mapIntent);


            }
        });
    }
}
