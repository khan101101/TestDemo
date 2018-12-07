package com.example.ahmadn.testdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button bt;

    Firebase url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = (Button) findViewById(R.id.button);

        Firebase.setAndroidContext(this);

        url = new Firebase ("https://testdemo-3e073.firebaseio.com/");

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Firebase firebase = url.child("namee");
                firebase.setValue("Khan sahib");
            }
        });

        // Write a message to the database
       // FirebaseDatabase database = FirebaseDatabase.getInstance();
       // DatabaseReference myRef = database.getReference("message");

        //myRef.setValue("Hello, World!");
    }
}
