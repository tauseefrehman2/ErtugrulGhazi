package com.example.ertugrulghazi.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.ertugrulghazi.Dao.FavDAO;
import com.example.ertugrulghazi.database.database;
import com.example.ertugrulghazi.models.FavoriteModel;

import java.util.List;

public class FavRepo {

    private FavDAO favDAO;
    private LiveData<List<FavoriteModel>> allFavorite;

    public FavRepo(Application application) {
        database db = database.getInstance(application);
        favDAO = db.favDAO();
        allFavorite = favDAO.getAllFav();
    }

    //Make different methods for database operations

    public void insert(FavoriteModel favModel) {
        new InsertAmrAsyncTask(favDAO).execute(favModel);
    }

    public void update(FavoriteModel favModel) {
        new UpdateAmrAsyncTask(favDAO).execute(favModel);
    }

    public void delete(FavoriteModel favModel) {
        new DeleteAmrAsyncTask(favDAO).execute(favModel);
    }

    public LiveData<List<FavoriteModel>> getAllFavorite() {
        return allFavorite;
    }

    //Async task for inserting data
    private static class InsertAmrAsyncTask extends AsyncTask<FavoriteModel, Void, Void> {

        FavDAO amrDao;

        InsertAmrAsyncTask(FavDAO amrDao) {
            this.amrDao = amrDao;
        }

        @Override
        protected Void doInBackground(FavoriteModel... models) {
            amrDao.insert(models[0]);
            return null;
        }
    }

    //Async task for updating data
    private static class UpdateAmrAsyncTask extends AsyncTask<FavoriteModel, Void, Void> {

        FavDAO amrDao;

        UpdateAmrAsyncTask(FavDAO amrDao) {
            this.amrDao = amrDao;
        }

        @Override
        protected Void doInBackground(FavoriteModel... models) {
            amrDao.update(models[0]);
            return null;
        }
    }

    //Async task for deleting data
    private static class DeleteAmrAsyncTask extends AsyncTask<FavoriteModel, Void, Void> {

        FavDAO amrDao;

        DeleteAmrAsyncTask(FavDAO amrDao) {
            this.amrDao = amrDao;
        }

        @Override
        protected Void doInBackground(FavoriteModel... models) {
            amrDao.delete(models[0]);
            return null;
        }
    }
}
