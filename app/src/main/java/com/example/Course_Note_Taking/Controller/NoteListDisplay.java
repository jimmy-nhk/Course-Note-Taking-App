package com.example.Course_Note_Taking.Controller;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Course_Note_Taking.Model.Course;
import com.example.Course_Note_Taking.Model.Note;
import com.example.Course_Note_Taking.R;
import com.example.Course_Note_Taking.helper.CustomListAdapter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class NoteListDisplay extends AppCompatActivity {

    TextView courseText ;
    String courseName;
    Button backBtn ,addNoteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        try {
//            Course.writeFiles();
//        } catch (IOException exception) {
//            exception.printStackTrace();
//        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_lists);

        // Get the name of the course
        Intent intent = getIntent();
        courseName = intent.getExtras().get("course").toString();
        Toast.makeText(NoteListDisplay.this, courseName, Toast.LENGTH_SHORT).show();

        courseText = findViewById(R.id.courseDisplay);
        courseText.setText(courseName);

        // Set the back button
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent1 = new Intent(NoteListDisplay.this , HomeScreen.class);
//
//                setResult(RESULT_OK , intent1);
                finish();
            }
        });

        // Set the addNote button
        addNoteBtn = findViewById(R.id.addBtn);
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(NoteListDisplay.this , NoteEditing.class);

                intent1.putExtra("courseName" , courseName);
                intent1.putExtra("newNote" , "true");

                startActivityForResult(intent1 , 100);
            }
        });


        // Create the course
        Course course = new Course(courseName);


        // Load the data based on the course


        final ListView lv = (ListView) findViewById(R.id.note_list);
        lv.setAdapter(new CustomListAdapter(this, course.getNoteList() , this , courseName));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 200){
            if (resultCode == RESULT_OK){

            }
        }
    }
}

