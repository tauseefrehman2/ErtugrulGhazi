package com.example.ertugrulghazi.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fav")
public class FavoriteModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int epiId;
    private String seasonName;
    private String episodeName;
    private String url;

    public FavoriteModel(int epiId, String seasonName, String episodeName, String url) {
        this.epiId = epiId;
        this.seasonName = seasonName;
        this.episodeName = episodeName;
        this.url = url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEpiId() {
        return epiId;
    }

    public int getId() {
        return id;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public String getUrl() {
        return url;
    }
}

