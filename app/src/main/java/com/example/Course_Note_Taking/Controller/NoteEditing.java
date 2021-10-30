/**https://stackoverflow.com/questions/4118751/how-do-i-serialize-an-object-and-save-it-to-a-file-in-android*/
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

    Button backListBtn;
    EditText titleText, mainNoteText;
    Note note;
    String titleBeforeChanged, titleAfterChanged;
    Course course;

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
        course = new Course(courseName);

        readFiles();

        // Check the scenario which addBtn is pressed

        try {
            String booleanValue = intent.getExtras().get("newNote").toString();


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

                    try {
                        writeFiles();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }

                    Intent intent1 = new Intent(NoteEditing.this, NoteListDisplay.class);
                    setResult(RESULT_OK , intent1);
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
                        Toast.makeText(NoteEditing.this, "Set current time successfully.", Toast.LENGTH_LONG).show();
                        note.setDateEdited(LocalDateTime.now());
                    }

                    try {
                        writeFiles();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }

                    Intent intent1 = new Intent(NoteEditing.this, NoteListDisplay.class);
                    intent1.putExtra("noteTitle" ,titleText.getText().toString() );
                    intent1.putExtra("noteContent" , mainNoteText.getText().toString());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        intent1.putExtra("dateEdited", LocalDateTime.now());
                    }
                    setResult(RESULT_OK , intent1);
                    finish();
                }


            });
        }
    }

    // read files
    public void readFiles(){
        try {
            /* We have to use the openFileInput()-method
             * the ActivityContext provides.
             * Again for security reasons with
             * openFileInput(...) */

            FileInputStream fIn = openFileInput("test.ser" );
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
            /* We have to use the openFileOutput()-method
             * the ActivityContext provides, to
             * protect your file from others and
             * This is done for security-reasons.
             * We chose MODE_WORLD_READABLE, because
             *  we have nothing to hide in our file */
            FileOutputStream fOut = openFileOutput("test.ser",
                    MODE_PRIVATE);
            ObjectOutputStream osw ;

//            BufferedOutputStream osw = new BufferedOutputStream(fOut));

            RandomAccessFile raf = new RandomAccessFile("test.ser", "rw");
            FileOutputStream fos = new FileOutputStream(raf.getFD());
            osw = new ObjectOutputStream(fos);


            // Write the string to the file
            osw.writeObject(course);

            wait(1000000);
            /* ensure that everything is
             * really written out and close */
            osw.flush();
            osw.close();

        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
