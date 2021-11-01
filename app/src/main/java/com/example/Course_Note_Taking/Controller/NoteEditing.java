/**https://stackoverflow.com/questions/4118751/how-do-i-serialize-an-object-and-save-it-to-a-file-in-android*/
package com.example.Course_Note_Taking.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class NoteEditing extends AppCompatActivity {

    private Button backListBtn;
    private EditText titleText, mainNoteText;
    private Note note;
    private String titleAfterChanged , courseName;
    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editing);

        Intent intent = getIntent();
        String noteTitle = (String) intent.getExtras().get("noteTitle");

        courseName = intent.getExtras().get("courseName").toString();

        titleText = findViewById(R.id.titleText);

        // Check the title empty
        if (TextUtils.isEmpty(titleText.getText()))
        {
            titleText.setError(" Please do not leave the title blank.");
        }


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
        course = new Course(courseName);

        readFiles();


        // Check the scenario whether addBtn is pressed

        try {

            String booleanValue = intent.getExtras().get("newNote").toString();


            note = new Note();

            backListBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Check if the title is empty
                    if (titleText.getText().toString().equals("")){
                        titleText.setError("This note title does not allow value.");
                        titleText.setHint("Please fill in the title.");
                        return;
                    }

                    // Check if the title is already existed among the course.
                    if (course.checkNoteTitleExists(titleText.getText().toString())) {

                        titleText.setError("This note title is already existed.");
                        titleText.setHint("Please choose another one.");
                        Toast.makeText(NoteEditing.this, "This note title is already existed.\nPlease choose another one.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    note.setNoteName(titleText.getText().toString());
                    note.setContent(mainNoteText.getText().toString());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        note.setDateEdited(LocalDateTime.now());
                    }

                    course.getNoteList().add(note);


                    try {
                        writeFiles();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }

                    Intent intent1 = new Intent(NoteEditing.this, NoteListDisplay.class);
                    setResult(RESULT_OK , intent1);
                    Toast.makeText(NoteEditing.this, "Successfully created the new note", Toast.LENGTH_SHORT).show();
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

                    // Check if the title is empty
                    if (titleText.getText().toString().equals("")){
                        titleText.setError("This note title does not allow value.");
                        titleText.setHint("Please fill in the title.");
                        return;
                    }

                    // Check if the title has changed ?
                    if (note.getNoteName().compareTo(titleAfterChanged) != 0){

                        // Check if the new title is already existed among the course.
                        if (course.checkNoteTitleExists(titleText.getText().toString())) {
                            titleText.setError("This note title is already existed.");
                            titleText.setHint("Please choose another one.");
                            Toast.makeText(NoteEditing.this, "This note title is already existed.\nPlease choose another one.", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }

                    note.setNoteName(titleText.getText().toString());
                    note.setContent(mainNoteText.getText().toString());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        note.setDateEdited(LocalDateTime.now());
                    }

                    try {
                        writeFiles();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }

                    Toast.makeText(NoteEditing.this, "Successfully edited the note", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(NoteEditing.this, NoteListDisplay.class);

                    setResult(RESULT_OK , intent1);
                    finish();
                }


            });
        }
    }

    // read files
    public void readFiles(){
        try {

            String fileName;
            switch (courseName){
                case "ANDROID":
                    fileName = "android.ser";
                    break;
                case "ARCHITECTURE AND DESIGN":
                    fileName = "architecture.ser";
                    break;
                default:
                    fileName = "engineering.ser";

            }

            FileInputStream fIn = openFileInput(fileName );
            ObjectInputStream isr = new ObjectInputStream(fIn);



            // Fill the Buffer with data from the file
            course = (Course) isr.readObject();
            isr.close();
            fIn.close();


        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }


    public void writeFiles() throws IOException {

        try {

            String fileName;
            switch (courseName){
                case "ANDROID":
                    fileName = "android.ser";
                    break;
                case "ARCHITECTURE AND DESIGN":
                    fileName = "architecture.ser";
                    break;
                default:
                    fileName = "engineering.ser";

            }
            FileOutputStream fOut = openFileOutput(fileName,
                    MODE_PRIVATE);
            ObjectOutputStream osw = new ObjectOutputStream(fOut);


            // Write the string to the file
            osw.writeObject(course);


            /* ensure that everything is
             * really written out and close */
            osw.flush();
            osw.close();

        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
