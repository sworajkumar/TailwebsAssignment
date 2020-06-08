package com.cpetsolut.com.tailwebsassignment.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cpetsolut.com.tailwebsassignment.model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class Controllerdb extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "DETAILSDB";
    private static final String TABLE_Users = "detsilsTable";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SUBJECT = "subject";
    private static final String KEY_MARK = "mark";

    //Registration Page
    // User table name
    private static final String TABLE_USER = "user";
    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_MOBILENUMBER = "user_mobile";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    public Controllerdb(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_Users + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_SUBJECT + " TEXT,"
                + KEY_MARK + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);

        String CREATE_TABLE_REGI = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_EMAIL + " TEXT,"
                + COLUMN_USER_MOBILENUMBER + " TEXT,"
                + COLUMN_USER_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_REGI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
    public void insertUserDetails(String name, String subject, String mark) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        cValues.put(KEY_SUBJECT, subject);
        cValues.put(KEY_MARK, mark);
        long newRowId = db.insert(TABLE_Users, null, cValues);
        db.close();
    }

    public void UpdateUserDetails(String name, String subject, String mark) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        cValues.put(KEY_SUBJECT, subject);
        cValues.put(KEY_MARK, mark);
        String[] args = new String[]{"name", "subject"};
        db.update(TABLE_Users, cValues,"name=? AND subject=?" , args);
        db.close();
    }

    // Get User Details
    public ArrayList<HashMap<String, String>> getname() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT name FROM " + TABLE_Users;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> user = new HashMap<>();
            user.put("name", cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            userList.add(user);
        }
        return userList;
    }
    public ArrayList<HashMap<String, String>> getsub() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT subject FROM " + TABLE_Users;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> user = new HashMap<>();
            user.put("subject", cursor.getString(cursor.getColumnIndex(KEY_SUBJECT)));
            userList.add(user);
        }
        return userList;
    }
    public ArrayList<HashMap<String, String>> getmarks() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT mark FROM " + TABLE_Users;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> user = new HashMap<>();
            user.put("mark", cursor.getString(cursor.getColumnIndex(KEY_MARK)));
            userList.add(user);
        }
        return userList;
    }


    // Get User Details
    public ArrayList<HashMap<String, String>> GetUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT name,subject,mark FROM " + TABLE_Users;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> user = new HashMap<>();
            user.put("name", cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            user.put("subject", cursor.getString(cursor.getColumnIndex(KEY_SUBJECT)));
            user.put("mark", cursor.getString(cursor.getColumnIndex(KEY_MARK)));
            userList.add(user);
        }
        return userList;
    }

    public void addUser(String name, String email, String mobilenumber,String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME,name);
        values.put(COLUMN_USER_EMAIL,email);
        values.put(COLUMN_USER_MOBILENUMBER, mobilenumber);
        values.put(COLUMN_USER_PASSWORD, password);
        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public boolean checkUser(String mobilenumber) {
        String[] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_MOBILENUMBER + " = ?";
        String[] selectionArgs = {mobilenumber};
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean checkUser(String mobilenumber, String password) {

        String[] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_MOBILENUMBER + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = {mobilenumber, password};
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

}
