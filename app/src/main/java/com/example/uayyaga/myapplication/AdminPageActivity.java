package com.example.uayyaga.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uayyaga on 11/4/16.
 */

public class AdminPageActivity extends AppCompatActivity {
    public static ArrayList<String> ArrayofName = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpage);
        DBHelper dbHelper=new DBHelper(AdminPageActivity.this);
        ListView listView = (ListView) findViewById(R.id.userdb_list);
        //creating list to populate the data from database
      List<UserDB> listUser=  dbHelper.getAllUser();
        dbHelper.getAllUser();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ArrayofName);
        listView.setAdapter(adapter);
    }
}
