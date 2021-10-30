package com.example.Course_Note_Taking.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    EditText titleText, mainNoteText;
    Note note;
    String titleBeforeChanged, titleAfterChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editing);

        Intent intent = getIntent();
        String noteTitle = (String) intent.getExtras().get("noteTitle");

        String courseName = intent.getExtras().get("courseName").toString();

        titleText = findViewById(R.id.titleText);
        titleText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                titleAfterChanged = s.toString();
                Toast.makeText(NoteEditing.this, titleAfterChanged, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mainNoteText = findViewById(R.id.noteMainText);

        // Set the back button
        backListBtn = findViewById(R.id.backToListBtn);

        // Create the course
        Course course = new Course(courseName);


        // Check the scenario which addBtn is pressed

        try {
            String booleanValue = intent.getExtras().get("newNote").toString();
            boolean checked = booleanValue.equals("true");

            note = new Note();

            backListBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (course.checkNoteTitleExists(titleText.getText().toString())) {
                        Toast.makeText(NoteEditing.this, "This note title is already existed.\nPlease choose another one.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    note.setNoteName(titleText.getText().toString());
                    note.setContent(mainNoteText.getText().toString());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        note.setDateEdited(LocalDateTime.now());
                    }

                    course.getNoteList().add(note);
                    //TODO: write note1 to object

                    finish();
                }
            });
        } catch (Exception e) {

            note = course.getNoteBasedOnTitle(noteTitle);
            titleText.setText(note.getNoteName());
            mainNoteText.setText(note.getContent());

            backListBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (note.getNoteName().compareTo(titleAfterChanged) != 0)

                        if (course.checkNoteTitleExists(titleText.getText().toString())) {
                            Toast.makeText(NoteEditing.this, "This note title is already existed.\nPlease choose another one.", Toast.LENGTH_LONG).show();
                            return;
                        }

                    note.setNoteName(titleText.getText().toString());
                    note.setContent(mainNoteText.getText().toString());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        note.setDateEdited(LocalDateTime.now());
                    }
                    //TODO: write note1 to object

                    finish();
                }


            });
        }
    }
}
