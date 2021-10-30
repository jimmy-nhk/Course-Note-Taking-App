package com.example.Course_Note_Taking.Controller;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class NoteListDisplay extends AppCompatActivity {

    TextView courseText ;
    String courseName;
    Button backBtn ,addNoteBtn;
    Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_lists);

        // Get the name of the course
        Intent intent = getIntent();
        courseName = intent.getExtras().get("course").toString();
        Toast.makeText(NoteListDisplay.this, courseName, Toast.LENGTH_SHORT).show();

        courseText = findViewById(R.id.courseDisplay);
        courseText.setText(courseName);

        // Create the course
        course = new Course(courseName);

        readFiles();

        // Load the data based on the course


        final ListView lv = (ListView) findViewById(R.id.note_list);
        lv.setAdapter(new CustomListAdapter(this, Course.sortList(course.getNoteList()) , this , courseName));


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

    }

    // read files
    public void readFiles(){
        try {
            /* We have to use the openFileInput()-method
             * the ActivityContext provides.
             * Again for security reasons with
             * openFileInput(...) */

            FileInputStream fIn = openFileInput("test.ser");
            ObjectInputStream isr = new ObjectInputStream(fIn);

            /* Prepare a char-Array that will
             * hold the chars we read back in. */


            // Fill the Buffer with data from the file
            course = (Course) isr.readObject();
            isr.close();
            fIn.close();


        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100){

            if (resultCode == RESULT_OK){
//                course = new Course(courseName);
//
//                for (int i = 0 ; i < 10 ; i++){
//                    readFiles();
//                }

                // TEST
                String noteTitle =  data.getExtras().get("noteTitle").toString();
                String noteContent =  data.getExtras().get("noteContent").toString();
                LocalDateTime time = (LocalDateTime) data.getExtras().get("dateEdited");
                Note note = new Note();
                note.setNoteName(noteTitle);
                note.setContent(noteContent);
                note.setDateEdited(time);
                course.getNoteList().add(note);

                try {
                    writeFiles();
                    wait(1000);
                } catch (IOException | InterruptedException exception) {
                    exception.printStackTrace();
                }

                // Load the data based on the course
                readFiles();

                final ListView lv =  findViewById(R.id.note_list);
                lv.setAdapter(new CustomListAdapter(this, Course.sortList(course.getNoteList()) , this , courseName));

            }
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

