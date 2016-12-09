package com.example.albert.albertmwanjesa_pset6;

import android.widget.TextView;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by albert on 6/12/2016.
 */

public class Garage implements Serializable{

    public String name;
    public String district;
    public float[] coordinates;
    public int shortCapacity;
    public int longCapacity;

    public Garage(ArrayList properties){
        this.name = (String) properties.get(0);
        this.district = (String) properties.get(1);
        this.coordinates = (float[]) properties.get(2);
        this.shortCapacity = (int) properties.get(3);
        this.longCapacity = (int) properties.get(4);
    }

    public String toString(){
        return name + district + coordinates + Integer.toString(shortCapacity) + Integer.toString(longCapacity);
    }

}
