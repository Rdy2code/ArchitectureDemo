package com.example.android.notetakingapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.android.notetakingapp.model.NoteEntity;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertNote (NoteEntity note);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertAll (List<NoteEntity> notes);

    @Delete
    void deleteNote (NoteEntity note);

    @Query("SELECT * FROM notes WHERE id = :id")
    NoteEntity getNoteById (int id);

    @Query("SELECT * FROM notes ORDER BY date DESC")
    LiveData<List<NoteEntity>> getAll();

    @Query ("DELETE FROM notes")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM notes")
    int getCount();
}