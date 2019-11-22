package com.example.word.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.word.model.Question;
import com.example.word.model.User;
import com.example.word.model.Words;

@Database(entities = {Words.class, User.class, Question.class}, version=1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "word")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract AppDao dao();
}