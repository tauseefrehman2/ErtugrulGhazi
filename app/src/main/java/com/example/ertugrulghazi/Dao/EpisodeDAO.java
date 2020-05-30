package com.example.ertugrulghazi.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ertugrulghazi.models.EpisodeModel;

import java.util.List;

@Dao
public interface EpisodeDAO {

    @Insert
    void insert(EpisodeModel model);

    @Update
    void update(EpisodeModel model);

    @Delete
    void delete(EpisodeModel model);

    @Query("Select * from epi")
    List<EpisodeModel> getAllEpisode();

    @Query("Select * from epi where seasonName=:seasonname")
    List<EpisodeModel> getAllEpisodeBySeason(String seasonname);

    @Query("Select * from epi where id=:favId limit 1")
    EpisodeModel getDramaById(int favId);
}
