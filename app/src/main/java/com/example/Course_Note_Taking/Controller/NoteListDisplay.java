package com.example.Course_Note_Taking.Controller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;

import com.example.Course_Note_Taking.Model.Note;
import com.example.Course_Note_Taking.R;
import com.example.Course_Note_Taking.helper.CustomListAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class NoteListDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_lists);

        ArrayList noteList = getListData();


        final ListView lv = (ListView) findViewById(R.id.note_list);
        lv.setAdapter(new CustomListAdapter(this, noteList));
    }


    public ArrayList<Note> getListData(){

        ArrayList<Note> noteList = new ArrayList<>();

        Note note1 = new Note();
        note1.setNoteName("First Title");
        note1.setContent("Hello there ??");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            note1.setDateEdited(LocalDateTime.now());
        }

        noteList.add(note1);

        return noteList;
    }
}