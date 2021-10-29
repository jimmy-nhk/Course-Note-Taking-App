package com.example.Course_Note_Taking.Model;

import android.os.Build;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Course {

    private String courseName;
    private ArrayList<Note> noteList;

    public Course(String courseName) {
        this.courseName = courseName;
        this.noteList = new ArrayList<>();

        populateDataBasedOnCourse(courseName);

    }

    // Check note title exists
    public Note getNoteBasedOnTitle(String noteTitle){

        for (Note note: noteList){

            if (noteTitle.equals(note.getNoteName())){
                return note;
            }
        }
        return null;
    }

    // Check note title exists
    public boolean checkNoteTitleExists(String noteTitle){

        for (Note note: noteList){

            if (noteTitle.equals(note.getNoteName())){
                return true;
            }
        }
        return false;
    }


    public void populateDataBasedOnCourse(String courseName){


        if(courseName.equals("ANDROID")){

            for (int i = 0 ; i < 4 ; i++){
                Note note1 = new Note();
                note1.setNoteName("Android First Note " + i);
                note1.setContent("Hello there");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    note1.setDateEdited(LocalDateTime.now());
                }

                noteList.add(note1);
            }

            return;
        }
        else if (courseName.equals("ARCHITECTURE AND DESIGN")){


            Note note1 = new Note();
            note1.setNoteName("Architecture First Note");
            note1.setContent("Hello Here");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                note1.setDateEdited(LocalDateTime.now());
            }

            noteList.add(note1);
            return;
        }
        else {
            Note note1 = new Note();
            note1.setNoteName("Engineering First Note");
            note1.setContent("Hello ReactJs");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                note1.setDateEdited(LocalDateTime.now());
            }

            noteList.add(note1);
            return;
        }
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public ArrayList<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(ArrayList<Note> noteList) {
        this.noteList = noteList;
    }
}

