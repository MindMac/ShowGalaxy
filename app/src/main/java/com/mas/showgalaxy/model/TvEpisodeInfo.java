package com.mas.showgalaxy.model;


public class TvEpisodeInfo {
    public String mReleaseDate;
    public String mName;
    public String mOverview;
    public String mBackdropPath;
    public int mEpisodeNum;

    public TvEpisodeInfo(String releaseDate, String name, String overview, String backdropPath,
                         int episodeNum){
        this.mReleaseDate = releaseDate;
        this.mName = name;
        this.mOverview = overview;
        this.mBackdropPath = backdropPath;
        this.mEpisodeNum = episodeNum;
    }

    public String getReleaseDate(){
        return mReleaseDate;
    }

    public String getName(){
        return mName;
    }

    public String getOverview(){
        return mOverview;
    }

    public String getBackdropPath(){
        return mBackdropPath;
    }

    public int getEpisodeNum(){
        return mEpisodeNum;
    }

}
