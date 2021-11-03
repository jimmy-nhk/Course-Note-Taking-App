package com.example.Course_Note_Taking.Model;

import static android.content.Context.MODE_PRIVATE;
import android.content.Context;
import android.os.Build;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Course implements Serializable {

    private String courseName;
    private ArrayList<Note> noteList;

    public Course(String courseName) {
        this.courseName = courseName;
        this.noteList = new ArrayList<>();


    }

    public static ArrayList<Note> sortList(ArrayList<Note> noteList){
        Collections.sort(noteList);
        return noteList;
    }


    // Check note title exists
    public Note getNoteBasedOnTitle(String noteTitle){

        try{
            for (Note note: noteList){

                if (noteTitle.equals(note.getNoteName())){
                    return note;
                }
            }

        } catch (Exception e){
            e.printStackTrace();
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


