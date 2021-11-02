package com.example.Course_Note_Taking.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.Course_Note_Taking.Model.Note;
import com.example.Course_Note_Taking.R;
import com.example.Course_Note_Taking.helper.CustomListAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    // Initialize the buttons for switching the activities
    private Button buttonAndroid, buttonArchitecture , buttonEngineering;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        buttonAndroid = findViewById(R.id.androidBtn);

        //Set event handler
        buttonAndroid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this , NoteListDisplay.class);
                intent.putExtra("course" , "ANDROID");
                // start activity
                startActivity(intent );

            }
        });


        buttonArchitecture = findViewById(R.id.architectureBtn);

        //Set event handler for architecture course
        buttonArchitecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this , NoteListDisplay.class);
                intent.putExtra("course" , "ARCHITECTURE AND DESIGN");

                // start activity
                startActivity(intent);
            }
        });


        buttonEngineering = findViewById(R.id.engineerDesignBtn);

        //Set event handler for engineering course
        buttonEngineering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this , NoteListDisplay.class);
                intent.putExtra("course" , "ENGINEERING DESIGN 3");

                startActivity(intent );
            }
        });
    }


}

