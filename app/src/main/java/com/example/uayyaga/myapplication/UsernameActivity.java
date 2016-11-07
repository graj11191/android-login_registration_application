package com.example.uayyaga.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import static android.R.id.message;

/**
 * Created by uayyaga on 10/24/16.
 */

public class UsernameActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);
        Intent intent = getIntent();
        String str = intent.getStringExtra("KEY");
        TextView user = (TextView) findViewById(R.id.username_textview);
        user.setText(str);
    }
}