package com.example.ertugrulghazi.models;


public class EpisodeModel {

    private String videoId;
    private String episodeName;
    private String seasonName;

    public EpisodeModel() {
    }

    public EpisodeModel(String videoId, String episodeName) {
        this.videoId = videoId;
        this.episodeName = episodeName;
    }

    public EpisodeModel(String videoId, String episodeName, String seasonName) {
        this.videoId = videoId;
        this.episodeName = episodeName;
        this.seasonName = seasonName;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }
}
