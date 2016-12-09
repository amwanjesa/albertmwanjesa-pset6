package com.example.albert.albertmwanjesa_pset6;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by albert on 15/11/2016.
 */
public class HttpRequestHelper {

    protected static synchronized String downloadFromServer(String urlString) {

        // declare return string result
        String result = "";


        // turn string into url
        URL url;
        try {
            url = new URL(urlString);
        } catch (java.net.MalformedURLException e) {
            url = null;
            e.printStackTrace();
        }
        // make the connection
        HttpURLConnection connection;
        if (url != null) {
            try {
                // open connection, set request method
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // get response code
                Integer responseCode = connection.getResponseCode();

                // if 200-300, read inputstream
                if (200 <= responseCode && responseCode <= 299) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = br.readLine()) != null) {
                        result = result + line;
                    }
                }
                else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    // communicate correct error
                }
            }catch (java.io.IOException e){
                e.printStackTrace();
            }
            // return result
            Log.d("got eem", result);
            return result;

        }
        return result;
    }

}
