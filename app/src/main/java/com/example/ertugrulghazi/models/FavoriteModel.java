package com.example.ertugrulghazi.models;
//
//import androidx.room.Entity;
//import androidx.room.PrimaryKey;

//@Entity(tableName = "fav")
public class FavoriteModel {

    private String epiId;
    private String seasonName;
    private String episodeName;
    private String url;
    private String thumbnail;

    public FavoriteModel() {
    }

    public FavoriteModel(String epiId, String seasonName, String episodeName, String url, String thumbnail) {
        this.epiId = epiId;
        this.seasonName = seasonName;
        this.episodeName = episodeName;
        this.url = url;
        this.thumbnail = thumbnail;
    }

    public String getEpiId() {
        return epiId;
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

    public String getThumbnail() {
        return thumbnail;
    }
}

