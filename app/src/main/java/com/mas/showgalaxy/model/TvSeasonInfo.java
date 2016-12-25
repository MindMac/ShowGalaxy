package com.mas.showgalaxy.model;


public class TvSeasonInfo {
    public int mShowId;
    public String mReleaseDate;
    public String mName;
    public String mOverview;
    public String mPosterpath;
    public int mSeasonNum;

    public TvSeasonInfo(int showId, String releaseDate, String name, String overview,
                        String posterpath, int seasonNum){
        this.mShowId = showId;
        this.mReleaseDate = releaseDate;
        this.mName = name;
        this.mOverview = overview;
        this.mPosterpath = posterpath;
        this.mSeasonNum = seasonNum;
    }

    public TvSeasonInfo(int showId, String posterpath, int seasonNum){
        this.mShowId = showId;
        this.mPosterpath = posterpath;
        this.mSeasonNum = seasonNum;
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

    public int getShowId(){
        return mShowId;
    }

    public int getSeasonNum(){
        return mSeasonNum;
    }

    public String getPosterpath(){
        return mPosterpath;
    }

}
