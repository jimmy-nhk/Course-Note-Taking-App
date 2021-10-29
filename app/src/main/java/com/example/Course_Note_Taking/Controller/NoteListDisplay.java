
package com.example.Course_Note_Taking.Controller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Course_Note_Taking.Model.Course;
import com.example.Course_Note_Taking.Model.Note;
import com.example.Course_Note_Taking.R;
import com.example.Course_Note_Taking.helper.CustomListAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class NoteListDisplay extends AppCompatActivity {

    TextView courseText ;
    String courseName;
    Button backBtn;

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

        // Set the back button
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(NoteListDisplay.this , HomeScreen.class);

                setResult(RESULT_OK , intent1);
                finish();
            }
        });

        // Create the course
        Course course = new Course(courseName);

        // Load the data based on the course


        final ListView lv = (ListView) findViewById(R.id.note_list);
        lv.setAdapter(new CustomListAdapter(this, course.getNoteList() , this , courseName));
    }



}
