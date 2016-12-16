package com.example.albert.albertmwanjesa_pset6;

import android.util.Log;
import android.widget.TextView;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Albert Mwanjesa 6/12/2016.
 * This is the Garage class. It contains the garage's name and other properties
 * such as short and long capacity and coordinates in latitude and longitude.
 */

public class Garage implements Serializable, Cloneable{

    public String name;
    public String id;
    public float[] coordinates;
    public int shortCapacity;
    public int longCapacity;

    public Garage(ArrayList properties){
        this.name = (String) properties.get(0);
        Log.d("final name", this.name);
        this.id = (String) properties.get(1);
        this.coordinates = (float[]) properties.get(2);
        this.shortCapacity = (int) properties.get(3);
        this.longCapacity = (int) properties.get(4);
    }

    public  Garage(){
        this.name = "";
        this.id = "";
        this.coordinates = null;
        this.shortCapacity = 0;
        this.longCapacity = 0;
    }

    public String toString(){
        return name + id + coordinates + Integer.toString(shortCapacity) + Integer.toString(longCapacity);
    }



}
