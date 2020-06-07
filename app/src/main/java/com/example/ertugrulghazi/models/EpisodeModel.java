package com.example.ertugrulghazi.models;


public class EpisodeModel {

    private String id;
    private String episodeName;
    private String url;
    private String thumbnail;
    private String seasonName;

    public EpisodeModel() {
    }

    public EpisodeModel(String id, String episodeName, String url, String thumbnail) {
        this.id = id;
        this.episodeName = episodeName;
        this.url = url;
        this.thumbnail = thumbnail;
    }

    public EpisodeModel(String id, String episodeName, String url, String thumbnail, String seasonName) {
        this.id = id;
        this.episodeName = episodeName;
        this.url = url;
        this.thumbnail = thumbnail;
        this.seasonName = seasonName;
    }

    public String getId() {
        return id;
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

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }
}
