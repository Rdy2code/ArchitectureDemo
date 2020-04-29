package com.example.android.notetakingapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.android.notetakingapp.constants.Constants;
import com.example.android.notetakingapp.model.NoteEntity;

@Database(entities = {NoteEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    //Singleton method variables. Volatile means it will be stored in main memory
    private static volatile AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (Constants.LOCK) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, Constants.DATABASE_NAME).build();
                }
            }
         }
        return instance;
    }

    //Public method to obtain instance of Dao
    public abstract NoteDao noteDao();
}
