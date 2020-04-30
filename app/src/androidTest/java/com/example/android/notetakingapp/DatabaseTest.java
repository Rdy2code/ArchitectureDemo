package com.example.android.notetakingapp;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.android.notetakingapp.constants.Constants;
import com.example.android.notetakingapp.database.AppDatabase;
import com.example.android.notetakingapp.database.NoteDao;
import com.example.android.notetakingapp.model.NoteEntity;
import com.example.android.notetakingapp.utilities.SampleData;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    //Fields
    private AppDatabase mDb;
    private NoteDao mDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mDao = mDb.noteDao();
        Log.i(Constants.TAG, "createDb");
    }

    @Test
    public void createAndRetrievenotes() {
        mDao.insertAll(SampleData.getNotes());
        int count = mDao.getCount();
        Log.i(Constants.TAG, "createAndRetrievenotes: count= " + count);
        Assert.assertEquals(SampleData.getNotes().size(), count);
    }

    @Test
    public void compareStrings() {
        mDao.insertAll(SampleData.getNotes());
        NoteEntity original = SampleData.getNotes().get(0);
        NoteEntity fromDb = mDao.getNoteById(1);
        Assert.assertEquals(original.getText(), fromDb.getText());
        Log.i(Constants.TAG, "compareStrings: " + fromDb.getId());
        Assert.assertEquals(1, fromDb.getId());
    }

    @After
    public void closeDb() {
        mDb.close();
        Log.i(Constants.TAG, "closeDb");
    }
}
