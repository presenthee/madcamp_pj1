package com.example.madcamp_pj1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Empty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        finish();
    }
}