package com.logistics.alucard.sqliteimgapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Toast.makeText(this, "The new Activity", Toast.LENGTH_SHORT).show();
    }
}
