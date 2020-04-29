package com.example.android.notetakingapp.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

//Will need a type converter for the date to store it in SQLite

@Entity(tableName = "notes")
public class NoteEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date date;
    private String text;

    @Ignore
    public NoteEntity() {
    }

    public NoteEntity(int id, Date date, String text) {
        this.id = id;
        this.date = date;
        this.text = text;
    }

    @Ignore
    public NoteEntity(Date date, String text) {
        this.date = date;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "NoteEntity{" +
                "id=" + id +
                ", date=" + date +
                ", text='" + text + '\'' +
                '}';
    }
}
