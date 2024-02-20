package com.skyblue.roomdatabase.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.skyblue.roomdatabase.model.Post;

@Database(entities = {Post.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "post";
    private static AppDatabase mInstance;

    public static AppDatabase getInstance(Context context){
        if (mInstance == null){
            synchronized (LOCK){
                Log.e(TAG, "Creating new database instance");
                mInstance = Room.databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.e(TAG, "Getting the database instance");
        return mInstance;
    }
    public abstract PostDao postDao();
}