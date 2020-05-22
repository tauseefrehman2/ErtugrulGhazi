package com.example.ertugrulghazi.models;

public class SeasonModel {

    private int image;
    private String dramaName;
    private String seasonName;

    public SeasonModel(int image, String dramaName, String seasonName) {
        this.image = image;
        this.dramaName = dramaName;
        this.seasonName = seasonName;
    }

    public int getImage() {
        return image;
    }

    public String getDramaName() {
        return dramaName;
    }

    public String getSeasonName() {
        return seasonName;
    }
}
