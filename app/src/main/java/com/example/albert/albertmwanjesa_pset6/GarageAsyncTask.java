package com.example.albert.albertmwanjesa_pset6;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Albert Mwanjesa 16/12/2016
 * This class takes care of getting all the data for parking garages in Amsterdam.
 * using the HttpRequestHelper. The JSONObject is altered to get garages after
 * execution.
 */

class GarageAsyncTask extends AsyncTask<String, Integer, String> {

    Context context;
    GarageListActivity activity;


    public GarageAsyncTask(GarageListActivity activity){
        this.activity = activity;
        this.context = this.activity.getApplicationContext();
    }

    @Override
    protected String doInBackground(String... params) {
        return HttpRequestHelper.downloadFromServer(params[0]);
    }

    protected void onPreExecute() {
        // Toast.makeText(context, R.string.find_parking, Toast.LENGTH_SHORT).show();
    }

    // onPostExecute()
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result.length() == 0) {
            Toast.makeText(context, R.string.not_found_parking, Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                JSONObject respObj = new JSONObject(result);
                JSONArray garageList = respObj.getJSONArray("features");
                ArrayList<Garage> garages= new ArrayList<Garage>();
                ArrayList nextGarage = new ArrayList();

                // Taking apart each JSONObject in the JSONArray to
                // to obtain Garage properties.
                for (int i = 0; i < garageList.length(); i++) {
                    JSONObject thisGarage = (JSONObject) garageList.get(i);
                    JSONObject itsGeometry = thisGarage.getJSONObject("geometry");
                    JSONObject itsProperties = thisGarage.getJSONObject("properties");
                    String[] parts = thisGarage.getString("Id").split(" ", 2);
                    String[] idParts = parts[0].split("-");
                    if(idParts.length > 1 && idParts[1].length() > 1){
                        String name = parts[1] + " " + idParts[1];
                        nextGarage.add(name);
                    }else{
                        nextGarage.add(parts[1]);
                    }

                    nextGarage.add(parts[0]);
                    String coords = itsGeometry.get("coordinates").toString();
                    nextGarage.add(stringToFloatArray(coords));
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

    private float[] stringToFloatArray(String coordinates){
        coordinates = coordinates.replace("[", "");
        coordinates = coordinates.replace("]", "");
        coordinates = coordinates.replace(",", " ");
        String[] split_coords = coordinates.split("\\s+");
        float[] finalCoords = new float[2];
        finalCoords[0] = Float.parseFloat(split_coords[1]);
        finalCoords[1] = Float.parseFloat(split_coords[0]);
        return finalCoords;
    }
}
