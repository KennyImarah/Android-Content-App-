package com.example.contentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickRetrieveStudents(View view) {

    }

    public void onClickAddName(View view) {
        // add new record

        ContentValues values = new ContentValues();
        values.put(StudentProvider.NAME, ((EditText) findViewById(R.id.editText2)).getText().toString());
        values.put(StudentProvider.GRADE, ((EditText) findViewById(R.id.editText3)).getText().toString());

        Uri uri = getContentResolver().insert(StudentProvider.CONTENT_URI, values);

        Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
    }
}
