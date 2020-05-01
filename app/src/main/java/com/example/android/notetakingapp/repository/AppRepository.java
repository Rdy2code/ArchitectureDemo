package com.example.android.notetakingapp.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.android.notetakingapp.constants.Constants;
import com.example.android.notetakingapp.database.AppDatabase;
import com.example.android.notetakingapp.model.NoteEntity;
import com.example.android.notetakingapp.utilities.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {

    private static volatile AppRepository sInstance;
    public LiveData<List<NoteEntity>> mNotes;
    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    //Constructor provides the data
    private AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        mNotes = getAllNotes();
    }

    public static AppRepository getInstance(Context context) {
        if (sInstance == null) {
            synchronized (Constants.LOCK) {
                if (sInstance == null) {
                    sInstance = new AppRepository(context);
                }
            }
        }
        return sInstance;
    }

    //Here is where we access the Room database on a background thread
    //Add the sample data to the database
    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDao().insertAll(SampleData.getNotes());
            }
        });
    }

    private LiveData<List<NoteEntity>> getAllNotes() {
        return mDb.noteDao().getAll();
    }


    //Need to use an executor when not returning a LiveData object
    public void deleteAllNotes() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDao().deleteAll();
            }
        });
    }
}
