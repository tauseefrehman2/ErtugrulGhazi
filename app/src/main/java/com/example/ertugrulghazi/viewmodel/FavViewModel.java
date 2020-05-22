package com.example.ertugrulghazi.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ertugrulghazi.models.FavoriteModel;
import com.example.ertugrulghazi.repository.FavRepo;

import java.util.List;

public class FavViewModel extends AndroidViewModel {

    private FavRepo repository;
    private LiveData<List<FavoriteModel>> getAllFav;

    public FavViewModel(@NonNull Application application) {
        super(application);
        repository = new FavRepo(application);
        getAllFav = repository.getAllFavorite();
    }

    public void insert(FavoriteModel model) {
        repository.insert(model);
    }

    public void update(FavoriteModel model) {
        repository.update(model);
    }

    public void delete(FavoriteModel model) {
        repository.delete(model);
    }

    public LiveData<List<FavoriteModel>> getAllFav() {
        return getAllFav;
    }
}
