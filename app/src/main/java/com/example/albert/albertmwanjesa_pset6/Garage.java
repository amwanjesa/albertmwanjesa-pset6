package com.example.albert.albertmwanjesa_pset6;

import android.util.Log;
import android.widget.TextView;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by albert on 6/12/2016.
 */

public class Garage implements Serializable, Cloneable{

    public String name;
    public String district;
    public float[] coordinates;
    public int shortCapacity;
    public int longCapacity;
    public ArrayList properties;

    public Garage(ArrayList properties){
        this.name = (String) properties.get(0);
        Log.d("final name", this.name);
        this.district = (String) properties.get(1);
        this.coordinates = (float[]) properties.get(2);
        this.shortCapacity = (int) properties.get(3);
        this.longCapacity = (int) properties.get(4);
        this.properties = properties;
    }

    public  Garage(){
        this.name = "";
        this.district = "";
        this.coordinates = null;
        this.shortCapacity = 0;
        this.longCapacity = 0;
        this.properties = new ArrayList();
    }

    public String toString(){
        return name + district + coordinates + Integer.toString(shortCapacity) + Integer.toString(longCapacity);
    }



}
