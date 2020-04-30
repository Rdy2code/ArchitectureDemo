package com.example.android.notetakingapp.database;

import androidx.room.TypeConverter;

import java.util.Date;

//Register this with AppDatabase class

public class DateConverter {

    @TypeConverter
    public static Date toDate(Long timeStamp) {

        //This means check if the timestamp is null and if it is return null, if not
        //return a new date object from a timestamp value
        return timeStamp == null ? null :  new Date (timeStamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
