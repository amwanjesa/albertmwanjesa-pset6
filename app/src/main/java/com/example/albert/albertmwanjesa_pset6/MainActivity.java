package com.example.albert.albertmwanjesa_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ArrayList<Garage> garages;
    private GarageArrayAdapter adapter;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GarageAsyncTask garageTask = new GarageAsyncTask(this);
        garageTask.execute(getString(R.string.amsterdamGarageURL));


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
                Intent mapIntent = new Intent(MainActivity.this, GarageMapsActivity.class);
                mapIntent.putExtra("garage", thisGarage);
                startActivity(mapIntent);


            }
        });
    }
}
