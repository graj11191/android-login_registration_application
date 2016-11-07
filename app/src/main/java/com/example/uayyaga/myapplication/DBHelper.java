package com.example.uayyaga.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uayyaga on 11/02/16.
 */

public class DBHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "UserDb.db";
    public SQLiteDatabase db;
    DBHelper dbhelp;
    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }
    // Called when no database exists in disk and the helper class needs
    // to create a new one.
    @Override
    public void onCreate(SQLiteDatabase _db)
    {
        _db.execSQL("create table "+"LOGIN"+
                "( " +"ID"+" integer primary key autoincrement,"+ "USERNAME  text, FATHERNAME TEXT, DATEOFBIRTH text, AGE text, RELIGION text, SEX text, EMAIL text, PASSWORD text, LANGUAGE text); "
        );

    }
    // Called when there is a database version mismatch meaning that the version
    // of the database on disk needs to be upgraded to the current version.
    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion)
    {
        // Log the version upgrade.
        Log.w("TaskDBAdapter", "Upgrading from version " +_oldVersion + " to " +_newVersion + ", which will destroy all old data");

        // The simplest case is to drop the old table and create a new one.
        _db.execSQL("DROP TABLE IF EXISTS " + "LOGIN");
        // Create a new one.
        onCreate(_db);
    }
    public  DBHelper open() throws SQLException
    {
        db = this.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }
    public String getSinlgeEntry(String uname)
    {
        Cursor cursor=db.query("LOGIN", null, " EMAIL=?", new String[]{uname}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
        Log.d("Password:",password);
        cursor.close();
        return password;

    }

    //inserting data into db
    public void insertData(String uname, String fname, String dob, String age, String religion, String sex, String email1, String pwd, String language)
    {
        db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("USERNAME", uname);
        contentValues.put("FATHERNAME",fname);
        contentValues.put("DATEOFBIRTH",dob);
        contentValues.put("AGE",age);
        contentValues.put("RELIGION",religion);
        contentValues.put("SEX",sex);
        contentValues.put("EMAIL",email1);
        contentValues.put("PASSWORD",pwd);
        contentValues.put("LANGUAGE",language);
        // Insert the row into your table
        db.insert("LOGIN", null, contentValues);
        db.close();
    }
    public List<UserDB> getAllUser() {
        List<UserDB> contactList = new ArrayList<UserDB>();
        // Select All Query
        String selectQuery = "SELECT  * FROM LOGIN";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserDB userdetail = new UserDB();
                userdetail.setId(cursor.getString(0));
                userdetail.setUname(cursor.getString(1));
                userdetail.setFname(cursor.getString(2));
                userdetail.setDob(cursor.getString(3));
                userdetail.setAge(cursor.getString(4));
                userdetail.setReligion(cursor.getString(5));
                userdetail.setSex(cursor.getString(6));
                userdetail.setEmail(cursor.getString(7));
                userdetail.setPassword(cursor.getString(8));
                userdetail.setLanguage(cursor.getString(9));

                //displaying the loaded date
                String name = "Name: "+ cursor.getString(1) +"\nFather's Name: "+ cursor.getString(2)+"\nDOB: "+ cursor.getString(3)+"  Age:"+ cursor.getString(4)+"\nReligion: "+ cursor.getString(5)+"  Sex: "+ cursor.getString(6)+"\nEmail ID: "+ cursor.getString(7)+"\nPassword: "+ cursor.getString(8)+"\nLanguages "+ cursor.getString(9);
               Log.d("admin page",name);
                AdminPageActivity.ArrayofName.add(name);
                // Adding contact to list
                contactList.add(userdetail);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
}