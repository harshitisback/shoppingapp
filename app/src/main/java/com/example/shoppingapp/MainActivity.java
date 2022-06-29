package com.example.shoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    String name, id, pass;

    View Rectanle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Rectanle = (View) findViewById(R.id.rectangle_1);

        Rectanle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}