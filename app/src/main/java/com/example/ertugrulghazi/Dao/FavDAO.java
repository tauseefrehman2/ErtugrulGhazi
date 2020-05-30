package com.example.ertugrulghazi.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ertugrulghazi.models.FavoriteModel;

import java.util.List;

@Dao
public interface FavDAO {

    @Insert
    void insert(FavoriteModel model);

    @Update
    void update(FavoriteModel model);

    @Delete
    void delete(FavoriteModel model);

    @Query("Select * from fav order by id desc")
    List<FavoriteModel> getAllFav();

    @Query("Select * from fav where epiId=:epiId")
    FavoriteModel getDramaById(int epiId);


}
