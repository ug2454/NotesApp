package com.example.notesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

public class EditNote extends AppCompatActivity {
    Intent intent = new Intent();
    EditText editText;
    String finalNote = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        editText = findViewById(R.id.editText);
        intent = getIntent();
        SharedPreferences sharedPreferences= this.getSharedPreferences("com.example.notesapp", Context.MODE_PRIVATE);

        String editNotes = intent.getStringExtra("notes");
        if (editNotes.equals("newNote")) {
            MainActivity.newNotes.add("");

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                    MainActivity.newNotes.set(MainActivity.newNotes.size()-1, editable.toString());
                    MainActivity.arrayAdapter.notifyDataSetChanged();
                    try {

                        sharedPreferences.edit().putString("newNotes",ObjectSerializer.serialize(MainActivity.newNotes)).apply();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                }
            });



        } else {
            Log.i("notes", intent.getStringExtra("notes"));
            int listValue = intent.getIntExtra("listValue", 0);
            editText.setText(editNotes);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    MainActivity.newNotes.set(listValue, editable.toString());
                    MainActivity.arrayAdapter.notifyDataSetChanged();
                    try {

                        sharedPreferences.edit().putString("newNotes",ObjectSerializer.serialize(MainActivity.newNotes)).apply();
                        System.out.println(MainActivity.newNotes);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });



        }


    }
}