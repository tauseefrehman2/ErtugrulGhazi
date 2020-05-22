package com.example.ertugrulghazi.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.ertugrulghazi.Dao.FavDAO;
import com.example.ertugrulghazi.models.FavoriteModel;

@Database(entities = {FavoriteModel.class,}, version = 2, exportSchema = false)
public abstract class database extends RoomDatabase {

    private static database instance;

    //Because we are initializing database here
    //Room automatically connect our code to this methods

    public abstract FavDAO favDAO();

    //Create object using singleton method
    public static synchronized database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext()
                    , database.class, "ertugrul_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
