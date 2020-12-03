package com.example.roomdatabaseapp;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MainData.class},version = 1,exportSchema = false)
public abstract class RoomDB  extends RoomDatabase {

    private static RoomDB database;

    private static String Database_name = "database";

    public synchronized static RoomDB getInstance(Context context)
    {
        if(database==null)
        {
            database = Room.databaseBuilder(context.getApplicationContext()
                ,RoomDB.class,Database_name)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }



    public abstract MainDao mainDao();
}
