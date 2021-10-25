package com.example.Course_Note_Taking.Model;

import java.time.LocalDateTime;
import java.util.Date;

public class Note {

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
}
