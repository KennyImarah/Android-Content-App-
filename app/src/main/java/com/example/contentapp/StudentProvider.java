package com.example.contentapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.CancellationSignal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;

public class StudentProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.example.contentapp.StudentProvider";  // PROVIDER_NAME object set on package.file
    static final String URL =    "content//" + PROVIDER_NAME + "/students";  // URL object set
    static final Uri CONTENT_URI = Uri.parse(URL);   // CONTENT_URI object (parse uri on URL

    static final String _ID = "_id";       // _ID object
    static final String NAME = "name";     // NAME object
    static final String GRADE = "grade";   //GRADE object

    //HashMap holds key and value
    static HashMap<String , String > STUDENTS_MAP;   // HasMap STUDENT_MAP object

    static final int STUDENTS = 1;   //STUDENTS OBJECT
    static final int STUDENT_ID = 2;   //STUDENT_ID object
    static final UriMatcher uriMatcher;   //uriMatcher object

    // uriMatcher assigned
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "students", STUDENTS);   //addURI with STUDENTS
        uriMatcher.addURI(PROVIDER_NAME, "students/#", STUDENT_ID);   //addURI with STUDENTS_ID
    }


    private SQLiteDatabase db;    // SQLiteDatabase object
    static final String DATABASE_NAME = "College";     // database name assignment
    static final String TABLE_NAME = "students";       // table name assignment
    static final int DATABASE_VERSION = 1;             // database version assignment
    //assign CREATE_DB_TABLE object
    static final String CREATE_DB_TABLE = "CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + " name TEXT NOT NULL, " +
            " grade TEXT NOT NULL );";

    //DatabaseHelper class
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context c) {  //class constructor
            super(c, DATABASE_NAME, null, DATABASE_VERSION);  // call db name and version on super

        }

        //onCreate method with parameter
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);  //call CREATE_DB_TABLE on db.execSQL
        }

        //onUpgrade method
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);  // check table version
            onCreate(db);  //call onCreate with db
        }
    }

    //override onCreate
    @Override
    public boolean onCreate() {

        Context context = getContext();  // context object
        DatabaseHelper dbHelper = new DatabaseHelper(context); // dbHelper object

        db = dbHelper.getWritableDatabase(); // sssign dbHelper.getWritable on db
        return (db == null)? false: true;  // check and return db
    }

    // override Cursor query
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    // override getType
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    // override Uri insert
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long rowID = db.insert(TABLE_NAME, null, values);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);

        }
        throw new SQLException("failed to add a record into " + uri);
    }

    // override delete
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    // override update
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
