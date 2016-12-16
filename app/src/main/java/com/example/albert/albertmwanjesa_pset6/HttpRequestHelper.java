package com.example.albert.albertmwanjesa_pset6;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Albert Mwanjesa 6/12/2016
 * This HttpRequestHelper requests data from the given API.
 * The data is processed further by the Asynctask.
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
                // Open connection, set request method
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                Integer responseCode = connection.getResponseCode();

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

            return result;

        }
        return result;
    }

}
