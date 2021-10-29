package com.example.Course_Note_Taking.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.Course_Note_Taking.Model.Course;
import com.example.Course_Note_Taking.Model.Note;
import com.example.Course_Note_Taking.R;
import com.example.Course_Note_Taking.helper.CustomListAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class NoteEditing extends AppCompatActivity {

    Button backListBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editing);

        Intent intent = getIntent();
        String noteTitle = (String) intent.getExtras().get("noteTitle");

        String courseName = intent.getExtras().get("courseName").toString();

        // Create the course
        Course course = new Course(courseName);
        Note note = course.getNoteBasedOnTitle(noteTitle);

        Toast.makeText(NoteEditing.this, "" + note.getNoteName(), Toast.LENGTH_LONG).show();


        // Set the back button
        backListBtn = findViewById(R.id.backToListBtn);
        backListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: Needs to implement saving the note
                Intent intent1 = new Intent(NoteEditing.this , NoteListDisplay.class);

                setResult(RESULT_OK , intent1);
                finish();
            }
        });
    }
}