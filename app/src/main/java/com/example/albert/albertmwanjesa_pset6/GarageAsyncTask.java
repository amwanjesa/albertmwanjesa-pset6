package com.example.albert.albertmwanjesa_pset6;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by albert on 6/12/2016.
 */

class GarageAsyncTask extends AsyncTask<String, Integer, String> {

    Context context;
    MainActivity activity;


    public GarageAsyncTask(MainActivity activity){
        this.activity = activity;
        this.context = this.activity.getApplicationContext();
    }

    @Override
    protected String doInBackground(String... params) {
        return HttpRequestHelper.downloadFromServer(params[0]);
    }

    protected void onPreExecute() {
        Toast.makeText(context, "getting garages", Toast.LENGTH_SHORT).show();
    }

    // onPostExecute()
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result.length() == 0) {
            Toast.makeText(context, "Garage not found.", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                JSONObject respObj = new JSONObject(result);
                JSONArray garageList = respObj.getJSONArray("features");
                ArrayList<Garage> garages= new ArrayList<Garage>();
                ArrayList nextGarage = new ArrayList();
                for (int i = 0; i < garageList.length(); i++) {
                    JSONObject thisGarage = (JSONObject) garageList.get(i);
                    JSONObject itsGeometry = thisGarage.getJSONObject("geometry");
                    JSONObject itsProperties = thisGarage.getJSONObject("properties");
                    String[] parts = thisGarage.getString("Id").split(" ", 2);
                    nextGarage.add(parts[1]);
                    nextGarage.add(parts[0]);
                    String coords = itsGeometry.get("coordinates").toString();
                    nextGarage.add(stringToIntArray(coords));
                    nextGarage.add(itsProperties.getInt("ShortCapacity"));
                    nextGarage.add(itsProperties.getInt("LongCapacity"));
                    Garage newGarage = new Garage(nextGarage);
                    garages.add(newGarage);
                    nextGarage.clear();
                }

                this.activity.setGarages(garages);


            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private float[] stringToIntArray(String coordinates){
        coordinates = coordinates.replace("[", "");
        coordinates = coordinates.replace("]", "");
        coordinates = coordinates.replace(",", " ");
        Log.d("regex_coords", coordinates);
        String[] split_coords = coordinates.split("\\s+");
        Log.d("coords", Arrays.toString(split_coords));
        float[] finalCoords = new float[2];
        finalCoords[0] = Float.parseFloat(split_coords[1]);
        finalCoords[1] = Float.parseFloat(split_coords[0]);
        return finalCoords;
    }
}

