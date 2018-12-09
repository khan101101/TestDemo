package com.example.ahmadn.testdemo.models;


import com.google.type.LatLng;

import java.util.ArrayList;

public class DatabaseHandler {
    // Variables for tables
    public String userMail, artDescription, cat;
    public ArrayList<String> picIds;
    public LatLng loc;
    public double price;

    public DatabaseHandler(){
        userMail = null;
        artDescription = null;
        loc = null;
        cat = null;
        picIds = null;
        price = 0;
    }

    public void search_user(DatabaseHandler current_user){
        // Read from Firestore to identicate the current user
    }

}

