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

    private TextView courseText ;
    private String courseName;
    private Button backBtn ,addNoteBtn;
    private Course course;
    private final int SENDING_CODE_FROM_NOTE_LIST = 100;

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

        // Load the data based on the course
        readFiles();

        final ListView lv = (ListView) findViewById(R.id.note_list);
        lv.setAdapter(new CustomListAdapter(this, Course.sortList(course.getNoteList()) , this , courseName));


        // Set the back button
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                startActivityForResult(intent1 , SENDING_CODE_FROM_NOTE_LIST);
            }
        });

    }

    // read files
    public void readFiles(){
        try {

            // scan the file name
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

            // Open file
            FileInputStream fIn = openFileInput(fileName);
            ObjectInputStream isr = new ObjectInputStream(fIn);


            // Write to the object
            course = (Course) isr.readObject();
            isr.close();
            fIn.close();


        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check the request code
        if(requestCode == SENDING_CODE_FROM_NOTE_LIST){

            // check the result code
            if (resultCode == RESULT_OK){
                course = new Course(courseName);


                // Load the data based on the course
                readFiles();

                // update the list
                final ListView lv =  findViewById(R.id.note_list);
                lv.setAdapter(new CustomListAdapter(this, Course.sortList(course.getNoteList()) , this , courseName));

            }
        }
    }

}

