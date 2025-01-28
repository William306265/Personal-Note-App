package com.example.personalnoteapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {
    EditText edTitle, edDesc;
    Button button;
    NoteHelper noteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        edTitle=findViewById(R.id.edTitle);
        edDesc = findViewById(R.id.edDesc);
        button = findViewById(R.id.addButton);
        noteHelper = new NoteHelper(MainActivity2.this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edTitle.length()>0 && edDesc.length()>0){
                    noteHelper.insertData(edTitle.getText().toString(),edDesc.getText().toString());
                    edDesc.setText("");
                    edTitle.setText("");
                    startActivity(new Intent(MainActivity2.this,MainActivity.class));

                }
                else {
                    Toast.makeText(MainActivity2.this, "Add a Note First", Toast.LENGTH_SHORT).show();
                }

            }
        });
        





    }
}