package com.mas.showgalaxy.model;

import java.util.ArrayList;

public class ShowInfo {
    private String mShowType;
    private int mShowId;
    private String mTitle;
    private double mScore;
    private int mVoteCount;
    private double mPopularity;
    private String mBackdropPath;
    private String mPosterPath;
    private String mOverview;
    private String mReleaseDate;
    private int mRunTime;
    private ArrayList<Integer> mGenreIds = new ArrayList<Integer>();
    private ArrayList<String> mGenreNames = new ArrayList<String>();

    public ShowInfo(String showType, int showId, String title, String posterPath){
        this.mShowType = showType;
        this.mShowId = showId;
        this.mTitle = title;
        this.mPosterPath = posterPath;
    }

    public ShowInfo(String showType, int showId, String title, double score, int voteCount, double popularity,
                    String backdropPath, String posterPath, String overview, String releaseDate){
        this.mShowType = showType;
        this.mShowId = showId;
        this.mTitle = title;
        this.mBackdropPath = backdropPath;
        this.mScore = score;
        this.mVoteCount = voteCount;
        this.mPopularity = popularity;
        this.mPosterPath = posterPath;
        this.mOverview = overview;
        this.mReleaseDate = releaseDate;
    }

    public ShowInfo(String showType, int showId, String title, double score, int voteCount, double popularity,
                    String backdropPath, String posterPath, String overview, String releaseDate,
                    ArrayList<Integer> genreIds){
        this(showType, showId, title, score, voteCount, popularity, backdropPath, posterPath, overview, releaseDate);
        this.mGenreIds = genreIds;
    }

    public ShowInfo(String showType, int showId, String title, double score, int voteCount, double popularity,
                    String backdropPath, String posterPath, String overview, String releaseDate,
                    ArrayList<String> genreNames, int runTime){
        this(showType, showId, title, score, voteCount, popularity, backdropPath, posterPath, overview, releaseDate);
        this.mGenreNames = genreNames;
        this.mRunTime = runTime;
    }

    public String getShowType(){
        return mShowType;
    }

    public int getShowId(){
        return mShowId;
    }

    public String getShowTitle(){
        return mTitle;
    }

    public String getBackdropPath(){
        return mBackdropPath;
    }

    public double getScore(){
        return mScore;
    }

    public int getVoteCount(){
        return mVoteCount;
    }

    public double getPopularity(){
        return mPopularity;
    }

    public String getPosterPath(){
        return mPosterPath;
    }

    public String getOverview(){
        return mOverview;
    }

    public ArrayList<Integer> getGenreIds(){
        return mGenreIds;
    }

    public String getReleaseDate(){
        return mReleaseDate;
    }

    public int getRunTime(){
        return mRunTime;
    }

    public ArrayList<String> getGenreNames(){
        return mGenreNames;
    }

}
