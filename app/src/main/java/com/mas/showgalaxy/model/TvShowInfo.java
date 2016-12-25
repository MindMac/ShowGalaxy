package com.mas.showgalaxy.model;

import java.util.ArrayList;


public class TvShowInfo extends ShowInfo{
    private ArrayList<TvSeasonInfo> mTvSeasonShowInfos = new ArrayList<TvSeasonInfo>();

    public TvShowInfo(String showType, int showId, String title, double score, int voteCount, double popularity,
                      String backdropPath, String posterPath, String overview, String releaseDate,
                      ArrayList<String> genreNames, int runTime, ArrayList<TvSeasonInfo> tvSeasonShowInfos){
        super(showType, showId, title, score, voteCount, popularity, backdropPath, posterPath,
                overview, releaseDate, genreNames, runTime);
        this.mTvSeasonShowInfos = tvSeasonShowInfos;
    }

    public ArrayList<TvSeasonInfo> getTvSeasonShowInfos(){
        return mTvSeasonShowInfos;
    }
}


