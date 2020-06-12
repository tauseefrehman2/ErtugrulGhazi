package com.example.ertugrulghazi.models;

public class FavoriteModel {

    private String userId;
    private String videoId;
    private String seasonName;
    private String episodeName;

    public FavoriteModel() {
    }

    public FavoriteModel(String userId, String videoId, String seasonName, String episodeName) {
        this.userId = userId;
        this.videoId = videoId;
        this.seasonName = seasonName;
        this.episodeName = episodeName;
    }

    public String getUserId() {
        return userId;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public String getEpisodeName() {
        return episodeName;
    }
}

