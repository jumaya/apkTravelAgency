package com.example.travel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.travel.Asyntask.ClientAsyntask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new ClientAsyntask(this).execute();
    }
}
