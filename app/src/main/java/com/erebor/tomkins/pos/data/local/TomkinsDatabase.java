package com.erebor.tomkins.pos.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.erebor.tomkins.pos.data.local.dao.UserDao;
import com.erebor.tomkins.pos.data.local.model.UserModel;


@Database(entities = {
        UserModel.class
}, version = 1)
public abstract class TomkinsDatabase extends RoomDatabase {
    public static final String DB_NAME = "TOMKINS.db";

    private static TomkinsDatabase INSTANCE;

    public abstract UserDao userDAO();

    private static final Object sLock = new Object();

    public static TomkinsDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        TomkinsDatabase.class, DB_NAME)
                        .build();
            }
            return INSTANCE;
        }
    }
}
