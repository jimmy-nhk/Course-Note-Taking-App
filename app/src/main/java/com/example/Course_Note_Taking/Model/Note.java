package com.example.Course_Note_Taking.Model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class Note implements Serializable , Comparable {



    private String noteName;
    private LocalDateTime dateEdited;
    private String content;



    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public LocalDateTime getDateEdited() {
        return dateEdited;
    }

    public void setDateEdited(LocalDateTime dateEdited) {
        this.dateEdited = dateEdited;

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteName='" + noteName + '\'' +
                ", dateEdited=" + dateEdited +
                ", content='" + content + '\'' +
                '}';
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int compareTo(Object o) {
        Note note = (Note) o;

        return this.dateEdited.compareTo(note.getDateEdited());
    }
}

