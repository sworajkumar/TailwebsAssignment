package com.cpetsolut.com.tailwebsassignment.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.cpetsolut.com.tailwebsassignment.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int WAIT_TIME = 1000;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;
    private static int TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, ImageSliderActivity.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);

    }
}
