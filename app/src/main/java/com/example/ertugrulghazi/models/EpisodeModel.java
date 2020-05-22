package com.example.ertugrulghazi.models;

public class EpisodeModel {

    //ID must be unique
    private int EpiId;
    private String DramaName;
    private String SeasonName;
    private String episodeName;
    private String url;

    public EpisodeModel(int EpiId, String dramaName, String seasonName, String episodeName, String url) {
        this.EpiId = EpiId;
        DramaName = dramaName;
        SeasonName = seasonName;
        this.episodeName = episodeName;
        this.url = url;
    }

    public int getEpiId() {
        return EpiId;
    }

    public String getDramaName() {
        return DramaName;
    }

    public String getSeasonName() {
        return SeasonName;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public String getUrl() {
        return url;
    }
}
