package com.example.personalnoteapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton floatingId;
    RecyclerView recyclerView;
    ArrayList<NoteModel> arrayList = new ArrayList<>();
    NoteHelper noteHelper;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        floatingId = findViewById(R.id.floatingId);
        recyclerView = findViewById(R.id.recyclerView);
        noteHelper = new NoteHelper(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Cursor cursor = noteHelper.showData();


        while(cursor.moveToNext()){
            arrayList.add(new NoteModel(cursor.getString(1),cursor.getString(2),cursor.getInt(0)));
        }

        NoteAdapter adapter = new NoteAdapter(this,arrayList);
        recyclerView.setAdapter(adapter);
        adapter.attachSwipeHelper(recyclerView);




        floatingId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivity2.class));

            }
        });

    }
}