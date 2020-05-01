package com.example.android.notetakingapp.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.android.notetakingapp.constants.Constants;
import com.example.android.notetakingapp.database.AppDatabase;
import com.example.android.notetakingapp.model.NoteEntity;
import com.example.android.notetakingapp.utilities.SampleData;

import java.util.List;

public class AppRepository {

    private static volatile AppRepository sInstance;
    public List<NoteEntity> mNotes;

    //Constructor provides the data
    private AppRepository() {
        mNotes = SampleData.getNotes();
    }

    public static AppRepository getInstance() {
        if (sInstance == null) {
            synchronized (Constants.LOCK) {
                if (sInstance == null) {
                    sInstance = new AppRepository();
                }
            }
        }
        return sInstance;
    }
}
