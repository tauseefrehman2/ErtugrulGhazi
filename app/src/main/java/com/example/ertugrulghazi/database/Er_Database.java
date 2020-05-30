package com.example.ertugrulghazi.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.ertugrulghazi.Dao.EpisodeDAO;
import com.example.ertugrulghazi.Dao.FavDAO;
import com.example.ertugrulghazi.R;
import com.example.ertugrulghazi.models.EpisodeModel;
import com.example.ertugrulghazi.models.FavoriteModel;

@Database(entities = {FavoriteModel.class, EpisodeModel.class}, version = 18, exportSchema = false)
public abstract class Er_Database extends RoomDatabase {

    private static Er_Database Er_Database;

    public abstract FavDAO favDAO();

    public abstract EpisodeDAO episodeDAO();

    public static Er_Database getInstance(Context context) {
        if (null == Er_Database) {
            Er_Database = Room.databaseBuilder(context.getApplicationContext(),
                    Er_Database.class, "ertugrul_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(roomCallBack)
                    .build();
        }
        return Er_Database;
    }

    public void cleanUp() {
        Er_Database = null;
    }


    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
