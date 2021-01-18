package com.example.notesapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    static ArrayList<String> notes = new ArrayList<>();
    Intent intent;
    static ArrayAdapter arrayAdapter;
    static ArrayList<String> newNotes = new ArrayList<>();

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.addNote:
                intent = new Intent(this, EditNote.class);
                intent.putExtra("notes", "newNote");
                startActivity(intent);
                return true;
            default:
                Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
                return false;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notesapp", Context.MODE_PRIVATE);

        try {
            newNotes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("newNotes", ObjectSerializer.serialize(new ArrayList<>())));
            System.out.println(newNotes.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, newNotes);
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            String notesAtI = newNotes.get(i);
            intent = new Intent(this, EditNote.class);

            intent.putExtra("notes", notesAtI);
            intent.putExtra("listValue", i);
            startActivity(intent);
        });


//        notes.add("Example notes");
        System.out.println(newNotes.toString());

        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Are you sure")
                    .setMessage("Do you want to delete?")
                    .setPositiveButton("Yes", (dialogInterface, i1) -> delete(i))
                    .setNegativeButton("No", null)
                    .show();


            return true;
        });


    }

    public void delete(int i) {
        newNotes.remove(i);
        arrayAdapter.notifyDataSetChanged();
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notesapp", Context.MODE_PRIVATE);
        try {
            sharedPreferences.edit().putString("newNotes", ObjectSerializer.serialize(newNotes)).commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}