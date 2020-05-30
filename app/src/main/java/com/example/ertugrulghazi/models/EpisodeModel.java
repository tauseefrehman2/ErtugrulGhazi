package com.example.ertugrulghazi.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "epi")
public class EpisodeModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String dramaName;
    private String seasonName;
    private String episodeName;
    private String url;
    private int isFav;
    private int thumbnail;

    public EpisodeModel(String dramaName, String seasonName, String episodeName, String url, int isFav, int thumbnail) {
        this.dramaName = dramaName;
        this.seasonName = seasonName;
        this.episodeName = episodeName;
        this.url = url;
        this.isFav = isFav;
        this.thumbnail = thumbnail;
    }

    @Ignore
    public EpisodeModel(int id, String dramaName, String seasonName, String episodeName, String url, int isFav, int thumbnail) {
        this.id = id;
        this.dramaName = dramaName;
        this.seasonName = seasonName;
        this.episodeName = episodeName;
        this.url = url;
        this.isFav = isFav;
        this.thumbnail = thumbnail;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDramaName() {
        return dramaName;
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

    public int getIsFav() {
        return isFav;
    }

    public int getThumbnail() {
        return thumbnail;
    }
}
